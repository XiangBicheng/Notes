package chapter1;

import java.util.Iterator;
import java.util.StringJoiner;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
public class RandomBag<Item> implements Iterable<Item> {
	
	private Item[] array;
	private int size;
	
    @SuppressWarnings("unchecked")
	public RandomBag() {
		array = (Item[]) new Object[1];
		size = 0;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public void add(Item item) {
		if (size == array.length) {
			resize(size * 2);
		}
		
		array[size] = item;
		size++;
	}
	
    @SuppressWarnings("unchecked")
	private void resize(int cap) {
		Item[] temp = (Item[]) new Object[cap];
		
		for (int i = 0; i < size; i++) {
			temp[i] = array[i];
		}
		
		array = temp;
	}
    
    @Override
    public Iterator<Item> iterator() {
    	return new RandomBagIterator();
    }

    private class RandomBagIterator implements Iterator<Item> {
    	int index;
    	int[] randomList;
    	
    	public RandomBagIterator() {
    		index = 0;
    		randomList = StdRandom.permutation(size);  		
    	}
    	
    	@Override
    	public boolean hasNext() {
    		return index < size;
    	}
    	
    	@Override
    	public Item next() {
    		Item item = array[randomList[index]];
    		index++;
    		return item;
    	}
    }
	public static void main(String[] args) {
        RandomBag<Integer> randomBag = new RandomBag<>();
        randomBag.add(1);
        randomBag.add(2);
        randomBag.add(3);
        randomBag.add(4);
        randomBag.add(5);
        randomBag.add(6);
        randomBag.add(7);
        randomBag.add(8);
        
        StdOut.print("Random bag items: ");
        
        StringJoiner randomBagItems = new StringJoiner(" ");
        for (int item : randomBag) {
            randomBagItems.add(String.valueOf(item));
        }

        StdOut.println(randomBagItems.toString());

	}

}
