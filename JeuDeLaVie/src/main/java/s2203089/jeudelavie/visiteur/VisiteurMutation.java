package s2203089.jeudelavie.visiteur;

import java.util.Random;

import s2203089.jeudelavie.JeuDeLaVie;
import s2203089.jeudelavie.cellule.Cellule;
import s2203089.jeudelavie.commande.CommandeMeurt;
import s2203089.jeudelavie.commande.CommandeVit;

/**
 * Represente un visiteur pour le jeu de la vie. Applique les règles de
 * mutation.
 *
 * Les règles sont les suivantes : - Une cellule vivante meurt si elle a moins
 * de 2 ou plus de 4 voisins - Une cellule morte renaît si elle a exactement 3
 * voisins ou si elle a 2 voisins et un peu de chance - Une cellule devient
 * immortelle si elle atteint MAX_RESISTANCE - Une cellule meurt définitivement
 * après MAX_RENAISSANCES renaissances
 *
 */
public class VisiteurMutation extends Visiteur {

    private static final Random random = new Random();
    private static final int MAX_RESISTANCE = 5;
    private static final int MAX_RENAISSANCES = 3;

    public VisiteurMutation(JeuDeLaVie jeu) {
        super(jeu);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinesVivantes(jeu);

        // Augmente la résistance de la cellule
        cellule.augmenterResistance();

        // Cellule devient immortelle si elle atteint MAX_RESISTANCE
        if (cellule.estImmortelle(MAX_RESISTANCE)) {
            return;
        }

        // Meurt si trop de voisins ou pas assez
        if (nbVoisins < 2 || nbVoisins > 4) {
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinesVivantes(jeu);

        // Garde un compteur de résurrections
        cellule.enregistrerRenaissance();

        // Une cellule meurt définitivement après trop de renaissances
        if (!cellule.peutEncoreRenaître(MAX_RENAISSANCES)) {
            return;
        }

        // Revient à la vie selon les règles
        if (nbVoisins == 3 || (random.nextDouble() < 0.1 && nbVoisins == 2)) {
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
