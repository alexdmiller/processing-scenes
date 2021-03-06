package lusio.scenes;

import spacefiller.graph.renderer.DottedLineGraphRenderer;
import lusio.Lusio;
import lusio.components.ParticleComponent;
import particles.Bounds;
import particles.behaviors.FlockParticles;
import particles.renderers.GraphParticleRenderer;
import particles.renderers.ParticleDotRenderer;
import processing.core.PGraphics;

public class FancyParticles extends LusioScene {
  ParticleComponent particleGenerator;
  FlockParticles flockParticles;
  GraphParticleRenderer graphParticleRenderer;
  DottedLineGraphRenderer dottedLineGraphRenderer;

  @Override
  public void setup() {
    particleGenerator = ParticleComponent.withReflectiveBounds(50, new Bounds(Lusio.WIDTH, Lusio.HEIGHT), 2);
    particleGenerator.setPos(Lusio.WIDTH / 2, Lusio.HEIGHT / 2);

    dottedLineGraphRenderer = new DottedLineGraphRenderer();
    dottedLineGraphRenderer.setThickness(4);
    graphParticleRenderer =  new GraphParticleRenderer(dottedLineGraphRenderer);
    particleGenerator.addRenderer(graphParticleRenderer);
    particleGenerator.addRenderer(new ParticleDotRenderer(20, Lusio.instance));

//    particleGenerator.addBehavior(new AttractParticles(200, 0.1f));
//    particleGenerator.addBehavior(new RepelParticles(100, 0.5f));
//    particleGenerator.addBehavior(new ParticleFriction(0.95f));

    flockParticles = new FlockParticles(1, 0.5f, 0.5f, 100, 100, 100, 1f, 5);
    particleGenerator.addBehavior(flockParticles);

    addComponent(particleGenerator);
  }

  @Override
  public void draw(PGraphics graphics) {
    dottedLineGraphRenderer.setColor(cube.getColor());
    float[] euler = cube.getNormalizedEuler();

    flockParticles.setMaxSpeed(cube.getCounter() * 20 + 5);
    flockParticles.setDesiredSeparation(euler[1] * 100 + 10);
    flockParticles.setCohesionThreshold(euler[0] * 100 + 10);
    // particleWebRenderer.setLineThreshold(Math.min(cube.getRotationalVelocity() * 10, 100));

    super.draw(graphics);
  }

  @Override
  public boolean transitionOut() {
    return true;
  }
}
