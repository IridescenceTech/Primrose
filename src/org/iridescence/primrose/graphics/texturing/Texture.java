package org.iridescence.primrose.graphics.texturing;

import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

/** Defines the Texture Object */
public class Texture {
  public final int id;
  public int width, height;

  /**
   * Creates a new texture object
   *
   * @param path - The path of the image file
   * @param magFilter - The mipmap mag filter
   * @param minFilter - The mipmap min filter
   */
  public Texture(String path, int magFilter, int minFilter) {
    id = glGenTextures();
    glBindTexture(GL_TEXTURE_2D, id);

    IntBuffer w = MemoryUtil.memAllocInt(1);
    IntBuffer h = MemoryUtil.memAllocInt(1);
    IntBuffer comp = MemoryUtil.memAllocInt(1);

    stbi_set_flip_vertically_on_load(true);
    ByteBuffer image = stbi_load(path, w, h, comp, 4);
    if (image == null) {
      throw new RuntimeException(
          "Failed to load a texture file!" + System.lineSeparator() + stbi_failure_reason());
    }

    width = w.get();
    height = h.get();

    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

    glGenerateMipmap(GL_TEXTURE_2D);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);

    if (GL.getCapabilities().GL_EXT_texture_filter_anisotropic) {
      float max;
      max = glGetFloat(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT);
      float amount = Math.min(16.0f, max);
      glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
    }
  }

  /** Deletes the texture */
  public void cleanup() {
    glDeleteTextures(id);
  }

  /** Binds the texture to the current texture unit */
  public void bind() {
    glBindTexture(GL_TEXTURE_2D, id);
  }

  /** Unbinds the texture from the current texture unit */
  public void unbind() {
    glBindTexture(GL_TEXTURE_2D, 0);
  }
}
