package javaa.SpaceInvaderFolder;

import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;
import com.jogamp.opengl.util.FPSAnimator;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Main extends JFrame implements GLEventListener, KeyListener {

    public int width = 800;
    public int height = 600;
    GLCanvas canvas = new GLCanvas(); // Canvas OpenGL
    Ship ship = new Ship(width); // Vaisseau
    ArrayList<Monster> monsters = new ArrayList<>(); // Liste de monstres
    Score score = new Score();


    public Main() {
        // Ajout du canvas à la fenêtre
        this.add(canvas);
        // Configuration de la fenêtre
        this.setSize(width, height);
        this.setTitle("Space Invaders");
        this.setLocationRelativeTo(null); // center the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setPreferredSize(new Dimension(width, height));

        // Ajout de l'objet Main en tant qu'écouteur d'évènements OpenGL et de clavier
        canvas.addGLEventListener(this);
        canvas.addKeyListener(this);

        // Création de l'animateur
        FPSAnimator animator = new FPSAnimator(canvas, 60);
        animator.start();

    }

    // Fonction appelée lors de l'initialisation du contexte OpenGL
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // vider les valeurs du z-buffer
        gl.glClearDepth(1.0f);
        // activer le test de profondeur
        gl.glEnable(GL2.GL_DEPTH_TEST);
        // choisir le type de test de profondeur
        gl.glDepthFunc(GL2.GL_LEQUAL);
        // choix de la meilleur correction de perspective
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
    }

    // Fonction appelée lorsque la taille de la fenêtre est modifiée
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // Récupération du contexte OpenGL
        GL2 gl = drawable.getGL().getGL2();
        // Mise à jour de la matrice de projection
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1, 1, -1, 1, -1, 1);
        // Mise à jour de la matrice de vue
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    // Fonction de rendu
    public void display(GLAutoDrawable drawable) {
        // Récupération du contexte OpenGL
        GL2 gl = drawable.getGL().getGL2();
        // Effacement du framebuffer
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        // Réinitialisation de la matrice de projection
        gl.glLoadIdentity();
        // Définition de la perspective
        gl.glFrustum(-1, 1, -1, 1, 1, 5);
        // Translation de la scène
        gl.glTranslatef(0, 0, -5);

        score.draw(gl);

        // vaisseau
        ship.update();
        ship.draw(gl);

        // Mise à jour et dessin des monstres
        for (int i=0; i<monsters.size(); i++) {
            Monster monster = monsters.get(i);
            monster.update();
            monster.draw(gl);

            // Vérifier les collisions avec les projectiles
            for (int j = 0; j < ship.projectiles.size(); j++) {
                Projectile projectile = ship.projectiles.get(j);
                if (projectile.checkCollision(monster)) {


                    monsters.remove(i);
                    ship.projectiles.remove(j);

                    score.increase();

                    break; // On ne vérifie pas les autres projectiles pour ce monstre
                }
            }
        }


        // Création de nouveaux monstres
        if (Math.random() < 0.01) {
            monsters.add(new Monster((float)Math.random() * 5 - 1, 4));
        }

    }

    public void dispose(GLAutoDrawable drawable) {
        // nothing
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                // Déplacement du vaisseau vers la gauche
                ship.moveLeft();
                break;

            case KeyEvent.VK_RIGHT:
                // Déplacement du vaisseau vers la droite
                ship.moveRight();
                break;

            case KeyEvent.VK_SPACE:
                // Tir d'un projectile
                ship.shoot();
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }


    /*
    / LAUNCH THE GAME
    */
    public static void main(String[] args) {
        // Création de la fenêtre
        Main window = new Main();
        // Affichage de la fenêtre
        window.setVisible(true);
    }


}
