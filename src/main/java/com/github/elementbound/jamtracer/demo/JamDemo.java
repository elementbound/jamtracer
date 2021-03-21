package com.github.elementbound.jamtracer.demo;

import com.github.elementbound.jamtracer.raytracing.Raytracer;

/**
 * Interface representing demo scenes to show off Jamtracer.
 */
public interface JamDemo {
  /**
   * Update scene once the frame has been rendered.
   * <p>Typically this includes animation updates.</p>
   *
   * @param raytracer raytracer that will be used to render frame
   */
  void update(Raytracer raytracer);
}
