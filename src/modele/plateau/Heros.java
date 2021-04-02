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
    private Inventaire inventaire = new Inventaire();

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
            if(jeu.getEntite(x+1,y) instanceof Porte){
                traverserPorte(x+1, y);
            }
            else if (traversable(x+1, y)) {
                x ++;
            }
        }
        else {
            orientation = 'e';
        }
    }

    public void gauche() {
        if(orientation == 'w'){
            if(jeu.getEntite(x-1,y) instanceof Porte){
                traverserPorte(x-1, y);
            }
            else if (traversable(x-1, y)) {
                x --;
            }
        }
        else{
            orientation = 'w';
        }
    }

    public void bas() {
        if(orientation == 's'){
            if(jeu.getEntite(x,y+1) instanceof Porte){
                traverserPorte(x, y+1);
            }
            else if (traversable(x, y+1)) {
                y ++;
            }
        }
        else{
            orientation = 's';
        }
    }

    public void haut() {
        if(orientation == 'n'){
            if(jeu.getEntite(x,y-1) instanceof Porte){
                traverserPorte(x, y-1);
            }
            else if (traversable(x, y-1)) {
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

    private void traverserPorte(int _x, int _y){
        EntiteStatique e = jeu.getEntite(_x,_y);
        if(e instanceof Porte && ((Porte) e).getJumelle() != null){
            Porte dest = ((Porte) e).getJumelle();
            switch (dest.getDirection()){
                case 'n': x = dest.getX(); y = dest.getY() + 1; break;
                case 's': x = dest.getX(); y = dest.getY() - 1; break;
                case 'e': x = dest.getX() - 1; y = dest.getY(); break;
                case 'w': x = dest.getX() + 1; y = dest.getY(); break;
            }
        }
    }
}
