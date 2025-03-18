package s2203089.jeudelavie.visiteur;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import s2203089.jeudelavie.JeuDeLaVie;
import s2203089.jeudelavie.cellule.Cellule;
import s2203089.jeudelavie.commande.CommandeMeurt;
import s2203089.jeudelavie.commande.CommandeVit;

public class VisiteurMutation extends Visiteur {

    private static final Random random = new Random();
    private final Map<Cellule, Integer> resistance = new HashMap<>();
    private final Map<Cellule, Integer> renaissances = new HashMap<>();
    private static final int MAX_RESISTANCE = 5;
    private static final int MAX_RENAISSANCES = 3;

    public VisiteurMutation(JeuDeLaVie jeu) {
        super(jeu);
    }

    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinesVivantes(jeu);

        // Augmente la résistance avec le temps
        resistance.put(cellule, resistance.getOrDefault(cellule, 0) + 1);

        // Cellule devient immortelle si elle atteint MAX_RESISTANCE
        if (resistance.get(cellule) >= MAX_RESISTANCE) {
            return;
        }

        // Meurt si trop de voisins ou pas assez
        if (nbVoisins < 2 || nbVoisins > 4) {
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }
    }

    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinesVivantes(jeu);

        // Garde un compteur de résurrections
        renaissances.put(cellule, renaissances.getOrDefault(cellule, 0) + 1);

        // Une cellule meurt définitivement après trop de renaissances
        if (renaissances.get(cellule) > MAX_RENAISSANCES) {
            return;
        }

        if (nbVoisins == 3 || (random.nextDouble() < 0.1 && nbVoisins == 2)) {
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
