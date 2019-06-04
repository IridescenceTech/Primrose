package org.iridescence.primrose.graphics.sprite;

import static org.iridescence.primrose.graphics.utils.OGLUtils.oglDrawGeometry;

import org.iridescence.primrose.game.GameObject;
import org.iridescence.primrose.game.ObjectType;
import org.iridescence.primrose.game.Transform;
import org.iridescence.primrose.graphics.Camera;
import org.iridescence.primrose.graphics.geometry.Geometry;
import org.iridescence.primrose.graphics.geometry.GeometryBuilderUtil;
import org.iridescence.primrose.graphics.shaders.BasicShader;
import org.iridescence.primrose.graphics.texturing.Texture;
import org.iridescence.primrose.window.Window;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector3f;

public class Sprite extends GameObject {

  public Geometry geometry;

  private int index;
  private Texture tex;

  public Sprite(Texture map, Vector2i offset, Vector2i extent) {
    super(new Transform(new Vector3f(0), new Vector3f(0), new Vector3f(1)), ObjectType.OBJECT_TYPE_SPRITE);
    tex = map;
    index = 0;

    transform.position.x = extent.x + offset.x;
    transform.position.y = offset.y;
    transform.position.z = index;
    transform.UpdateTransform();
    geometry = GeometryBuilderUtil.createPlane(-extent.x, extent.y);
  }

  public Sprite(Texture map, Vector2i offset, Vector2i extent, int ind) {
    super(new Transform(new Vector3f(0), new Vector3f(0), new Vector3f(1)), ObjectType.OBJECT_TYPE_SPRITE);
    tex = map;
    index = ind;

    transform.position.x = offset.x;
    transform.position.y = offset.y;
    transform.position.z = index;
    transform.UpdateTransform();
    geometry = GeometryBuilderUtil.createPlane(extent.x, extent.y);
  }

  public void render() {
    transform.UpdateTransform(); // MAKE SURE THE TRANSFORM IS VALID!

    BasicShader.shader.bind();
    BasicShader.shader.setUniformMat4("modelMatrix", transform.modelMatrix);
    BasicShader.shader.setUniformMat4(
        "projViewMatrix", new Matrix4f().ortho(0, Window.windowObject.getWidth(), Window.windowObject.getHeight(), 0, -1, 32));
    tex.bind();

    oglDrawGeometry(geometry);

    tex.unbind();
    BasicShader.shader.unbind();

  }

}
