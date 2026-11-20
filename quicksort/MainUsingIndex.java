import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
public class MainUsingIndex {
	public static void main(String[] args) {
		Sort(Generate());
	}
	public static void Sort(int[] array) {
		Partition(array,0,array.length-1);
	}
	public static void Partition(int[] array,int start,int end) {
		int endlength = end;
		int pivot=array[end];
		int samenumber=2;
		int endfirstarray= 0;
		for(int i = start; i <= (endlength-1); i++) {
			if(array[i] < pivot) {
				endfirstarray++;	
			} else if(array[i] > pivot) {
				int temp=array[end-1];
				array[end-1]=array[i];
				array[i]=temp;
				end--;
				i--;
			}
			else { // if(array[i] == pivot	
				int temp=array[endlength-samenumber];
				array[endlength-samenumber]=array[i];
				array[i]=temp;
				samenumber++;
			}
		}
		setPivotInMiddle(array,		pivot,endfirstarray,endfirstarray+samenumber-1);		
		Partition(array,start,endfirstarray-1);
		Partition(array,endfirstarray-1+samenumber-1,endlength);
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
			do {
				y=random.nextInt(10)+1;
			}
			while(hashmap.containsKey(y));
			hashmap.put(y,y);
			array[i]=y;
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