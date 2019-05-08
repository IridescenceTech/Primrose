package org.iridescence.primrose.graphics.utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_SHADING_LANGUAGE_VERSION;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_SRGB;
import static org.lwjgl.opengl.GL30.GL_INVALID_FRAMEBUFFER_OPERATION;

import org.iridescence.primrose.utils.Logging;

public class OGLReporter {

  /**
   * Reports capabilities and systems information.
   */
  public static void capabilitiesReport(){
    Logging.logger.info("OpenGL Version: " + glGetString(GL_VERSION));
    Logging.logger.info("GLSL Version: " + glGetString(GL_SHADING_LANGUAGE_VERSION));
    Logging.logger.info("OpenGL Vendor: " + glGetString(GL_VENDOR));
    Logging.logger.info("OpenGL Renderer: " + glGetString(GL_RENDERER));


    //This also can do initialization work
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glEnable(GL_FRAMEBUFFER_SRGB);
  }

  /**
   * Converts a raw error code from OpenGL to a string
   * @param err - OpenGL error code
   * @return - String of error
   */
  public static String convertError(int err){
    String error = "";
    switch(err) {
      case GL_INVALID_OPERATION:      error="INVALID_OPERATION";      break;
      case GL_INVALID_ENUM:           error="INVALID_ENUM";           break;
      case GL_INVALID_VALUE:          error="INVALID_VALUE";          break;
      case GL_OUT_OF_MEMORY:          error="OUT_OF_MEMORY";          break;
      case GL_INVALID_FRAMEBUFFER_OPERATION:  error="INVALID_FRAMEBUFFER_OPERATION";  break;
    }

    return error;
  }

  /**
   * Searches for potential OpenGL errors.
   */
  public static void searchErrors(){
    int err = glGetError();
    if(err != GL_NO_ERROR){
      Logging.logger.severe("OpenGL Error: " + convertError(err));
    }
  }
}
