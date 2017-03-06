/* Author: Zachary Humphreys; ID:200951438
*   Intructions: 
*   Please input the momentum energies you require into the array lists p1/p2 that you want to console
*   NOTE: This has only the outputs and properties for IRON baked in (COPPER properties commented out; print code omitted).
*/
import java.io.*;

class Task1
{   //Data values for task question part 1, and part 2
    static double[] p1_array = {30,300,3000,10000,30000,100000};   //stores part1 momenta
    static double[] p2_array = {500,1000,3000};                    //stores part2 momenta
    //Initialise Materials IRON:
    static EnergyLoss ironEloss = new EnergyLoss(26,55.845,7.87);
    static MCS ironMS = new MCS(26,55.845,7.87);
    static final double iron_thickness = 1;                        //cm
    /*COPPER:
    EnergyLoss copperEloss = new EnergyLoss(29,63.546,8.96);
    MCS copperMS = new MCS(29,63.546,8.96);
    */
    public static void main()
    {
        System.out.println("\nThe values for energy loss:");
        //Cycles through until it reaches the end of the array of momenta values
        for(int n=0;n<p1_array.length;n++)
        {   //This prints out the output wanted from part 1 cycling through the array values to console
            System.out.printf("dE/dx = %.2f",ironEloss.getEnergyLoss(p1_array[n])); //For 2 decimal points
            System.out.println(" MeV/cm for p = "+p1_array[n]+" MeV");
        }
        System.out.println("\nThe values for MCS:");
        System.out.printf("Iron X0 = %.3f",ironMS.getX_0());
        System.out.println("cm\nFor a material thickness of 1cm:");
        for(int n=0;n<p2_array.length;n++)
        {   //This prints out the output wanted from part 2 cycling through the array values to console (5 sigfig)
            System.out.printf("thetaT = %.5f",ironMS.getThetaT(p2_array[n],iron_thickness));
            System.out.println(" for p = "+p2_array[n]+" MeV");
        }
    }   
}