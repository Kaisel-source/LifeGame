package s2203089.jeudelavie.cellule;

import s2203089.jeudelavie.visiteur.Visiteur;

/**
 * Représente l'état "mort" d'une cellule. (Singleton)
 */
public class CelluleEtatMort implements CelluleEtat {

    /**
     * L'instance de la classe.
     */
    private static final CelluleEtatMort instance = new CelluleEtatMort();

    /**
     * constructeur de la classe.
     */
    private CelluleEtatMort() {
    }

    /**
     * Fais vivre la cellule.
     *
     * @return l'état "vivant"
     */
    @Override
    public CelluleEtat vit() {
        return CelluleEtatVivante.getInstance();
    }

    /**
     * Fais mourir la cellule.
     *
     * @return l'état "mort"
     */
    @Override
    public CelluleEtat meurt() {
        return this;
    }

    /**
     * Retourne vrai si la cellule est vivante.
     *
     * @return vrai si la cellule est vivante
     */
    @Override
    public boolean estVivante() {
        return false;
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
        visiteur.visiteCelluleMorte(cellule);
    }
}
