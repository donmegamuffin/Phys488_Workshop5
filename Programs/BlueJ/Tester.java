import java.io.*;

/*
* The class MCS has 4 arguements: Z, A, density, thickness
* Theta/sigma methods require particle momentum argument.
*/
class Tester
{
    public static void main()
    {   long start = System.nanoTime();
        //MCS test
        MCS MCS_Test = new MCS(26,55.845,7.87);
        System.out.println("X_0 is:\t"+ MCS_Test.getX_0());
        System.out.println("theta_0 is:\t" +MCS_Test.getTheta0(1.,1.));
        System.out.println("theta_t is:\t" +MCS_Test.getThetaT(1.,1.));
        //Energyloss test
        EnergyLoss ELoss_Test = new EnergyLoss(26,55.845,7.87);
        System.out.println("Energyloss is:\t"+ELoss_Test.getEnergyLoss(300));
        long end = System.nanoTime();
        System.out.println("Time taken was:\t"+(end-start));
    }
}