public class Fraction {
	public double numerator;
	public double denominator;
	public void Flip() {
		double temporary = numerator;
		numerator=denominator;
		denominator=temporary;
	}
	public void Simplify() {
		int a = (int) numerator;
		int b = (int) denominator;
		int gcd = GCD(a, b);
		numerator = a / gcd;
		denominator = b / gcd;
	}
	private int GCD(int a, int b) {
		while (b != 0) {
			int temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}
}
