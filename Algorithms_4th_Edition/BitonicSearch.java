package chapter1;

import edu.princeton.cs.algs4.*;

import java.util.Arrays;
public class BitonicSearch {
	
	public static int bitonicSearch(int[] a, int key) {
		int maxIndex = findMaxIndex(a);
		int ascendingIndex = ascendingBinarySearch(Arrays.copyOfRange(a, 0, maxIndex + 1), key);
		int descendingIndex = descendingBinarySearch(Arrays.copyOfRange(a, maxIndex + 1, a.length), key);
		if(ascendingIndex == -1) 
			if (descendingIndex != -1)
				return descendingIndex;
		return ascendingIndex;
	}
	
	public static int findMaxIndex(int[] a) {
		int max = a.length / 2;
		int low = 0;
		int high = a.length - 1;
		/* 如果max位置的元素大于它之前的元素，则该位置及之后的位置肯定包含最大值，故置low等于max；
		 * 如果max位置的元素小于它之前的元素，则该位置之前的位置肯定包含最大值，故置high等于max-1；
		 * 不断地缩减low和high之间的距离，直到它们相等，那个相等的元素指向的位置就是最大值。
		 */
		while (low < high) {
			if (a[max] > a[max - 1]) {
				low = max;
				/* 这里和后面，一定要使得max = low + (high - low + 1) / 2，不能是max = low + (high - low) / 2。
				 * 如果以后者的形式更新max，可能会导致循环永远退不出去。
				 * 假设数组元素是[..., 5, [6, 7], ...]，此时low指向6， high指向7，如果以后者的形式更新max，则max指向6，
				 * 下一次进入循环的时候，由于6 > 5，进入当前条件分支，从当前条件分支出去的时候仍然是low指向6， high指向7， max指向6。
				*/
				max = low + (high - low + 1) / 2;
			} else {
				high = max - 1;
				max = low + (high - low + 1) / 2;
			}
		}
		return low;
	}
	
	public static int ascendingBinarySearch(int[] a, int key) {
		int low = 0;
		int high = a.length - 1;
		while (low <= high) {
			int mid = low + (high - low) / 2;
			if (a[mid] > key)
				high = mid - 1;
			else if (a[mid] < key)
				low = mid + 1;
			else
				return mid;
		}
		return -1;
	}
	
	public static int descendingBinarySearch(int[] a, int key) {
		int low = 0;
		int high = a.length - 1;
		while (low <= high) {
			int mid = low + (high - low) / 2;
			if (a[mid] > key)
				low = mid + 1;
			else if (a[mid] < key)
				high = mid - 1;
			else 
				return mid;
		}
		return -1;
	}
	
	public static void main(String[] args) {
		int[] array1 = {1, 2, 3, 4, -1, -2, -3};
        int[] array2 = {1, 5, 4, 3, 2, 0};
        int[] array3 = {2, 4, 8, 16, 32, 1};
        int[] array4 = {2, 4, 8, 16, 32};
        int[] array5 = {2, 1};
        int[] array6 = {9};

        int indexOfElement1 = bitonicSearch(array1, -1);
        StdOut.println("Index of element: " + indexOfElement1 + " Expected: 4");
        int indexOfElement2 = bitonicSearch(array2, 5);
        StdOut.println("Index of element: " + indexOfElement2 + " Expected: 1");
        int indexOfElement3 = bitonicSearch(array3, 2);
        StdOut.println("Index of element: " + indexOfElement3 + " Expected: 0");
        int indexOfElement4 = bitonicSearch(array3, 99);
        StdOut.println("Index of element: " + indexOfElement4 + " Expected: -1");
        int indexOfElement5 = bitonicSearch(array4, 32);
        StdOut.println("Index of element: " + indexOfElement5 + " Expected: 4");
        int indexOfElement6 = bitonicSearch(array5, 1);
        StdOut.println("Index of element: " + indexOfElement6 + " Expected: 1");
        int indexOfElement7 = bitonicSearch(array6, 9);
        StdOut.println("Index of element: " + indexOfElement7 + " Expected: 0");

	}

}
