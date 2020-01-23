package blockchain;

import java.util.Date;

public class Miner extends Thread{

    private int minerID;
    private Blockchain blockchain;

    public Miner(int id, Blockchain blockchain) {
        this.blockchain = blockchain;
        this.minerID = id;
    }

    @Override
    public void run() {
        while(blockchain.size() < 6) {
            mine(blockchain.getDifficulty());
        }
    }

    public void mine(int difficulty) {
        Block lastBlock = blockchain.getTopBlock();
        int id = blockchain.size();

        String previousHash = lastBlock.getCurrentHash();
        int nonce = -1;
        String currentHash;

        String challengeString = "0".repeat(difficulty);
        boolean hashFound;
        String status;
        long timeStamp = new Date().getTime();
        
        while(true) {
            currentHash = generateHash(id, timeStamp, previousHash, ++nonce);
            hashFound = currentHash.substring(0, difficulty).equals(challengeString);

            if(hashFound) {
                Block newBlock = new Block(minerID, id, timeStamp, previousHash, currentHash, nonce);
                status = blockchain.addBlock(newBlock);

                if(!status.equals("101")) {
                    return;
                }
            }
        }


    }

    private String generateHash(int id, long timestamp, String hashOfPreviousBlock, int nonce) {
        String blockContents = "" + id + timestamp + hashOfPreviousBlock + nonce;

        return StringUtil.applySha256(blockContents);
    }
}
