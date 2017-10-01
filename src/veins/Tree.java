package veins;

import processing.core.PVector;

import java.util.*;

public class Tree {
	public List<Edge> edges;
	public List<Node> nodes;
	private boolean active;

	public Tree() {
		edges = new ArrayList<Edge>();
		nodes = new ArrayList<Node>();
	}

	public boolean isActive() {
		return active;
	}

	public void grow(
			List<PVector> attractors,
			float attractorInfluenceRadius,
			float attractorKillRadius,
			float growthSpeed) {
		active = false;
		Map<Node, PVector> forces = new HashMap<Node, PVector>();
		List<PVector> attractorsToRemove = new ArrayList<PVector>();

		for (PVector attractor : attractors) {

			// Find the closest node to attractor.
			Node closest = null;
			for (Node node : nodes) {
				float dist = attractor.dist(node.v);
				if (dist < attractorInfluenceRadius &&
						(closest == null ||
								dist < attractor.dist(closest.v))) {
					closest = node;
				}

				if (dist < attractorKillRadius) {
					attractorsToRemove.add(attractor);
				}
			}

			// Apply a force to the nearest node.
			if (closest != null) {
				if (!forces.containsKey(closest)) {
					forces.put(closest, new PVector(0, 0));
					active = true;
				}
				PVector diff = new PVector();
				diff.set(attractor);
				diff.sub(closest.v);
				diff.normalize();
				forces.get(closest).add(diff);
			}
		}

		attractors.removeAll(attractorsToRemove);

		List<Node> newNodes = new ArrayList<Node>();
		for (Node node : nodes) {
			if (forces.containsKey(node)) {
				PVector force = forces.get(node);
				force.normalize();
				force.mult((float) (growthSpeed + Math.random() * 2 - 1));
				force.add(node.v);

				Node n = new Node(force);

				newNodes.add(n);
				edges.add(new Edge(node, n));
			}
		}

		nodes.addAll(newNodes);
	}

	public void addNode(PVector n) {
		nodes.add(new Node(n));
	}

	public List<Node> getNodes() {
		return nodes;
	}

  public static class Node {
		public PVector v;
		public int age;

		Node(PVector v) {
			this.v = v;
		}

		public String toString() {
			return this.v.toString() + " " + String.valueOf(this.v.hashCode());
		}
	}

	public static class Edge {
		public Node n1, n2;
		public int age;

		Edge(Node n1, Node n2) {
			this.n1 = n1;
			this.n2 = n2;
		}
	}
}

