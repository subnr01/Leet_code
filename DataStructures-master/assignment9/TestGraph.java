/*Jose Nino Rivera jnino1@jhu.edu
600.226 Data Structures
Assignment 9: Six Degrees of Kevin Bacon
4/23/14*/

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;

import java.util.Iterator;

import org.junit.rules.ExpectedException;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import java.lang.RuntimeException;
 
@RunWith(Theories.class)
public class TestGraph {

    private interface Fixture {
        Graph<Integer,Integer> init();
    }
    

    @DataPoint
    public static final Fixture SparseGraph = new Fixture() {
        public SparseGraph<Integer,Integer> init() {
            return new SparseGraph<>();
        }
    }; 

    @Theory
    public void emptyIsCorrect(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        assertEquals("digraph {\n}", graph.toString());
    }

    @Theory
    public void insertSingleVertex(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        graph.insert(1);
        assertEquals("digraph {\n  \"1\";\n}", graph.toString());
    }

    @Theory
    public void insertMultipleVertices(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        graph.insert(1);
        graph.insert(2);
        graph.insert(3);
        assertEquals("digraph {\n  \"1\";\n  \"2\";\n  \"3\";\n}", graph.toString());
    }

    @Theory
    public void insertSingleEdge(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> vertexFrom = graph.insert(1);
        Vertex<Integer> vertexTo = graph.insert(2);

        graph.insert(vertexFrom, vertexTo, 10);
        assertEquals("digraph {\n  \"1\";\n  \"2\";\n  \"1\" -> \"2\" [label=\"10\"];\n}", graph.toString());
    }

    @Theory 
    public void insertMultipleEdges(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> v = graph.insert(1);
        Vertex<Integer> v2 = graph.insert(2);
        Edge<Integer> e = graph.insert(v, v2, 10);
        Edge<Integer> e2 = graph.insert(v2, v, 20);
        assertEquals("digraph {\n  \"1\";\n  \"2\";\n  \"1\" -> \"2\" [label=\"10\"];\n  \"2\" -> \"1\" [label=\"20\"];\n}", graph.toString());
    }

    @Theory @Test(expected = IllegalArgumentException.class)
    public void insertingRepeatedEdge(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> vertexFrom = graph.insert(1);
        Vertex<Integer> vertexTo = graph.insert(2);

        graph.insert(vertexFrom, vertexTo, 10);
        graph.insert(vertexFrom, vertexTo, 20);
    }

    @Theory @Test(expected = IllegalArgumentException.class)
    public void insertingEdgeInvalidVertexNull(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> vertexFrom = null;
        Vertex<Integer> vertexTo = graph.insert(2);

        graph.insert(vertexFrom, vertexTo, 10);
    }


    @Theory @Test(expected = IllegalArgumentException.class)
    public void insertingEdgeInvalidVertexNotGraph(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Graph<Integer, Integer> fakeGraph = fix.init();
        Vertex<Integer> vertexFrom = fakeGraph.insert(2);
        Vertex<Integer> vertexTo = graph.insert(2);

        graph.insert(vertexFrom, vertexTo, 10);
    }

    @Theory
    public void removeSingleVertex(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> v = graph.insert(1);
        graph.remove(v);
        assertEquals("digraph {\n}", graph.toString());
    }

    @Theory
    public void removeMultipleVertices(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> a = graph.insert(1);
        Vertex<Integer> b = graph.insert(2);
        Vertex<Integer> c = graph.insert(3);
        assertEquals("digraph {\n  \"1\";\n  \"2\";\n  \"3\";\n}", graph.toString());
        graph.remove(b);
        assertEquals("digraph {\n  \"1\";\n  \"3\";\n}", graph.toString());
        graph.remove(c);
        assertEquals("digraph {\n  \"1\";\n}", graph.toString());
        graph.remove(a);
        assertEquals("digraph {\n}", graph.toString());
    }

    @Theory @Test(expected = IllegalArgumentException.class)
    public void removeVertexWithEdges(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> v = graph.insert(1);
        Vertex<Integer> v2 = graph.insert(1);
        graph.insert(v, v2, 10);
        graph.insert(v2, v, 10);
        graph.remove(v);
    }

