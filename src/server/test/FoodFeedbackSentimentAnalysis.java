package server.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import server.FoodFeedbackSentimentAnalysis;


public class FoodFeedbackSentimentAnalysisTest<sentimentAnalyzer> {

    private FoodFeedbackSentimentAnalysis sentimentAnalyzer;

    @Before
    public void setUp() {
        sentimentAnalyzer = new FoodFeedbackSentimentAnalysis("sentiment_words.txt");
    }

    @Test
    public void testPositiveFeedback() {
        String comment = "The food was delicious and the service was excellent!";
        String sentimentResult = sentimentAnalyzer.analyzeSentiment(comment);
        assertEquals("Positive", sentimentResult);
    }

    @Test
    public void testNegativeFeedback() {
        String comment = "The food was terrible and the waiter was rude.";
        String sentimentResult = sentimentAnalyzer.analyzeSentiment(comment);
        assertEquals("Negative", sentimentResult);
    }

    @Test
    public void testNeutralFeedback() {
        String comment = "The food was okay, but the service was slow.";
        String sentimentResult = sentimentAnalyzer.analyzeSentiment(comment);
        assertEquals("Neutral", sentimentResult);
    }

    @Test
    public void testNegation() {
        String comment = "I didn't like the food, but I did enjoy the ambiance.";
        String sentimentResult = sentimentAnalyzer.analyzeSentiment(comment);
        assertEquals("Neutral", sentimentResult);
    }

    @Test
    public void testIntensifier() {
        String comment = "The food was absolutely delicious!";
        String sentimentResult = sentimentAnalyzer.analyzeSentiment(comment);
        assertEquals("Positive", sentimentResult);
    }

    @Test
    public void testMultiWordExpressions() {
        String comment = "I couldn't believe how tasty the dish was!";
        String sentimentResult = sentimentAnalyzer.analyzeSentiment(comment);
        assertEquals("Positive", sentimentResult);
    }

    @Test
    public void testMixedSentiments() {
        String comment = "The food was good, but the service was terrible.";
        String sentimentResult = sentimentAnalyzer.analyzeSentiment(comment);
        assertEquals("Negative", sentimentResult);
    }
}