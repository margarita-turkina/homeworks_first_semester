import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.*;

public class FileProcessor {
    
    public List<Path> splitFile(String sourcePath, String outputDir, int partSize) throws IOException {
        if (partSize <= 0) {
            throw new IllegalArgumentException("Размер части должен быть больше 0");
        }
        
        Path sourceFile = Paths.get(sourcePath);
        if (!Files.exists(sourceFile)) {
            throw new FileNotFoundException("Исходный файл не найден: " + sourcePath);
        }
        
        List<Path> parts = new ArrayList<>();
        String fileName = sourceFile.getFileName().toString();
        
        try (FileChannel sourceChannel = FileChannel.open(sourceFile, StandardOpenOption.READ)) {
            long fileSize = sourceChannel.size();
            long bytesProcessed = 0;
            int partNumber = 1;
            
            ByteBuffer buffer = ByteBuffer.allocate(partSize);
            
            while (bytesProcessed < fileSize) {
                int currentPartSize = (int) Math.min(partSize, fileSize - bytesProcessed);
                buffer.clear();
                if (buffer.capacity() > currentPartSize) {
                    buffer = ByteBuffer.allocate(currentPartSize);
                }
                
                int bytesRead = sourceChannel.read(buffer);
                if (bytesRead == -1) {
                    break;
                }
                
                String partName = String.format("%s.part%d", fileName, partNumber);
                Path partPath = Paths.get(outputDir, partName);
                
                try (FileChannel partChannel = FileChannel.open(
                        partPath, 
                        StandardOpenOption.CREATE, 
                        StandardOpenOption.WRITE)) {
                    
                    buffer.flip();
                    partChannel.write(buffer);
                }
                
                parts.add(partPath);
                bytesProcessed += bytesRead;
                partNumber++;
            }
        }
        
        return parts;
    }
    
    public void mergeFiles(List<Path> partPaths, String outputPath) throws IOException {
        if (partPaths == null || partPaths.isEmpty()) {
            throw new IllegalArgumentException("Список частей не может быть пустым");
        }
        
        for (Path part : partPaths) {
            if (!Files.exists(part)) {
                throw new FileNotFoundException("Часть не найдена: " + part);
            }
        }
        
        Path outputFile = Paths.get(outputPath);
        
        try (FileChannel outputChannel = FileChannel.open(
                outputFile, 
                StandardOpenOption.CREATE, 
                StandardOpenOption.WRITE)) {
            
            ByteBuffer buffer = ByteBuffer.allocate(8192); // 8KB буфер
            
            for (Path partPath : partPaths) {
                try (FileChannel partChannel = FileChannel.open(partPath, StandardOpenOption.READ)) {
                    long partSize = partChannel.size();
                    long bytesProcessed = 0;
                    
                    while (bytesProcessed < partSize) {
                        buffer.clear();
                        int bytesRead = partChannel.read(buffer);
                        if (bytesRead == -1) {
                            break;
                        }
                        
                        buffer.flip();
                        outputChannel.write(buffer);
                        bytesProcessed += bytesRead;
                    }
                }
            }
            
            outputChannel.force(true);
        }
    }
    
    public List<Path> findAndSortParts(String inputDir, String baseName) throws IOException {
        Path dir = Paths.get(inputDir);
        if (!Files.exists(dir) || !Files.isDirectory(dir)) {
            throw new IllegalArgumentException("Директория не существует: " + inputDir);
        }
        
        List<Path> parts = new ArrayList<>();
        
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, baseName + ".part*")) {
            for (Path path : stream) {
                parts.add(path);
            }
        }
        
        parts.sort((p1, p2) -> {
            String name1 = p1.getFileName().toString();
            String name2 = p2.getFileName().toString();
            
            int num1 = extractPartNumber(name1);
            int num2 = extractPartNumber(name2);
            
            return Integer.compare(num1, num2);
        });
        
        return parts;
    }
    
    private int extractPartNumber(String fileName) {
        try {
            // Ищем .partX в конце имени файла
            int start = fileName.lastIndexOf(".part");
            if (start != -1) {
                String numberStr = fileName.substring(start + 5);
                return Integer.parseInt(numberStr);
            }
        } catch (NumberFormatException e) {
        }
        return Integer.MAX_VALUE;
    }
}
