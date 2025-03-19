package s2203089.jeudelavie.visiteur;

import java.util.Random;

import s2203089.jeudelavie.JeuDeLaVie;
import s2203089.jeudelavie.cellule.Cellule;
import s2203089.jeudelavie.commande.CommandeMeurt;
import s2203089.jeudelavie.commande.CommandeVit;

/**
 * Represente un visiteur appliquant les règles gel du jeu de la vie.
 *
 * Les règles sont les suivantes : - Une cellule vivante meurt si elle a moins
 * de 2 ou plus de 5 voisins - Une cellule morte renaît si elle a exactement 4
 * voisins - 10% de chance de geler une cellule pour 2 tours
 *
 */
public class VisiteurGel extends Visiteur {

    private static final Random random = new Random();

    public VisiteurGel(JeuDeLaVie jeu) {
        super(jeu);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        if (cellule.estGelee()) {
            cellule.decrementerGel();
            return;
        }

        int nbVoisins = cellule.nombreVoisinesVivantes(jeu);
        if (nbVoisins == 0 || nbVoisins > 5) {
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }

        // 10% de chance de geler une cellule pour 2 tours
        if (random.nextDouble() < 0.1) {
            cellule.geler(2);
        }
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        if (cellule.estGelee()) {
            cellule.decrementerGel();
            return;
        }

        int nbVoisins = cellule.nombreVoisinesVivantes(jeu);
        if (nbVoisins == 4) {
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
