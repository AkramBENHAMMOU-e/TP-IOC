package presentation;

import dao.DaoImplV1;
import extension.DaoImplV2;
import metier.MetierImpl;

public class PresentationV1 {

    public static void main(String[] args) {
        DaoImplV2 d = new DaoImplV2();
        // MetierImpl metier = new MetierImpl();
        //metier.setDao(d);  //injection via setter
        MetierImpl metier = new MetierImpl(d); //injection via constructeur
        System.out.println("Result: "+metier.calcul());

    }
}
