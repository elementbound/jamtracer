package com.github.elementbound.jamtracer.display;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.ColorUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class AWTWindowDisplay implements Display {
  private final Window window;
  private final Graphics graphics;
  private final BufferedImage image;
  private final int width;
  private final int height;

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
    graphics.drawImage(image, 0, 0, new ImageObserver() {
      @Override
      public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
      }
    });
  }
}
