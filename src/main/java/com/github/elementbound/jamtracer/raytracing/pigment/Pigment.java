package com.github.elementbound.jamtracer.raytracing.pigment;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.Vector;

/**
 * Provides color detail to surfaces.
 * <p>Pigments are by default dimension-agnostic, but implementations may pose specific
 * restrictions.</p>
 */
public interface Pigment {
  /**
   * Evaluate pigment at given point.
   *
   * @param texcoords texture-space coordinates
   *
   * @return pigment color
   */
  Color evaluate(Vector texcoords);
}
