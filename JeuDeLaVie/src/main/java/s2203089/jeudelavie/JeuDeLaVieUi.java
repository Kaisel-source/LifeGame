package s2203089.jeudelavie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeListener;

import s2203089.jeudelavie.commande.Commande;
import s2203089.jeudelavie.commande.CommandeMeurt;
import s2203089.jeudelavie.commande.CommandeVit;
import s2203089.jeudelavie.observeur.Observateur;
import s2203089.jeudelavie.visiteur.Visiteur;
import s2203089.jeudelavie.visiteur.VisiteurClassique;
import s2203089.jeudelavie.visiteur.VisiteurDayAndNight;
import s2203089.jeudelavie.visiteur.VisiteurHighLife;

/**
 * Classe représentant l'interface utilisateur du jeu de la vie.
 */
public class JeuDeLaVieUi extends JFrame implements Observateur {

    /**
     * Le jeu de la vie.
     */
    private final JeuDeLaVie jeu;

    /**
     * Bouton pour passer à la génération suivante.
     */
    private final JButton nextGeneration;

    /**
     * Largeur par défaut de la fenêtre.
     */
    private final int defaultWidth = 1366;

    /**
     * Hauteur par défaut de la fenêtre.
     */
    private final int defaultHeight = 768;

    /**
     * Label de la génération.
     */
    private final JLabel generationLabel;

    /**
     * Label de le nombre de cellules.
     */
    private final JLabel cellulesLabel;

    /**
     * Label de la densité.
     */
    private final JLabel densiteLabel;

    /**
     * Label des couleurs.
     */
    private final JLabel colorLabel;

    /**
     * Bouton pour démarrer le jeu.
     */
    private final JButton startButton;

    /**
     * Radio bouton pour le mode classique.
     */
    private JRadioButton classiqueMode;

    /**
     * Radio bouton pour le mode day and night.
     */
    private JRadioButton dayAndNightMode;

    /**
     * Radio bouton pour le mode high life.
     */
    private JRadioButton highLifeMode;

    /**
     * Boolean pour activé les couleurs.
     */
    private boolean color = false;

    /**
     * Couleur des boutons.
     */
    private final Color buttonColor = new Color(230, 240, 255);

