/* Kruskal.java */

import graph.*;
import set.*;
import criplist.*;
import dict.*;


/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   */
  public static WUGraph minSpanTree(WUGraph g){
    WUGraph tree = new WUGraph();
    Object[] vertices = g.getVertices();
    DisjointSets elem = new DisjointSets(g.vertexCount());
    HashTableChained h = new HashTableChained(g.vertexCount());
    for(int i=0; i < vertices.length; i++){
      tree.addVertex(vertices[i]);
      h.insert(vertices[i], i);
    }
    LinkedQueue edges = edges(g, vertices);
    while(! edges.isEmpty()){
      try{
        Edge e = (Edge)edges.dequeue();
        Entry v1 = h.find(e.v1()); 
        Entry v2 = h.find(e.v2());
        int v1root = elem.find((int)v1.value()); 
        int v2root = elem.find((int)v2.value());
        if(v1root != v2root){
          elem.union(v1root, v2root);
          tree.addEdge(e.v1(), e.v2(), e.weight());
        } 
      }catch(QueueEmptyException e){
        System.err.println(e);
      }
     
    }
    return tree;
  }
 
  /**
   * 
   **/
   
 
  /**
   * edges() returns a sorted linkedqueue of the edges of the graph
   * @param g Graph vertex is on
   * @param vertex of graph 
   * @return a sorted linkedqueue of edges of graph
   **/
  private static LinkedQueue edges(WUGraph g, Object[] vertices){
    LinkedQueue result = new LinkedQueue();
    for(int i = 0; i < vertices.length; i++){
      Neighbors n = g.getNeighbors(vertices[i]);
      for(int j = 0; j < n.neighborList.length; j++){
        result.enqueue(new Edge(vertices[i], n.neighborList[j], n.weightList[j]));
      }
    }   
    ListSorts.quickSort(result);   
    return result;
  }


}
