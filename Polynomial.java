import java.io.*;
import java.util.*;

public class Polynomial {
	double[] coefficients;
    int[] exponents;

	public Polynomial(){
		coefficients = new double[0];
		exponents = new int[0];
	}
	
	public Polynomial(double[] c, int[] e){
		coefficients = c;
        exponents = e;
	}

	public Polynomial(File f) throws FileNotFoundException {
		int count = 0;
		Scanner input = new Scanner(f);
		String[] terms = input.next().split("[-+]");
		coefficients = new double[terms.length];
		exponents = new int[terms.length];
		for (int i = 0; i < terms.length; i++){
			String co = "";
			String ex = "";
			int x = -1;
			for (int j = 0; j < terms[i].length() && terms[i].charAt(j) != 'x'; j++){
				co += terms[i].charAt(j);
				x = j;
			}
			System.out.println("costring: " + co);
			coefficients[count] = Double.parseDouble(co);
			System.out.println("coefficients[count]: " + coefficients[count]);
			x+=2;
			for (int j = x; j < terms[i].length(); j++){
				ex += terms[i].charAt(j);
			}
			if (ex != ""){
				exponents[count] = Integer.parseInt(ex);
			}
			count++;
		}
		input.close();
	}

	public void saveToFile(String fileName) throws FileNotFoundException {
		PrintStream output = new PrintStream(fileName);
		String poly = "";
		for (int i = 0; i < coefficients.length; i++){
			poly += coefficients[i];
			if (exponents[i]!=0){
				poly+='x';
				poly += exponents[i];
			}
			if (i+1 < coefficients.length && coefficients[i+1] >= 0){
				poly+='+';
			}
		}
		output.println(poly);
		output.close();
	}

	public int idxInArray(int[] e, int x){
		for (int i = 0; i < e.length; i++){
			if (e[i] == x){
				return i;
			}
		}
		return -1;
	}

	public int updPoly(Polynomial r, Polynomial t, int count){
		for (int i = 0; i < t.exponents.length; i++){
			int idx = t.idxInArray(r.exponents, t.exponents[i]);
			if (idx == -1){
				r.exponents[count] = t.exponents[i];
				r.coefficients[count] = t.coefficients[i];
				count++;
			}
			else{
				r.coefficients[idx] += t.coefficients[i];
				if (idx == count){
					count ++;
				}
			}
		}
		return count;
	}

	public Polynomial add(Polynomial p){
		int length = this.coefficients.length + p.coefficients.length;
		Polynomial result = new Polynomial(new double[length], new int[length]);
		int count = 0;
		count = this.updPoly(result, this, count);
		this.updPoly(result, p, count);
		return result;
	}

	public double evaluate(double d){
		double result = 0;
		for (int i = 0;  i < coefficients.length; i++){
			result += coefficients[i]*Math.pow(d, exponents[i]);
		}
		return result;
	}

	public boolean hasRoot(double d){
		return evaluate(d) == 0;
	}

    public Polynomial multiply(Polynomial p){
		int length = this.coefficients.length*p.coefficients.length;
		Polynomial[] results = new Polynomial[length];
		Polynomial answer = new Polynomial();
		int count = 0;
		for (int i = 0; i < this.exponents.length; i++){
			for (int j = 0; j < p.exponents.length; j++){
				Polynomial temp = new Polynomial(new double[1], new int[1]);
				temp.exponents[0] = this.exponents[i] + p.exponents[j];
				temp.coefficients[0] = this.coefficients[i] * p.coefficients[j];
				results[count] = temp;
				count ++;
			}
		}
		for (int k = 0; k < length; k++){
			answer = answer.add(results[k]);
		}
		return answer;
    }
}