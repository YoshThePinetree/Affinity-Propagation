import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AP {
	public void AffinityPropagation(){	// AP algorithm method
		File file = new File("C:\\JavaIO\\Input\\g2-2-30.txt");	// The file name
		int d = 2;	// the number of dimension of the data
		int data1[][] = DataRead(file,d);	// Load the data
		System.out.println();

		///////////////////////////////
		// Paramter Set & Initiation //
		///////////////////////////////
		int c=2;							// the number of clusters
		int n=data1.length;					// the number of elements
		int maxite=300;						// the number of maximum iteration
		double q=1.4;						// the fuzzifier parameter
		int rseed=1;						// random seed
		String dist="Euclid";				// the distance metric type
		double F [] = new double [maxite];	// Objective function value


		// data normalization
		double data[][] = new double[c][n];
//		data=NormStd.Normalization(data1);
		data=NormStd.Standardization(data1);


		Sfmt rnd = new Sfmt(rseed);
		//
	}

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