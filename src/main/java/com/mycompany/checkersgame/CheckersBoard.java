package com.mycompany.checkersgame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;

class MyThread implements Runnable {
    private Handler handler;
    public MyThread(Handler handler_) {
        handler = handler_;
    }

    public void run() {

        System.out.printf("%s started... \n", Thread.currentThread().getName());
        try {

            Client client = new Client("127.0.0.1", 9090, handler);
        } catch (Exception e) {
            System.out.println("Thread has been interrupted");
        }
        System.out.printf("%s finished... \n", Thread.currentThread().getName());
    }
}

class BoardThread implements Runnable {
    private CheckersBoard chBrd;
    public void run() {

        System.out.printf("%s started... \n", Thread.currentThread().getName());
        try {
            chBrd = new CheckersBoard();
            chBrd.setVisible(true);
            chBrd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        } catch (Exception e) {
            System.out.println("Thread has been interrupted");
        }
        System.out.printf("%s finished... \n", Thread.currentThread().getName());
    }

    public Handler getHandler() {
        return chBrd.getHandler();
    }

    public CheckersBoard getChBrd() {
        return chBrd;
    }
}

class BoardThread2 implements Runnable {
    private CheckersBoard chBrd;

    public BoardThread2(CheckersBoard checkersBoard) {
        this.chBrd = checkersBoard;
    }

    public void run() {

        System.out.printf("%s started... \n", Thread.currentThread().getName());
        try {
            while (true) {
                Thread.sleep(1000);
                chBrd.revalidate();
                chBrd.repaint();
            }
//            chBrd = new CheckersBoard();
//            chBrd.setVisible(true);
//            chBrd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        } catch (Exception e) {
            System.out.println("Thread has been interrupted");
        }
        System.out.printf("%s finished... \n", Thread.currentThread().getName());
    }

    public void setChBrd(CheckersBoard chBrd) {
        this.chBrd = chBrd;
    }

    //    public Handler getHandler() {
//        return chBrd.getHandler();
//    }
//
//    public CheckersBoard getChBrd() {
//        return chBrd;
//    }
}

public class CheckersBoard extends JFrame implements ActionListener, MouseListener {
    private Handler handler;
    private JLabel title;
    private Field [][] fieldArray;
    private JPanel board;

    public Handler getHandler() {
        return handler;
    }

    public static void main(String[] args) throws InterruptedException {

        BoardThread bt = new BoardThread();
        Thread board = new Thread(bt,"BoardThread");
        board.start();

        board.join();
        Handler handler = bt.getHandler();
        CheckersBoard checkersBoard = bt.getChBrd();

        //Server server= new Server();
        Thread client1 = new Thread(new MyThread(handler),"MyThread1");
        client1.start();

        Thread board2 = new Thread(new BoardThread2(checkersBoard),"BoardThread2");
        board2.start();


//        Thread client2 = new Thread(new MyThread(handler),"MyThread2");
//        client2.start();


//        Client client = new Client("127.0.0.1", 9090);
//
//        Client client2 = new Client("127.0.0.1", 9090);

//        CheckersBoard chBrd = new CheckersBoard();
//        chBrd.setVisible(true);
//        chBrd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }



    public CheckersBoard() throws IOException {
        Container cp = getContentPane();
        JPanel boardParent = new JPanel();
        boardParent.setLayout(new GridBagLayout());              
        board = new JPanel();
        board.addMouseListener(this);
        board.setPreferredSize(new Dimension(400, 400));
        board.setMinimumSize(new Dimension(400, 400));
        board.setMaximumSize(new Dimension(400, 400));

        boardParent.setPreferredSize(new Dimension(400, 400));
        boardParent.setMinimumSize(new Dimension(400, 400));
        boardParent.setMaximumSize(new Dimension(400, 400));

        board.setLayout(new GridLayout(8,8));

        /*
        for (int i = 0; i < 64; i++)
        {
            JPanel square =  new Field();
            board.add( square );
            
            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground( i % 2 == 0 ? Color.white : Color.blue );
            else
                square.setBackground( i % 2 == 0 ? Color.blue : Color.white );
        }*/



        fieldArray = new Field[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++ ){                
                fieldArray[i][j] = new Field();                
                board.add(fieldArray[i][j]);
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        if (i < 3) {
                            fieldArray[i][j].Black = true;
                            fieldArray[i][j].Empty = false;
                        }
                        if (i > 4) {
                            fieldArray[i][j].Red = true;
                            fieldArray[i][j].Empty = false;
                        }
                        fieldArray[i][j].setBackground(Color.white);
                    }
                    else {
                        fieldArray[i][j].setBackground(Color.blue);
                    }
                    //fieldArray[i][j].setBackground( j % 2 == 0 ? Color.white : Color.blue );
                }
                else {
                    if (j % 2 == 0) {
                        fieldArray[i][j].setBackground(Color.blue);
                    }
                    else {
                        if (i < 3) {
                           fieldArray[i][j].Black = true;
                            fieldArray[i][j].Empty = false;
                        }
                        if (i > 4) {
                            fieldArray[i][j].Red = true;
                            fieldArray[i][j].Empty = false;
                        }
                        fieldArray[i][j].setBackground(Color.white);
                    }
                    //fieldArray[i][j].setBackground( j % 2 == 0 ? Color.blue : Color.white );
                }
            }
        }

        boardParent.add(board);

        JPanel top = new JPanel();
        title = new JLabel("Checkers Board");
        top.add(title);
        JPanel down = new JPanel();
        JButton start = new JButton("START");
        down.add(start);
        
        cp.add(top, BorderLayout.NORTH);
        cp.add(boardParent, BorderLayout.CENTER);
        cp.add(down, BorderLayout.SOUTH);
        setBounds(50, 50, 500, 600);

        handler = new Handler(fieldArray);
//        handler.clickHandler(5,5);
//        handler.clickHandler(4,6);
//        handler.SetyBlack(5);
//        handler.SetBlackMove(true);
//        handler.SetRedMove(false);
//        handler.SetBlackMove(true);
//        handler.moveRed(4,1);
        handler.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseClicked(MouseEvent e) {       
    }

    public void mousePressed(MouseEvent e) {
        int y = e.getX() / 50;
        int x = e.getY() / 50;
        //fieldArray[x][y] = new RedChecker();
        title.setText(x + " " + y);        

        /*for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                fieldArray[i][j].Black = false;
                fieldArray[i][j].Current = false;
            }
        }
        fieldArray[x][y].Black = true;
        fieldArray[x][y].Current = true;*/
        try {
            handler.clickHandler(x, y, true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        repaint();

    }

    public void mouseReleased(MouseEvent e) {
        
    }

    public void mouseEntered(MouseEvent e) {
        
    }

    public void mouseExited(MouseEvent e) {
        
    }
}