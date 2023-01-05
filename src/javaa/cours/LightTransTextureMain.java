package javaa.cours;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;


public class LightTransTextureMain extends GLCanvas implements GLEventListener {

    private float xrot, yrot, zrot; // variables for the rotations
    private int texture;
    private int texture2;

    private final float[] lightAmbient = { 0.5f, 0.5f, 0.5f, 1.0f };
    private final float[] lightDiffuse = { 1.0f, 1.0f, 1.0f, 1.0f };
    private final float[] lightPosition = { 0.0f, 0.0f, 2.0f, 1.0f };
    private boolean light;
    private boolean blend;


    public LightTransTextureMain() {
        this.addGLEventListener(this);
    }

    private static void keyBindings(JPanel p, final LightTransTextureMain r) {

        ActionMap actionMap = p.getActionMap();
        InputMap inputMap = p.getInputMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_L, 0), "L");
        actionMap.put("L", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent drawable) { r.light = !r.light;
            }}); // r.light?false:true

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0), "B");
        actionMap.put("B", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent drawable) { r.blend = !r.blend;
            }}); // r.blend?false:true
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {

        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL2.GL_SMOOTH);
        // vider les valeurs du z-buffer
        gl.glClearDepth(1.0f);
        // activer le test de profondeur
        gl.glEnable(GL2.GL_DEPTH_TEST);
        // choisir le type de test de profondeur
        gl.glDepthFunc(GL2.GL_LEQUAL);
        // choix de la meilleur correction de perspective
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

        // definition de la texture à appliquer
        gl.glEnable(GL2.GL_TEXTURE_2D);
        try {
            // 1. lire une image et l'assigner à une texture
            File im = new File("src/res/Glass_Texture_25.jpg");
            Texture t = TextureIO.newTexture(im, true);
            texture = t.getTextureObject(gl);

            File im2 = new File("src/res/texture.png");
            Texture t2 = TextureIO.newTexture(im2, true);
            texture2 = t2.getTextureObject(gl);

            // 2. spécifier les paramètres de texture mapping
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
            //gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
            gl.glEnable(GL2.GL_TEXTURE_2D);

            // lighting
            gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, this.lightAmbient,0);
            gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, this.lightDiffuse,0);
            gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, this.lightPosition,0);

            gl.glEnable(GL2.GL_LIGHT1);
            gl.glEnable(GL2.GL_LIGHTING);

            // blend
            gl.glColor4f(1f, 1f, 1f, 0.5f);//50% Alpha
            gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE);

            this.light = true;
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
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);     // Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        gl.glTranslatef(0f, 0f, -7.0f);
        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(yrot, 1.0f, 1.0f, 0.0f);
        gl.glRotatef(zrot, 1.0f, 0.0f, 1.0f);

        gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);

        if (light) {
            gl.glEnable(GL2.GL_LIGHTING);
        } else {
            gl.glDisable(GL2.GL_LIGHTING);
        }

        if(blend){
            gl.glEnable(GL2.GL_BLEND);
            gl.glDisable(GL2.GL_DEPTH_TEST);
        }else{
            gl.glDisable(GL2.GL_BLEND);
            gl.glEnable(GL2.GL_DEPTH_TEST);
        }

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

        gl.glBindTexture(GL2.GL_TEXTURE_2D, texture2);

        gl.glScalef(0.5f, 0.5f, 0.5f);
        gl.glTranslatef(0.0f, 4.0f, 0.0f);
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

        gl.glFlush();
        xrot += .3f;
        yrot += .2f;
        zrot += .4f;

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


    // MAIN
    public static void main(String[] args) {
        LightTransTextureMain canvas = new LightTransTextureMain();
        canvas.setPreferredSize(new Dimension(800, 600));

        final FPSAnimator animator = new FPSAnimator(canvas, 300,true );

        final JFrame frame = new JFrame();
        frame.getContentPane().add(canvas);
        frame.setTitle("Java - OpenGL");
        frame.pack();
        frame.setLocationRelativeTo(null); // center window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // add control buttons to JPanel
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(0, 0));
        frame.add(p, BorderLayout.SOUTH);

        keyBindings(p, canvas);

        animator.start();
    }

}