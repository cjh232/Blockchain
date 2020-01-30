package blockchain;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Blockchain {

    public static Blockchain instance = null;
    private List<Block> blockchain;
    private volatile Block lastBlock;
    private static final int UPPER_TIME_BOUND = 60;
    private static final int LOWER_TIME_BOUND = 10;
    private volatile int difficulty;
    private volatile long timeLastAdded;

    private Blockchain() {
        this.blockchain = new ArrayList<Block>();
        this.difficulty = 0;
        Block genesisBlock = new Block(0,0, new Date().getTime(), "0", "0", 0);
        blockchain.add(genesisBlock);
        timeLastAdded = genesisBlock.getTimestamp();
    }

    public static Blockchain getInstance() {
        if(instance == null) {
            instance = new Blockchain();
        }

        return instance;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public Block getTopBlock() {
        return blockchain.get(blockchain.size() - 1);
    }

    public void printChain() {
        for(Block block: blockchain) {
            if(block.getId() == 0) continue;
            System.out.println(block.toString());
        }
    }

    public synchronized String addBlock(Block block) {
        String status = validateBlock(block);

        if(status.equals("100")) {
            // System.out.println("Validated!");
            blockchain.add(block);
            long timeToGenerate = (new Date().getTime() - timeLastAdded) /  1000;
            timeLastAdded = new Date().getTime();

            System.out.print("\n" + block.toString());
            System.out.printf("Block was generating for %d seconds\n", timeToGenerate);

            adjustDifficulty(timeToGenerate);

        }

        return status;

    }

    public synchronized String validateBlock(Block block) {
        Block lastBlock = getTopBlock();

        String challengeString = "0".repeat(difficulty);
        boolean validated = block.getCurrentHash().substring(0,difficulty).equals(challengeString);
        boolean isChained = lastBlock.getCurrentHash().equals(block.getPreviousHash());

        if(validated && isChained) {
            // Correct hash and the spot is correct
            return "100";
        }

        if(!validated && isChained) {
            // Wrong hash but the spot is correct. Miner can try again.
            return "101";
        }

        // Wrong hash and it is not in the right spot
        if(!validated && !isChained) {
            return "102";
        }

        // Correct hash, but wrong spot
        return "103";



    }

    public synchronized void adjustDifficulty(long timeToGenerate) {

        if(timeToGenerate < LOWER_TIME_BOUND) {
            System.out.printf("N was increased to %d\n", ++difficulty);
        } else if(timeToGenerate > UPPER_TIME_BOUND) {
            System.out.printf("N was decreased by 1\n");
            difficulty--;
        } else {
            System.out.print("N stays the same\n");
        }
    }

    public int size() {
        return blockchain.size();
    }
}
