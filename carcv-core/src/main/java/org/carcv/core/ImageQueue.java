/**
 * 
 */
package org.carcv.core;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.Queue;

/**
 * Own object similar to ArrayDeque
 * @author oskopek
 *
 */ //TODO: change to extends ArrayDeque
public class ImageQueue extends AbstractCollection<ImageFile> implements Serializable, Cloneable, Iterable<ImageFile>, Collection<ImageFile>, Deque<ImageFile>, Queue<ImageFile> {

    /**
     * 
     */
    private static final long serialVersionUID = -4341878508265409797L;
    
    //private static ImageQueue imageQueue;
    
    private ArrayDeque<ImageFile> queue;
    
    public ImageQueue() {
        queue = new ArrayDeque<>();
    }
    
    
    public ImageQueue(int numElements) {
        queue = new ArrayDeque<>(numElements);
    }
    
    public ImageQueue(Collection<? extends ImageFile> c) {
        queue = new ArrayDeque<>(c);
    }
    
    /*
    public static ImageQueue getQueue() {
        if(imageQueue == null) {
            imageQueue = new ImageQueue();
        }
            
        return ImageQueue.imageQueue;
    }
    */
    
    @Override
    public void addFirst(ImageFile e) {
        queue.addFirst(e);
    }

    @Override
    public void addLast(ImageFile e) {
        queue.addLast(e);
    }

    @Override
    public boolean offerFirst(ImageFile e) {
        return queue.offerFirst(e);
    }

    @Override
    public boolean offerLast(ImageFile e) {
        return queue.offerLast(e);
    }

    @Override
    public ImageFile removeFirst() {
        return queue.removeFirst();
    }

    @Override
    public ImageFile removeLast() {
        return queue.removeLast();
    }

    @Override
    public ImageFile pollFirst() {
        return queue.pollFirst();
    }

    @Override
    public ImageFile pollLast() {
        return queue.pollLast();
    }

    @Override
    public ImageFile getFirst() {
        return queue.getFirst();
    }

    @Override
    public ImageFile getLast() {
        return queue.getLast();
    }

    @Override
    public ImageFile peekFirst() {
        return queue.peekFirst();
    }

    @Override
    public ImageFile peekLast() {
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
    public boolean offer(ImageFile e) {
        return queue.offer(e);
    }

    @Override
    public ImageFile remove() {
        return queue.remove();
    }

    @Override
    public ImageFile poll() {
        return queue.poll();
    }

    @Override
    public ImageFile element() {
        return queue.element();
    }

    @Override
    public ImageFile peek() {
        return queue.peek();
    }

    @Override
    public void push(ImageFile e) {
        queue.push(e);
    }

    @Override
    public ImageFile pop() {
        return queue.pop();
    }

    @Override
    public Iterator<ImageFile> descendingIterator() {
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
    public boolean add(ImageFile e) {
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
    public boolean addAll(Collection<? extends ImageFile> c) {
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
    public Iterator<ImageFile> iterator() {
        return queue.iterator();
    }

}
