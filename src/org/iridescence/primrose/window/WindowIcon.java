package org.iridescence.primrose.window;


import static org.lwjgl.stb.STBImage.stbi_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.system.MemoryStack;

/**
 * WindowIcon is a class which describes an icon file (.ico) in a GLFW readable format.
 */
public class WindowIcon {

  /**
   * Gets the ByteBuffer Image
   * @return - Returns the ByteBuffer Image Data
   */
  public ByteBuffer get_image() {
    return image;
  }

  /**
   * Gets the width of the icon
   * @return - Returns the width of the Icon
   */
  public int get_width() {
    return width;
  }

  /**
   * Gets the height of the icon
   * @return - Returns the height of the Icon
   */
  public int get_height() {
    return height;
  }

  private ByteBuffer image;
  private int width, height;

  /**
   * Create a new Window Icon
   * @param width - Icon width
   * @param height - Icon height
   * @param image - Icon Data
   */
  public WindowIcon(int width, int height, ByteBuffer image) {
    this.image = image;
    this.height = height;
    this.width = width;
  }

  /**
   * Loads a Window Icon given an image
   * @param path - Path to the Icon file
   * @return - Returns the Window Icon
   */
  public static WindowIcon load_image(String path) {
    ByteBuffer image;
    int width, height;
    try (MemoryStack stack = MemoryStack.stackPush()) {
      IntBuffer comp = stack.mallocInt(1);
      IntBuffer w = stack.mallocInt(1);
      IntBuffer h = stack.mallocInt(1);

      image = stbi_load(path, w, h, comp, 4);
      if (image == null) {
        throw new Error("STBI could not load image resources.");
      }
      width = w.get();
      height = h.get();
    }
    return new WindowIcon(width, height, image);
  }
}

