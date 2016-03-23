import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PacMan101 {

    public static void main(String[] args) {
        new PacMan101();
    }

    public PacMan101() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new MazePane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class MazePane extends JPanel {

        private JLabel pacMan;
        private JLabel ghost;

        public MazePane() {
        	pacMan = new JLabel(new String("test"));
            ghost = new JLabel(new String("testing"));


            setLayout(new GridLayout(8, 8));
            add(pacMan);

            for (int index = 1; index < (8 * 8) - 1; index++) {
                add(new JPanel() {

                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(32, 32);
                    }

                });
            }

            //add(ghost);

            Timer timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    move(pacMan);
                    //move(ghost);
                    revalidate();
                    repaint();
                }

                protected void move(Component obj) {
                    int order = getComponentZOrder(obj);
                    int row = order / 8;
                    int col = order - (row * 8);

                    boolean moved = false;
                    while (!moved) {
                        int direction = (int) (Math.round(Math.random() * 3));
                        int nextRow = row;
                        int nextCol = col;
                        switch (direction) {
                            case 0:
                                nextRow--;
                                break;
                            case 1:
                                nextCol++;
                                break;
                            case 2:
                                nextRow++;
                                break;
                            case 3:
                                nextCol--;
                                break;
                        }

                        if (nextRow >= 0 && nextRow < 8 && nextCol >= 0 && nextCol < 8) {
                            row = nextRow;
                            col = nextCol;
                            moved = true;
                        }
                    }

                    order = (row * 8) + col;
                    setComponentZOrder(obj, order);
                    System.out.println(getComponentZOrder(obj));
                    System.out.println(row +" "+col);
                }
            });
            timer.start();
        }

    }

}