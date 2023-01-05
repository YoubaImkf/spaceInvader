package javaa.Projet_SpaceInvader;


import com.jogamp.opengl.GL2;

public class Projectile {
    float x, y, speed;
    public Projectile(float x, float y, float speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void update() {
            y += speed;
        }

    public boolean checkCollision(Monster monster) {
        // on vérifie si la distance entre le centre du projectile et du monstre est inférieure à leur rayon respectif
        float dx = x - monster.x;
        float dy = y - monster.y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < 0.2f; // rayon du projectile : 0.1f, rayon du monstre : 0.1f
    }


    public void draw(GL2 gl) {
         gl.glBegin(GL2.GL_QUADS);
         gl.glVertex2f(x - 0.025f, y - 0.1f);
         gl.glVertex2f(x + 0.025f, y - 0.1f);
         gl.glVertex2f(x + 0.025f, y + 0.1f);
         gl.glVertex2f(x - 0.025f, y + 0.1f);
         gl.glEnd();
    }

}
