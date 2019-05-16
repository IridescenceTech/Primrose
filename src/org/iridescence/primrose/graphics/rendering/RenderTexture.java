package org.iridescence.primrose.graphics.rendering;

import static org.lwjgl.opengl.GL30.*;

import org.joml.Vector2i;
import org.lwjgl.system.MemoryUtil;

public class RenderTexture {


  private final Vector2i bufferResolution;
  public final int fboID;
  private int colTexture;
  private int depTexture;

  public RenderTexture(Vector2i resolution, boolean hdr, int flags){
    bufferResolution = resolution;
    fboID = glGenFramebuffers();

    glBindFramebuffer(GL_FRAMEBUFFER, fboID);

    colTexture = 0;
    depTexture = 0;

    if((flags & RenderTargets.COLOR_TARGET) == RenderTargets.COLOR_TARGET){
      colTexture = glGenTextures();
      glBindTexture(GL_TEXTURE_2D, colTexture);

      if (!hdr) {
        glTexImage2D(
            GL_TEXTURE_2D,
            0,
            GL_RGBA32F,
            resolution.x,
            resolution.y,
            0,
            GL_RGBA,
            GL_UNSIGNED_BYTE,
            MemoryUtil.NULL);
      } else {
        glTexImage2D(
            GL_TEXTURE_2D,
            0,
            GL_RGBA32F,
            resolution.x,
            resolution.y,
            0,
            GL_RGBA,
            GL_FLOAT,
            MemoryUtil.NULL);
      }
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
      glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, colTexture, 0);
    }
    if((flags & RenderTargets.DEPTH_TARGET) == RenderTargets.DEPTH_TARGET){
      depTexture = glGenTextures();
      glBindTexture(GL_TEXTURE_2D, depTexture);

      glTexImage2D(
          GL_TEXTURE_2D,
          0,
          GL_DEPTH_COMPONENT32,
          resolution.x,
          resolution.y,
          0,
          GL_DEPTH_COMPONENT,
          GL_FLOAT,
          MemoryUtil.NULL);

      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

      glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depTexture, 0);
    }

    clear(); //Initial Clear
    unbind();
  }

  /**
   * Returns the texture ID of the framebuffer data if it exists
   *
   * @return - Texture ID
   */
  public int getColorTexture() {
    return colTexture;
  }

  /**
   * Returns the texture ID of the framebuffer data if it exists
   *
   * @return - Texture ID
   */
  public int getDepthTexture() {
    return depTexture;
  }

  /** Binds this framebuffer to be drawn to. */
  public void bind() {
    glBindFramebuffer(GL_FRAMEBUFFER, fboID);
    glViewport(0, 0, bufferResolution.x, bufferResolution.y);
  }

  /** Unbinds this framebuffer. */
  public void unbind() {
    glBindFramebuffer(GL_FRAMEBUFFER, 0);
  }

  /** Clears framebuffer data. */
  public void clear() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  }

  /**
   * Returns the OpenGL Framebuffer ID
   *
   * @return - OpenGL Framebuffer ID
   */
  public int getFBO() {
    return fboID;
  }

  public Vector2i getBufferResolution(){
    return bufferResolution;
  }
}