    public JeuDeLaVieUi(JeuDeLaVie jeu) {
        this.jeu = jeu;
        this.jeu.attacheObservateur(this);

        /*
         * Bouton de démarrage
         */
        this.startButton = new JButton();
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // inverse l'état de jeu
                jeu.setlancer(!jeu.getlancer());
            }
        });
        startButton.setFont(new Font("", Font.PLAIN, 40));
        startButton.setBackground(buttonColor);

        this.nextGeneration = new JButton("Next Generation");
        nextGeneration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (jeu.getlancer()) { // Si le jeu est en pause
                    jeu.setlancer(false); // On met le jeu en pause
                }
                jeu.calculerGenerationSuivante(); // On calcule la génération suivante
                jeu.notifieObservateur(); // On notifie les observateurs
            }
        });

        nextGeneration.setFont(new Font("", Font.PLAIN, 40));
        nextGeneration.setBackground(buttonColor);

        /* Un slider allant de 1000 a 50 pour set le délais */
        JSlider slider = new JSlider(20, 1000, 500);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                jeu.setDelai(slider.getMaximum() - slider.getValue());
            }
        });

        /* Ajout du rendu */
        RenduJeuDeLaVie rendu = new RenduJeuDeLaVie();

        /* Un slider allant de 1 a 30 pour set le rayon */
        JSlider rayonSlider = new JSlider(1, 30, 1);
        rayonSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                rendu.setRayon(rayonSlider.getValue());
            }
        });

        /* des bouton pour déplacer la matrice */
        int pullSpeed = 50;
        int offset = 10;
        // Créez un Timer pour chaque direction
        Timer timerUp = new Timer(pullSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rendu.offsetY += offset;
                rendu.repaint();
            }
        });

        // Répétez pour timerDown, timerLeft, timerRight avec les ajustements appropriés
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

        Timer timerDown = new Timer(pullSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rendu.offsetY -= offset;
                rendu.repaint();
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

        Timer timerLeft = new Timer(pullSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rendu.offsetX += offset;
                rendu.repaint();
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

        Timer timerRight = new Timer(pullSpeed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rendu.offsetX -= offset;
                rendu.repaint();
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

        JToolBar toolBar = new JToolBar();

        /* Ajout des composants */
        toolBar.add(startButton);
        toolBar.add(nextGeneration);

        JCheckBox colorModel = new JCheckBox();
        colorModel.setSelected(false);

        colorModel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = colorModel.isSelected();
            }
        });

        toolBar.add(colorModel);

        toolBar.add(slider);
        toolBar.add(creerSelecteurMode());

        toolBar.setFloatable(false);

        this.add(toolBar, BorderLayout.NORTH);

        /* Statistiques du jeu */
        JPanel stats = new JPanel();
        stats.setSize(100, 500);

        this.generationLabel = new JLabel();
        this.cellulesLabel = new JLabel();
        this.densiteLabel = new JLabel();
        this.colorLabel = new JLabel();

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

        JTextArea usage = new JTextArea(
                "Placer : Click Gauche\n"
                + "Enlever : Click Droit\n"
                + "Zoom : Molette\n"
                + "Déplacer : Flèches\n"
                + "Rayon : Slider bas\n"
                + "Vitesse : Slider haut\n"
                + "Mode : Radio bouton\n"
                + "Couleurs : Checkbox\n"
                + "0 voisine : Noir\n"
                + "1 voisine : Rouge\n"
                + "2 voisines : Orange\n"
                + "3 voisines : Jaune\n"
                + "4 voisines : Vert\n"
                + "5 voisines : Bleu\n"
                + "6 voisines : Cyan\n"
                + "7 voisines : Magenta\n"
                + "8 voisines : Rose\n",
                6, 16);
        usage.setEditable(false);

        stats.add(generationLabel);
        stats.add(cellulesLabel);
        stats.add(densiteLabel);
        stats.add(colorLabel);

        stats.add(new JSeparator(SwingConstants.HORIZONTAL));
        stats.add(usage);
        stats.add(new JSeparator(SwingConstants.HORIZONTAL));
        stats.add(rayonSlider);
        stats.add(movePanel);
        stats.add(new JSeparator(SwingConstants.HORIZONTAL));
        stats.add(resetButton);
        JTextArea density = new JTextArea("Densité : ", 1, 1);
        density.setEditable(false);
        density.setPreferredSize(new Dimension(100, 20));
        density.setMaximumSize(getPreferredSize());
        stats.add(density);
        JSlider densitySlider = new JSlider(0, 200, 100);
        densitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                jeu.setdensite((Double) (densitySlider.getValue() / 200.0));
            }
        });
        stats.add(densitySlider);
        stats.add(randomFill);
        stats.setLayout(new BoxLayout(stats, BoxLayout.PAGE_AXIS));
        this.add(stats, BorderLayout.WEST);

        this.add(rendu, BorderLayout.CENTER);

        this.setSize(defaultWidth, defaultHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Méthode permettant de créer le selecteur de mode.
     *
     * @return le JPanel du selecteur de mode
     */
    private JPanel creerSelecteurMode() {
        /*
        * Selection du mode de jeu
         */ JPanel modSelector = new JPanel();
        modSelector.setLayout(new BoxLayout(modSelector, BoxLayout.PAGE_AXIS));

        /* mode classique */
        this.classiqueMode = new JRadioButton("Classique");
        classiqueMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jeu.setVisiteur(new VisiteurClassique(jeu));
            }
        });

        /* mode day and night */
        this.dayAndNightMode = new JRadioButton("Day and Night");
        dayAndNightMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jeu.setVisiteur(new VisiteurDayAndNight(jeu));
            }
        });

        /* mode high life */
        this.highLifeMode = new JRadioButton("High Life");
        highLifeMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jeu.setVisiteur(new VisiteurHighLife(jeu));
            }
        });

        /* Ajout des modes de jeu */
        modSelector.add(classiqueMode);
        modSelector.add(dayAndNightMode);
        modSelector.add(highLifeMode);
        return modSelector;
    }

    /**
     * Actualise l'interface utilisateur.
     */
    @Override
    public void actualise() {
        this.generationLabel.setText("Generation : " + this.jeu.getGeneration());
        this.cellulesLabel.setText("Cellules : " + this.jeu.compterCellules());
        this.densiteLabel.setText("Densité : " + jeu.getdensite());
        this.colorLabel.setText("Couleurs : " + this.color);

        if (jeu.getlancer()) {
            this.startButton.setText("Pause");
        } else {
            this.startButton.setText("Start");
        }

        Visiteur v = jeu.getVisiteur();
        if (v instanceof VisiteurClassique) {
            this.classiqueMode.setSelected(true);
            this.dayAndNightMode.setSelected(false);
            this.highLifeMode.setSelected(false);
        } else if (v instanceof VisiteurDayAndNight) {
            this.dayAndNightMode.setSelected(true);
            this.classiqueMode.setSelected(false);
            this.highLifeMode.setSelected(false);
        } else if (v instanceof VisiteurHighLife) {
            this.highLifeMode.setSelected(true);
            this.classiqueMode.setSelected(false);
            this.dayAndNightMode.setSelected(false);
        }

        this.repaint();
    }

    /**
     * Classe représentant le rendu du jeu de la vie. utilisée pour appliquer le
     * pattern Observer
     *
     * @see Observateur
     * @see JPanel
     * @see MouseWheelListener
     * @see MouseMotionListener
     * @see MouseListener
     */
    public class RenduJeuDeLaVie extends JPanel implements MouseWheelListener, MouseMotionListener, MouseListener {

        /**
         * Décalage en x.
         */
        private int offsetX;

        /**
         * Décalage en y.
         */
        private int offsetY;

        /**
         * Dernier bouton pressé (gauche ou droite).
         */
        private int dernierClic;

        /**
         * Échelle.
         */
        private double scale = 10;

        /**
         * Dernière commande.
         */
        private Commande lastCommande;

        /**
         * Rayon d'action des action.
         */
        private int rayon = 1;

        /**
         * la position x de la cellule pointer par la souris.
         */
        private int posX;

        /**
         * position y de la cellule pointer par la souris.
         */
        private int posY;

        /**
         * Constructeur de la classe.
         */
        RenduJeuDeLaVie() {
            this.setVisible(true);
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
            this.addMouseWheelListener(this);

            // commence au centre
            offsetX = -(int) (jeu.getTailleMaxX() * scale / 2);
            offsetY = -(int) (jeu.getTailleMaxY() * scale / 2);
        }

        /**
         * Méthode permettant de dessiner la grille.
         *
         * @param g l'instance de Graphics
         */
        @Override
        public void paint(Graphics g) {
            super.paint(g);

            for (int x = 0; x < jeu.getTailleMaxX(); x++) {
                for (int y = 0; y < jeu.getTailleMaxY(); y++) {

                    int tailleZone = (int) (scale);

                    int cx = (int) (x * scale + offsetX);
                    if (cx + tailleZone < 0 || cx > this.getWidth()) {
                        continue;
                    }
                    int cy = (int) (y * scale + offsetY);
                    if (cy + tailleZone < 0 || cy > this.getWidth()) {
                        continue;
                    }
                    if (jeu.getGrillexy(x, y).estVivante()) {
                        if (color) {
                            switch (jeu.getGrillexy(x, y).nombreVoisinesVivantes(jeu)) {
                                case 0:
                                    g.setColor(Color.BLACK);
                                    break;
                                case 1:
                                    g.setColor(Color.RED);
                                    break;
                                case 2:
                                    g.setColor(Color.ORANGE);
                                    break;
                                case 3:
                                    g.setColor(Color.YELLOW);
                                    break;
                                case 4:
                                    g.setColor(Color.GREEN);
                                    break;
                                case 5:
                                    g.setColor(Color.BLUE);
                                    break;
                                case 6:
                                    g.setColor(Color.CYAN);
                                    break;
                                case 7:
                                    g.setColor(Color.MAGENTA);
                                    break;
                                case 8:
                                    g.setColor(Color.PINK);
                                    break;
                                default:
                                    g.setColor(Color.BLACK);
                                    break;
                            }

                        } else {
                            g.setColor(Color.BLACK);
                        }
                        g.fillRect(cx, cy, tailleZone, tailleZone);
                    }
                }
            }
            g.setColor(Color.RED);
            g.drawRect(offsetX, offsetY, (int) (jeu.getTailleMaxX() * scale), (int) (jeu.getTailleMaxY() * scale));
        }

        /**
         * Ajoute une commande manuelle et l'execute.
         *
         * @param e l'instance de MouseEvent
         * @param c la commande a appliquer
         */
        public void commandeManuelle(MouseEvent e, Commande c) {
            if (lastCommande == null || !lastCommande.equals(c)
                    && posX >= 0 && posY >= 0
                    && posX < jeu.getTailleMaxX() && posY < jeu.getTailleMaxY()) {
                lastCommande = c;
                c.executer();
            }
        }

        /**
         * Défini le rayon d'action des actions.
         *
         * @param rayon le rayon a set
         */
        public void setRayon(int rayon) {
            if (rayon > 0) {
                this.rayon = rayon;
            }
        }

        /**
         * Gère l'actualisation de la cellule en fonction de la position de la
         * souris.
         *
         * @param e l'evenement de souris
         */
        public void actualiserCellule(MouseEvent e) {

            posX = (int) ((e.getX() - offsetX) / scale);
            posY = (int) ((e.getY() - offsetY) / scale);
        }

        /**
         * Gère l'évènement lors du clic de souris (Clic gauche ou droit).
         *
         * @param dernierClic le dernier bouton pressé
         * @param e l'evenement de souris
         */
        private void gestionClick(int dernierClic, MouseEvent e) {
            if (dernierClic == MouseEvent.BUTTON1) { // Si on clique gauche on ajoute des cellules
                for (int i = -rayon; i <= rayon; i++) {
                    for (int j = -rayon; j <= rayon; j++) {
                        if ((i * i + j * j) <= (rayon * rayon)) {
                            commandeManuelle(e, new CommandeVit(jeu.getGrillexy(posX + i, posY + j)));
                        }
                    }
                }
            }

            if (dernierClic == MouseEvent.BUTTON3) { // Si on clique droit on enlève des cellules
                for (int i = -rayon; i <= rayon; i++) {
                    for (int j = -rayon; j <= rayon; j++) {
                        if ((i * i + j * j) <= (rayon * rayon)) {
                            commandeManuelle(e, new CommandeMeurt(jeu.getGrillexy(posX + i, posY + j)));
                        }
                    }
                }
            }
        }

        /**
         * Gère l'évènement lors du clic de souris.
         *
         * @param e l'evenement de souris
         */
        @Override
        public void mousePressed(MouseEvent e) {
            dernierClic = e.getButton();
            actualiserCellule(e);

            gestionClick(dernierClic, e);

            repaint();
        }

        /**
         * Gère l'évènement lors du déplacement de souris.
         *
         * @param e l'evenement de souris
         */
        @Override
        public void mouseDragged(MouseEvent e) {

            actualiserCellule(e);

            gestionClick(dernierClic, e);

            repaint();
        }

        /**
         * Change l'échelle de la grille en fonction de la molette de la souris.
         */
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            double oldScale = this.scale;

            // On change la taille multiplicativement, car c'est beaucoup plus agréable
            this.scale *= e.getWheelRotation() > 0 ? (this.scale < 1.2 ? 1 : 0.9) : (this.scale > 200 ? 1 : 1.1);

            // L'offset change pour que l'on zoom en centrant sur le curseur
            offsetX -= ((e.getX() - offsetX) * ((this.scale / oldScale) - 1));
            offsetY -= ((e.getY() - offsetY) * ((this.scale / oldScale) - 1));
            repaint();
        }

        // ================================== Non utilisé ==================================
        /**
         * Non utilisé.
         */
        @Override
        public void mouseReleased(MouseEvent e) {
        }

        /**
         * Non utilisé.
         */
        @Override
        public void mouseEntered(MouseEvent e) {
        }

        /**
         * Non utilisé.
         */
        @Override
        public void mouseExited(MouseEvent e) {
        }

        /**
         * Non utilisé.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        /**
         * Non utilisé.
         */
        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }
}
