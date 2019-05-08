package org.iridescence.primrose.graphics.shaders;

/**
 * Phong Shader, lit with specular.
 */
public class PhongShader extends Shader {



    public PhongShader(){
        super( "./phong.vert", "./phong.frag" ,true);
    }

    /**
     * A static instance of the shader.
     */
    public final static PhongShader shader = new PhongShader();
}
