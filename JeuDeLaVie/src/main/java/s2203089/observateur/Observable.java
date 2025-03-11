package s2203089.observateur;

import java.util.List;

/**
 * Interface pour les objets observables. utilisée pour appliquer le pattern
 * Observateur
 *
 */
public interface Observable {

    /**
     * Attache l'observateur.
     *
     * @param o l'observateur à attacher
     */
    public void attacheObservateur(Observateur o);

    /**
     * Détache l'observateur.
     *
     * @param o l'observateur à détacher
     */
    public void detacheObservateur(Observateur o);

    /**
     * Notifie les observateurs d'une mise à jour.
     */
    public void notifieObservateur();

    /**
     * Renvoie la liste des observateurs.
     *
     * @return la liste des observateurs
     */
    public List<Observateur> getObservateurs();
}
