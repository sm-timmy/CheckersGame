/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.server;

/*import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9090))
        {
          System.out.println("Server started");
          while (true)
          
   try ( Socket socket =  server.accept();
         BufferedWriter writer = 
           new BufferedWriter (
                new OutputStreamWriter(socket.getOutputStream()));
         BufferedReader reader = 
                   new BufferedReader(
                   new InputStreamReader(
                   socket.getInputStream())))
   {
      String request = reader.readLine();
       writer.write("Hello!"+ request);
       writer.newLine();
       writer.flush();
}

        }
    catch (IOException e)
    {
        throw new RuntimeException(e);
    }
   
 
    }
    
    
}
*/
//import com.mycompany.checkersgame.Client;
import com.mycompany.checkersgame.Client;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.LinkedList;
import static javax.swing.JOptionPane.showMessageDialog;
import java.io.*;


public class Server {

    private static ServerSocket server;
    public static ArrayList<ClientObject> clients = new ArrayList<ClientObject>();


    public static void main(String[] args) {
        new Server().run();
//        new Server().checkAliveClients();
////        new Server().sendMes();
    }   
    
    
      public int checkAliveClients() {

        ArrayList<ClientObject> active_clients = new ArrayList();

        for (int i = 0; i < Server.clients.size(); i++) {
            try {
                ClientObject client = Server.clients.get(i);
                DataOutputStream out = client.out;
                //DataInputStream in = client.in;

                out.writeUTF("start");
                out.flush();
                active_clients.add(client);
            }
            catch (IOException e) { }
        }

        clients = active_clients;
        return Server.clients.size();
    }
    
    
    
    public void sendMes() {
        int clientsCount = 2;
        
        if (clientsCount > 0) {
          // ArrayList<int[]> partsIndex = splitFile(clientsCount);
        
            try {
                for (int i = 0; i < 2; i++) {

                    DataInputStream in = Server.clients.get(i).in;
                    DataOutputStream out = Server.clients.get(Math.abs(i-1)).out;

                    //showMessageDialog(null, "Клиент подключен");
                    if (in.available() != 0) {

                        Boolean rM = in.readBoolean();
                        Boolean bM = in.readBoolean();

                        Boolean rT = in.readBoolean();
                        Boolean bT = in.readBoolean();

                        int xR = in.read();
                        int yR = in.read();
                        int xB = in.read();
                        int yB = in.read();

                        if( !(bT || rT) ) {
                            if (bM)
                                rT = true;
                            else
                                bT = true;

//
                            boolean temp = rM;
                            rM = bM;
                            bM = temp;

                        }
//                        else {
//                            rT = false;
//                            bT = false;
//                        }

                        System.out.println("Message from client " + i);
                        System.out.println("rM " + rM);
                        System.out.println("bM " + bM);
                        System.out.println("rT " + rT);
                        System.out.println("bT " + bT);
                        System.out.println("xR " + xR);
                        System.out.println("yR " + yR);
                        System.out.println("xB " + xB);
                        System.out.println("yB " + yB + "\n\n\n");

                        out.writeBoolean(rM);
                        out.flush();

                        out.writeBoolean(bM);
                        out.flush();

                        out.writeBoolean(rT);
                        out.flush();

                        out.writeBoolean(bT);
                        out.flush();

                        out.write(xR);
                        out.flush();

                        out.write(yR);
                        out.flush();

                        out.write(xB);
                        out.flush();

                        out.write(yB);
                        out.flush();
                    }

                }
            }
            catch (IOException e) {
            }

        }
        else showMessageDialog(null, "Нет подключенных клиентов!");  
    }
     

    public void run() {
        try {
            server = new ServerSocket(9090);//

            while (clients.size() < 2)
            {
                ClientObject newClient = new ClientObject(server.accept());
                if (newClient.client.isConnected()) {
                    System.out.println("Клиент подключен!" + "\n" + newClient.client.toString());
                }
                clients.add(newClient);
                newClient.start();
                System.out.println("Количество клиентов: " + clients.size() + "\n\n");
            }
            //while (!server.isClosed() && this.checkAliveClients()==2) {
            while (!server.isClosed()) {
                this.sendMes();
            }
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }
    
    public void sendTask() {
        
    }
}

