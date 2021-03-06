import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class APMat {
	Mat mat = new Mat();

	public double[][] Calcsim(double X[][], String dist, String pretype, double wp) {
    	int n = X.length;		// the number of elements
    	int d = X[0].length;	// the number of dimension
    	double S [][] = new double [n][n] ;
		double xsum=0;
		double sdiag=0;	// the value given in the diagonal elements of the matrix
		Mat mat = new Mat();

		switch (dist) {
		case "Euclid":	// Metric: Euclidean distance
			for(int i=0; i<n; i++) {
				for(int j=i+1; j<n; j++) {
					for(int k=0; k<d; k++) {
						xsum = xsum + Math.pow((X[i][k]-X[j][k]),2);
					}
					S[i][j]=Math.sqrt(xsum);
					xsum=0;
				}
			}

			switch (pretype) {
			case "Median":
				sdiag = mat.FindMedian(S);
				break;
			case "Min":
				sdiag = mat.FindMin(S);
				break;
			}
			sdiag = sdiag * wp;		// weighting the preference

			S = mat.CopyUpLow(S);			// copy the elements of upper triangle to lower triangle
			S = mat.AssignDiag(S,sdiag);	// assign sdiag into the all diagonal elements of S
			S = mat.ScalaMultiply(S, -1);	// negativenize

			break;
			/*
		case "SEuclid":	// Metric: Scaled Euclidean distance
			double[] X = new double[nd];
			double[] sigma = new double[nf];
			double val;

			StatCalc stat = new StatCalc();
			for(int i=0; i<nf; i++) {
				X = getCul(data,i);
				sigma[i] = stat.Var(X);
			}

			for(int i=0; i<nd; i++) {
				for(int j=count; j<nd; j++) {
					for(int k=0; k<nf; k++) {
						val = ((Math.pow((data[i][k]-data[j][k]),2)) / sigma[k]);
						if(Double.isNaN(val) == false) {
							xsum = xsum + val;
						}
					}
					D[i][j]=Math.sqrt(xsum);
					xsum = 0;
				}
				count = count+1;
			}
			D = Dissim.matUcopy2L(D);

			break;
		case "City":	// Metric: city block distance

			for(int i=0; i<nd; i++) {
				for(int j=count; j<nd; j++) {
					for(int k=0; k<nf; k++) {
						xsum = xsum + Math.abs(data[i][k]-data[j][k]);
					}
					D[i][j] = xsum;
					xsum = 0;
				}
				count = count+1;
			}
			D = Dissim.matUcopy2L(D);

			break;
		case "Chebyshev":	// Metric: Chebyshev distance
			double dmax = 0;
			double a = 0;

			for(int i=0; i<nd; i++) {
				for(int j=count; j<nd; j++) {
					for(int k=0; k<nf; k++) {
						a = Math.abs(data[i][k]-data[j][k]);
						if(dmax < a) {
							dmax = a;
						}
					}
					D[i][j] = dmax;
					dmax=0;
				}
				count=count+1;
			}
			D = Dissim.matUcopy2L(D);

			break;
		case "Mahalanobis":
			break;
		case "Cosine":
			break;
			*/
		}
		return S;
	}

	public double [][] Calcrho(double S[][], double A[][]){
		int n=S.length;
		double rho[][] = new double [n][n];
		double SA[][] = new double [n][n];
		SA = mat.Sum(S, A); // sum of similarity and availability
		double max;

		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(i==j) {		// diagonal elements
					max=mat.FindVecMaxExp(S[i], j);
					rho[i][j] = S[i][j] - max;
				}else {			// nondiagonal elements
					max=mat.FindVecMaxExp(SA[i], j);
					rho[i][j] = S[i][j] - max;
				}
			}
		}

		return rho;
	}

	public double [][] Calcalpha(double R[][]){
		int n=R.length;
		double sum=0;
		double alpha[][] = new double [n][n];

		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(i==j) {
					for(int k=0; k<n; k++) {
						if(k!=j) {
							sum = sum + Math.max(0, R[k][j]);
						}
					}
					alpha[i][j] = sum;
					sum=0;
				}else {
					for(int k=0; k<n; k++) {
						if(k!=i && k!=j) {
							sum = sum + Math.max(0, R[k][j]);
						}
					}
					alpha[i][j] = Math.min(0, (R[j][j]+sum));
					sum=0;
				}
			}
		}

		return alpha;
	}

	public int [] Clustering(double R[][], double A[][]) {
		int n=R.length;
		int C[] = new int [n];
		double RA [][] = new double [n][n];
		boolean exmplr [] = new boolean [n];		// exempler vector
		RA = mat.Sum(R, A); 						// sum of similarity and availability
		List<Integer> list = new ArrayList<Integer>();

		for(int i=0; i<n; i++) {	// if diag(r+a) > 0 then the node will be an exempler
			if(RA[i][i]>0) {
				exmplr[i]=true;
				list.add(i);
			}else {
				exmplr[i]=false;
			}
		}

		double RA1[][] = new double [n][list.size()];	// get the RA only exempler
		for(int i=0; i<n; i++) {
			for(int j=0; j<list.size(); j++) {
				RA1[i][j]=RA[i][list.get(j)];
			}
		}

		if(list.size()!=0){
			int tmp;
			for(int i=0; i<n; i++) {	// determine cluster partition
				if(exmplr[i]==true) {
					C[i] = i;
				}else {
					tmp = mat.FindVecArgMax(RA1[i]);
					C[i] = list.get(tmp);
				}
			}
		}else{
			for(int i=0; i<n; i++) {	// determine cluster partition
				C[i]=i;
			}
		}

		return C;
	}

	public boolean CheckConverge(int C[], int C1[]) {
		boolean a;
		int n=C.length;
		int comp [] = new int [n];
		Arrays.fill(comp,0);

		for(int i=0; i<n; i++) {
			if(C[i]!=C1[i]) {
				comp[i]=1;
			}
		}
		if(SumVecEleAll(comp)==0) {
			a=true;		// clusteirng converged
		}else {
			a=false;
		}

		return a;
	}

	private static int SumVecEleAll(int X[]) {
		int n=X.length;
		int sum=0;
		for(int i=0; i<n; i++) {
			sum = sum + X[i];
		}
		return sum;
	}


}
