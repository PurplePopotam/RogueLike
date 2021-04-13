package modele.entites;

import modele.plateau.Jeu;

public class Coffre extends Pickup {

    private final Pickup[] contenu;
    private final int taille = 5;
    private final double spawnRateCle = 0.2;
    private final double spawnRateCoffre = 0.05;
    private final double spawnRateCapsule = 0.2;

    public Coffre(Jeu _jeu) { super(_jeu); contenu = new Pickup[taille];}

    @Override
    public boolean traversable() {
        return false;
    }

    public void initialiserContenu(){

        double tirage;

        for(int i = 0; i < taille; i++){
            tirage = Math.random();
            if(tirage < spawnRateCoffre){
                contenu[i] = new Coffre(jeu);
            }
            else if(tirage > spawnRateCoffre && tirage < spawnRateCapsule + spawnRateCoffre){
                contenu[i] = new Capsule(jeu);
            }
            else if(tirage > spawnRateCoffre + spawnRateCapsule && tirage < spawnRateCapsule + spawnRateCle + spawnRateCoffre){
                contenu[i] = new Cle(jeu);
            }
        }
    }

    public Pickup getContenu(int i){
        return contenu[i];
    }

    public boolean isEmpty(){
        for(int i = 0; i < taille; i++){
            if(contenu[i] != null){
                return false;
            }
        }
        return true;
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

    public void removeItem(int i){
        contenu[i] = null;
    }

}
