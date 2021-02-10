package pack;

import entity.Card;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("You must specify the path to the images directory!");
            return;
        }
        String directoryPath = args[0];
        List<Path> imagesPath;
        try {
            imagesPath = FileReader.getFiles(directoryPath);
        } catch (NoSuchFileException e) {
            System.err.printf("Specified path: \"%s\" does not exists.", directoryPath);
            return;
        }
        if (imagesPath.isEmpty()) {
            System.err.printf("No PNG files were found in the directory: %s%n", directoryPath);
            return;
        }
        long start = System.currentTimeMillis();
        System.out.printf("Found %s PNG files, process started...%n%n", imagesPath.size());

        List<String> allCardNames = new ArrayList<>();

        for (Path path : imagesPath) {
            BufferedImage image = ImageIO.read(new File(path.toString()));
            List<BufferedImage> cardImages = Card.getCardImagesFromBaseImage(image);

            StringBuilder cardNames = new StringBuilder(path.getFileName().toString() + " - ");
            for (BufferedImage cardImage : cardImages) {
                Card card = Card.fromImage(cardImage);
                if (card != null) {
                    cardNames.append(card.toString());
                }
            }
            allCardNames.add(cardNames.toString());
        }

        for (String path : allCardNames) {
            System.out.println(path);
        }

        System.out.printf("%n******** Done ********%n");
        System.out.printf("Recognized images count: %d%n", allCardNames.size());
        System.out.printf("Elapsed time: %d ms%n", System.currentTimeMillis() - start);
    }
}