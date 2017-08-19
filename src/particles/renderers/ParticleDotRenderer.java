package particles.renderers;

import color.ColorProvider;
import lusio.Lusio;
import particles.Particle;
import processing.core.PGraphics;

import java.awt.*;
import java.util.List;

/**
 * Created by miller on 7/12/17.
 */
public class ParticleDotRenderer extends ParticleRenderer {
  private float dotSize;

  public ParticleDotRenderer(float dotSize, ColorProvider colorProvider) {
    this.dotSize = dotSize;
    this.colorProvider = colorProvider;
  }

  @Override
  public void render(PGraphics graphics) {
    graphics.strokeWeight(this.dotSize);
    int i = 0;
    for (Particle p : particles) {
      graphics.stroke(colorProvider.getColor(i));
      graphics.point(p.position.x, p.position.y, p.position.z);
      i++;
    }
  }

  public void setDotSize(float dotSize) {
    this.dotSize = dotSize;
  }
}
