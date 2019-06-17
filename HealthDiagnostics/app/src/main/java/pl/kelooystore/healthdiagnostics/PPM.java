package pl.kelooystore.healthdiagnostics;

public abstract class PPM
{
    public static double BenedictaHarrisa(double waga, double wzrost, int wiek, String plec) {
        double PPMwynik = 0;
        if (plec.equals("K")) {
            PPMwynik = 655.1 + (9.563 * waga) + (1.85 * wzrost) - (4.676 * wiek);
        } else {
            PPMwynik = 66.5 + (13.75 * waga) + (5.003 * wzrost) - (6.775 * wiek);
        }
        return PPMwynik;
    }

    public static double Mifflina(double waga, double wzrost, int wiek, String plec){
        double PPMwynik = 0;
        if (plec.equals("K")) {
            PPMwynik = (10*waga)+(6.25*wzrost)-(5*wiek)-161;
        } else {
            PPMwynik = (10*waga)+(6.25*wzrost)-(5*wiek)+5;
        }
        return PPMwynik;
    }
}