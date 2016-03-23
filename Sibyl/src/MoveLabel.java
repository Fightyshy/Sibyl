import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MoveLabel {

    public static void main(String[] args) {
        new MoveLabel();
    }

    public MoveLabel() {
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
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public static class TestPane extends JPanel {

        protected static final int PLAY_TIME = 4000;

        private JLabel label;
        private int targetX;
        private int targetY;
        private long startTime;
        private final int startX;
        private final int startY;

        public TestPane() {
            setLayout(null);
            label = new JLabel("Testing");
            label.setSize(label.getPreferredSize());
            add(label);

            Dimension size = getPreferredSize();

            startX = 0;
            startY = 0;

            targetX = (size.width - label.getSize().width) / 2;
            targetY = (size.height - label.getSize().height) / 2;

            Timer timer = new Timer(40, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int x = label.getX();
                    int y = label.getY();
                    long duration = System.currentTimeMillis() - startTime;
                    float progress = (float)duration / (float)PLAY_TIME;
                    if (progress > 1f) {
                        progress = 1f;
                        ((Timer)(e.getSource())).stop();
                    }

                    x = startX + (int)Math.round((targetX - startX) * progress);
                    y = startY + (int)Math.round((targetY - startY) * progress);

                    label.setLocation(x, y);
                }
            });
            startTime = System.currentTimeMillis();
            timer.start();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 400);
        }
    }
}