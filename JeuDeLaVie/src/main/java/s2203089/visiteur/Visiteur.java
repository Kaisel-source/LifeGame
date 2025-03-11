package s2203089.visiteur;

import s2203089.JeuDeLaVie;
import s2203089.cellule.Cellule;

/**
 * Classe abstraite pour le pattern Visiteur.
 */
public abstract class Visiteur {

    /**
     * Le jeu
     */
    protected JeuDeLaVie jeu;

    /**
     * Constructeur de la classe.
     *
     * @param jeu le jeu
     */
    public Visiteur(JeuDeLaVie jeu) {
        this.jeu = jeu;
    }

    /**
     * Permet de visiter une cellule vivante.
     *
     * @param cellule la cellule a visiter
     */
    public abstract void visiteCelluleVivante(Cellule cellule);

    /**
     * Permet de visiter une cellule morte.
     *
     * @param cellule la cellule a visiter
     */
    public abstract void visiteCelluleMorte(Cellule cellule);
}
