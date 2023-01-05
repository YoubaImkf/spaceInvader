package javaa.cours;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;

public class MainTest extends GLCanvas implements GLEventListener {

    private float r_speed1, r_speed2 , r_speed3;

    public MainTest() {
        this.addGLEventListener(this);
    }

    @Override // met en place des états de depart
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

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override // spécifie la géometrie, les objets et la transformation
    public void display(GLAutoDrawable glAutoDrawable) {
        // for triangle
//        GL2 gl = glAutoDrawable.getGL().getGL2();
//        // Initialisation des états
//        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
//        gl.glLoadIdentity();
        // Dessin d’un triangle
//        gl.glBegin(GL2.GL_TRIANGLES); // draw using triangles
//        gl.glColor3f(0.9f, 0.1f, 0.1f);
//        gl.glVertex3f(0.0f, 1.0f, -6.0f);
//        gl.glVertex3f(-1.0f, -1.0f, -6.0f);
//        gl.glVertex3f(1.0f, -1.0f, -6.0f);
//        gl.glEnd();
//        // second triangle
//        gl.glBegin(GL2.GL_TRIANGLES); // draw using triangles
//        gl.glColor3f(0.1f, 0.1f, 0.9f);
//        gl.glVertex3f(0.0f, -1.5f, -6.0f);
//        gl.glVertex3f(-1.0f, 0.5f, -6.0f);
//        gl.glVertex3f(1.0f, 0.5f, -6.0f);
//        gl.glEnd();

        // pour 3D
        GL2 gl = glAutoDrawable.getGL().getGL2();
        // Initialisation des Ã©tats
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);     // Clear The Screen And The Depth Buffer
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glLoadIdentity();

        // mon carré
//        gl.glBegin(GL2.GL_QUADS);
//        gl.glColor3f(0.9f, 0.1f, 0.1f);     // ordre importe
//        gl.glVertex3f(-1.0f, 1.0f, -6.0f);  // top left corner
//        gl.glVertex3f(1.0f, 1.0f, -6.0f);   // top right corner
//        gl.glVertex3f(1.0f, -1.0f, -6.0f);  // bottom right corner
//        gl.glVertex3f(-1.0f, -1.0f, -6.0f); // bottom left corner

        gl.glEnd();
            gl.glPushMatrix();
                gl.glTranslatef(0.0f, -8.0f, -35.0f); // position
                    gl.glRotatef(r_speed1, 0.0f, 1.0f, 0.0f);
                    gl.glScaled(4.0f, 4.0f, 4.0f);
                    drawCube(gl);
                    gl.glPushMatrix();
                    gl.glTranslatef(0.0f, 1.8f, 0.0f);
                    gl.glRotatef(r_speed2 + 30.0f, 0.0f, 1.0f, 0.0f);
                    gl.glScalef(0.8f, 0.8f, 0.8f);
                    drawCube(gl);
                    gl.glPushMatrix();
                    gl.glTranslatef(0.0f, 1.5f, 0.0f);
                    gl.glRotatef(r_speed3 + 30.0f, 0.0f, 1.0f, 0.0f);
                    gl.glScalef(0.5f, 0.5f, 0.5f);
                    drawCube(gl);
                gl.glPopMatrix();
            gl.glPopMatrix();
        gl.glPopMatrix();

        gl.glFlush();
        r_speed1 += 0.2f;
        r_speed2 -= 0.5f;
        r_speed3 += 0.8f;
    }

    @Override // gere la transformations de vue (model view)
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        GLU glu = GLU.createGLU(gl); // glu api

        float aspect = (float)width / height;
        // Set the view port (display area)
        gl.glViewport(0, 0, width, height);
        // Setup perspective projection,
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0, aspect, 0.1, 100.0);
        // Enable the model-view transform
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); // reset
    }

    // square 4x6
    public void drawCube(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);
        // Front
        gl.glColor3d(0.184314, 0.184314, 0.309804);
        gl.glVertex3d(-1, -1, 1);
        gl.glVertex3d(1, -1, 1);
        gl.glVertex3d(1, 1, 1);
        gl.glVertex3d(-1, 1, 1);
        // Back
        gl.glColor3d(0.137255, 0.137255, 0.556863);
        gl.glVertex3d(-1, -1, -1);
        gl.glVertex3d(1, -1, -1);
        gl.glVertex3d(1, 1, -1);
        gl.glVertex3d(-1, 1, -1);
        // Left
        gl.glColor3d(0.258824, 0.258824, 0.435294);
        gl.glVertex3d(-1, -1, -1);
        gl.glVertex3d(-1, -1, 1);
        gl.glVertex3d(-1, 1,  1);
        gl.glVertex3d(-1, 1, -1);
        // Right
        gl.glColor3d(0.196078, 0.8, 0.6);
        gl.glVertex3d(1, -1, -1);
        gl.glVertex3d(1, -1, 1);
        gl.glVertex3d(1, 1,  1);
        gl.glVertex3d(1, 1, -1);
        // Bottom
        gl.glColor3d(0.22, 0.69, 0.87);
        gl.glVertex3d(-1, -1, 1);
        gl.glVertex3d(1, -1, 1);
        gl.glVertex3d(1, -1, -1);
        gl.glVertex3d(-1, -1, -1);
        // Up
        gl.glColor3d(0.372549, 0.623529, 0.623529);
        gl.glVertex3d(-1, 1, 1);
        gl.glVertex3d(1, 1, 1);
        gl.glVertex3d(1, 1, -1);
        gl.glVertex3d(-1, 1, -1);
        gl.glEnd();
    }

    public static void main(String[] args) {
        // GLCanvas canvas = new GLCanvas(); // as we implement we can do like through
        GLCanvas canvas = new MainTest();
        canvas.setPreferredSize(new Dimension(800, 600));

        final JFrame frame = new JFrame();
        final FPSAnimator animator = new FPSAnimator(canvas, 300, true);

        frame.setTitle("JOGL");
        frame.setSize(new Dimension(800, 600));
        frame.getContentPane().add(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // center window
        frame.pack(); // force fit size
        frame.setVisible(true);

        animator.start();
    }

}

