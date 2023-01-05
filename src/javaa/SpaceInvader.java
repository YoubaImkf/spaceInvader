package javaa;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;



public class SpaceInvader implements GLEventListener, KeyListener {

    private float positionX = 0.0f; // position initiale du vaisseau sur l'axe X
    private boolean isShooting = false; // booléen indiquant si le vaisseau est en train de tirer

    public static void main(String[] args) {
        // création du profil OpenGL
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        com.jogamp.opengl.awt.GLCanvas canvas = new GLCanvas(capabilities);
        SpaceInvader spaceInvader = new SpaceInvader();
        canvas.addGLEventListener(spaceInvader);
        canvas.addGLEventListener(spaceInvader);

        JFrame frame = new JFrame("Space Invader");
        frame.getContentPane();
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Création de l'animateur
        FPSAnimator animator = new FPSAnimator(canvas, 60);
        animator.start();

    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        // dessin du vaisseau à sa position actuelle
        gl.glPushMatrix();
        gl.glTranslatef(positionX, 0.0f, 0.0f);
        drawSpaceShip(gl);
        gl.glPopMatrix();

        // si le vaisseau est en train de tirer, dessin du projectile
        if (isShooting) {
            gl.glPushMatrix();
            gl.glTranslatef(positionX, 0.5f, 0.0f);
            drawProjectile(gl);
            gl.glPopMatrix();
        }
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                positionX -= 0.1f; // déplacement vers la gauche
                break;
            case KeyEvent.VK_RIGHT:
                positionX += 0.1f; // déplacement vers la droite
                break;
            case KeyEvent.VK_SPACE:
                isShooting = true; // tir du projectile
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            isShooting = false; // arrêt du tir
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // pas utilisé dans ce code
    }

    // méthode pour dessiner le vaisseau spatial
    private void drawSpaceShip(GL2 gl) {
        GLU glu = new GLU();
        GLUT glut = new GLUT();

// dessin du corps du vaisseau
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex3f(-0.5f, -0.5f, 0.0f);
        gl.glVertex3f(0.5f, 0.0f, 0.0f);
        gl.glVertex3f(-0.5f, 0.5f, 0.0f);
        gl.glEnd();

// dessin de la fusée arrière du vaisseau
        gl.glPushMatrix();
        gl.glTranslatef(-0.5f, 0.0f, 0.0f);
        gl.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        glut.glutSolidCone(0.1f, 0.5f, 20, 20);
        gl.glPopMatrix();
    }

    // méthode pour dessiner le projectile
    private void drawProjectile(GL2 gl) {
        GLU glu = new GLU();
        GLUT glut = new GLUT();

        gl.glColor3f(1.0f, 1.0f, 0.0f); // couleur jaune pour le projectile
        glut.glutSolidCylinder(0.1f, 0.5f, 20, 20);
    }




}
