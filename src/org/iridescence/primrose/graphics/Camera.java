package org.iridescence.primrose.graphics;

import org.iridescence.primrose.math.MatrixUtil;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/** Defines a basic camera object. */
public class Camera {
  public static Camera camera;
  public final Vector3f position;
  public final Vector3f rotation;
  final Matrix4f projMatrix;
  public Vector3f velocity;
  Matrix4f viewMatrix;
  Matrix4f projViewMatrix;

  /**
   * Creates a camera object
   *
   * @param pos - Position of the 3D Camera
   * @param rot - Rotation of the 3D Camera
   * @param fov - Field Of View of the 3D Camera
   * @param aspectRatio - Aspect Ratio of the 3D Camera
   */
  public Camera(Vector3f pos, Vector3f rot, float fov, float aspectRatio) {
    projMatrix = MatrixUtil.genProjection(fov, aspectRatio);
    position = pos;
    rotation = rot;
    viewMatrix = MatrixUtil.genModelView(position, rotation);
    projViewMatrix = new Matrix4f(projMatrix).mul(viewMatrix);
  }

  public void Update() {
    viewMatrix = MatrixUtil.genModelView(position, rotation);
    projViewMatrix = new Matrix4f(projMatrix).mul(viewMatrix);

    // TODO: GENERATE FRUSTUM FOR CULLING LATER!
  }

  /**
   * Gets the View Matrix
   *
   * @return - Returns the View Matrix
   */
  public Matrix4f getViewMatrix() {
    return viewMatrix;
  }

  /**
   * Gets the Projection Matrix
   *
   * @return - Returns the Projection Matrix
   */
  public Matrix4f getProjMatrix() {
    return projMatrix;
  }

  /**
   * Gets the result of the multiplication of the Projection and View Matrices
   *
   * @return - Returns the Projection View Matrix
   */
  public Matrix4f getProjViewMatrix() {
    return projViewMatrix;
  }
}
