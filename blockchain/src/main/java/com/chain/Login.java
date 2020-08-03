package com.chain;

//package ; // package name here

import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.io.File;

class Login implements ActionListener{
    static String username;
    static int B;

    JFrame f=new JFrame();

    JLabel l1=new JLabel("Vendorname");
    JLabel l2=new JLabel("Password");

    JTextField t1 = new JTextField(); JTextField t2 = new JTextField();

    JButton b1=new JButton("Login");
    JButton b2=new JButton("Quit");


    Login()
    {


        l1.setBounds(50,110,300,20);
        t1.setBounds(50,135,300,20);

        l2.setBounds(50,160,300,20);
        t2.setBounds(50,185,300,20);

        b1.setBounds(50,220,300,20);
        b2.setBounds(50,300,300,20);


        f.add(l1);f.add(l2);f.add(t1);f.add(t2);
        f.add(b1); f.add(b2);
        b1.addActionListener(this); b2.addActionListener(this);
        f.setLayout(null);
        f.setVisible(true);
        f.setSize(400,400);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == b1 ){
            // Login button
            if( verifyUserCredentials(t1.getText(), t2.getText())){
                System.out.println("Current User:- " + username);
                new Wallet();
                System.out.println("\nBlockChain is Valid: " + BlockChain.verifyTransaction());
                f.setVisible(false);
            }
            else{
                new Login();
                f.dispose();
            }
        }

        else if( e.getSource() == b2){
            System.exit(0);
        }
    }

    public static boolean verifyUserCredentials(String a, String b){
        try{
            String hashA = StringUtil.applySha256(a);
            String hashB = StringUtil.applySha256(b);

            File f = new File("src/main/resources/userDB.txt");// where the vendor database will go;
            BufferedReader buffer = new BufferedReader(new FileReader(f));
            String readLine = "";

            while ( (readLine = buffer.readLine()) != null){
                String[] line = readLine.split(",");

                if(line[0].equals(hashA) && line[1].equals(hashB)){
                    username = hashA;
                    //B = Integer.parseInt(line[3]);
                    buffer.close();
                    return true;
                }
            }
            buffer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args){
        BlockChain.initiateBlockChain();
        new Login();
    }
}
