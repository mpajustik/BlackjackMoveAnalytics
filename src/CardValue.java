import java.util.HashMap;

public class CardValue {
    static HashMap<String, Integer> cardValues = new java.util.HashMap<String, Integer>();

    static {
        cardValues.put("A", 11);
        cardValues.put("2", 2);
        cardValues.put("3", 3);
        cardValues.put("4", 4);
        cardValues.put("5", 5);
        cardValues.put("6", 6);
        cardValues.put("7", 7);
        cardValues.put("8", 8);
        cardValues.put("9", 9);
        cardValues.put("10", 10);
        cardValues.put("J", 10);
        cardValues.put("Q", 10);
        cardValues.put("K", 10);

    }

    public static Integer getCardValue(String key) {
        return cardValues.get(key);
    }
}
