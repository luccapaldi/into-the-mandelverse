import java.util.ArrayList;
import java.awt.Color;

// Luc Capaldi
// Last Modified: June 3, 2021
// ColorMap: Class to manage various colormapping techniques for fractal plotting 

public class ColorMap
{
    // Instance variables
    int[] xRange;               // screen dimensions (width by height) 
    double[] yRange;            // bounding range for cubic interp of colors
    ArrayList<int[]> colorMap;  // array list containing control point colors

    /**
     * Default constructor
     */
    public ColorMap()
    {
        this.xRange = new int[]{1920, 1080};
        this.yRange = new double[]{0, 1};

        colorMap = new ArrayList<int[]>();
        colorMap.add(new int[]{0, 7, 100});
        colorMap.add(new int[]{32, 107, 203});
        colorMap.add(new int[]{237, 255, 255});
        colorMap.add(new int[]{255, 170, 0});
        colorMap.add(new int[]{0, 2, 0});
    }

    ///**
    // * Constructor that accepts screen dimensions
    // */
    //public ColorMap(int[] screenDims)
    //{
    //    int[] xRange = new int[]{0, 1};
    //    double[] yRange = new double[]{screenDims[0], screenDims[1]}; 
    //}

    // Methods
    
    /**
     * Pick appropriate color for scaled coordinates via cubic interpolation between control points
     * @param pixel integer array containing pixel coordinates scaled between [0, 1)
     * @param n number of Mandelbrot iterations before escaping set 
     * @return c color object containing output color 
     */
    public Color pickColor(int[] pixel, int n)
    {
        // See: https://stackoverflow.com/questions/16500656/which-color-gradient-is-used-to-color-mandelbrot-in-wikipedia
        int[] c0;  // starting point of interpolation
        int[] c1;  // ending point of interpolation
        double[] magRange; // range for interpolation of pixel ragnitude

        // Compute magnitude of pixel vector and scale between [0,1) via lerp
        int mag = (int)Math.sqrt(Math.pow(pixel[0], 2) + Math.pow(pixel[1], 2));
        double magScaled = Mandelbrot2d.linInterp(xRange, yRange, mag);

        // Find correct interpolation points based on range
        if (magScaled <= 0.16)
        { 
            magRange = new double[]{0.0, 0.16};
            c0 = colorMap.get(0);
            c1 = colorMap.get(1);
        }
        else if (magScaled > 0.16 && magScaled <= 0.42)
        {
            magRange = new double[]{0.161, 0.42};
            c0 = colorMap.get(1);
            c1 = colorMap.get(2);
        }
        else if (magScaled > 0.42 && magScaled <= 0.6425)
        {
            magRange = new double[]{0.421, 0.6425};
            c0 = colorMap.get(2);
            c1 = colorMap.get(3);
        }
        else if (magScaled > 0.6425 && magScaled <= 0.8575)
        {
            magRange = new double[]{0.64251, 0.8575};
            c0 = colorMap.get(3);
            c1 = colorMap.get(4);
        }
        else
        {
            magRange = new double[]{0.85751, 1.0};
            c0 = colorMap.get(4);
            c1 = colorMap.get(0);
        }

        // Calculate RGB values via linear interpolation
        double r = linInterp(magRange, new int[]{c0[0], c1[0]}, magScaled);
        double g = linInterp(magRange, new int[]{c0[1], c1[1]}, magScaled);
        double b = linInterp(magRange, new int[]{c0[2], c1[2]}, magScaled);
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
    public static double linInterp(double[] xRange, int[] yRange, double x)
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
