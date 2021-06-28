package com.mycompany.checkersgame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import static javax.swing.JOptionPane.showMessageDialog;

public class Handler extends JFrame implements ActionListener, MouseListener {

    public Handler(Field [][] fieldArray) throws IOException {
        this.fieldArray = fieldArray;
        redMove = true;
        blackMove = false;
     /*   try {

            // создаём сокет общения на стороне клиента в конструкторе объекта
            socket = new Socket("localhost", 3345);
            System.out.println("Client connected to socket");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
//     clickHandler(5,3,false);
//     clickHandler(4,4, false);
//
//     clickHandler(2,6, false);
//     clickHandler(3,5, false);
//
//     clickHandler(4,4, false);
//     clickHandler(2,6, false);
//     clickHandler();
//     clickHandler();
    }
   
    private Field [][] fieldArray;
    private boolean redMove;//переменная показывает , ходят ли крансые
    private boolean redTake;//переменная , поакзывает, подхвачена ли красная шашка
    private int xRed;//координаты подхваченной красной шашки
    private int yRed;//координаты подхваченной красной шашки
  
    private boolean blackMove;//переменная показывает , ходят ли крансые
    private boolean blackTake;//переменная , поакзывает, подхвачена ли красная шашка
    private int xBlack;//координаты подхваченной красной шашки
    private int yBlack;//координаты подхваченной красной шашки

    private DataOutputStream out;
    public static String ip = "127.0.0.1";
    public static int port = 9090;


    public boolean GetBlackTake() {
       return this.blackTake;
   }

   public void SetBlackTake(boolean blackTake) {
       this.blackTake = blackTake;
   }
  

   public void SetRedTake(boolean redTake) {
       this.redTake = redTake;
   }   
   public boolean GetRedTake() {
       return this.redTake;
   }
      public boolean GetBlackMove() {
       return this.blackMove;
   }

   public void SetBlackMove(boolean blackMove) {
       this.blackMove = blackMove;
   }
  

   public void SetRedMove(boolean redMove) {
       this.redMove = redMove;
   }   
   public boolean GetRedMove() {
       return this.redMove;
   }
   
   
     public void SetxRed(int xRed) {
       this.xRed = xRed;
   }   
   public int GetxRed() {
       return this.xRed;
   }
   
    
     public void SetyRed(int yRed) {
       this.yRed = yRed;
   }   
   public int GetyRed() {
       return this.yRed;
   }
   
       public void SetxBlack(int xBlack) {
       this.xBlack = xBlack;
   }   
   public int GetxBlack() {
       return this.xBlack;
   }
     public void SetyBlack(int yBlack) {
       this.yBlack = yBlack;
   }   
   public int GetyBlack() {
       return this.yBlack;
   }
    /*    public void run() {

        try (

                // создаём объект для записи строк в созданный скокет, для
                // чтения строк из сокета
                // в try-with-resources стиле
                DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
                DataInputStream ois = new DataInputStream(socket.getInputStream())) {
            System.out.println("Client oos & ois initialized");

            int i = 0;
            // создаём рабочий цикл
            while (i < 5) {

                // пишем сообщение автогенерируемое циклом клиента в канал
                // сокета для сервера
                oos.writeUTF("clientCommand " + i);

                // проталкиваем сообщение из буфера сетевых сообщений в канал
                oos.flush();

                // ждём чтобы сервер успел прочесть сообщение из сокета и
                // ответить
             //   Thread.sleep(10);
                System.out.println("Client wrote & start waiting for data from server...");

                // забираем ответ из канала сервера в сокете
                // клиента и сохраняем её в ois переменную, печатаем на
                // консоль
                System.out.println("reading...");
                String in = ois.readUTF();
                System.out.println(in);
                i++;
            //    Thread.sleep(5000);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } /* catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    } 
        */
        
        
    public void clickHandler(int x, int y, boolean sendIt) throws IOException {

        System.out.println("rM " + redMove);
        System.out.println("bM " + blackMove);
        System.out.println("rT " + redTake);
        System.out.println("bT " + blackTake);
        System.out.println("xR " + x);
        System.out.println("yR " + y);
//        System.out.println("xB " + xBlack);
//        System.out.println("yB " + yBlack + "\n\n\n");

        System.out.println("Handler called");
        //если ходят красные
        if (redMove) {
            System.out.println("Red move");
            //если подхвачена красная шашка
            if (redTake) {
                //если щелчёк на доступной красной шашке
                if (availableRed(x, y)) {
                    //перезахватываем другую шашку (или эту же)
                    xRed = x;
                    yRed = y;
                }
                //
                else {
                    //щелчёк на доступном поле
                    if (availableFieldRed(x, y)) {
                        //тогда либо просто ход, либо ход со съедением черной шашки
                        if (eatingRed(x, y)) {
                            //destroyBlack();//уничтожить черную шашку??? - засунуть в eating
                            moveRed(x, y, sendIt);
                            if (continueEatingRed()) {
                                //System.out.println("CONTINUJE EATING RED");
                             return;
                            }
                            else {
                            redTake = false;
                            redMove = false;
                            blackMove = true;
                            }
                        }
                        else {
                            //перемещаем шашку
                            moveRed(x, y, sendIt);
                            redTake = false;
                            redMove = false;
                            blackMove = true;
                        }
                    }
                    else {
                        return;
                    }
                }
            }
            //если красная шашка не подхвачена
            else {
                //если щелчёк на доступной красной шашке
                if (availableRed(x, y)) {
                    redTake = true;//захват шашки
                    xRed = x;
                    yRed = y;
                }
            }
        }
        //если ходят черные
        else {
            //если подхвачена черная шашка
            if (blackTake) {

                System.out.println("Black move");
                //если щелчёк на доступной черной шашке
                if (availableBlack(x, y)) {
                    //перезахватываем другую шашку (или эту же)
                    xBlack = x;
                    yBlack = y;
                }
                //
                else {
                    //щелчёк на доступном поле
                    if (availableFieldBlack(x, y)) {
                        //тогда либо просто ход, либо ход со съедением красной шашки
                        if (eatingBlack(x, y)) {
                            //destroyBlack();//уничтожить красную шашку??? - засунуть в eating
                            moveBlack(x, y, sendIt);
                            if (continueEatingBlack()) {
                                //System.out.println("CONTINUJE EATING RED");
                             return;
                            }
                            else {
                            blackTake = false;
                            blackMove = false;
                            redMove = true;
                            }
                        }
                        else {
                            //перемещаем шашку
                            moveBlack(x, y, sendIt);
                            blackTake = false;
                            blackMove = false;
                            redMove = true;
                        }
                    }
                    else {
                        return;
                    }
                }
            }
            //если черная шашка не подхвачена
            else {
                //если щелчёк на доступной черной шашке
                if (availableBlack(x, y)) {
                    blackTake = true;//захват шашки
                    xBlack = x;
                    yBlack = y;
                }
            }
        }
        if (sendIt)
            Client.sendToServer = true;
    }

//    public boolean sendToServer(Socket socket) throws IOException {
//        //SEND TO server!
//
//        out = new DataOutputStream(socket.getOutputStream());
//        try {
//            out.writeBoolean(redMove);
//            out.flush();
//            out.writeBoolean(blackMove);
//            out.flush();
//            out.writeBoolean(redTake);
//            out.flush();
//            out.writeBoolean(blackTake);
//            out.flush();
//
//            //    in.readBoolean(redMove);
//
//            out.write(xRed);
//            out.flush();
//            out.write(yRed);
//            out.flush();
//            out.write(xBlack);
//            out.flush();
//            out.write(yBlack);
//            out.flush();
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }


