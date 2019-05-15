package org.iridescence.primrose.graphics.materials;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.iridescence.primrose.graphics.Camera;
import org.iridescence.primrose.graphics.shaders.IntegratedLambertShader;
import org.iridescence.primrose.graphics.texturing.Texture;
import org.joml.Matrix4f;

public class MaterialLambert extends Material {
  public MaterialLambert(Texture diffuse) {
    super(diffuse, MaterialType.MATERIAL_TYPE_LAMBERT);
  }

  @Override
  public void bindMaterial(Matrix4f modelview) {
    IntegratedLambertShader.shader.bind();
    IntegratedLambertShader.shader.setUniformBoolean("toon", false);
    IntegratedLambertShader.shader.setUniformMat4("projViewMatrix", Camera.camera.getProjViewMatrix());
    IntegratedLambertShader.shader.setUniformMat4("modelMatrix", modelview);
    IntegratedLambertShader.shader.setUniformVec3f("cameraPosition", Camera.camera.position);
    IntegratedLambertShader.shader.setUniformInt("material.map", 0);

    glActiveTexture(GL_TEXTURE0);
    map.bind();
  }

  @Override
  public void unbindMaterial() {
    glActiveTexture(GL_TEXTURE0);
    map.unbind();
    IntegratedLambertShader.shader.unbind();
  }
}
