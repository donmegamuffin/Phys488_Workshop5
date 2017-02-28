import java.io.*;

class MCS
{
    // Stating Global variables needed for class
    //Constants
    static private final double Rydberg_constant = 13.6; //MeV
    static private final double c = 3e8;
    //Material properties
    private double material_density;     //gcm^{-3}
    private int material_Z;
    private double material_A;
    private double material_thickness;  //cm
    
    //Constructor: suffixes: p=particle, m= material
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
    public double theta_0(double particle_charge ,double particle_beta, double particle_momentum)
    {
        double x_ratio = material_thickness/X_0();
        double theta_0 = ((Rydberg_constant*particle_charge*Math.sqrt(x_ratio))*(1+0.038*Math.log(x_ratio))/(particle_beta*particle_momentum));
        return theta_0;
    }

    //Calculates the theta_t angle of scattering
    public double theta_t(double particle_charge ,double particle_beta, double particle_momentum)
    {
        double theta_t = (Math.sqrt(2)*theta_0(particle_charge,particle_beta,particle_momentum));
        return theta_t;
    }
}