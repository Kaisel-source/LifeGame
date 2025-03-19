package s2203089.jeudelavie.visiteur;

import java.util.Random;

import s2203089.jeudelavie.JeuDeLaVie;
import s2203089.jeudelavie.cellule.Cellule;
import s2203089.jeudelavie.commande.CommandeMeurt;
import s2203089.jeudelavie.commande.CommandeVit;

/**
 * Represente un visiteur appliquant les règles chaotiques du jeu de la vie.
 * (Règles custom)
 *
 * Les règles sont les suivantes : - 5% de chance qu'une cellule vivante meure
 * spontanément - Une cellule vivante meurt si elle a moins de 2 ou plus de 4
 * voisins - 10% de chance qu'une cellule vivante échange de place avec une
 * voisine morte - 5% de chance qu'une cellule morte revienne à la vie
 * spontanément - Une cellule morte renaît si elle a exactement 3 voisins ou si
 * l'état précédent formait un motif oscillant
 *
 */
public class VisiteurChaos extends Visiteur {

    private static final Random random = new Random();

    /**
     * Constructeur de la classe.
     *
     * @param jeu le jeu de la vie a visiter
     */
    public VisiteurChaos(JeuDeLaVie jeu) {
        super(jeu);
    }

    /**
     * Méthode pour visiter une cellule vivante.
     *
     * @param cellule la cellule a visiter
     */
    @Override
    public void visiteCelluleVivante(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinesVivantes(jeu);

        // 5% de chance qu'une cellule vivante meure spontanément
        if (random.nextDouble() < 0.05) {
            jeu.ajouteCommande(new CommandeMeurt(cellule));
            return;
        }

        // Règle classique modifiée : meurt si trop de voisins
        if (nbVoisins < 2 || nbVoisins > 4) {
            jeu.ajouteCommande(new CommandeMeurt(cellule));
        }

        // Propagation erratique : chance d'échanger une cellule avec une voisine
        if (random.nextDouble() < 0.1) {
            Cellule voisine = cellule.voisineAleatoire(jeu);
            if (voisine != null && !voisine.estVivante()) {
                jeu.ajouteCommande(new CommandeVit(voisine));
                jeu.ajouteCommande(new CommandeMeurt(cellule));
            }
        }
    }

    /**
     * Méthode pour visiter une cellule morte.
     *
     * @param cellule la cellule a visiter
     */
    @Override
    public void visiteCelluleMorte(Cellule cellule) {
        int nbVoisins = cellule.nombreVoisinesVivantes(jeu);

        // 5% de chance qu'une cellule morte revienne à la vie spontanément
        if (random.nextDouble() < 0.05) {
            jeu.ajouteCommande(new CommandeVit(cellule));
            return;
        }

        // Renaissance chaotique : renaît si elle a exactement 3 voisins ou si l'état précédent formait un motif oscillant
        if (nbVoisins == 3 || random.nextInt(10) == cellule.hashCode() % 10) {
            jeu.ajouteCommande(new CommandeVit(cellule));
        }
    }
}
