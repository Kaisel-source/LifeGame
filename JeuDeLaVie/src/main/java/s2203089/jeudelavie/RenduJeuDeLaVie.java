package s2203089.jeudelavie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import s2203089.jeudelavie.cellule.Cellule;
import s2203089.jeudelavie.commande.Commande;
import s2203089.jeudelavie.commande.CommandeMeurt;
import s2203089.jeudelavie.commande.CommandeVit;

public class RenduJeuDeLaVie extends JPanel implements MouseWheelListener, MouseMotionListener, MouseListener {

    /**
     * Décalage en x.
     */
    public int offsetX;

    /**
     * Décalage en y.
     */
    public int offsetY;

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

    JeuDeLaVie jeu;
    int modeJeu;
    boolean color;

    /**
     * Constructeur de la classe.
     */
    RenduJeuDeLaVie(JeuDeLaVie jeu, int modeJeu, boolean color) {
        this.jeu = jeu;
        this.setVisible(true);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);

        // commence au centre
        offsetX = -(int) (jeu.getTailleMaxX() * scale / 2);
        offsetY = -(int) (jeu.getTailleMaxY() * scale / 2);
    }

    public void setModeJeu(int modeJeu) {
        this.modeJeu = modeJeu;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public int getRayon() {
        return rayon;
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
                if (cy + tailleZone < 0 || cy > this.getHeight()) {
                    continue;
                }

                if (color) {
                    switch (modeJeu) {
                        case 0 ->
                            colorPredicClassique(x, y, g);
                        case 1 ->
                            colorPredicDN(x, y, g);
                        case 2 ->
                            colorPredicHL(x, y, g);
                        default -> {
                        }
                    }
                } else {
                    Cellule cellule = jeu.getGrillexy(x, y);
                    if (cellule.estVivante()) {
                        switch (modeJeu) {
                            case 0 ->
                                g.setColor(Color.BLACK);
                            case 1 ->
                                g.setColor(Color.ORANGE);
                            case 2 ->
                                g.setColor(Color.WHITE);
                            case 3 ->
                                g.setColor(Color.GREEN);
                            case 4 -> { // Mode Mutation
                                if (cellule.estImmortelle(3)) {
                                    g.setColor(Color.MAGENTA);
                                } else {
                                    g.setColor(Color.BLUE);
                                }
                            }
                            case 5 -> { // Mode Gel
                                g.setColor(cellule.estGelee() ? Color.CYAN : Color.LIGHT_GRAY);
                            }
                            case 6 -> { // Mode Évolution
                                g.setColor(cellule.estSuperCellule() ? Color.GREEN : Color.CYAN);
                            }
                            case 7 -> { // Mode Civilisation
                                switch (cellule.getNation()) {
                                    case 0 ->
                                        g.setColor(new Color(255, 0, 0));   // Rouge
                                    case 1 ->
                                        g.setColor(new Color(0, 255, 0));   // Vert
                                    case 2 ->
                                        g.setColor(new Color(0, 0, 255));   // Bleu
                                    case 3 ->
                                        g.setColor(new Color(255, 255, 0)); // Jaune
                                    case 4 ->
                                        g.setColor(new Color(255, 165, 0)); // Orange
                                    default ->
                                        g.setColor(Color.WHITE);
                                }
                            }
                            case 8 -> { // Mode Pandémie
                                if (cellule.estInfectee()) {
                                    g.setColor(cellule.estCritique() ? new Color(128, 0, 128) : Color.RED); // Critique en violet
                                } else if (cellule.estImmune()) {
                                    g.setColor(new Color(144, 238, 144)); // Vert clair pour immunisé
                                } else {
                                    g.setColor(Color.BLUE); // Cellules saines
                                }
                            }
                            default -> {
                            }
                        }
                    } else { // Cellules mortes
                        g.setColor(modeJeu == 7 || modeJeu == 8 ? Color.GRAY : switch (modeJeu) {
                            case 0 ->
                                Color.WHITE;
                            case 1, 2 ->
                                Color.BLACK;
                            case 3 ->
                                Color.RED;
                            case 4 ->
                                cellule.peutEncoreRenaître(2) ? Color.YELLOW : Color.DARK_GRAY;
                            case 5 ->
                                Color.DARK_GRAY;
                            case 6 ->
                                Color.BLACK;
                            default ->
                                throw new AssertionError();
                        });
                    }
                }
                g.fillRect(cx, cy, tailleZone, tailleZone);
            }
            g.drawRect(offsetX, offsetY, (int) (jeu.getTailleMaxX() * scale), (int) (jeu.getTailleMaxY() * scale));
        }
    }

    private void colorPredicDN(int x, int y, Graphics g) {
        switch (jeu.getGrillexy(x, y).nombreVoisinesVivantes(jeu)) {
            case 3, 6, 7, 8 -> {
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.GREEN); //vie
                } else {
                    g.setColor(Color.BLUE); //naissance
                }
            }
            case 4 -> {
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.GREEN); //vie
                } else {
                    g.setColor(Color.WHITE); //meurt
                }
            }
            default -> {
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.RED); //meurt
                } else {
                    g.setColor(Color.WHITE); //Vide
                }
            }
        }
    }

    private void colorPredicClassique(int x, int y, Graphics g) {
        switch (jeu.getGrillexy(x, y).nombreVoisinesVivantes(jeu)) {
            case 2 -> {
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.GREEN); //vie
                } else {
                    g.setColor(Color.WHITE);
                }
            }
            case 3 -> {
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.GREEN); //vie
                } else {
                    g.setColor(Color.BLUE); //naissance
                }
            }
            default -> {
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.RED); //meurt
                } else {
                    g.setColor(Color.WHITE); //Vide
                }
            }
        }

    }

    private void colorPredicHL(int x, int y, Graphics g) {
        switch (jeu.getGrillexy(x, y).nombreVoisinesVivantes(jeu)) {
            case 2 -> {
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.WHITE);
                }
            }
            case 3 -> {
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.GREEN); //vie
                } else {
                    g.setColor(Color.BLUE); //naissance
                }
            }
            case 6 -> {
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.RED); //vie
                } else {
                    g.setColor(Color.BLUE); //naissance
                }
            }
            default -> {
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.RED); //meurt
                } else {
                    g.setColor(Color.WHITE); //vide
                }
            }
        }
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
