import java.io.*;
import java.util.stream.*;
import java.util.Random;

class Histogram
{   
    static PrintWriter screen = new PrintWriter(System.out, true);
    static Random randGen = new Random();
    
    private double binlow, binhigh;
    private double binsize;
    private int SIZE;
    
    private double[] error;
    private double[] methods = new double[getSize()+4];
    private int nfilled = 0;
    private double[][] trackOfMuon;
    private double binCentre;   
    private double AtomicNumber;
    private double ironThickness;
    private double stepSize;
    private double xnew;
    private double ynew;    
    private double AtomicMass;
    private double thickness;
    public double c = 3E8;
    public double ylast;
    // integer array to store the actual histogram data
    private int[] hist;
    private double[] histH;
    private int underflow, overflow;

    // constructor for the class Histogram
    public Histogram(int size, double low, double high)
    {
    // store the parameters and setup the histogram
    // note that parameters need to have different names than class variables
    SIZE = size;
    binlow = low;
    binhigh = high;
    
    binsize = (binhigh - binlow) / (double) SIZE;
    hist = new int[SIZE];
    underflow = 0;
    overflow = 0;
    }
    
    
    //-------------------------------------
    // instance methods start here
    //-------------------------------------

    public int getSize()
    {
    return SIZE;
    }

    //----------------------------------------
    public void fill(double value)
    {
    if (value < binlow) {
        underflow++;
        nfilled++;
            } else if (value >= binhigh) {
        overflow++;
        nfilled++;
            } else {
        // add 1 to the correct bin
        nfilled++;
        int bin = (int) ( (value - binlow)/binsize);
        hist[bin]++;
    } 
    }
   

    //-------------------------------------
    public int getContent(int nbin)
    {
    // returns the contents on bin 'nbin' to the user
    return hist[nbin];
    }
    
    public int getnfilled() {
        return nfilled;
    }
  
    public int getunder() {
        return underflow;
    }
    
    public int getover() {
        return overflow;
    }
    
    public double geterror(int bin) {
        error = new double[SIZE];
        error[bin] = Math.sqrt(hist[bin]);
        return error[bin];
    }
    
    public int getIntegral() {
        int integral = IntStream.of(hist).sum();     
        return integral;
    }
    
        
    public double methods(int m) {
        methods = new double[2*getSize()+4];
        
        methods[0] = getnfilled();
        methods[1] = getover();
        methods[2] = getunder();
        methods[3] = getIntegral();
    
        for (int i = 0; i < getSize(); i++){
          methods[i+4] = getContent(i);
          methods[i+4+getSize()] = geterror(i);
        }
        
        return methods[m];
        }
    
     public void print(PrintWriter output) {
        
        for (int i = 0; i < getSize(); i++){
          output.println("Bin " + i + " = " + getContent(i) + " +- " + geterror(i));
        }
        output.println("The number of trials = " + getnfilled() + " , the sum of the contents = " + getIntegral());
        output.println("Underflows = " + getunder() + " , Overflows = " + getover());
        }
        
        
    public double [] getmethods() {
        return methods;
    }
    
    public int [] gethist() {
        return hist;
    }
    
    
    public double getbinSize()
    {
    return binsize;
    }
    
    public void writeToDisk(String filename) throws IOException
    {
        FileWriter file = new FileWriter(filename);  
        PrintWriter outputFile = new PrintWriter(file); 
        
        outputFile.println("nfilled , " + methods(0));
        outputFile.println("sum of bins , " + methods(3));
        outputFile.println("overflows  , " + methods(1));
        outputFile.println("underflows  , " + methods(2));
        
        for (int n = 0; n < getSize(); n++) {
        double binCentre = binlow + getbinSize()/2 + n*getbinSize();        
        outputFile.println(n + "," + binCentre + "," + methods(n+4) + "," + methods(n+4+getSize()));
      }
      outputFile.close();
      screen.println("Data written to disk in file " + filename);
      return;
    }
    
    public void writeToDiskXY(double [] muonenergyXY, double [] ylastE, int nsteps,String filename) throws IOException
    {
        FileWriter file = new FileWriter(filename);  
        PrintWriter outputFile = new PrintWriter(file);        
       
        
        for (int n = 0; n <  muonenergyXY.length; n++) {
        outputFile.println(n + "," + muonenergyXY [n] + "," + ylastE [n]);
      }
      outputFile.close();
      screen.println("Data written to disk in file " + filename);
      return;
    }
  
    public double getbincentre()
    {
    return binCentre;
    }   
    
    public double nearGauss(double mean, double sigma)
    {
    // add up 12 random numbers
    double sum = 0.;
    for (int n = 0 ; n < 12; n++) {
        sum = sum + randGen.nextDouble();
    }
    return (mean + sigma*(sum - 6.0));
    }
    
   
    
    
}
    
