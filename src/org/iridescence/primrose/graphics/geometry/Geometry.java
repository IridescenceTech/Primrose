package org.iridescence.primrose.graphics.geometry;

import static org.lwjgl.opengl.GL30.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL30.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL30.GL_FLOAT;
import static org.lwjgl.opengl.GL30.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL30.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glBufferData;
import static org.lwjgl.opengl.GL30.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glGenBuffers;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glVertexAttribPointer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.system.MemoryUtil;

/** Defines an OpenGL renderable object. */
public class Geometry {
  public int vao;
  private int vbo;
  private int tbo;
  private int ebo;
  private int nbo;
  private int indexCount;

  /**
   * Creates the mesh object.
   *
   * @param vertices - Vertex Data
   * @param texture - Texture Coordinate Data
   * @param indices - Index Data
   */
  public Geometry(float[] vertices, float[] texture, float[] normals, int[] indices) {
    FloatBuffer verticesBuffer = null; // Temp
    IntBuffer indicesBuffer = null;
    FloatBuffer texturesBuffer = null;
    FloatBuffer normalsBuffer = null;
    try {
      vao = glGenVertexArrays();
      glBindVertexArray(vao);

      verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
      verticesBuffer.put(vertices).flip();

      indicesBuffer = MemoryUtil.memAllocInt(indices.length);
      indicesBuffer.put(indices).flip();

      texturesBuffer = MemoryUtil.memAllocFloat(texture.length);
      texturesBuffer.put(texture).flip();

      normalsBuffer = MemoryUtil.memAllocFloat(normals.length);
      normalsBuffer.put(normals).flip();

      indexCount = indices.length;

      vbo = glGenBuffers();
      glBindBuffer(GL_ARRAY_BUFFER, vbo);
      glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

      tbo = glGenBuffers();
      glBindBuffer(GL_ARRAY_BUFFER, tbo);
      glBufferData(GL_ARRAY_BUFFER, texturesBuffer, GL_STATIC_DRAW);

      nbo = glGenBuffers();
      glBindBuffer(GL_ARRAY_BUFFER, nbo);
      glBufferData(GL_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);

      ebo = glGenBuffers();
      glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
      glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

      glBindBuffer(GL_ARRAY_BUFFER, vbo);
      glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

      glBindBuffer(GL_ARRAY_BUFFER, tbo);
      glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

      glBindBuffer(GL_ARRAY_BUFFER, nbo);
      glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);

      glBindBuffer(GL_ARRAY_BUFFER, 0);
      glBindVertexArray(0);

    } finally {
      if (verticesBuffer != null) {
        MemoryUtil.memFree(verticesBuffer);
      }
      if (texturesBuffer != null) {
        MemoryUtil.memFree(texturesBuffer);
      }
      if (indicesBuffer != null) {
        MemoryUtil.memFree(indicesBuffer);
      }
      if (normalsBuffer != null) {
        MemoryUtil.memFree(normalsBuffer);
      }
    }
  }

  /** Binds the Vertex Array Object */
  public void bindVAO() {
    glBindVertexArray(vao);
  }

  /** Unbinds the Vertex Array Object */
  public void unbindVAO() {
    glBindVertexArray(vao);
  }

  /** Enables Vertex Attribute Arrays! */
  public void enableVAA() {
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);
    glEnableVertexAttribArray(2);
  }

  /** Disables Vertex Attribute Arrays */
  public void disableVAA() {
    glDisableVertexAttribArray(0);
    glEnableVertexAttribArray(1);
    glEnableVertexAttribArray(2);
  }

  /**
   * Gets the index count
   *
   * @return - Returns the number of indices.
   */
  public int getIndexCount() {
    return indexCount;
  }

  /** Destroys the Geometry Object */
  public void cleanup() {
    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);

    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glDeleteBuffers(vbo);
    glDeleteBuffers(tbo);
    glDeleteBuffers(ebo);
    glDeleteBuffers(nbo);

    glBindVertexArray(0);
    glDeleteVertexArrays(vao);
  }
}
