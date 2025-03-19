package s2203089.jeudelavie.visiteur;

import s2203089.jeudelavie.JeuDeLaVie;
import s2203089.jeudelavie.cellule.Cellule;
import s2203089.jeudelavie.commande.CommandeMeurt;
import s2203089.jeudelavie.commande.CommandeVit;

/**
 * Represente un visiteur pour le jeu de la vie. Applique les règles High Life.
 *
 * Les règles sont les suivantes : - Une cellule vivante meurt si elle a moins
 * de 2 ou plus de 3 voisins - Une cellule morte renaît si elle a exactement 3
 * voisins ou si elle a 6 voisins
 *
 */
public class VisiteurHighLife extends Visiteur {

    /**
     * Constructeur de la classe.
     *
     * @param jeu le jeu de la vie a visiter
     */
    public VisiteurHighLife(JeuDeLaVie jeu) {
        super(jeu);
    }

    /**
     * Méthode pour visiter une cellule vivante.
     *
     * @param cellule la cellule a visiter
     */
    @Override
    public void visiteCelluleVivante(Cellule cellule) {

        int nbVoisines = cellule.nombreVoisinesVivantes(jeu);

        if (nbVoisines < 2 || nbVoisines > 3) {
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    /**
     * Méthode pour visiter une cellule morte.
     *
     * @param cellule la cellule a visiter
     */
    @Override
    public void visiteCelluleMorte(Cellule cellule) {

        int nbVoisines = cellule.nombreVoisinesVivantes(jeu);

        if (nbVoisines == 3 || nbVoisines == 6) {
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
