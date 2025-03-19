package s2203089.jeudelavie.cellule;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import s2203089.jeudelavie.JeuDeLaVie;
import s2203089.jeudelavie.visiteur.Visiteur;

/**
 * Classe représentant une cellule du Jeu de la Vie, appliquant le pattern
 * State.
 */
public class Cellule {

    private final int posX, posY;
    private CelluleEtat etat;
    private static final Random random = new Random();

    // Modes spéciaux
    private int gel = 0;
    private int resistance = 0, renaissances = 0;
    private int generationsSurvecues = 0;
    private boolean superCellule = false;
    private boolean fortifiee = false;

    // Mode Civilisation
    private static final int NOMBRE_NATIONS = 5;
    private static int[] populationNations = new int[NOMBRE_NATIONS];
    private int nation;

    // Mode Epidémie
    private boolean infectee = false, immune = false, critique = false, resistante = false;
    private int tempsInfection = 0, mutationPropagation = 0;

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

    public void vit() {
        this.etat = this.etat.vit();
    }

    public void meurt() {
        this.etat = this.etat.meurt();
    }

    public boolean estVivante() {
        return this.etat.estVivante();
    }

    public void accepte(Visiteur visiteur) {
        this.etat.accepte(visiteur, this);
    }

    public int nombreVoisinesVivantes(JeuDeLaVie jeu) {
        return compterVoisines(jeu, Cellule::estVivante);
    }

    public Cellule voisineAleatoire(JeuDeLaVie jeu) {
        return celluleAleatoire(jeu, c -> true);
    }

    // ==================== Mode Mutation ====================
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

    // ==================== Mode Gel ====================
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

    // ==================== Mode Evolution ====================
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

    public int nombreSuperVoisines(JeuDeLaVie jeu) {
        return compterVoisines(jeu, Cellule::estSuperCellule);
    }

    // ==================== Mode Civilisation ====================
    private int assignerNation() {
        int nationMin = 0;
        for (int i = 1; i < NOMBRE_NATIONS; i++) {
            if (populationNations[i] < populationNations[nationMin]) {
                nationMin = i;
            }
        }
        return nationMin;
    }

    public void changerNation(int nouvelleNation) {
        if (this.nation != nouvelleNation) {
            populationNations[this.nation]--;
            this.nation = nouvelleNation;
            populationNations[nouvelleNation]++;
        }
    }

    public int getNation() {
        return this.nation;
    }

    public int nombreVoisinesAlliees(JeuDeLaVie jeu) {
        return compterVoisines(jeu, c -> c.getNation() == this.nation);
    }

    public int nombreVoisinesEnnemies(JeuDeLaVie jeu) {
        return compterVoisines(jeu, c -> c.getNation() != this.nation);
    }

    public void fortifier() {
        this.fortifiee = true;
    }

    public boolean estFortifiee() {
        return this.fortifiee;
    }

    public Cellule voisineAllieAleatoire(JeuDeLaVie jeu) {
        return celluleAleatoire(jeu, c -> c.getNation() == this.nation);
    }

    public Cellule voisineEnnemieAleatoire(JeuDeLaVie jeu) {
        return celluleAleatoire(jeu, c -> c.getNation() != this.nation);
    }

    // ==================== Mode Epidémie ====================
    public void infecter() {
        if (!immune && !resistante) {
            this.infectee = true;
            this.tempsInfection = 0;
        }
    }

    public boolean estInfectee() {
        return this.infectee;
    }

    public boolean estImmune() {
        return this.immune;
    }

    public boolean estCritique() {
        return this.critique;
    }

    public void setCritique(boolean critique) {
        this.critique = critique;
    }

    public boolean estResistante() {
        return this.resistante;
    }

    public void progressionMaladie(int seuilGuerison, int seuilCritique) {
        if (infectee) {
            tempsInfection++;
            mutationPropagation++;
            if (tempsInfection >= seuilCritique) {
                critique = true;
            }
            if (tempsInfection >= seuilGuerison) {
                infectee = false;
                immune = true;
                critique = false;
            }
        }
    }

    public int nombreVoisinesInfectees(JeuDeLaVie jeu) {
        return compterVoisines(jeu, Cellule::estInfectee);
    }

    public void incrementerInfection() {
        this.tempsInfection++;
    }

    public void augmenterMutationPropagation() {
        this.mutationPropagation++;
    }

    public int getMutationPropagation() {
        return this.mutationPropagation;
    }

    public int getToursInfectee() {
        return this.tempsInfection;
    }

    // ==================== Méthodes Utilitaires ====================
    private int compterVoisines(JeuDeLaVie jeu, java.util.function.Predicate<Cellule> condition) {
        int count = 0;
        for (int i = posX - 1; i <= posX + 1; i++) {
            for (int j = posY - 1; j <= posY + 1; j++) {
                if (i >= 0 && i < jeu.getTailleMaxX() && j >= 0 && j < jeu.getTailleMaxY() && !(i == posX && j == posY)) {
                    Cellule voisine = jeu.getGrillexy(i, j);
                    if (condition.test(voisine)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private Cellule celluleAleatoire(JeuDeLaVie jeu, java.util.function.Predicate<Cellule> condition) {
        List<Cellule> voisines = new ArrayList<>();
        for (int i = posX - 1; i <= posX + 1; i++) {
            for (int j = posY - 1; j <= posY + 1; j++) {
                if (i >= 0 && i < jeu.getTailleMaxX() && j >= 0 && j < jeu.getTailleMaxY()) {
                    Cellule voisine = jeu.getGrillexy(i, j);
                    if (condition.test(voisine)) {
                        voisines.add(voisine);
                    }
                }
            }
        }
        return voisines.isEmpty() ? null : voisines.get(random.nextInt(voisines.size()));
    }
}
