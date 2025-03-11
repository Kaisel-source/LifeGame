package s2203089.cellule;

import s2203089.visiteur.Visiteur;

public class CelluleEtatVivant implements CelluleEtat {

    /**
     * Instance de la cellule vivante Permet de pouvoir utiliser le pattern
     * Singleton
     */
    private static final CelluleEtatVivant instance = new CelluleEtatVivant();

    /**
     * Constructeur de la cellule vivante
     */
    private CelluleEtatVivant() {
    }

    /**
     * Permet de faire vivre une cellule
     */
    @Override
    public CelluleEtat vit() {
        return this;
    }

    /**
     * Permet de tuer une cellule
     */
    @Override
    public CelluleEtat meurt() {
        return CelluleEtatMort.getInstance();
    }

    /**
     * Permet de savoir si une cellule est vivante
     *
     * @return true si la cellule est vivante, false sinon
     */
    @Override
    public boolean estVivante() {
        return true;
    }

    /**
     * Permet de récupérer l'instance de la cellule vivante
     *
     * @return L'instance de la cellule vivante
     */
    public static CelluleEtat getInstance() {
        return instance;
    }

    /**
     * Permet de faire accepter un visiteur à une cellule
     */
    @Override
    public void accepte(Visiteur visiteur, Cellule cellule) {
        visiteur.visiteCelluleVivante(cellule);
    }

    /**
     * Permet de formater l'affichage terminal de létat
     *
     * @return L'affichage formaté
     */
    @Override
    public String toString() {
        return "Vivant";
    }
}
