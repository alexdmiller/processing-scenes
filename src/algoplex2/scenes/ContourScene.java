package algoplex2.scenes;

import algoplex2.Algoplex2;
import common.color.ConstantColorProvider;
import common.components.ContourComponent;
import graph.*;
import lusio.Lusio;
import modulation.Mod;
import particles.Bounds;
import processing.core.PGraphics;
import toxi.geom.Quaternion;
import toxi.geom.Vec3D;

public class ContourScene extends GridScene {
  @Mod
  public ContourComponent contourComponent;

  @Mod
  public float stripes;

  @Override
  public void setup() {
    contourComponent = new ContourComponent(
        new Bounds(grid.getHeight() + grid.getCellSize() * 4, grid.getWidth() + grid.getCellSize()));
    contourComponent.setColor(0xFFFFFFFF);
    contourComponent.setPos(0, 0);
    // contourComponent.setRotation(Quaternion.createFromEuler((float) (Math.PI / 2), 0, 0));
    contourComponent.setCellSize(grid.getCellSize() * 2);
    contourComponent.setNoiseScale(0.5f);
    contourComponent.setLineSize(3);
    contourComponent.setNoiseAmplitude(1000);
    contourComponent.setUpdateSpeed(0.001f);
    contourComponent.setHeightIncrements(10);
    addComponent(contourComponent);
  }

  @Override
  public void draw(PGraphics graphics) {
    graphics.ortho();
    contourComponent.setColor((int) (stripes * 0xFFFFFFFF));
//    contourComponent.setNoiseAmplitude(controller.getValue(0) * 5000);
//    contourComponent.setNoiseScale(controller.getValue(1) * 10 + 0.01f);
//    contourComponent.setUpdateSpeed(controller.getValue(2) / 10f);
//    contourComponent.setXSpeed(controller.getValue(3) / 10f);


    super.draw(graphics);
  }
}
