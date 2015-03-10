/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * SortedQueue.java
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
import lexa.core.expression.Expression;
import lexa.core.expression.ExpressionException;
import lexa.core.logging.Logger;

/**
 * A sorted queue for use by the message broker and its services.
 *
 * <p>A Sorted queue works on the principal of ranking each item as they arrive and
 * returning them from the lowest ranked to the highest rank.
 * <p>If an item cannot be ranked it will not be added to the queue.
 * @since 2013-05
 * @author William NW
 */
public class SortedQueue
        implements Queue {

    /** The expression to rank individual items */
    private final Expression expression;
    /** The first item in the queue, returned on the next get */
    private Item first;
    /** The size of the queue */
    private int size;

    /**
     * Create a new Sorted queue with no items.
     *
     * @param   expression
     *          An {@link Expression} that returns an {@link Integer} value used to
     *          order the queue.  If the evaluation of the value does not return an
     *          integer then an {@link IllegalArgumentException} is thrown.
     */
    public SortedQueue(Expression expression) {
        this.expression = expression;
        this.size = 0;
    }

    /**
     * Add an item to the queue.
     * <br>
     * <p>The item is added to the end of the queue
     * <p><b>NB.</b> If the evaluation of the queue's {@link Expression} does not
     * return an integer, then an exception is thrown that should be caught.
     *
     * @param   data
     *          the item to add to the queue
     *
     * @throws  IllegalArgumentException
     *          The {@link DataSet} cannot be ranked using the {@link Expression}
     *          passed in the constructor.
     */
    @Override
    public synchronized void add(DataSet data) {
        Item item = new Item();
        item.data = data;
        try {
            item.rank = (Integer) this.expression.evaluate(data);
        } catch (ExpressionException ex) {
            new Logger(SortedQueue.class.getSimpleName(),null).error("Cannot add item", data, ex);
            throw new IllegalArgumentException(ex);
        }

        if (this.size == 0) {
            this.first = item;
        } else {
            this.first = this.first.addItem(item);
        }
        this.size++;
    }

    /**
     * Get an item from the queue.
     *
     * <p>This will return the first item in the queue.
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
        /** A ranking used to order items. */
        private Integer rank;

        private Item addItem(Item next) {
            if (this.rank <= next.rank) {
                this.next = this.next.addItem(next);
                return this;
            }
            next.next = this;
            return next;
        }
    }
}
