package modele.plateau;

public class Inventaire {

    private Pickup[] contenu;

    private Jeu jeu;

    private final int taille = 8;

    public Inventaire(Jeu _jeu){
        jeu = _jeu;
        contenu = new Pickup[taille];
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

    public boolean addItem(Pickup p){
        for(int i =0; i < taille;i++){
            if(contenu[i] == null){
                contenu[i] = p;
                return true;
            }
        }
        return false;
    }

    public void removeItem(int i){
        contenu[i] = null;
    }
}
