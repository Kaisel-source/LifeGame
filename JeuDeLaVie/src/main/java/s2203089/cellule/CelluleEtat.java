package s2203089.cellule;

import s2203089.visiteur.Visiteur;

/*
 * Interface permettant de définir le comportement d'une cellule
 * 
 * @autor MABIRE Aymeric
 */
public interface CelluleEtat {

    /**
     * Permet de faire vivre une cellule
     *
     * @return La cellule vivante
     */
    public CelluleEtat vit();

    /**
     * Permet de tuer une cellule
     *
     * @return La cellule morte
     */
    public CelluleEtat meurt();

    /**
     * Permet de savoir si une cellule est vivante
     *
     * @return true si la cellule est vivante, false sinon
     */
    public boolean estVivante();

    public void accepte(Visiteur visiteur, Cellule cellule);

}
