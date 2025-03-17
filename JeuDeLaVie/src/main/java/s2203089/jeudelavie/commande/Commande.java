package s2203089.jeudelavie.commande;

import s2203089.jeudelavie.cellule.Cellule;

/**
 * Représente une commande. (Command)
 */
public abstract class Commande {

    /**
     * La cellule pour appliquer la commande.
     */
    protected Cellule cellule;

    /**
     * Constructeur de la classe.
     *
     * @param cellule la cellule sur laquelle la commande va être appliquée
     */
    public Commande(Cellule cellule) {
        this.cellule = cellule;
    }

    /**
     * Permet d'exécuter la commande.
     */
    public abstract void executer();
}
