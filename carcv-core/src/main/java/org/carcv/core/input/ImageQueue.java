/**
 * 
 */
package org.carcv.core.input;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.Queue;

import org.carcv.core.model.CarImage;

/**
 * Own object similar to ArrayDeque
 * @author oskopek
 *
 */ //TODO: change to extends ArrayDeque
public abstract class ImageQueue<T extends CarImage> extends AbstractCollection<T> implements Serializable, Cloneable, Iterable<T>, Collection<T>, Deque<T>, Queue<T> {

    
    /**
     * 
     */
    private static final long serialVersionUID = -88371342806720875L;
    
    private ArrayDeque<T> queue;
    
    public ImageQueue() {
        queue = new ArrayDeque<>();
    }
    
    
    public ImageQueue(int numElements) {
        queue = new ArrayDeque<>(numElements);
    }
    
    public ImageQueue(Collection<? extends T> c) {
        queue = new ArrayDeque<>(c);
    }
    
    @Override
    public void addFirst(T e) {
        queue.addFirst(e);
    }

    @Override
    public void addLast(T e) {
        queue.addLast(e);
    }

    @Override
    public boolean offerFirst(T e) {
        return queue.offerFirst(e);
    }

    @Override
    public boolean offerLast(T e) {
        return queue.offerLast(e);
    }

    @Override
    public T removeFirst() {
        return queue.removeFirst();
    }

    @Override
    public T removeLast() {
        return queue.removeLast();
    }

    @Override
    public T pollFirst() {
        return queue.pollFirst();
    }

    @Override
    public T pollLast() {
        return queue.pollLast();
    }

    @Override
    public T getFirst() {
        return queue.getFirst();
    }

    @Override
    public T getLast() {
        return queue.getLast();
    }

    @Override
    public T peekFirst() {
        return queue.peekFirst();
    }

    @Override
    public T peekLast() {
        return queue.peekLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return queue.removeFirstOccurrence(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return queue.removeLastOccurrence(o);
    }

    @Override
    public boolean offer(T e) {
        return queue.offer(e);
    }

    @Override
    public T remove() {
        return queue.remove();
    }

    @Override
    public T poll() {
        return queue.poll();
    }

    @Override
    public T element() {
        return queue.element();
    }

    @Override
    public T peek() {
        return queue.peek();
    }

    @Override
    public void push(T e) {
        queue.push(e);
    }

    @Override
    public T pop() {
        return queue.pop();
    }

    @Override
    public Iterator<T> descendingIterator() {
        return queue.descendingIterator();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return queue.contains(o);
    }

    @Override
    public Object[] toArray() {
        return queue.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) { //TODO: fix warning
        return queue.toArray(a);
    }

    @Override
    public boolean add(T e) {
        return queue.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return queue.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return queue.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return queue.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return queue.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return queue.retainAll(c);
    }

    @Override
    public void clear() {
        queue.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return queue.iterator();
    }

}
