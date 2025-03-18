package s2203089.jeudelavie.visiteur;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import s2203089.jeudelavie.JeuDeLaVie;
import s2203089.jeudelavie.cellule.Cellule;
import s2203089.jeudelavie.commande.CommandeMeurt;
import s2203089.jeudelavie.commande.CommandeVit;

public class VisiteurGel extends Visiteur {

    private static final Random random = new Random();
    private final Map<Cellule, Integer> cellulesGelees = new HashMap<>();

    public VisiteurGel(JeuDeLaVie jeu) {
        super(jeu);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        if (cellulesGelees.getOrDefault(cellule, 0) > 0) {
            cellulesGelees.put(cellule, cellulesGelees.get(cellule) - 1);
            return;
        }

        int nbVoisins = cellule.nombreVoisinesVivantes(jeu);
        if (nbVoisins == 0 || nbVoisins > 5) {
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }

        if (random.nextDouble() < 0.1) {
            cellulesGelees.put(cellule, 2);
        }
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        if (cellulesGelees.getOrDefault(cellule, 0) > 0) {
            cellulesGelees.put(cellule, cellulesGelees.get(cellule) - 1);
            return;
        }

        int nbVoisins = cellule.nombreVoisinesVivantes(jeu);
        if (nbVoisins == 4) {
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
