/*
Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 4
*/
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Scanner;

/**
Junit class to test ArrayDequeue<T>
*/
public class TestArrayDequeue {

    Dequeue<Integer> dq;      

    //Always create an ArrayDequeue<Integer> at the beginning
    @Before
    public void setupArrayDequeue() {
        dq = new ArrayDequeue<Integer>();
    }

    @Test
    public void newDequeueEmpty() {
        assertTrue(dq.empty());
    }

    @Test
    public void newDequeueLength0() {
        assertTrue(dq.length() == 0);
    }

    @Test
    public void insertBackInsertsCorrectly() {
        Integer push = new Integer(1);
        dq.insertBack(push);
        assertEquals(dq.back(), push);
    }    

    @Test
    public void insertFrontInsertsCorrectly() {
        Integer push = new Integer(1);
        dq.insertFront(push);
        assertEquals(dq.front(), push);
    }

    @Test
    public void removeBackRemovesCorrectly() {
        Integer push = new Integer(1);
        dq.insertBack(push);
        dq.removeBack();
        assertTrue(dq.empty());
    }    

    @Test
    public void removeFrontRemovesCorrectly() {
        Integer push = new Integer(1);
        dq.insertFront(push);
        dq.removeFront();
        assertTrue(dq.empty());
    }          

    @Test(expected=EmptyQueueException.class)
    public void frontTriggersEmptyQueueException() throws EmptyQueueException {
        dq.front();
    }

    @Test(expected=EmptyQueueException.class)
    public void backTriggersEmptyQueueException() throws EmptyQueueException {
        dq.back();
    }

    @Test(expected=EmptyQueueException.class)
    public void removeFrontTriggersEmptyQueueException() throws EmptyQueueException {
        dq.removeFront();
    }

    @Test(expected=EmptyQueueException.class)
    public void removeBackTriggersEmptyQueueException() throws EmptyQueueException {
        dq.removeBack();
    }

    @Test
    public void alternatingInsertion() {
        for (int i = 0; i < 10; i++) {
            dq.insertBack(i);
        }

        for (int i = 0; i < 5; i++) {
            dq.removeFront();
        }

        for (int i = 10; i < 15; i++) {
            dq.insertBack(i);
        }

        for (int i = 4; i > -6; i--) {
            dq.insertFront(i);
        }

        for (int i = 0; i < 20; i++) {
            dq.removeBack();
        }

        assertTrue(dq.empty());

    } 

    @Test
    public void growWorks() {
        dq.insertFront(1);
        dq.insertBack(2);
        assertEquals(dq.length(), 2);
    }

    //I was testing if things were printed in the correct order
    /*@Test
    public void testtoString() {
        for (int i = 0; i < 10; i++) {
            dq.insertBack(i);
        }
        System.out.println(dq);
    }*/

}
