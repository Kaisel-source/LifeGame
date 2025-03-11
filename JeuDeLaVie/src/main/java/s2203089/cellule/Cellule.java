package s2203089.cellule;

import s2203089.JeuDeLaVie;
import s2203089.visiteur.Visiteur;

public class Cellule {

    /**
     * Etat de la cellule (vivante ou morte)
     */
    private final CelluleEtat etat;

    private final int posX;
    private final int posY;

    /**
     * Constructeur de la cellule
     *
     * @param etat Etat de la cellule
     * @param posX Position en x de la cellule
     * @param posY Position en y de la cellule
     */
    public Cellule(int posX, int posY, CelluleEtat etat) {
        this.etat = etat;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Permet de faire vivre une cellule
     */
    public void vit() { //Une cellule est vivante si elle possède 

    }

    /**
     * Permet de tuer une cellule
     */
    public void meurt() {

    }

    /**
     * Permet de savoir si une cellule est vivante
     *
     * @return true si la cellule est vivante, false sinon
     */
    public boolean estVivante() {
        return etat.estVivante();
    }

    /**
     * Permet de récupérer l'état de la cellule
     *
     * @return L'état de la cellule
     */
    public int posX() {
        return posX;
    }

    /**
     * Permet de récupérer la position en y de la cellule
     *
     * @return La position en y de la cellule
     */
    public int posY() {
        return posY;
    }

    /**
     * Permet de récupérer le nombre de voisins vivants de la cellule
     *
     * @return Le nombre de voisins vivants de la cellule
     */
    public int nombreVoisinsVivants(JeuDeLaVie jeu) {
        int nbVoisinsVivants = 0;
        for (int i = posX - 1; i <= posX + 1; i++) {
            for (int j = posY - 1; j <= posY + 1; j++) {
                if (i >= 0 && i < jeu.getTailleX() && j >= 0 && j < jeu.getTailleY() && !(i == posX && j == posY)) {
                    if (jeu.getGrille(i, j).estVivante()) {
                        nbVoisinsVivants++;
                    }
                }
            }
        }

        return nbVoisinsVivants;
    }

    /**
     * Permet de formater l'affichage terminal de la cellule
     *
     * @return L'affichage terminal de la cellule
     */
    public String toStringAffichTerm() {
        return this.estVivante() ? "O" : ".";
    }

    /**
     * Autorise un visiteur à visiter la cellule
     *
     * @param visiteur le visiteur à autoriiser
     */
    public void accepte(Visiteur visiteur) {
        this.etat.accepte(visiteur, this);
    }

    /**
     * Permet d'afficher la cellule et ses informations
     */
    @Override
    public String toString() {
        return "Cellule{"
                + "posX=" + posX
                + ", posY=" + posY
                + ", etat=" + etat
                + '}';
    }

}
