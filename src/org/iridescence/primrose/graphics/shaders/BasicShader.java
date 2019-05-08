package org.iridescence.primrose.graphics.shaders;

/**
 * Unlit Shader with textures
 */
public class BasicShader extends Shader {
  public static final String vs = "#version 330\n"
      + "\n"
      + "// A basic Vertex Shader\n"
      + "\n"
      + "layout(location = 0) in vec3 inPosition;\n"
      + "layout(location = 1) in vec2 inTexcoord;\n"
      + "layout(location = 2) in vec3 inNormal;\n"
      + "\n"
      + "out vec2 texcoord;\n"
      + "\n"
      + "uniform mat4 projViewMatrix;\n"
      + "uniform mat4 modelMatrix;\n"
      + "\n"
      + "void main()\n"
      + "{\n"
      + "    gl_Position = projViewMatrix * modelMatrix * vec4(inPosition, 1.0f);\n"
      + "\n"
      + "    texcoord = inTexcoord;\n"
      + "}";
  public static final String fs = "#version 330\n"
      + "\n"
      + "// A basic Fragment Shader\n"
      + "\n"
      + "out vec4 outColor;\n"
      + "in  vec2 texcoord;\n"
      + "\n"
      + "uniform sampler2D texSample;\n"
      + "\n"
      + "void main()\n"
      + "{\n"
      + "    vec4 color = texture(texSample, texcoord.xy);\n"
      + "\n"
      + "    outColor = color;\n"
      + "    if(color.a == 0)\n"
      + "            discard;\n"
      + "}";

  public BasicShader(){
    super( vs, fs ,false);
  }

  /**
   * A static instance of the shader.
   */
  public final static BasicShader shader = new BasicShader();
}