    @Theory @Test(expected = IllegalArgumentException.class)
    public void removeVertexWithEdges2(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> v = graph.insert(1);
        Vertex<Integer> v2 = graph.insert(1);
        graph.insert(v, v2, 10);
        graph.insert(v2, v, 10);
        graph.remove(v2);
    }

    @Theory @Test(expected = IllegalArgumentException.class)
    public void removeVertexWithOutEdges(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> v = graph.insert(1);
        Vertex<Integer> v2 = graph.insert(1);
        graph.insert(v, v2, 10);
        graph.remove(v);
    }

    @Theory @Test(expected = IllegalArgumentException.class)
    public void removeVertexWithInEdges(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> v = graph.insert(1);
        Vertex<Integer> v2 = graph.insert(1);
        graph.insert(v, v2, 10);
        graph.remove(v2);
    }

    @Theory 
    public void removeMultipleEdges(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> v = graph.insert(1);
        Vertex<Integer> v2 = graph.insert(2);
        Edge<Integer> e = graph.insert(v, v2, 10);
        Edge<Integer> e2 = graph.insert(v2, v, 20);
        assertEquals("digraph {\n  \"1\";\n  \"2\";\n  \"1\" -> \"2\" [label=\"10\"];\n  \"2\" -> \"1\" [label=\"20\"];\n}", graph.toString());
        graph.remove(e2);
        assertEquals("digraph {\n  \"1\";\n  \"2\";\n  \"1\" -> \"2\" [label=\"10\"];\n}", graph.toString());
        graph.remove(e);
        assertEquals("digraph {\n  \"1\";\n  \"2\";\n}", graph.toString());
    }

    @Theory 
    public void removeVertexWithoutEdges(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> v = graph.insert(1);
        Vertex<Integer> v2 = graph.insert(2);
        Edge<Integer> e = graph.insert(v, v2, 10);
        Edge<Integer> e2 = graph.insert(v2, v, 20);
        assertEquals("digraph {\n  \"1\";\n  \"2\";\n  \"1\" -> \"2\" [label=\"10\"];\n  \"2\" -> \"1\" [label=\"20\"];\n}", graph.toString());
        graph.remove(e2);
        assertEquals("digraph {\n  \"1\";\n  \"2\";\n  \"1\" -> \"2\" [label=\"10\"];\n}", graph.toString());
        graph.remove(e);
        assertEquals("digraph {\n  \"1\";\n  \"2\";\n}", graph.toString());
        graph.remove(v);
        assertEquals("digraph {\n  \"2\";\n}", graph.toString());
        graph.remove(v2);
        assertEquals("digraph {\n}", graph.toString());

    }

    @Theory @Test(expected = IllegalArgumentException.class)
    public void removeInvalidEdge(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Edge<Integer> edge = null;
        graph.remove(edge);
    }

    @Theory @Test(expected = IllegalArgumentException.class)
    public void removeEdgeInvalidEdgeNotGraph(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Graph<Integer, Integer> fakeGraph = fix.init();
        Vertex<Integer> vertexFrom = fakeGraph.insert(2);
        Vertex<Integer> vertexTo = fakeGraph.insert(2);

        Edge<Integer> edge = fakeGraph.insert(vertexFrom, vertexTo, 10);

        graph.remove(edge);
    }

    //TODO: inserting a self edge triggers exception
    @Theory @Test(expected = IllegalArgumentException.class)
    public void insertingSelfEdge(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> vertex = graph.insert(2);

        graph.insert(vertex, vertex, 10);
    }

    @Theory
    public void correctFrom(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> vertexFrom = graph.insert(3);
        Vertex<Integer> vertexTo = graph.insert(2);

        Edge<Integer> edge = graph.insert(vertexFrom, vertexTo, 10);

        assertEquals(vertexFrom, graph.from(edge));
    }

