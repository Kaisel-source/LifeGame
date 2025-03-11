package s2203089.cellule;

import s2203089.visiteur.Visiteur;

/*
 * 
 * 
 * @autor MABIRE Aymeric
 */
public class CelluleEtatMort implements CelluleEtat {

    /**
     * Instance de la cellule morte Permet de pouvoir utiliser le pattern
     * Singleton
     */
    private static final CelluleEtatMort instance = new CelluleEtatMort();

    /**
     * Constructeur de la cellule morte
     */
    private CelluleEtatMort() {
    }

    /**
     * Permet de faire vivre une cellule
     */
    @Override
    public CelluleEtat vit() {
        return CelluleEtatVivant.getInstance();
    }

    /**
     * Permet de tuer une cellule
     */
    @Override
    public CelluleEtat meurt() {
        return this;
    }

    /**
     * Permet de savoir si une cellule est vivante
     *
     * @return true si la cellule est vivante, false sinon
     */
    @Override
    public boolean estVivante() {
        return false;
    }

    /**
     * Permet de récupérer l'instance de la cellule morte
     *
     * @return L'instance de la cellule morte
     */
    public static CelluleEtat getInstance() {
        return instance;
    }

    /**
     * Permet de faire accepter un visiteur
     */
    @Override
    public void accepte(Visiteur visiteur, Cellule cellule) {
        visiteur.visiteCelluleMorte(cellule);
    }

    /**
     * Permet de formater l'affichage terminal de létat
     *
     * @return L'affichage formaté
     */
    @Override
    public String toString() {
        return "Mort";
    }
}
