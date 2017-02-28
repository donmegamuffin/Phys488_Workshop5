/* Author: Zachary Humphreys; ID:200951438
*   Intructions: 
*   2 Sets of inputs are required for all of the code to function.
*   1)The constructor requires 4 material arguments (in order): density, atomic number, atomic mass, material thickness
*   2)The theta/sigma methods require addition particle argument: momentum (MeV)
*/

import java.io.*;

class MCS
{
    // Stating Global variables needed for class
    //Constants
    static private final double Rydberg_constant = 13.6;    //MeV
    static private final double c = 3e8;                    //ms^{-1}
    //Material properties
    private double material_density;                        //gcm^{-3}
    private int    material_Z;
    private double material_A;
    private double material_thickness;                      //cm
    //Particle variables: Taken as constants
    private double particle_charge = 1;                     //Coulombs
    private double particle_mass = 106;                     //MeV
    
    //Constructor
    public MCS(double in_density, int in_Z, double in_A, double in_thickness)
    {
        material_density = in_density;
        material_Z = in_Z;
        material_A = in_A;
        material_thickness = in_thickness;
    }
    
    //Calculates the material radiation length
    public double X_0()
    {
        double X_0 = ((716.4*material_A)/(material_density*material_Z*(material_Z+1)*Math.log(287/Math.sqrt(material_Z))));
        return X_0;
    }
    
    //Calculates the theta_0 angle of scattering
    public double theta_0(double particle_momentum)
    {
        double x_ratio = material_thickness/X_0();
        //Calculates the velocity as speed of light fractions
        double particle_beta = (particle_momentum/(Math.pow(particle_momentum,2)+Math.pow(particle_mass,2)));
        double theta_0 = ((Rydberg_constant*particle_charge*Math.sqrt(x_ratio))*(1+0.038*Math.log(x_ratio))/(particle_beta*particle_momentum));
        return theta_0;
    }

    //Calculates the theta_t angle of scattering
    public double findSigma(double particle_momentum)
    {
        double theta_t = (Math.sqrt(2)*theta_0(particle_momentum));
        return theta_t;
    }
}