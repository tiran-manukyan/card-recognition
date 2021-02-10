package entity;

import pack.FileReader;
import pack.ImageComparator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum Rank {

    ACE("A", "ace"),
    TWO("2", "two"),
    THREE("3", "three"),
    FOUR("4", "four"),
    FIVE("5", "five"),
    SIX("6", "six"),
    SEVEN("7", "seven"),
    EIGHT("8", "eight"),
    NINE("9", "nine"),
    TEN("10", "ten"),
    JACK("J", "jack"),
    QUEEN("Q", "queen"),
    KING("K", "king");

    private static final Map<Group, List<BufferedImage>> RANKS_IMAGES = Map.ofEntries(
            Map.entry(Group.RED, new ArrayList<>(13)),
            Map.entry(Group.BLACK, new ArrayList<>(13))
    );

    static {
        try {
            List<BufferedImage> blackGroupOfRanks = RANKS_IMAGES.get(Group.BLACK);
            List<BufferedImage> redGroupOfRanks = RANKS_IMAGES.get(Group.RED);
            for (Rank value : values()) {
                blackGroupOfRanks.add(FileReader.getImageFromResources("rank/black/" + value.name + ".png"));
                redGroupOfRanks.add(FileReader.getImageFromResources("rank/red/" + value.name + ".png"));
            }

        } catch (IOException e) {
            throw new RuntimeException("An error occurred while reading rank images from resources.", e);
        }
    }

    private final String symbol;
    private final String name;

    Rank(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Rank fromImage(BufferedImage img, Group group) {
        ImageComparator imageComparator = new ImageComparator();

        Rank[] ranks = values();
        List<BufferedImage> images = RANKS_IMAGES.get(group);

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

        return minDiff < Constants.MAX_DIFF_PERCENT ? ranks[minIndex] : null;
    }

    public enum Group {
        RED,
        BLACK
    }
}