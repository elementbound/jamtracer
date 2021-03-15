package com.github.elementbound.jamtracer.raytracing;

import com.github.elementbound.jamtracer.core.Matrix;
import com.github.elementbound.jamtracer.core.Vector;

/**
 * Class to represent a shape's transform.
 */
public class Transform {
  private Vector position;
  private Vector scale;
  private Vector rotation;

  private Matrix matrix;
  private Matrix inverseMatrix;

  /**
   * Create an identity transform.
   * <p>The identity transform does not change anything about the shape.</p>
   */
  public Transform() {
    position = Vector.ZERO;
    scale = Vector.ONE;
    rotation = Vector.ZERO;

    matrix = Matrix.identity(4);
    inverseMatrix = matrix;
  }

  /**
   * Get transform positon.
   *
   * @return position
   */
  public Vector getPosition() {
    return position;
  }

  /**
   * Get transform scale.
   *
   * @return scale
   */
  public Vector getScale() {
    return scale;
  }

  /**
   * Get transform rotation, in degrees for each axis.
   *
   * @return rotation
   */
  public Vector getRotation() {
    return rotation;
  }

  /**
   * Get transform matrix.
   *
   * @return transform matrix
   */
  public Matrix getMatrix() {
    return matrix;
  }

  /**
   * Get inverse transform matrix.
   *
   * @return inverse transform matrix
   */
  public Matrix getInverseMatrix() {
    return inverseMatrix;
  }

  /**
   * Set transform position.
   *
   * @param position position
   *
   * @return transform update
   */
  public TransformUpdater setPosition(Vector position) {
    assert position.dimensions() == 3 : "Transform position must be 3D!";

    return new TransformUpdater(position, this.scale, this.rotation, this);
  }

  /**
   * Set transform scale.
   *
   * @param scale scale
   *
   * @return transform update
   */
  public TransformUpdater setScale(Vector scale) {
    assert scale.dimensions() == 3 : "Transform scale must be 3D!";

    return new TransformUpdater(this.position, scale, this.rotation, this);
  }

  /**
   * Set transform rotation, in degrees for each axis.
   *
   * @param rotation rotation
   *
   * @return transform update
   */
  public TransformUpdater setRotation(Vector rotation) {
    assert rotation.dimensions() == 3 : "Transform rotation must be 3D!";

    return new TransformUpdater(this.position, this.scale, rotation, this);
  }

  private void updateMatrices() {
    Matrix translation = Matrix.translate(position);
    Matrix scaling = Matrix.scale(scale);
    Matrix rotX = Matrix.rotateAroundX(Math.toRadians(rotation.get(0)));
    Matrix rotY = Matrix.rotateAroundY(Math.toRadians(rotation.get(1)));
    Matrix rotZ = Matrix.rotateAroundZ(Math.toRadians(rotation.get(2)));

    Matrix inverseTranslation = Matrix.translate(position.scale(-1.0));
    Matrix inverseScaling = Matrix.scale(Vector.ONE.divide(scale));
    Matrix inverseRotX = Matrix.rotateAroundX(-Math.toRadians(rotation.get(0)));
    Matrix inverseRotY = Matrix.rotateAroundY(-Math.toRadians(rotation.get(1)));
    Matrix inverseRotZ = Matrix.rotateAroundZ(-Math.toRadians(rotation.get(2)));

    /*
    Matrix multiplication order is the reverse of transformation order.
    So here, we:
      1. Rotate around X axis
      2. Rotate around Y axis
      3. Rotate around Z axis
      4. Scale
      5. Translate
     */
    matrix = translation
            .multiply(scaling)
            .multiply(rotZ)
            .multiply(rotY)
            .multiply(rotX);

    inverseMatrix = inverseTranslation
            .multiply(inverseScaling)
            .multiply(inverseRotZ)
            .multiply(inverseRotY)
            .multiply(inverseRotX);
  }

  /**
   * Static class to enforce matrix update after changing transform.
   */
  public static class TransformUpdater {
    private Vector position;
    private Vector scale;
    private Vector rotation;
    private final Transform transform;

    private TransformUpdater(Vector position, Vector scale, Vector rotation, Transform transform) {
      this.position = position;
      this.scale = scale;
      this.rotation = rotation;
      this.transform = transform;
    }

    /**
     * Set transform position.
     *
     * @param position position
     *
     * @return transform update
     */
    public TransformUpdater setPosition(Vector position) {
      assert position.dimensions() == 3 : "Transform position must be 3D!";

      this.position = position;
      return this;
    }

    /**
     * Set transform scale.
     *
     * @param scale scale
     *
     * @return transform update
     */
    public TransformUpdater setScale(Vector scale) {
      assert scale.dimensions() == 3 : "Transform scale must be 3D!";

      this.scale = scale;
      return this;
    }

    /**
     * Set transform rotation, in degrees for each axis.
     *
     * @param rotation rotation
     *
     * @return transform update
     */
    public TransformUpdater setRotation(Vector rotation) {
      assert rotation.dimensions() == 3 : "Transform rotation must be 3D!";

      this.rotation = rotation;
      return this;
    }

    /**
     * Update transform.
     */
    public void update() {
      transform.updateMatrices();
    }
  }
}
