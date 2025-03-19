package s2203089.jeudelavie.visiteur;

import java.util.Random;

import s2203089.jeudelavie.JeuDeLaVie;
import s2203089.jeudelavie.cellule.Cellule;
import s2203089.jeudelavie.commande.CommandeMeurt;
import s2203089.jeudelavie.commande.CommandeVit;

public class VisiteurCivilisation extends Visiteur {

    private static final Random random = new Random();

    public VisiteurCivilisation(JeuDeLaVie jeu) {
        super(jeu);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        int ennemis = cellule.nombreVoisinesEnnemies(jeu);

        // Attaque un ennemi avec 80% de chances
        if (ennemis > 0 && random.nextDouble() < 0.8) {
            Cellule ennemi = cellule.voisineEnnemieAleatoire(jeu);
            if (ennemi != null) {
                jeu.ajouteCommande(new CommandeMeurt(ennemi)); // Tue l'ennemi
            }
        }

        // Fortification progressive des cellules survivantes
        cellule.incrementerSurvie();
        if (cellule.getGenerationsSurvecues() >= 2) {
            cellule.fortifier();
        }
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        int allies = cellule.nombreVoisinesAlliees(jeu);

        // Renaît si suffisamment d'alliés sont proches
        if (allies > 1 || random.nextDouble() < 0.5) {
            jeu.ajouteCommande(new CommandeVit(cellule));

            // Adopte la nation dominante des cellules voisines alliées
            Cellule voisinAllie = cellule.voisineAllieAleatoire(jeu);
            if (voisinAllie != null) {
                cellule.changerNation(voisinAllie.getNation());
            }
        }
    }
}
