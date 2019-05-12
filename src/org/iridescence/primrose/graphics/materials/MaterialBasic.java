package org.iridescence.primrose.graphics.materials;

import org.iridescence.primrose.graphics.Camera;
import org.iridescence.primrose.graphics.shaders.BasicShader;
import org.iridescence.primrose.graphics.texturing.Texture;
import org.joml.Matrix4f;

public class MaterialBasic extends Material {

  public MaterialBasic(Texture t){
    super(t, MaterialType.MATERIAL_TYPE_BASIC);
  }

  @Override
  public void bindMaterial(Matrix4f modelview) {
    BasicShader.shader.bind();
    BasicShader.shader.setUniformMat4("modelMatrix", modelview);
    BasicShader.shader.setUniformMat4("projViewMatrix", Camera.camera.getProjViewMatrix());
    map.bind();
  }

  @Override
  public void unbindMaterial() {
    map.unbind();
    BasicShader.shader.unbind();
  }
}
