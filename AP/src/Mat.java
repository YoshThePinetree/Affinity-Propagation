import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mat {

		public double[][] ScalaMultiply(double X[][], double a){
			int n=X.length;
			for(int i=0; i<n; i++) {
				for(int j=0; j<n; j++) {
					X[i][j] = X[i][j] * a;
				}
			}
			return X;
		}

		public double[][] Sum(double X[][], double Y[][]){
			int n=X.length;
			double Z [][] = new double [n][n];
			for(int i=0; i<n; i++) {
		    	for(int j=0; j<n; j++) {
		    		Z[i][j] = X[i][j] + Y[i][j];
		    	}
	    	}
	    	return Z;
		}

		public double[][] WeightedSum(double X[][], double Y[][], double a, double b){
			int n=X.length;
			double Z [][] = new double [n][n];
			for(int i=0; i<n; i++) {
		    	for(int j=0; j<n; j++) {
		    		Z[i][j] = (a*X[i][j]) + (b*Y[i][j]);
		    	}
	    	}
	    	return Z;
		}

	 	public double[][] CopyUpLow(double X[][]){
	    	int n=X.length;
	    	double Y [][] = new double [n][n];
	    	Y=X;
	    	for(int i=0; i<n; i++) {
		    	for(int j=0; j<n; j++) {
		    		Y[j][i] = X[i][j];
		    	}
	    	}
	    	return Y;
	    }

	    public double[][] AssignDiag(double X[][], double diag){
	    	int n=X.length;
	    	for(int i=0; i<n; i++) {
		    	X[i][i]=diag;
	    	}
	    	return X;
	    }

	    public double FindMedian(double X[][]){
	    	int n=X.length;
	    	double median=0;
	    	List<Double> list = new ArrayList<Double>();

	    	for(int i=0; i<n; i++) {
				for(int j=i+1; j<n; j++) {
					list.add(X[i][j]);
				}
	    	}

	    	//for(Double str :list){
	    	//	  System.out.println(str);
    		//}

	    	Double[] array = list.toArray(new Double[list.size()]);
	    	Arrays.sort(array);

	    	int m=array.length/2;

	    	if(0 != (array.length%2)){
				median = array[m];
			}
			else{
				median = (array[m-1] + array[m])/2.0;
			}


	    	return median;
	    }

	    public double FindMin(double X[][]){
	    	int n=X.length;
	    	double min=X[0][1];

	    	for(int i=0; i<n; i++) {
				for(int j=i+1; j<n; j++) {
					min=Math.min(min, X[i][j]);
				}
	    	}

	    	return min;
	    }

	    public double FindVecMaxExp(double X[], int m){
	    	int n=X.length;
	    	double max = -999999999;

	    	for(int i=0; i<n; i++) {
	    		if(i!=m) {
		    		max = Math.max(max, X[i]);
	    		}
	    	}

	    	return max;
	    }

	    public int FindVecArgMax(double X[]){
	    	int n=X.length;
	    	int ind=0;
	    	double max = -999999999;

	    	for(int i=0; i<n; i++) {
	    		if(X[i]>max) {
	    			max=X[i];
	    			ind=i;
	    		}
	    	}

	    	return ind;
	    }
}
