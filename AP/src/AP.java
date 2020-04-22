import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
		int n=data1.length;						// the number of elements
		int maxite=1000;						// the number of maximum iteration
		double lamda=0.90;						// the damping factor
		double wp=0.241;							// the weight of preference
		int rseed=1;							// random seed
		String dist="Euclid";					// the distance metric type
		String pref="Median";					// the preference type (method to calc the diagonal element of s)
		double s [][] = new double [n][n];		// similarity matrix
		double r [][] = new double [n][n];		// responsibility matrix
		double a [][] = new double [n][n];		// availability matrix
		double rho [][] = new double [n][n];	// availability matrix (propagation)
		double alpha [][] = new double [n][n];	// availability matrix (propagation)
		int c [] = new int [n];					// the clustering vector
		int carc [][] = new int [maxite][n];	// the clustering vector archive

		// data normalization
		double data[][] = new double[n][d];
//		data=NormStd.Normalization(data1);
//		data=NormStd.Standardization(data1);
		data=data1;

		// create instance
		Sfmt rnd = new Sfmt(rseed);
		APMat apmat = new APMat();
		Mat mat = new Mat();

		s = apmat.Calcsim(data,dist,pref,wp);	// similarity calculation

		////////////////////////////////////////////////////////////
		// Recursive Cauclation of Responsibiliy and Availability //
		// (the main loop of AP)                                  //
		////////////////////////////////////////////////////////////

		// AP matrix initiation by zero
		for(int i=0; i<n; i++) {
			Arrays.fill(r[i],0);
			Arrays.fill(a[i],0);
			Arrays.fill(rho[i],0);
			Arrays.fill(alpha[i],0);
			Arrays.fill(carc[i],0);
		}
		Arrays.fill(c,0);

		int ite=0;	// iteration counter
		boolean ctr=false;

		// loop
		while(ite<maxite && ctr==false) {
			rho = apmat.Calcrho(s,a);
			alpha = apmat.Calcalpha(r);
			r = mat.WeightedSum(rho, r, 1-lamda, lamda);
			a = mat.WeightedSum(alpha, a, 1-lamda, lamda);
			c = apmat.Clustering(r, a);

			for(int i=0; i<n; i++) {
				carc[ite][i] = c[i];
			}

			if(ite==0) {
				ctr=false;
			}else {
				ctr=apmat.CheckConverge(carc[ite-1],carc[ite]);
			}
			System.out.println("Iteration: "+ (ite+1));

			ite=ite+1;
		}

		c = apmat.Clustering(r, a);

		for(int i=0; i<n; i++) {
			System.out.printf("%d\n",c[i]);
		}

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






}