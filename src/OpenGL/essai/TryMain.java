package OpenGL.essai;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TryMain extends GLCanvas implements GLEventListener {

    private float xrot, yrot, zrot; // variables for the rotations
    int spaceshipX = 200, spaceshipY = 70;
    private int texture;

    public TryMain() {
        this.addGLEventListener(this);
    }

    @Override
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

        try {
            // 1. lire une image et l'assigner à une texture
            File im = new File("src/res/InvaderShip.png");
            Texture t = TextureIO.newTexture(im, true);
            texture = t.getTextureObject(gl);

            // 2. spécifier les paramètres de texture mapping
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
            gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);

            gl.glEnable(GL2.GL_TEXTURE_2D);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        // Initialisation des états
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);     // Clear The Screen And The Depth Buffer
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glLoadIdentity();

        gl.glTranslatef(0f, 0f, -7.0f);
        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(yrot, 1.0f, 1.0f, 0.0f);
        gl.glRotatef(zrot, 1.0f, 0.0f, 1.0f);

        gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);

        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3d(-1, -1, 1);
        gl.glVertex3d(1, -1, 1);
        gl.glVertex3d(1, 1, 1);
        gl.glVertex3d(-1, 1, 1);
        gl.glEnd();

//        drawCube3d(gl);
//        drawCube(gl);

        gl.glFlush();
        xrot += .3f;
        yrot += .2f;
        zrot += .4f;


    }

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


    public void drawCube3d(GL2 gl){
        gl.glBegin(GL2.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);
        // Back Face
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);
        // Top Face
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);
        // Bottom Face
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);
        // Right face
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);
        // Left Face
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
        gl.glEnd();
    }

    public void drawCube(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);
        // Front
        // red , green , blue
        gl.glColor3d(0, 0, 1);
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



    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        GLU glu = GLU.createGLU(gl);

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

    public static void main(String[] args) {
        GLCanvas canvas = new TryMain();
        canvas.setPreferredSize(new Dimension(800, 600));

        // add animator to set the frame rate
//        final FPSAnimator animator = new FPSAnimator(canvas, 300,true );

        final JFrame frame = new JFrame();
        frame.getContentPane().add(canvas);
        frame.setTitle("Ex 3 - Texture Cubes");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // center window
        frame.setVisible(true);

//        animator.start();
    }
}