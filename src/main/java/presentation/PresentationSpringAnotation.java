package presentation;

import metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PresentationSpringAnotation {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("extension", "metier");
        IMetier metier = (IMetier) context.getBean(IMetier.class);
        System.out.println("Result: " + metier.calcul());
    }
}
