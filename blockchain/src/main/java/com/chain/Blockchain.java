package com.chain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//import java.security.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.google.gson.*;
/**
 *
 * @author Bhavish
 */
class BlockChain{
    public static int difficulty = 4;
    public static ArrayList<Block> blockchain = new ArrayList<Block>();

    public static Boolean verifyTransaction(){
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for(int i=1; i<blockchain.size(); i++){
            previousBlock = blockchain.get(i - 1);
            currentBlock = blockchain.get(i);

            if(!previousBlock.hash.equals(currentBlock.previousHash)){
                System.out.println("Previous Hashes Not Equal");
                return false;
            }

            if(!currentBlock.hash.equals(currentBlock.calculateHash())){
                System.out.println("Current Hashes Not Equal");
                return false;
            }

            if(!currentBlock.hash.substring(0, difficulty).equals(hashTarget)){
                System.out.println("The Block hasn't been mined");
                return false;
            }
        }
        return true;
    }

    /*add create block function*/

    public static void initiateBlockChain(){
        blockchain.add(new Block("Dummy User", "0"));/*change variable acc to block class*/
        System.out.println("Hash for block 1... ");
        blockchain.get(0).mineBlock(difficulty);

        //System.out.println("\nBlockChain is Valid: " + verifyTransaction());
        /*viewLedger();*/
    }

    public static void createBlock(String snum){
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        Block currBlock = new Block(snum,
                blockchain.get(blockchain.size() - 1).hash);
        System.out.println("***Mining Block***");
        currBlock.mineBlock(difficulty);
        blockchain.add(currBlock);
    }

    public static ArrayList<Block> getUserData(String vendorUsername) {
        ArrayList<Block> userData = new ArrayList<Block>();
        /*vendorUsername acc to initiateblockchain i.e. based on class block */
        for(int i = 0; i<blockchain.size(); i++) {
            //if(blockchain.get(i).vendorUsername.equals(vendorUsername)) {
            userData.add(blockchain.get(i));
            //}
        }
        return userData;
    }
}
