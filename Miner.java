package blockchain;

import java.util.Date;
import java.util.Random;

public class Miner extends Thread {

    private int minerID;
    private Blockchain blockchain;

    public Miner(int minerID) {
        this.minerID = minerID;
        this.blockchain = Blockchain.getInstance();
    }

    @Override
    public void run() {

        for(int i = 0; i < 5; i++) {
            mine(i + 1);
        }
    }

    public void mine(int blockNumber) {
        Block lastBlock = blockchain.getLastBlock();
        int prevID = lastBlock != null ? lastBlock.getId() : 0;

        // The miner is attempting to add a block that has already been placed.
        if(blockNumber != prevID + 1) {
            return;
        }

        Random ran = new Random();

        int currentDifficulty = blockchain.getDifficulty();

        int id = lastBlock != null ? lastBlock.getId() + 1 : 1;
        long timestamp = new Date().getTime();
        int nonce = ran.nextInt() & Integer.MAX_VALUE;

        String previousHash = lastBlock != null ? lastBlock.getCurrentHash() : "0";
        String currentHash = generateHash(id, nonce, timestamp, previousHash);

        String challenge = "0".repeat(currentDifficulty);
        String subString = currentHash.substring(0, currentDifficulty);


        while(true) {

            if(subString.equals(challenge)) {
                Block newBlock = new Block(minerID, id, nonce, timestamp, new Date().getTime(), previousHash, currentHash);
                if(blockchain.offerBlock(newBlock, minerID)) {
                    return;
                }
            }

            lastBlock = blockchain.getLastBlock();
            prevID = lastBlock != null ? lastBlock.getId() : 0;

            if(id != prevID + 1) return;

            nonce = ran.nextInt() & Integer.MAX_VALUE;
            currentHash = generateHash(id, nonce, timestamp, previousHash);
            subString = currentHash.substring(0, currentDifficulty);

        }
    }

    public int getMinerID() {
        return minerID;
    }

    private String generateHash(int id, int nonce, long timestamp, String previousHash) {
        String blockContents = id + nonce + timestamp + previousHash;
        return StringUtil.applySha256(blockContents);
    }

}
