package entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static entity.Suit.SPADES;

public class Card {

    private final Suit suit;
    private final Rank rank;

    private Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public static Card fromImage(BufferedImage cardImage) {
        BufferedImage suitImage = getSuitImageFromCardImage(cardImage);
        Suit suit = Suit.fromImage(suitImage);
        if (suit == null) {
            return null;
        }

        BufferedImage rankImage = getRankImageFromCardImage(cardImage);
        Rank.Group rankGroup = Rank.Group.RED;
        if (suit == Suit.CLUBS || suit == SPADES) {
            rankGroup = Rank.Group.BLACK;
        }
        Rank rank = Rank.fromImage(rankImage, rankGroup);
        return rank != null ? new Card(suit, rank) : null;
    }

    public static List<BufferedImage> getCardImagesFromBaseImage(BufferedImage img) {
        List<BufferedImage> cards = new ArrayList<>();

        int imageWidth = img.getWidth();
        int imageHeight = img.getHeight();
        int cardsSectionX = (int) (imageWidth / 4.49);
        int cardsSectionY = (int) (imageHeight / 1.996);
        int cardsSectionWidth = (int) (imageWidth / 1.8);
        int cardsSectionHeight = (int) (imageHeight / 12.955);

        BufferedImage cardsSection = img.getSubimage(cardsSectionX, cardsSectionY, cardsSectionWidth, cardsSectionHeight);
        int sectionWidth = cardsSection.getWidth();
        int cardWidth = (int) (sectionWidth / 5.333);

        double[] cardsXPositions = {1, sectionWidth / 4.85, sectionWidth / 2.45, sectionWidth / 1.63, sectionWidth / 1.228};
        int availableCardsCount = 5;

        for (int j = 0; j < availableCardsCount; j++) {
            BufferedImage cardImage = cardsSection.getSubimage((int) cardsXPositions[j], 0, cardWidth, cardsSectionHeight);
            cards.add(cardImage);
        }

        return cards;
    }

    private static BufferedImage getRankImageFromCardImage(BufferedImage img) {
        int rankX = (img.getWidth() / 14);
        int rankY = (img.getHeight() / 13);
        int rankWidth = (int) (img.getWidth() / 2.1);
        int rankHeight = (int) (img.getHeight() / 3.4);
        return img.getSubimage(rankX, rankY, rankWidth, rankHeight);
    }

    private static BufferedImage getSuitImageFromCardImage(BufferedImage img) {
        int suitX = (int) (img.getWidth() / 2.5);
        int suitY = (int) (img.getHeight() / 1.85);
        int suitWidth = (int) (img.getWidth() / 1.9);
        int suitHeight = (int) (img.getHeight() / 2.4);
        return img.getSubimage(suitX, suitY, suitWidth, suitHeight);
    }

    @Override
    public String toString() {
        return rank.getSymbol() + suit.getSymbol();
    }
}