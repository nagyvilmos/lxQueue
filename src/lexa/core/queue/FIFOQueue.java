/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * FIFOQueue.java
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
 * A simple FIFO queue for use by the message broker and its services.
 *
 * <p>A FIFO queue works on the principal of "First In, First Out".  The items are
 * read off the queue in the order they are added.
 * @since 2013-05
 * @author William NW
 */
public class FIFOQueue
        implements Queue {

    /** The first item in the queue, returned on the next get */
    private Item first;
    /** The last item in the queue, replaced on the next add */
    private Item last;
    /** The size of the queue */
    private int size;

    /**
     * Create a new FIFO queue with no items.
     */
    public FIFOQueue() {
        this.size = 0;
    }

    /**
     * Add an item to the queue.
     * <br>
     * The item is added to the end of the queue
     *
     * @param data the item to add to the queue
     */
    @Override
    public synchronized void add(DataSet data) {
        Item item = new Item();
        item.data = data;
        if (this.size == 0) {
            this.first = item;
        } else {
            this.last.next = item;
        }
        this.last = item;
        this.size++;
    }

    /**
     * Get an item from the queue.
     *
     * <p>This will return the first item added to the queue.
     *
     * @return the next item in the queue
     */    @Override
    public synchronized DataSet get() {
        if (this.size == 0) {
            return null;
        }
        Item item = this.first;
        this.first = this.first.next;
        this.size--;

        return item.data;
    }

    @Override
    @SuppressWarnings("SizeReplaceableByIsEmpty")
    public boolean isEmpty() {
        return (this.size() == 0);
    }

    /**
     * View the first item on the queue.
     * <p>The item returned here will always be the next item returned
     * by {@code get()} for a non-null value.
     *
     * @return  the first item on the queue without removing it.
     */    @Override
    public DataSet peek() {
        if (this.size == 0) {
            return null;
        }
        return this.first.data;
    }

    @Override
    public synchronized int size() {
        return this.size;
    }

    /**
     * A single item on the queue.
     * <p>Each item is  a {@link DataSet} and a reference to the next item in the queue.
     */
    private final class Item {
        /** The data for the queue item */
        private DataSet data;
        /** A reference to the next item. */
        private Item next;
    }
}
