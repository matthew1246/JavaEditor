public class Fraction {
	public double numerator;
	public double denominator;
	public void Flip() {
		double temporary = numerator;
		numerator=denominator;
		denominator=temporary;
	}
}
