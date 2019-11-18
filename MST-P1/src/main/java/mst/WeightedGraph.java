package mst;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class WeightedGraph {
    private final int verticesAmount;
    private final int edgesAmount;
    //linked list  for each vertex
    private LinkedList<Edge>[] adj;

    public WeightedGraph(int verticesAmount, String file) {
        if(verticesAmount < 0) {
            throw new IllegalArgumentException("Number of vertices  must be greater 0");
        }

        this.verticesAmount = verticesAmount;
        adj = (LinkedList<Edge>[]) new LinkedList[verticesAmount];
        for(int i = 0; i < verticesAmount; i++) {
            adj[i] = new LinkedList<Edge>();
        }
        //read data from file
        //File paramFile = new File(getClass().getClassLoader().getResource("data.txt").getFile());
        File paramFile = new File(file);
        try {
            int edgesAmount = 0;
            Scanner scanner = new Scanner(paramFile);
            while (scanner.hasNextLine()) {
                edgesAmount++;
                String[] i = scanner.nextLine().split(" ");
                if(i.length != 3) {
                    throw new IllegalArgumentException("Data file is incorrect");
                }
                Edge edge = new Edge(Integer.parseInt(i[0]), Integer.parseInt(i[1]), Double.parseDouble(i[2]));
                addEdge(edge);

            }
            scanner.close();
            this.edgesAmount = edgesAmount;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Data file is incorrect");
        }
    }

    public void addEdge(Edge edge) {
        int nodeA = edge.getNodeA();
        int nodeB = edge.getNodeB();
        //TODO add checks if(nodeA < 0 || nodeB >=)
        adj[nodeA].add(edge);
        adj[nodeB].add(edge);
    }

    public Iterable<Edge> getEdges() {
        LinkedList<Edge> list = new LinkedList<>();
        for (int i =0; i < verticesAmount; i++)  {
            int selfLoops = 0;
            for(Edge e : adj[i]) {
                int otherVertex = e.getOther(i);
                if(otherVertex == -1) {
                    throw new IllegalArgumentException("Wrong endpoint for:" + i);
                }
                if(otherVertex > i) {
                    list.add(e);
                }
                // only add one copy of each self loop (self loops will be
                // consecutive)
                if(otherVertex == i) {
                    if (selfLoops % 2 == 0) {
                        list.add(e);
                    }
                    selfLoops++;
                }
            }
        }
        return list;
    }



    public int getVerticesAmount() {
        return verticesAmount;
    }
    public int getEdgesAmount() {
        return edgesAmount;
    }
}
