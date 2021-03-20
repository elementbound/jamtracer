package com.github.elementbound.jamtracer.raytracing.shape;

import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.raytracing.Ray;
import com.github.elementbound.jamtracer.raytracing.RaycastResult;
import com.github.elementbound.jamtracer.raytracing.Transform;

/**
 * Shape representing a unit-sphere at origin.
 * <p>Use the transform to move or resize the sphere.</p>
 */
public class SphereShape implements Shape {
  private Transform transform;

  public SphereShape() {
    transform = new Transform();
  }

  @Override
  public Transform getTransform() {
    return transform;
  }

  @Override
  public void setTransform(Transform transform) {
    this.transform = transform;
  }

  @Override
  public RaycastResult raycast(Ray ray) {
    var localRay = transform.inverseTransformRay(ray);

    var localDirection = localRay.getDirection();
    var localOrigin = localRay.getFrom();

    // See: https://viclw17.github.io/2018/07/16/raytracing-ray-sphere-intersection/
    var a = localDirection.dot(localDirection);
    var b = 2.0 * localOrigin.dot(localDirection);
    var c = localOrigin.dot(localOrigin) - 1.0;

    var discriminant = b * b - 4 * a * c;

    if (discriminant < 0.0) {
      return RaycastResult.NO_HIT;
    } else {
      var t1 = -b + Math.sqrt(discriminant) / 2.0 * a;
      var t2 = -b - Math.sqrt(discriminant) / 2.0 * a;
      var t = 0.0;

      if (t1 < 0.0 && t2 < 0.0) {
        return RaycastResult.NO_HIT;
      } else if (Math.signum(t1) != Math.signum(t2)) {
        t = Math.max(t1, t2);
      } else {
        t = Math.min(t1, t2);
      }

      var localPoint = localRay.getPoint(t);
      var hitPoint = transform.getMatrix().transform(localPoint.asHeterogeneous()).asHomogeneous();
      var normal = localPoint.normalized();
      var texcoords = new Vector(
              (Math.PI + Math.atan2(normal.get(1), normal.get(0))) / (2.0 * Math.PI),
              (Math.PI / 2.0 + Math.asin(normal.get(2))) / Math.PI
      );
      normal = transform.getMatrix().transform(normal.asHeterogeneousNormal()).asHomogeneous();
      double distance = Vector.distance(ray.getFrom(), hitPoint);

      return new RaycastResult(true, this, distance, hitPoint, normal, texcoords);
    }
  }
}
