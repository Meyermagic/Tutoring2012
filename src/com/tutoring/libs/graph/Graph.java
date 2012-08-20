package com.tutoring.libs.graph;


import java.util.HashMap;
import java.util.TreeMap;

public class Graph<V> {

    private HashMap<Integer, V> nodes;
    private HashMap<Integer, TreeMap<Integer, Integer>> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
    }

    public void addNode(int node, V value) {
        nodes.put(node, value);
        edges.put(node, new TreeMap<Integer, Integer>());
    }

    public void addEdge(int start, int end, int weight) {
        assert nodes.containsKey(start);
        assert nodes.containsKey(end);
        edges.get(start).put(end, weight);
    }

    public void removeEdge(int start, int end) {
        assert nodes.containsKey(start);
        assert nodes.containsKey(end);
        assert edges.containsKey(end);
        edges.remove(end);
    }
}
