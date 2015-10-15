/*Jose Nino Rivera jnino1@jhu.edu
600.226 Data Structures
Assignment 9: Six Degrees of Kevin Bacon
4/23/14*/
import java.util.ArrayList;

/**
    Directed graphs.

    An incidence list implementation of the Graph<V, E>
    interface. It uses two inner private classes to represent
    vertices, and edges.

    Instead of our customary Position<T> interfaces we use
    Vertex<V> and Edge<E> interfaces for positions. We can
    therefore overload method names to keep down interface
    complexity and (more importantly) we get some degree of
    static type safety (clients who confuse vertex and edge
    positions will find out at compile-time).

    Positions can be invalid for several reasons: They could
    be null, they could come from a different data structure,
    or they could come from a different, unrelated graph. We
    don't allow self-loops or duplicate edges.

    We don't throw our own specific exceptions anymore: Just
    like many Java classes, we throw IllegalArgumentException
    with a short string describing the problem instead.

    @param <V> Type of vertex element
    @param <E> Type of edge element
*/
public class SparseGraph<V, E> implements Graph<V, E> {

    private class VertexObj<V> implements Vertex<V> {
        ArrayList<EdgeObj<E>> outgoingEdges;
        ArrayList<EdgeObj<E>> incomingEdges;
        Object label;
        Graph<V, E> color;
        private V data;

        public V get() {
            return this.data;
        }

        public void put(V v) {
            this.data = v;
        }
    }

    private class EdgeObj<E> implements Edge<E> {
        VertexObj<V> toVertex;
        VertexObj<V> fromVertex;
        Object label;
        Graph<V, E> color;
        private E data;

        public E get() {
            return this.data;
        }

        public void put(E e) {
            this.data = e;
        }
    }

    private ArrayList<VertexObj<V>> vertices;
    private ArrayList<EdgeObj<E>> edges;

    /**
    Constructor for the SparseGraph.
    Initializes both ArrayLists; one for all
    edges, one for all vertices in the Graph
    */
    public SparseGraph() {
        this.vertices = new ArrayList<VertexObj<V>>();
        this.edges = new ArrayList<EdgeObj<E>>();
    }

    /**
    This private method validates vertices passed as parameters to any
    public methods.

    @param Position<V> p, the position to validate
    @return Vertex<V>, the vertex the valid position corresponds to
    @throws InvalidPositionException, when the position is not a node,
    when the position is null, when the position is from a different list,
    or when the position is for one of the sentinel nodes.
    */
    private VertexObj<V> validateVertex(Vertex<V> p)
        throws IllegalArgumentException {
        if (p == null || !(p instanceof VertexObj)) {
            throw new IllegalArgumentException("Not a valid Vertex");
        }

        VertexObj<V> v = (VertexObj<V>) p;

        if (this != v.color) {
            throw new
                IllegalArgumentException("Not a valid Vertex for this Graph");
        }

        return v;
    }

    /**
    This private method validates vertices passed as parameters to any
    public methods.

    @param Position<V> p, the position to validate
    @return Vertex<V>, the vertex the valid position corresponds to
    @throws InvalidPositionException, when the position is not a node,
    when the position is null, when the position is from a different list,
    or when the position is for one of the sentinel nodes.
    */
    private EdgeObj<E> validateEdge(Edge<E> p)
        throws IllegalArgumentException {
        if (p == null || !(p instanceof EdgeObj)) {
            throw new IllegalArgumentException("Not a valid Edge");
        }

        EdgeObj<E> e = (EdgeObj<E>) p;

        if (this != e.color) {
            throw
                new IllegalArgumentException("Not a valid Edge for this Graph");
        }

        return e;
    }

