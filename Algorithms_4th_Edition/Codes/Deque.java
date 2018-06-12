package chapter1;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.StringJoiner;


public class Deque<Item> implements Iterable<Item> {

	private class Node {
		Item item;
		Node previous;
		Node next;
	}
	
	private Node first;
	private Node last;
	private int size;
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public void pushLeft(Item item) {
		Node newNode = new Node();
		newNode.item = item;
		if (isEmpty()) {
			first = newNode;
			last = newNode;
		} else {
			newNode.next = first;
			first.previous = newNode;
			first = newNode;
		}
		
		size++;
	}
	
	public void pushRight(Item item) {
		Node newNode = new Node();
		newNode.item = item;
		if (isEmpty()) {
			first = newNode;
			last = newNode;
		} else {
			newNode.previous = last;
			last.next = newNode;
			last = newNode;
		}
		
		size++;
	}
	
	public Item popLeft() {
		if (isEmpty()) {
			return null;
		}
		
		Item item = first.item;
		first = first.next;
		if (first != null) {
			first.previous = null;
		} else {
			last = null;
		}
		size--;
		
		return item;
	}
	
	public Item popRight() {
		if (isEmpty()) {
			return null;
		}
		
		Item item = last.item;
		last = last.previous;
		if (last != null) {
			last.next = null;
		} else {
			first = null;
		}
		size--;
		
		return item;
	}
	
	public Iterator<Item> iterator() {
		return new StequeIterator();
	}
	
	private class StequeIterator implements Iterator<Item> {
		Node current = first;
		int index = 0;
		
		@Override
		public boolean hasNext() {
			return index < size;
		}
		
		@Override
		public Item next() {
			Item item = current.item;
			current = current.next;
			
			index++;
			
			return item;
		}
	}
	
	public static void main(String[] args) {
        StdOut.println("\nTest Pop Right");

        Deque<String> deque = new Deque<String>();
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
        StdOut.println("Expected   : a");

	}

}
