/**Jose Nino Rivera jnino1@jhu.edu. 
 * Tiffany Chung tchung12@jhu.edu
 * 600.226 Data Structures 3/26/14
 * Assignment 6.4: Queueing Priorities
 */


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
 

import java.util.Comparator;
import java.util.*;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Before;


@RunWith(Theories.class)
public class TestReversePriorityQueue {
    
    //custom comparator makes smaller integers "larger"
    //this causes the testswim and testremoveafterinsertingmultiple tests to fail
    //but the way the theories are written it doesn't work for different comparators
    private static class ReverseComparator<T extends Comparable<? super T>> implements Comparator<T> {
        public ReverseComparator() {}

        public int compare(T first, T second) {
            return second.compareTo(first);
        }
    }
    
    // custom comparator is the same as default comparator
    // should give same results as default comparator
    private static class NormalComparator<T extends Comparable<? super T>> implements Comparator<T> {
    public NormalComparator() {}

         public int compare(T first, T second) {
            return first.compareTo(second);
        }
    }

    
    private interface Fixture {
        PriorityQueue<Integer> init();
    }

    /*
    @DataPoint
    public static final Fixture defaulted = new Fixture() {
        public BinaryHeapPriorityQueue<Integer> init() {
            return new BinaryHeapPriorityQueue<Integer>();
        }
    };
    */
    
    @DataPoint
    public static final Fixture reversed = new Fixture() {
        public BinaryHeapPriorityQueue<Integer> init() {
            Comparator<Integer> comparator = new ReverseComparator<Integer>();
            return new BinaryHeapPriorityQueue<Integer>(comparator);
        }
    };

        @Theory
    public void testInsertWeird(Fixture fix) {
        PriorityQueue<Integer> queue = fix.init();
        queue.insert(5);
        assertEquals(5,(int)queue.top());
        queue.insert(3);
        assertEquals(3,(int)queue.top());
        queue.insert(5);
        assertEquals(3,(int)queue.top());
        queue.insert(4);
        assertEquals(3,(int)queue.top());
        queue.insert(1);
        assertEquals(1,(int)queue.top());
        queue.insert(1);
        assertEquals(1,(int)queue.top());
        queue.insert(7);
        assertEquals(1,(int)queue.top());
        
        queue.remove();
        assertEquals(3,(int)queue.top());
        queue.remove();
        assertEquals(4,(int)queue.top());
        queue.remove();
        assertEquals(5,(int)queue.top());
        queue.remove();
        assertEquals(7, (int)queue.top());

        
        queue.remove();
        assertTrue(queue.empty());
    }

    @Theory
    public void testEmptyQueue(Fixture fix) {
        PriorityQueue<Integer> queue = fix.init();
        assertTrue(queue.empty());
    }

    @Theory
    public void testInsertRandom(Fixture fix) {
        PriorityQueue<Integer> queue = fix.init();
        ArrayList<Integer> data = new ArrayList<Integer>();
        for (int i = 0; i <= 99999; i++) {
            data.add(i);
        }

        Collections.shuffle(data);

        for (int i = 0; i <= 99999; i++) {
            queue.insert(data.get(i));
        }

        for (int i = 0; i <= 99999; i++) {
            assertEquals(i, (int) queue.top());
            queue.remove();
        }

    }

    @Theory
    public void testInsertOnce(Fixture fix) {
        PriorityQueue<Integer> queue = fix.init();
        assertTrue(queue.empty());
        queue.insert(1);
        assertTrue(!queue.empty());
        assertEquals(1, (int) queue.top());
    }
    
    @Theory
    public void testInsertRepeated(Fixture fix) {
        PriorityQueue<Integer> queue = fix.init();
        assertTrue(queue.empty());
        queue.insert(1);
        assertTrue(!queue.empty());
        assertEquals(1, (int) queue.top());
        queue.insert(1);
        assertEquals(1, (int) queue.top());
    }
    
    @Theory
    public void testRemoveOnce(Fixture fix) {
        PriorityQueue<Integer> queue = fix.init();
        assertTrue(queue.empty());
        queue.insert(1);
        queue.remove();
        assertTrue(queue.empty());
    }
    
    @Theory
    public void testRemoveMultiple(Fixture fix) {
        PriorityQueue<Integer> queue = fix.init();
        assertTrue(queue.empty());
        queue.insert(1);
        queue.insert(1);
        queue.insert(1);
        queue.insert(2);
        queue.insert(3);
        queue.insert(3);
        queue.insert(3);
        queue.remove();
        assertTrue(!queue.empty());
        assertEquals(2, (int) queue.top());
        queue.remove();
        assertTrue(!queue.empty());
        assertEquals(3, (int) queue.top());
        queue.remove();
        assertTrue(queue.empty());
    }

    @Theory
    public void testTop(Fixture fix) {
        PriorityQueue<Integer> queue = fix.init();
        assertTrue(queue.empty());
        queue.insert(1);
        assertEquals(1, (int) queue.top());
    }

    @Theory
    public void testRemoveAfterInsertingTheSame(Fixture fix) {
        PriorityQueue<Integer> queue = fix.init();
        assertTrue(queue.empty());
        queue.insert(1);
        queue.insert(1);
        queue.insert(1);
        queue.insert(1);
        queue.insert(1);
        queue.insert(1);
        queue.remove();
        assertTrue(queue.empty());
    }
    
    @Theory
    public void testRemoveAfterInsertingMultiple(Fixture fix) {
        PriorityQueue<Integer> queue = fix.init();
        assertTrue(queue.empty());
        queue.insert(1);
        queue.insert(2);
        queue.insert(1);
        queue.insert(3);
        queue.insert(1);
        queue.remove();
        assertTrue(!queue.empty());
        assertEquals(2, (int) queue.top());
    }

    @Theory
    public void testSwim(Fixture fix) {
        PriorityQueue<Integer> queue = fix.init();
        assertTrue(queue.empty());
        queue.insert(3);
        queue.insert(1);
        assertEquals(1,(int) queue.top());
    }    
    
    @Theory @Test (expected = EmptyQueueException.class)
    public void testRemoveFromEmpty(Fixture fix) {
        PriorityQueue<Integer> queue = fix.init();
        assertTrue(queue.empty());
        queue.remove();
    }
    
    @Theory @Test (expected = EmptyQueueException.class)
    public void testRemoveTwice(Fixture fix) {
        PriorityQueue<Integer> queue = fix.init();
        assertTrue(queue.empty());
        queue.insert(1);
        queue.remove();
        assertTrue(queue.empty());
        queue.remove();
    }
    
    @Theory @Test (expected = EmptyQueueException.class)
    public void testTopofEmpty(Fixture fix) {
        PriorityQueue<Integer> queue = fix.init();
        assertTrue(queue.empty());
        queue.top();
    }
    
    @Theory @Test (expected = EmptyQueueException.class)
    public void testTopTwice(Fixture fix) {
        PriorityQueue<Integer> queue = fix.init();
        assertTrue(queue.empty());
        queue.insert(1);
        queue.top();
        queue.remove();
        queue.top();
    }
    

}