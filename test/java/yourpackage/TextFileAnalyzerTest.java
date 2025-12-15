import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextFileAnalyzerTest {
    
    @Test
    void testAnalyzeFile() throws IOException {
        TextFileAnalyzer analyzer = new TextFileAnalyzer();
      
        Path testFile = Files.createTempFile("test", ".txt");
        List<String> lines = Arrays.asList("Hello world!", "This is test.", "Third line");
        Files.write(testFile, lines);
        
        TextFileAnalyzer.AnalysisResult result = analyzer.analyzeFile(testFile.toString());
        
        assertEquals(3, result.getLineCount(), "Количество строк должно быть 3");
        assertEquals(6, result.getWordCount(), "Количество слов должно быть 6");
        assertEquals(39, result.getCharCount(), "Количество символов должно быть 39");
        
        var frequency = result.getCharFrequency();
        assertEquals(3, frequency.get('l'), "Символ 'l' должен встречаться 3 раза");
        assertEquals(3, frequency.get('e'), "Символ 'e' должен встречаться 3 раза");
        assertTrue(frequency.get('H') >= 1, "Символ 'H' должен встречаться хотя бы 1 раз");
        
        assertTrue(frequency.get(' ') >= 5, "Пробелов должно быть хотя бы 5");
    }
    
    @Test
    void testAnalyzeEmptyFile() throws IOException {
        TextFileAnalyzer analyzer = new TextFileAnalyzer();
        
        Path testFile = Files.createTempFile("empty", ".txt");
        Files.write(testFile, new byte[0]);
        
        TextFileAnalyzer.AnalysisResult result = analyzer.analyzeFile(testFile.toString());
        
        assertEquals(0, result.getLineCount());
        assertEquals(0, result.getWordCount());
        assertEquals(0, result.getCharCount());
        assertTrue(result.getCharFrequency().isEmpty());
    }
    
    @Test
    void testSaveAnalysisResult() throws IOException {
        TextFileAnalyzer analyzer = new TextFileAnalyzer();
        
        var frequency = new java.util.HashMap<Character, Integer>();
        frequency.put('a', 3);
        frequency.put('b', 2);
        frequency.put('c', 1);
        TextFileAnalyzer.AnalysisResult result = new TextFileAnalyzer.AnalysisResult(2, 5, 20, frequency);
        
        Path outputFile = Files.createTempFile("analysis", ".txt");
        analyzer.saveAnalysisResult(result, outputFile.toString());
        
        assertTrue(Files.exists(outputFile), "Файл должен существовать");
        assertTrue(Files.size(outputFile) > 0, "Размер файла должен быть больше 0");
        
        List<String> lines = Files.readAllLines(outputFile);
        assertFalse(lines.isEmpty(), "Файл не должен быть пустым");
        
        String content = String.join("\n", lines);
        assertTrue(content.contains("Количество строк: 2"));
        assertTrue(content.contains("Количество слов: 5"));
        assertTrue(content.contains("Количество символов: 20"));
        assertTrue(content.contains("'a' : 3"));
        assertTrue(content.contains("'b' : 2"));
        assertTrue(content.contains("'c' : 1"));
    }
    
    @Test
    void testAnalyzeFileWithSpecialCharacters() throws IOException {
        TextFileAnalyzer analyzer = new TextFileAnalyzer();
        
        Path testFile = Files.createTempFile("special", ".txt");
        List<String> lines = Arrays.asList("Line\twith\ttabs", "Line with  spaces", "End");
        Files.write(testFile, lines);
        
        TextFileAnalyzer.AnalysisResult result = analyzer.analyzeFile(testFile.toString());
        
        assertEquals(3, result.getLineCount());
        assertEquals(6, result.getWordCount()); // "Line with tabs", "Line with spaces", "End"
        
        var frequency = result.getCharFrequency();
        assertTrue(frequency.get('\t') >= 2, "Должно быть минимум 2 таба");
        assertTrue(frequency.get(' ') >= 2, "Должно быть минимум 2 пробела");
    }
}
