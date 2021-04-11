package modele.plateau;

public class Coffre extends Pickup {

    private Pickup[] contenu;
    private final int taille = 5;
    private final double spawnRateCle = 0.3f;
    private final double spawnRateCoffre = 0.1f;
    private final double spawnRateCapsule = 0.2f;

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

    public void ajouter(Pickup p){
        for(int i = 0; i < taille; i++){
            if(contenu[i] == null){
                contenu[i] = p;
            }
        }
    }

    public void enlever(int i){
        contenu[i] = null;
    }
}
