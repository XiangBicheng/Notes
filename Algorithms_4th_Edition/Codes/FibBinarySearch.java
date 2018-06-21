package chapter1;

import edu.princeton.cs.algs4.StdOut;
public class FibBinarySearch {

	public static int rank(int[] a, int key) {
		/* 这个算法的核心仍然是二分查找，只不过搜索范围，不是以2的幂每次减半，而是以Fibonacci数列的值递减，
		 * 比如8， 5， 3， 2， 1。关键就是要抓住循环不变式：每次循环开始时，（base, base + Fk）表示要搜索的范围，
		 * 且两个端点处已经检测过是否等于要查找的key，当Fk等于1时，搜索范围就变为（base，base + 1），也就是没有
		 * 需要搜索的了，就退出循环。
		 */
		int base = 0;
		int Fk = 1;
		int FkMinus1 = 0;
		/* 这里很多时候Fk的值不是等于数组的长度，而是比（数组长度-1）长，比如如果数组长度为7，那么实际Fk开始时就是8。
		 * 数组长度和Fk不相等这个特点，后面的循环中需要我们做相应的处理。
		 */
		while (Fk < (a.length - 1)) {
			int temp = Fk;
			Fk = Fk + FkMinus1;
			FkMinus1 = temp;
		}
		/* 下面两个条件分支语句，实际的目的是在开始时构造循环不变式，因为后面每次回把base + FkMinus2的值设为搜索范围的
		 * 某侧端点，而base + FkMinus2所指元素的值又已经检查过了，所以每次循环我们实际都要求搜索范围两侧的端点已经检查
		 * 过了。对于开始时Fk大于(数组长度-1)的情况，我们直接视该位置所指元素不等于key。
		 */
		if (a[0] == key) 
			return 0;
		if (a[a.length - 1] == key)
			return a.length - 1;
		while(Fk > 1) {
			int FkMinus2 = Fk - FkMinus1;	
			/* 这里利用逻辑或操作符的短路特性，首先检查base + FkMinus2是不是在有效索引范围内。如果超过了有效索引范围，直接
			 * 视该索引处的值大于key值，缩减搜索范围。
			 */
			if ((base + FkMinus2) > (a.length - 1) || a[base + FkMinus2] > key) {
				Fk = FkMinus2;
				FkMinus1 = FkMinus1 - FkMinus2;
			} else if (a[base + FkMinus2] < key) {
				Fk = FkMinus1;
				FkMinus1 = FkMinus2;
				base += FkMinus2;
			} else {
				return base + FkMinus2;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
        int[] array = {-2, -1, 0, 1, 2, 3, 4, 5, 6, 7};
        StdOut.println(rank(array, 1));
        
        int index1 = rank(array, 2);
        int index2 = rank(array, 9);
        int index3 = rank(array, -3);
        int index4 = rank(array, 7);
        int index5 = rank(array, -2);

        StdOut.println("Is element in the array: " + (index1 != -1) + " Expected: true");
        StdOut.println("Is element in the array: " + (index2 != -1) + " Expected: false");
        StdOut.println("Is element in the array: " + (index3 != -1) + " Expected: false");
        StdOut.println("Is element in the array: " + (index4 != -1) + " Expected: true");
        StdOut.println("Is element in the array: " + (index5 != -1) + " Expected: true");

	}

}
