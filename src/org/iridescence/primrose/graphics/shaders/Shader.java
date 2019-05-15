package org.iridescence.primrose.graphics.shaders;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform2i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform3i;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniform4i;
import static org.lwjgl.opengl.GL20.glUniformMatrix3fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import org.iridescence.primrose.utils.Logging;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4f;
import org.joml.Vector4i;
import org.lwjgl.BufferUtils;

/** Defines the basic extensible shader object */
public abstract class Shader {

  public final int programID;
  private final int vertexID;
  private final int fragmentID;

  /**
   * Creates and compiles a shader program from vertex and fragment shader, either from source or a
   * path.
   *
   * @param vs - Vertex shader
   * @param fs - Fragment shader
   * @param loadFromFile - Whether or not the string is source code or a path.
   */
  public Shader(String vs, String fs, boolean loadFromFile) {
    if (loadFromFile) {
      vertexID = loadShader(vs, GL_VERTEX_SHADER);
      fragmentID = loadShader(fs, GL_FRAGMENT_SHADER);
    } else {
      vertexID = compileShader(vs, GL_VERTEX_SHADER);
      fragmentID = compileShader(fs, GL_FRAGMENT_SHADER);
    }
    programID = glCreateProgram();

    glAttachShader(programID, vertexID);
    glAttachShader(programID, fragmentID);

    glLinkProgram(programID);

    if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
      System.err.println("Error linking Shader code: " + glGetProgramInfoLog(programID, 1024));
      System.exit(-1);
    }
  }

  /**
   * Loads a shader
   *
   * @param file - Path to the file
   * @param type - Type of shader
   * @return - Returns the shader ID.
   */
  private static int loadShader(String file, int type) {
    StringBuilder shaderSource = new StringBuilder();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line;
      while ((line = reader.readLine()) != null) {
        shaderSource.append(line).append("//\n");
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(-1);
    }
    return compileShader(shaderSource.toString(), type);
  }

  /**
   * Compiles a shader
   *
   * @param source - Source of the shader
   * @param type - Type of shader
   * @return - Returns the shader ID.
   */
  private static int compileShader(String source, int type) {
    int shaderID = glCreateShader(type);
    glShaderSource(shaderID, source);
    glCompileShader(shaderID);
    if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
      Logging.logger.severe(glGetShaderInfoLog(shaderID, 500));
      Logging.logger.severe("Could not compile shader!");
      System.exit(-1);
    }
    return shaderID;
  }

  /** Destroys the current shader program */
  public void cleanup() {
    unbind();
    glDetachShader(programID, vertexID);
    glDetachShader(programID, fragmentID);
    glDeleteProgram(programID);
    glDeleteShader(vertexID);
    glDeleteShader(fragmentID);
  }

  /** Binds the current shader program. */
  public void bind() {
    glUseProgram(programID);
  }

  /** Unbinds the current shader program */
  public void unbind() {
    glUseProgram(0);
  }

  /** All of the shader uniform */
  public void setUniformInt(String uniform, int data) {
    glUniform1i(glGetUniformLocation(programID, uniform), data);
  }

  public void setUniformFloat(String uniform, float data) {
    glUniform1f(glGetUniformLocation(programID, uniform), data);
  }

  public void setUniformVec2f(String uniform, Vector2f data) {
    glUniform2f(glGetUniformLocation(programID, uniform), data.x, data.y);
  }

  public void setUniformVec2i(String uniform, Vector2i data) {
    glUniform2i(glGetUniformLocation(programID, uniform), data.x, data.y);
  }

  public void setUniformVec3f(String uniform, Vector3f data) {
    glUniform3f(glGetUniformLocation(programID, uniform), data.x, data.y, data.z);
  }

  public void setUniformVec3i(String uniform, Vector3i data) {
    glUniform3i(glGetUniformLocation(programID, uniform), data.x, data.y, data.z);
  }

  public void setUniformVec4f(String uniform, Vector4f data) {
    glUniform4f(glGetUniformLocation(programID, uniform), data.x, data.y, data.z, data.w);
  }

  public void setUniformVec4i(String uniform, Vector4i data) {
    glUniform4i(glGetUniformLocation(programID, uniform), data.x, data.y, data.z, data.w);
  }

  public void setUniformMat3(String uniform, Matrix3f data) {
    FloatBuffer fb = BufferUtils.createFloatBuffer(9);
    data.get(fb);
    glUniformMatrix3fv(glGetUniformLocation(programID, uniform), false, fb);
  }

  public void setUniformMat4(String uniform, Matrix4f data) {
    FloatBuffer fb = BufferUtils.createFloatBuffer(16);
    data.get(fb);
    glUniformMatrix4fv(glGetUniformLocation(programID, uniform), false, fb);
  }

  public void setUniformBoolean(String uniform, boolean bool){
    glUniform1i(glGetUniformLocation(programID, uniform), bool ? 1 : 0);
  }
}
