package boids.renderers;

import boids.Flock;
import boids.Magnet;
import boids.emitter.Emitter;
import boids.emitter.LineEmitter;
import boids.emitter.PointEmitter;
import common.StoredVectorField;
import javafx.util.Pair;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PVector;

import java.awt.*;
import java.util.List;

public class DebugFlockRenderer extends FlockRenderer {
	public DebugFlockRenderer(Flock flock) {
		super(flock);
	}

	public void render(PGraphics graphics) {
		renderFollowPathBehavior(graphics, flock.getPathSegments());
		renderMagnetBehavior(graphics, flock.getMagnets());
		renderEmitBehavior(graphics, flock.getEmitters());
		renderFlowField(graphics, flock);

		graphics.stroke(255);
		graphics.strokeWeight(2);
		Rectangle bounds = flock.getBounds();
		graphics.noFill();
		graphics.rect(bounds.x, bounds.y, bounds.width, bounds.height);

		graphics.stroke(255);
		graphics.textSize(24);
		graphics.text(flock.getBoids().size(), 100, 100);
	}

	private void renderEmitBehavior(PGraphics graphics, List<Emitter> emitters) {
		graphics.stroke(0, 255, 0);
		graphics.noFill();
		graphics.strokeWeight(2);
		for (Emitter e : emitters) {
			if (e instanceof LineEmitter) {
				LineEmitter le = (LineEmitter) e;
				graphics.line(le.getP1().x, le.getP1().y, le.getP2().x, le.getP2().y);
			} else if (e instanceof PointEmitter) {
				PointEmitter pe = (PointEmitter) e;
				graphics.pushMatrix();
				graphics.translate(pe.getPosition().x, pe.getPosition().y);
				graphics.line(-10, 0, 10, 0);
				graphics.line(0, -10, 0, 10);
				graphics.popMatrix();
			}
		}
	}

	private void renderFollowPathBehavior(PGraphics canvas, List<Pair<PVector, PVector>> pathPoints) {
		canvas.noFill();
		canvas.stroke(255);
		canvas.strokeCap(PConstants.ROUND);
		canvas.strokeWeight(5);
		for (Pair<PVector, PVector> pair : pathPoints) {
			PVector p1 = pair.getKey();
			PVector p2 = pair.getValue();
			canvas.line(p1.x, p1.y, p2.x, p2.y);
		}
	}

	private void renderMagnetBehavior(PGraphics canvas, List<Magnet> magnets) {
		for (Magnet m : magnets) {
			canvas.noStroke();
			canvas.fill(255, 0, 0);
			canvas.ellipse(m.position.x, m.position.y, 10, 10);

			canvas.noFill();
			canvas.stroke(255, 0, 0);
			canvas.ellipse(m.position.x, m.position.y, m.radius * 2, m.radius * 2);
		}
	}

	private void renderFlowField(PGraphics canvas, Flock flock) {
		canvas.stroke(255);

		StoredVectorField field = (StoredVectorField) flock.getFlowField();

		for (int x = 0; x < flock.getFlowFieldWidth(); x++) {
			for (int y = 0; y < flock.getFlowFieldHeight(); y++) {
				PVector v = field.getCell(x, y);
				canvas.pushMatrix();
				float posX = x * field.getCellSize() + flock.getBounds().x + field.getCellSize() / 2;
				float posY = y * field.getCellSize() + flock.getBounds().y + field.getCellSize() / 2;
				canvas.translate(posX, posY);
				canvas.rotate(v.heading());
				canvas.line(0, 0, v.mag(), 0);
				canvas.popMatrix();
			}
		}
	}
}
