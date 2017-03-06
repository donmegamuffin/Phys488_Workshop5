import java.io.*;
import java.util.Random; 

// 2D tracking of a high energy muon through iron with no magnetic field
class task24
{
    static BufferedReader keyboard = new BufferedReader (new InputStreamReader(System.in)) ;
    static PrintWriter screen = new PrintWriter( System.out, true);
    
    static Random randGen = new Random(); //instantiate the class random
    static double startEnergy;
    static double stepSize;
    static double ironThickness;
    static double numberOfMuons;

    static final double muonmass = 106.;
    
    static double [][] trackOfMuon;
    static int nsteps;   
 
   
    public static void main (String [] args) throws IOException
    {
        outerloop:
        for (int i = 1500; i < 10000; i++) {
            
        randGen.setSeed(7894694);  //sets seed and obtains input data from the user
    
        
        double startEnergy = 0.2*i;
        
        double stepSize = 10;
        
        double ironThickness = 100;
       
        int numberOfMuons = 1000;       
        
     
        EnergyLoss ironEloss = new EnergyLoss(26,55.845,7.87);//sets up code to make use of 
        
        
        
    // Define position and resolution of counters that detect the muon as it 
    // exits the iron, all length units are cm
        final double xc1= ironThickness + 10; // x - coord of first counter after the iron
        final double xc2 =ironThickness + 20;
        final double xc3 =ironThickness + 30;
        final double counterYcoordResolution = 0.1; // sigma of y coord resolution in cm.
        
        
    
         //counts the number of steps
        final int nmax = 200; // maximum allowed number of steps before we stop following a muon
        
       
        double [] muonenergyXY = new double [numberOfMuons];  
        double [] ylastE = new double [numberOfMuons];   

        Histogram exitE = new Histogram(50, 0, startEnergy); //muon exit from iron
        Histogram exitY = new Histogram(50, -1,1);
        Histogram detector1 = new Histogram(100,-15,15);
        Histogram detector2 = new Histogram(100,-15,15);
        Histogram detector3 = new Histogram(100,-15,15);
       


    

    // Start tracking each muon
    for (int n =0; n < numberOfMuons; n++) 
    {
        // Define a 2D array to store the (x,y) pairs generated as track is followed.

        // allow enough space to store the hit positions on the counters.
        trackOfMuon = new double[nmax+2][2];

        double muonEnergy = startEnergy;
        double x = 0; // Set the initial starting position of muon
        double y = 0; //
        nsteps = 0; //counts the number of steps
        double theta = 0; // muon starts out parallel to x-axis
        //screen.println("Start tracking muon  " + n + " , energy =  " + muonEnergy);
        
      while (x < ironThickness && nsteps < nmax)  { // Note the 2 conditions here to avoid infinite loop
          // Step is the direction in the x-direction
         double step = Math.min(stepSize, ironThickness-x);
        // Ensure the final step just reaches the end of the iron.
            
        // Find width of MCS distribution for this muon travelling a distance step through material
         MCS ironMS = new MCS(26,55.845,7.87); //MSC for each step
        
        double muonMomentum = Math.sqrt(muonEnergy*muonEnergy - muonmass*muonmass);
        // double theta0 = ...
        // smear theta by a Gaussian random angle with mean 0 and sigma=thetaT
        // ...
        
        // smear theta by a Gaussian random angle with mean 0 and sigma=thetaT
        // ...
        theta = theta + randGen.nextGaussian()*ironMS.getThetaT(muonMomentum, step); //new theta after MSC angle 

        // If muon travels at angle theta, amount of material traversed is is d = step/cos(theta) 
        double d = Math.abs(step/Math.cos(theta));
        // energy loss going through d cm of material.
        // ....
        muonEnergy = muonEnergy - ironEloss.getEnergyLoss(muonMomentum)*Math.abs(d);  //new energy after energyloss and abs of d means always positive as d was negative sometimes

        if(muonEnergy < muonmass) {
            
            break; // This causes the 'for' loop to terminate.
        }

        // calculate next (x,y) position
        double xnew = x + step ; 
        double ynew = y + d*Math.sin(theta); 

        //screen.println("tracking.. nsteps, x, y = " + nsteps + "  " + xnew + "  " + ynew + "  " + d + "  " + theta);
        // Store these co-ordinates
        trackOfMuon[nsteps][0] = xnew;
        trackOfMuon[nsteps][1] = ynew;
        // Update coordinates in order to take the next step.
        x = xnew;
        y = ynew;
        
        nsteps++;
        }

        if (nsteps == nmax) {
        screen.println("Too many steps for muon " + n + ",  abandon it");
        } else {
        exitE.fill(muonEnergy);
        }
        
        // Finished tracking this muon, assume the path to counters is a straight line
        // Work out y-hit position on each counter and smear it by the resolution.
        double yhitOnC1 = (xc1 - x)*Math.tan(theta) + y;
        yhitOnC1 += randGen.nextGaussian() * counterYcoordResolution;
        double yhitOnC2 = (xc2 - x)*Math.tan(theta) + y;
        yhitOnC2 += randGen.nextGaussian() * counterYcoordResolution;
        double yhitOnC3 = (xc3 - x)*Math.tan(theta) +y;
        yhitOnC3 += randGen.nextGaussian() * counterYcoordResolution;
        
        // Add these coords into the array
        trackOfMuon[nsteps+1][0] = xc1;
        trackOfMuon[nsteps+1][1] = yhitOnC1;
        trackOfMuon[nsteps+2][0] = xc2;
        trackOfMuon[nsteps+2][1] = yhitOnC2;
        trackOfMuon[nsteps+3][0] = xc3;
        trackOfMuon[nsteps+3][0] = yhitOnC3;
        
        detector1.fill(yhitOnC1);
        detector2.fill(yhitOnC2);
        detector3.fill(yhitOnC3);
        
        // pass the data to this method for any further processing
       
       
        exitY.fill(trackOfMuon [nsteps-1][1]); //fills exity with all the y positions like for each detector
        
        ylastE [n] = trackOfMuon [nsteps-1][1]; //used to fill the final energy for each y position
        muonenergyXY[n] = muonEnergy; //used to fill the final y positions for each energy
        // Now generate the next muon
    }
    int underflow = exitE.getunder();
    int sumbins = exitE.getIntegral();
    
    if (underflow == sumbins){
        screen.println("The start energy is " + startEnergy);
        screen.println("undeflows = " + underflow);
        screen.println("sum of bins = " + sumbins);
        exitE.writeToDisk("muon_exitE.csv");
        break outerloop;
    }
       
     screen.println(startEnergy);       
        
  }
}
}