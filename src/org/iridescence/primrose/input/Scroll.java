package org.iridescence.primrose.input;

import org.lwjgl.glfw.GLFWScrollCallback;

/**
 * Handler for Mouse Scrolling
 */
public class Scroll extends GLFWScrollCallback {

  private static double xoff, yoff;

  /**
   * GLFW Callback - See GLFW Documentation for parameter data.
   */
  public void invoke(long window, double xoffset, double yoffset) {
    xoff = xoffset;
    yoff = yoffset;
  }

  /**
   * Retrieves the X Offset of the Scroll Wheel.
   *
   * @return - Returns a double of the scroll wheel's x offset.
   */
  public static double getXoff() {
    return xoff;
  }

  /**
   * Retrieves the Y Offset of the Scroll Wheel.
   *
   * @return - Returns a double of the scroll wheel's y offset.
   */
  public static double getYoff() {
    return yoff;
  }
}
