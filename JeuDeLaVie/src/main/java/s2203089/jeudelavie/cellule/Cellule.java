package s2203089.jeudelavie.cellule;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import s2203089.jeudelavie.JeuDeLaVie;
import s2203089.jeudelavie.visiteur.Visiteur;

/**
 * Classe représentant une cellule. utilisée pour appliquer le pattern State
 */
public class Cellule {

    /**
     * La position en x de la cellule sur la matrice.
     */
    private final int posX;

    /**
     * La position en y de la cellule sur la matrice.
     */
    private final int posY;

    /**
     * L'état de la cellule (Singletone).
     */
    private CelluleEtat etat;

    private static final Random random = new Random();

    private int gel = 0;

    private int resistance = 0;
    private int renaissances = 0;

    /**
     * Constructeur de la classe.
     *
     * @param x la position en x de la cellule sur la matrice
     * @param y la position en y de la cellule sur la matrice
     * @param etat l'état de la cellule
     */
    public Cellule(int x, int y, CelluleEtat etat) {
        this.posX = x;
        this.posY = y;
        this.etat = etat;
    }

    /**
     * Fais vivre la cellule.
     */
    public void vit() {
        this.etat = this.etat.vit();
    }

    /**
     * fais mourir la cellule.
     */
    public void meurt() {
        this.etat = this.etat.meurt();
    }

    /**
     * Retourne vrai si la cellule est vivante.
     *
     * @return vrai si la cellule est vivante
     */
    public boolean estVivante() {
        return this.etat.estVivante();
    }

    /**
     * Retourne le nombre de cellules voisines vivantes.
     *
     * @param jeu le jeu de la vie
     * @return le nombre de cellules voisines vivantes
     */
    public int nombreVoisinesVivantes(JeuDeLaVie jeu) {
        int nb = 0;
        for (int i = this.posX - 1; i <= this.posX + 1; i++) {
            for (int j = this.posY - 1; j <= this.posY + 1; j++) {
                if (i >= 0 && i < jeu.getTailleMaxX() && j >= 0 && j < jeu.getTailleMaxY() && !(i == this.posX && j == this.posY)
                        && jeu.getGrillexy(i, j).estVivante()) {
                    nb++;
                }
            }
        }
        return nb;
    }

    /**
     * Accepte un visiteur.
     *
     * @param visiteur le visiteur à accepter
     */
    public void accepte(Visiteur visiteur) {
        this.etat.accepte(visiteur, this);
    }

    public Cellule voisineAleatoire(JeuDeLaVie jeu) {
        List<Cellule> voisines = new ArrayList<>();

        for (int i = this.posX - 1; i <= this.posX + 1; i++) {
            for (int j = this.posY - 1; j <= this.posY + 1; j++) {
                if (i >= 0 && i < jeu.getTailleMaxX() && j >= 0 && j < jeu.getTailleMaxY()
                        && !(i == this.posX && j == this.posY)) {
                    voisines.add(jeu.getGrillexy(i, j));
                }
            }
        }

        return voisines.isEmpty() ? null : voisines.get(random.nextInt(voisines.size()));
    }

    public void augmenterResistance() {
        this.resistance++;
    }

    public boolean estImmortelle(int seuil) {
        return this.resistance >= seuil;
    }

    public void enregistrerRenaissance() {
        this.renaissances++;
    }

    public boolean peutEncoreRenaître(int maxRenaissances) {
        return this.renaissances < maxRenaissances;
    }

    public void geler(int tours) {
        this.gel = tours;
    }

    public boolean estGelee() {
        return this.gel > 0;
    }

    public void decrementerGel() {
        if (this.gel > 0) {
            this.gel--;
        }
    }

}
