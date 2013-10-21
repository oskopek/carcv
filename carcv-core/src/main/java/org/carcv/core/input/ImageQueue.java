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
 */
public abstract class ImageQueue<E extends CarImage> extends AbstractCollection<E> implements Serializable, Cloneable, Iterable<E>, Collection<E>, Deque<E>, Queue<E> {

    
    /**
     * 
     */
    private static final long serialVersionUID = -88371342806720875L;
    
    private ArrayDeque<E> queue;
    
    public ImageQueue() {
        queue = new ArrayDeque<>();
    }
    
    
    public ImageQueue(int numElements) {
        queue = new ArrayDeque<>(numElements);
    }
    
    public ImageQueue(Collection<? extends E> c) {
        queue = new ArrayDeque<>(c);
    }
    
    @Override
    public void addFirst(E e) {
        queue.addFirst(e);
    }

    @Override
    public void addLast(E e) {
        queue.addLast(e);
    }

    @Override
    public boolean offerFirst(E e) {
        return queue.offerFirst(e);
    }

    @Override
    public boolean offerLast(E e) {
        return queue.offerLast(e);
    }

    @Override
    public E removeFirst() {
        return queue.removeFirst();
    }

    @Override
    public E removeLast() {
        return queue.removeLast();
    }

    @Override
    public E pollFirst() {
        return queue.pollFirst();
    }

    @Override
    public E pollLast() {
        return queue.pollLast();
    }

    @Override
    public E getFirst() {
        return queue.getFirst();
    }

    @Override
    public E getLast() {
        return queue.getLast();
    }

    @Override
    public E peekFirst() {
        return queue.peekFirst();
    }

    @Override
    public E peekLast() {
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
    public boolean offer(E e) {
        return queue.offer(e);
    }

    @Override
    public E remove() {
        return queue.remove();
    }

    @Override
    public E poll() {
        return queue.poll();
    }

    @Override
    public E element() {
        return queue.element();
    }

    @Override
    public E peek() {
        return queue.peek();
    }

    @Override
    public void push(E e) {
        queue.push(e);
    }

    @Override
    public E pop() {
        return queue.pop();
    }

    @Override
    public Iterator<E> descendingIterator() {
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
    public <T> T[] toArray(T[] a) {
        return queue.toArray(a);
    }

    @Override
    public boolean add(E e) {
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
    public boolean addAll(Collection<? extends E> c) {
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
    public Iterator<E> iterator() {
        return queue.iterator();
    }

}
