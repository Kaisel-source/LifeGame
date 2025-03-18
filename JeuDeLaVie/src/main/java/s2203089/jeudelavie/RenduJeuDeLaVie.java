package s2203089.jeudelavie;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

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
                if (cy + tailleZone < 0 || cy > this.getWidth()) {
                    continue;
                }
                if (color) {
                    switch (modeJeu) {
                        case 0:
                            colorPredicClassique(x, y, g);
                            break;
                        case 1:
                            colorPredicDN(x, y, g);
                            break;
                        case 2:
                            colorPredicHL(x, y, g);
                            break;
                        default:
                            break;
                    }
                } else {
                    if (jeu.getGrillexy(x, y).estVivante()) {
                        switch (modeJeu) {
                            case 0:
                                g.setColor(Color.BLACK);
                                break;
                            case 1:
                                g.setColor(Color.ORANGE);
                                break;
                            case 2:
                                g.setColor(Color.WHITE);
                                break;
                            case 3:
                                g.setColor(Color.GREEN);
                                break;
                            case 4: // Mode Mutation
                                if (jeu.getGrillexy(x, y).estImmortelle(3)) { // Seuil d'immortalité défini à 3
                                    g.setColor(Color.MAGENTA); // Cellule immortelle en magenta
                                } else {
                                    g.setColor(Color.BLUE); // Autres cellules vivantes en bleu
                                }
                                break;
                            case 5: // Mode Gel
                                if (jeu.getGrillexy(x, y).estGelee()) {
                                    g.setColor(Color.CYAN); // Cellule gelée en cyan
                                } else {
                                    g.setColor(Color.LIGHT_GRAY); // Cellule vivante normale en gris clair
                                }
                                break;
                            default:
                                break;
                        }
                    } else {
                        switch (modeJeu) {
                            case 0:
                                g.setColor(Color.WHITE);
                                break;
                            case 1:
                                g.setColor(Color.BLACK);
                                break;
                            case 2:
                                g.setColor(Color.BLACK);
                                break;
                            case 3:
                                g.setColor(Color.RED);
                                break;
                            case 4: // Mode Mutation
                                if (jeu.getGrillexy(x, y).peutEncoreRenaître(2)) { // Seuil de renaissances défini à 2
                                    g.setColor(Color.YELLOW); // Cellule morte mais pouvant renaître en jaune
                                } else {
                                    g.setColor(Color.DARK_GRAY); // Cellule définitivement morte en gris foncé
                                }
                                break;
                            case 5: // Mode Gel
                                g.setColor(Color.DARK_GRAY); // Cellule morte en gris foncé pour indiquer qu’elle est hors jeu
                                break;
                            default:
                                throw new AssertionError();
                        }
                    }

                }
                g.fillRect(cx, cy, tailleZone, tailleZone);
            }
            g.drawRect(offsetX, offsetY, (int) (jeu.getTailleMaxX() * scale), (int) (jeu.getTailleMaxY() * scale));
        }

    }

    private void colorPredicDN(int x, int y, Graphics g) {
        switch (jeu.getGrillexy(x, y).nombreVoisinesVivantes(jeu)) {
            case 3:
            case 6:
            case 7:
            case 8:
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.GREEN); //vie
                } else {
                    g.setColor(Color.BLUE); //naissance
                }
                break;
            case 4:
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.GREEN); //vie
                } else {
                    g.setColor(Color.WHITE); //meurt
                }
                break;

            default:
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.RED); //meurt
                } else {
                    g.setColor(Color.WHITE); //Vide
                }
                break;
        }
    }

    private void colorPredicClassique(int x, int y, Graphics g) {
        switch (jeu.getGrillexy(x, y).nombreVoisinesVivantes(jeu)) {
            case 2:
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.GREEN); //vie
                } else {
                    g.setColor(Color.WHITE);
                }
                break;
            case 3:
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.GREEN); //vie
                } else {
                    g.setColor(Color.BLUE); //naissance
                }
                break;

            default:
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.RED); //meurt
                } else {
                    g.setColor(Color.WHITE); //Vide
                }
                break;
        }

    }

    private void colorPredicHL(int x, int y, Graphics g) {
        switch (jeu.getGrillexy(x, y).nombreVoisinesVivantes(jeu)) {
            case 2:
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.WHITE);
                }
                break;
            case 3:
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.GREEN); //vie
                } else {
                    g.setColor(Color.BLUE); //naissance
                }
                break;
            case 6:
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.RED); //vie
                } else {
                    g.setColor(Color.BLUE); //naissance
                }
                break;

            default:
                if (jeu.getGrillexy(x, y).estVivante()) {
                    g.setColor(Color.RED); //meurt
                } else {
                    g.setColor(Color.WHITE); //vide
                }
                break;
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
