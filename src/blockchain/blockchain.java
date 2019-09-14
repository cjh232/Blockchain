package blockchain;

import java.util.ArrayList;
import java.util.Date;

public class blockchain {
    ArrayList<block> chain;
    stringUtil hashGen;
    int length;

    public blockchain(){
        this.chain = new ArrayList<block>();
        this.hashGen = new stringUtil();
        this.length = 0;
    }

    public void addBlock(){
        long timeStamp = new Date().getTime();
        String hashOfPreviousBlock;
        int id;

        if(chain.isEmpty()){
            hashOfPreviousBlock = "0";
            id = 0;
        }else{
            hashOfPreviousBlock = chain.get(length - 1).getCurrentHash();
            id = chain.get(length - 1).getId() + 1;
        }

        String blockContents = "" + timeStamp + hashOfPreviousBlock + id;
        String hashOfCurrentBlock = generateHash(blockContents);
        block newBlock = new block(id, timeStamp, hashOfPreviousBlock, hashOfCurrentBlock);
        chain.add(newBlock);
        length+=1;
    }

    /* generateHash will handle proof of work and return the proven hash */

    private String generateHash(String blockContents){
        String hashOfCurrentBlock = hashGen.applySha256(blockContents);
        return hashOfCurrentBlock;
    }

    public void printChain(){
        for(block currentBlock: chain){
            currentBlock.printBlock();
            System.out.println("");
        }
    }



}
