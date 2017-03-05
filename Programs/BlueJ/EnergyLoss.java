/*  Authors: Luke Jones, Zachary Humphreys, Lorna Baker
*   Intructions: 
*   2 Sets of inputs are required for all of the code to function.
*   1)The constructor requires 3 material arguments (in order): atomic number, atomic mass, density.
*   2)The getEnergyLoss method require addition arguments: particle momentum (MeV), and material thickness (cm)
*/

import java.io.*;

class EnergyLoss
{   
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
    double mass = 106;          // MeV
        
    public EnergyLoss(int Z, double A, double p) 
    {
        AtomicNumber = Z;
        AtomicMass = A;
        density = p;       
    }
    
    public double getEnergyLoss(double m)
    {
        double momentum = m;                                                       //MeV
        double energy = Math.pow((Math.pow(momentum,2)+Math.pow(mass,2)),0.5);     //energy of muon //MeV      
        double Beta2 = Math.pow((momentum/energy),2);                              //Velocity as fraction of speed of light squared
        double Gamma2 = Math.pow((1/(Math.pow((1-Beta2),0.5))),2);                 //Lorentz Factor squared
        double I = 0.0000135*AtomicNumber;                                         //Mean excitation energy //MeV
        double Wmax = (2*electronmass*Beta2*Gamma2)/(1+((2*Math.sqrt(Gamma2)*electronmass)/mass)+Math.pow((electronmass/mass),2));
        double energyloss = K*Math.pow(charge,2)*density*(AtomicNumber/AtomicMass)*(1/Beta2)*(0.5*Math.log((2*electronmass*Beta2*Gamma2*Wmax)/Math.pow(I,2))-Beta2);
        
        return energyloss;          // energy lost per distance (MeV/cm
    }    
}
    
