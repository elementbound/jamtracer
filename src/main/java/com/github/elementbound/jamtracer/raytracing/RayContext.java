package com.github.elementbound.jamtracer.raytracing;

import com.github.elementbound.jamtracer.raytracing.shape.scene.Scene;

/**
 * Record for carrying ray context information.
 *
 * @param raytracer     raytracer that this context belongs to
 * @param ray           currently traced ray
 * @param scene         currently traced scene
 * @param raycastResult raycast result of the current ray
 * @param depth         ray depth
 */
public record RayContext(Raytracer raytracer, Ray ray, Scene scene, RaycastResult raycastResult,
                         int depth) {
}
