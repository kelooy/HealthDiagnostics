package pl.kelooystore.healthdiagnostics;
//https://dietetykpro.pl/kalkulatory/ymca/

public abstract class FAT {

    public static double bodyFat(double waga, double talia, String plec)
    {
        double FATwynik=0;
        if (plec.equals("K")) {
            FATwynik = 100 * (1.634 * talia - 0.1804 * waga - 76.76) / (2.2 * waga);
        } else {
            FATwynik = 100 * (1.634 * talia - 0.1804 * waga - 98.42) / (2.2 * waga);
        }
        return FATwynik;
    }
}
