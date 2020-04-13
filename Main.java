package blockchain;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Blockchain chain = Blockchain.getInstance();

        ArrayList<Miner> minerList = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            Miner miner = new Miner(i + 1);
            minerList.add(miner);
            miner.start();
        }

        for(Miner miner: minerList) {
            try {
                miner.join();
            }catch (Exception ex) {
                System.out.println(String.format("Miner %d had an error joining", miner.getMinerID()));
            }
        }


        if(chain.validateChain()) {
            // chain.print();
        }
    }
}
