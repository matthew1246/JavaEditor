package com.perky;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
public class LiveIterator<E> implements Iterator<E> {
	public static void main(String[] args) {
		LiveIterator<Integer> liveiterator = new LiveIterator<Integer>();
		liveiterator.Add(1);
		liveiterator.Add(2);
		liveiterator.Add(3);
		liveiterator.Add(4);
		liveiterator.Add(5);
		while(liveiterator.hasNext()) {
			int c=liveiterator.next();
			if(c == 2)
				liveiterator.remove(3);
			System.out.println(c);
		}
	}
	public LiveIterator() {
	}
	public List<E> list = new ArrayList<E>();
	public LiveIterator(List<E> list) {
		this.list = list;
	}
	public LiveIterator(List<E> list,boolean isClone) {
		if(isClone) {
			for(E element:list) {
				this.list.add(element);
			}
		}
		else {
			this.list = list;
		}
	}
	public LiveIterator(E[] array) {
		for(int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
	}
	public void reset() {
		position = -1;
	}
	public int indexOf(E element) {
		return list.indexOf(element);
	}
	public boolean contains(E element) {
		return list.contains(element);
	}
	public void Add(E element) {
		list.add(element);
	}
	int position = -1;
	@Override
	public E next() {
		position++;
		return list.get(position);
	}
	@Override
	public boolean hasNext() {
		int position2 = position+1;
		return position2!=list.size();
	}
	/*
	** This very important because purpose of 
	** live iterator is you can remove elements from 
	** list and iterator will still work.
	*/
	public void remove(E element) {
		int index=list.indexOf(element);
		if(index <= position) {
			position--;
		}
		list.remove(element);
	}
}
