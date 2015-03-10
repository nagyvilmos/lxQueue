/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * LIFOQueue.java
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
 * A simple LIFO queue for use by the message broker and its services.
 *
 * <p>A LIFO queue works on the principal of "Last In, First Out".  The items are
 * read off the queue in the reverse of the order they are added.
 * @since 2013-05
 * @author William NW
 */
public class LIFOQueue
        implements Queue {

    /** The first item in the queue, replaced on the next add */
    private Item first;
    /** The size of the queue */
    private int size;

    /**
     * Create a new LIFO queue with no items.
     */
    public LIFOQueue() {
        this.size = 0;
    }

    /**
     * Add an item to the queue.
     * <br>
     * The item is added to the front of the queue
     *
     * @param data the item to add to the queue
     */
    @Override
    public synchronized void add(DataSet data) {
        Item item = new Item();
        item.data = data;
        item.next = this.first;
        this.first = item;
        this.size++;
    }

    /**
     * Get an item from the queue.
     *
     * <p>This will return the last item added to the queue.
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
     * <p>Each item is a {@link DataSet} and a reference to the next item in the queue.
     */
    private final class Item {
        /** The data for the queue item */
        private DataSet data;
        /** A reference to the next item. */
        private Item next;
    }
}
