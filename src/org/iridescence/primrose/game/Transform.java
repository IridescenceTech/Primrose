package org.iridescence.primrose.game;

import org.iridescence.primrose.math.MatrixUtil;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {
  public final Vector3f scale;
  public Vector3f position;
  public Vector3f rotation;
  public Matrix4f modelMatrix;

  public Transform(Vector3f pos, Vector3f rot, Vector3f sca) {
    position = pos;
    rotation = rot;
    scale = sca;
  }

  public void UpdateTransform() {
    modelMatrix = MatrixUtil.genModelMatrix(position, rotation, scale);
  }
}
