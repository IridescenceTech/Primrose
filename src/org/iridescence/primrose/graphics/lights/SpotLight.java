package org.iridescence.primrose.graphics.lights;

import org.joml.Vector3f;

import static java.lang.Math.PI;
import static java.lang.Math.toRadians;

public class SpotLight extends Light {
    public Vector3f direction;

    public float linearTerm;
    public float quadraticTerm;

    public float mCutOff;

    public SpotLight(Vector3f pos, Vector3f col, float intense, float maxCutOff, float linear, float quadratic) {
        super(pos, col, intense, LightType.LIGHT_TYPE_SPOTLIGHT);
        mCutOff = maxCutOff;

        linearTerm = linear;
        quadraticTerm = quadratic;
    }

    @Override
    public void Update() {
        transform.UpdateTransform();

        Vector3f defaultVec = new Vector3f(0, 0, 1);
        direction = defaultVec.rotateX((float)toRadians(transform.rotation.x)).rotateY((float)toRadians(transform.rotation.y)).rotateZ((float)toRadians(transform.rotation.z));
    }
}
