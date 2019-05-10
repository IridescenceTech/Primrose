package org.iridescence.primrose.graphics.utils;

import static org.lwjgl.opengl.GL30.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL30.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL30.GL_DRAW_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_NEAREST;
import static org.lwjgl.opengl.GL30.GL_READ_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL30.GL_TRIANGLES;
import static org.lwjgl.opengl.GL30.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL30.glBindFramebuffer;
import static org.lwjgl.opengl.GL30.glBlitFramebuffer;
import static org.lwjgl.opengl.GL30.glClear;
import static org.lwjgl.opengl.GL30.glClearColor;
import static org.lwjgl.opengl.GL30.glDrawArrays;
import static org.lwjgl.opengl.GL30.glDrawElements;
import static org.lwjgl.opengl.GL30.glViewport;

import org.iridescence.primrose.graphics.geometry.Geometry;
import org.joml.Vector2i;

/** A Utility Class For Wrapping OpenGL Calls. */
public class OGLUtils {

  /** Clears all OpenGL drawing buffers for the attached framebuffer. */
  public static void oglClear() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
  }

  /** Set the OpenGL Clear Color in terms of 255 */
  public static void oglSetClearColorRGB(int r, int g, int b, int a) {
    glClearColor(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
  }

  /** Set the OpenGL Clear Color in floating points from 0.0 - 1.0 */
  public static void oglSetClearColorFloat(float r, float g, float b, float a) {
    glClearColor(r, g, b, a);
  }

  /**
   * Re-sizes the current framebuffer to a new width/height combination.
   *
   * @param width - The new width of the viewport.
   * @param height - The new height of the viewport.
   */
  public static void oglViewport(int width, int height) {
    glViewport(0, 0, width, height);
  }

  /**
   * Draws the currently bound VAO
   *
   * @param indexCount - Number of indices to draw.
   */
  public static void oglDrawElements(int indexCount) {
    glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_INT, 0);
  }

  /**
   * Draws the currently bound VAO
   *
   * @param vertexCount - Number of vertices to draw.
   */
  public static void oglDrawArrays(int vertexCount) {
    glDrawArrays(GL_TRIANGLES, 0, vertexCount);
  }

  /**
   * Blits one framebuffer to another.
   *
   * @param fbo1 - The FBO to be written to
   * @param fbo1Res - The resolution of the first FBO
   * @param fbo2 - The FBO to be read from
   * @param fbo2Res - The resolution of the second FBO
   */
  public static void oglResolveFrameBuffers(
      int fbo1, Vector2i fbo1Res, int fbo2, Vector2i fbo2Res) {
    glBindFramebuffer(GL_DRAW_FRAMEBUFFER, fbo1);
    glBindFramebuffer(GL_READ_FRAMEBUFFER, fbo2);
    glBlitFramebuffer(
        0,
        0,
        fbo2Res.x,
        fbo2Res.y,
        0,
        0,
        fbo1Res.x,
        fbo1Res.y,
        GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT,
        GL_NEAREST);
  }

  /**
   * Can draw a geometry with pre-bound shader and texture.
   *
   * @param geo - The Geometry to be drawn.
   */
  public static void oglDrawGeometry(Geometry geo) {
    geo.bindVAO();
    geo.enableVAA();
    oglDrawElements(geo.getIndexCount());
    geo.disableVAA();
    geo.unbindVAO();
  }
}
