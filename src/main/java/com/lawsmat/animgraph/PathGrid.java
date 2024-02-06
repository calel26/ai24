package com.lawsmat.animgraph;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PathGrid {
    private final JFrame frame;

    public PathGrid() {
        frame = new JFrame("Path Finding");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(frame.getSize());
        frame.add(new TheGrid(frame.getSize()));
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String... argv) {
        new PathGrid();
    }

    public static class TheGrid extends JPanel implements Runnable, MouseListener  {

        private Thread animator;
        Dimension d;
        String str = "";
        int xPos = 0;
        int yPos = 0;
        int startSpot = 0;
        int stopSpot = 4;
        int fontSize = 20;
        int tick = 0;
        int step = 0;
        boolean play = false;
        ArrayList<Integer> path = new ArrayList<>();

        CreateGraph grd = new CreateGraph();

        public TheGrid (Dimension dimension) {
            setSize(dimension);
            setPreferredSize(dimension);
            addMouseListener(this);
            addKeyListener(new TAdapter());
            setFocusable(true);
            d = getSize();


            //for animating the screen - you won't need to edit
            if (animator == null) {
                animator = new Thread(this);
                animator.start();
            }
            setDoubleBuffered(true);
        }



        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(Color.black);
            g2.fillRect(0, 0,(int)d.getWidth() , (int)d.getHeight());

            int startx = 10;
            int starty = 100;
            Integer at = null;
            if(step < path.size()) {
                at = path.get(step);
            }
            for(int r = 0; r < 6; r++){
                for(int c = 0; c < 8; c++){
                    g2.setColor(new Color(30,30,30));
                    g2.drawRect(startx, starty, 50 ,50);
                    if(grd.grid[r][c]==grd.startIdx){
                        g2.setColor(Color.red);
                    }
                    if(grd.grid[r][c]==grd.endIdx){
                        g2.setColor(Color.green);
                    }

                    if(grd.grid[r][c]==-1){
                        g2.setColor(Color.gray);
                    }

                    if(at != null && grd.grid[r][c] == at) {
                        g2.setColor(Color.PINK);
                    }

                    g2.fillRect(startx, starty, 50 ,50);
                    startx += 50;
                }
                startx = 10;
                starty += 50;
            }

            if(tick % 20 == 0 && play) {
                step++;
                if(step == path.size()) {
                    step = 0;
                }
            }

            g2.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
            g2.drawString("String " + str,20,40);
            if(tick == 0) {
                recalcPath();
            }
            tick++;
        }

        private void recalcPath() {
            path = grd.graph.astar(grd.startIdx, grd.endIdx, e ->
                    (int) Math.sqrt(Math.pow(e.point.x - grd.endPt.x, 2) + Math.pow(e.point.y - grd.endPt.y, 2)),
                    grd.startPt);
        }

        public void mousePressed(MouseEvent e) {
            xPos = e.getX();
            yPos = e.getY();

            int startx = 10;
            int starty = 100;

            xPos = (xPos - startx)/50;

            yPos = (yPos - starty)/50;

            System.out.println(" c " + xPos + " r " + yPos );
            //System.out.println(" c " + xPos + " r " + yPos + " " + grd.grid[yPos][xPos]);


        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
            for(int r = 0; r < grd.rows; r++) {
                for(int c = 0; c < grd.cols; c++) {
                    var hitbox = new int[]{10 + 50 * c, 100 + 50 * r};
                    if(hitbox[0] < e.getX() && hitbox[0] + 50 > e.getX()
                        && hitbox[1] < e.getY() && hitbox[1] + 50 > e.getY()) {
                        var pt = new Point(c, r);
                        if(grd.walls.contains(pt)) {
                            grd.walls.remove(pt);
                        } else {
                            grd.walls.add(pt);
                        }

                        grd.buildGraph();
                        recalcPath();
                        break;
                    }
                }
            }
        }

        private class TAdapter extends KeyAdapter {

            public void keyReleased(KeyEvent e) {
                int keyr = e.getKeyCode();

            }

            public void keyPressed(KeyEvent e) {
                play = !play;
            }
        }//end of adapter

        public void run() {
            long beforeTime, timeDiff, sleep;
            beforeTime = System.currentTimeMillis();
            int animationDelay = 37;
            long time = System.currentTimeMillis();
            while (true) {// infinite loop
                // spriteManager.update();
                repaint();
                try {
                    time += animationDelay;
                    Thread.sleep(Math.max(0, time - System.currentTimeMillis()));
                } catch (InterruptedException e) {
                    System.out.println(e);
                } // end catch
            } // end while loop
        }// end of run

    }//end of class
}