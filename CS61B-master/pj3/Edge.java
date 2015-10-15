/* Edge.java */

public class Edge implements Comparable{
  
  //vertices and weight of the edge
  protected Object v1, v2;
  protected int weight;
  
  //constructs an edge with end vertices v2 and v1 and a specified weight
  public Edge(Object v1, Object v2, int weight){
    this.v1 = v1;
    this.v2 = v2;
    this.weight = weight;
  }

  /**
   * compareTo() compares the weights of edges
   * @param edge the edge to be compared with this one
   * @return -1 if this edge smaller, 0 if equal, 1 if larger
   **/
  public int compareTo(Object edge){
    if(this.weight < ((Edge)edge).weight){
      return -1;
    }else if(this.weight == ((Edge)edge).weight){
      return 0;
    }else{
      return 1;
    }
  }

  /**
   * v1() returns the v1 vertex of this edge
   * @return one vertex
   **/
  public Object v1(){
    return this.v1;
  }

  /**
   * v2() returns the v2 vertex of this edge
   * @return one vertex
   **/
  public Object v2(){
    return this.v2;
  }

  /**
   * weight() returns the weight of this edge
   * @return int weight of this edge
   **/
  public int weight(){
    return this.weight;
  }

}
