import java.util.Random;
public class Main {
	public static void main(String[] args) {
		int[] array={2,3,5,1,4,9,10,6,8,7};
		Sort(array);
		/*
		for(int i = 0; i < 10; i++) {
			Sort(Generate());
			System.out.println();
		}
		*/
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
		Sort(array,0,array.length-1);
	}
	public static void Sort(int[] array,int left,int right) {
		toString(array);
		if(left == right)
			return;
		int middle=((right+1)-(left+1))/2;
		Sort(array,left,left+middle); // Left Array
		Sort(array,left+middle+1,right); // Right Array
		
		Merge(array,left,left+middle,left+middle+1,right);
		toString(array);
	}		
	public static void Merge(int[] array,int firstarray,int firstarraylength,int secondarray,int secondarraylength) {
		int start=firstarray;
		while(firstarray <= firstarraylength && secondarray <= secondarraylength) {
			if(array[firstarray] < array[secondarray]) {
				int temp=array[start];
				array[start]=array[firstarray];
				array[firstarray]=temp;
				start++;
				firstarray++;
			}
			else if(array[firstarray] > array[secondarray]) {
				int temp=array[start];
				array[start]=array[secondarray];
				array[secondarray]=temp;
				start++;
				secondarray++;
			}
			else { // If firstarray[secondleftarray] == secondarray[rightarray]
				int temp=array[start];
				array[start]=array[firstarray];
				array[firstarray]=temp;
				start++;
				firstarray++;
				
				int temp2=array[start];
				array[start]=array[secondarray];
				array[secondarray]=temp2;
				start++;
				secondarray++;
			}
		}
		if(firstarray <= firstarraylength) {
			while(firstarray <= firstarraylength) {
				array[start]=array[firstarray];
				start++;
				firstarray++;
			}
		}
		else if(secondarray <= secondarraylength) {
			while(secondarray <= secondarraylength) {
				array[start]=array[secondarray];
				start++;
				secondarray++;
			}
		}						
	}
}