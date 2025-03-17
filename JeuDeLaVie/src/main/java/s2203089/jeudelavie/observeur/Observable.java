package s2203089.jeudelavie.observeur;

import java.util.List;

/**
 * Reoprésente un objet observable.
 */
public interface Observable {

    /**
     * Attache un observateu.
     *
     * @param obj l'observateur à attacher
     */
    public void attacheObservateur(Observateur obj);

    /**
     * Détache un observateur.
     *
     * @param obj l'observateur à détacher
     */
    public void detacheObservateur(Observateur obj);

    /**
     * Notifie les observateurs.
     */
    public void notifieObservateur();

    /**
     * Renvoie la liste des observateurs.
     *
     * @return la liste des observateurs
     */
    public List<Observateur> getObservateurs();
}
