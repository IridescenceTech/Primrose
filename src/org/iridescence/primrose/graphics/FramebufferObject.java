package org.iridescence.primrose.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;
import static org.lwjgl.opengl.GL30.*;

import org.joml.Vector2i;
import org.lwjgl.system.MemoryUtil;


/**
 * Defines a FrameBuffer Object
 */
public class FramebufferObject {

  private int fboID;

  //We don't track normals... this is still forward rendering
  private int colorID;
  private int depthID;

  private Vector2i bufferResolution;

  /**
   * Creates the framebuffer object
   * @param resolution - Framebuffer resolution
   */
  public FramebufferObject(Vector2i resolution, boolean hdr){
    fboID = glGenFramebuffers();
    glBindFramebuffer(GL_FRAMEBUFFER, fboID);
    bufferResolution = resolution;
    //Render texture
    colorID = glGenTextures();
    glBindTexture(GL_TEXTURE_2D, colorID);

    if(!hdr) {
      glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA32F, resolution.x, resolution.y, 0, GL_RGBA,
          GL_UNSIGNED_BYTE, MemoryUtil.NULL);
    }else{
      glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA32F, resolution.x, resolution.y, 0, GL_RGBA,
          GL_FLOAT, MemoryUtil.NULL);
    }

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

    //Bind render texture to framebuffer
    glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, colorID, 0);

    depthID = glGenTextures();
    glBindTexture(GL_TEXTURE_2D, depthID);

    glTexImage2D(
        GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT32, resolution.x, resolution.y, 0,
        GL_DEPTH_COMPONENT, GL_FLOAT, MemoryUtil.NULL
    );


    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);


    glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, depthID, 0);

    glBindFramebuffer(GL_FRAMEBUFFER, 0);
  }

  /**
   * Returns the texture ID of the framebuffer data
   * @return - Texture ID
   */
  public int getColorTexture(){
    return colorID;
  }

  /**
   * Returns the texture ID of the framebuffer data
   * @return - Texture ID
   */
  public int getDepthTexture(){
    return depthID;
  }

  /**
   * Binds this framebuffer to be drawn to.
   */
  public void bind(){
    glBindFramebuffer(GL_FRAMEBUFFER, fboID);
    glViewport(0, 0, bufferResolution.x, bufferResolution.y);
  }

  /**
   * Unbinds this framebuffer.
   */
  public void unbind(){
    glBindFramebuffer(GL_FRAMEBUFFER, 0);
  }

  /**
   * Clears framebuffer data.
   */
  public void clear(){ glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); }

  /**
   * Returns the OpenGL Framebuffer ID
   * @return - OpenGL Framebuffer ID
   */
  public int getFBO(){ return fboID; }
}
