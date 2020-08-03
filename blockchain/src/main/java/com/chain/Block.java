package com.chain;

import java.util.Date;
//package blockChain;

class Data {

    String drugName;

    public Data(String drugName)
    {
        this.drugName = drugName;

    }
}

/**
 *
 * @author MY PC
 */
public class Block extends Data{

    /**
     *
     */
    public String hash;

    /**
     *
     */
    public String previousHash;
    private int nonce;
    private long timeStamp;

    /**
     *
     * @param drugName
     * @param previousHash
     */
    public Block(String drugName, String previousHash)
    {
        super(drugName);
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    /**
     *
     * @return
     */
    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce)+
                        drugName

        );
        return calculatedhash;
    }

    /**
     *
     * @param difficulty
     */
    public void mineBlock(int difficulty){
        String target = new String(new char[difficulty]).replace('\0', '0');
        while(!hash.substring(0, difficulty).equals(target)){
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined! : " + hash);
    }
}