package s2203089.jeudelavie.visiteur;

import s2203089.jeudelavie.JeuDeLaVie;
import s2203089.jeudelavie.cellule.Cellule;
import s2203089.jeudelavie.commande.CommandeMeurt;
import s2203089.jeudelavie.commande.CommandeVit;

/**
 * Represente un visiteur pour le jeu de la vie. Applique les règles
 * d'évolution.
 *
 * Les règles sont les suivantes : - Une cellule vivante meurt si elle a moins
 * de 2 ou plus de 3 voisins - Une cellule morte renaît si elle a exactement 3
 * voisins ou si elle est entourée de 2 super-cellules - Une cellule morte
 * renaît en super-cellule après 5 générations - Une super-cellule ne meurt pas
 * par sous-population - Une super-cellule augmente la probabilité de
 * renaissance des voisines mortes
 *
 */
public class VisiteurEvolution extends Visiteur {

    public VisiteurEvolution(JeuDeLaVie jeu) {
        super(jeu);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinesVivantes(jeu);
        boolean estSuperCellule = cellule.estSuperCellule();

        // Incrémenter la longévité
        cellule.incrementerSurvie();

        // Transformation en super-cellule après 5 générations
        if (cellule.getGenerationsSurvecues() >= 5 && !estSuperCellule) {
            cellule.devenirSuperCellule();
        }

        // Règle : une super-cellule ne meurt pas par sous-population
        if (estSuperCellule && nbVoisins < 2) {
            return;
        }

        // Mort normale selon les règles du jeu
        if (nbVoisins < 2 || nbVoisins > 3) {
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinesVivantes(jeu);
        int nbSuperCellules = cellule.nombreSuperVoisines(jeu);

        // Une cellule morte avec 2 super-cellules renaît en super-cellule
        if (nbSuperCellules >= 2) {
            cellule.devenirSuperCellule();
            jeu.ajouteCommande(new CommandeVit(cellule));
            return;
        }

        // Une super-cellule augmente la probabilité de renaissance des voisines mortes
        double chanceRenaissance = nbSuperCellules * 0.1; // 10% par super-cellule
        if (nbVoisins == 3 || Math.random() < chanceRenaissance) {
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
