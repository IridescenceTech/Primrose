package org.iridescence.primrose.graphics.shaders;

public class IntegratedLambertShader extends Shader{
  /** A static instance of the shader. */
  public static final IntegratedLambertShader shader = new IntegratedLambertShader();

  public IntegratedLambertShader() {
    super("./integratedLambert.vert", "./integratedLambert.frag", true);
  }
}
