package OpenGL.some_try;

import java.awt.event.KeyListener;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;

public class SpaceShip implements GLEventListener, KeyListener {
    // Vitesse de déplacement du vaisseau
    private static final float SPEED = 10f;
    // Position du vaisseau
    private float x = 0;



    public void init(GLAutoDrawable drawable) {
        // Récupération du contexte OpenGL
        GL2 gl = drawable.getGL().getGL2();
        // Activation de l'éclairage
        gl.glEnable(GL2.GL_LIGHTING);
        // Définition des propriétés de l'éclairage
        float[] lightAmbient = { 0.2f, 0.2f, 0.2f, 1 };
        float[] lightDiffuse = { 1, 1, 1, 1 };
        float[] lightSpecular = { 1, 1, 1, 1 };
        float[] lightPosition = { 0, 0, 2, 1 };
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, lightAmbient, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, lightDiffuse, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, lightSpecular, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);
        // Activation de la lumière 0
        gl.glEnable(GL2.GL_LIGHT0);
        // Activation du test de profondeur
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

        public void display(GLAutoDrawable drawable) {
            // Récupération du contexte OpenGL
            GL2 gl = drawable.getGL().getGL2();
            // Effacement du tampon de couleur et de profondeur
            gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
            // Sauvegarde de la matrice de vue
            gl.glPushMatrix();
            // Translation du vaisseau
            gl.glTranslatef(x, 0, 0);
            // Dessin du vaisseau
            gl.glBegin(GL2.GL_TRIANGLES);
            // Face avant (jaune)
            gl.glColor3f(1, 1, 0);
            gl.glVertex3f(-0.5f, -0.5f, 0.5f);
            gl.glVertex3f(0.5f, -0.5f, 0.5f);
            gl.glVertex3f(0, 0.5f, 0.5f);
            // Face arrière (bleu)
            gl.glColor3f(0, 0, 1);
            gl.glVertex3f(-0.5f, -0.5f, -0.5f);
            gl.glVertex3f(0, 0.5f, -0.5f);
            gl.glVertex3f(0.5f, -0.5f, -0.5f);
            // Face gauche (vert)
            gl.glColor3f(0, 1, 0);
            gl.glVertex3f(-0.5f, -0.5f, 0.5f);
            gl.glVertex3f(-0.5f, -0.5f, -0.5f);
            gl.glVertex3f(0, 0.5f, 0);
            // Face droite (rouge)
            gl.glColor3f(1, 0, 0);
            gl.glVertex3f(0.5f, -0.5f, 0.5f);
            gl.glVertex3f(0, 0.5f, 0);
            gl.glVertex3f(0.5f, -0.5f, -0.5f);
            gl.glEnd();
            // Restauration de la matrice de vue
            gl.glPopMatrix();
        }

        public void dispose(GLAutoDrawable drawable) {
        }

        public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
//            // Récupération du contexte OpenGL
//            GL2 gl = drawable.getGL().getGL2();
//            // Définition de la zone de dessin
//            gl.glViewport(0, 0, width, height);
//            // Initialisation de la matrice de projection
//            gl.glMatrixMode(GL2.GL_PROJECTION);
//            gl.glLoadIdentity();
//            // Définition de la perspective
//            GLU glu = new GLU();
//            double aspect = (double) width / (double) height;
//            glu.gluPerspective(45, aspect, 0.1, 100);
//            // Initialisation de la matrice de vue
//            gl.glMatrixMode(GL2.GL_MODELVIEW);
//            gl.glLoadIdentity();
        }


    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {

    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        // Déplacement du vaisseau à gauche
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            x -= SPEED;
        }
        // Déplacement du vaisseau à droite
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            x += SPEED;
        }
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {

    }


    public static void main(String[] args) {
        // Création de la fenêtre OpenGL
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);
        // Ajout de l'objet SpaceShip en tant qu'écouteur d'évènements OpenGL et de touches du clavier

        // Création de la fenêtre
        JFrame frame = new JFrame("Space Ship");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.setVisible(true);
        // Création de l'animateur
        FPSAnimator animator = new FPSAnimator(canvas, 60);
        animator.start();
    }
}