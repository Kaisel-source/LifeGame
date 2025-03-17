package s2203089.jeudelavie.commande;

import s2203089.jeudelavie.cellule.Cellule;

/**
 * Classe représentant la commande pour tuer une cellule.
 */
public class CommandeMeurt extends Commande {

    /**
     * Constructeur de la classe.
     *
     * @param cellule la cellule sur laquelle la commande va être appliquée
     */
    public CommandeMeurt(Cellule cellule) {
        super(cellule);
    }

    /**
     * Méthode pour exécuter la commande.
     */
    @Override
    public void executer() {
        this.cellule.meurt();
    }

}
