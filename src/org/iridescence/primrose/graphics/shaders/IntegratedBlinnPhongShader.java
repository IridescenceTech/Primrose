package org.iridescence.primrose.graphics.shaders;

public class IntegratedBlinnPhongShader extends Shader{
  /** A static instance of the shader. */
  public static final IntegratedBlinnPhongShader shader = new IntegratedBlinnPhongShader();

  public IntegratedBlinnPhongShader() {
    super("./integratedPhong.vert", "./integratedPhong.frag", true);
  }
}
