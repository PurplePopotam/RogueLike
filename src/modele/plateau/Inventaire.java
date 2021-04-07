package modele.plateau;

public class Inventaire {

    private Pickup[] inventaire;

    private final int taille = 8;

    public Inventaire(){
        inventaire = new Pickup[taille];
    }

    public Pickup[] getInventaire() {
        return inventaire;
    }

    public Pickup getInventaire(int i) {
        return inventaire[i];
    }

    public void setInventaire(Pickup[] inventaire) {
        this.inventaire = inventaire;
    }

    public int getTaille() {
        return taille;
    }

    public void addItem(Pickup p){

    }
}
