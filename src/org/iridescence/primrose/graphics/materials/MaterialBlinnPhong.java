package org.iridescence.primrose.graphics.materials;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import org.iridescence.primrose.graphics.Camera;
import org.iridescence.primrose.graphics.shaders.BlinnPhongShader;
import org.iridescence.primrose.graphics.shaders.PhongShader;
import org.iridescence.primrose.graphics.texturing.Texture;
import org.joml.Matrix4f;

public class MaterialBlinnPhong extends Material {

  public final float specularIntensity;
  public final float shininess;
  protected final Texture specMap;

  public MaterialBlinnPhong(Texture diffuse, Texture specular, float specIntensity, float shiny) {
    super(diffuse, MaterialType.MATERIAL_TYPE_BLINN_PHONG);
    specMap = specular;
    specularIntensity = specIntensity;
    shininess = shiny;
  }

  @Override
  public void bindMaterial(Matrix4f modelview) {
    BlinnPhongShader.shader.bind();
    BlinnPhongShader.shader.setUniformMat4("projViewMatrix", Camera.camera.getProjViewMatrix());
    BlinnPhongShader.shader.setUniformMat4("modelMatrix", modelview);
    BlinnPhongShader.shader.setUniformVec3f("cameraPosition", Camera.camera.position);
    BlinnPhongShader.shader.setUniformFloat("material.specularIntensity", specularIntensity);
    BlinnPhongShader.shader.setUniformFloat("material.shininess", shininess);
    BlinnPhongShader.shader.setUniformInt("material.map", 0);
    BlinnPhongShader.shader.setUniformInt("material.specMap", 1);

    glActiveTexture(GL_TEXTURE0);
    map.bind();
    glActiveTexture(GL_TEXTURE1);
    specMap.bind();
  }

  @Override
  public void unbindMaterial() {
    glActiveTexture(GL_TEXTURE1);
    specMap.unbind();
    glActiveTexture(GL_TEXTURE0);
    map.unbind();
    BlinnPhongShader.shader.unbind();
  }
}
