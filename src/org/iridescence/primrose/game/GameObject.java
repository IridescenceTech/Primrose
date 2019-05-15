package org.iridescence.primrose.game;

public abstract class GameObject {
  public final Transform transform;
  final ObjectType type;

  public String tag, name;
  public int layer;

  public GameObject(Transform t, ObjectType oT) {
    transform = t;
    type = oT;
    tag = "default";
    name = "GameObject";
    layer = 0;
  }

  public GameObject(Transform t, ObjectType oT, String tags){
    transform = t;
    type = oT;
    tag = tags;
    name = "GameObject";
    layer = 0;
  }

  public GameObject(Transform t, ObjectType oT, String tags, String names){
    transform = t;
    type = oT;
    tag = tags;
    name = names;
    layer = 0;
  }


}
