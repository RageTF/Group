package algorithms;

import generator.GraphGenerator;

import java.util.Arrays;

/**
 * Created by Rage on 02.05.2017.
 */
public class RecursiveAlgorithm implements TravellingSalesAlgorithm {

    private int minLength=-1;
    private int[] minPath;

    private void rec(int[][] graph,boolean[] visited,int countVisit,int[] path){

        if(countVisit==visited.length){
            int length=getLengthPath(graph,path);
            if(length!=-1 && (minLength==-1 || length<minLength)){
                minLength=length;
                minPath=Arrays.copyOf(path,path.length);
            }
        }

        for(int i=0;i<visited.length;i++){
            if(!visited[i]) {
                visited[i]=true;
                path[countVisit]=i;
                rec(graph,visited,countVisit+1,path);
                visited[i]=false;
            }
        }
    }

    private int getLengthPath(int[][] graph, int[] path) {
        int length = 0;
        int k = graph[path[0]][path[path.length - 1]];
        if (k != GraphGenerator.NOT_EXIST) {
            length += k;
        } else {
            return -1;
        }
        for (int i = 0; i < path.length - 1; i++) {
            k = graph[path[i]][path[i + 1]];
            if (k != GraphGenerator.NOT_EXIST) {
                length += k;
            } else {
                return -1;
            }
        }
        return length;
    }

    @Override
    public synchronized int[] getHamiltonianPath(int[][] graph) {
        minLength=-1;
        minPath=null;
        boolean[] visited=new boolean[graph.length];
        visited[0]=true;
        int path[]=new int[graph.length];
        path[0]=0;
        rec(graph,visited,1,path);
        return minPath;
    }

}
