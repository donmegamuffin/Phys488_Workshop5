/*  Author: Zachary Humphreys; ID:200951438
*   Intructions: 
*   2 Sets of inputs are required for all of the code to function.
*   1)The constructor requires 3 material arguments (in order): atomic number, atomic mass, density.
*   2)The theta methods require addition arguments: particle momentum (MeV), and material thickness (cm)
*/
import java.io.*;

class MCS
{
    // Stating Global variables needed for class
    //Constants
    static private final double Rydberg_constant = 13.6;    //MeV
    //Material properties
    private double material_density;                        //gcm^{-3}
    private int    material_Z;
    private double material_A;
    //Particle variables: Taken as constants
    private final double particle_charge = 1;                     //Coulombs
    private final double particle_mass = 106;                     //MeV
    
    //Constructor
    public MCS(int in_Z, double in_A, double in_density)
    {
        material_density = in_density;
        material_Z = in_Z;
        material_A = in_A;
    }
    
    //Calculates the material radiation length
    public double getX_0()
    {
        double X_0 = ((716.4*material_A)/(material_density*material_Z*(material_Z+1)*Math.log(287/Math.sqrt(material_Z))));
        return X_0;
    }
    
    //Calculates the theta_0 angle of scattering
    public double getTheta0(double particle_momentum, double material_thickness)
    {
        double x_ratio = material_thickness/getX_0();
        //Calculates the velocity as speed of light fractions
        double particle_beta = (particle_momentum/Math.sqrt((Math.pow(particle_momentum,2)+Math.pow(particle_mass,2))));
        double theta_0 = ((Rydberg_constant*particle_charge*Math.sqrt(x_ratio))*(1+0.038*Math.log(x_ratio))/(particle_beta*particle_momentum));
        return theta_0;
    }

    //Calculates the theta_t angle of scattering
    public double getThetaT(double particle_momentum, double material_thickness)
    {
        double theta_t = (Math.sqrt(2)*getTheta0(particle_momentum, material_thickness));
        return theta_t;
    }
}