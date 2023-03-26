import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void writeToFile(String textToWrite, String filename) {
        try {
            FileWriter fileWriter = new FileWriter(filename, true);
            fileWriter.write(textToWrite + "\n");
            fileWriter.close();
            //System.out.println("Successfully wrote to file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArrayList<GameMove> gameDataList = new ArrayList<GameMove>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("game_data_2.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] gameData = line.split(",");
                if (gameData.length < 5 || gameData[0].isEmpty() || gameData[0].equals(" ")) {
                    //System.out.println(gameData[0]);
                    continue;
                }
                int moveNumber = Integer.parseInt(gameData[0]);
                String sessionId = gameData[1];
                int playerID = Integer.parseInt(gameData[2]);
                String action = gameData[3];
                String houseCards = gameData[4];
                String playerCards = gameData[5];
                GameMove move = new GameMove(moveNumber, sessionId, playerID, action, houseCards, playerCards);
                gameDataList.add(move);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashSet<String> allDifferentSessionIds = new HashSet<>();

        // Print the game data list to verify the contents
        for (GameMove gameData : gameDataList) {
            //System.out.println(gameData.toString());
            allDifferentSessionIds.add(gameData.getSessionId());
        }

        ArrayList<String> differentSessionIds = new ArrayList<>(allDifferentSessionIds);


        DeckOfCards dc = new DeckOfCards();
        for(String id : differentSessionIds){
            Analytics an = new Analytics(id, gameDataList);
            //an.printArrayList();
            if(an.checkActionsSequence()){
                //an.getDeckOfCards().printDeckOfCards();
                if(an.testingJoinedAction()){
                    for(int i = 1; i<an.getListToControl().size(); i++ ){
                        if(!an.testingNextActions(i)){
                            break;
                        }
                    }
                } else {
                    an.firstElementInListErrorToFile();
                }
            } else {
                an.whereIsMultipleOccurence();

            }


        }

    }
}