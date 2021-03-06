package scene;

import spacefiller.remote.Mod;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PJOGL;

import java.util.ArrayList;
import java.util.List;

public class SceneApplet extends PApplet {
  public static int WIDTH = 1920;
  public static int HEIGHT = 1080;

  protected Scene currentScene;
  protected int currentSceneIndex;
  protected List<Scene> scenes;
  protected PGraphics canvas;

  public SceneApplet() {
    scenes = new ArrayList<>();
  }

  public void settings() {
    // fullScreen(2);
    size(1920, 1080, P3D);
    PJOGL.profile = 1;
  }

  public void setup() {
    setCanvas(getGraphics());
    switchScene(0);
  }

  public void draw() {
    canvas.background(0);
    if (currentScene != null) {
      currentScene.draw(this.canvas);
    }
  }

  public void switchScene(int sceneIndex) {
    if (currentScene != null && currentScene.alwaysReset()) {
      currentScene.teardown();
    }

    if (sceneIndex < scenes.size()) {
      Scene scene = scenes.get(sceneIndex);
      currentSceneIndex = sceneIndex;

      if (!scene.isSetup() || scene.alwaysReset()) {
        scene.setup();
      }

      currentScene = scene;
    }
  }

  @Mod
  public void gotoNextScene() {
    switchScene((currentSceneIndex + 1) % scenes.size());
  }

  public void addScene(Scene scene) {
    scene.setDimensions(width, height);
    scenes.add(scene);
  }

  public final void addAllScenes(Scene[] sceneArray) {
    for (int i = 0; i < sceneArray.length; i++) {
      addScene(sceneArray[i]);
    }
  }

  public void setCanvas(PGraphics canvas) {
    this.canvas = canvas;
  }
}
