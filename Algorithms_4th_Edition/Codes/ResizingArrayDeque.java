package chapter1;

import java.util.Iterator;
import java.util.StringJoiner;

import edu.princeton.cs.algs4.StdOut;

public class ResizingArrayDeque<Item> implements Iterable<Item> {
	@SuppressWarnings("unchecked")
	private Item[] array = (Item[]) new Object[1];
	private int size = 0;
	private int first = -1;
	private int last = -1;
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	@SuppressWarnings("unchecked")
	private void resize(int max) {
		Item[] temp = (Item[]) new Object[max];
		for (int i = 0; i < size; i++) {
			temp[i] = array[(first + i) % array.length];
		}
		first = 0;
		last = size - 1;
		array = temp;
	}
	
	public void pushLeft(Item item) {
		if (size == array.length) {
			resize(size * 2);
		}
		if (isEmpty()) {
			first = 0;
			last = 0;
			array[first] = item;
		} else {
			first = (first - 1) >= 0 ? (first - 1) : (array.length - 1);
			array[first] = item;
		}
		
		size++;
	}
	
	public void pushRight(Item item) {
		if (size == array.length) {
			resize(size * 2);
		}
		if (isEmpty()) {
			first = 0;
			last = 0;
			array[last] = item;
		} else {
			last = (last + 1) < array.length ? (last + 1) : 0;
			array[last] = item;
		}
		
		size++;
	}

	public Item popLeft() {
		if (isEmpty()) {
			return null;
		} 
		Item item = array[first];
		array[first] = null;
		first = (first + 1) < array.length ? (first + 1) : 0; 
		if (size == array.length / 4) {
			resize(array.length / 2);
		}
		size--;
		
		return item;
	}
	
	public Item popRight() {
		if (isEmpty()) {
			return null;
		} 
		Item item = array[last];
		array[last] = null;
		last = (last - 1) >= 0 ? (last - 1) : (array.length - 1);
		if (size == array.length / 4) {
			resize(array.length / 2);
		}
		size--;
		
		return item;
	}
	
	public Iterator<Item> iterator() {
		return new ResizeingArrayDequeIterator();
	}
	
	private class ResizeingArrayDequeIterator implements Iterator<Item> {
		int index = 0;
		
		@Override
		public boolean hasNext() {
			return index < size;
		}
		
		@Override
		public Item next() {
			Item item = array[(first + index) % array.length];
			index++;
			return item;
		}
	}
	
 	public static void main(String[] args) {
		ResizingArrayDeque<String> deque = new ResizingArrayDeque<String>();
		deque.testPushLeft();
        deque.testPushRight();
        deque.testPopLeft();
        deque.testPopRight();
	}
 	
    private void testPushLeft() {
        StdOut.println("Test Push Left");

        ResizingArrayDeque<String> deque = new ResizingArrayDeque<>();
        deque.pushLeft("a");
        deque.pushLeft("b");
        deque.pushLeft("c");

        StringJoiner dequeItems = new StringJoiner(" ");
        for (String item : deque) {
            dequeItems.add(item);
        }

        StdOut.println("Deque items: " + dequeItems.toString());
        StdOut.println("Expected: c b a");
    }
    
    private void testPushRight() {
        StdOut.println("\nTest Push Right");

        ResizingArrayDeque<String> deque = new ResizingArrayDeque<>();
        deque.pushRight("a");
        deque.pushRight("b");
        deque.pushRight("c");

        StringJoiner dequeItems = new StringJoiner(" ");
        for (String item : deque) {
            dequeItems.add(item);
        }

        StdOut.println("Deque items: " + dequeItems.toString());
        StdOut.println("Expected: a b c");
    }

    private void testPopLeft() {
        StdOut.println("\nTest Pop Left");

        ResizingArrayDeque<String> deque = new ResizingArrayDeque<>();
        deque.pushRight("a");
        deque.pushRight("b");
        deque.pushRight("c");

        deque.popLeft();
        deque.popLeft();

        StringJoiner dequeItems = new StringJoiner(" ");
        for (String item : deque) {
            dequeItems.add(item);
        }

        StdOut.println("Deque items: " + dequeItems.toString());
        StdOut.println("Expected: c");
    }
    
    private void testPopRight() {
        StdOut.println("\nTest Pop Right");

        ResizingArrayDeque<String> deque = new ResizingArrayDeque<>();
        deque.pushRight("a");
        deque.pushRight("b");
        deque.pushRight("c");

        deque.popRight();
        deque.popRight();

        StringJoiner dequeItems = new StringJoiner(" ");
        for (String item : deque) {
            dequeItems.add(item);
        }

        StdOut.println("Deque items: " + dequeItems.toString());
        StdOut.println("Expected: a");
    }
}

