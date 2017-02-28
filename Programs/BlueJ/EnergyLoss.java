import java.io.*;
import java.util.stream.*;
import java.util.Random;

class EnergyLoss
{   
    static PrintWriter screen = new PrintWriter(System.out, true);
    //Universal constant
    private final double K = 0.307075;          // MeV cm^2
    private final double electronmass = 0.511;  // MeV
    private final double c = 3E8;               // m/s     
    //Material variables
    int AtomicNumber;           // atomic Z
    double AtomicMass;          // atomic A
    double density;             // of material
    //Particle variables
    double charge = 1;          //Coulombs
    double Beta;    
    double momentum;            // MeV
    double mass = 106;          // MeV
    double energy;              // energy of muon
    
    double energyloss;          // energy lost per distance
    
    public EnergyLoss(int Z, double A, double p) {
        AtomicNumber = Z;
        AtomicMass = A;
        density = p;       
    }
    
    public double getenergyloss(double m){
        
        momentum = m;
        energy = Math.pow((Math.pow(m,2)+Math.pow(mass,2)),0.5);        
        Beta = m/energy;
        double Beta2 = Math.pow(Beta,2);
        gamma = 1/(Math.pow((1-Beta2),0.5));
        double Gamma2 = Math.pow(gamma,2);
        double I = 0.0000135*AtomicNumber;
        double Wmax = (2*electronmass*Beta2*Gamma2)/(1+((2*gamma*electronmass)/mass)+Math.pow((electronmass/mass),2));
        energyloss = K*Math.pow(charge,2)*density*(AtomicNumber/AtomicMass)*(1/Beta2)*(0.5*Math.log((2*electronmass*Beta2*Gamma2*Wmax)/Math.pow(I,2))-Beta2);
        
        return energyloss;
    }    
}
    
