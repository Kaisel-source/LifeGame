package s2203089.jeudelavie.rendu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeListener;

import java.awt.FlowLayout;

import s2203089.jeudelavie.JeuDeLaVie;
import s2203089.jeudelavie.observeur.Observateur;
import s2203089.jeudelavie.panel.*;
import s2203089.jeudelavie.panel.GameStatsPanel;

/**
 * Classe représentant l'interface utilisateur du jeu de la vie.
 */
public class JeuDeLaVieUi extends JFrame implements Observateur {

    /**
     * Le jeu de la vie.
     */
    private final JeuDeLaVie jeu;

    /**
     * Largeur par défaut de la fenêtre.
     */
    private final int defaultWidth = 1300;

    /**
     * Hauteur par défaut de la fenêtre.
     */
    private final int defaultHeight = 800;

    /**
     * Label des couleurs.
     */
    private Label colorLabel;

    /**
     * Bouton pour démarrer le jeu.
     */
    private JSlider rayonSlider;

    /**
     * Boolean pour activé les couleurs.
     */
    private boolean color = false;

    private final Label rayonLabel = new Label("Rayon : ");

    private Label modeLabel = new Label("Mode : ");
    private Label vitesseLabel = new Label("Vitesse : ");
    private Label couleurLabel = new Label("Couleurs : ");
    private Label sonLabel = new Label("Son : 80");

    private final RenduJeuDeLaVie rendu;
    private final GameStatsPanel statsPanel;

