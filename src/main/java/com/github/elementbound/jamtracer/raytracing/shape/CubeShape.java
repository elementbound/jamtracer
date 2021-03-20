package com.github.elementbound.jamtracer.raytracing.shape;

import com.github.elementbound.jamtracer.core.Vector;
import com.github.elementbound.jamtracer.raytracing.Ray;
import com.github.elementbound.jamtracer.raytracing.RaycastResult;
import com.github.elementbound.jamtracer.raytracing.Transform;

/**
 * Shape representing a unit-cube at origin.
 * <p>Use the transform to move or resize the cube.</p>
 */
public class CubeShape implements Shape {
  private Transform transform;

  public CubeShape() {
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
    // Based on: https://tavianator.com/2011/ray_box.html
    var localRay = transform.inverseTransformRay(ray);

    var tmin = Double.NEGATIVE_INFINITY;
    var tmax = Double.POSITIVE_INFINITY;

    for (int i = 0; i < localRay.getDirection().dimensions(); i++) {
      var t1 = (+1.0 - localRay.getFrom().get(i)) / localRay.getDirection().get(i);
      var t2 = (-1.0 - localRay.getFrom().get(i)) / localRay.getDirection().get(i);

      tmin = Math.max(tmin, Math.min(t1, t2));
      tmax = Math.min(tmax, Math.max(t1, t2));
    }

    if (tmax >= tmin && tmin >= 0.0) {
      Vector localPoint = localRay.getPoint(tmin);
      Vector normal = Vector.ZERO;
      Vector texcoords = Vector.ZERO;

      var absVector = localPoint.map(Math::abs);

      if (absVector.get(0) > absVector.get(1) && absVector.get(0) > absVector.get(2)) {
        normal = new Vector(Math.signum(localPoint.get(0)), 0.0, 0.0);
        texcoords = new Vector(localPoint.get(1), localPoint.get(2));
      } else if (absVector.get(1) > absVector.get(0) && absVector.get(1) > absVector.get(2)) {
        normal = new Vector(0.0, Math.signum(localPoint.get(1)), 0.0);
        texcoords = new Vector(localPoint.get(0), localPoint.get(2));
      } else if (absVector.get(2) > absVector.get(0) && absVector.get(2) > absVector.get(1)) {
        normal = new Vector(0.0, 0.0, Math.signum(localPoint.get(2)));
        texcoords = new Vector(localPoint.get(0), localPoint.get(1));
      }

      normal = transform.getMatrix().transform(normal.asHeterogeneousNormal()).asHomogeneous();
      texcoords = texcoords.map(v -> (1.0 + v) / 2.0);

      Vector point = transform.getMatrix().transform(localPoint.asHeterogeneous()).asHomogeneous();
      double distance = Vector.distance(ray.getFrom(), point);

      return new RaycastResult(true, this, distance, point, normal, texcoords);
    } else {
      return RaycastResult.NO_HIT;
    }
  }
}
