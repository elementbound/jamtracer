package com.github.elementbound.jamtracer.raytracing.camera;

import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.raytracing.Ray;
import com.github.elementbound.jamtracer.raytracing.Transform;

/**
 * Perspective implementation of {@link Camera}.
 */
public class PerspectiveCamera implements Camera {
  private double aspectRatio;
  private double fieldOfView;
  private Transform transform;

  /**
   * Construct camera with default settings.
   */
  public PerspectiveCamera() {
    aspectRatio = 1.0;
    fieldOfView = Math.toRadians(60.0);
    transform = new Transform();
  }

  /**
   * Construct camera with settings.
   *
   * @param aspectRatio aspect ratio
   * @param fieldOfView field of view in radians
   */
  public PerspectiveCamera(double aspectRatio, double fieldOfView) {
    this.aspectRatio = aspectRatio;
    this.fieldOfView = fieldOfView;
    transform = new Transform();
  }

  /**
   * Get aspect ratio.
   *
   * @return aspect ratio
   */
  public double getAspectRatio() {
    return aspectRatio;
  }

  /**
   * Set aspect ratio.
   *
   * @param aspectRatio aspect ratio
   */
  public void setAspectRatio(double aspectRatio) {
    this.aspectRatio = aspectRatio;
  }

  /**
   * Set aspect ratio based on viewport size.
   *
   * @param width  viewport width
   * @param height viewport height
   */
  public void setAspectRatio(double width, double height) {
    this.aspectRatio = width / height;
  }

  /**
   * Get field of view in degrees.
   *
   * @return field of view
   */
  public double getFieldOfView() {
    return fieldOfView;
  }

  /**
   * Set field of view in degrees.
   *
   * @param fieldOfView field of view
   */
  public void setFieldOfView(double fieldOfView) {
    this.fieldOfView = fieldOfView;
  }

  @Override
  public Ray getRay(Vector texcoords) {
    return new Ray(Vector.ZERO, Vector.FORWARD);
  }

  @Override
  public Transform getTransform() {
    return transform;
  }

  @Override
  public void setTransform(Transform transform) {
    this.transform = transform;
  }
}
