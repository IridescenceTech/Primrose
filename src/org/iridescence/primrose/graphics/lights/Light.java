package org.iridescence.primrose.graphics.lights;

import org.iridescence.primrose.game.GameObject;
import org.iridescence.primrose.game.ObjectType;
import org.iridescence.primrose.game.Transform;
import org.joml.Vector3f;

public abstract class Light extends GameObject {
  public final Vector3f color;
  public final float intensity;
  public final LightType type;

  public Light(Vector3f pos, Vector3f col, float intense, LightType ty) {
    super(new Transform(pos, new Vector3f(0.0f), new Vector3f(1.0f)), ObjectType.OBJECT_TYPE_LIGHT);
    color = col;
    intensity = intense;
    type = ty;
  }

  public Light(Vector3f pos, Vector3f col, float intense, LightType ty, String tags) {
    super(new Transform(pos, new Vector3f(0.0f), new Vector3f(1.0f)), ObjectType.OBJECT_TYPE_LIGHT, tags);
    color = col;
    intensity = intense;
    type = ty;
  }

  public Light(Vector3f pos, Vector3f col, float intense, LightType ty, String tags, String names) {
    super(new Transform(pos, new Vector3f(0.0f), new Vector3f(1.0f)), ObjectType.OBJECT_TYPE_LIGHT, tags, names);
    color = col;
    intensity = intense;
    type = ty;
  }

  public abstract void Update();
}
