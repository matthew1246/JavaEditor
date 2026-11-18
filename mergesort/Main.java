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
		int[] onetemp=new int[array.length];
		Sort(array,onetemp,0,array.length-1);
	}
	public static void Sort(int[] array,int[] onetemp,int left,int right) {
		toString(array);
		if(left == right)
			return;
		int middle=((right+1)-(left+1))/2;
		Sort(array,onetemp,left,left+middle); // Left Array
		Sort(array,onetemp,left+middle+1,right); // Right Array
		
		Merge(array,onetemp,left,left+middle,left+middle+1,right);
		toString(array);
	}		
	public static void Merge(int[] array,int[] temp,int firstarray,int firstarraylength,int secondarray,int secondarraylength) {
		int start=0;
		while(firstarray <= firstarraylength && secondarray <=secondarraylength) {
			if(array[firstarray] < array[secondarray]) {
				temp[start]=array[firstarray];
				start++;
				firstarray++;
			}
			else if(array[secondarray] < array[firstarray]) {
				temp[start]=array[secondarray];
				start++;
				secondarray++;
			}
			else { // If firstarray[secondleftarray] == secondarray[rightarray]
				temp[start]=array[firstarray];
				start++;
				firstarray++;
				temp[start]=array[secondarray];
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