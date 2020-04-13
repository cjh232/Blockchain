package blockchain;

public class Block {

    private int minerID;

    private int id;
    private int nonce;
    private long timestamp;

    private String previousHash;
    private String currentHash;

    private long timeAdded;

    public Block(int minerID, int id, int nonce, long timestamp, long timeAdded, String previousHash, String currentHash) {
        this.minerID = minerID;
        this.id = id;
        this.nonce = nonce;
        this.timestamp = timestamp;
        this.timeAdded = timeAdded;
        this.previousHash = previousHash;
        this.currentHash = currentHash;
    }

    public int getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getCurrentHash() {
        return currentHash;
    }

    public long getComputeTime() {
        return (timeAdded - timestamp) / 1000;
    }

    @Override
    public String toString() {
        return String.format("Block:\n" +
                "Created by miner # %d\n" +
                "Id: %d\n" +
                "Timestamp: %d\n" +
                "Magic number: %d\n" +
                "Hash of the previous block:\n" +
                "%s\n" +
                "Hash of the block:\n" +
                "%s\n" +
                "Block was generating for %d seconds\n", minerID, id, timestamp, nonce, previousHash, currentHash, getComputeTime());
    }
}
