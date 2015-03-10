/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Queue.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: May 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ----------   --- ----------  --------------------------------------------------
 * -            -   -           -
 *================================================================================
 */
package lexa.core.queue;

import lexa.core.data.DataSet;

/**
 * Interface for a queue of {@link DataSet} messages.
 * <br>
 * The queue adds and returns each {@code DataSet} individually.  The order in which items are
 * returned is down to the specific implementation.
 *
 * @since 2013-05
 */
public interface Queue {

    /**
     * Add an item to the queue.
     *
     * @param data the item to add to the queue
     */
    public void add(DataSet data);

    /**
     * Get an item from the queue.
     *
     * @return the next item in the queue
     */
    public DataSet get();

    /**
     * Is the queue empty or not.
     *
     * @return {@code true} if there is no items in the queue,
     *      otherwise {@code false}.
     */
    public boolean isEmpty();

    /**
     * View the first item on the queue.
     * <p><B>N.B.</B> After calling {@code peek()}, the same item may not
     * still be on the queue when you call {@code get()} depending on the
     * implementation of the queue.
     * <p>See the concrete class for details.
     * @return The first item on the queue without removing it.
     */
    public DataSet peek();

    /**
     * Get the size of the queue.
     *
     * @return the size of the queue
     */
    public int size();
}