    /**
    This private method checks if an edge exists between two
    vertices.
    @param from, the from Vertex of the Edge
    @param to, the to Vertex of the Edge
    @return true if there exists an edge between the two
    vertices, false otherwise.
    */
    private boolean edgeExists(VertexObj<V> from, VertexObj<V> to) {
        if (from.outgoingEdges.size() < to.incomingEdges.size()) {
            //check outgoingEdges of from
            for (EdgeObj<E> edge: from.outgoingEdges) {
                if (edge.toVertex.equals(to)) {
                    return true;
                }
            }
        } else {
            //check incomingEdges of to
            for (EdgeObj<E> edge: to.incomingEdges) {
                if (edge.fromVertex.equals(from)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
        Insert new vertex.
        @param v Element to insert.
        @return Vertex position created to hold element.
    */
    public Vertex<V> insert(V v) {
        //Create the VertexObj and put correct data
        VertexObj<V> newVertex = new VertexObj<V>();
        newVertex.data = v;
        newVertex.color = this;
        newVertex.outgoingEdges = new ArrayList<EdgeObj<E>>();
        newVertex.incomingEdges = new ArrayList<EdgeObj<E>>();
        this.vertices.add(newVertex);

        return (Vertex<V>) newVertex;
    }

    /**
        Insert new edge.
        @param from Vertex position where edge starts.
        @param to Vertex position where edge ends.
        @param e Element to insert.
        @return Edge position created to hold element.
        @throws IllegalArgumentException If vertex positions
            are invalid or if this insertion would create a
            duplicate edge.
    */
    public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
        throws IllegalArgumentException {
        //validate both vertices
        VertexObj<V> fromVertex = this.validateVertex(from);
        VertexObj<V> toVertex = this.validateVertex(to);

        //use private function to check an equal edge
        //does not exist
        if (this.edgeExists(fromVertex, toVertex)) {
            throw new IllegalArgumentException("This edge already exists");
        }

        if (fromVertex.equals(toVertex)) {
            throw new IllegalArgumentException("No self-loops allowed");
        }

        //create edge and point it to vertices
        EdgeObj<E> newEdge = new EdgeObj<E>();
        newEdge.put(e);
        newEdge.color = this;

        newEdge.toVertex = toVertex;
        newEdge.fromVertex = fromVertex;

        fromVertex.outgoingEdges.add(newEdge);
        toVertex.incomingEdges.add(newEdge);

        //add it to the list
        this.edges.add(newEdge);

        return (Edge<E>) newEdge;
    }

    /**
        Remove a vertex.
        @param v Vertex position to remove.
        @return Element from destroyed vertex position.
        @throws IllegalArgumentException If vertex position
            is invalid or if vertex still has incident edges.
    */
    public V remove(Vertex<V> v)
        throws IllegalArgumentException {

        VertexObj<V> removeVertex = this.validateVertex(v);

        if (removeVertex.outgoingEdges.size() != 0
            || removeVertex.incomingEdges.size() != 0) {
            throw new IllegalArgumentException("This Vertex still has Edges");
        }

        removeVertex.color = null;
        this.vertices.remove(removeVertex);
        return removeVertex.get();
    }
    /**
        Remove an edge.
        @param e Edge position to remove.
        @return Element from destroyed edge position.
        @throws IllegalArgumentException If edge position
            is invalid.
    */
    public E remove(Edge<E> e)
        throws IllegalArgumentException {
        EdgeObj<E> removeEdge = this.validateEdge(e);

        removeEdge.fromVertex.outgoingEdges.remove(removeEdge);
        removeEdge.toVertex.incomingEdges.remove(removeEdge);

        this.edges.remove(removeEdge);

        removeEdge.toVertex = null;
        removeEdge.fromVertex = null;
        removeEdge.color = null;

        return removeEdge.get();
    }

    /**
        Vertices of graph.
        @return Iterable that can be used to explore the
          vertices of the graph; the remove() method of
          the iterator should not affect the graph.
    */
    public Iterable<Vertex<V>> vertices() {
        ArrayList<Vertex<V>> iterable = new ArrayList<Vertex<V>>();
        iterable.addAll(this.vertices);
        return iterable;
    }
    /**
        Edges of graph.
        @return Iterable that can be used to explore the
          edges of the graph; the remove() method of the
          iterator should not affect the graph.
    */
    public Iterable<Edge<E>> edges() {
        ArrayList<Edge<E>> iterable = new ArrayList<Edge<E>>();
        iterable.addAll(this.edges);
        return iterable;
    }

    /**
        Outgoing edges of vertex.
        @param v Vertex position to explore.
        @return Iterable that can be used to explore the
          outgoing edges of the given vertex; the remove()
          method of the iterator should not affect the graph.
        @throws IllegalArgumentException If vertex position
            is invalid.
    */
    public Iterable<Edge<E>> outgoing(Vertex<V> v)
        throws IllegalArgumentException {
        VertexObj<V> vertex = this.validateVertex(v);

        ArrayList<Edge<E>> iterable = new ArrayList<Edge<E>>();
        iterable.addAll(vertex.outgoingEdges);
        return iterable;
    }
    /**
        Incoming edges of vertex.
        @param v Vertex position to explore.
        @return Iterable that can be used to explore the
          incoming edges of the given vertex; the remove()
          method of the iterator should not affect the graph.
        @throws IllegalArgumentException If vertex position
            is invalid.
    */
    public Iterable<Edge<E>> incoming(Vertex<V> v)
        throws IllegalArgumentException {
        VertexObj<V> vertex = this.validateVertex(v);

        ArrayList<Edge<E>> iterable = new ArrayList<Edge<E>>();
        iterable.addAll(vertex.incomingEdges);
        return iterable;
    }

    /**
        Start vertex of edge.
        @param e Edge position to explore.
        @return Vertex position edge starts from.
        @throws IllegalArgumentException If edge position
            is invalid.
    */
    public Vertex<V> from(Edge<E> e)
        throws IllegalArgumentException {
        EdgeObj<E> edge = this.validateEdge(e);

        return (Vertex<V>) edge.fromVertex;
    }

    /**
        End vertex of edge.
        @param e Edge position to explore.
        @return Vertex position edge leads to.
        @throws IllegalArgumentException If edge position
            is invalid.
    */
    public Vertex<V> to(Edge<E> e)
        throws IllegalArgumentException {
        EdgeObj<E> edge = this.validateEdge(e);

        return (Vertex<V>) edge.toVertex;
    }

    /**
        Label vertex with object.
        @param v Vertex position to label.
        @param l Label object.
        @throws IllegalArgumentException If vertex position
            is invalid or label is null.
    */
    public void label(Vertex<V> v, Object l)
        throws IllegalArgumentException {
        VertexObj<V> vertex = this.validateVertex(v);
        if (l == null) {
            throw new IllegalArgumentException();
        }
        vertex.label = l;
    }

    /**
        Label edge with object.
        @param e Edge position to label.
        @param l Label object.
        @throws IllegalArgumentException If edge position
            is invalid or label is null.
    */
    public void label(Edge<E> e, Object l)
        throws IllegalArgumentException {
        EdgeObj<E> edge = this.validateEdge(e);
        if (l == null) {
            throw new IllegalArgumentException();
        }
        edge.label = l;
    }

    /**
        Vertex label.
        @param v Vertex position to query.
        @return Label object (or null if none).
        @throws IllegalArgumentException If vertex position
            is invalid.
    */
    public Object label(Vertex<V> v)
        throws IllegalArgumentException {
        VertexObj<V> vertex = this.validateVertex(v);
        return vertex.label;
    }

    /**
        Edge label.
        @param e Edge position to query.
        @return Label object (or null if none).
        @throws IllegalArgumentException If edge position
            is invalid.
    */
    public Object label(Edge<E> e)
        throws IllegalArgumentException {
        EdgeObj<E> edge = this.validateEdge(e);
        return edge.label;
    }

    /**
        Clear all labels.
    */
    public void clearLabels() {
        for (VertexObj<V> vertex: this.vertices) {
            vertex.label = null;
        }
        for (EdgeObj<E> edge: this.edges) {
            edge.label = null;
        }
    }

    /**
    Prints the entire list of edges and vertices
    of a the Graph, in a compatible format
    to be visualized on GraphViz.
    @return String, the string containing the graph representation
    */
    public String toString() {
        String s = "digraph {\n";
        for (VertexObj<V> vertex: this.vertices) {
            s = s + "  \"" + vertex.get() + "\";\n";
        }
        for (EdgeObj<E> edge: this.edges) {
            s = s + "  \"" + edge.fromVertex.get() + "\""
                + " -> " + "\"" + edge.toVertex.get() + "\""
                + " [label=\"" + edge.get() + "\"];\n";
        }
        s = s + "}";
        return s;
    }
}
