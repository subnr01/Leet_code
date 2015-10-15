/*Jose Nino Rivera jnino1@jhu.edu
Elliott Binder ebinder1@jhu.edu
600.226 Data Structures
Assignment 7: Whispering Trees
4/04/14 */

import java.util.Iterator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;

import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TestOrderedMap {

    private interface Fixture<K extends Comparable<? super K>, V> {
        OrderedMap<K, V> init();
    }

    @DataPoint
    public static final Fixture<String, Integer> BinarySearchTreeMap = new Fixture<String, Integer>() {
        public OrderedMap<String, Integer> init() {
            return new BinarySearchTreeMap<String, Integer>();
        }
    };

    @DataPoint
    public static final Fixture<String, Integer> AvlTreeMap = new Fixture<String, Integer>() {
        public OrderedMap<String, Integer> init() {
            return new AvlTreeMap<String, Integer>();
        }
    };

    @DataPoint
    public static final Fixture<String, Integer> TreapMap = new Fixture<String, Integer>() {
        public OrderedMap<String, Integer> init() {
            return new TreapMap<String, Integer>();
        }
    };



    @Theory
    public void newOrderedMapHasSizeZero(Fixture<String, Integer> f) {
       OrderedMap<String, Integer> m = f.init();
       assertEquals(0, m.size());
    }

    @Theory
    public void inserKeyValuePairWorks(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.insert("A", 1);
        assertTrue(m.has("A"));
        assertEquals(Integer.valueOf(1), m.get("A"));
        assertEquals(1, m.size());        
    }

    @Theory
    @Test(expected=IllegalArgumentException.class)
    public void insertNullKeyNotAllowed(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.insert(null, 1);
    }

    @Theory
    @Test(expected=IllegalArgumentException.class)
    public void insertKeyThatAlreadyExistsNotAllowed(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.insert("A", 1);
        assertTrue(m.has("A"));
        m.insert("A", 2);
    }

    @Theory
    public void removeKeyValuePairWorks(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.insert("A", 1);
        assertTrue(m.has("A"));
        assertEquals(Integer.valueOf(1), m.get("A"));
        m.remove("A");
        assertFalse(m.has("A"));
        assertEquals(0, m.size());
    }

    @Theory
    @Test(expected=IllegalArgumentException.class)
    public void removeNullKeyNotAllowed(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.remove(null);
    }

    @Theory
    @Test(expected=IllegalArgumentException.class)
    public void removeNoMappingKeyNotAllowed(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        assertFalse(m.has("Z"));
        m.remove("Z");
    }

    @Theory
    public void putUpdateKeyValuePairWorks(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.insert("A", 1);
        assertEquals(Integer.valueOf(1), m.get("A"));
        m.put("A", 2);
        assertEquals(Integer.valueOf(2), m.get("A"));
    }

    @Theory
    @Test(expected=IllegalArgumentException.class)
    public void putUpdateNullKeyNotAllowed(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.put(null, 9);
    }

    @Theory
    @Test(expected=IllegalArgumentException.class)
    public void putUpdateNoMappingKeyNotAllowed(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        assertFalse(m.has("Z"));
        m.put("Z", 9);
    }

    @Theory
    public void getWorks(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.insert("A", 1);
        assertEquals(Integer.valueOf(1), m.get("A"));
    }

    @Theory
    @Test(expected=IllegalArgumentException.class)
    public void getNullKeyNotAllowed(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.get(null);
    }

    @Theory
    @Test(expected=IllegalArgumentException.class)
    public void getNoMappingKeyNotAllowed(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        assertFalse(m.has("Z"));
        m.get("Z");
    }

    @Theory
    public void iteratorWorks(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.insert("A", 8);
        m.insert("B", 1);
        m.insert("C", 4);
        Iterator<String> i = m.iterator();
        assertEquals("A", i.next());
        assertEquals("B", i.next());
        assertEquals("C", i.next());
    }


    @Theory
    public void iteratorDoesNotChangeTree(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.insert("A", 8);
        m.insert("B", 1);
        m.insert("C", 4);
        Iterator<String> i = m.iterator();

        assertEquals(8, (int)m.get(i.next()));
        assertEquals(1, (int)m.get(i.next()));
        assertEquals(4, (int)m.get(i.next()));
    }

    @Theory
    public void iteratorOfEmptyTreeEmpty(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        Iterator<String> i = m.iterator();
        assertFalse(i.hasNext());
    }

    @Theory
    public void iteratorNotChangedByTree(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.insert("A", 8);
        m.insert("B", 1);
        m.insert("C", 4);
        Iterator<String> i = m.iterator();
        m.remove("B");
        assertEquals("A", i.next());
        assertEquals("B", i.next());
        assertEquals("C", i.next());
    }

    // A little more //

    @Theory
    public void simpleDoubleRotateCaseWorks(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.insert("C", 8);
        m.insert("A", 1);
        m.insert("B", 4);
        Iterator<String> i = m.iterator();
        assertEquals("A", i.next());
        assertEquals("B", i.next());
        assertEquals("C", i.next());
    }

    @Theory
    public void multiLevelTreeWorks(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.insert("E", 8);
        m.insert("B", 1);
        m.insert("A", 4);
        m.insert("C", 0);
        m.insert("D", 3);
        m.insert("G", 1);
        m.insert("F", 2);
        Iterator<String> i = m.iterator();
        assertEquals("A", i.next());
        assertEquals("B", i.next());
        assertEquals("C", i.next());
        assertEquals("D", i.next());
        assertEquals("E", i.next());
        assertEquals("F", i.next());
        assertEquals("G", i.next());
    }

    @Theory
    public void jumbledInsertOrderWorks(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.insert("E", 8);
        m.insert("B", 1);
        m.insert("A", 4);
        m.insert("C", 0);
        m.insert("D", 3);
        m.insert("G", 1);
        m.insert("F", 2);
        Iterator<String> i = m.iterator();
        assertEquals("A", i.next());
        assertEquals("B", i.next());
        assertEquals("C", i.next());
        assertEquals("D", i.next());
        assertEquals("E", i.next());
        assertEquals("F", i.next());
        assertEquals("G", i.next());
    }

    @Theory
    public void insertRemoveMultipleKeyValuePairWorks(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.insert("A", 8);
        m.insert("B", 1);
        m.insert("C", 4);
        m.remove("A");
        assertFalse(m.has("A"));
        assertTrue(m.has("B"));
        assertTrue(m.has("C"));
        m.remove("C");
        assertTrue(m.has("B"));
        assertFalse(m.has("C"));
    }

    @Theory
    public void correctIteratorAfterRemoval(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.insert("A", 8);
        m.insert("B", 1);
        m.insert("C", 4);
        m.remove("A");
        Iterator<String> i = m.iterator();
        assertEquals("B", i.next());
        assertEquals("C", i.next());
    }

    @Theory
    public void correctIteratorAfterMultipleInsertsAndRemovals(Fixture<String, Integer> f) {
        OrderedMap<String, Integer> m = f.init();
        m.insert("B", 1);
        m.insert("C", 4);
        m.insert("D", 3);
        m.insert("A", 3);
        m.remove("C");
        m.insert("E", 4);
        m.insert("F", 0);
        m.remove("A");
        Iterator<String> i = m.iterator();
        assertEquals("B", i.next());
        assertEquals("D", i.next());
        assertEquals("E", i.next());
        assertEquals("F", i.next());
    }
    
}
