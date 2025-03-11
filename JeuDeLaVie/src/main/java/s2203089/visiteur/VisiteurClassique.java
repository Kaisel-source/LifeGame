package s2203089.visiteur;

import s2203089.JeuDeLaVie;
import s2203089.cellule.Cellule;
import s2203089.commande.CommandeMeurt;
import s2203089.commande.CommandeVit;

/**
 * Classe abstraite pour le pattern Visiteur. Configurer pour le jeu de la vie
 * classique.
 *
 * @see Visiteur
 */
public class VisiteurClassique extends Visiteur {

    /**
     * Constructeur de la classe.
     *
     * @param jeu le jeu
     */
    public VisiteurClassique(JeuDeLaVie jeu) {
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
        if (nbVoisins < 2 || nbVoisins > 3) {
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
        if (cellule.nombreVoisinsVivants(jeu) == 3) {
            this.jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }

}
