/*
Jose Nino - jnino1@jhu.edu
Data Structures Spring 2014
Assignment 3
*/

/**
    A statable object can provide statistics about itself.

    This interface has to be quite vague to cover all kinds
    of statistics we could collect about an object. What we
    do is simply reduce all possible statistics to a string
    and let the object decide how to format that.

    For the purposes of the assignment, we could have used
    toString() for statistics, but the basic form an object
    chooses to "print" itself should be independent from
    the (potentially) more complicated statistical mess.

    Note that "Statable" should be read "stat-able" as in
    "able to produce statistics" and not "state-able" as
    in "able to be clearly stated". :-)
*/
public interface Statable {
    /**
        Return statistics about object.

        @return String representing statistics about the object.
    */
    String getStatistics();

    /**
        Reset statistics object has accumulated.
    */
    void resetStatistics();
}
