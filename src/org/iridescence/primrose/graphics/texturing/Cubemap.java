package org.iridescence.primrose.graphics.texturing;

import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_LINEAR;
import static org.lwjgl.opengl.GL13.GL_RGBA;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL13.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL13.glBindTexture;
import static org.lwjgl.opengl.GL13.glDeleteTextures;
import static org.lwjgl.opengl.GL13.glGenTextures;
import static org.lwjgl.opengl.GL13.glTexImage2D;
import static org.lwjgl.opengl.GL13.glTexParameteri;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.system.MemoryUtil;

/** Defines a basic Cubemap */
public class Cubemap {

  private int texID;

  /**
   * Creates a brand new Cubemap
   *
   * @param files - The 6 files in the order: -right -left -top -bottom -back -front
   */
  public Cubemap(String[] files) {
    loadFromFiles(files);
  }

  /** Destroys the cubemap object */
  public void cleanup() {
    glDeleteTextures(texID);
  }

  /**
   * Loads the cubemap from the file array
   *
   * @param files - The 6 files to load.
   */
  private void loadFromFiles(String[] files) {
    texID = glGenTextures();
    glBindTexture(GL_TEXTURE_CUBE_MAP, texID);

    for (int i = 0; i < 6; i++) {

      IntBuffer w = MemoryUtil.memAllocInt(1);
      IntBuffer h = MemoryUtil.memAllocInt(1);
      IntBuffer comp = MemoryUtil.memAllocInt(1);
      stbi_set_flip_vertically_on_load(false);
      ByteBuffer image = stbi_load(files[i], w, h, comp, 4);

      if (image == null) {
        throw new RuntimeException(
            "Failed to load a texture file!" + System.lineSeparator() + stbi_failure_reason());
      }

      int width = w.get();
      int height = h.get();
      int param = GL_TEXTURE_CUBE_MAP_POSITIVE_X + i;

      glTexImage2D(param, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
    }

    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
  }

  /** Binds the cubemap to the current texture unit. */
  public void bindTexture() {
    glBindTexture(GL_TEXTURE_CUBE_MAP, texID);
  }
}
