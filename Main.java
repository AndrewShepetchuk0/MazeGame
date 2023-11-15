import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class Main extends JPanel implements KeyListener
{
    //Variables
    
    public static int pSize = 60;
    //Variable for the size fo the player
    public int x = 60, y = 60;
    //Basic variables for character location
    public BufferedImage up, down, left, right, wrld1;
    public String dir = "down";
    //creates image variable for each direction and makes a string file for the direction of the player with the default value set to up as well as the default player location
    public static JFrame main;
    //Makes new JFrame  
    String currentDir = System.getProperty("user.dir");
    String filePath = currentDir + "/src/assets/wrld1.png";
    BufferedImage background = ImageIO.read(new File(filePath));
    //Makes a new image called background and sets it to the file "wrld1.png" within it's corresponding directory
    int[] resolution = {1200, 600};
    //Array for resolution where the first number is the width of the window and the second number is the height of the window
    public static int KEY_W = KeyEvent.VK_W, KEY_S = KeyEvent.VK_S, KEY_A = KeyEvent.VK_A, KEY_D = KeyEvent.VK_D;
    
    //Basic window functions
    public Main() throws IOException
    {
        setPreferredSize(new Dimension(resolution[0], resolution[1]));
        setFocusable(true);
        addKeyListener(this);
        pImage();
    }
    //Sets the game resolution to the values determined in the previous variables, makes the game window focusable, and implements key input

    
    //Image loading
    
    public void pImage()
    {
       try{
           up = ImageIO.read(getClass().getResourceAsStream("\\assets\\1.png"));
           down = ImageIO.read(getClass().getResourceAsStream("\\assets\\2.png"));
           left = ImageIO.read(getClass().getResourceAsStream("\\assets\\3.png"));
           right = ImageIO.read(getClass().getResourceAsStream("\\assets\\4.png"));
           wrld1 = ImageIO.read(getClass().getResourceAsStream("\\assets\\wrld1.png"));
       }catch(IOException e){} 
       //sets each image variable to the corresponding image
    }
    
    //Character controller
    
    @Override
    public void keyPressed(KeyEvent e) 
    {
        switch (e.getKeyCode()) 
        {
            case Key_W:
                py -= pSize;
                dir = "up";
                //Moves player and changes current player image
                break;
            case Key_S:
                py += pSize;
                dir = "down";
                //Moves player and changes current player image
                break;
            case Key_A:
                px -= pSize;
                dir = "left";
                //Moves player and changes current player image
                break;
            case Key_D:
                px += pSize;
                dir = "right";
                //Moves player and changes current player image
                break;
        }
        if (background.getRGB(px, py) == Color.BLACK.getRGB()){
            JOptionPane.showMessageDialog(null, "You touched the wall.","Message", JOptionPane.ERROR_MESSAGE);
                main.dispose();
        }
        if (background.getRGB(px, py) == Color.RED.getRGB()){
            System.out.println("good job");
            JOptionPane.showMessageDialog(null, "Congratulations! You completed the game!","Message", JOptionPane.PLAIN_MESSAGE);
                main.dispose();
        }
        repaint();
    }
    /*
    1. Gets input from the player and updates the screen based on user input.
    2. Checks if the player touches black and closes the game if so 
    3. Checks if the player touches red and counts that as a win condition 
    */
    
    @Override
    public void keyReleased(KeyEvent e){}
    //filler method
    
    @Override
    public void keyTyped(KeyEvent e) {}
    //filler method
    
    //Graphics
    
    @Override
    public void paintComponent(Graphics g) 
    {
       pImage();
       super.paintComponent(g);
       BufferedImage image = null;
       
       switch(dir){
           case "up":
               image = up;
               break;
           case "down":
               image = down;
               break;
           case "left":
               image = left;
               break;
           case "right":
               image = right;
               break;
       }
       g.drawImage(wrld1, 0, 0, null);
       g.drawImage(image, px, py, this);   
    }
    /*
    1. Draws background image
    2. draws image corresponding to direction inputed by the player
    */
    
    //Window
    
    public static void main(String[] args) throws IOException
    {
        main = new JFrame("Maze Game");
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setContentPane(new Main());
        main.pack();
        main.setVisible(true);
        
    }
    //makes a new, visible window for the game   
