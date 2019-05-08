package org.iridescence.primrose.window;

import static org.iridescence.primrose.graphics.utils.OGLUtils.oglViewport;

import org.lwjgl.glfw.GLFWFramebufferSizeCallback;

/**
 * Handles Framebuffer Resizing and resets the viewport.
 */
public class FramebufferResizeCallback extends GLFWFramebufferSizeCallback {

  /**
   * GLFW Callback - See GLFW Documentation for parameter data.
   */
  public void invoke(long window, int width, int height) {
    oglViewport(width, height);
  }
}

