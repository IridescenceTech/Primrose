package org.iridescence.primrose.graphics.lights;

import org.joml.Vector3f;

public class PointLight extends Light {

    public float linearTerm;
    public float quadraticTerm;

    public PointLight(Vector3f pos, Vector3f col, float intense, float linear, float quadratic) {
        super(pos, col, intense, LightType.LIGHT_TYPE_POINTLIGHT);
        linearTerm = linear;
        quadraticTerm = quadratic;
    }

    public void Update(){
        transform.UpdateTransform();
    }
}
