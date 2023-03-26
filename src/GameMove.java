public class GameMove {
    private int moveNumber;
    private String sessionId;
    private int playerID;
    private String action;
    private String houseCards;
    private String playerCards;

    public GameMove(int moveNumber, String sessionId, int playerID, String action, String houseCards, String playerCards) {
        this.moveNumber = moveNumber;
        this.sessionId = sessionId;
        this.playerID = playerID;
        this.action = action;
        this.houseCards = houseCards;
        this.playerCards = playerCards;
    }

    // Getters and setters for the private fields

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getHouseCards() {
        return houseCards;
    }

    public void setHouseCards(String houseCards) {
        this.houseCards = houseCards;
    }

    public String getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(String playerCards) {
        this.playerCards = playerCards;
    }

    @Override
    public String toString() {
        return "GameMove{" +
                "moveNumber=" + moveNumber +
                ", sessionId='" + sessionId + '\'' +
                ", playerID=" + playerID +
                ", action='" + action + '\'' +
                ", houseCards='" + houseCards+ '\'' +
                ", playerCards='" + playerCards+ '\'' +
                '}';
    }
}
