package s2203089.jeudelavie.visiteur;

import s2203089.jeudelavie.JeuDeLaVie;
import s2203089.jeudelavie.cellule.Cellule;
import s2203089.jeudelavie.commande.CommandeMeurt;
import s2203089.jeudelavie.commande.CommandeVit;

/**
 * Represente un visiteur pour le jeu de la vie. Applique les règles Day &
 * Night.
 */
public class VisiteurDayAndNight extends Visiteur {

    /**
     * Constructeur de la classe.
     *
     * @param jeu le jeu de la vie a visiter
     */
    public VisiteurDayAndNight(JeuDeLaVie jeu) {
        super(jeu);
    }

    /**
     * Méthode pour visiter une cellule vivante.
     *
     * @param cellule la cellule a visiter
     */
    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinesVivantes(jeu);
        if (nbVoisins < 3 || nbVoisins == 5) {
            this.jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    /**
     * Méthode pour visiter une cellule morte.
     *
     * @param cellule la cellule a visiter
     */
    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        if (cellule.nombreVoisinesVivantes(jeu) == 3 || cellule.nombreVoisinesVivantes(jeu) > 5) {
            this.jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }

}
