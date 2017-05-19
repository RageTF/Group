package algorithms;

import generator.GraphGenerator;

import java.util.*;

/**
 * Created by Rage on 15.05.2017.
 */
public class DynamicAlgorithm implements TravellingSalesAlgorithm {

    public static final String NAME="Динамическое программирование";

    @Override
    public synchronized AlgorithmResult getHamiltonianPath(int[][] graph) {
        AlgorithmResult algorithmResult=new AlgorithmResult(NAME,graph.length);

        int minLength = -1;
        int[] minPath = null;

        long first=System.nanoTime();

        HashMap<PointsSubset, int[]> h = new DynamicAlgorithm().rec(graph, graph.length);
        for (Map.Entry<PointsSubset, int[]> entry : h.entrySet()) {
            int[] path = entry.getValue();
            int length = path[path.length - 1];
            int k;
            if ((k = graph[path[path.length - 2]][path[0]]) != GraphGenerator.NOT_EXIST) {
                length += k;
                if (minLength == -1 || length < minLength) {
                    minLength = length;
                    minPath = path;
                }
            }

        }
        long time=System.nanoTime()-first;

        algorithmResult.setTime(time);
        algorithmResult.setLength(minLength);
        if(minLength!=-1)
        algorithmResult.setPath(Arrays.copyOf(minPath,minPath.length-1));
        return algorithmResult;
    }

    private HashMap<PointsSubset, int[]> rec(int[][] graph, int subCount) {

        HashMap<PointsSubset, int[]> recExtra;
        if (subCount > 2) {
            recExtra = rec(graph, subCount - 1);
        } else {
            int[] k = new int[2];
            k[0] = 0;// - точка в множестве
            k[1] = 0;// - финиш
            PointsSubset pointsSubset = new PointsSubset(k);
            int[] v = new int[2];
            v[0] = 0;// - путь
            v[1] = 0;// - длина пути
            recExtra = new HashMap<PointsSubset, int[]>();
            recExtra.put(pointsSubset, v);
        }
        int n = graph.length;
        HashMap<PointsSubset, int[]> result=new HashMap<>();
        for (int mask = 1; mask < (1 << n); mask++) {
            int count = 0;
            int[] subSet = new int[subCount];
            for (int j = 0; j < n; j++) {
                if ((mask & (1 << j)) != 0) {
                    if (count < subCount) {
                        subSet[count] = j;
                        count++;
                    } else {
                        count++;
                        break;
                    }
                }
            }
            if (count == subCount && subSet[0] == 0) {
                for (int j : subSet) {
                    if (j != 0) {

                        int min = -1;
                        PointsSubset minPs = null;
                        int[] minArr = null;

                        for (int i : subSet) {
                            if (i != j) {
                                int[] c = removeAndCopyFromArray(subSet, j, i);
                                int d = graph[j][i];
                                if (d != GraphGenerator.NOT_EXIST) {
                                    int[] lengthAndPath = recExtra.get(new PointsSubset(c));
                                    if (lengthAndPath != null) {
                                        int[] value = Arrays.copyOf(lengthAndPath, lengthAndPath.length + 1);
                                        int length = value[lengthAndPath.length - 1] + d;
                                        value[lengthAndPath.length - 1] = j;
                                        value[lengthAndPath.length] = length;
                                        int[] key = Arrays.copyOf(subSet, subSet.length + 1);
                                        key[key.length - 1] = j;
                                        if (min == -1 || length < min) {
                                            min = length;
                                            minPs = new PointsSubset(key);
                                            minArr = value;
                                        }
                                    }
                                }
                            }
                        }
                        if (min != -1) {
                            result.put(minPs, minArr);
                        }

                    }
                }
            }
        }
        return result;
    }
    private int[] removeAndCopyFromArray(int[] mas, int val, int finish) {
        int[] mas1 = new int[mas.length];
        int count = 0;
        for (int i = 0; i < mas.length; i++) {
            if (mas[i] != val) {
                mas1[count++] = mas[i];
            }
        }
        mas1[mas1.length - 1] = finish;
        return mas1;
    }

    private static class PointsSubset {

        private int[] points;

        public PointsSubset(int[] points) {
            this.points = points;
        }

        @Override
        public boolean equals(Object obj) {
            PointsSubset pointsSubset = ((PointsSubset) obj);
            if (hashCode() != pointsSubset.hashCode()) {
                return false;
            }
            if (Arrays.equals(points, pointsSubset.points)) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(points);
        }
    }
}
