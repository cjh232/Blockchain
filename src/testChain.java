import blockchain.*;

public class testChain {
    public static void main(String[] args) {
        blockchain chain = new blockchain();

        for(int i = 0; i < 5; i++){
            chain.addBlock();
        }

        chain.printChain();
    }
}
