import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AP {
///////////////////////////////////////////////////////////////////////////////////
	public void AffinityPropagation(){	// AP algorithm method
//		File file = new File("C:\\JavaIO\\Input\\g2-2-30.txt");	// The file name
		File file = new File("C:\\JavaIO\\Input\\xor_simple.txt");	// The file name
		int d = 2;	// the number of dimension of the data
//		int data1[][] = DataRead(file,d);	// Load the data
		double data1[][] = DataReadDouble(file,d);	// Load the data
		System.out.println();

		///////////////////////////////
		// Paramter Set & Initiation //
		///////////////////////////////
		int c;								// the number of clusters
		int n=data1.length;					// the number of elements
		int maxite=300;						// the number of maximum iteration
		double lamda=0.9;					// the damping factor
		int rseed=1;						// random seed
		String dist="Euclid";				// the distance metric type
		String pref="Median";				// the preference type (method to calc the diagonal element of s)
		double F [] = new double [maxite];	// Objective function value
		double s [][] = new double [n][n];	// similarity matrix
		double r [][] = new double [n][n];	// responsibility matrix
		double a [][] = new double [n][n];	// availability matrix
		double rprop [][] = new double [n][n];	// availability matrix (propagation)
		double aprop [][] = new double [n][n];	// availability matrix (propagation)

		// data normalization
		double data[][] = new double[n][d];
//		data=NormStd.Normalization(data1);
//		data=NormStd.Standardization(data1);
		data=data1;

		Sfmt rnd = new Sfmt(rseed);
		s = Similarity(data,dist,pref);

		/*
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(j<3-1) {
					System.out.printf("%.3f", s[i][j]);
				}else {
					System.out.printf("%.3f\n", s[i][j]);
				}
			}
		}
		*/

		//


	}

///////////////////////////////////////////////////////////////////////////////////

		/////////////
//******// Methods //**************************************************************
		/////////////

	//public int[][] DataRead(int n){
		public static int[][] DataRead(final File file, int d) {
	        List<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();
	        for (int i = 0; i < d; i++) {
	            lists.add(new ArrayList<Integer>());
	        }
	        BufferedReader br = null;
	        try {
	            // Read the file, save data to List<Integer>
	            br = new BufferedReader(new FileReader(file));
	            String line = null;
	            while ((line = br.readLine()) != null) {
	                // Add nth integer to lists[n]
	                List<Integer> ints = parse_line(line,d);
	                for (int i = 0; i < d; i++) {
	                    (lists.get(i)).add(ints.get(i));
	                }
	            }

	            // convert lists to 2 Integer[]
	            Integer[] array1 = lists.get(0).toArray(new Integer[lists.size()]);
	            int n=array1.length;
	            int data[][] = new int[n][d];

	            int j=0;
	            while(j<d) {
	            	// convert lists to 2 Integer[]
	                Integer[] array = lists.get(j).toArray(new Integer[lists.size()]);
		            for(int i=0; i<n; i++) {
		                	data[i][j]=array[i];
		            }

		            j++;
	            }

	            return data;

	        } catch (Exception ex) {
	            System.out.println(ex);
	        } finally {
	            try {
	                if (br != null) {
	                    br.close();
	                }
	            } catch (Exception ex) {
	                // ignore error
	            }
	        }
	        return null;
	    }

	    // parse 2 integers as a line of String
	    private static List<Integer> parse_line(String line, int d) throws Exception {
	        List<Integer> ans = new ArrayList<Integer>();
	        StringTokenizer st = new StringTokenizer(line, " ");
	        if (st.countTokens() != d) {
	            throw new Exception("Bad line: [" + line + "]");
	        }
	        while (st.hasMoreElements()) {
	            String s = st.nextToken();
	            try {
	                ans.add(Integer.parseInt(s));
	            } catch (Exception ex) {
	                throw new Exception("Bad Integer in " + "[" + line + "]. " + ex.getMessage());
	            }
	        }
	        return ans;
	    }

	    public static double[][] DataReadDouble(final File file, int d) {
	        List<ArrayList<Double>> lists = new ArrayList<ArrayList<Double>>();
	        for (int i = 0; i < d; i++) {
	            lists.add(new ArrayList<Double>());
	        }
	        BufferedReader br = null;
	        try {
	            // Read the file, save data to List<Integer>
	            br = new BufferedReader(new FileReader(file));
	            String line = null;
	            while ((line = br.readLine()) != null) {
	                // Add nth integer to lists[n]
	                List<Double> ints = parse_line_double(line,d);
	                for (int i = 0; i < d; i++) {
	                    (lists.get(i)).add(ints.get(i));
	                }
	            }

	            // convert lists to 2 Integer[]
	            Double[] array1 = lists.get(0).toArray(new Double[lists.size()]);
	            int n=array1.length;
	            double data[][] = new double[n][d];

	            int j=0;
	            while(j<d) {
	            	// convert lists to 2 Integer[]
	                Double[] array = lists.get(j).toArray(new Double[lists.size()]);
		            for(int i=0; i<n; i++) {
		                	data[i][j]=array[i];
		            }

		            j++;
	            }

	            return data;

	        } catch (Exception ex) {
	            System.out.println(ex);
	        } finally {
	            try {
	                if (br != null) {
	                    br.close();
	                }
	            } catch (Exception ex) {
	                // ignore error
	            }
	        }
	        return null;
	    }

	    // parse 2 integers as a line of String
	    private static List<Double> parse_line_double(String line, int d) throws Exception {
	        List<Double> ans = new ArrayList<Double>();
	        StringTokenizer st = new StringTokenizer(line, " ");
	        if (st.countTokens() != d) {
	            throw new Exception("Bad line: [" + line + "]");
	        }
	        while (st.hasMoreElements()) {
	            String s = st.nextToken();
	            try {
	                ans.add(Double.parseDouble(s));
	            } catch (Exception ex) {
	                throw new Exception("Bad Integer in " + "[" + line + "]. " + ex.getMessage());
	            }
	        }
	        return ans;
	    }


	    private static void DataWrite(String file_name, double data[][]) {
	    	int n = data.length;	// the number of rows
	    	int m = data[0].length;	// the number of colmuns

	    	try {
	            PrintWriter pw = new PrintWriter(file_name);
	            for(int i=0; i<n; i++) {
	            	for(int j=0; j<m; j++) {
	            		if(j<m-1) {
	                		pw.format("%.3f\t", data[i][j]);
	            		}else {
	                		pw.format("%.3f\n", data[i][j]);
	            		}
	                }
	            }

	            pw.close();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }

	    private static double[][] Similarity(double X[][], String dist, String pretype) {
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




}