package s2203089.jeudelavie.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import s2203089.jeudelavie.JeuDeLaVie;
import s2203089.jeudelavie.rendu.RenduJeuDeLaVie;

public class ControlsPanel extends JPanel {

    public ControlsPanel(JeuDeLaVie jeu, RenduJeuDeLaVie rendu) {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1300, 180));  // Augmenter la hauteur
        setMinimumSize(new Dimension(1300, 180));
        setBackground(new Color(240, 240, 240)); // Fond gris clair pour tester la taille

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10); // Marges autour des composants

        JButton startButton = new JButton("Lancer");
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.addActionListener(e -> {
            jeu.setlancer(!jeu.getlancer());
            startButton.setText(jeu.getlancer() ? "Stop" : "Lancer");
        });

        JButton nextGenButton = new JButton("Gen suivante");
        nextGenButton.setPreferredSize(new Dimension(200, 50));
        nextGenButton.addActionListener(e -> {
            if (jeu.getlancer()) {
                jeu.setlancer(false);
            }
            jeu.calculerGenerationSuivante();
            jeu.notifieObservateur();
        });

        JSlider speedSlider = new JSlider(20, 1000, 500);
        speedSlider.setPreferredSize(new Dimension(400, 50));
        speedSlider.addChangeListener(e -> jeu.setDelai(speedSlider.getMaximum() - speedSlider.getValue()));
        speedSlider.setMajorTickSpacing(100);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);

        JLabel speedLabel = new JLabel("Vitesse: ");

        // Placement des composants avec GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        add(startButton, gbc);

        gbc.gridx = 1;
        add(nextGenButton, gbc);

        gbc.gridx = 2;
        add(speedLabel, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.6;
        add(speedSlider, gbc);

        // Ajout du ModeSelectionPanel avec largeur complète
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Étendre sur toute la ligne
        gbc.weightx = 1.0;
        gbc.weighty = 0.5; // Laisser un peu d'espace verticalement
        ModeSelectionPanel modePanel = new ModeSelectionPanel(jeu, rendu);
        modePanel.setPreferredSize(new Dimension(1200, 60)); // Taille adaptée
        modePanel.setMinimumSize(new Dimension(1200, 60));
        add(modePanel, gbc);
    }
}
