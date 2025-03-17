package s2203089.jeudelavie;

import java.util.ArrayList;
import java.util.List;

import s2203089.jeudelavie.cellule.Cellule;
import s2203089.jeudelavie.cellule.CelluleEtatMort;
import s2203089.jeudelavie.cellule.CelluleEtatVivante;
import s2203089.jeudelavie.commande.Commande;
import s2203089.jeudelavie.observeur.Observable;
import s2203089.jeudelavie.observeur.Observateur;
import s2203089.jeudelavie.visiteur.Visiteur;
import s2203089.jeudelavie.visiteur.VisiteurDayAndNight;

/**
 * Classe représentant le jeu de la vie.
 *
 */
public class JeuDeLaVie implements Observable {

    // ================================== Attributs ==================================
    /**
     * La matrice pour représenter la grille du jeu.
     */
    private Cellule[][] grille;

    /**
     * La taille maximale en x de la grille.
     */
    private int tailleMaxX;

    /**
     * La taille maximale en y de la grille.
     */
    private int tailleMaxY;

    /**
     * La liste des commandes.
     */
    private final List<Commande> commandes;

    /**
     * La liste des observateurs.
     */
    private final List<Observateur> observateurs;

    /**
     * Le visiteur pour les règles du jeu.
     */
    private Visiteur visiteur;

    /**
     * La densité de remplissage de la grille.
     */
    private Double densite = 0.5;

    /**
     * Le nombre de cellules vivantes.
     */
    private int nbCellules;

    /**
     * Le nombre de la génération.
     */
    private int generation;

    /**
     * Le boolean pour savoir si le jeu est en cours.
     */
    private Boolean lancer = false;

    /**
     * Le délai entre chaque génération.
     */
    private int delai;

    /**
     * Constructeur de la classe.
     */
    public JeuDeLaVie() {
        this.tailleMaxX = 1000;
        this.tailleMaxY = 1000;
        this.grille = new Cellule[this.tailleMaxX][this.tailleMaxY];
        this.initialiseGrille();
        //dort pendant 200 ms
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.observateurs = new ArrayList<>();
        this.commandes = new ArrayList<>();
        this.visiteur = new VisiteurDayAndNight(this);

        this.delai = 200;

    }

    // ================================== Getter ==================================
    /**
     * Retourne la taille maximale en x de la grille.
     *
     * @return la taille maximale en x de la grille
     */
    public int getTailleMaxX() {
        return this.tailleMaxX;
    }

    /**
     * Retourne la taille maximale en y de la grille.
     *
     * @return la taille maximale en y de la grille
     */
    public int getTailleMaxY() {
        return this.tailleMaxY;
    }

    /**
     * Retourne l'élément de la grille à la position x et y.
     *
     * @param x La position en x
     * @param y La position en y
     * @return l'élément de la grille à la position x et y
     */
    public Cellule getGrillexy(int x, int y) {
        return this.grille[x][y];
    }

    /**
     * Retourne le délai entre chaque génération.
     *
     * @return le délai entre chaque génération
     */
    public int getDelai() {
        return this.delai;
    }

    /**
     * Retourne le nombre de générations.
     *
     * @return le nombre de générations
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * Retourne la densité de remplissage de la grille.
     *
     * @return la densité de remplissage de la grille
     */
    public Double getdensite() {
        return this.densite;
    }

    /**
     * Retourne le boolean pour savoir si le jeu est en cours.
     *
     * @return le boolean pour savoir si le jeu est en cours
     */
    public Boolean getlancer() {
        return this.lancer;
    }

    // ================================== Setter ==================================
    /**
     * Modifie le délai entre chaque génération.
     *
     * @param delai le délai entre chaque génération
     */
    public void setDelai(int delai) {
        this.delai = delai;
    }

    /**
     * Modifie le boolean pour savoir si le jeu est en cours.
     *
     * @param lancer le boolean pour savoir si le jeu est en cours
     */
    public void setlancer(Boolean lancer) {
        this.lancer = lancer;
    }

    /**
     * Modifie la densité de remplissage de la grille.
     *
     * @param densite la densité de remplissage de la grille
     */
    public void setdensite(Double densite) {
        this.densite = densite;
    }

    // ================================== Méthodes ==================================
    /**
     * Initialise la grille avec des cellules vivantes ou mortes aléatoirement.
     */
    public void initialiseGrille() {
        this.generation = 0;
        for (int i = 0; i < this.tailleMaxX; i++) {
            for (int j = 0; j < this.tailleMaxY; j++) {
                this.grille[i][j] = Math.random() < this.densite
                        ? new Cellule(i, j, CelluleEtatVivante.getInstance())
                        : new Cellule(i, j, CelluleEtatMort.getInstance());
            }
        }
    }

    /**
     * Calcul la génération suivante.
     */
    public void calculerGenerationSuivante() {
        this.distribueVisiteur();
        this.executeCommandes();
        this.notifieObservateur();
        this.generation++;
    }

    /**
     * Compte le nombre de cellules vivantes.
     *
     * @return le nombre de cellules vivantes
     */
    public int compterCellules() {
        this.nbCellules = 0;
        for (int y = 0; y < getTailleMaxY(); y++) {
            for (int x = 0; x < getTailleMaxX(); x++) {
                if (getGrillexy(x, y).estVivante()) {
                    this.nbCellules++;
                }
            }
        }
        return this.nbCellules;
    }

    /**
     * Reinitalise la grille.
     */
    public void reinitialiser() {
        for (int i = 0; i < this.tailleMaxX; i++) {
            for (int j = 0; j < this.tailleMaxY; j++) {
                this.grille[i][j].meurt();
            }
        }
        this.generation = 0;
        this.commandes.clear();
        this.notifieObservateur();
    }

    // ================================== Méthodes de les observables ==================================
    /**
     * Attache un observateur.
     */
    @Override
    public void attacheObservateur(Observateur o) {
        this.observateurs.add(o);
    }

    /**
     * Détache un observateur.
     */
    @Override
    public void detacheObservateur(Observateur o) {
        this.observateurs.remove(o);
    }

    /**
     * Retourne la liste des observateurs.
     */
    @Override
    public List<Observateur> getObservateurs() {
        return this.observateurs;
    }

    /**
     * Notifie les observateurs.
     */
    @Override
    public void notifieObservateur() {
        for (Observateur o : this.observateurs) {
            o.actualise();
        }
    }

    // ================================== Méthodes pour les commandes ==================================
    /**
     * Ajoute une commande.
     */
    public void ajouteCommande(Commande c) {
        if (c != null) {
            this.commandes.add(c);
        }
    }

    /**
     * Exécute les commandes.
     */
    public void executeCommandes() {
        for (Commande c : new ArrayList<>(commandes)) { // Copie temporaire de la liste
            if (c != null) {
                c.executer();
                commandes.remove(c);
            }
        }
    }

    // ================================== Méthodes pour les visiteurs ==================================
    /**
     * Modifie le visiteur.
     *
     * @param vis le visiteur
     */
    public void setVisiteur(Visiteur vis) {
        this.visiteur = vis;
    }

    /**
     * Retourne le visiteur.
     *
     * @return le visiteur
     */
    public Visiteur getVisiteur() {
        return this.visiteur;
    }

    /**
     * Distribue le visiteur aux cellules.
     */
    public void distribueVisiteur() {
        for (int i = 0; i < this.tailleMaxX; i++) {
            for (int j = 0; j < this.tailleMaxY; j++) {
                this.grille[i][j].accepte(this.visiteur);
            }
        }
    }

}
