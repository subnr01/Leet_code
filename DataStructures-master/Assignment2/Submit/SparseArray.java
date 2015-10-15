/**Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 2 Problem 3
*/

/**
This Class implements the Array<T> Interface. It implements a SparseArray, which is a linked list of Node<T> objects that contain the data of interest at a specific index. The nodes are only created if the set function is called on them. They ARE NOT deallocated if the set function sets it data back to the default.

Use this implementation of the Array<T> interface if you are going to store data that will rarely be changed from the default value. This will save memory compared to SimpleArray<T>, and will make searching for a value faster than in the ListArray<T> implementation. 
*/
public class SparseArray<T> implements Array<T> {

    /**
    This private static class build Node<T> objects which contain a Node<T>, a T, and an index data members. This Nodes are linked to create an Array of length n, of T objects.
    */
    private static class Node<T> {
        Node<T> next;   //This data member points to the next Node<T> in the linked list
        T data;         //This keeps the data of type T in the Node
        int index;      //This is the index number of the Node
    }

    private Node<T> first;  //Start of the linked list
    private int len;        //Length of the Array, Nodes cant have indeces less than 0 or greater than len
    private T def_value;    //The default value of all Nodes in the SparseArray

    /**
    Constructor checks if the length is greater than 0, and stores the length and t parameters in the Objects len and def_value data members respectively
    @para int length, the length of the SparseArray
    @param T t, the default value for all indeces in the SparseArray
    */
    public SparseArray(int length, T t) throws LengthException {
        if (length <= 0) {
            throw new LengthException();
        }
        
        //No need to create any Nodes, just store the parameters
        this.len = length; 
        this.def_value = t;   
    }

    /**
    find is a private function that accepts an index and returns the Node<T> for that index if it exists, null if it doesnt.
    @param int index, the index of the Node<T> of interest.
    @return Node<T>, the Node<T> object at the index of interest, null if it doesnt exist.
    */
    private Node<T> find(int index) throws IndexException {
        if (!((0 <= index) && (index < this.len))) {
            throw new IndexException();
        }
        
        Node<T> current = this.first;

        //Traverse through the list while there are more Nodes in it
        while (current != null) {
            if (current.index == index) {
                return current;
            }
            current = current.next;
        }
        
        //This means that there were no Nodes in the list with the index of interest
        return null;
    }

    public T get(int index) throws IndexException {
        
        //Find the node with the index passed as parameter.
        Node<T> node = this.find(index);

        //If it has never been set, node is null
        if (node == null) {
            return this.def_value; //Then need to return the SparseArray's def_value
        } else {
            return node.data;
        }

    }


    public void set(int index, T t) throws IndexException {

        //Find if the node with the index passed as parameter.
        Node<T> node = this.find(index);

        //The Node has never been set
        if (node == null) {
            
            //If the client is trying to set it as the default value, no need to do anything. More about this logic in the README file
            if (t == def_value) {
                return;

            //If the user is trying to set it as a value different than def_value
            } else {
                
                //Need to allocate the Node
                Node<T> new_node = new Node<T>();

                //Pass it its respective data and index
                new_node.data = t;
                new_node.index = index;
                
                //The list was previously empty
                if (this.first == null) {
                    first = new_node;
                
                //Link the new_node to the list
                } else {
                    new_node.next = first;
                    first = new_node;
                }
            }
        }  

        //If the node has been previously set, change the Node's data to passed parameter
        //Not going to deallocate if t is def_value, logic explained in README file
        else {
            node.data  = t;
        }
    }

    public int length() {
        return this.len;
    }
    
}