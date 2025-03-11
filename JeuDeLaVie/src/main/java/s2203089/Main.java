package s2203089;

public class Main {

    public static void main(String[] args) {
        JeuDeLaVie jeu = new JeuDeLaVie(1000, 1000);
        JeuDeLaVieTerminal jeuTerm = new JeuDeLaVieTerminal(jeu);
        new JeuDeLaVieUi(jeu);

        while (true) {
            jeu.notifieObservateur();
            if (jeu.getRun()) {
                jeu.calculerGenerationSuivante();
                try {
                    Thread.sleep(jeu.getDelai());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
