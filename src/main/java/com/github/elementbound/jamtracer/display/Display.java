package com.github.elementbound.jamtracer.display;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.event.EventSource;

/**
 * Display for image presentation.
 */
public interface Display {
  /**
   * Set pixel color.
   *
   * @param x     pixel's X coordinate
   * @param y     pixel's Y coordinate
   * @param color pixel color
   */
  void setPixel(int x, int y, Color color);

  /**
   * Get pixel color.
   *
   * @param x pixel's X coordinate
   * @param y pixel's Y coordinate
   *
   * @return pixel's color
   */
  Color getPixel(int x, int y);

  /**
   * Get display width.
   *
   * @return display width
   */
  int getWidth();

  /**
   * Get display height.
   *
   * @return display height
   */
  int getHeight();

  /**
   * Check if display is open and able to present.
   *
   * @return true if open
   */
  boolean isOpen();

  /**
   * Event source for display close.
   *
   * @return event source
   */
  EventSource<Void> onClose();

  /**
   * Update display with modified pixels.
   */
  void present();
}
