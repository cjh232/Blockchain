package blockchain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Blockchain {

    ArrayList<Block> blocks;
    Block lastBlock;
    private static Blockchain instance;
    private int DIFFICULTY;

    private Blockchain() {
        blocks = new ArrayList<>();
        Scanner scannner = new Scanner(System.in);
        System.out.print("Enter how many zeros the hash must start with: ");
        this.DIFFICULTY = scannner.nextInt();
    }

    public static Blockchain getInstance() {
        if(instance == null) {
            instance = new Blockchain();
        }

        return instance;
    }

    private String generateHash(int id, int nonce, long timestamp, String previousHash) {
        String blockContents = id + nonce + timestamp + previousHash;
        return StringUtil.applySha256(blockContents);
    }

    public void generateNextBlock() {
        Random ran = new Random();

        int id = lastBlock != null ? lastBlock.getId() + 1 : 1;
        long timestamp = new Date().getTime();
        int nonce = ran.nextInt() & Integer.MAX_VALUE;

        String previousHash = lastBlock != null ? lastBlock.getCurrentHash() : "0";
        String currentHash = generateHash(id, nonce, timestamp, previousHash);

        String challenge = "0".repeat(DIFFICULTY);
        String subString = currentHash.substring(0, DIFFICULTY);

        while(!subString.equals(challenge)) {
            nonce = ran.nextInt() & Integer.MAX_VALUE;
            currentHash = generateHash(id, nonce, timestamp, previousHash);
            subString = currentHash.substring(0, DIFFICULTY);
        }

        Block newBlock = new Block(id, nonce, timestamp, new Date().getTime(), previousHash, currentHash);
        lastBlock = newBlock;

        blocks.add(newBlock);


    }

    public boolean validateChain() {
        int idx = 0;

        while(idx + 1 < blocks.size()) {
            Block curr = blocks.get(idx);
            Block next = blocks.get(idx + 1);

            if(!curr.getCurrentHash().equals(next.getPreviousHash())) {
                return false;
            }

            idx++;
        }

        return true;
    }

    public void print() {
        for(Block b: blocks) {
            System.out.println(b.toString());
        }
    }

}
