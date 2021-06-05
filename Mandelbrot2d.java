import java.util.ArrayList;
import java.awt.Color;

// Luc Capaldi
// Last Modified: June 1, 2021
// Mandelbrot2d: Class for the computation of the 2d Mandelbrot set using the Escape Time Algorithm.
// The Mandelbrot set, M, is a set of complex numbers bounded from (-2, 2) on the complex plane.
//

public class Mandelbrot2d 
{
    // Instance variables
    private int[] screenDims;   // screen dimensions
    private int[] screenDimsX;  // screen width
    private int[] screenDimsY;  // screen height
    private double[] xScale;       // x-scale of Mandelbrot plot
    private double[] yScale;       // y-scale of Mandelbrot plot
    private int nMax;              // max number of Mandelbrot iterations

    // Constructors:
    /**
    * Default constructor  
    */
    public Mandelbrot2d()
    {
        screenDimsX = new int[]{0, 1920}; 
        screenDimsY = new int[]{0, 1080};
        screenDims = new int[]{screenDimsX[1], screenDimsY[1]};
        xScale = new double[]{-2.5, 1};
        yScale = new double[]{-1, 1};
        nMax = 1000;
    }

    // Methods
    // Accessors:
    /**
     * Return the screen dimensions as an integer array
     * @return screenDims integer array of screen dimensions
     */
    public int[] getScreenDims()
    {
        return screenDims;
    }
   
    /**
     * Computes image of Mandelbrot set
     * @return imageRGB 2D array list of Color objects representing the pixels within the image
     */
    public ArrayList<Color> computeImage()
    {
        // Declare array list of RGB objects and create map object
        ArrayList<Color> imageRGB = new ArrayList<Color>();
        ColorMap map = new ColorMap(nMax);

        // Iterate through all pixels
        for (int i = 1; i <= screenDimsX[1]; i++)
        {
            for (int j = 1; j <= screenDimsY[1]; j++)
            {
                // Compute number of iterations for pixel
                int[] pixel = new int[]{i, j};
                int numIterations = computePixel(pixel);
                Color color;

                // If max iterations exceeded, color black
                if (numIterations == nMax)
                    color = new Color(0, 0, 0);
                else
                    color = map.pickColor(pixel, numIterations);
                imageRGB.add(color);
            }
        }
        return imageRGB;
    }
    
    
    /**
     * Cubically interpolate color based on pixel input from control points 
     * @param n number of Mandelbrot iterations before escaping set 
     * @param pixel integer array containing pixel coordinates
     * @return rgb color object containing output color 
     */
    public Color colorMap(int[] pixel, int n)
    {
        // Colors are cubically interpolated from five control points defined in range [0, 2) with corresponding colors from [0, 255]
        // See: https://stackoverflow.com/questions/16500656/which-color-gradient-is-used-to-color-mandelbrot-in-wikipedia
      
        //int[] pixelScaled = scalePixel(pixel, new double[]{0, 1}, new double[]{0, 1});
        
        // TEMP:
        Color rgb;
        if (n == nMax)
            rgb = new Color(0,0,0);
        else
            rgb = new Color(255, 255, 255);
        return rgb;

        //switch()...
    }
 
    /**
     * Compute number of iterations for a given pixel using the Escape Time Algorithm 
     * @param param pixel integer array containing pixel coordinates
     * @return n number of iterations before escaping set
     */
    public int computePixel(int[] pixel)
    {
     // Starting values
     double x2 = 0;
     double y2 = 0;
     double x = 0;
     double y = 0;

     int n = 0;
    
     // Scale pixels via linear interpolation
     double[] pixelScaled;
     double iScaled = linInterp(screenDimsX, xScale, (double)pixel[0]);
     double jScaled = linInterp(screenDimsY, yScale, (double)pixel[1]);
     pixelScaled = new double[]{iScaled, jScaled};

     // Compute number of iterations
     while ((x2 + y2 <= 4) && (n < nMax))
     {
         y = (2 * x * y) + pixelScaled[1];
         x = x2 - y2 + pixelScaled[0];
         x2 = x * x;
         y2 = y * y;
         n++;
     }
     return n;
    }

    ///**
    // * Scale input pixel to fit desired plotting dimensions using linear interpolation
    // * @param pixel integer array containing original pixel coordinates
    // * @param xScale desired plotting scale along x-dimension 
    // * @param yScale desired plotting scale along y-dimension 
    // * @return pixelScaled double array containing scaled pixel coordinates 
    // */
    //public double[] scalePixel(int[] pixel, double[] xScale, double[] yScale)
    //{
    //    double[] pixelScaled;

    //    

    //    // Perform linear interpolation
    //    double iScaled = (xScale[0] + (pixel[0] - 0) * ((double)(xScale[1] - xScale[0]) / (screenDims[0] - 0)));
    //    double jScaled = yScale[0] + (pixel[1] - 0) * ((double)(yScale[1] - yScale[0]) / (screenDims[1] - 0));

    //    // Return result
    //    pixelScaled = new double[]{iScaled, jScaled};
    //    return pixelScaled;
    //}

    /**
     * Performs a linear interpolation based on the input values 
     * @param xRange interpolation range along x-direction
     * @param yRange interpolation range along y-direction
     * @param x position along x-direction
     * @return y postition along y-direction
     */
    public static double linInterp(int[] xRange, double[] yRange, double x)
    {
        double y;
        y = (yRange[0] + (x - xRange[0]) * ((double)(yRange[1] - yRange[0]) / (xRange[1] - xRange[0])));
        return y;
    }
}

