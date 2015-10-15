/**Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 2 Problem 3
*/

/**
This Class implements the Array<T> Interface. It implements a ListArray, which is a linked list of Node<T> objects that contain the data of interest at a specific index. 
*/
public class ListArray<T> implements Array<T> {

    /**
    This private static class build Node<T> objects which contain a next and a T data members. This Nodes are linked to create an Array of length n, of T objects.
    */
    private static class Node<T> {
        Node<T> next;
        T data;
    }

    private Node<T> first;  //The first Node in the ListArray, we need it to be able to traverse the list
    private int len;        //The length, number of nodes, in the ListArray

    /**
    Constructor, checks that length is positive, creates a linked list of length nodes, and assigns t to every node.
    @param int length, length of the ListArray to initialize
    @param T t, the value t type T to populate the ListArray with
    */
    public ListArray(int length, T t) throws LengthException {
        
        //Check the length is positive
        if (length <= 0) {
            throw new LengthException();
        }
        
        //Create the first node
        Node<T> something = new Node<T>();
        this.first = something;
    
        //Create length-1 remaining Nodes to populate the ListArray
        for (int i = 0; i < length-1; i++) {
            something.next = new Node<T>();
            something.data = t;
            something = something.next;
        }
        
        something.data = t;
        this.len = length;    
    }

    /**
    find is a private function that accepts and index and returns the Node<T> for that index.
    @param int index, the index of the Node<T> of interest.
    @return Node<T>, the Node<T> object at the index of interest.
    */
    private Node<T> find(int index) throws IndexException {
        if (!((0 <= index) && (index < this.len))) {
            throw new IndexException();
        }
        
        Node<T> current = this.first;
    
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    public T get(int index) throws IndexException {
        return this.find(index).data;
    }

    public void set(int index, T t) throws IndexException {
        this.find(index).data = t;
    }

    public int length() {
        return this.len;
    }
}