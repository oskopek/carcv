/**
 * 
 */
package org.carcv.core.impl.input;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;

import org.carcv.core.input.ImageQueue;

/**
 * Own object similar to ArrayDeque
 * @author oskopek
 *
 */
public class FileImageQueue extends ImageQueue<FileCarImage> {

       
    /**
     * 
     */
    private static final long serialVersionUID = 385700571714341890L;
    
    private ArrayDeque<FileCarImage> queue;
    
    public FileImageQueue() {
        queue = new ArrayDeque<>();
    }
    
    
    public FileImageQueue(int numElements) {
        queue = new ArrayDeque<>(numElements);
    }
    
    public FileImageQueue(Collection<? extends FileCarImage> c) {
        queue = new ArrayDeque<>(c);
    }
    
    //@Override
    public void addFirst(FileCarImage e) {
        queue.addFirst(e);
    }

    //@Override
    public void addLast(FileCarImage e) {
        queue.addLast(e);
    }

    //@Override
    public boolean offerFirst(FileCarImage e) {
        return queue.offerFirst(e);
    }

    //@Override
    public boolean offerLast(FileCarImage e) {
        return queue.offerLast(e);
    }

    @Override
    public FileCarImage removeFirst() {
        return queue.removeFirst();
    }

    @Override
    public FileCarImage removeLast() {
        return queue.removeLast();
    }

    @Override
    public FileCarImage pollFirst() {
        return queue.pollFirst();
    }

    @Override
    public FileCarImage pollLast() {
        return queue.pollLast();
    }

    @Override
    public FileCarImage getFirst() {
        return queue.getFirst();
    }

    @Override
    public FileCarImage getLast() {
        return queue.getLast();
    }

    @Override
    public FileCarImage peekFirst() {
        return queue.peekFirst();
    }

    @Override
    public FileCarImage peekLast() {
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

    //@Override
    public boolean offer(FileCarImage e) {
        return queue.offer(e);
    }

    @Override
    public FileCarImage remove() {
        return queue.remove();
    }

    @Override
    public FileCarImage poll() {
        return queue.poll();
    }

    @Override
    public FileCarImage element() {
        return queue.element();
    }

    @Override
    public FileCarImage peek() {
        return queue.peek();
    }

    //@Override
    public void push(FileCarImage e) {
        queue.push(e);
    }

    @Override
    public FileCarImage pop() {
        return queue.pop();
    }

    @Override
    public Iterator<FileCarImage> descendingIterator() {
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
    public boolean add(FileCarImage e) {
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
    public boolean addAll(Collection<? extends FileCarImage> c) {
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
    public Iterator<FileCarImage> iterator() {
        return queue.iterator();
    }

}
