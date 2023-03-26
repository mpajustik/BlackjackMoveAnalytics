import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Analytics  {
    private ArrayList<GameMove> listToControl;
    private String currentSessionId;
    //private GameMove currentAction;
    private DeckOfCards deckOfCards = new DeckOfCards();
    String[] currentActionArray;

    static HashMap<String, Integer> cardValues = new HashMap<String, Integer>();

    public Analytics(String currentSessionId, ArrayList<GameMove> moves) {
        listToControl = new ArrayList<>();
        this.currentSessionId = currentSessionId;
        for(GameMove move : moves){
            if(move.getSessionId().equals(currentSessionId)){
                listToControl.add(move);
            }
        }

        Collections.sort(listToControl, new Comparator<GameMove>() {
            public int compare(GameMove g1, GameMove g2) {
                return g1.getMoveNumber() - g2.getMoveNumber();
            }
        });
    }

    public DeckOfCards getDeckOfCards() {
        return deckOfCards;
    }

    public void setDeckOfCards(DeckOfCards deckOfCards) {
        this.deckOfCards = deckOfCards;
    }

    public ArrayList<GameMove> getListToControl() {
        return listToControl;
    }

    public void setListToControl(ArrayList<GameMove> listToControl) {
        this.listToControl = listToControl;
    }

    public String getCurrentSessionId() {
        return currentSessionId;
    }

    public void setCurrentSessionId(String currentSessionId) {
        this.currentSessionId = currentSessionId;
    }

    public String[] playerDifferentCardsArray(int num){
        String playerCards = listToControl.get(num).getPlayerCards();
        String[] playerAllCards= playerCards.split("-");
        return playerAllCards;

    }

    public ArrayList<Integer> whereIsMultipleOccurence(){
        int num = 0;
        int multipleNumber = 0;
        ArrayList<Integer> multipleNumbersList = new ArrayList<>();
        for(GameMove move: listToControl){
            if(move.getMoveNumber() != num){
                num = move.getMoveNumber();
            } else {
                multipleNumber = move.getMoveNumber();
                multipleNumbersList.add(multipleNumber);
                Main.writeToFile(move.getMoveNumber()+", "+move.getSessionId()+", "+move.getPlayerID()+", "+move.getAction()+", "+move.getHouseCards()+", "+move.getPlayerCards()+" Multiple occurence", "output.txt");
            }
        }
        return multipleNumbersList;
    }

    public String[] houseDifferentCardsArray( int num){
        String houseCards = listToControl.get(num).getHouseCards();
        String[]houseAllCards= houseCards.split("-");
        return houseAllCards;

    }

    public String[] currentActionInArray(int num){
        String currentAction = listToControl.get(num).getAction();
        currentActionArray = currentAction.split(" ");
        return currentActionArray;

    }

    public void firstElementInListErrorToFile(){
        Main.writeToFile(listToControl.get(0).getMoveNumber()+", "+listToControl.get(0).getSessionId()+", "+listToControl.get(0).getPlayerID()+", "+listToControl.get(0).getAction()+", "+listToControl.get(0).getHouseCards()+", "+listToControl.get(0).getPlayerCards(), "output.txt");

    }

    public void numElementInListErrorToFile(int num){
        Main.writeToFile(listToControl.get(num).getMoveNumber()+", "+listToControl.get(num).getSessionId()+", "+listToControl.get(num).getPlayerID()+", "+listToControl.get(num).getAction()+", "+listToControl.get(num).getHouseCards()+", "+listToControl.get(num).getPlayerCards(), "output.txt");

    }

    public void removeAllHandCardsFromDeck(String[] cardsArray, int num){
        for(String card : cardsArray){
            deckOfCards.removeCardFromDeck(card);
        }

        deckOfCards.printDeckOfCards();
    }

    public int calculateHandSum(String[] playerCardsInHand){
        int value = 0;
        int numAces = 0;

        for (int i = 0; i < playerCardsInHand.length; i++) {
            value += CardValue.getCardValue(playerCardsInHand[i].substring(0,1).toUpperCase());

            if (CardValue.getCardValue(playerCardsInHand[i].substring(0,1).toUpperCase()) == 11) {
                numAces++;
            }
        }

        while (value > 21 && numAces > 0) {
            value -= 10;
            numAces--;
        }

        return value;
    }


    public boolean testingJoinedAction(){
        int actionNum = 0;
        String[] joinAction =  currentActionInArray(actionNum);
        String[] houseCards = houseDifferentCardsArray(actionNum);
        String[] playerCards = playerDifferentCardsArray(actionNum);
        if(!houseCards[1].equals("?")){
            return false;
        } else if(houseCards[0].equals(playerCards[0]) || houseCards[0].equals(playerCards[1]) || playerCards[0].equals(playerCards[1])){
            return false;
        }if(joinAction[1].equals("Joined") &&  deckOfCards.isCardInDeck(houseCards[0]) && deckOfCards.isCardInDeck(playerCards[0]) &&  deckOfCards.isCardInDeck(playerCards[1])) {
            deckOfCards.removeCardFromDeck(houseCards[0]);
            deckOfCards.removeCardFromDeck(playerCards[0]);
            deckOfCards.removeCardFromDeck(playerCards[1]);
            return true;
        }
        return false;

    }

    public boolean testingNextActions(int actionNum){
        int playerHandSum = 0;
        int houseHandSum = 0;
        String[] joinAction =  currentActionInArray(actionNum);
        if(joinAction[0].equals("P")){
            int playerCardNum = playerDifferentCardsArray(actionNum).length-1;
            switch (joinAction[1]){
                case "Hit":
                    playerHandSum = calculateHandSum(playerDifferentCardsArray(actionNum));
                    if(playerHandSum > 21){
                        numElementInListErrorToFile(actionNum);
                        return false;
                    }
                    if(playerDifferentCardsArray(actionNum).length != playerDifferentCardsArray(actionNum-1).length ){
                        if(deckOfCards.isCardInDeck(playerDifferentCardsArray(actionNum)[playerCardNum])){
                            deckOfCards.removeCardFromDeck(playerDifferentCardsArray(actionNum)[playerCardNum]);
                            return true;
                        } else {
                            numElementInListErrorToFile(actionNum);
                            return false;
                        }
                    }
                    break;
                case "Stand":
                    playerHandSum = calculateHandSum(playerDifferentCardsArray(actionNum));
                    if(playerHandSum > 21){
                        numElementInListErrorToFile(actionNum);
                        return false;
                    }

                    break;
                case "Win":
                    playerHandSum = calculateHandSum(playerDifferentCardsArray(actionNum));
                    houseHandSum = calculateHandSum(houseDifferentCardsArray(actionNum));
                    if( playerHandSum < houseHandSum && playerHandSum < 21 ){
                        numElementInListErrorToFile(actionNum);
                        return false;
                    } else if( playerHandSum > 21){
                        numElementInListErrorToFile(actionNum);
                        return false;
                    }

                    break;
                case "Lose":
                    playerHandSum = calculateHandSum(playerDifferentCardsArray(actionNum));
                    houseHandSum = calculateHandSum(houseDifferentCardsArray(actionNum));
                    if(playerHandSum == 21 ){
                        numElementInListErrorToFile(actionNum);
                        return false;
                    } else if ( playerHandSum > houseHandSum  && playerHandSum < 21 ){
                        numElementInListErrorToFile(actionNum);
                        return false;
                    } else if (playerHandSum < houseHandSum  && houseHandSum > 21){
                        numElementInListErrorToFile(actionNum);
                        return false;
                    }


                    break;
                case "Left":

                    break;

            }
            return true;
        }else{
            switch (joinAction[1]){
                case "Show":
                    playerHandSum = calculateHandSum(playerDifferentCardsArray(actionNum));
                    if(playerHandSum > 21){
                        numElementInListErrorToFile(actionNum);
                        return false;
                    }
                    break;
                case "Hit":
                    houseHandSum = calculateHandSum(houseDifferentCardsArray(actionNum));
                    if(houseHandSum == 21){
                        numElementInListErrorToFile(actionNum);
                        return false;
                    }

                    break;
                case "Redeal":
                    deckOfCards.dealNewCards();
                    joinAction =  currentActionInArray(actionNum);
                    String[] houseCards = houseDifferentCardsArray(actionNum);
                    String[] playerCards = playerDifferentCardsArray(actionNum);
                    if(joinAction[1].equals("Redeal") &&  deckOfCards.isCardInDeck(houseCards[0]) && deckOfCards.isCardInDeck(playerCards[0]) &&  deckOfCards.isCardInDeck(playerCards[1])) {
                        deckOfCards.removeCardFromDeck(houseCards[0]);
                        deckOfCards.removeCardFromDeck(playerCards[0]);
                        deckOfCards.removeCardFromDeck(playerCards[1]);
                        return true;
                    } else {
                        numElementInListErrorToFile(actionNum);
                        return false;
                    }
            }
            return true;
        }
    }


    public void printArrayList(){
        for(GameMove m : listToControl){
            System.out.print(m.getMoveNumber()+", ");
            System.out.print(m.getSessionId()+", ");
            System.out.print(m.getPlayerID()+", ");
            System.out.print(m.getAction()+", ");
            System.out.print(m.getHouseCards()+", ");
            System.out.println(m.getPlayerCards());
        }
    }

    public boolean checkActionsSequence(){
        HashMap<Integer, Integer> map = new HashMap<>();
        for (GameMove move : listToControl) {
            Integer moveActionNumber = move.getMoveNumber();
            if (map.containsKey(moveActionNumber)) {
                map.put(moveActionNumber, map.get(moveActionNumber) + 1);
            } else {
                map.put(moveActionNumber, 1);
            }
        }
        boolean hasDuplicates = false;

        for (Integer count : map.values()) {
            if (count > 1) {
                hasDuplicates = true;
                break;
            }
        }

        return !hasDuplicates;
    }




}

