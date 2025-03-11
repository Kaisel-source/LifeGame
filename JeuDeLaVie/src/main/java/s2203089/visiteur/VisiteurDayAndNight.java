package s2203089.visiteur;

import s2203089.JeuDeLaVie;
import s2203089.cellule.Cellule;
import s2203089.commande.CommandeMeurt;
import s2203089.commande.CommandeVit;

/**
 * Représente un visiteur pour le jeu de la vie DayAndNight.
 *
 * @see Visiteur
 */
public class VisiteurDayAndNight extends Visiteur {

    /**
     * Constructeur de la classe.
     *
     * @param jeu le jeu
     */
    public VisiteurDayAndNight(JeuDeLaVie jeu) {
        super(jeu);
    }

    /**
     * Permet de visiter une cellule vivante.
     *
     * @param cellule la cellule a visiter
     */
    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinsVivants(jeu);
        if (nbVoisins < 3 || nbVoisins == 5) {
            this.jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    /**
     * Permet de visiter une cellule morte.
     *
     * @param cellule la cellule a visiter
     */
    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        if (cellule.nombreVoisinsVivants(jeu) == 3 || cellule.nombreVoisinsVivants(jeu) > 5) {
            this.jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }

}
