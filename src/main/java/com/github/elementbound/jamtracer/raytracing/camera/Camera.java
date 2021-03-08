package com.github.elementbound.jamtracer.raytracing.camera;

import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.raytracing.Ray;

/**
 * Interface to represent cameras.
 */
public interface Camera {
  /**
   * Get ray corresponding to given texture coordinates.
   * <p>Coordinates are expected to be 2D with both components in the [0,1] range.</p>
   *
   * @param texcoords texture coordinates
   *
   * @return ray
   */
  Ray getRay(Vector texcoords);
}
