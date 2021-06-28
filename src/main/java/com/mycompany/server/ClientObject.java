/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.io.*;


public class ClientObject extends Thread {
    public DataInputStream in;
    public DataOutputStream out;
    public final Socket client;
    
    public ClientObject(Socket clientSocket) throws IOException {
        client = clientSocket;
        
        in = new DataInputStream(client.getInputStream());    
        out = new DataOutputStream (client.getOutputStream());
        
        
    }
}
//   @Override
//    public void run() {
//
//
//            try {
//                for (int i = 0; i < 2; i++) {
//                    DataOutputStream out = Server.clients.get(i).out;
//                    DataInputStream in = Server.clients.get(i).in;
//
//
//                    Boolean bM = in.readBoolean();
//                    Boolean rM = in.readBoolean();
//                    Boolean bT = in.readBoolean();
//                    Boolean rT = in.readBoolean();
//                    int xR = in.readInt();
//                    int yR = in.readInt();
//                    int xB = in.readInt();
//                    int yB = in.readInt();
//
//                    out.writeInt(xR);
//                    out.flush();
//
//                    out.writeInt(yR);
//                    out.flush();
//
//                    out.writeInt(xB);
//                    out.flush();
//
//                    out.writeInt(yB);
//                    out.flush();
//
//                    out.writeBoolean(bM);
//                    out.flush();
//
//                    out.writeBoolean(rM);
//                    out.flush();
//
//                    out.writeBoolean(rT);
//                    out.flush();
//
//                    out.writeBoolean(bT);
//                    out.flush();
//            }
//
//        }
//        catch (IOException e) {
//
//        }
    //}


    
