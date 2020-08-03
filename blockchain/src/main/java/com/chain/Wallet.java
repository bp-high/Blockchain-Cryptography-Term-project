package com.chain;



import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
//import com.google.gson.GsonBuilder;
import com.google.gson.*;
import java.io.*;
import java.io.File;

class Wallet implements ActionListener{
    JFrame wallet = new JFrame();
    JLabel labelInsert = new JLabel("Enter Drug Name");
    JTextField tfInsert = new JTextField();
    JLabel labelToken = new JLabel("Enter Serial No.");
    JTextField tfToken = new JTextField();

    JButton buttonAddTrans = new JButton("Add New Drug");
    JButton buttonViewUserTrans = new JButton("View");
    JButton buttonMenu = new JButton("Logout");

    public Wallet(){
        generateAWTWindow();
    }

    public void generateAWTWindow(){
        labelInsert.setBounds(50,10,250,20);
        tfInsert.setBounds(50,30,250,20);
        labelToken.setBounds(50,50,250,20);
        tfToken.setBounds(50,70,250,20);

        buttonAddTrans.setBounds(50,100,250,25);
        buttonViewUserTrans.setBounds(50,180,250,25);
        buttonMenu.setBounds(50,250,250,25);

        wallet.add(labelInsert); wallet.add(tfInsert);
        wallet.add(labelToken); wallet.add(tfToken);
        wallet.add(buttonAddTrans);
        wallet.add(buttonViewUserTrans);
        wallet.add(buttonMenu);

        buttonAddTrans.addActionListener(this);
        buttonViewUserTrans.addActionListener(this);
        buttonMenu.addActionListener(this);

        wallet.setLayout(null);
        wallet.setVisible(true);
        wallet.setSize(400,400);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == buttonAddTrans){
            String diagnosis = tfInsert.getText();
            int token = Integer.parseInt(tfToken.getText());
            if(verifyDrugs(Login.username, token) != true) {
                System.out.println("Illegal Access Token. Unable to verify Signature.\n");
                wallet.setVisible(false);
                new Wallet();
            }
            else {
                BlockChain.createBlock(diagnosis);
                wallet.setVisible(false);
                new Wallet();
            }
        }
        else if(e.getSource() == buttonViewUserTrans){
            viewUserTransactions();
        }
        else if(e.getSource() == buttonMenu){
            wallet.setVisible(false);
            new Login();
        }
    }
    public static Boolean ZKP(int token, int B){
//		System.out.println("token:" + (token) + " B:" + (B));

        int r = (int) ((Math.random() * (10 - 0)) + 0);
        int h = (int) ((Math.pow(2,r))%11);

        int bit;
        if(Math.random() < 0.001) { bit = 0; }
        else { bit = 1; }

        int s = (r + bit*token)%(11 - 1);
        int lhs = (int) Math.pow(2, s) % (11);
        int rhs =  (h * ((int) Math.pow(B, bit)) % (11));
        //System.out.println("\nlhs: " + lhs + " rhs:" + rhs);
        if(lhs != rhs) {
            return false;
        }else {
            return true;
        }
    }
    public static void viewUserTransactions(){
        System.out.println("\n\n****VENDOR DRUG TRANSACTION****");
        System.out.println("Current User:- " + Login.username);

        ArrayList<Block> userData = BlockChain.getUserData(Login.username);
        String userDataJson = new GsonBuilder().setPrettyPrinting().create().toJson(userData);
        System.out.println(userDataJson);
    }

    public static boolean verifyDrugs(String a,int b){
        try{
            //String hashA = StringUtil.applySha256(a);
            //String hashB = StringUtil.applySha256(b);
            int B;

            File f = new File("src/main/resources/Drugs.txt");// where the vendor database will go;
            BufferedReader buffer = new BufferedReader(new FileReader(f));
            String readLine = "";

            while ( (readLine = buffer.readLine()) != null){
                String[] line = readLine.split(",");

                if(line[0].equals(a)){
                    B = Integer.parseInt(line[1]);
                    System.out.println(B);
                    if(ZKP(B,b) == true)
                    {
                        buffer.close();
                        return true;
                    }
                }
            }
            buffer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
