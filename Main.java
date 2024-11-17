import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class Main extends JPanel implements KeyListener {

    // Constants
    private static final int PLAYER_SIZE = 60;
    private static final int[] RESOLUTION = {1200, 600};
    private static final int KEY_W = KeyEvent.VK_W, KEY_S = KeyEvent.VK_S, KEY_A = KeyEvent.VK_A, KEY_D = KeyEvent.VK_D;

    // Variables
    private int playerX = 60, playerY = 60;
    private BufferedImage up, down, left, right, background;
    private String direction = "down";
    private static JFrame mainFrame;

    private final int colorBlackRGB = Color.BLACK.getRGB();
    private final int colorRedRGB = Color.RED.getRGB();

    // Constructor
    public Main() throws IOException {
        setPreferredSize(new Dimension(RESOLUTION[0], RESOLUTION[1]));
        setFocusable(true);
        addKeyListener(this);
        loadImages();
    }

    // Load images once during initialization
    private void loadImages() throws IOException {
        up = ImageIO.read(getClass().getResourceAsStream("/assets/1.png"));
        down = ImageIO.read(getClass().getResourceAsStream("/assets/2.png"));
        left = ImageIO.read(getClass().getResourceAsStream("/assets/3.png"));
        right = ImageIO.read(getClass().getResourceAsStream("/assets/4.png"));
        background = ImageIO.read(getClass().getResourceAsStream("/assets/wrld1.png"));
    }

    // Handle key presses
    @Override
    public void keyPressed(KeyEvent e) {
        int prevX = playerX, prevY = playerY;

        switch (e.getKeyCode()) {
            case KEY_W:
                playerY -= PLAYER_SIZE;
                direction = "up";
                break;
            case KEY_S:
                playerY += PLAYER_SIZE;
                direction = "down";
                break;
            case KEY_A:
                playerX -= PLAYER_SIZE;
                direction = "left";
                break;
            case KEY_D:
                playerX += PLAYER_SIZE;
                direction = "right";
                break;
        }

        // Bounds checking
        playerX = Math.max(0, Math.min(playerX, RESOLUTION[0] - PLAYER_SIZE));
        playerY = Math.max(0, Math.min(playerY, RESOLUTION[1] - PLAYER_SIZE));

        // Collision detection
        if (background.getRGB(playerX, playerY) == colorBlackRGB) {
            JOptionPane.showMessageDialog(null, "You touched the wall.", "Game Over", JOptionPane.ERROR_MESSAGE);
            mainFrame.dispose();
        } else if (background.getRGB(playerX, playerY) == colorRedRGB) {
            JOptionPane.showMessageDialog(null, "Congratulations! You completed the game!", "Victory", JOptionPane.PLAIN_MESSAGE);
            mainFrame.dispose();
        }

        // Repaint only if the position has changed
        if (prevX != playerX || prevY != playerY) {
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    // Paint graphics
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);

        BufferedImage playerImage = switch (direction) {
            case "up" -> up;
            case "down" -> down;
            case "left" -> left;
            case "right" -> right;
            default -> down;
        };

        g.drawImage(playerImage, playerX, playerY, PLAYER_SIZE, PLAYER_SIZE, null);
    }

    // Main method to start the game
    public static void main(String[] args) throws IOException {
        mainFrame = new JFrame("Maze Game");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setContentPane(new Main());
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
