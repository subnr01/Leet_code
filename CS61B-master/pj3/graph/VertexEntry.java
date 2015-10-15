/* VertexEntry.java */

package graph;
import list.*;

/*
* VertexEntry is a class that stores data values for each vertex in the DList of vertices. This class contains protected values that can be accessed through public methods. 
* These are the data values it stores: 
* Name - This is what vertex it stores. 
* Key-  The hashcode 
* Edges - This is a DList of all the edges that this vertex has with the other vertices in the graph. 
* The following is the constructor for an VertexEntry object. 
*/

class VertexEntry{
	protected Object name;
	protected Object key;
	protected DList edges;
	
	VertexEntry(Object v,Object hc){
		name=v;
		key=hc;
		edges = new DList();
	}
    /* name() returns the vertex of the VertexEntry.
     * It is the label for the vertex in this graph. 
       * Running time:  O(1)
     */	
	public Object name(){
		return name;
	}
    /* key() returns the hashcode of the VertexEntry.  
       * Running time:  O(1)
     */
	public Object key(){
		return key;
	}
    /* edges() returns the DList of all the edges of this vertex.   
       * Running time:  O(1)
     */
	
	public DList edges(){
		return edges;
	}
}
