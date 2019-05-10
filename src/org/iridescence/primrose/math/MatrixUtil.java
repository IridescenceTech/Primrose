package org.iridescence.primrose.math;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MatrixUtil {

  /**
   * Generates a general purpose Model or View Matrix
   *
   * @param position - The position at which your matrix is supposed to represent
   * @param rotations - The rotation at which your matrix is supposed to represent
   * @return - The result matrix in OpenGL format.
   */
  public static Matrix4f genModelView(Vector3f position, Vector3f rotations) {
    Matrix4f mat = new Matrix4f();

    mat.rotate((float) MathUtilities.degToRad(rotations.x), new Vector3f(1, 0, 0));
    mat.rotate((float) MathUtilities.degToRad(rotations.y), new Vector3f(0, 1, 0));
    mat.rotate((float) MathUtilities.degToRad(rotations.z), new Vector3f(0, 0, 1));
    mat.translate(position);

    return mat;
  }

  public static Matrix4f genModelMatrix(Vector3f position, Vector3f rotation, Vector3f scale) {
    Matrix4f mat = new Matrix4f();

    mat.scale(scale);

    mat.rotate((float) MathUtilities.degToRad(rotation.x), new Vector3f(1, 0, 0));
    mat.rotate((float) MathUtilities.degToRad(rotation.y), new Vector3f(0, 1, 0));
    mat.rotate((float) MathUtilities.degToRad(rotation.z), new Vector3f(0, 0, 1));
    mat.translate(position);

    return mat;
  }

  /**
   * Generates a general purpose projection Matrix.
   *
   * @param fov - Field of View of the projection Matrix
   * @param aspectRatio - Aspect ratio of the resulting frustum Matrix
   * @return - The result matrix in OpenGL format.
   */
  public static Matrix4f genProjection(float fov, float aspectRatio) {
    Matrix4f proj = new Matrix4f();
    proj.perspective(fov, aspectRatio, 0.1f, 1000.0f);

    return proj;
  }
}
