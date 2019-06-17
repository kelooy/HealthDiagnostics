package pl.kelooystore.healthdiagnostics;

public abstract class BMI {

    public static double bodyMassIndex(double waga, double wzrost){
        return waga / (wzrost * wzrost);
    }

}
