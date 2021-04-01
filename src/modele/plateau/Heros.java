/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

/**
 * Héros du jeu
 */
public class Heros {
    private int x;
    private int y;

    private char orientation = 's';

    private Jeu jeu;
    private Inventaire inventaire = new Inventaire(20);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getOrientation() {
        return orientation;
    }

    public Heros(Jeu _jeu, int _x, int _y) {
        jeu = _jeu;
        x = _x;
        y = _y;
    }

    public void droite() {
        if(orientation == 'e'){
            if (traversable(x+1, y)) {
                x ++;
            }
        }
        else {
            orientation = 'e';
        }
    }

    public void gauche() {
        if(orientation == 'w'){
            if (traversable(x-1, y)) {
                x --;
            }
        }
        else{
            orientation = 'w';
        }
    }

    public void bas() {
        if(orientation == 's'){
            if (traversable(x, y+1)) {
                y ++;
            }
        }
        else{
            orientation = 's';
        }
    }

    public void haut() {
        if(orientation == 'n'){
            if (traversable(x, y-1)) {
                y --;
            }
        }
        else{
            orientation = 'n';
        }
    }

    private boolean traversable(int x, int y) {

        if (x >0 && x < jeu.SIZE_X && y > 0 && y < jeu.SIZE_Y) {
            return jeu.getEntite(x, y).traversable();
        } else {
            return false;
        }
    }
}
