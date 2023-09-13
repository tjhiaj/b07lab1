public class Polynomial {
	double[] coefficients;

	public Polynomial(){
		coefficients = new double[]{0};
	}
	
	public Polynomial(double[] c){
		coefficients = c;
	}

	public Polynomial add(Polynomial p){
		Polynomial p1 = p;
		for (int i = 0;  i < this.coefficients.length; i++){
			p1.coefficients[i] += this.coefficients[i];
		}
		return p1;
	}

	public double evaluate(double d){
		double result = 0;
		for (int i = 0;  i < coefficients.length; i++){
			result += coefficients[i]*Math.pow(d, i);
		}
		return result;
	}

	public boolean hasRoot(double d){
		return evaluate(d) == 0;
	}
}
		
		
