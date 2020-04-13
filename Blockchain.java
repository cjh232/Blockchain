package blockchain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Blockchain {

    ArrayList<Block> blocks;
    volatile Block  lastBlock;
    private static Blockchain instance;

    private volatile long timeLastAdded;
    private volatile int DIFFICULTY;

    private Blockchain() {
        blocks = new ArrayList<>();
        timeLastAdded = new Date().getTime();
        this.DIFFICULTY = 0;
    }

    public static Blockchain getInstance() {
        if(instance == null) {
            instance = new Blockchain();
        }

        return instance;
    }

    public Block getLastBlock() {
        return lastBlock;
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

    public int getDifficulty() {
        return this.DIFFICULTY;
    }

    public void print() {
        for(Block b: blocks) {
            System.out.println(b.toString());
        }
    }

    public synchronized boolean validateNewBlock(Block newBlock) {
        String challenge = "0".repeat(DIFFICULTY);
        String subString = newBlock.getCurrentHash().substring(0, DIFFICULTY);
        int lastID = lastBlock != null ? lastBlock.getId() : 0;
        String lastHash = lastBlock != null ? lastBlock.getCurrentHash() : "0";

        if(challenge.equals(subString)) {
            if(newBlock.getId() == lastID + 1) {
                if(newBlock.getPreviousHash().equals(lastHash)) {
                    return true;
                }
            }
        }

        return false;
    }

    public synchronized boolean  offerBlock(Block newBlock, int minerID) {
        // System.out.println("Miner " + minerID + " is offering a block");

        if(!validateNewBlock(newBlock)) {
            // System.out.println("failed...");
            return false;
        }


        blocks.add(newBlock);
        System.out.print(newBlock.toString());

        adjustDifficulty(new Date().getTime());
        lastBlock = newBlock;

        return true;
    }

    public synchronized void adjustDifficulty(long now) {

        long timeSinceLastAdded = (now - timeLastAdded) / 1000;

        if(timeSinceLastAdded < 15) {
            DIFFICULTY++;
            System.out.println("N was increased to " + DIFFICULTY + "\n");
        } else if(timeSinceLastAdded <= 60) {
            System.out.println("N stays the same\n");
        } else {
            DIFFICULTY--;
            System.out.println("N was decreased by 1\n");
        }

        timeLastAdded = now;
    }
}
