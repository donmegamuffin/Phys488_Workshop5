import java.io.*;

class MCS
{
    // Stating Global variables needed for class
    static private final double Rydberg_constant = 13.6; //MeV
    static private final double c = 3e8;
    private double particle_beta;        
    private double particle_momentum; //MeV
    private double particle_charge;      //C
    private double material_density;     //gcm^{-3}
    private int material_Z;
    private double material_A;
    private double material_thickness;  //cm
    
    //Constructor: suffixes: p=particle, m= material
    public MCS(double inp_beta, double inp_momentum, double inp_charge, double inm_density, int inm_Z, double inm_A, double inm_thickness)
    {
        particle_beta = inp_beta;
        particle_momentum = inp_momentum;
        particle_charge = inp_charge;
        material_density = inm_density;
        material_Z = inm_Z;
        material_A = inm_A;
        material_thickness = inm_thickness;
    }
    
    //Calculates the material radiation length
    public double X_0()
    {
        double X_0 = ((716.4*material_A)/(material_density*material_Z*(material_Z+1)*Math.log(287/Math.sqrt(material_Z))));
        return X_0;
    }
    
    //Calculates the theta_0 angle of scattering
    public double theta_0()
    {
        double x_ratio = material_thickness/X_0();
        double theta_0 = ((Rydberg_constant*particle_charge*Math.sqrt(x_ratio))*(1+0.038*Math.log(x_ratio))/(particle_beta*particle_momentum));
        return theta_0;
    }

}