    public JeuDeLaVieUi(JeuDeLaVie jeu) {
        this.jeu = jeu;
        this.jeu.attacheObservateur(this);

        rendu = new RenduJeuDeLaVie(this.jeu, 0, color);

        setLayout(new BorderLayout());
        add(new ControlsPanel(jeu, rendu), BorderLayout.NORTH);
        statsPanel = new GameStatsPanel(jeu);
        add(creeMenuGauche(), BorderLayout.EAST);
        add(rendu, BorderLayout.CENTER);

        setSize(defaultWidth, defaultHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.actualise();
    }

    private Label creeLabel(String text) {
        Label label = new Label(text);
        return label;
    }

    private JPanel creeMenuDirection() {
        Timer timerUp = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rendu.offsetY += 10;
                rendu.repaint();
            }
        });

        Timer timerDown = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rendu.offsetY -= 10;
                rendu.repaint();
            }
        });

        Timer timerLeft = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rendu.offsetX += 10;
                rendu.repaint();
            }
        });

        Timer timerRight = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rendu.offsetX -= 10;
                rendu.repaint();
            }
        });

        JButton up = new JButton("↑");
        up.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                timerUp.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                timerUp.stop();
            }
        });

        JButton down = new JButton("↓");
        down.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                timerDown.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                timerDown.stop();
            }
        });

        JButton left = new JButton("←");
        left.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                timerLeft.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                timerLeft.stop();
            }
        });

        JButton right = new JButton("→");
        right.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                timerRight.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                timerRight.stop();
            }
        });


        /* on met les bouton dans une matrice */
        JPanel movePanel = new JPanel();
        movePanel.add(up);
        movePanel.add(down);
        movePanel.add(left);
        movePanel.add(right);
        return movePanel;
    }

    private JPanel creeMenuGauche() {
        JPanel stats = new JPanel();
        stats.setSize(100, 500);

        rayonSlider = new JSlider(1, 100, 1);
        rayonSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                rendu.setRayon(rayonSlider.getValue());
            }
        });
        rayonSlider.setMajorTickSpacing(10);
        rayonSlider.setMinorTickSpacing(1);
        rayonSlider.setPaintTicks(true);
        rayonSlider.setPaintLabels(true);

        // Réinitialisation de la grille
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jeu.reinitialiser();
                jeu.setlancer(false);
            }
        });

        JButton randomFill = new JButton("Random Fill");
        randomFill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jeu.initialiseGrille();
            }
        });

        JSlider slider = new JSlider(20, 1000, 500);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                jeu.setDelai(slider.getMaximum() - slider.getValue());
            }
        });
        slider.setMajorTickSpacing(100);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        JCheckBox colorModel = new JCheckBox();
        colorModel.setSelected(false);

        colorModel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = colorModel.isSelected();
                rendu.setColor(color);
                System.out.println("Color : " + color);
                actualise();
            }
        });
        stats.add(statsPanel);
        stats.add(new JSeparator(SwingConstants.HORIZONTAL));
        stats.add(creeLabel("Placer : Click Gauche"));
        stats.add(creeLabel("Enlever : Click Droit"));
        stats.add(creeLabel("Zoom : Molette"));
        stats.add(creeLabel("Déplacer : Flèches"));
        vitesseLabel = creeLabel("Vitesse : ");

        modeLabel = creeLabel("Mode : ");
        couleurLabel = creeLabel("Couleurs : ");
        stats.add(couleurLabel);

        stats.add(new JSeparator(SwingConstants.HORIZONTAL));
        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        colorPanel.add(creeLabel("Prédic : "));
        colorPanel.add(colorModel);
        stats.add(colorPanel);
        stats.add(creeLabel("Bleu : Va naitre"));
        stats.add(creeLabel("Vert : Reste vivant"));
        stats.add(creeLabel("Rouge : Va Mourrir"));
        stats.add(creeLabel("Blanc : Mort"));
        colorLabel = creeLabel("Couleurs : ");
        stats.add(colorLabel);
        stats.add(new JSeparator(SwingConstants.HORIZONTAL));
        stats.add(modeLabel);
        stats.add(new JSeparator(SwingConstants.HORIZONTAL));
        stats.add(rayonSlider);
        stats.add(new JSeparator(SwingConstants.HORIZONTAL));
        stats.add(creeMenuDirection());
        stats.add(new JSeparator(SwingConstants.HORIZONTAL));

        JTextArea density = new JTextArea("Densité : ", 1, 1);
        density.setEditable(false);
        density.setPreferredSize(new Dimension(100, 20));
        density.setMaximumSize(getPreferredSize());

        JSlider densitySlider = new JSlider(0, 200, 100);
        densitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                jeu.setdensite((Double) (densitySlider.getValue() / 200.0));
            }
        });
        JSlider volumeSlider = new JSlider(0, 100, MidiSoundManager.getInstance().getVolume());
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                MidiSoundManager.getInstance().setVolume((int) volumeSlider.getValue());
            }
        });
        stats.add(sonLabel);
        stats.add(volumeSlider);
        stats.add(new JSeparator(SwingConstants.HORIZONTAL));
        stats.add(density);
        stats.add(densitySlider);
        stats.add(randomFill);
        stats.add(resetButton);
        stats.setLayout(new BoxLayout(stats, BoxLayout.PAGE_AXIS));
        return stats;
    }

    /**
     * Actualise l'interface utilisateur.
     */
    @Override
    public void actualise() {
        statsPanel.actualiser(jeu);
        rendu.repaint();
        this.colorLabel.setText("Couleurs : " + getDescMode());
        this.modeLabel.setText("Mode : " + jeu.getVisiteur().getClass().getSimpleName());
        this.vitesseLabel.setText("Vitesse : " + jeu.getDelai());
        this.rayonLabel.setText("Rayon : " + rendu.getRayon());
        this.modeLabel.setText("Mode : " + jeu.getVisiteur().getClass().getSimpleName());
        this.sonLabel.setText("Son : " + MidiSoundManager.getInstance().getVolume());
    }

    private String getDescMode() {
        switch (jeu.getVisiteur().getClass().getSimpleName()) {
            case "VisiteurClassique":
                return "Vivant: Noir, Mort: Blanc";
            case "VisiteurDayAndNight":
                return "Vivant: Orange, Mort: Noir";
            case "VisiteurHighLife":
                return "Vivant: Blanc, Mort: Noir";
            case "VisiteurChaos":
                return "Vivant: Vert, Mort: Rouge";
            case "VisiteurMutation":
                return "Vivant: Bleu, Mort: Blanc";
            case "VisiteurPandemie":
                return "Sain:Bleu,Infecté: Rouge, Mort: Gris";
            case "VisiteurGel":
                return "Vivant: Gris,Gelé:Bleu, Mort: Noir";
            case "VisiteurCivilisation":
                int[] nation = jeu.compterCelluleNation();
                return "Vert: " + nation[0] + ", Rouge: " + nation[1] + ", Bleu: " + nation[2] + ", Jaune: "
                        + nation[3];
            default:
                return "No data";
        }

    }

    public RenduJeuDeLaVie getRendu() {
        return rendu;
    }

}
