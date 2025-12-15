package com.perky;

import java.util.*;
public class InsertionSort {
	public static void main(String[] args) {
		int[] array = new int[5];
		
		array[0] = 3;
		array[1] = 0;
		array[2] = -2;
		array[3] = 2;
		array[4] = 5;	
		
		for(int i = 0; i < array.length; i++) {
			System.out.print(array[i]+" ");
		}
		System.out.println();
		
		List<Integer> list = new LinkedList<Integer>();
		list.add(array[0]);
		Label2: for(int i = 1; i < array.length; i++) {
			for(int j = i; j > 0; j--) {
				if(array[i] > list.get(j-1)) {
					list.add(j,array[i]); // Insert into LinkedList
					continue Label2;
				}
			}
			list.add(0,array[i]);
		}
		for(int number: list) {
			System.out.print(number+" ");
		}
		System.out.println();
		System.out.println();

		array = InsertionSort.RandomNumbers(10);
		for(int i = 0; i < array.length; i++) {
			System.out.print(array[i]+" ");
		}
		System.out.println();
		list = new LinkedList<Integer>();
		list.add(array[0]);
		Label3: for(int i = 1; i < array.length; i++) {
			for(int j = i; j > 0; j--) {
				if(array[i] > list.get(j-1)) {
					list.add(j,array[i]); // Insert into LinkedList
					continue Label3;
				}
			}
			list.add(0,array[i]);
		}


		for(int number: list) {
			System.out.print(number+" ");
		}
		System.out.println();
	}
	public static int[] RandomNumbers(int n) {
		int[] numbers = new int[n];
		for(int i = 0; i < n; i++) {
			numbers[i] = (int)(Math.random()*(double)n)+1;
		}
		return numbers;
	}
}
