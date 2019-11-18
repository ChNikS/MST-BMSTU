package ru.bmstu.mst;

public class App {
    public static void main(String[] args) {
        //WeightedGraph graph = new WeightedGraph(8);
        long startTime = System.nanoTime();
        //WeightedGraph graph = new WeightedGraph(8, "/Users/nikitacherednik/Documents/bmstu/11 семестр/параллельные вычисления/MST/src/main/resources/data.txt");
        WeightedGraph graph = new WeightedGraph(10000, "/Users/nikitacherednik/Documents/bmstu/11 семестр/параллельные вычисления/MST/src/main/resources/dataL.txt");
        MST mst = new MST(graph);
        System.out.println("MST: ");
        for (Edge e : mst.getEdges()) {
            System.out.println(e);
        }
        long duration = System.nanoTime() - startTime;
        System.out.println("///////////");
        System.out.println(duration/1000000);
        System.out.println("///////////");
    }
}
