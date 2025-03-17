package s2203089.jeudelavie;

/**
 * Classe principale.
 */
public class Main {

    public static void main(String[] args) {
        JeuDeLaVie jeu = new JeuDeLaVie();
        new JeuDeLaVieUi(jeu);

        while (true) {
            jeu.notifieObservateur();

            if (jeu.getlancer()) {
                jeu.calculerGenerationSuivante();
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
