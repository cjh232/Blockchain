package blockchain;

public class block {
    private int id;
    private long timestamp;
    private String previousHash;
    private String currentHash;
    private int nonce;

    block(int id, long timestamp, String previousHash, String currentHash){
        this.id = id;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.currentHash = currentHash;
    }

    public int getId(){
        return this.id;
    }

    public long getTimestamp(){
        return this.timestamp;
    }

    public String getPreviousHash(){
        return this.previousHash;
    }

    public String getCurrentHash(){
        return this.currentHash;
    }

    public int getNonce(){
        return this.nonce;
    }

    public void setNonce(int nonce){
        this.nonce = nonce;
    }

    public void printBlock(){
        System.out.println("Block: ");
        System.out.println("Id: " + this.id);
        System.out.println("Timestamp: " + this.timestamp);
        System.out.println("Hash of the previouse block: \n" + this.previousHash);
        System.out.println("Hash of the block: \n" + this.currentHash);
    }
}
