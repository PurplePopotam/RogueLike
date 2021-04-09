package modele.plateau;

public class Coffre extends Pickup {

    private Pickup[] contenu;
    private final int taille = 10;

    public Coffre(Jeu _jeu) { super(_jeu); contenu = new Pickup[taille];}

    @Override
    public boolean traversable() {
        return false;
    }

    public void initialiserContenu(){
        /*TO DO : Faire en sorte que le contenu soit aléatoire*/
        /*Pour le moment toujours le même contenu.*/
        contenu[0] = new Cle(jeu);
        contenu[1] = new Capsule(jeu);
        contenu[3] = new Coffre(jeu);
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
