import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FileProcessorTest {
    
    @TempDir
    Path tempDir;
    
    @Test
    void testSplitAndMergeFile() throws IOException {
        FileProcessor processor = new FileProcessor();
        
        Path testFile = tempDir.resolve("test.dat");
        byte[] testData = new byte[1500];
        new Random().nextBytes(testData);
        Files.write(testFile, testData);
        
        Path outputDir = tempDir.resolve("parts");
        Files.createDirectory(outputDir);
        
        List<Path> parts = processor.splitFile(testFile.toString(), outputDir.toString(), 500);
        
        assertEquals(3, parts.size(), "Должно быть 3 части");
        
        assertEquals(500, Files.size(parts.get(0)), "Первая часть должна быть 500 байт");
        assertEquals(500, Files.size(parts.get(1)), "Вторая часть должна быть 500 байт");
        assertEquals(500, Files.size(parts.get(2)), "Третья часть должна быть 500 байт");
        
        assertTrue(parts.get(0).getFileName().toString().endsWith(".part1"));
        assertTrue(parts.get(1).getFileName().toString().endsWith(".part2"));
        assertTrue(parts.get(2).getFileName().toString().endsWith(".part3"));
        
        Path mergedFile = tempDir.resolve("merged.dat");
        processor.mergeFiles(parts, mergedFile.toString());
        
        assertArrayEquals(Files.readAllBytes(testFile), Files.readAllBytes(mergedFile));
    }
    
    @Test
    void testSplitFileExactSize() throws IOException {
        FileProcessor processor = new FileProcessor();
      
        Path testFile = tempDir.resolve("exact.dat");
        byte[] testData = new byte[1000];
        Arrays.fill(testData, (byte) 42);
        Files.write(testFile, testData);
        
        Path outputDir = tempDir.resolve("exactParts");
        Files.createDirectory(outputDir);
        
        List<Path> parts = processor.splitFile(testFile.toString(), outputDir.toString(), 500);
        
        assertEquals(2, parts.size(), "Должно быть 2 части по 500 байт каждая");
        assertEquals(500, Files.size(parts.get(0)));
        assertEquals(500, Files.size(parts.get(1)));
    }
    
    @Test
    void testSplitFileSmallLastPart() throws IOException {
        FileProcessor processor = new FileProcessor();
        
        Path testFile = tempDir.resolve("smalllast.dat");
        byte[] testData = new byte[1200];
        new Random().nextBytes(testData);
        Files.write(testFile, testData);
        
        Path outputDir = tempDir.resolve("smallParts");
        Files.createDirectory(outputDir);
        
        List<Path> parts = processor.splitFile(testFile.toString(), outputDir.toString(), 500);
        
        assertEquals(3, parts.size(), "Должно быть 3 части");
        assertEquals(500, Files.size(parts.get(0)), "Первая часть: 500 байт");
        assertEquals(500, Files.size(parts.get(1)), "Вторая часть: 500 байт");
        assertEquals(200, Files.size(parts.get(2)), "Третья часть: 200 байт");
    }
    
    @Test
    void testMergeEmptyList() {
        FileProcessor processor = new FileProcessor();
        
        assertThrows(IllegalArgumentException.class, () -> {
            processor.mergeFiles(Arrays.asList(), "output.dat");
        }, "Должно выбрасываться исключение при пустом списке частей");
    }
    
    @Test
    void testSplitNonExistentFile() {
        FileProcessor processor = new FileProcessor();
        
        assertThrows(IOException.class, () -> {
            processor.splitFile("nonexistent.dat", "output", 100);
        }, "Должно выбрасываться исключение при отсутствии файла");
    }
    
    @Test
    void testFindAndSortParts() throws IOException {
        FileProcessor processor = new FileProcessor();
        
        Path dir = tempDir.resolve("findParts");
        Files.createDirectory(dir);
        
        Files.createFile(dir.resolve("file.part3"));
        Files.createFile(dir.resolve("file.part1"));
        Files.createFile(dir.resolve("file.part2"));
        Files.createFile(dir.resolve("file.other"));
        
        List<Path> sortedParts = processor.findAndSortParts(dir.toString(), "file");
        
        assertEquals(3, sortedParts.size(), "Должно найти 3 части");
        assertTrue(sortedParts.get(0).toString().endsWith(".part1"));
        assertTrue(sortedParts.get(1).toString().endsWith(".part2"));
        assertTrue(sortedParts.get(2).toString().endsWith(".part3"));
    }
    
    @Test
    void testSplitAndMergeLargeFile() throws IOException {
        FileProcessor processor = new FileProcessor();
      
        Path testFile = tempDir.resolve("large.dat");
        byte[] testData = new byte[10240]; 
        new Random().nextBytes(testData);
        Files.write(testFile, testData);
        
        Path outputDir = tempDir.resolve("largeParts");
        Files.createDirectory(outputDir);
        
        List<Path> parts = processor.splitFile(testFile.toString(), outputDir.toString(), 1024);
        
        assertEquals(10, parts.size(), "Должно быть 10 частей по 1KB");
        
        Path mergedFile = tempDir.resolve("mergedLarge.dat");
        processor.mergeFiles(parts, mergedFile.toString());
        
        assertArrayEquals(Files.readAllBytes(testFile), Files.readAllBytes(mergedFile));
    }
}