    //функция, проверяет доступна ли красная шашка , находящаяся на этих координатах
    private boolean availableRed(int x, int y) {
        ArrayList<Pair> eating = new ArrayList<Pair>();//если длина этого списочного массива будет > 0 , значит существует хотя бы одна шашка, которая должна есть, её координаты и будут храниться в этом массиве
        //ArrayList<Pair> simple = new ArrayList<Pair>();//здесь будут храниться координаты шашек, которые могут быть "захвачены"

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (fieldArray[i][j].Red) {
                    if ( (i - 1) >= 0 && (j - 1) >= 0) {
                        if (fieldArray[i - 1][j - 1].Black || fieldArray[i - 1][j - 1].BlackQueen) {
                            if ((i - 2) >= 0 && (j - 2) >= 0) {
                                if (fieldArray[i - 2][j - 2].Empty) {
                                    eating.add(new Pair(i, j));
                                }
                            }
                        }
                    }
                    if ( (i - 1) >= 0 && (j + 1) < 8) {
                        if (fieldArray[i - 1][j + 1].Black || fieldArray[i - 1][j + 1].BlackQueen) {
                            if ((i - 2) >= 0 && (j + 2) < 8) {
                                if (fieldArray[i - 2][j + 2].Empty) {
                                    eating.add(new Pair(i, j));
                                }
                            }
                        }
                    }
                }
                if (fieldArray[i][j].RedQueen) {
                    //лево верх
                    if ((i - 1) >= 0 && (j - 1) >= 0 && (fieldArray[i - 1][j - 1].Black || fieldArray[i - 1][j - 1].BlackQueen)) {
                        if ((i - 2) >= 0 && (j - 2) >= 0 && fieldArray[i- 2][j - 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    //право верх
                    if ((i - 1) >= 0 && ( j + 1) < 8 && (fieldArray[i - 1][ j + 1].Black || fieldArray[i - 1][ j + 1].BlackQueen)) {
                        if (( i - 2) >= 0 && (j + 2) < 8 && fieldArray[i - 2][j + 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    //низ лево
                    if ((i + 1) < 8 && (j - 1) >= 0 && (fieldArray[i + 1][j - 1].Black || fieldArray[i + 1][j - 1].BlackQueen)) {
                        if ((i + 2) < 8 && (j - 2) >= 0 && fieldArray[i + 2][j - 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    //низ право
                    if ((i + 1) < 8 && (j + 1) < 8 && (fieldArray[i + 1][ j + 1].Black || fieldArray[i + 1][ j + 1].BlackQueen)) {
                        if (( i + 2) < 8 && (j + 2) < 8 && fieldArray[i + 2][ j + 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                }
            }
        }

        if (eating.size() > 0) {
                for (int i = 0; i < eating.size(); i++) {
                    if (eating.get(i).x == x && eating.get(i).y == y) {
                        clearCurrent();
                        fieldArray[x][y].Current = true;
                        return true;
                    }
                }
            }
        else if (fieldArray[x][y].Red && (x - 1) >= 0 && (y - 1) >= 0 && fieldArray[x - 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].Red && (x - 1) >= 0 && (y + 1) < 8 && fieldArray[x - 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].RedQueen && (x - 1) >= 0 && (y - 1) >= 0 && fieldArray[x - 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].RedQueen && (x - 1) >= 0 && (y + 1) < 8 && fieldArray[x - 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].RedQueen && (x + 1) < 8 && (y - 1) >= 0 && fieldArray[x + 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].RedQueen && (x + 1) < 8 && (y + 1) < 8 && fieldArray[x + 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        return false;
    }

    //функция, проверяет доступно ли поле
    private boolean availableFieldRed(int x, int y) {
        boolean key = false;
        if ((fieldArray[xRed][yRed].RedQueen || fieldArray[xRed][yRed].Red) &&(xRed - 2) >= 0 && (yRed - 2) >= 0 && fieldArray[xRed -2][yRed -2].Empty && (xRed - 1) >= 0 && (yRed - 1) >= 0 && (fieldArray[xRed -1][yRed -1].Black || fieldArray[xRed -1][yRed -1].BlackQueen)) {
            key = true;
            if (x == (xRed - 2) && y == (yRed - 2)) return true;
        }
        if ((fieldArray[xRed][yRed].RedQueen || fieldArray[xRed][yRed].Red) && (xRed - 2) >= 0 && (yRed + 2) < 8 && fieldArray[xRed -2][yRed +2].Empty && (xRed - 1) >= 0 && (yRed + 1) < 8 && (fieldArray[xRed -1][yRed +1].Black || fieldArray[xRed -1][yRed +1].BlackQueen)) {
            key = true;
            if (x == (xRed - 2) && y == (yRed + 2)) return true;
        }
        //для дамки
        if (fieldArray[xRed][yRed].RedQueen && (xRed + 2) < 8 && (yRed - 2) >= 0 && fieldArray[xRed + 2][yRed -2].Empty && (xRed + 1) < 8 && (yRed - 1) >= 0 && (fieldArray[xRed + 1][yRed -1].Black || fieldArray[xRed + 1][yRed -1].BlackQueen)) {
            key = true;
            if (x == (xRed + 2) && y == (yRed - 2)) return true;
        }
        if (fieldArray[xRed][yRed].RedQueen && (xRed + 2) < 8 && (yRed + 2) < 8 && fieldArray[xRed + 2][yRed + 2].Empty && (xRed + 1) < 8 && (yRed + 1) < 8 && (fieldArray[xRed + 1][yRed +1].Black || fieldArray[xRed + 1][yRed +1].BlackQueen)) {
            key = true;
            if (x == (xRed + 2) && y == (yRed + 2)) return true;
        }
        //
        if (!key ) {
        if ((fieldArray[x][y].Empty && x == (xRed - 1) && y == (yRed -1)) || (fieldArray[x][y].Empty && x == (xRed - 1) && y == (yRed +1))) {
            return true;
        }        
        //для дамки
        if ((fieldArray[xRed][yRed].RedQueen && fieldArray[x][y].Empty && x == (xRed - 1) && y == (yRed -1)) || (fieldArray[xRed][yRed].RedQueen && fieldArray[x][y].Empty && x == (xRed - 1) && y == (yRed +1)) || (fieldArray[xRed][yRed].RedQueen && fieldArray[x][y].Empty && x == (xRed + 1) && y == (yRed +1)) || (fieldArray[xRed][yRed].RedQueen && fieldArray[x][y].Empty && x == (xRed + 1) && y == (yRed - 1))) {
            return true;
        }
        }
        return false;   
    }

    //функция, проверяет, произошло ли съедение шашки, уничтожает черную шашку
    private boolean eatingRed(int x, int y) {
        if (x == (xRed - 2) && y == (yRed - 2) && fieldArray[x][y].Empty && (fieldArray[xRed - 1][yRed - 1].Black || fieldArray[xRed - 1][yRed - 1].BlackQueen)){
            if (fieldArray[xRed - 1][yRed - 1].Black) fieldArray[xRed - 1][yRed - 1].Black  = false;
            else fieldArray[xRed - 1][yRed - 1].BlackQueen = false;
            fieldArray[xRed - 1][yRed - 1].Empty = true;
            return true;
        }
        if (x == (xRed - 2) && y == (yRed + 2) && fieldArray[x][y].Empty && (fieldArray[xRed - 1][yRed + 1].Black || fieldArray[xRed - 1][yRed + 1].BlackQueen)){
            if (fieldArray[xRed - 1][yRed + 1].Black) fieldArray[xRed - 1][yRed + 1].Black = false;
            else fieldArray[xRed - 1][yRed + 1].BlackQueen = false;
            fieldArray[xRed - 1][yRed + 1].Empty = true;
            return true;
        }
        //для дамки
        if (fieldArray[xRed][yRed].RedQueen && x == (xRed + 2) && y == (yRed - 2) && fieldArray[x][y].Empty && (fieldArray[xRed + 1][yRed - 1].Black || fieldArray[xRed + 1][yRed - 1].BlackQueen)){
            if (fieldArray[xRed + 1][yRed - 1].Black) fieldArray[xRed + 1][yRed - 1].Black = false;
            else fieldArray[xRed + 1][yRed - 1].BlackQueen = false;
            fieldArray[xRed + 1][yRed - 1].Empty = true;
            return true;
        }
        if (fieldArray[xRed][yRed].RedQueen && x == (xRed + 2) && y == (yRed + 2) && fieldArray[x][y].Empty && (fieldArray[xRed + 1][yRed + 1].Black || fieldArray[xRed + 1][yRed + 1].BlackQueen)){
            if (fieldArray[xRed + 1][yRed + 1].Black) fieldArray[xRed + 1][yRed + 1].Black = false;
            else fieldArray[xRed + 1][yRed + 1].BlackQueen = false;
            fieldArray[xRed + 1][yRed + 1].Empty = true;
            return true;
        }
        return false;
    }
    //ф-ция перемещения шашки
    public void moveRed(int x, int y, boolean sendIt) {

        if (fieldArray[xRed][yRed].Red) {
            if (x == 0) {
                fieldArray[x][y].RedQueen = true;
                fieldArray[x][y].Empty = false;
                fieldArray[xRed][yRed].Empty = true;
                fieldArray[xRed][yRed].Red = false;
            }
            else {
                fieldArray[x][y].Red = true;
                fieldArray[x][y].Empty = false;
                fieldArray[xRed][yRed].Empty = true;
                fieldArray[xRed][yRed].Red = false;
            }
        }
        else {
            fieldArray[x][y].RedQueen = true;
                fieldArray[x][y].Empty = false;
                fieldArray[xRed][yRed].Empty = true;
                fieldArray[xRed][yRed].RedQueen = false;
        }
        xRed = x;
        yRed = y;
        if (sendIt)
            Client.sendToServer = true;
    }

    private boolean continueEatingRed() {
        if ((xRed - 2) >= 0 && (yRed - 2) >= 0 && fieldArray[xRed - 2][yRed - 2].Empty && fieldArray[xRed - 1][yRed - 1].Black) {
            return true;
        }
        if ((xRed - 2) >= 0 && (yRed + 2) < 8 && fieldArray[xRed - 2][yRed + 2].Empty && fieldArray[xRed - 1][yRed + 1].Black) {
            return true;
        }
        //для дамки
        if (fieldArray[xRed][yRed].RedQueen  && (xRed + 2) < 8 && (yRed - 2) >= 0 && fieldArray[xRed + 2][yRed - 2].Empty && (fieldArray[xRed + 1][yRed - 1].Black || fieldArray[xRed + 1][yRed - 1].BlackQueen)) {
            return true;
        }
        if (fieldArray[xRed][yRed].RedQueen  && (xRed + 2) < 8 && (yRed + 2) < 8 && fieldArray[xRed + 2][yRed + 2].Empty && (fieldArray[xRed + 1][yRed + 1].Black || fieldArray[xRed + 1][yRed + 1].BlackQueen)) {
            return true;
        }
        return false;
    }   

    //функция, проверяет доступна ли черная шашка , находящаяся на этих координатах
    private boolean availableBlack(int x, int y) {
        ArrayList<Pair> eating = new ArrayList<Pair>();//если длина этого списочного массива будет > 0 , значит существует хотя бы одна шашка, которая должна есть, её координаты и будут храниться в этом массиве
        //ArrayList<Pair> simple = new ArrayList<Pair>();//здесь будут храниться координаты шашек, которые могут быть "захвачены"

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (fieldArray[i][j].Black) {
                    if ( (i + 1) < 8 && (j - 1) >= 0) {
                        if (fieldArray[i + 1][j - 1].Red || fieldArray[i + 1][j - 1].RedQueen) {
                            if ((i + 2) < 8 && (j - 2) >= 0) {
                                if (fieldArray[i + 2][j - 2].Empty) {
                                    eating.add(new Pair(i, j));
                                }
                            }
                        }
                    }
                    if ( (i + 1) < 8 && (j + 1) < 8) {
                        if (fieldArray[i + 1][j + 1].Red || fieldArray[i + 1][j + 1].RedQueen) {
                            if ((i + 2) < 8 && (j + 2) < 8) {
                                if (fieldArray[i + 2][j + 2].Empty) {
                                    eating.add(new Pair(i, j));
                                }
                            }
                        }
                    }
                }
                if (fieldArray[i][j].BlackQueen) {
                    //лево верх
                    if ((i - 1) >= 0 && (j - 1) >= 0 && (fieldArray[i - 1][j - 1].Red || fieldArray[i - 1][j - 1].RedQueen)) {
                        if ((i - 2) >= 0 && (j - 2) >= 0 && fieldArray[i- 2][j - 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    //право верх
                    if ((i - 1) >= 0 && ( j + 1) < 8 && (fieldArray[i - 1][ j + 1].Red || fieldArray[i - 1][ j + 1].RedQueen)) {
                        if (( i - 2) >= 0 && (j + 2) < 8 && fieldArray[i - 2][j + 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    //низ лево
                    if ((i + 1) < 8 && (j - 1) >= 0 && (fieldArray[i + 1][j - 1].Red || fieldArray[i + 1][j - 1].RedQueen)) {
                        if ((i + 2) < 8 && (j - 2) >= 0 && fieldArray[i + 2][j - 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                    //низ право
                    if ((i + 1) < 8 && (j + 1) < 8 && (fieldArray[i + 1][ j + 1].Red || fieldArray[i + 1][ j + 1].RedQueen)) {
                        if (( i + 2) < 8 && (j + 2) < 8 && fieldArray[i + 2][ j + 2].Empty) {
                            eating.add(new Pair(i, j));
                        }
                    }
                }
            }
        }

        if (eating.size() > 0) {
                for (int i = 0; i < eating.size(); i++) {
                    if (eating.get(i).x == x && eating.get(i).y == y) {
                        clearCurrent();
                        fieldArray[x][y].Current = true;
                        return true;
                    }
                }
            }
        else if (fieldArray[x][y].Black && (x + 1) < 8 && (y - 1) >= 0 && fieldArray[x + 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].Black && (x + 1) < 8 && (y + 1) < 8 && fieldArray[x + 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }

        else if (fieldArray[x][y].BlackQueen && (x - 1) >= 0 && (y - 1) >= 0 && fieldArray[x - 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].BlackQueen && (x - 1) >= 0 && (y + 1) < 8 && fieldArray[x - 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].BlackQueen && (x + 1) < 8 && (y - 1) >= 0 && fieldArray[x + 1][y - 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        else if (fieldArray[x][y].BlackQueen && (x + 1) < 8 && (y + 1) < 8 && fieldArray[x + 1][y + 1].Empty) {
            clearCurrent();
            fieldArray[x][y].Current = true;
            return true;
        }
        return false;
    }

    //функция, проверяет доступно ли поле
    private boolean availableFieldBlack(int x, int y) {
        boolean key = false;
        if ((fieldArray[xBlack][yBlack].BlackQueen || fieldArray[xBlack][yBlack].Black) && (xBlack + 2) < 8 && (yBlack - 2) >= 0 && fieldArray[xBlack + 2][yBlack -2].Empty && (xBlack + 1) < 8 && (yBlack - 1) >= 0 && (fieldArray[xBlack + 1][yBlack - 1].Red || fieldArray[xBlack + 1][yBlack - 1].RedQueen)) {
            key = true;
            if (x == (xBlack + 2) && y == (yBlack - 2)) return true;
        }
        if ((fieldArray[xBlack][yBlack].BlackQueen || fieldArray[xBlack][yBlack].Black) && (xBlack + 2) < 8 && (yBlack + 2) < 8 && fieldArray[xBlack + 2][yBlack + 2].Empty && (xBlack + 1) < 8 && (yBlack + 1) < 8 && (fieldArray[xBlack + 1][yBlack + 1].Red || fieldArray[xBlack + 1][yBlack + 1].RedQueen)) {
            key = true;
            if (x == (xBlack + 2) && y == (yBlack + 2)) return true;
        }
        //для дамки
        if (fieldArray[xBlack][yBlack].BlackQueen && (xBlack - 2) >= 0 && (yBlack - 2) >= 0 && fieldArray[xBlack - 2][yBlack -2].Empty && (xBlack - 1) >= 0 && (yBlack - 1) >= 0 && (fieldArray[xBlack - 1][yBlack -1].Red || fieldArray[xBlack - 1][yBlack -1].RedQueen)) {
            key = true;
            if (x == (xBlack - 2) && y == (yBlack - 2)) return true;
        }
        if (fieldArray[xBlack][yBlack].BlackQueen && (xBlack - 2) >= 0 && (yBlack + 2) < 8 && fieldArray[xBlack - 2][yBlack +2].Empty && (xBlack - 1) >= 0 && (yBlack + 1) < 8 && (fieldArray[xBlack - 1][yBlack +1].Red || fieldArray[xBlack - 1][yBlack +1].RedQueen)) {
            key = true;
            if (x == (xBlack - 2) && y == (yBlack + 2)) return true;
        }

        if (!key) {
            if ((fieldArray[x][y].Empty && x == (xBlack + 1) && y == (yBlack -1)) || (fieldArray[x][y].Empty && x == (xBlack + 1) && y == (yBlack +1))) {
                return true;
            }
            //для дамки
        if ((fieldArray[xBlack][yBlack].BlackQueen && fieldArray[x][y].Empty && x == (xBlack - 1) && y == (yBlack -1)) || (fieldArray[xBlack][yBlack].BlackQueen && fieldArray[x][y].Empty && x == (xBlack - 1) && y == (yBlack +1)) || (fieldArray[xBlack][yBlack].BlackQueen && fieldArray[x][y].Empty && x == (xBlack + 1) && y == (yBlack +1)) || (fieldArray[xBlack][yBlack].BlackQueen && fieldArray[x][y].Empty && x == (xBlack + 1) && y == (yBlack - 1))) {
            return true;
        }
        }
        return false;
    }

    //функция, проверяет, произошло ли съедение шашки
    private boolean eatingBlack(int x, int y) {
        if ((fieldArray[xBlack][yBlack].BlackQueen || fieldArray[xBlack][yBlack].Black) && x == (xBlack + 2) && y == (yBlack - 2) && fieldArray[x][y].Empty && (fieldArray[xBlack + 1][yBlack - 1].Red || fieldArray[xBlack + 1][yBlack - 1].RedQueen)){
            if (fieldArray[xBlack + 1][yBlack - 1].Red) fieldArray[xBlack + 1][yBlack - 1].Red = false;
            else fieldArray[xBlack + 1][yBlack - 1].RedQueen = false;
            fieldArray[xBlack + 1][yBlack - 1].Empty = true;
            return true;
        }
        if ((fieldArray[xBlack][yBlack].BlackQueen || fieldArray[xBlack][yBlack].Black) && x == (xBlack + 2) && y == (yBlack + 2) && fieldArray[x][y].Empty && (fieldArray[xBlack + 1][yBlack + 1].Red || fieldArray[xBlack + 1][yBlack + 1].RedQueen)){
            if (fieldArray[xBlack + 1][yBlack + 1].Red) fieldArray[xBlack + 1][yBlack + 1].Red = false;
            else fieldArray[xBlack + 1][yBlack + 1].RedQueen = false;
            fieldArray[xBlack + 1][yBlack + 1].Empty = true;
            return true;
        }
        //для дамки
        if (fieldArray[xBlack][yBlack].BlackQueen && x == (xBlack - 2) && y == (yBlack - 2) && fieldArray[x][y].Empty && (fieldArray[xBlack - 1][yBlack - 1].Red || fieldArray[xBlack - 1][yBlack - 1].RedQueen)){
            if (fieldArray[xBlack - 1][yBlack - 1].Red) fieldArray[xBlack - 1][yBlack - 1].Red = false;
            else fieldArray[xBlack - 1][yBlack - 1].RedQueen = false;
            fieldArray[xBlack - 1][yBlack - 1].Empty = true;
            return true;
        }
        if (fieldArray[xBlack][yBlack].BlackQueen && x == (xBlack - 2) && y == (yBlack + 2) && fieldArray[x][y].Empty && (fieldArray[xBlack - 1][yBlack + 1].Red || fieldArray[xBlack - 1][yBlack + 1].RedQueen)){
            if (fieldArray[xBlack - 1][yBlack + 1].Red) fieldArray[xBlack - 1][yBlack + 1].Red = false;
            else fieldArray[xBlack - 1][yBlack + 1].RedQueen = false;
            fieldArray[xBlack - 1][yBlack + 1].Empty = true;
            return true;
        }
        return false;
    }
    //ф-ция перемещения шашки
    private void moveBlack(int x, int y, boolean sendIt) {
        

         if (fieldArray[xBlack][yBlack].Black) {
            if (x == 7) {
                fieldArray[x][y].BlackQueen = true;
                fieldArray[x][y].Empty = false;
                fieldArray[xBlack][yBlack].Empty = true;
                fieldArray[xBlack][yBlack].Black = false;
            }
            else {
                fieldArray[x][y].Black = true;
                fieldArray[x][y].Empty = false;
                fieldArray[xBlack][yBlack].Empty = true;
                fieldArray[xBlack][yBlack].Black = false;
            }
        }
        else {
            fieldArray[x][y].BlackQueen = true;
                fieldArray[x][y].Empty = false;
                fieldArray[xBlack][yBlack].Empty = true;
                fieldArray[xBlack][yBlack].BlackQueen = false;
        }


        /*
        fieldArray[x][y].Black = true;
        fieldArray[x][y].Empty = false;
        fieldArray[xBlack][yBlack].Empty = true;
        fieldArray[xBlack][yBlack].Black = false;*/
        xBlack = x;
        yBlack = y;
        if (sendIt)
            Client.sendToServer = true;
    }

    private boolean continueEatingBlack() {
        if ((xBlack + 2) < 8 && (yBlack - 2) >= 0 && fieldArray[xBlack + 2][yBlack - 2].Empty && fieldArray[xBlack + 1][yBlack - 1].Red) {
            return true;
        }
        if ((xBlack + 2) < 8 && (yBlack + 2) < 8 && fieldArray[xBlack + 2][yBlack + 2].Empty && fieldArray[xBlack + 1][yBlack + 1].Red) {
            return true;
        }
        if (fieldArray[xBlack][yBlack].BlackQueen && (xBlack - 2) >= 0 && (yBlack - 2) >= 0 && fieldArray[xBlack - 2][yBlack - 2].Empty && (fieldArray[xBlack - 1][yBlack - 1].Red || fieldArray[xBlack - 1][yBlack - 1].RedQueen)) {
            return true;
        }
        if (fieldArray[xBlack][yBlack].BlackQueen && (xBlack - 2) >= 0 && (yBlack + 2) < 8 && fieldArray[xBlack - 2][yBlack + 2].Empty && (fieldArray[xBlack - 1][yBlack + 1].Red && fieldArray[xBlack - 1][yBlack + 1].RedQueen)) {
            return true;
        }
        return false;
    }

    private void clearCurrent() {
        for (int i = 0; i < 8; i++ ) {
            for (int j = 0; j < 8; j++) {
                fieldArray[i][j].Current = false;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    //служебный внутренний класс для хранения пар значений координат элементов, хотя можно тупо массив из 2-х integer сделать
    private class Pair {
        public Pair() {
        }
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int x;
        public int y;
    }

}