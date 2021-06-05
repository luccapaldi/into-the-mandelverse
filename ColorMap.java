import java.util.ArrayList;
import java.awt.Color;

// Luc Capaldi
// Last Modified: June 3, 2021
// ColorMap: Class to manage various colormapping techniques for fractal plotting 

public class ColorMap
{
    // Instance variables
    int nMax;                      // max number of Mandelbrot iterations
    double[] screenDims;           // screen dimensions (width by height) 
    double[] screenMagRange;       // range of screen magnitudes
    double[] lerpRange;            // bounding range for color lerp
    double[] nRange;               // range of values for Mandelbrot iterations
    ArrayList<int[]> colorMap;     // array list containing control point colors

    /**
     * Default constructor
     */
    public ColorMap(int iterationsMax)
    {
        nMax = iterationsMax;
        this.nRange = new double[]{0, (double)nMax};
        this.screenDims = new double[]{1920, 1080};
        this.lerpRange = new double[]{0, 1};

        // Input pixel coords are pre-scaled form [0, 1), so mag range is known       
        this.screenMagRange = new double[]{0, (Math.sqrt(Math.pow(screenDims[0], 2) + Math.pow(screenDims[1], 2)))};


        // Hard-coded control points
        // See: https://stackoverflow.com/questions/16500656/which-color-gradient-is-used-to-color-mandelbrot-in-wikipedia
        colorMap = new ArrayList<int[]>();
        colorMap.add(new int[]{0, 7, 100});
        colorMap.add(new int[]{32, 107, 203});
        colorMap.add(new int[]{237, 255, 255});
        colorMap.add(new int[]{255, 170, 0});
        colorMap.add(new int[]{0, 2, 0});
    }

    // Methods
    /**
     * Pick appropriate color for scaled coordinates via linear interpolation between control points
     * @param pixel integer array containing unmodified pixel coordinates
     * @param n number of Mandelbrot iterations before escaping set 
     * @param nMax maximum number of Mandelbrot iterations
     * @return c color object containing output color 
     */
    public Color pickColor(int[] pixel, int n)
    {
        int[] c0;  // starting point of interpolation
        int[] c1;  // ending point of interpolation

        // Scale n to range between [0, 1)
        double nScaled = linInterp(nRange, lerpRange, n);

        // Store lerp range based on pixel vector magnitude 
        double[] iRange; 

        // Find correct interpolation points based on range
        if (nScaled <= 0.16)
        { 
            iRange = new double[]{0.0, 0.16};
            c0 = colorMap.get(0);
            c1 = colorMap.get(1);
        }
        else if (nScaled > 0.16 && nScaled <= 0.42)
        {
            iRange = new double[]{0.161, 0.42};
            c0 = colorMap.get(1);
            c1 = colorMap.get(2);
        }
        else if (nScaled > 0.42 && nScaled <= 0.6425)
        {
            iRange = new double[]{0.421, 0.6425};
            c0 = colorMap.get(2);
            c1 = colorMap.get(3);
        }
        else if (nScaled > 0.6425 && nScaled <= 0.8575)
        {
            iRange = new double[]{0.64251, 0.8575};
            c0 = colorMap.get(3);
            c1 = colorMap.get(4);
        }
        else
        {
            iRange = new double[]{0.85751, 1.0};
            c0 = colorMap.get(4);
            c1 = colorMap.get(0);
        }

        // Calculate RGB values via linear interpolation
        double r = linInterp(lerpRange, new double[]{c0[0], c1[0]}, nScaled);
        double g = linInterp(lerpRange, new double[]{c0[1], c1[1]}, nScaled);
        double b = linInterp(lerpRange, new double[]{c0[2], c1[2]}, nScaled);
        Color c = new Color((int)r, (int)g, (int)b);
        return c;
    }

    /**
     * Performs a linear interpolation based on the input values 
     * @param xRange interpolation range along x-direction
     * @param yRange interpolation range along y-direction
     * @param x position along x-direction
     * @return y postition along y-direction
     */
    public static double linInterp(double[] xRange, double[] yRange, double x)
    {
        double y = (yRange[0] + (x - xRange[0]) * ((yRange[1] - yRange[0]) / (xRange[1] - xRange[0])));
        return y;
    }


    ///**
    // * Performs a cubic interpolation based on the input values 
    // *
    // */

    //public static void cubicInterp()
    //{
    //    // See: https://www.paulinternet.nl/?page=bicubic
    //    
    //}

    ///**
    // * Performs a log interpolation based on the input values 
    // *
    // */
    //public Color logInterpolation
    //{
    //}
}
