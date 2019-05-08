package org.iridescence.primrose.graphics.lights;

import org.iridescence.primrose.game.GameObject;
import org.iridescence.primrose.game.ObjectType;
import org.iridescence.primrose.game.Transform;
import org.joml.Vector3f;

public abstract class Light extends GameObject {
    public Vector3f color;
    public float intensity;
    public LightType type;

    public Light(Vector3f pos, Vector3f col, float intense, LightType ty){
        super(new Transform(pos, new Vector3f(0.0f), new Vector3f(1.0f)), ObjectType.OBJECT_TYPE_LIGHT);
        color = col;
        intensity = intense;
        type = ty;
    }

    public abstract void Update();
}
