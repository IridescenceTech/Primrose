package org.iridescence.primrose.window;


import static org.iridescence.primrose.graphics.utils.OGLUtils.oglViewport;

import org.lwjgl.glfw.GLFWWindowSizeCallback;

/**
 * Handles window resizing and changes the viewport.
 */
public class WindowResizeCallback extends GLFWWindowSizeCallback {

  public static int w, h;
  public static boolean needsUpdate;

  /**
   * GLFW Callback - See GLFW Documentation for parameter data.
   */
  public void invoke(long window, int width, int height) {
    oglViewport(width, height);
    w = width;
    h = height;
    needsUpdate = true;
  }
}
