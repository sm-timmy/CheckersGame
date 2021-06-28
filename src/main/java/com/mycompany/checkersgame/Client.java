/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.checkersgame;

import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import static javax.swing.JOptionPane.showMessageDialog;

public class Client extends Handler  {
/*    public static void main(String[] args) {
try (Socket socket = new Socket("127.0.0.1", 9090);
         BufferedWriter writer = 
           new BufferedWriter (
                new OutputStreamWriter(socket.getOutputStream()));
         BufferedReader reader = 
                   new BufferedReader(
                   new InputStreamReader(
                   socket.getInputStream())))
{
    
}
    catch(IOException e){
    }
    
    
}
*/
   
    
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Handler handler;
    private Field [][] fieldArray;
    private CheckersBoard checkersBoard;

    public static boolean sendToServer = false;
    
  //  public static TanIntegral calc = new TanIntegral();
    
    public Client(String ip, int port, Handler handler_) throws IOException {
        super(new Field[8][8]);
            System.out.println("1");


        try {
            this.socket = new Socket(ip, port);
        } catch (IOException e) {
            System.err.println("Can't run socket");
        }
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
                        System.out.println("2");
                        handler = handler_;

        } catch (IOException e) {
            Client.this.closeSocket();
        }
        writeMes();
    }
    
    private void writeMes() {
        String message;
                    System.out.println("3");

        fieldArray = new Field[8][8];
//        handler = new Handler(fieldArray);
        boolean redMove =  handler.GetRedMove();
        boolean blackMove =  handler.GetBlackMove();
        boolean redTake =  handler.GetRedTake();
        boolean blackTake =  handler.GetBlackTake();
        int xRed = handler.GetxRed();//координаты подхваченной красной шашки
        int yRed = handler.GetyRed();
        int xBlack = handler.GetxBlack();
        int yBlack = handler.GetyBlack();
        
        try {
            while (true) {
//                String message1;
//                System.out.println("1111");
//                DataInputStream dataInputStream = new DataInputStream(in);
//                if (dataInputStream.available() != 0) {
//                    message1 = dataInputStream.readUTF();
//                    System.out.println("msg " + message1);
//                }
//                in = new DataInputStream(socket.getInputStream());
//                out = new DataOutputStream(socket.getOutputStream());

                if (in.available() != 0) {
                    redMove = in.readBoolean();
                    blackMove = in.readBoolean();
                    redTake = in.readBoolean();
                    blackTake = in.readBoolean();

                    xRed = in.read();
                    yRed = in.read();
                    xBlack = in.read();
                    yBlack = in.read();


                    handler.SetRedMove(redMove);
                    handler.SetBlackMove(blackMove);
                    handler.SetRedTake(redTake);
                    handler.SetBlackTake(blackTake);


//                    handler.SetxRed(xRed);
//                    handler.SetyRed(yRed);
//                    handler.SetxBlack(xBlack);
//                    handler.SetyBlack(yBlack);

                    System.out.println("\n\n\nrM " + redMove);
                    System.out.println("bM " + blackMove);
                    System.out.println("rT " + redTake);
                    System.out.println("bT " + blackTake);
                    System.out.println("xR " + xRed);
                    System.out.println("yR " + yRed);
                    System.out.println("xB " + xBlack);
                    System.out.println("yB " + yBlack + "\n\n\n");

                    if (redMove) {
                        handler.clickHandler(xRed, yRed, false);
                    }
                    if (blackMove) {
                        handler.clickHandler(xBlack, yBlack, false);
//                        handler.SetRedMove(true);
                    }

//                    handler.SetRedMove(!redMove);
//                    handler.SetBlackMove(!blackMove);

//                    handler.SetxRed(xRed);
//                    handler.SetyRed(yRed);
//                    handler.SetxBlack(xBlack);
//                    handler.SetyBlack(yBlack);

//                    handler.clickHandler(xRed,yRed);
//                    handler.clickHandler(xBlack,yBlack);
                    //handler.moveRed(xRed,yRed);
                    //handler.invalidate();
                    handler.validate();
                    handler.repaint();
                }
                if(sendToServer) {
                    redMove =  handler.GetRedMove();
                    blackMove =  handler.GetBlackMove();
                    redTake =  handler.GetRedTake();
                    blackTake =  handler.GetBlackTake();
                    xRed = handler.GetxRed();//координаты подхваченной красной шашки
                    yRed = handler.GetyRed();
                    xBlack = handler.GetxBlack();
                    yBlack = handler.GetyBlack();


                    out.writeBoolean(redMove);
                    out.flush();
                    out.writeBoolean(blackMove);
                    out.flush();
                    out.writeBoolean(redTake);
                    out.flush();
                    out.writeBoolean(blackTake);
                    out.flush();

                //    in.readBoolean(redMove);

                    out.write(xRed);
                    out.flush();
                    out.write(yRed);
                    out.flush();
                    out.write(xBlack);
                    out.flush();
                    out.write(yBlack);
                    out.flush();
                    //out.close();
                    sendToServer = false;
                }
            }
        } catch (IOException e) {
            Client.this.closeSocket(); 
        }
    }
    private void closeSocket() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                            System.out.println("123");

            }
        } catch (IOException e) {}
    }
     public static String ip = "127.0.0.1";
    public static int port = 9090;
    
//  public static void main(String[] args) {
//        new Client(ip, port);
//      //  CheckersBoard chc = CheckersBoard();
//      //  new CheckersBoard();
//    }
}
