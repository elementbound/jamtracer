package com.github.elementbound.jamtracer.display;

import com.github.elementbound.jamtracer.core.Color;
import com.github.elementbound.jamtracer.core.ColorUtils;
import com.github.elementbound.jamtracer.event.EventSource;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

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

    isOpen = true;

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

    this.window.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        present();
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
    var size = window.getSize();
    var scale = Math.min(
            (double) size.width / image.getWidth(),
            (double) size.height / image.getHeight()
    );

    var destW = image.getWidth() * scale;
    var destH = image.getHeight() * scale;
    var destX = (size.width - destW) / 2.0;
    var destY = (size.height - destH) / 2.0;

    graphics.drawImage(image, (int) destX, (int) destY, (int) (destX + destW), (int) (destY + destH),
            0, 0, image.getWidth(), image.getHeight(),
            (img, infoflags, x, y, width, height) -> false);
  }
}
