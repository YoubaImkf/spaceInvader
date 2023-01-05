package javaa.Projet_SpaceInvader;

import com.jogamp.opengl.*;
import java.util.ArrayList;

public class Ship {
        float x = 0; // XPosition du vaisseau

        float speed = 0.1f; // Vitesse de déplacement du vaisseau

        int windowWidth; // Largeur de la fenêtre
        private static final int SHOOT_COOLDOWN = 1000;
        private long lastShootTime = 0; // stocke le temps du dernier tir effectué
        ArrayList<Projectile> projectiles = new ArrayList<>(); // Liste des projectiles tirés par le vaisseau

        public Ship(int windowWidth) {
                this.windowWidth = windowWidth;
        }

        public void moveLeft() {
            x -= speed;
            // Limitation de la position en x du vaisseau à l'intérieur de la fenêtre
            if (x < -4.8f) { // car on utilise des coordonnées orthonormales dans main
                x = -4.8f;
            }
        }

        public void moveRight() {
            x += speed;
            // Limitation de la position en x du vaisseau à l'intérieur de la fenêtre
            if (x > 4.8f) {
                x = 4.8f;
            }
        }

        public void shoot() {
            // On récupère le temps actuel
            long currentTime = System.currentTimeMillis();
            // Si le délai de tir est écoulé
            if (currentTime - lastShootTime >= SHOOT_COOLDOWN) {
                // On enregistre le temps du tir
                lastShootTime = currentTime;
                // On ajoute un nouveau projectile à la liste
                projectiles.add(new Projectile(x, -4.0f, 0.1f));
            }

        }

        public void update() {
            // Mise à jour de la position des projectiles
            for (int i = 0; i < projectiles.size(); i++) {
                Projectile p = projectiles.get(i);
                p.update();
                // Suppression des projectiles qui ont quitté l'écran
                if (p.y > 4) {
                    projectiles.remove(i);
                    i--;
                }
            }
        }

        public void draw(GL2 gl) {
            // Dessin du vaisseau
            gl.glColor3f(0, 1, 0);
            gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex2f(x-0.2f, -4.0f);
            gl.glVertex2f(x+0.2f, -4.0f);
            gl.glVertex2f(x+0.2f, -3.9f);
            gl.glVertex2f(x-0.2f, -3.9f);
            gl.glEnd();

            // Dessin des projectiles
            gl.glColor3f(1, 1, 1);
            for (Projectile p : projectiles) {
                p.draw(gl);
            }
        }

}
