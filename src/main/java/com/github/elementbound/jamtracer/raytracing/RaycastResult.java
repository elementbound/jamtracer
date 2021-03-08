package com.github.elementbound.jamtracer.raytracing;

import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.raytracing.shape.Shape;

/**
 * Class to represent a raycast result.
 */
public record RaycastResult(boolean isHit, Shape shape, Vector point, Vector normal,
                            Vector texcoords) {
  public static RaycastResult NO_HIT =
          new RaycastResult(false, null, Vector.ZERO, Vector.ZERO, Vector.ZERO);
}
