package mst;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MST {
    private LinkedList<Edge> mst = new LinkedList<>();
    private double weigth = 0;
    private volatile Edge[] closest;
    private UnionFind uf;

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public MST(WeightedGraph graph) {
        int verticesAmount = graph.getVerticesAmount();
        uf = new UnionFind(verticesAmount);
        while(mst.size() < verticesAmount - 1) {
            List<Runnable> tasks = new ArrayList<>();

            class EdgeRunnable implements Runnable {
                Edge e;
                int mode;
                public EdgeRunnable(Edge e, int mode) {
                    this.e = e;
                    this.mode =  mode;
                }
                public void run() {
                    if(mode == 0) {
                        if(!e.getMark()) {
                            int v = e.getNodeA();
                            int w = e.getOther(v);

                            int i = uf.find(v);
                            int j = uf.find(w);
                            if (i == j) {
                                //the same tree
                                return;
                            }
                            if (closest[i] == null || isLesser(e, closest[i])) {
                                updateClosest(i, e);
                            }
                            if (closest[j] == null || isLesser(e, closest[j])) {
                                updateClosest(j, e);
                            }
                        }
                    } else {
                        if (e != null && !e.getMark()) {
                            addToMST(e);
                        }
                    }
                }
            }

            closest = new Edge[verticesAmount];
            for (Edge e : graph.getEdges()) {
                executorService.execute(new EdgeRunnable(e,0));
            }
            // add newly discovered Edge to MST
            for(int i = 0; i < graph.getVerticesAmount(); i++) {
                executorService.execute(new EdgeRunnable(closest[i],1));
            }
        }
        //TODO check function here
    }

    private synchronized void updateClosest(int index, Edge e) {
        closest[index] = e;
    }

    public void addToMST(Edge e) {
        int v = e.getNodeA();
        int w = e.getOther(v);
        if (!uf.connected(v, w)) {
            e.setMark(true);
            synchronized (this) {
                mst.add(e);

                weigth += e.getWeight();
                uf.union(v, w);
            }
        }
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
