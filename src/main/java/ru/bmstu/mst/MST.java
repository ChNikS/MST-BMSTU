package ru.bmstu.mst;

import java.util.LinkedList;

public class MST {
    private LinkedList<Edge> mst = new LinkedList<>();
    private double weigth = 0;

    public MST(WeightedGraph graph) {
        int verticesAmount = graph.getVerticesAmount();
        UnionFind uf = new UnionFind(verticesAmount);
        while(mst.size() < verticesAmount - 1) {

            Edge[] closest = new Edge[verticesAmount];
            for (Edge e : graph.getEdges()) {
                int v = e.getNodeA();
                int w = e.getOther(v);

                int i = uf.find(v);
                int j = uf.find(w);
                if (i == j) {
                    //the same tree
                    continue;
                }
                if (closest[i] == null || isLesser(e, closest[i])) {
                    closest[i] = e;
                }
                if (closest[j] == null || isLesser(e, closest[j])) {
                    closest[j] = e;
                }
            }
            // add newly discovered Edge to MST
            for(int i = 0; i < graph.getVerticesAmount(); i++) {
                Edge e = closest[i];

                if (e != null) {
                    int v = e.getNodeA();
                    int w = e.getOther(v);
                    // don't add the same edge twice
                    if (!uf.connected(v, w)) {
                        mst.add(e);
                        weigth += e.getWeight();
                        uf.union(v, w);
                    }
                }
            }
        }

        //TODO check function here
    }

    /**
     * Checks if weight of edge e strictly less than that of edge f?
     * @param e
     * @param f
     * @return
     */
    private boolean isLesser(Edge e, Edge f) {
        return e.getWeight() < f.getWeight();
    }

    public Iterable<Edge> getEdges() {
        return mst;
    }


}
