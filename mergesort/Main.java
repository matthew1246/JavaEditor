import java.util.Random;
public class Main {
	public static void main(String[] args) {
		int[] array=Generate();
		toString(array);
		Sort(array);
		
		for(int i = 0; i < 10; i++) {
			Sort(Generate());
			System.out.println();
		}
	}
	public static int[] Generate() {
		int[] array=new int[10];
		Random random=new Random();
		for(int i = 0; i < array.length; i++) {
			array[i]=random.nextInt(10)+1;
		}
		return array;
	}
	public static void toString(int[] array) {
		for(int i = 0; i < array.length; i++) {
			System.out.print(array[i]+" ");
		}
		System.out.println();
	}
	public static void Sort(int[] array) {
		return Sort(array,0,array.length-1);
	}
	public static void Sort(int[] array,int left,int right) {
		toString(array);
		if(left == right)
			return array;
		int middle=((right+1)-(left+1))/2;
		Sort(array,left,left+middle); // Left Array
		Sort(array,left+middle+1,right); // Right Array
		
		Merge(array,left,left+middle,left+middle+1,right);
		toString(array);
		
		return array;	
	}		
	public static void Merge(int[] array,int rightarray,int firstarraylength,int secondarray,int secondarraylength) {
		int secondleftarray = 0;
		int rightarray=0;
		int Count = 0;
		while(secondleftarray < firstarray.length && rightarray < secondarray.length) {
			if(firstarray[secondleftarray] < secondarray[rightarray]) {
				array[Count]=firstarray[secondleftarray];
				Count++;
				secondleftarray++;
			}
			else if(firstarray[secondleftarray] > secondarray[rightarray]) {
				array[Count]=secondarray[rightarray];
				Count++;
				rightarray++;
			}
			else { // If firstarray[secondleftarray] == secondarray[rightarray]
				array[Count]=firstarray[secondleftarray];
				Count++;
				secondleftarray++;
				array[Count]=secondarray[rightarray];
				Count++;
				rightarray++;
			}
		}
		if(secondleftarray < firstarray.length) {
			while(secondleftarray < firstarray.length) {
				array[Count]=firstarray[secondleftarray];
				Count++;
				secondleftarray++;
			}
		}
		else {  //if(rightarray > secondarray.length) 
			while(rightarray < secondarray.length) {
				array[Count]=secondarray[rightarray];
				Count++;
				rightarray++;
			}
		}						
		return array;
	}
}
