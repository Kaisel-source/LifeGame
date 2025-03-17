package s2203089.jeudelavie.commande;

import s2203089.jeudelavie.cellule.Cellule;

/**
 * Classe représentant la commande pour faire vivre une cellule.
 */
public class CommandeVit extends Commande {

    /**
     * Constructeur de la classe.
     *
     * @param cellule la cellule sur laquelle la commande va être appliquée
     */
    public CommandeVit(Cellule cellule) {
        super(cellule);
    }

    /**
     * Méthode pour exécuter la commande.
     */
    @Override
    public void executer() {
        this.cellule.vit();
    }

}
