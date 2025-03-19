package s2203089.jeudelavie.visiteur;

import s2203089.jeudelavie.JeuDeLaVie;
import s2203089.jeudelavie.cellule.Cellule;
import s2203089.jeudelavie.commande.CommandeVit;
import s2203089.jeudelavie.commande.CommandeMeurt;

import java.util.Random;

public class VisiteurPandemie extends Visiteur {

    private static final Random random = new Random();

    public VisiteurPandemie(JeuDeLaVie jeu) {
        super(jeu);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        if (cellule.estResistante()) {
            return; // Immunisée, rien ne change
        }
        if (cellule.estInfectee()) {
            cellule.incrementerInfection();

            if (cellule.getToursInfectee() >= 3) {
                cellule.setCritique(true); // Devient critique (prête à mourir)
            }
        }

        int nbVoisinsInfectes = cellule.nombreVoisinesInfectees(jeu);
        if (!cellule.estInfectee() && nbVoisinsInfectes > 0) {
            if (random.nextDouble() < 0.3 + cellule.getMutationPropagation()) {
                cellule.infecter(); // Infection avec probabilité de mutation
            }
        }

        if (cellule.estCritique()) {
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        if (cellule.estInfectee() && random.nextDouble() < 0.05) {
            jeu.ajouteCommande(new CommandeVit(cellule)); // Chance de réanimation en zombie
        }
    }
}
