package s2203089.jeudelavie.cellule;

import s2203089.jeudelavie.visiteur.Visiteur;

/**
 * Représente l'état "vivant" d'une cellule. (Singleton)
 */
public class CelluleEtatVivante implements CelluleEtat {

    /**
     * L'instance de la classe.
     */
    private static final CelluleEtatVivante instance = new CelluleEtatVivante();

    /**
     * constructeur de la classe.
     */
    private CelluleEtatVivante() {
    }

    /**
     * Fais vivre la cellule.
     *
     * @return l'état "vivant"
     */
    @Override
    public CelluleEtat vit() {
        return this;
    }

    /**
     * Fais mourir la cellule.
     *
     * @return l'état "mort"
     */
    @Override
    public CelluleEtat meurt() {
        return CelluleEtatMort.getInstance();
    }

    /**
     * Retourne vrai si la cellule est vivante.
     *
     * @return vrai si la cellule est vivante
     */
    @Override
    public boolean estVivante() {
        return true;
    }

    /**
     * Retourne l'instance de la classe.
     *
     * @return l'instance de la classe
     */
    public static CelluleEtat getInstance() {
        return instance;
    }

    /**
     * Accepte un visiteur.
     *
     * @param visiteur le visiteur à accepter
     * @param cellule la cellule à visiter
     */
    @Override
    public void accepte(Visiteur visiteur, Cellule cellule) {
        visiteur.visiteCelluleVivante(cellule);
    }
}
