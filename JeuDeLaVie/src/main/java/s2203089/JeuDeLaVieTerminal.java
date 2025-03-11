package s2203089;

import s2203089.observateur.Observateur;

/**
 * Classe représentant le jeu de la vie dans le terminal. utilisée pour afficher
 * les informations du jeu de la vie dans le terminal Implémente l'interface
 * Observateur
 *
 * @see Observateur
 */
public class JeuDeLaVieTerminal implements Observateur {

    /**
     * Instance du jeu de la vie.
     */
    private final JeuDeLaVie jeu;
    /**
     * Nombre de cellules vivantes.
     */
    private int nbCellules;
    /**
     * Booléen d'affichage.
     */
    private boolean affichage;

    /**
     * Constructeur de la classe. attache l'observateur à l'instance du jeu de
     * la vie
     *
     * @param jeu l'instance du jeu de la vie
     */
    public JeuDeLaVieTerminal(JeuDeLaVie jeu) {
        this.jeu = jeu;
        this.nbCellules = 0;
        this.jeu.attacheObservateur(this);
        this.affichage = false;
    }

    /**
     * Getter de la génération.
     *
     * @return la génération
     */
    public int getGeneration() {
        return jeu.getGeneration();
    }

    /**
     * Setter de l'affichage.
     *
     * @param affichage le booléen d'affichage
     */
    public void setAffichage(boolean affichage) {
        this.affichage = affichage;
    }

    /**
     * Getter de l'affichage.
     *
     * @return le booléen d'affichage
     */
    public boolean getAffichage() {
        return affichage;
    }

    /**
     * Getter du nombre de cellules.
     *
     * @return le nombre de cellules
     */
    public int getNbCellules() {
        return nbCellules;
    }

    /**
     * Actualise le nombre de cellules.
     */
    public void compterCellules() {
        this.nbCellules = 0;
        for (int y = 0; y < jeu.getTailleY(); y++) {
            for (int x = 0; x < jeu.getTailleX(); x++) {
                if (jeu.getGrille(x, y).estVivante()) {
                    this.nbCellules++;
                    if (affichage) {
                        System.out.print("O ");
                    }
                } else {
                    if (affichage) {
                        System.out.print(" ");
                    }
                }
            }
        }
    }

    public JeuDeLaVie getJeu() {
        return jeu;
    }

    /**
     * Actualise la génération et le nombre de cellule.
     */
    @Override
    public void actualise() {
        compterCellules();
        if (affichage) {
            afficherInfos();
        }
    }

    /**
     * Affiche les informations dans le terminal.
     */
    public void afficherInfos() {
        System.out.println("Génération " + this.getGeneration());
        System.out.println("Nombre de cellules vivantes : " + nbCellules);
        System.out.println("---------------------------------------");
    }

}
