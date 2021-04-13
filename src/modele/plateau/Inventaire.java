package modele.plateau;

import modele.entites.*;

public class Inventaire {

    private Pickup[] contenu;

    private Jeu jeu;

    private final int taille = 8;

    public Inventaire(Jeu _jeu){
        jeu = _jeu;
        contenu = new Pickup[taille];
        contenu[0] = new Cle(jeu);
        contenu[1] = new Cle(jeu);
        contenu[2] = new Cle(jeu);
    }

    public Pickup[] getContenu() {
        return contenu;
    }

    public Pickup getContenu(int i) {
        return contenu[i];
    }

    public void setContenu(Pickup[] contenu) {
        this.contenu = contenu;
    }

    public int getTaille() {
        return taille;
    }

    public void addItem(Pickup p){
        int i = 0;
        while(contenu[i] != null){
            i++;
        }
        if(i < taille){
            contenu[i] = p;
        }
    }

    public boolean isFull(){
        for (int i = 0; i < taille; i++){
            if(contenu[i] == null){
                return false;
            }
        }
        return true;
    }

    public void removeItem(int i){
        contenu[i] = null;
    }

    public int hasKey(){
        for(int i = 0; i < taille; i++){
            if(contenu[i] instanceof Cle){
                return i;
            }
        }
        return -1;
    }
}

