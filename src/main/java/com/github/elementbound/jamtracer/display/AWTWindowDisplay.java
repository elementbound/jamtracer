package com.github.elementbound.jamtracer.display;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.ColorUtils;
import com.github.elementbound.jamtracer.event.EventSource;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
  private EventSource<Void> onClose;
  private boolean isOpen;

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
    this.onClose = new EventSource<>();
    this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);

    this.window.setSize(this.width, this.height);
    this.window.setVisible(true);
    this.graphics = this.window.getGraphics();

    this.window.addWindowListener(new WindowAdapter() {
      @Override
      public void windowOpened(WindowEvent e) {
        super.windowOpened(e);
        isOpen = true;
      }

      @Override
      public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        isOpen = false;
        window.setVisible(false);
        onClose.emit(null);
      }
    });
  }

  @Override
  public void setPixel(int x, int y, Color color) {
    this.image.setRGB(x, y, ColorUtils.asRGB(color));
  }

  @Override
  public Color getPixel(int x, int y) {
    return ColorUtils.fromRGB(this.image.getRGB(x, y));
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public boolean isOpen() {
    return isOpen;
  }

  @Override
  public EventSource<Void> onClose() {
    return onClose;
  }

  @Override
  public void present() {
    graphics.drawImage(image, 0, 0, (img, infoflags, x, y, width, height) -> false);
  }
}
