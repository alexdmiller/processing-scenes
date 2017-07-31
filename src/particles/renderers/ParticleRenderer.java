package particles.renderers;

import particles.Particle;
import particles.ParticleEventListener;
import processing.core.PGraphics;

import java.util.List;

/**
 * Created by miller on 7/12/17.
 */
public abstract class ParticleRenderer implements ParticleEventListener {
  protected List<Particle> particles;

  abstract public void render(PGraphics graphics);

  public void setParticles(List<Particle> particles) {
    this.particles = particles;
  }

  public void particleAdded(Particle particle) {}
  public void particleRemoved(Particle particle) {}
}
