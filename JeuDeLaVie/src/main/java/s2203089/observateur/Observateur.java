package s2203089.observateur;

/**
 * Interface représentant un observateur. utilisée pour appliquer le pattern
 * Observateur
 *
 * @see Observable
 */
public interface Observateur {

    /**
     * Actualise l'observateur.
     */
    public void actualise();
}
