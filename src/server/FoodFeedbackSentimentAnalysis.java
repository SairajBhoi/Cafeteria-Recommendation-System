package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FoodFeedbackSentimentAnalysis {

    private Map<String, Integer> sentimentWords;

    public FoodFeedbackSentimentAnalysis(String fileName) {
        this.sentimentWords = new HashMap<>();
        loadWords(fileName);
    }

    private void loadWords(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().toLowerCase().split("\\s+");
                if (parts.length == 2) {
                    String word = parts[0];
                    int weight = Integer.parseInt(parts[1]);
                    sentimentWords.put(word, weight);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + fileName);
            e.printStackTrace();
        }
    }

    public String analyzeSentiment(String comment) {
        // Tokenize and preprocess the comment
        String[] words = tokenize(comment);

        int positiveScore = 0;
        int negativeScore = 0;
        boolean negateNext = false;
        boolean intensifyNext = false;

        // Calculate sentiment score considering multi-word expressions, negations, and intensifiers
        for (String word : words) {
            if (isNegation(word)) {
                negateNext = true;
                continue;
            }

            if (isIntensifier(word)) {
                intensifyNext = true;
                continue;
            }

            if (sentimentWords.containsKey(word)) {
                int weight = sentimentWords.get(word);

                if (negateNext) {
                    weight = -weight;
                    negateNext = false;
                }

                if (intensifyNext) {
                    weight *= 2; // Double the weight for intensifiers
                    intensifyNext = false;
                }

                if (weight > 0) {
                    positiveScore += weight;
                } else {
                    negativeScore += weight;
                }
            }
        }

        // Determine sentiment category based on score
        int totalScore = positiveScore + negativeScore;
        if (totalScore > 0) {
            return "Positive";
        } else if (totalScore < 0) {
            return "Negative";
        } else {
            return "Neutral";
        }
    }

    private boolean isNegation(String word) { // List of negation words to check against
        return word.equals("not") || word.equals("no") || word.equals("didn't") || word.endsWith("n't");
    }

    private boolean isIntensifier(String word) {
        
        return word.equals("very") || word.equals("extremely") || word.equals("quite") || word.equals("really");
    }

    private String[] tokenize(String comment) {
        
        return comment.toLowerCase().split("[\\s,.!?]+");
    }

   
}
