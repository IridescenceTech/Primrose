package org.iridescence.primrose.graphics.materials;

import org.iridescence.primrose.graphics.Camera;
import org.iridescence.primrose.graphics.lights.Light;
import org.iridescence.primrose.graphics.shaders.PhongShader;
import org.iridescence.primrose.graphics.texturing.Texture;
import org.joml.Matrix4f;

import java.util.ArrayDeque;

import static org.lwjgl.opengl.GL13.*;

public class MaterialPhong extends Material{

    protected Texture specMap;
    public float specularIntensity, shininess;

    public MaterialPhong(Texture diffuse, Texture specular, float specIntensity, float shiny){
        super(diffuse);
        specMap = specular;
        specularIntensity = specIntensity;
        shininess = shiny;
    }


    @Override
    public void bindMaterial(Matrix4f modelview) {
        PhongShader.shader.bind();
        PhongShader.shader.setUniformMat4("projViewMatrix", Camera.camera.getProjViewMatrix());
        PhongShader.shader.setUniformMat4("modelMatrix", modelview);
        PhongShader.shader.setUniformVec3f("cameraPosition", Camera.camera.position);
        PhongShader.shader.setUniformFloat("material.specularIntensity", specularIntensity);
        PhongShader.shader.setUniformFloat("material.shininess", shininess);
        PhongShader.shader.setUniformInt("material.map", 0);
        PhongShader.shader.setUniformInt("material.specMap", 1);

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
        PhongShader.shader.unbind();
    }
}
