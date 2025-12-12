package Util;

/**
 * Structure DSU
 * Source : geeksforgeeks.org, simplifier par moi meme (Fabretti Forent) a l'aide de l'IA Grok
 */
public class UnionFind {

    private final int[] parent;
    private final int[] rank;   

    /**
     * Crée une structure avec n éléments isolés {0, 1, ..., n-1}
     */
    public UnionFind(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        this.parent = new int[n];
        this.rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    /**
     * Trouve la racine de x avec compression de chemin
     */
    public int find(int x) {
        if (x < 0 || x >= parent.length) {
            throw new IllegalArgumentException("x is out of bounds");
        }
        if (parent[x] != x) {
            parent[x] = find(parent[x]);  // compression
        }
        return parent[x];
    }

    /**
     * Fusionne les ensembles contenant x et y
     * @return true si une fusion a eu lieu (x et y étaient dans des ensembles différents)
     */
    public boolean union(int x, int y) {
        int rx = find(x);
        int ry = find(y);
        if (rx == ry) return false;

        // Union par rang
        if (rank[rx] < rank[ry]) {
            parent[rx] = ry;
        } else if (rank[rx] > rank[ry]) {
            parent[ry] = rx;
        } else {
            parent[ry] = rx;
            rank[rx]++;
        }
        return true;
    }
}