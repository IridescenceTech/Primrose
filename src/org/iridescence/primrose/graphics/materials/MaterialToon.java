package org.iridescence.primrose.graphics.materials;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.iridescence.primrose.graphics.Camera;
import org.iridescence.primrose.graphics.shaders.ToonShader;
import org.iridescence.primrose.graphics.texturing.Texture;
import org.joml.Matrix4f;

public class MaterialToon extends Material {
  public float colorShades;

  public MaterialToon(Texture diffuse, float cShades) {
    super(diffuse, MaterialType.MATERIAL_TYPE_TOON);
    colorShades = cShades;
  }

  @Override
  public void bindMaterial(Matrix4f modelview) {
    ToonShader.shader.bind();
    ToonShader.shader.setUniformMat4("projViewMatrix", Camera.camera.getProjViewMatrix());
    ToonShader.shader.setUniformMat4("modelMatrix", modelview);
    ToonShader.shader.setUniformVec3f("cameraPosition", Camera.camera.position);
    ToonShader.shader.setUniformInt("material.map", 0);
    ToonShader.shader.setUniformFloat("colorShades", colorShades);

    glActiveTexture(GL_TEXTURE0);
    map.bind();
  }

  @Override
  public void unbindMaterial() {
    glActiveTexture(GL_TEXTURE0);
    map.unbind();
    ToonShader.shader.unbind();
  }
}
