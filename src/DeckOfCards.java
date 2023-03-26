import java.util.ArrayList;

public class DeckOfCards {
    private String[] suits = {"S", "H", "D", "C"};
    private String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    private ArrayList<String> allCardsInDeck;

    public DeckOfCards() {
        allCardsInDeck = new ArrayList<>();
        for(String s : suits){
            for(String r : ranks){
                allCardsInDeck.add(r+s);
            }
        }
    }

    public String[] getSuits() {
        return suits;
    }

    public String[] getRanks() {
        return ranks;
    }

    public ArrayList<String> dealNewCards(){
        for(String s : suits){
            for(String r : ranks){
                allCardsInDeck.add(r+s);
            }
        }
        return allCardsInDeck;
    }

    public ArrayList<String> removeCardFromDeck(String card){
        String cardValue = card.toUpperCase();
        if (allCardsInDeck.contains(cardValue)) {
            //System.out.println(cardValue + " is in the list and removed from deck");
            allCardsInDeck.remove(cardValue);
        } else {
        }

        return allCardsInDeck;
    }

    public boolean isCardInDeck(String card){
        String cardValue = card.toUpperCase();
        if (allCardsInDeck.contains(cardValue)) {
            return true;
        } else {
            return false;
        }

    }

    public void printDeckOfCards(){
        for(String card : allCardsInDeck){
            System.out.print(card+", ");
        }
        System.out.println();
    }

}
