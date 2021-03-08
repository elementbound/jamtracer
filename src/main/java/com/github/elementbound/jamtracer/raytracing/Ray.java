package com.github.elementbound.jamtracer.raytracing;

import com.github.elementbound.jamtracer.core.Vector;
import java.util.Objects;

/**
 * Immutable class representing a ray.
 */
public class Ray {
  private final Vector from;
  private final Vector direction;

  /**
   * Construct a ray.
   *
   * @param from      ray origin
   * @param direction ray direction
   */
  public Ray(Vector from, Vector direction) {
    this.from = from;
    this.direction = direction.normalized();
  }

  /**
   * Get ray origin.
   *
   * @return ray origin
   */
  public Vector getFrom() {
    return from;
  }

  /**
   * Get ray direction.
   * <p>The direction is <em>always</em> a normal vector.</p>
   *
   * @return ray direction
   */
  public Vector getDirection() {
    return direction;
  }

  /**
   * Get point along ray.
   *
   * @param distance distance from origin
   *
   * @return point
   */
  public Vector getPoint(double distance) {
    return from.add(direction.scale(distance));
  }

  /**
   * Create a look-at ray.
   *
   * @param from ray origin
   * @param at   ray target
   *
   * @return ray
   */
  public static Ray lookat(Vector from, Vector at) {
    return new Ray(
            from,
            at.subtract(from)
    );
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Ray{");
    sb.append("from=").append(from);
    sb.append(", direction=").append(direction);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Ray ray = (Ray) o;

    return this.from.equals(ray.from)
            && this.direction.equals(ray.direction);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, direction);
  }
}
