/*
Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 4
*/
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
 
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.junit.Test;
 
@RunWith(Theories.class)
public class TestStack {
 
    private interface Fixture {
        Stack<Integer> init();
    }
 
    @DataPoint
    public static final Fixture arrayStack = new Fixture() {
        public ArrayStack<Integer> init() {
            return new ArrayStack<>();
        }
    };
 
    @DataPoint
    public static final Fixture listStack = new Fixture() {
        public ListStack<Integer> init() {
            return new ListStack<>();
        }
    };
 
    @Theory
    public void newStackIsEmpty(Fixture fix) {
        Stack<Integer> s = fix.init();
        assertTrue(s.empty());
    }

    @Theory
    public void notEmptyAfterPush(Fixture fix) {
        Stack<Integer> s = fix.init();
        s.push(0);
        assertTrue(!s.empty());
    }

    @Theory
    public void topRightWhenPushed(Fixture fix) {
        Stack<Integer> s = fix.init();
        Integer top = new Integer(7);
        s.push(top);
        assertEquals(top,s.top());
    }

    @Theory
    public void topBackWhenPopped(Fixture fix) {
        Stack<Integer> s = fix.init();
        Integer top = new Integer(7);
        s.push(top);
        s.push(8);
        s.pop();
        assertEquals(top,s.top());
    }

    @Theory @Test(expected=EmptyStackException.class)
    public void topThrowsExceptionWhenEmpty(Fixture fix) throws EmptyStackException {
        Stack<Integer> s = fix.init();
        if(s.empty()) 
            s.top();
    }

    @Theory @Test(expected=EmptyStackException.class)
    public void popThrowsExceptionWhenEmpty(Fixture fix) throws EmptyStackException {
        Stack<Integer> s = fix.init();
        if(s.empty()) 
            s.pop();
    }


 
}