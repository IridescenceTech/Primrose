package org.iridescence.primrose.graphics;

import org.iridescence.primrose.game.GameObject;
import org.iridescence.primrose.game.ObjectType;
import org.iridescence.primrose.game.Transform;
import org.iridescence.primrose.graphics.geometry.Geometry;
import org.iridescence.primrose.graphics.lights.Light;
import org.iridescence.primrose.graphics.materials.Material;
import org.joml.Vector3f;

import java.util.ArrayDeque;

import static org.iridescence.primrose.graphics.utils.OGLUtils.oglDrawGeometry;

public class Mesh extends GameObject {


    public Geometry geometry;
    public Material material;

    public boolean isLit;

    public Mesh(Geometry geo, Material mat){
        super(new Transform(new Vector3f(0), new Vector3f(0), new Vector3f(1.0f)), ObjectType.OBJECT_TYPE_MESH);
        geometry = geo;
        material = mat;

        isLit = true;
        transform.UpdateTransform();
    }

    public void render(){
        transform.UpdateTransform(); //MAKE SURE THE TRANSFORM IS VALID!
        material.bindMaterial(transform.modelMatrix);
        oglDrawGeometry(geometry);
        material.unbindMaterial();
    }
}
