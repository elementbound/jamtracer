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
   * Create a transform with given settings.
   *
   * @param position position
   * @param rotation rotation
   * @param scale scale
   */
  public Transform(Vector position, Vector rotation, Vector scale) {
    this.position = position;
    this.rotation = rotation;
    this.scale = scale;

    updateMatrices();
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
   * Initiate transform update.
   *
   * @return transform update
   */
  public TransformUpdater update() {
    return new TransformUpdater(this.position, this.scale, this.rotation, this);
  }

  /**
   * Apply transform to ray, i.e. transform ray from object space to world space.
   *
   * @param ray object space ray
   *
   * @return world space ray
   */
  public Ray transformRay(Ray ray) {
    var from = ray.getFrom().asHeterogeneous();
    var to = ray.getPoint(1.0).asHeterogeneous();

    from = matrix.transform(from);
    to = matrix.transform(to);

    return Ray.lookat(from.asHomogeneous(), to.asHomogeneous());
  }

  /**
   * Apply inverse transform to ray, i.e. transform ray from world space to object space.
   *
   * @param ray world space ray
   *
   * @return object space ray
   */
  public Ray inverseTransformRay(Ray ray) {
    var from = ray.getFrom().asHeterogeneous();
    var to = ray.getPoint(1.0).asHeterogeneous();

    from = inverseMatrix.transform(from);
    to = inverseMatrix.transform(to);

    return Ray.lookat(from.asHomogeneous(), to.asHomogeneous());
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

    matrix = rotX
            .multiply(rotY)
            .multiply(rotZ)
            .multiply(scaling)
            .multiply(translation);

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
     * Translate transform.
     *
     * @param offset offset
     *
     * @return transform update
     */
    public TransformUpdater translate(Vector offset) {
      this.position = this.position.add(offset);
      return this;
    }

    /**
     * Scale transform.
     *
     * @param scalars scalars
     *
     * @return transform update
     */
    public TransformUpdater scale(Vector scalars) {
      this.scale = this.scale.multiply(scalars);
      return this;
    }

    /**
     * Uniformly scale transform.
     *
     * @param scalar scalar
     *
     * @return transform update
     */
    public TransformUpdater scale(double scalar) {
      this.scale = this.scale.scale(scalar);
      return this;
    }

    /**
     * Rotate transform.
     *
     * @param rotation Rotation in XYZ degrees
     *
     * @return transform update
     */
    public TransformUpdater rotate(Vector rotation) {
      this.rotation = this.rotation.add(rotation);
      return this;
    }

    /**
     * Update transform, call when done with the update.
     */
    public void done() {
      transform.position = this.position;
      transform.scale = this.scale;
      transform.rotation = new Vector(
              this.rotation.get(0) % 360.0,
              this.rotation.get(1) % 360.0,
              this.rotation.get(2) % 360.0
      );

      transform.updateMatrices();
    }
  }
}
