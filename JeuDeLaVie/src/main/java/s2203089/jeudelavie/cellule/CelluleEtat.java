package s2203089.jeudelavie.cellule;

import s2203089.jeudelavie.visiteur.Visiteur;

/**
 * Interface pour l'état d'une cellule. (Singleton)
 */
interface CelluleEtat {

    /**
     * Fais vivre la cellule.
     *
     * @return l'état "vivant"
     */
    public CelluleEtat vit();

    /**
     * Fais mourir la cellule.
     *
     * @return l'état "mort"
     */
    public CelluleEtat meurt();

    /**
     * Retourne vrai si la cellule est vivante.
     *
     * @return vrai si la cellule est vivante
     */
    public boolean estVivante();

    /**
     * Accepte un visiteur.
     *
     * @param visiteur le visiteur à accepter
     * @param cellule la cellule à visiter
     */
    public void accepte(Visiteur visiteur, Cellule cellule);
}
