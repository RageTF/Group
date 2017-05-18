package generator;

import algorithms.DynamicAlgorithm;
import algorithms.RecursiveAlgorithm;
import algorithms.TravellingSalesAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Rage on 02.05.2017.
 */
public class GraphGenerator {

    public static final int NOT_EXIST = 0;

    public final int[][] getGraph(int countPoint, int maxPathLength) {
        int[][] graph = new int[countPoint][countPoint];
        Random random = new Random();
        for (int i = 0; i < graph.length; i++) {
            for (int k = i; k < graph[i].length; k++) {
                int path;
                if (i == k) {
                    path = NOT_EXIST;
                } else {
                    path = random.nextInt(maxPathLength + 1);
                }
                graph[i][k] = path;
                graph[k][i] = path;
            }
        }
        return graph;
    }

    public static void main(String[] args) {
        TravellingSalesAlgorithm rec =new RecursiveAlgorithm();
        TravellingSalesAlgorithm dyn = new DynamicAlgorithm();
        //2+2+2+2
        //4+
        GraphGenerator graphGenerator = new GraphGenerator();
        ArrayList<int[][]> graphs = new ArrayList<>();
        for (int i = 5; i < 22; i++) {
            graphs.add(graphGenerator.getGraph(i, 20));
        }
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < graphs.size(); i++) {
                    int[][] graph = graphs.get(i);
                    int[] dynPath;
                    long first = System.nanoTime();
                    dynPath = dyn.getHamiltonianPath(graph);
                    double time = (double) (System.nanoTime() - first)/1000000000;

                    if (dynPath != null) {
                        System.out.println("(" + i + ")" + "Динамическое программирование: " + Arrays.toString(dynPath) + " , Время работы: " + time + " , Длина пути: " + getLengthPath(graph, dynPath));
                    } else {
                        System.out.println("(" + i + ")" + "Динамическое программирование: не существует цикла" + " , Время работы: " + time);
                    }
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < graphs.size(); i++) {
                    int[][] graph = graphs.get(i);
                    int[] recPath;
                    long first = System.currentTimeMillis();
                    recPath = rec.getHamiltonianPath(graph);
                    long time = System.currentTimeMillis() - first;

                    if (recPath != null) {
                        System.out.println("(" + i + ")" +"Полный перебор: " + Arrays.toString(recPath) + " , Время работы: " + time + " , Длина пути: " + getLengthPath(graph, recPath));
                    } else {
                        System.out.println("(" + i + ")" + "Полный перебор: не существует цикла" + " , Время работы: " + time);
                    }
                }
            }
        }.start();
    }

    private static int getLengthPath(int[][] graph, int[] path) {
        int length = 0;
        for (int i = 0; i < path.length - 1; i++) {
            length += graph[path[i]][path[i + 1]];
        }
        length += graph[path[0]][path[path.length - 1]];
        return length;//2+4+5++3+1+1
    }

}
