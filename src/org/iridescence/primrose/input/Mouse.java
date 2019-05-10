package org.iridescence.primrose.input;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

/** A Handler for mouse button clicks (scroll wheel is separate). */
public class Mouse extends GLFWMouseButtonCallback {

  private static final boolean[] buttons = new boolean[2048];

  /**
   * A function to retrieve whether or not a button is being pressed or held down or not.
   *
   * @param button - The button to be checked. Please use GLFW to specify the button such as
   *     GLFW_MOUSE_BUTTON_1
   * @return - Returns a boolean of whether or not the button has been pressed.
   */
  public static boolean isButtonDown(int button) {
    return buttons[button];
  }

  /** GLFW Callback - See GLFW Documentation for parameter data. */
  public void invoke(long window, int button, int action, int mods) {
    buttons[button] = action != GLFW_RELEASE;
  }
}
