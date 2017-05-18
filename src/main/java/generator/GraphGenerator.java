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

}
