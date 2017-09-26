package common.components;

import processing.core.PGraphics;
import processing.core.PVector;
import scene.SceneComponent;
import spacefiller.remote.Mod;
import veins.Tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by miller on 9/25/17.
 */
public class TreeComponent extends SceneComponent {
  @Mod(min=1, max=10)
  public float growthSpeed = 5;

  @Mod(min=10, max=200)
  public float attractorKillRadius = 5;

  @Mod(min=50, max=60)
  public float attractorInfluenceRadius = 50;

  @Mod(min=0, max=20)
  public float edgeThickness = 5;

  @Mod(min=2, max=10)
  public float pulsePeriod = 5;

  @Mod(min=0, max=1000)
  public float pulseLife = 500;

  private List<PVector> attractors;
  private Tree tree;

  public TreeComponent() {
    attractors = new ArrayList<PVector>();
    tree = new Tree();
  }

  public void addAttractor(PVector pos) {
    synchronized (tree) {
      attractors.add(pos);
    }
  }

  public void addNode(PVector pos) {
    synchronized (tree) {
      tree.addNode(pos);
    }
  }

  public int numNodes() {
    return tree.getNodes().size();
  }

  @Override
  public void draw(PGraphics graphics) {
    synchronized (tree) {
      tree.grow(attractors, attractorInfluenceRadius, attractorKillRadius, growthSpeed);
      drawTree(tree, graphics);
      // drawAttractors(attractors, graphics);
    }
  }

  void drawTree(Tree tree, PGraphics graphics) {
    graphics.stroke(255);

    Iterator<Tree.Edge> edges = tree.edges.iterator();
    while (edges.hasNext()) {
      Tree.Edge edge = edges.next();
      if (pulseLife > 0 && edge.age >= pulseLife) {
        edges.remove();
      } else {
        float w = edgeThickness * ageToThickness(edge.age);
        if (w > 0) {
          graphics.strokeWeight(w);
          graphics.line(edge.n1.v.x, edge.n1.v.y, edge.n2.v.x, edge.n2.v.y);
        }
        edge.age++;
      }
    }

    Iterator<Tree.Node> nodes = tree.nodes.iterator();
    while (nodes.hasNext()) {
      Tree.Node node = nodes.next();
      if (node.age >= pulseLife) {
        nodes.remove();
      } else {
        node.age++;
      }
    }
  }

  void drawAttractors(List<PVector> attractors, PGraphics graphics) {
    graphics.strokeWeight(5);
    for (PVector attractor : attractors) {
      graphics.stroke(255);
      graphics.point(attractor.x, attractor.y);
    }
  }

  float ageToThickness(int age) {
    return (float) Math.max(0, Math.sin(age * (Math.PI / pulsePeriod)));
  }

  public int numAttractors() {
    return attractors.size();
  }
}
