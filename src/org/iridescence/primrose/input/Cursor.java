package org.iridescence.primrose.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

/** A Handler For The Cursor Position on the window screen. */
public class Cursor extends GLFWCursorPosCallback {

  private static double x, y;

  /**
   * Retrieves the x position of the mouse cursor.
   *
   * @return - Returns a double (should be integer) of the mouse's X coordinates.
   */
  public static double getX() {
    return x;
  }

  /**
   * Retrieves the y position of the mouse cursor.
   *
   * @return - Returns a double (should be integer) of the mouse's Y coordinates.
   */
  public static double getY() {
    return y;
  }

  /** GLFW Callback - See GLFW Documentation for parameter data. */
  public void invoke(long window, double xpos, double ypos) {
    x = xpos;
    y = ypos;
  }
}
