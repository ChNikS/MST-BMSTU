package ru.bmstu.mst;

public class Edge {
    private int nodeA;
    private int nodeB;
    private double weight;

    public Edge(int nodeA, int nodeB, double weight)  {
        String message = (nodeA < 0)? "node " + nodeA + " is null"
                : (nodeB < 0)? "node " + nodeB + " is null" : null;
        if(weight < 0) {
            message="wrong weights for nodes: "+nodeA+"->"+nodeB;
        }
        if(message!= null) {
            throw new IllegalArgumentException(message);
        }
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.weight = weight;

    }

    public int getNodeA() {
        return nodeA;
    }

    public int getNodeB() {
        return nodeB;
    }

    /**
     * Return another vertex connected by edge
     * if wrong vertex returns -1
     * @param vertex
     * @return
     */
    public int getOther(int vertex) {
        return (vertex == nodeA) ? nodeB : (vertex == nodeB) ? nodeA : -1;
    }

    public double getWeight() {
        return weight;
    }

    public void setNodeA(int nodeA) {
        this.nodeA = nodeA;
    }

    public void setNodeB(int nodeB) {
        this.nodeB = nodeB;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String toString() {
        return String.format("%d-%d %.5f", nodeA, nodeB, weight);
    }
}
