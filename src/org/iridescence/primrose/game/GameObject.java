package org.iridescence.primrose.game;

public abstract class GameObject {
  public final Transform transform;
  final ObjectType type;

  public GameObject(Transform t, ObjectType oT) {
    transform = t;
    type = oT;
  }
}
