package com.github.elementbound.jamtracer.raytracing.camera;

import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.raytracing.Ray;

/**
 * Perspective implementation of {@link Camera}.
 */
public class PerspectiveCamera implements Camera {
  private double aspectRatio;
  private double fieldOfView;

  /**
   * Construct camera with default settings.
   */
  public PerspectiveCamera() {
    aspectRatio = 1.0;
    fieldOfView = Math.toRadians(60.0);
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
   * Get field of view.
   *
   * @return field of view
   */
  public double getFieldOfView() {
    return fieldOfView;
  }

  /**
   * Set field of view.
   *
   * @param fieldOfView field of view
   */
  public void setFieldOfView(double fieldOfView) {
    this.fieldOfView = fieldOfView;
  }

  @Override
  public Ray getRay(Vector texcoords) {
    double planeHeight = Math.tan(this.fieldOfView / 2.0);
    double planeWidth = planeHeight * aspectRatio;

    Vector target = new Vector(
            (2.0 * texcoords.get(0) - 1.0) * planeWidth,
            1.0,
            (2.0 * texcoords.get(0) - 1.0) * planeHeight
    );

    return Ray.lookat(Vector.ZERO, target);
  }
}
