package org.iridescence.primrose.input;


import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;

import org.lwjgl.glfw.GLFWKeyCallback;

/**
 * Handler for Keyboard Events.
 */
public class Keyboard extends GLFWKeyCallback {

  private static int[] keys = new int[65535];

  /**
   * GLFW Callback - See GLFW Documentation for parameter data.
   */
  public void invoke(long window, int key, int scancode, int action, int mods) {
    if(key > 0 && key < 65535) {
      keys[key] = action;
    }
  }

  /**
   * A function to retrieve whether or not a key is being pressed or held down or not.
   *
   * @param keycode - A keycode from the GLFW library, like GLFW_KEY_A.
   * @return - Returns the boolean state of the key.
   */
  public static boolean isKeyDown(int keycode) {
    if(keycode > 0 && keycode < 65535) {
      return keys[keycode] != GLFW_RELEASE;
    }else{
      return false;
    }
  }

  /**
   * A function to retrieve whether or not a key is being held down.
   *
   * @param keycode - A keycode from the GLFW library, like GLFW_KEY_A.
   * @return - Returns the boolean state of the key.
   */
  public static boolean isKeyHeld(int keycode) {
    if(keycode > 0 && keycode < 65535) {
      return keys[keycode] == GLFW_REPEAT;
    }else{
      return false;
    }
  }

  /**
   * A function to retrieve whether or not a key is pressed.
   *
   * @param keycode - A keycode from the GLFW library, like GLFW_KEY_A.
   * @return - Returns the boolean state of the key.
   */
  public static boolean isKeyPressed(int keycode) {
    if(keycode > 0 && keycode < 65535) {
      return keys[keycode] == GLFW_PRESS;
    }else{
      return false;
    }
  }

  /**
   * A function to retrieve whether or not a key is released.
   *
   * @param keycode - A keycode from the GLFW library, like GLFW_KEY_A.
   * @return - Returns the boolean state of the key.
   */
  public static boolean isKeyUp(int keycode) {
    if(keycode > 0 && keycode < 65535) {
      return keys[keycode] == GLFW_RELEASE;
    }else{
      return false;
    }
  }

}