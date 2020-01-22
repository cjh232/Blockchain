package blockchain;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Blockchain bc =  Blockchain.getInstance();
        List<Miner> threadList = new ArrayList<>();

        for(int i = 0; i < 50; i++) {
            Miner miner = new Miner(i + 1, bc);
            threadList.add(miner);
            miner.start();
        }

        for(Miner miner: threadList) {
            try {
                miner.join();
            } catch (Exception e) {
                System.out.println("Error Occurred");
            }
        }

    }
}
