package blockchain;

public class Block {

    private int minerID;
    private int id;
    private long timestamp;
    private String previousHash;
    private String currentHash;
    private int nonce;

    public Block(int minerID, int id, long timestamp, String previousHash, String currentHash, int nonce) {
        this.minerID = minerID;
        this.id = id;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.currentHash = currentHash;
        this.nonce = nonce;
    }

    public String getCurrentHash() {
        return currentHash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getId() {
        return id;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    @Override
    public String toString() {
        return String.format("Block:\n" +
                "Created by miner # %d\n" +
                "Id: %d\n" +
                "Timestamp: %d\n" +
                "Magic number: %d\n" +
                "Hash of the previous block:\n%s\n" +
                "Hash of the block:\n%s\n"
                , minerID, id, timestamp, nonce, previousHash, currentHash);
    }

}
