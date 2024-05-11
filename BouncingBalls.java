import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** Defines a panel containing a load of bouncing balls, 
 * each one of which is controlled by a different thread,
 * and which, once this assignment is completed,
 * should change color when the buttons are pressed.
 * 
 * Note that you are not expected to understand all the GUI code in this program, in fact you can ignore it.
 * It is possible to complete the exercise purely by implementing an Observer pattern
 * in the ColorPublisher class, to communicate colour changes from the buttons to the balls.
 */
public class BouncingBalls extends JPanel {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int BALL_SIZE_LARGE = 12;
    private static final int BALL_SIZE_SMALL = 7;
    private static final int NUM_BALLS = 500;
    private static final Color paleRed = new Color(255,150,150);
    private static final Color paleBlue = new Color(150,150,255);

    private JButton redButton = new JButton("Small & Red");
    private JButton blueButton = new JButton("Small & Blue");
    
    //The ColorPublisher - the object of this exercise. 
    //All balls will subscribe to this.
    //When we want balls to change colour, we will publish a new color via this object.
    private ColorPublisher publisher = new ColorPublisher();

    //ArrayList to hold all the balls
    private ArrayList<BallThread> balls = new ArrayList<BallThread>();

    /** 
     * Constructor: creates the panel, buttons and balls, and starts the balls bouncing.
     */
    public BouncingBalls() {
        // Create all the balls, giving each one a reference to the ColorPublisher 
        // so they can subscribe to it
        for (int i = 0; i < NUM_BALLS; i++) {
            balls.add(new BallThread(publisher));
        }
    
        // Create the buttons
        this.add(redButton);
        this.add(blueButton);

        // Call publisher to publish a pale red when the red button is clicked
        redButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                publisher.publish(paleRed);
            }
        });

        // Call publisher to publish a pale blue when the blue button is clicked
        blueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                publisher.publish(paleBlue);
            }
        });
    }

    /** The paintComponent method is called by the Java Abstract Window Toolkit
     * whenever the panel needs to be redrawn, e.g. because it has just been created, 
     * because it has just been uncovered by another window, or because
     * something has changed and we manually triggered a redraw (which the balls do when they move).
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (BallThread ball : balls) {
            ball.draw(g);
        }
    }

    /** The main() method creates and displays the panel in a new window (JFrame) */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Bouncing Balls");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(new BouncingBalls());
        frame.setVisible(true);
    }

    /** BallThread is a class to define a thread governing a single ball */
    private class BallThread implements Runnable, ColorSubscriber {
        private int x, y, dx, dy;
        private Color color = Color.GRAY;
        private int size = BALL_SIZE_LARGE;
        private ColorPublisher publisher;
        
        /** notifyColorChange should be called by the ColorPublisher when a colour change is desired.
         * (This method also changes the size of the ball, to make it easy to see when balls have
         * received a notification, and also make this exercise colourblind-friendly).
         */
        public void notifyColorChange(Color c)
        {
            color = c;
            size = BALL_SIZE_SMALL;
        }

        /** The constructor initializes ball position and speed,
         * keeps a reference to the ColorPublisher, and starts the thread.
         */
        public BallThread(ColorPublisher cp) {
            x = (int) (Math.random() * (WIDTH - size));
            y = (int) (Math.random() * (HEIGHT - size));
            dx = (int) (Math.random() * 10) + 1;
            dy = (int) (Math.random() * 10) + 1;
            publisher = cp;
            new Thread(this).start();
        }

        /** The draw method is called by the paintComponent method in the BouncingBalls class
         * to draw the ball on the screen.
         */
        public void draw(Graphics g) {
            g.setColor(color);
            g.fillOval(x, y, size, size);
        }

        /** The run method is called when the thread starts, and contains the code that the thread will run. */
        public void run() {

            //Start by sleeping for 10 milliseconds
            //This is an artificial delay to create race conditions. Do not remove it.
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Subscribe to the ColorPublisher
            publisher.addSubscriber(this);

            //Now start the main loop of the thread
            while (true) {
                //Move the ball
                x += dx;
                y += dy;

                if (x < 0 || x > WIDTH - size) {
                    dx *= -1;
                }
                if (y < 0 || y > HEIGHT - size) {
                    dy *= -1;
                }

                //Trigger a repaint of the BouncingBalls panel
                //(this does not happen immediately - it tells the main thread to call paintComponent when it next can)
                repaint();

                //Sleep for 20 milliseconds before we move the ball again
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


