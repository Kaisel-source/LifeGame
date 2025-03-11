package s2203089.visiteur;

import s2203089.JeuDeLaVie;
import s2203089.cellule.Cellule;
import s2203089.commande.CommandeMeurt;
import s2203089.commande.CommandeVit;

/**
 * Classe représentant un visiteur pour le jeu de la vie HighLife. utilisée pour
 * appliquer le pattern Visiteur
 *
 * @see Visiteur
 */
public class VisiteurHighLife extends Visiteur {

    /**
     * Constructeur de la classe.
     *
     * @param jeu le jeu
     */
    public VisiteurHighLife(JeuDeLaVie jeu) {
        super(jeu);
    }

    /**
     * Permet de visiter une cellule vivante.
     *
     * @param cellule la cellule a visiter
     */
    @Override
    public void visiteCelluleVivante(Cellule cellule) {

        int nbVoisines = cellule.nombreVoisinsVivants(jeu);

        if (nbVoisines < 2 || nbVoisines > 3) {
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    /**
     * Permet de visiter une cellule morte.
     *
     * @param cellule la cellule a visiter
     */
    @Override
    public void visiteCelluleMorte(Cellule cellule) {

        int nbVoisines = cellule.nombreVoisinsVivants(jeu);

        if (nbVoisines == 3 || nbVoisines == 6) {
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
