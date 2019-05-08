package org.iridescence.primrose.game;

public abstract class GameObject {
    public Transform transform;
    ObjectType type;

    public GameObject(Transform t, ObjectType oT){
        transform = t;
        type = oT;
    }
}
