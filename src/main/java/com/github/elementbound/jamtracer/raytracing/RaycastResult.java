package com.github.elementbound.jamtracer.raytracing;

import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.raytracing.shape.Shape;

/**
 * Class to represent a raycast result.
 */
public record RaycastResult(boolean isHit, Shape shape, double distance, Vector point,
                            Vector normal,
                            Vector texcoords) {
  public static RaycastResult NO_HIT =
          new RaycastResult(false, null, -1.0, Vector.ZERO, Vector.ZERO, Vector.ZERO);
}
