package s2203089;

import java.util.ArrayList;
import java.util.List;

import s2203089.cellule.Cellule;
import s2203089.cellule.CelluleEtatMort;
import s2203089.cellule.CelluleEtatVivant;
import s2203089.commande.Commande;
import s2203089.observateur.Observable;
import s2203089.observateur.Observateur;
import s2203089.visiteur.Visiteur;
import s2203089.visiteur.VisiteurDayAndNight;

/**
 * Classe représentant le jeu de la vie
 */
public class JeuDeLaVie implements Observable {

    /**
     * Grille du jeu de la vie
     */
    private final Cellule[][] grille;

    /**
     * Taille en x de la grille
     */
    private final int tailleX;

    /**
     * Taille en y de la grille
     */
    private final int tailleY;

    /**
     * Liste des observateur.
     */
    private final List<Observateur> observateurs;

    private final List<Commande> commandes;

    private Visiteur visiteur;
    /**
     * Densité de la grille.
     */
    private Double density = 0.5;

    /**
     * Génération.
     */
    private int generation;

    /**
     * Paramètre de l'execution.
     */
    private Boolean run = false;
    /**
     * Délai de l'execution.
     */
    private int delai;

    /**
     * Constructeur du jeu de la vie
     *
     * @param tailleX Taille en x de la grille
     * @param tailleY Taille en y de la grille
     */
    public JeuDeLaVie(int tailleX, int tailleY) {
        this.tailleX = tailleX;
        this.tailleY = tailleY;
        grille = new Cellule[tailleX][tailleY];
        observateurs = new ArrayList<>();
        commandes = new ArrayList<>();
        visiteur = new VisiteurDayAndNight(this);
        generation = 0;
        delai = 200;
        init();
    }

    /**
     * Permet de récupérer la grille
     *
     * @return La grille
     */
    public Cellule getGrille(int x, int y) {
        return grille[x][y];
    }

    /**
     * Permet de récupérer la taille en x de la grille
     *
     * @return La taille en x de la grille
     */
    public int getTailleX() {
        return tailleX;
    }

    /**
     * Permet de récupérer la taille en y de la grille
     *
     * @return La taille en y de la grille
     */
    public int getTailleY() {
        return tailleY;
    }

    /**
     * Initialisation de la grille
     */
    public void init() {
        for (int i = 0; i < tailleX; i++) {
            for (int j = 0; j < tailleY; j++) {
                grille[i][j] = new Cellule(i, j, Math.random() < density ? CelluleEtatVivant.getInstance() : CelluleEtatMort.getInstance());
            }
        }
    }

    /**
     * Permet de faire l'affichage terminal du jeu de la vie
     */
    public void afficheTerm() {
        System.out.print("  ");
        for (int i = 0; i < tailleX; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < tailleX; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < tailleY; j++) {
                System.out.print(grille[i][j].toStringAffichTerm() + " ");
            }
            System.out.println();
        }
    }

    /**
     * Cette méthode ajoute une commande à la liste des commandes.
     *
     * @param c la commande à ajouter
     */
    public void ajouteCommande(Commande commande) {
        commandes.add(commande);
    }

    /**
     * Cette méthode exécute toutes les commandes de la liste des commandes.
     */
    public void executeCommandes() {
        for (Commande commande : commandes) {
            commande.executer();
        }

        commandes.clear();
    }

    /**
     * Méthode pour changer le visiteur.
     *
     * @param visit le visiteur à changer
     */
    public void setVisiteur(Visiteur visit) {
        visiteur = visit;
    }

    /**
     * Getter du visiteur.
     *
     * @return le visiteur
     */
    public Visiteur getVisiteur() {
        return visiteur;
    }

    /**
     * Distribue le visiteur sur la grille.
     */
    public void distribueVisiteur() {
        for (int i = 0; i < tailleX; i++) {
            for (int j = 0; j < tailleY; j++) {
                //System.out.println(grille[i][j]);
                grille[i][j].accepte(visiteur);
            }
        }
    }

    /**
     * Calcul la génération suivante du jeu de la vie.
     */
    public void calculerGenerationSuivante() {
        distribueVisiteur();
        executeCommandes();
        notifieObservateur();
        generation++;
    }

    /**
     * Récupère le délai de l'execution en ms.
     *
     * @return le délai d'éxécution
     */
    public int getDelai() {
        return delai;
    }

    /**
     * Permet de changer le délai de l'execution en ms.
     *
     * @param delai le nouveau délai
     */
    public void setDelai(int delai) {
        this.delai = delai;
    }

    /**
     * Récupère le numéro de la génération actuelle.
     *
     * @return le numéro de la génération actuelle
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * Récupère l'état de l'execution
     *
     * @return l'état de l'execution (true si en cours, false sinon)
     */
    public Boolean getRun() {
        return run;
    }

    /**
     * Permet de changer l'état de l'execution
     *
     * @param run le nouvel état de l'execution (true si en cours, false sinon)
     */
    public void setRun(Boolean run) {
        this.run = run;
    }

    /**
     * Permet de définir la densité de la grille.
     *
     * @param density la nouvelle densité
     */
    public void setDensity(Double density) {
        this.density = density;
    }

    /**
     * Permet de récupéré la densité.
     *
     * @return la densité
     */
    public Double getDensity() {
        return density;
    }

    /**
     * Réinitialise la grille.
     *
     */
    public void reinitialiser() {
        for (int i = 0; i < tailleX; i++) {
            for (int j = 0; j < tailleY; j++) {
                grille[i][j].meurt();
            }
        }
        generation = 0;
        commandes.clear();
        notifieObservateur();
    }

    /**
     * Attache l'observateur.
     *
     * @param o l'observateur à attacher
     */
    @Override
    public void attacheObservateur(Observateur o) {
        observateurs.add(o);
    }

    /**
     * Détache l'observateur.
     *
     * @param o l'observateur à détacher
     */
    @Override
    public void detacheObservateur(Observateur o) {
        observateurs.remove(o);
    }

    /**
     * Renvoie la liste des observateurs.
     *
     * @return la liste des observateurs
     */
    @Override
    public List<Observateur> getObservateurs() {
        return observateurs;
    }

    /**
     * Notifie les observateurs d'une mise à jour.
     */
    @Override
    public void notifieObservateur() {
        for (Observateur o : observateurs) {
            o.actualise();
        }
    }

    /**
     * Permet d'afficher la grille du jeu de la vie avec le détail des cellules
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("JeuDeLaVie{");
        sb.append("tailleX=").append(tailleX);
        sb.append(", tailleY=").append(tailleY);
        sb.append("}\n");
        for (int i = 0; i < tailleX; i++) {
            for (int j = 0; j < tailleY; j++) {
                sb.append(grille[i][j].toString());
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