    @Theory
    public void correctTo(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> vertexFrom = graph.insert(3);
        Vertex<Integer> vertexTo = graph.insert(2);

        Edge<Integer> edge = graph.insert(vertexFrom, vertexTo, 10);

        assertEquals(vertexTo, graph.to(edge));
    }

    @Theory
    public void vertexLabeling(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> vertex = graph.insert(3);
        String s = "sam";
        graph.label(vertex, s);
        assertEquals(graph.label(vertex), s);
    }

    @Theory
    public void edgeLabeling(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> vertex = graph.insert(3);
        Vertex<Integer> vertex1 = graph.insert(3);
        Edge<Integer> edge = graph.insert(vertex, vertex1, 3);
        String s = "sam";
        graph.label(edge, s);
        assertEquals(graph.label(edge), s);
    }

    @Theory @Test(expected = IllegalArgumentException.class)
    public void vertexLabelingNull(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> vertex = graph.insert(3);
        String s = null;
        graph.label(vertex, s);
    }

    @Theory @Test(expected = IllegalArgumentException.class)
    public void edgeLabelingNull(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> vertex = graph.insert(3);
        Vertex<Integer> vertex1 = graph.insert(3);
        Edge<Integer> edge = graph.insert(vertex, vertex1, 3);
        String s = null;
        graph.label(edge, s);
    }

    @Theory
    public void clearLabels(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> vertex = graph.insert(3);
        Vertex<Integer> vertex1 = graph.insert(3);
        Edge<Integer> edge = graph.insert(vertex, vertex1, 3);
        String s = "sam";
        graph.label(vertex, s);
        graph.label(vertex1, s);
        graph.label(edge, s);
        assertEquals(graph.label(vertex), s);
        assertEquals(graph.label(vertex), s);
        assertEquals(graph.label(edge), s);
        graph.clearLabels();
        assertEquals(graph.label(vertex), null);
        assertEquals(graph.label(vertex), null);
        assertEquals(graph.label(edge), null);

    }

    @Theory
    public void iterableVertices(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        graph.insert(1);
        graph.insert(2);
        graph.insert(3);
        int i = 1;
        Iterable<Vertex<Integer>> vertices = graph.vertices();
        for (Vertex<Integer> vertex : vertices) {
            assertEquals((Integer) i, vertex.get());
            i++;
        }
    }

    @Theory
    public void iterableEdges(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> vertex = graph.insert(3);
        Vertex<Integer> vertex1 = graph.insert(3);
        Edge<Integer> edge1 = graph.insert(vertex, vertex1, 3);
        Edge<Integer> edge2 = graph.insert(vertex1, vertex, 4);
        Iterable<Edge<Integer>> edges = graph.edges();
        int i = 3;
        for (Edge<Integer> edge : edges) {
            assertEquals((Integer) i, edge.get());
            i++;
        }
    }

    @Theory
    public void iterableVerticesOut(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> vertex1 = graph.insert(1);
        Vertex<Integer> vertex2 = graph.insert(2);
        Vertex<Integer> vertex3 = graph.insert(3);
        Edge<Integer> edge1 = graph.insert(vertex1, vertex2, 3);
        Edge<Integer> edge2 = graph.insert(vertex1, vertex3, 4);
        Iterable<Edge<Integer>> edges = graph.outgoing(vertex1);
        int i = 3;
        for (Edge<Integer> edge : edges) {
            assertEquals((Integer) i, edge.get());
            i++;
        }
    }

    @Theory
    public void iterableVerticesIn(Fixture fix) {
        Graph<Integer,Integer> graph = fix.init();
        Vertex<Integer> vertex1 = graph.insert(1);
        Vertex<Integer> vertex2 = graph.insert(2);
        Vertex<Integer> vertex3 = graph.insert(3);
        Edge<Integer> edge1 = graph.insert(vertex2, vertex1, 3);
        Edge<Integer> edge2 = graph.insert(vertex3, vertex1, 4);
        Iterable<Edge<Integer>> edges = graph.outgoing(vertex1);
        int i = 3;
        for (Edge<Integer> edge : edges) {
            assertEquals((Integer) i, edge.get());
            i++;
        }
    }



    
}