package scenes;

import codeanticode.syphon.SyphonServer;
import oscP5.OscP5;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PJOGL;

import java.util.ArrayList;
import java.util.List;

// TODO: how to handle ports?
// TODO: how to handle scenes?

public class Scene extends PApplet {
	private static PApplet instance;

	public static PApplet getInstance() {
		return instance;
	}

	protected static final int WIDTH = 1920;
	protected static final int HEIGHT = 1080;
	private static final float LOCAL_WINDOW_SCALE = 0.5f;
	private static final char PLAY_KEY = ' ';
	private static final char NEXT_TOOL_KEY = 39;
	private static final char PREV_TOOL_KEY = 37;
	private static final char DEBUG_TOGGLE = 'd';
	private static final int CLEAR_KEY = 8;

	private SyphonServer server;

	private PGraphics canvas;
	private boolean playing = true;
	private boolean debug = true;
	private int currentToolIndex;
	private List<SceneTool> tools;

	public Scene() {
		currentToolIndex = 0;
		tools = new ArrayList<>();

		Scene.instance = this;
	}

	protected void addSceneTool(SceneTool tool) {
		tools.add(tool);
	}

	public void settings() {
		size((int) (WIDTH * LOCAL_WINDOW_SCALE), (int) (HEIGHT * LOCAL_WINDOW_SCALE), P3D);
		PJOGL.profile = 1;
	}

	public final void setup() {
		canvas = createGraphics(WIDTH, HEIGHT, P3D);
		server = new SyphonServer(this, this.getClass().getName());
		frameRate(60);
		doSetup();
	}

	protected void doSetup() {}

	public final void draw() {
		background(0);
		if (playing) {
			canvas.beginDraw();
			canvas.background(0);
			drawCanvas(canvas, mouseX / LOCAL_WINDOW_SCALE, mouseY / LOCAL_WINDOW_SCALE);

			if (debug) {
				canvas.pushMatrix();
				canvas.translate(mouseX / LOCAL_WINDOW_SCALE, mouseY / LOCAL_WINDOW_SCALE);
				canvas.stroke(255);
				canvas.line(-10, 0, 10, 0);
				canvas.line(0, -10, 0, 10);
				canvas.popMatrix();

				drawControlPanel(canvas, mouseX / LOCAL_WINDOW_SCALE, mouseY / LOCAL_WINDOW_SCALE);
				tools.get(currentToolIndex).render(canvas, mouseX / LOCAL_WINDOW_SCALE, mouseY / LOCAL_WINDOW_SCALE, mousePressed);
			}

			canvas.endDraw();
		}

		image(canvas, 0, 0, width, height);

		textSize(16);
		fill(255);
		text(frameRate, 10, 30);
		text(tools.get(currentToolIndex).toString(), 10, height - 20);

		pushMatrix();
		translate(width / 2, height - 20);
		noStroke();
		if (playing) {
			fill(0, 255, 0);
			triangle(-10, -10, 10, 0, -10, 10);
		} else {
			fill(255, 0, 0);
			rectMode(CORNER);
			rect(-10, -10, 20, 20);
		}
		popMatrix();

		server.sendImage(canvas);
	}

	protected void drawCanvas(PGraphics graphics, float mouseX, float mouseY) {}
	protected void drawControlPanel(PGraphics graphics, float mouseX, float mouseY) {}

	public final void mousePressed() {
		tools.get(currentToolIndex).mousePressed(mouseX / LOCAL_WINDOW_SCALE, mouseY / LOCAL_WINDOW_SCALE);
	}

	public final void mouseReleased() {
		tools.get(currentToolIndex).mouseReleased(mouseX / LOCAL_WINDOW_SCALE, mouseY / LOCAL_WINDOW_SCALE);
	}

	public final void mouseDragged() {
		tools.get(currentToolIndex).mouseDragged(mouseX / LOCAL_WINDOW_SCALE, mouseY / LOCAL_WINDOW_SCALE);
	}

	public final void keyPressed() {
		if (key == PLAY_KEY) {
			playing = !playing;
		} else if (keyCode == NEXT_TOOL_KEY) {
			currentToolIndex = (currentToolIndex + 1) % tools.size();
		} else if (keyCode == PREV_TOOL_KEY) {
			currentToolIndex = (((currentToolIndex - 1) % tools.size()) + tools.size()) % tools.size();
		} else if (key == DEBUG_TOGGLE) {
			debug = !debug;
		} else if (keyCode == CLEAR_KEY) {
			tools.get(currentToolIndex).clear();
		}

		tools.get(currentToolIndex).keyDown(key);

		doKeyPressed();
	}

	protected void doKeyPressed() {}
}
