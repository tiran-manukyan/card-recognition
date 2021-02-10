package entity;

import pack.FileReader;
import pack.ImageComparator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public enum Suit {

    CLUBS('c', "clubs"),
    DIAMONDS('d', "diamonds"),
    HEARTS('h', "hearts"),
    SPADES('s', "spades");

    private static final List<BufferedImage> SUITS_IMAGES = new ArrayList<>(4);

    static {
        try {
            for (Suit value : values()) {
                BufferedImage image = FileReader.getImageFromResources("suit/" + value.name + ".png");
                SUITS_IMAGES.add(image);
            }
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading suit images from resources.", e);
        }
    }

    private final char symbol;
    private final String name;

    Suit(char symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    public char getSymbol() {
        return symbol;
    }

    public static Suit fromImage(BufferedImage img) {
        ImageComparator imageComparator = new ImageComparator();

        Suit[] suits = values();
        List<BufferedImage> images = SUITS_IMAGES;

        BufferedImage first = images.get(0);

        double minDiff = imageComparator.getDifferencePercent(first, img);
        int minIndex = 0;

        for (int i = 1; i < images.size(); i++) {
            BufferedImage image = images.get(i);
            double diff = imageComparator.getDifferencePercent(image, img);
            if (diff < minDiff) {
                minDiff = diff;
                minIndex = i;
            }
        }

        return minDiff < Constants.MAX_DIFF_PERCENT ? suits[minIndex] : null;
    }
}