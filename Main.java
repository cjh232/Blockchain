package blockchain;

public class Main {
    public static void main(String[] args) {
        Blockchain chain = Blockchain.getInstance();

        for(int i = 0; i < 5; i++) {
            chain.generateNextBlock();
        }

        if(chain.validateChain()) {
            chain.print();
        }
    }
}
