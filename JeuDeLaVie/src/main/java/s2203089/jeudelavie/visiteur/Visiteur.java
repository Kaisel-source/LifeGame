package s2203089.jeudelavie.visiteur;

import s2203089.jeudelavie.JeuDeLaVie;
import s2203089.jeudelavie.cellule.Cellule;

/**
 * Represente un visiteur pour le jeu de la vie. Le visiteur permet de définir
 * les règles du jeu de la vie.
 */
public abstract class Visiteur {

    /**
     * Le jeu de la vie.
     */
    protected JeuDeLaVie jeu;

    /**
     * Constructeur de la classe.
     *
     * @param jeu le jeu de la vie a visiter
     */
    public Visiteur(JeuDeLaVie jeu) {
        this.jeu = jeu;
    }

    /**
     * Méthode pour visiter une cellule vivante.
     *
     * @param cellule la cellule a visiter
     */
    public abstract void visiteCelluleVivante(Cellule cellule);

    /**
     * Méthode pour visiter une cellule morte.
     *
     * @param cellule la cellule a visiter
     */
    public abstract void visiteCelluleMorte(Cellule cellule);
}
