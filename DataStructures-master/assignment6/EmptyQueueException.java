/**
    Exception for an empty queue.

    This exception is raised if we try to access or remove the
    front value of an empty queue.
*/
public class EmptyQueueException extends RuntimeException {
    /** Serialization stuff, please ignore. */
    static final long serialVersionUID = 1L;
    /** Create a default exception. */
    EmptyQueueException() { /* nothing to do */ }
    /**
        Create an exception with the specified message.
        @param msg The message.
    */
    EmptyQueueException(String msg) { super(msg); }
}
