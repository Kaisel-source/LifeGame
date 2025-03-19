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
        this.nation = assignerNation();
        if (etat == CelluleEtatVivante.getInstance()) {
            populationNations[this.nation]++;
        }
        if (Math.random() < 0.07) {
            this.infectee = true;
        }

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
    private int generationsSurvecues = 0;
    private boolean superCellule = false;

    public void incrementerSurvie() {
        if (!superCellule) {
            generationsSurvecues++;
        }
    }

    public int getGenerationsSurvecues() {
        return generationsSurvecues;
    }

    public void devenirSuperCellule() {
        this.superCellule = true;
    }

    public boolean estSuperCellule() {
        return this.superCellule;
    }

// Compter le nombre de super-cellules voisines
    public int nombreSuperVoisines(JeuDeLaVie jeu) {
        int count = 0;
        for (int i = this.posX - 1; i <= this.posX + 1; i++) {
            for (int j = this.posY - 1; j <= this.posY + 1; j++) {
                if (i >= 0 && i < jeu.getTailleMaxX() && j >= 0 && j < jeu.getTailleMaxY()
                        && !(i == this.posX && j == this.posY)) {
                    Cellule voisine = jeu.getGrillexy(i, j);
                    if (voisine.estSuperCellule()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private static final int NOMBRE_NATIONS = 5;
    private static int[] populationNations = new int[NOMBRE_NATIONS];

    private int nation;
    private boolean fortifiee = false;

// Initialisation d'une nation aléatoire au début
// Assigne une nation en fonction des populations existantes
    private int assignerNation() {
        int nationMin = 0;
        for (int i = 1; i < NOMBRE_NATIONS; i++) {
            if (populationNations[i] < populationNations[nationMin]) {
                nationMin = i;
            }
        }
        return nationMin;
    }

// Change la nation d'une cellule
    public void changerNation(int nouvelleNation) {
        if (this.nation != nouvelleNation) {
            populationNations[this.nation]--;
            this.nation = nouvelleNation;
            populationNations[nouvelleNation]++;
        }
    }

// Retourne la nation actuelle
    public int getNation() {
        return this.nation;
    }

// Vérifie si une nation a disparu
    public static boolean nationExiste(int nation) {
        return populationNations[nation] > 0;
    }

// Sélectionne une nation alliée existante
    public static int nationAllieExistante() {
        for (int i = 0; i < NOMBRE_NATIONS; i++) {
            if (populationNations[i] > 0) {
                return i;
            }
        }
        return 0; // Par défaut, la nation 0 prend le relais si toutes disparaissent (ne devrait pas arriver)
    }

    public int nombreVoisinesAlliees(JeuDeLaVie jeu) {
        int count = 0;
        for (int i = this.posX - 1; i <= this.posX + 1; i++) {
            for (int j = this.posY - 1; j <= this.posY + 1; j++) {
                if (i >= 0 && i < jeu.getTailleMaxX() && j >= 0 && j < jeu.getTailleMaxY()) {
                    Cellule voisine = jeu.getGrillexy(i, j);
                    if (voisine.getNation() == this.nation) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

// Compter les voisines ennemies
    public int nombreVoisinesEnnemies(JeuDeLaVie jeu) {
        int count = 0;
        for (int i = this.posX - 1; i <= this.posX + 1; i++) {
            for (int j = this.posY - 1; j <= this.posY + 1; j++) {
                if (i >= 0 && i < jeu.getTailleMaxX() && j >= 0 && j < jeu.getTailleMaxY()) {
                    Cellule voisine = jeu.getGrillexy(i, j);
                    if (voisine.getNation() != this.nation) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

// Sélectionne une cellule ennemie au hasard
    public Cellule voisineEnnemieAleatoire(JeuDeLaVie jeu) {
        List<Cellule> ennemies = new ArrayList<>();
        for (int i = this.posX - 1; i <= this.posX + 1; i++) {
            for (int j = this.posY - 1; j <= this.posY + 1; j++) {
                if (i >= 0 && i < jeu.getTailleMaxX() && j >= 0 && j < jeu.getTailleMaxY()) {
                    Cellule voisine = jeu.getGrillexy(i, j);
                    if (voisine.getNation() != this.nation) {
                        ennemies.add(voisine);
                    }
                }
            }
        }
        return ennemies.isEmpty() ? null : ennemies.get(new Random().nextInt(ennemies.size()));
    }

// Sélectionne une cellule alliée au hasard
    public Cellule voisineAllieAleatoire(JeuDeLaVie jeu) {
        List<Cellule> alliees = new ArrayList<>();
        for (int i = this.posX - 1; i <= this.posX + 1; i++) {
            for (int j = this.posY - 1; j <= this.posY + 1; j++) {
                if (i >= 0 && i < jeu.getTailleMaxX() && j >= 0 && j < jeu.getTailleMaxY()) {
                    Cellule voisine = jeu.getGrillexy(i, j);
                    if (voisine.getNation() == this.nation) {
                        alliees.add(voisine);
                    }
                }
            }
        }
        return alliees.isEmpty() ? null : alliees.get(new Random().nextInt(alliees.size()));
    }

// Fortifie la cellule (elle devient plus résistante)
    public void fortifier() {
        this.fortifiee = true;
    }

// Vérifie si une cellule est fortifiée
    public boolean estFortifiee() {
        return this.fortifiee;
    }

    private boolean infectee = false;
    private boolean immune = false;
    private boolean critique = false;
    private boolean resistante = false;
    private int tempsInfection = 0;
    private int mutationPropagation = 0; // Augmente avec le temps, impacte la vitesse de propagation

// Infecte une cellule si elle n'est ni immunisée ni résistante
    public void infecter() {
        if (!immune && !resistante) {
            this.infectee = true;
            this.tempsInfection = 0;
        }
    }

// Vérifie si la cellule est infectée
    public boolean estInfectee() {
        return this.infectee;
    }

// Vérifie si la cellule est immunisée
    public boolean estImmune() {
        return this.immune;
    }

// Vérifie si la cellule est en état critique (risque élevé de mourir)
    public boolean estCritique() {
        return this.critique;
    }

// Définit si une cellule est en état critique
    public void setCritique(boolean critique) {
        this.critique = critique;
    }

// Vérifie si la cellule est résistante (elle ne peut pas être infectée)
    public boolean estResistante() {
        return this.resistante;
    }

// Augmente la durée d'infection et gère l'évolution de la maladie
    public void progressionMaladie(int seuilGuerison, int seuilCritique) {
        if (infectee) {
            this.tempsInfection++;
            this.mutationPropagation++;

            // Devient critique si l'infection dure trop longtemps
            if (this.tempsInfection >= seuilCritique) {
                this.critique = true;
            }

            // Guérison et immunisation après un certain seuil
            if (this.tempsInfection >= seuilGuerison) {
                this.infectee = false;
                this.immune = true;
                this.critique = false;
            }
        }
    }

// Nombre de tours où la cellule est infectée
    public int getToursInfectee() {
        return this.tempsInfection;
    }

// Renvoie la mutation de propagation (plus elle est élevée, plus la cellule infecte vite)
    public int getMutationPropagation() {
        return this.mutationPropagation;
    }

// Augmente l'infection manuellement (par exemple, via une mutation ou un effet spécial)
    public void incrementerInfection() {
        this.tempsInfection++;
    }

    public int nombreVoisinesInfectees(JeuDeLaVie jeu) {
        int nb = 0;
        for (int i = this.posX - 1; i <= this.posX + 1; i++) {
            for (int j = this.posY - 1; j <= this.posY + 1; j++) {
                if (i >= 0 && i < jeu.getTailleMaxX() && j >= 0 && j < jeu.getTailleMaxY()
                        && !(i == this.posX && j == this.posY)
                        && jeu.getGrillexy(i, j).estInfectee()) {
                    nb++;
                }
            }
        }
        return nb;
    }

}
