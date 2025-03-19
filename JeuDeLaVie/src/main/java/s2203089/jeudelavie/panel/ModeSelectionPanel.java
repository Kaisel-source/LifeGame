package s2203089.jeudelavie.panel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import s2203089.jeudelavie.JeuDeLaVie;
import s2203089.jeudelavie.rendu.RenduJeuDeLaVie;
import s2203089.jeudelavie.visiteur.Visiteur;
import s2203089.jeudelavie.visiteur.VisiteurChaos;
import s2203089.jeudelavie.visiteur.VisiteurCivilisation;
import s2203089.jeudelavie.visiteur.VisiteurClassique;
import s2203089.jeudelavie.visiteur.VisiteurDayAndNight;
import s2203089.jeudelavie.visiteur.VisiteurEvolution;
import s2203089.jeudelavie.visiteur.VisiteurGel;
import s2203089.jeudelavie.visiteur.VisiteurHighLife;
import s2203089.jeudelavie.visiteur.VisiteurMutation;
import s2203089.jeudelavie.visiteur.VisiteurPandemie;

public class ModeSelectionPanel extends JPanel {

    public ModeSelectionPanel(JeuDeLaVie jeu, RenduJeuDeLaVie rendu) {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(1200, 100)); // Taille ajustée
        setMinimumSize(new Dimension(1200, 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Espacement autour des boutons
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        ButtonGroup group = new ButtonGroup();

        // Création des boutons radio
        JRadioButton classiqueMode = createModeButton("Classique", jeu, rendu, new VisiteurClassique(jeu), 0, group);
        JRadioButton dayAndNightMode = createModeButton("Day and Night", jeu, rendu, new VisiteurDayAndNight(jeu), 1, group);
        JRadioButton highLifeMode = createModeButton("High Life", jeu, rendu, new VisiteurHighLife(jeu), 2, group);
        JRadioButton chaosMode = createModeButton("Chaos", jeu, rendu, new VisiteurChaos(jeu), 3, group);
        JRadioButton mutationMode = createModeButton("Mutation", jeu, rendu, new VisiteurMutation(jeu), 4, group);
        JRadioButton gelMode = createModeButton("Gel", jeu, rendu, new VisiteurGel(jeu), 5, group);
        JRadioButton evolutionMode = createModeButton("Evolution", jeu, rendu, new VisiteurEvolution(jeu), 6, group);
        JRadioButton civilisationMode = createModeButton("Civilisation", jeu, rendu, new VisiteurCivilisation(jeu), 7, group);
        JRadioButton pandemicMode = createModeButton("Pandemie", jeu, rendu, new VisiteurPandemie(jeu), 8, group);

        // Ajout des boutons dans un agencement 2 colonnes × 3 lignes
        JRadioButton[] buttons = {classiqueMode, dayAndNightMode, highLifeMode, chaosMode, mutationMode, gelMode, evolutionMode, civilisationMode, pandemicMode};
        for (int i = 0; i < buttons.length; i++) {
            gbc.gridx = i % 2; // Deux colonnes
            gbc.gridy = i / 2; // Change de ligne après deux boutons
            add(buttons[i], gbc);
        }
    }

    private JRadioButton createModeButton(String name, JeuDeLaVie jeu, RenduJeuDeLaVie rendu, Visiteur visiteur, int modeIndex, ButtonGroup group) {
        JRadioButton button = new JRadioButton(name);
        button.addActionListener(e -> {
            jeu.setVisiteur(visiteur);
            rendu.setModeJeu(modeIndex);
        });
        group.add(button);
        return button;
    }
}
