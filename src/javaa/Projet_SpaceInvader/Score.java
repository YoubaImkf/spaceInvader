package javaa.Projet_SpaceInvader;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class Score {

    private int score = 0; // Score actuel

    public void increase() {
        score += 1;
    }

    public void draw(GL2 gl) {
        GLUT glut = new GLUT();
        // Configuration de l'affichage du texte
        gl.glColor3f(1, 1, 1);
        gl.glRasterPos2f(3.8f, 4.5f);
        // Affichage du score en chaîne de caractères
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Score : " + score);
    }

}
