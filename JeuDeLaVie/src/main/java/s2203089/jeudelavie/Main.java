package s2203089.jeudelavie;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import s2203089.jeudelavie.rendu.JeuDeLaVieUi;

/**
 * Classe principale.
 */
public class Main {

    public static void main(String[] args) {
        // Demande de la taille de la grille via une interface Swing
        int[] taille = demanderTailleGrille();
        int largeur = taille[0];
        int hauteur = taille[1];

        // Création du jeu avec la taille sélectionnée
        JeuDeLaVie jeu = new JeuDeLaVie(largeur, hauteur);
        JeuDeLaVieUi ui = new JeuDeLaVieUi(jeu);

        while (true) {
            jeu.notifieObservateur();
            if (jeu.getlancer()) {
                jeu.calculerGenerationSuivante();
                ui.actualise();

                try {
                    Thread.sleep(100); // Pause entre les générations
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static int[] demanderTailleGrille() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        JTextField largeurField = new JTextField("500");
        JTextField hauteurField = new JTextField("500");
        panel.add(new JLabel("Largeur :"));
        panel.add(largeurField);
        panel.add(new JLabel("Hauteur :"));
        panel.add(hauteurField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Taille du Jeu de la Vie", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int largeur = Integer.parseInt(largeurField.getText());
                int hauteur = Integer.parseInt(hauteurField.getText());
                return new int[]{largeur, hauteur};
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Veuillez entrer des nombres valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return demanderTailleGrille(); // Redemander si entrée invalide
            }
        } else {
            System.exit(0); // Fermer l'application si l'utilisateur annule
        }
        return new int[]{500, 500}; // Valeurs par défaut (au cas où)
    }
}
