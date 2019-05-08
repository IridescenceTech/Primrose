package org.iridescence.primrose.graphics.lights;

import org.joml.Vector3f;

public class SpotLight extends Light {
    public Vector3f direction;

    public float linearTerm;
    public float quadraticTerm;

    public float cutOff, mCutOff;

    public SpotLight(Vector3f pos, Vector3f col, float intense, float softCutOff, float maxCutOff, float linear, float quadratic) {
        super(pos, col, intense, LightType.LIGHT_TYPE_SPOTLIGHT);
        mCutOff = maxCutOff;
        cutOff = softCutOff;

        linearTerm = linear;
        quadraticTerm = quadratic;
    }

    @Override
    public void Update() {
        transform.UpdateTransform();

        Vector3f defaultVec = new Vector3f(0, 0, 1);
        direction = defaultVec.rotateX(transform.rotation.x).rotateY(transform.rotation.y).rotateZ(transform.rotation.z);
    }
}
