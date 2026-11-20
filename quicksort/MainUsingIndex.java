import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
public class MainUsingIndex {
	public static void main(String[] args) {
		for(int i = 0; i < 10; i++) {
			Sort(Generate());
			System.out.println();
		}
	}
	public static void Sort(int[] array) {
		Partition(array,0,array.length-1);
	}
	public static void Partition(int[] array, int start, int end) {
	if (start >= end) return;
	toString(array);

	int pivot = array[end];
	int low = start;
	int i = start;
	int high = end;

	while (i <= high) {
		if (array[i] < pivot) {
			swap(array, low, i);
			low++;
			i++;
		} else if (array[i] > pivot) {
			swap(array, i, high);
			high--;
		} else {
			i++;
		}
	}

	Partition(array, start, low - 1);
	Partition(array, high + 1, end);
}

private static void swap(int[] array, int i, int j) {
	int temp = array[i];
	array[i] = array[j];
	array[j] = temp;
}
	public static void setPivotInMiddle(int[] array,int pivot,int start,int end) {
		for(int i = start; i <= end; i++) {
			array[i]=pivot;
		}
	}
	public static int[] Generate() {
		Map<Integer,Integer> hashmap=new HashMap<Integer,Integer>();
		int[] array=new int[10];
		Random random=new Random();
		for(int i = 0; i < array.length; i++) {
			int y = -2;
			/*do {
				y=random.nextInt(10)+1;
			}
			while(hashmap.containsKey(y));
			hashmap.put(y,y);
			array[i]=y;
			*/
			array[i]=random.nextInt(10)+1;
		}
		return array;
	}
	public static void toString(int[] array) {
		for(int i = 0; i < array.length; i++) {
			System.out.print(array[i]+" ");
		}
		if(array.length > 0) System.out.println();
	}
}