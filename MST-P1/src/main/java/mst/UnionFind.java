package mst;

/**
 * Class implementing union-find algorithm
 */
public class UnionFind {
    /**
     * array of parents
     * name of vertices if number
     */
    private volatile int[] roots;
    /**
     * Subtree rank in UF
     */
    private volatile int[]rank;
    /**
     * Amount of ele
     */
    private volatile int amount;

    public UnionFind(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException();
        }
        //init state
        this.amount = amount;
        roots = new int[amount];
        rank = new int[amount];
        for(int i = 0; i < amount; i++) {
            roots[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int a) {
        if (a < 0 || a >= roots.length) {
            throw new IndexOutOfBoundsException();
        }
        while(a != roots[a]) {
            roots[a] = roots[roots[a]];
            a = roots[a];
        }
        return a;
    }

    //TODO check logic here
    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j)
            return;
        // make root of smaller rank point to root of larger rank
        if (rank[i] < rank[j])
            roots[i] = j;
        else if (rank[i] > rank[j])
            roots[j] = i;
        else
        {
            roots[j] = i;
            rank[i]++;
        }
        amount--;
    }

    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }

}
