package org.iridescence.primrose.graphics.materials;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.iridescence.primrose.graphics.Camera;
import org.iridescence.primrose.graphics.shaders.LambertShader;
import org.iridescence.primrose.graphics.texturing.Texture;
import org.joml.Matrix4f;

public class MaterialLambert extends Material {
  public MaterialLambert(Texture diffuse) {
    super(diffuse, MaterialType.MATERIAL_TYPE_LAMBERT);
  }

  @Override
  public void bindMaterial(Matrix4f modelview) {
    LambertShader.shader.bind();
    LambertShader.shader.setUniformMat4("projViewMatrix", Camera.camera.getProjViewMatrix());
    LambertShader.shader.setUniformMat4("modelMatrix", modelview);
    LambertShader.shader.setUniformVec3f("cameraPosition", Camera.camera.position);
    LambertShader.shader.setUniformInt("material.map", 0);

    glActiveTexture(GL_TEXTURE0);
    map.bind();
  }

  @Override
  public void unbindMaterial() {
    glActiveTexture(GL_TEXTURE0);
    map.unbind();
    LambertShader.shader.unbind();
  }
}
