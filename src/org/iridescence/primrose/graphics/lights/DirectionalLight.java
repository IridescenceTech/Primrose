package org.iridescence.primrose.graphics.lights;

import org.joml.Vector3f;

import static java.lang.Math.toRadians;

public class DirectionalLight extends Light {
    public Vector3f direction;
    public DirectionalLight(Vector3f position, Vector3f color, float intensity){
        super(position, color, intensity, LightType.LIGHT_TYPE_DIRECTIONAL);
    }

    public void Update(){
        transform.UpdateTransform();

        Vector3f defaultVec = new Vector3f(0, 0, 1);
        direction = defaultVec.rotateX((float)toRadians(transform.rotation.x)).rotateY((float)toRadians(transform.rotation.y)).rotateZ((float)toRadians(transform.rotation.z));
    }
}
