/* EdgeEntry.java */

package graph;
import list.*;

/*
* EdgeEntry is a class that stores data values for each Edge. This class contains protected values that can be accessed through public methods. 
* These are the data values it stores: 
* VertexPair - The vertex pair that this edge serves as a path between
* Key-  The hashcode 
* Partner - The edge that travels in the opposite direction but involves the same two vertices
* Weight - Weight of the path between the two vertices
* The following is the constructor for an EdgeEntry object. 
*/

class EdgeEntry{
	protected VertexPair pair;
	protected Object key;
	protected Object partner;
	protected int weight;
	
	EdgeEntry(VertexPair p, Object hc, int w){
		pair=p;
		key=hc;
		weight = w;
		partner=null;
	}
    /* pair() returns the VertexPair that EdgeEntry stores.  
       * Running time:  O(1)
     */
	public Object pair(){
		return pair;
	}
    /* key() returns the hashcode of the EdgeEntry.  
       * Running time:  O(1)
     */

	public Object key(){
		return key;
	}
    /* weight() returns the weight that EdgeEntry stores.  
       * Running time:  O(1)
     */
	public int weight(){
		return weight;
	}
    /* partner returns the other edge that serves as a path between the two vertices in the VertexPair.  
       * Running time:  O(1)
     */
	public Object partner(){
		return partner;
	}
    /* setweight() sets the weight of this EdgeEntry.  
       * Running time:  O(1)
     */
	public void setweight(int w){
		weight=w;
	}
}
