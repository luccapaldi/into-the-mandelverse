import java.util.ArrayList;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

// Luc Capaldi
// Last Edited: June 3, 2021
// Mandelbrot: Compute and display the 2D Mandelbrot set. 

// ToDo:
//  - Dynamic plotting of RGB values within a Java interface (not images)
//  - Keyboard control to render new location/new resolution... with help screen like RatPoisonWM
//  - Ability to zoom in real time
//  - Add color mapping (maybe in class?)
//  - Config file

public class Mandelbrot
{
    public static void main(String[] args)
    {
        // Create Mandelbrot set object with default settings
        Mandelbrot2d set = new Mandelbrot2d();

        // Compute image
        ArrayList<Color> imageRGB = new ArrayList<Color>();
        imageRGB = set.computeImage();

        // Create BufferedImage object and paint all rgb values to it
        // See: https://stackoverflow.com/questions/15322329/how-to-display-an-image-represented-by-r-g-b-values-separately-in-3-matrices-in
        int screenWidth = set.getScreenDims()[0];
        int screenHeight = set.getScreenDims()[1];

        BufferedImage image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);

        int count = 0;
        for (int i = 0; i < screenWidth; i++)
        {
            for (int j = 0; j < screenHeight; j++)
            {
                // Retrieve and assign corresponding RGB color 
                Color c = imageRGB.get(count);
                image.setRGB(i, j, imageRGB.get(count).getRGB());
                count++;
            }
        }
        
        // Display image in JFrame
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(image)));
        frame.setPreferredSize(new java.awt.Dimension(screenWidth, screenHeight));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
