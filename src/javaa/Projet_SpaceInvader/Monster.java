package javaa.Projet_SpaceInvader;

import com.jogamp.opengl.GL2;

public class Monster {
    float x, y, speed;
    public Monster(float x, float y) {
        this.x = x;
        this.y = y;
        this.speed = 0.005f;
    }

    public void update() {
        y -= speed;
    }

    public void draw(GL2 gl) {
        gl.glColor3f(1, 0, 0);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3f(1, 1, 1);
        gl.glVertex2f(x-0.1f, y-0.1f);
        gl.glVertex2f(x+0.1f, y-0.1f);
        gl.glVertex2f(x+0.1f, y+0.1f);
        gl.glVertex2f(x-0.1f, y+0.1f);
        gl.glEnd();
    }
}