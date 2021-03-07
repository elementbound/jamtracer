package com.github.elementbound.jamtracer.display;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.ColorUtils;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.image.BufferedImage;

/**
 * AWT-based implementation of {@link Display}.
 *
 * <p>Images are presented in a window.</p>
 */
public class AWTWindowDisplay implements Display {
  private final Window window;
  private final Graphics graphics;
  private final BufferedImage image;
  private final int width;
  private final int height;

  /**
   * Create a new {@link AWTWindowDisplay} with given size.
   *
   * @param width  display width
   * @param height display height
   */
  public AWTWindowDisplay(int width, int height) {
    this.width = width;
    this.height = height;
    this.window = new Frame("Jamtracer");
    this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);

    this.window.setSize(this.width, this.height);
    this.window.setVisible(true);
    this.graphics = this.window.getGraphics();
  }

  @Override
  public void setPixel(int x, int y, Color color) {
    this.image.setRGB(x, y, ColorUtils.asRGB(color));
  }

  @Override
  public Color getPixel(int x, int y) {
    return ColorUtils.fromRGB(this.image.getRGB(x, y));
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  @Override
  public void present() {
    graphics.drawImage(image, 0, 0, (img, infoflags, x, y, width, height) -> false);
  }
}
