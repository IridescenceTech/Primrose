package org.iridescence.primrose.math;

import static java.lang.Math.*;

public class MathUtilities {

  /**
   * Makes a number be between two constants.
   * @param x - The number to be outputted.
   * @param lowerLimit - The lowest value.
   * @param upperLimit - The highest value.
   * @return - The clamped value.
   */
  public static double clamp(double x, double lowerLimit, double upperLimit){
    if(x < lowerLimit) {
      return lowerLimit;
    }else if(x > upperLimit) {
      return upperLimit;
    }else{
      return x;
    }
  }

  /**
   * Smoothed clamping between two constants.
   * @param edge0 - The lower value.
   * @param edge1 - The upper value.
   * @param x - The number to be smoothed.
   * @return - The smooth stepped value.
   */
  public static double smoothstep(double edge0, double edge1, double x){
    x = clamp((x - edge0) / (edge1 - edge0), 0.0, 1.0);
    return x * x * (3 - 2 * x);
  }

  /**
   * Smoothed clamping between two constants, revised by Ken Perlin.
   * @param edge0 - The lower value.
   * @param edge1 - The upper value.
   * @param x - The number to be smoothed.
   * @return - The smoother stepped value.
   */
  public static double smootherstep(double edge0, double edge1, double x){
    x = clamp((x - edge0) / (edge1 - edge0), 0.0, 1.0);
    return x * x * x * (x * (x * 6 - 15) + 10);
  }

  /**
   * Inverse smoothing
   * @param x - Parameter x.
   * @return - Inversely smoothed.
   */
  public static double inverse_smoothstep(double x){
    return 0.5 - sin(asin(1.0-2.0*x)/3.0);
  }

  /**
   * Converts degrees to radians
   * @param degrees - Degrees
   * @return - Radians
   */
  public static double degToRad(double degrees){
    return (degrees/180.0 * PI);
  }

  /**
   * Converts radians to degrees.
   * @param radians - Radians
   * @return - Degrees
   */
  public static double radToDeg(double radians){
    return (radians/PI * 180.0);
  }

  /**
   * Returns 1.0 if x is >= to the edge and 0 if it is not.
   * @param edge - The threshold value
   * @param x - Value
   * @return - Stepped or not.
   */
  public static double step(double edge, double x){
    if (x >= edge) {
      return 1.0;
    } else{
      return 0.0;
    }
  }

}
