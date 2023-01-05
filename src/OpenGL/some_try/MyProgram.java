package OpenGL.some_try;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;

import javax.swing.*;
import java.awt.event.*;

public class MyProgram implements GLEventListener, KeyListener {
    // Vaisseau spatial
    float x = 0;
    // Vitesse de déplacement du vaisseau
    float speed = 100f;

    public static void main(String[] args) {
        // Création de la fenêtre OpenGL
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);
        // Ajout de l'objet MyProgram en tant qu'écouteur d'évènements OpenGL et clavier
        canvas.addGLEventListener(new MyProgram());
        canvas.addKeyListener(new MyProgram());
        // Création de la fenêtre
        JFrame frame = new JFrame("My Program");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(canvas);
        frame.setVisible(true);
    }

    public void init(GLAutoDrawable drawable) {
        // Récupération du contexte OpenGL
        GL2 gl = drawable.getGL().getGL2();
        // Activation de l'éclairage
        gl.glEnable(GL2.GL_LIGHTING);
        // Activation de la lumière 0
        gl.glEnable(GL2.GL_LIGHT0);
        // Activation du test de profondeur
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
//        // Récupération du contexte OpenGL
//        GL2 gl = drawable.getGL().getGL2();
//        // Définition de la zone de dessin
//        gl.glViewport(0, 0, width, height);
    }

    public void display(GLAutoDrawable drawable) {
        // Récupération du contexte OpenGL
        GL2 gl = drawable.getGL().getGL2();
        // Effacement du tampon de couleur et de profondeur
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        // Sauvegarde de la matrice de vue
        gl.glPushMatrix();
        // Translation du vaisseau
        gl.glTranslated(x, 0, 0);
        // Dessin du vaisseau
        gl.glBegin(GL2.GL_TRIANGLES);
        // Face avant (jaune)
        gl.glColor3d(1, 0, 0);
        gl.glVertex3d(-0.5, -0.5, 0.5);
        gl.glVertex3d(0.5, -0.5, 0.5);
        gl.glVertex3d(0, 0.5, 0.5);
        // Face arrière (bleu)
        gl.glColor3d(0, 0, 1);
        gl.glVertex3d(-0.5, -0.5, -0.5);
        gl.glVertex3d(0, 0.5, -0.5);
        gl.glVertex3d(0.5, -0.5, -0.5);
        // Face gauche (vert)
        gl.glColor3d(0, 1, 0);
        gl.glVertex3d(-0.5, -0.5, 0.5);
        gl.glVertex3d(-0.5, -0.5, -0.5);
        gl.glVertex3d(0, 0.5, -0.5);
        gl.glVertex3d(0, 0.5, 0.5);
        // Face droite (rouge)
        gl.glColor3d(1, 0, 0);
        gl.glVertex3d(0.5, -0.5, 0.5);
        gl.glVertex3d(0.5, -0.5, -0.5);
        gl.glVertex3d(0, 0.5, -0.5);
        gl.glVertex3d(0, 0.5, 0.5);
        // Face supérieure (blanc)
        gl.glColor3d(1, 1, 1);
        gl.glVertex3d(-0.5, -0.5, 0.5);
        gl.glVertex3d(-0.5, -0.5, -0.5);
        gl.glVertex3d(0.5, -0.5, -0.5);
        gl.glVertex3d(0.5, -0.5, 0.5);
        gl.glEnd();
        // Restauration de la matrice de vue
        gl.glPopMatrix();
        gl.glFlush();
    }

    public void dispose(GLAutoDrawable drawable) {
    }

    // Fonction appelée lors de la pression d'une touche du clavier
    public void keyPressed(KeyEvent e) {
        // Déplacement du vaisseau vers la droite
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            x += speed;
        }
        // Déplacement du vaisseau vers la gauche
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            x -= speed;
        }
    }

    // Fonction appelée lors de la relâche d'une touche du clavier
    public void keyReleased(KeyEvent e) {
    }

    // Fonction appelée lors de la frappe d'un caractère du clavier
    public void keyTyped(KeyEvent e) {
    }

}