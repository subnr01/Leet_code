/**
    The classic last-in-first-out (LIFO) data structure.

    @param <T> Element type.
*/
public interface Stack<T> {
    /**
        Test if stack is empty.

        @return True if the stack is empty, false otherwise.
    */
    boolean empty();

    /**
        Access top element of stack.

        @return Top element of the stack.
        @throws EmptyStackException for empty stack.
    */
    T top() throws EmptyStackException;

    /**
        Remove top element from stack.

        @throws EmptyStackException for empty stack.
    */
    void pop() throws EmptyStackException;

    /**
        Insert new element at top of stack.

        @param t Element to push.
    */
    void push(T t);
}
