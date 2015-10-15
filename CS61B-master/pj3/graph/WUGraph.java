/* WUGraph.java */

package graph;
import list.*;
import dict.*;


/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

	
 
public class WUGraph {

	HashTableChained verticesTable, edgeTable;
	DList vertexList;
	int TABLESIZE=100;
	int numberVertices, numberEdges;
  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph(){
	verticesTable = new HashTableChained (TABLESIZE);
	edgeTable = new HashTableChained (TABLESIZE);
	vertexList = new DList();
	numberEdges=0;
	numberVertices=0;
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount(){
	return numberVertices;
  }

  /**
   * edgeCount() returns the number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount(){
	return numberEdges;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices(){
	DListNode current = (DListNode)vertexList.front();
	int n=numberVertices;
	Object[] vert = new Object[n];
	int i=0;
	try{
	while(i<n){
		vert[i]=((VertexEntry)current.item()).name();
		current=(DListNode)current.next();
		i++;
	}
	} catch (InvalidNodeException e){
		System.err.println(e);
	}
	return vert;
 }
  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.  The
   * vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex){
	VertexEntry nVE = new VertexEntry(vertex,vertex.hashCode());
	if(verticesTable.find(nVE.key)==null){
	numberVertices++;
	vertexList.insertBack(nVE);
	verticesTable.insert(nVE.key, vertexList.back());
	}
	return;
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex){
	Entry vertFind =  verticesTable.find(vertex.hashCode());
	if(vertFind==null){
	return;
	} 
	try{
	VertexEntry vE = ((VertexEntry)((DListNode)vertFind.value()).item());
	if(vE.edges().isEmpty()){
		numberVertices--;
		((DListNode)vertFind.value()).remove();
		verticesTable.remove(vertex.hashCode());
		return;
	} 
	else {
	DListNode c = ((DListNode)((DList)vE.edges()).front());
		while(c!=((DListNode)((DList)vE.edges()).back())){
			
			removeEdge(((Object)((VertexPair)((EdgeEntry)c.item()).pair()).o1()),((Object)((VertexPair)((EdgeEntry)c.item()).pair()).o2()));
			c =  ((DListNode)((DList)vE.edges()).front());
		}
		
		removeEdge(((Object)((VertexPair)((EdgeEntry)c.item()).pair()).o1()),((Object)((VertexPair)((EdgeEntry)c.item()).pair()).o2()));
	
	numberVertices--;
	((DListNode)vertFind.value()).remove();
	verticesTable.remove(vertex.hashCode());
	}
	} catch (InvalidNodeException e){
		System.err.println(e);
	}

	return;
	//remove all adges that are incident!!!!
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex){
	Entry vertFind =  verticesTable.find(vertex.hashCode());
	if(vertFind==null){
		return false;
	}
	return true;
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex){
	Entry vertFind =  verticesTable.find(vertex.hashCode());
	try{
	if(vertFind!=null){
	DListNode dnode = (DListNode) vertFind.value();
	VertexEntry v = (VertexEntry) dnode.item();
	DList d = (DList) v.edges();
	return d.length();
	}
	} catch (InvalidNodeException e){
		System.err.println(e);
	}
	return 0;
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex){
	Neighbors n = new Neighbors();
	try
	{
	Entry vertFind =  verticesTable.find(vertex.hashCode());
	if(vertFind==null){
		return null;
	}
	VertexEntry vE = ((VertexEntry)((DListNode)vertFind.value()).item());
	int size = ((DList)vE.edges()).length();
	if(size==0){
		return null;
	}
	n.neighborList = new Object[size];
	n.weightList = new int[size];
	int i;
	DListNode c = (DListNode)(vE.edges().front()); 
	for(i=0;i<size;i++)
	{
		Object a = ((VertexPair)((EdgeEntry)c.item()).pair()).o1();
		Object b = ((VertexPair)((EdgeEntry)c.item()).pair()).o2();
		if(a.equals(vertex))
			n.neighborList[i]=b;
		else 
			n.neighborList[i]=a;
		n.weightList[i]= ((EdgeEntry)c.item()).weight();
		c=((DListNode)c.next());
	}
	} catch (InvalidNodeException e){
		System.err.println(e);
		}
	
	return n;
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the edge is already
   * contained in the graph, the weight is updated to reflect the new value.
   * Self-edges (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight){
	VertexPair nE = new VertexPair(u,v);
	if((verticesTable.find(u.hashCode())==null)||(verticesTable.find(v.hashCode())==null)){
		return; 
	}
	EdgeEntry edge1 = new EdgeEntry(nE,nE.hashCode(), weight);
	EdgeEntry edge2 = new EdgeEntry(nE,nE.hashCode(), weight);	
	Entry hashedge =  edgeTable.find(edge1.key());
	if(hashedge==null){
		if(u.equals(v)){
			try{
			numberEdges++;
			Entry uvertFind =  verticesTable.find(u.hashCode());
			VertexEntry uE= ((VertexEntry)((DListNode)uvertFind.value()).item());
			uE.edges().insertBack(edge1); //add edges!
			edge1.partner=null;
			edgeTable.insert(edge1.key(), uE.edges().back());
			}catch (InvalidNodeException e){
		System.err.println(e);
		}
		}else{
		try{
		numberEdges++;
		Entry uvertFind =  verticesTable.find(u.hashCode());
		VertexEntry uE= ((VertexEntry)((DListNode)uvertFind.value()).item());
		uE.edges().insertBack(edge1); //add edges!
		Entry vvertFind =  verticesTable.find(v.hashCode());
		VertexEntry vE= ((VertexEntry)((DListNode)vvertFind.value()).item());
		vE.edges().insertBack(edge2);//add edges!
		edge1.partner=vE.edges().back();
		edge2.partner=uE.edges().back();
		edgeTable.insert(edge1.key(), uE.edges().back());
		} catch (InvalidNodeException e){
		System.err.println(e);
		}
		}
	} else {
		try{
		((EdgeEntry)(((DListNode)hashedge.value()).item())).setweight(weight);
		} catch(InvalidNodeException e){
			System.err.println(e);
		}
	}
	return;
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v){
	VertexPair nE = new VertexPair(u,v);
	if((verticesTable.find(u.hashCode())==null)||(verticesTable.find(v.hashCode())==null)){
		return; 
	}//case when you don't have vertex
	try{
	Entry hashedge =  edgeTable.find(nE.hashCode());
	if(hashedge==null)
		return;
	
	DListNode c = ((DListNode)hashedge.value());
	DListNode p = ((DListNode)((EdgeEntry)c.item()).partner());
		if(!u.equals(v)){
		c.remove();
		p.remove();
		}
		else {
			c.remove();
		}
		numberEdges--;
		edgeTable.remove(nE.hashCode());
	}
	catch(InvalidNodeException e){
		System.err.println(e);
	} 
  }
  
  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v){
	VertexPair nE = new VertexPair(u,v);
	if((verticesTable.find(u.hashCode())==null)||(verticesTable.find(v.hashCode())==null)){
		return false; 
	}//case when you don't have vertex
	Entry hashedge =  edgeTable.find(nE.hashCode());
	if(hashedge==null)
		return false;
	return true;
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but
   * also more annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v){
	try{
	VertexPair nE = new VertexPair(u,v);
	if((verticesTable.find(u.hashCode())==null)||(verticesTable.find(v.hashCode())==null)){
		return 0; 
	}//case when you don't have vertex
	Entry hashedge =  edgeTable.find(nE.hashCode());
	if(hashedge==null)
		return 0;
		
	DListNode c = ((DListNode) hashedge.value());
	EdgeEntry eE = ((EdgeEntry)c.item()); 
	return eE.weight();
	}
	catch(InvalidNodeException e){
		System.err.println(e);
	} 
	return 0;
  }
}
