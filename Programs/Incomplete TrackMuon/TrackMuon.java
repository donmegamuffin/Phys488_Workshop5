import java.io.*;
import java.util.Random; 

// 2D tracking of a high energy muon through iron with no magnetic field
class TrackMuon
{
    static BufferedReader keyboard = new BufferedReader (new InputStreamReader(System.in)) ;
    static PrintWriter screen = new PrintWriter( System.out, true);
    
    static Random randGen = new Random(); //instantiate the class random
    static double startEnergy;
    static double stepSize;
    static double ironThickness;
    static double numberOfMuons;

    static final double muonmass = 106.;
    
       
 
   private static void lookAtThisMuon(int nsteps, double [][] track, double finalE)
   {
      Histogram exitE = new Histogram(50, 0, startEnergy); //muon exit from iron
      Histogram exitY = new Histogram(50, -0.5,0.5);
       
     double xlast,ylast;
     xlast = track  [nsteps-1][0];
     ylast = track  [nsteps-1][1];
     screen.println(" last (x,y) of track =  ( " + xlast + " , " + ylast + ")" );
     screen.println(" energy of muon as it leaves the material = " + finalE +"  MeV");
    
     if (xlast >= ironThickness) //creates histogram if muon leaves the material
     {
        exitE.fill(finalE);//histogram storing data of the exit energy
        exitY.fill(ylast);
       }
    return;
    }
    
    // Eloss class: ...
    //EnergyLoss ironEloss =     new EnergyLoss(...);
    // MCS class: ...
    //MCS ironMS = new MCS(...);
    public static void main (String [] args) throws IOException
    {
        randGen.setSeed(7894694);  //sets seed and obtains input data from the user
    
        screen.println("Type in starting energy in MeV ");
        double startEnergy = new Double(keyboard.readLine() ).doubleValue();
        screen.println("Type in a step size in cm ");
        double stepSize = new Double(keyboard.readLine() ).doubleValue();
        screen.println("Type in the thickness of iron, cm "); 
        double ironThickness = new Double(keyboard.readLine()).doubleValue();
        screen.println("Type in the number of muons to track ");
        int numberOfMuons = new Integer(keyboard.readLine() ).intValue();
        
        EnergyLoss ironEloss = new EnergyLoss(26,55.845,7.87);//sets up code to make use of 
        MCS ironMS = new MCS(26,55.845,7.87,ironThickness);
        
        
    // Define position and resolution of counters that detect the muon as it 
    // exits the iron, all length units are cm
        final double xc1= ironThickness + 10; // x - coord of first counter after the iron
        final double xc2 =ironThickness + 20;
        final double xc3 =ironThickness + 30;
        final double counterYcoordResolution = 0.1; // sigma of y coord resolution in cm.
    
       // int nsteps; //counts the number of steps
        final int nmax = 200; // maximum allowed number of steps before we stop following a muon
        

        Histogram exitE = new Histogram(50, 0, startEnergy); //muon exit from iron
        Histogram exitY = new Histogram(50, -1,1);
        Histogram detector1 = new Histogram(100,-50,50);
        Histogram detector2 = new Histogram(100,-50,50);
        Histogram detector3 = new Histogram(100,-50,50);

    

    // Start tracking each muon
    for (int n =0; n < numberOfMuons; n++) 
    {
        // Define a 2D array to store the (x,y) pairs generated as track is followed.
        // allow enough space to store the hit positions on the counters.
        double [][] trackOfMuon = new double[nmax+2][2];

        double muonEnergy = startEnergy;
        double x = 0; // Set the initial starting position of muon
        double y = 0; //
        int nsteps = 0; //counts the number of steps
        double theta = 0; // muon starts out parallel to x-axis
        screen.println("Start tracking muon  " + n + " , energy =  " + muonEnergy);
        
      while (x < ironThickness && nsteps < nmax)  { // Note the 2 conditions here to avoid infinite loop
          // Step is the direction in the x-direction
         double step = Math.min(stepSize, ironThickness-x);
        // Ensure the final step just reaches the end of the iron.
            
        // Find width of MCS distribution for this muon travelling a distance step through material
        
        
        double muonMomentum = Math.sqrt(muonEnergy*muonEnergy - muonmass*muonmass);
        // double theta0 = ...
        // smear theta by a Gaussian random angle with mean 0 and sigma=thetaT
        // ...
        theta = theta + randGen.nextGaussian()*counterYcoordResolution;

        // If muon travels at angle theta, amount of material traversed is is d = step/cos(theta) 
        double d = step/Math.cos(theta);
        // energy loss going through d cm of material.
        // ....
        muonEnergy = muonEnergy - ironEloss.getEnergyLoss(muonMomentum)*d;

        if(muonEnergy < muonmass) {
            screen.print("Energy of muon below rest mass - got stuck!");
            break; // This causes the 'for' loop to terminate.
        }

        // calculate next (x,y) position
        double xnew = x + step ; 
        double ynew = y + d*Math.sin(theta); 

        //screen.println("tracking.. nsteps, x, y = " + nsteps + "  " + xnew + "  " + ynew);
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
        yhitOnC2 += randGen.nextGaussian() * counterYcoordResolution;
        
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
        lookAtThisMuon(nsteps, trackOfMuon, muonEnergy);
        // Now generate the next muon
    }
        exitE.writeToDisk("muon_exitE.csv");  
        exitY.writeToDisk("muon_exitY.csv");
        detector1.writeToDisk("muonDetector1.csv");
        detector2.writeToDisk("muonDetector2.csv");
        detector3.writeToDisk("muonDetector3.csv");
  }
}