package s2203089.commande;

import s2203089.cellule.Cellule;

/**
 * Classe abstraite pour le pattern Commande.
 */
public abstract class Commande {

    /**
     * La cellule pour appliquer la commande.
     */
    protected Cellule cellule;

    /**
     * Constructeur de la classe.
     *
     * @param cellule la cellule pour appliquer la commande
     */
    public Commande(Cellule cellule) {
        this.cellule = cellule;
    }

    /**
     * Permet d'exécuter la commande.
     */
    public abstract void executer();
}
