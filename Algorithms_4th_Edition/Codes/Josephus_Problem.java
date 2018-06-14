package chapter1;

import chapter1.Node;
import edu.princeton.cs.algs4.StdOut;
public class Josephus_Problem  {

	public static void main(String[] args) {
		
		int n = Integer.parseInt(args[0]);
		int s = Integer.parseInt(args[1]);
		Node first = new Node();
		first.value = 0;
		Node current = first;
		for(int i = 1; i < n; i++) {
			Node node = new Node();
			node.value = i;
			node.previous = current;
			if (i == (n - 1)) {
				node.next = first;
				first.previous = node;
			}
			current.next = node;
			current = node;		
		}
		current = first;
		int count = 1;
		for (int i = 1; i < (n + 1); ) {
			//环链中去掉n个元素为止；
			if (count == s) {
				StdOut.print(current.value);
				StdOut.print(" ");
				current.previous.next = current.next;
				current.next.previous = current.previous;
				i++;
				count = 0;
			}
			count++;
			current = current.next;
		}
		
	}

}
