package org.example;

import java.util.ArrayList;

import static java.lang.Integer.min;


public class FloydWarshall {

    final static int infinity = 10000000;

    ArrayList<ArrayList<ArrayList<Integer>>> matrix;
    int v;

    ArrayList<ArrayList<Integer>> listToMatrix(ArrayList<ArrayList<Edge>> adjList){
        ArrayList<ArrayList<Integer>> adjMatrix = new ArrayList<>();
        int v = adjList.size();
        for(int i = 0; i < v; ++i){
            adjMatrix.add(new ArrayList<>());
            for(int j = 0; j < v; ++j){
                adjMatrix.get(i).add(infinity);
            }
        }
        for(int i = 0; i < v; ++i){
            for(Edge e : adjList.get(i)){
                adjMatrix.get(i).set(e.dist, e.weight);
            }
        }
        return adjMatrix;
    }


    FloydWarshall(ArrayList<ArrayList<Edge>> adjList){

        ArrayList<ArrayList<Integer>> adjMatrix = listToMatrix(adjList);
        v = adjMatrix.size();
        matrix = new ArrayList<>();
        for(int k = 0; k < 2; ++k){
            matrix.add(new ArrayList<>());
            for(int i = 0; i < v; ++i){
                matrix.get(k).add(new ArrayList<>());
                for(int j = 0; j < v; ++j){
                    if(k == 0) {
                        if(i != j) {
                            matrix.get(k).get(i).add(adjMatrix.get(i).get(j));
                        } else{
                            matrix.get(k).get(i).add(0);
                        }
                    } else{
                        matrix.get(k).get(i).add(0);
                    }
                }
            }
        }
    }

    ArrayList<ArrayList<Integer>> floydWarshall(){
        int last = 0;
        for(int k = 1; k <= v; ++k){
            for(int j = 0; j < v; ++j){
                for(int i = 0; i < v; ++i){
                    int relax = matrix.get(last).get(i).get(k - 1) + matrix.get(last).get(k - 1).get(j);
                    int withoutK = matrix.get(last).get(i).get(j);
                    matrix.get(1 - last).get(i).set(j, min(relax, withoutK));
                }
            }
            last = 1 - last;
        }
        return matrix.get(last);
    }
}
