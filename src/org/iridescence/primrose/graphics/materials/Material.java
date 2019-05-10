package org.iridescence.primrose.graphics.materials;

import org.iridescence.primrose.graphics.texturing.Texture;
import org.joml.Matrix4f;

public abstract class Material {
  protected final Texture map;

  public Material(Texture tex) {
    map = tex;
  }

  // Matrix required for Mesh Class.
  public abstract void bindMaterial(Matrix4f modelview);

  public abstract void unbindMaterial();
}
