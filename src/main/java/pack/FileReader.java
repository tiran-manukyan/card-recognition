package pack;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class FileReader {

    public static List<Path> getFiles(String directoryPath) throws NoSuchFileException {
        try (Stream<Path> files = Files.walk(Paths.get(directoryPath), 1)) {
            return files.filter(Files::isRegularFile).filter(file -> file.toString().endsWith(".png")).collect(toList());
        } catch (NoSuchFileException e) {
            throw e;
        } catch (IOException e) {
            throw new RuntimeException("See cause.", e);
        }
    }

    public static BufferedImage getImageFromResources(String resource) throws IOException {
        InputStream inputStream = getResourceAsStream(resource);
        return inputStream != null ? ImageIO.read(inputStream) : null;
    }

    private static InputStream getResourceAsStream(String resource) {
        final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
        return in == null ? FileReader.class.getResourceAsStream(resource) : in;
    }
}