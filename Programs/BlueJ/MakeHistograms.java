import java.io.*;
import java.util.Random;

class MakeHistograms
{
    static BufferedReader keyboard = new BufferedReader (new InputStreamReader(System.in)) ;
    static PrintWriter screen = new PrintWriter(System.out, true);
    static Random randGen = new Random();

    private static double nearGauss(double mean, double sigma)
    {
    // add up 12 random numbers
    double sum = 0.;
    for (int n = 0 ; n < 12; n++) {
        sum = sum + randGen.nextDouble();
    }
    return (mean + sigma*(sum - 6.0));
    }

    public static void main (String [] args ) throws IOException
    {       
        // create an instance of the Class Histogram: 20 bins from 0.0 to 1.0
        Histogram nearGaussHist = new Histogram(50, -2, 2); 
        Histogram nextGaussHist = new Histogram(50, -2, 2); //<<TASK1.1>>
        
        
        screen.println( "Input the number of random numbers to generate");
        int trials = new Integer(keyboard.readLine()).intValue();
        long startTime = System.nanoTime();
        //For Near Gaussian Graph
        for (int i=0;i<trials;i++) 
        {
            //For Near Gauss
            double value_near = nearGauss(0,0.5);
            nearGaussHist.fill(value_near);
            //For Next Gauss                                //<<TASK1.1>>
            double value_next = randGen.nextGaussian()*0.5;
            nextGaussHist.fill(value_next);
        }
        
        //Console and file print
        nearGaussHist.print();             
        nearGaussHist.writeToDisk("neargauss_test.csv");   
        nextGaussHist.print();                            //<<TASK1.1>>
        nextGaussHist.writeToDisk("nextgauss_test.csv");  //<<TASK1.1>> 
        
        
        //Execute time
        long endTime = System.nanoTime();
        System.out.println("Took "+(endTime - startTime) + " ns");
    }
}
        
