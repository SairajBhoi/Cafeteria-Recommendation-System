package server.resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.LinkedHashMap;

public class FoodFeedbackSentimentAnalysis {

    private static final String SENTIMENT_KEYWORDS_FILE = "D:\\Learn_and_Code\\final-assignment-june\\Cafeteria-Recommendation-System\\src\\server\\resources\\SentimentKeyWords.txt";

    private Map<String, Integer> sentimentWords;

    public FoodFeedbackSentimentAnalysis() {
        this.sentimentWords = new LinkedHashMap<>();
        loadWords();
    }

    private void loadWords() {
        try (BufferedReader br = new BufferedReader(new FileReader(SENTIMENT_KEYWORDS_FILE))) {
            String line;
            System.out.println("Loading sentiment words from file: " + SENTIMENT_KEYWORDS_FILE);
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().toLowerCase().split("\\s+");
                if (parts.length == 2) {
                    String word = parts[0];
                    int weight = Integer.parseInt(parts[1]);
                    sentimentWords.put(word, weight);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + SENTIMENT_KEYWORDS_FILE);
            e.printStackTrace();
        }
    }

    public String analyzeSentiment(String comment) {
        System.out.println("Analyzing sentiment for comment: " + comment);
        String[] words = tokenize(comment);

        int positiveScore = 0;
        int negativeScore = 0;
        boolean negateNext = false;
        boolean intensifyNext = false;

        for (String word : words) {
            System.out.println("Processing word: " + word);

            if (isNegation(word)) {
                System.out.println("Negation word found: " + word);
                negateNext = true;
                continue;
            }

            if (isIntensifier(word)) {
                System.out.println("Intensifier word found: " + word);
                intensifyNext = true;
                continue;
            }

            if (sentimentWords.containsKey(word)) {
                int weight = sentimentWords.get(word);

                if (negateNext) {
                    System.out.println("Negating weight for word: " + word);
                    weight = -weight;
                    negateNext = false;
                }

                if (intensifyNext) {
                    System.out.println("Intensifying weight for word: " + word);
                    weight *= 2; 
                    intensifyNext = false;
                }

                if (weight > 0) {
                    positiveScore += weight;
                } else {
                    negativeScore += weight;
                }
            }
        }

        int totalScore = positiveScore + negativeScore;
        String sentimentResult;

        if (totalScore > 0) {
            sentimentResult = "Positive";
        } else if (totalScore < 0) {
            sentimentResult = "Negative";
        } else {
            sentimentResult = "Neutral";
        }

        System.out.println("Sentiment analysis result for comment '" + comment + "': " + sentimentResult);
        return sentimentResult;
    }

    private boolean isNegation(String word) {
        return word.equals("not") || word.equals("no") || word.equals("didn't") || word.endsWith("n't");
    }

    private boolean isIntensifier(String word) {
        return word.equals("very") || word.equals("extremely") || word.equals("quite") || word.equals("really");
    }

    private String[] tokenize(String comment) {
        return comment.toLowerCase().split("[\\s,.!?]+");
    }
}
