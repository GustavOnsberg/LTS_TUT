//Af Jana Jankovic, Viktor Rindow og Gustav Onsberg

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class MainLoop extends JFrame implements ActionListener {

    static BigInteger g;
    static BigInteger p;
    static BigInteger A;

    static BigInteger B;
    static BigInteger b = new BigInteger("7");

    static BigInteger K;

    static BigInteger X_i;
    static byte[] X_b = new byte[3];

    static BigInteger X_i_0;
    static byte[] X_b_0 = new byte[3];



    //0 = empty
    //1 = X
    //2 = O
    public static Socket MySocket = null;
    static BufferedInputStream bi;
    static  BufferedOutputStream bo;
    public static MainLoop mainLoop = null;
    public static PrintWriter pw;
    public static String fromServer;
    private static Object ByteBuffer;


    public static void sendString(String message) throws IOException {
        byte [] byteArray = message.getBytes();

        BigInteger c = new BigInteger("1");
        BigInteger a = new BigInteger("125");
        byte keyByte;

        for(int i = 0; i < byteArray.length;i++){
            X_i = X_i.multiply(a).add(c).mod(new BigInteger("2")).pow(24);
            X_b = X_i.toByteArray();
            keyByte = X_b[X_b.length - 1];
            byteArray[i] = (byte) (byteArray[i] ^ keyByte);
        }

        bo.write(byteArray);
        bo.flush();
    }

    public static String recieveString() throws IOException {
        X_i = new BigInteger(String.valueOf(X_i_0));
        X_b = X_i_0.toByteArray();

        byte[] byteArray = new byte[16];
        bi.read(byteArray,0,16);
        BigInteger c = new BigInteger("1");
        BigInteger a = new BigInteger("125");
        byte keyByte;

        for(int i = 0; i < byteArray.length;i++){
            X_i = X_i.multiply(a).add(c).mod(new BigInteger("2")).pow(24);
            X_b = X_i.toByteArray();
            keyByte = X_b[X_b.length - 1];
            byteArray[i] = (byte) (byteArray[i] ^ keyByte);
            System.out.print(byteArray[i]+" ");
        }
        String s = new String(byteArray, StandardCharsets.US_ASCII);
        return s;
    }

    public static void main(String[] args){
        mainLoop = new MainLoop();

        mainLoop.setTitle("MainLoop"); // Set title on window
        mainLoop.setSize(500, 500);     // Set size
        mainLoop.setResizable(false);    // Allow the window to be resized
        mainLoop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainLoop.setVisible(true);      // Make the window visible
        mainLoop.setResizable(true);

        //I dette stykke kan man vælge hvor svært spillet skal være, som bestemmer om man forbinder til port 1102 eller 1105
        int port = 1465;
        System.out.println("connecting to "+port);




        try {
            byte[] byteArray = new byte[128];
            MySocket = new Socket("itkomsrv.fotonik.dtu.dk",port);      //Opretter forbindelse til server med den valgte port
            bi = new BufferedInputStream(MySocket.getInputStream());
            bo = new BufferedOutputStream(MySocket.getOutputStream());



            bi.read(byteArray,0,128); //Tager beskeder fra server
            byte[] b1 = new byte[16];
            byte[] b2 = new byte[16];
            byte[] b3 = new byte[16];

            int b = 77000;

            for(int i = 0; i < 16; i++){
                b1[i] = byteArray[i+2];
            }
            for(int i = 0; i < 16; i++){
                b2[i] = byteArray[i+18];
            }
            for(int i = 0; i < 16; i++){
                b3[i] = byteArray[i+34];
            }
            g = new BigInteger(b1);
            System.out.println(g);
            p = new BigInteger(b2);
            System.out.println(p);
            A = new BigInteger(b3);
            System.out.println(A);

            B = g.pow(b).mod(p);
            System.out.println(B);

            bo.write(B.toByteArray());
            bo.flush();

            K = A.pow(b).mod(p);
            byte[] arrayK = K.toByteArray();

            for(int i = 0; i < 3; i++){
                System.out.println("hhg "+i);
                X_b_0[i] = arrayK[arrayK.length + i - 3];
            }
            X_i_0 = new BigInteger(X_b);





            loop();             //Starter loop-funktionen som er der hvor hele klientdelen af spillet finder sted.
        }catch(Exception e){
            e.printStackTrace();
        }



        try {
            //Lukker forbindelsen når spillet er slut.
            MySocket.close();
            System.out.println("Connection has been closed.");
        }catch(Exception e){
            System.out.println(e);
        }

    }
    private static final long serialVersionUID = 1L;
    public JButton b1, b2, b3, b4, b5, b6, b7, b8, b9;
    public JLabel l1;
    public MainLoop() {
        getContentPane().setLayout(new BorderLayout());

        b1 = new JButton("Button 1");
        b1.addActionListener(this);
        b1.setAlignmentX(Component.CENTER_ALIGNMENT);
        b1.setVisible(true);

        b2 = new JButton("Button 2");
        b2.addActionListener(this);
        b2.setAlignmentX(Component.CENTER_ALIGNMENT);
        b2.setVisible(true);

        b3 = new JButton("Button 3");
        b3.addActionListener(this);
        b3.setAlignmentX(Component.CENTER_ALIGNMENT);
        b3.setVisible(true);

        b4 = new JButton("Button 4");
        b4.addActionListener(this);
        b4.setAlignmentX(Component.CENTER_ALIGNMENT);
        b4.setVisible(true);

        b5 = new JButton("Button 5");
        b5.addActionListener(this);
        b5.setAlignmentX(Component.CENTER_ALIGNMENT);
        b5.setVisible(true);

        b6 = new JButton("Button 6");
        b6.addActionListener(this);
        b6.setAlignmentX(Component.CENTER_ALIGNMENT);
        b6.setVisible(true);

        b7 = new JButton("Button 7");
        b7.addActionListener(this);
        b7.setAlignmentX(Component.CENTER_ALIGNMENT);
        b7.setVisible(true);

        b8 = new JButton("Button 8");
        b8.addActionListener(this);
        b8.setAlignmentX(Component.CENTER_ALIGNMENT);
        b8.setVisible(true);

        b9 = new JButton("Button 9");
        b9.addActionListener(this);
        b9.setAlignmentX(Component.CENTER_ALIGNMENT);
        b9.setVisible(true);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(3,3));
        p1.add(b1);
        p1.add(b2);
        p1.add(b3);
        p1.add(b4);
        p1.add(b5);
        p1.add(b6);
        p1.add(b7);
        p1.add(b8);
        p1.add(b9);

        l1 = new JLabel("WELCOME TO TICTACTOE!");
        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        p2.add(l1);

        getContentPane().add(p1,BorderLayout.CENTER);
        getContentPane().add(p2,BorderLayout.SOUTH);
    }
    public void draw(String XO){
        b1.setText(XO.substring(0,1));
        b2.setText(XO.substring(1,2));
        b3.setText(XO.substring(2,3));
        b4.setText(XO.substring(3,4));
        b5.setText(XO.substring(4,5));
        b6.setText(XO.substring(5,6));
        b7.setText(XO.substring(6,7));
        b8.setText(XO.substring(7,8));
        b9.setText(XO.substring(8,9));

    }
    public void actionPerformed(ActionEvent e) {
        try{
            if(e.getSource()==b1){
                System.out.println("1");
                buttonSend(1);
            }else if(e.getSource()==b2){
                System.out.println("2");
                buttonSend(2);
            }else if(e.getSource()==b3){
                System.out.println("3");
                buttonSend(3);
            }else if(e.getSource()==b4){
                System.out.println("4");
                buttonSend(4);
            }else if(e.getSource()==b5){
                System.out.println("5");
                buttonSend(5);
            }else if(e.getSource()==b6){
                System.out.println("6");
                buttonSend(6);
            }else if(e.getSource()==b7){
                System.out.println("7");
                buttonSend(7);
            }else if(e.getSource()==b8){
                System.out.println("8");
                buttonSend(8);
            }else if(e.getSource()==b9){
                System.out.println("9");
                buttonSend(9);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void buttonSend(int input) throws IOException {
        pw = new PrintWriter(MySocket.getOutputStream());
        pw.print(input+"\n\r");
        pw.flush();
    }
    public static void loop() throws IOException {
        boolean running = true; //Bestemmer om loopet køre
              //Til at gemme beskeder fra servern
                //Til at printe til server
        Scanner sc2 = new Scanner(System.in);   //Til at tage spillerens input

        while (running) {

            //Kigger på besked fra server og gør hvad der er passende til situationen

            fromServer = recieveString();
            if(!fromServer.equals(""))
                System.out.println(fromServer);

            if(fromServer.contains("YOUR TURN") && false){
                try{
                    //Hvis det er spillerens tur, sender klienten et brugerinput til serveren
                    sendString(sc2.next()+"\r\n");
                }catch(Exception e){
                    System.out.println(e);
                }
            }
            else if(fromServer.contains("WINS")){
                running = false;    //Hvis nogen vinder eller hvis spillet bliver uafgjort, vil denne boolean blive sat til false, som resultere i while-loopet stopper
            }
            else if(fromServer.contains("BOARD IS")){
                mainLoop.draw(fromServer.substring(9,18));
            }
        }
    }
}
