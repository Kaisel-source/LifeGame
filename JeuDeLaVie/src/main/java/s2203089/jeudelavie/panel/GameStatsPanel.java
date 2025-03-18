package s2203089.jeudelavie.panel;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import s2203089.jeudelavie.JeuDeLaVie;

public class GameStatsPanel extends JPanel {

    private final JLabel generationLabel;
    private final JLabel cellulesLabel;
    private final JLabel densiteLabel;

    public GameStatsPanel(JeuDeLaVie jeu) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        generationLabel = new JLabel("Génération: 0");
        cellulesLabel = new JLabel("Cellules: 0");
        densiteLabel = new JLabel("Densité: 0");

        add(generationLabel);
        add(cellulesLabel);
        add(densiteLabel);
    }

    public void actualiser(JeuDeLaVie jeu) {
        generationLabel.setText("Génération: " + jeu.getGeneration());
        cellulesLabel.setText("Cellules: " + jeu.compterCellules());
        densiteLabel.setText("Densité: " + jeu.getdensite());
    }
}
