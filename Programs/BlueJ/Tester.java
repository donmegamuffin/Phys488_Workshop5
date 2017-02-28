import java.io.*;

/*
* The class MCS has 7 arguements: 
* [1-3: Particle: beta, momentum, charge] - all doubles
* [4-7]: Material: double density, int Z, int A, double thickness]
*/
class Tester
{
    public static void main()
    {
        MCS MCS_Test = new MCS(1.,1.,1.,7.87,26,55.845,1.);
        System.out.println("X_0 is:\t"+ MCS_Test.X_0());
        System.out.println("theta_0 is:\t" +MCS_Test.theta_0());
    }
}