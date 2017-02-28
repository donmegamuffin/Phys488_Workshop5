import java.io.*;

/*
* The class MCS has 4 arguements: 
* [1-4]: [Material: double density, int Z, int A, double thickness]
* The MCS submethods theta_0() and theta_t() require input of paricle variables : 
* [1-3]: [Particle: double charge, double beta, double momentum]
*/
class Tester
{
    public static void main()
    {
        MCS MCS_Test = new MCS(7.87,26,55.845,1.);
        System.out.println("X_0 is:\t"+ MCS_Test.X_0());
        System.out.println("theta_0 is:\t" +MCS_Test.theta_0(1.));
        System.out.println("theta_t is:\t" +MCS_Test.findSigma(1.));
    }
}