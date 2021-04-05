package modele.plateau;

public class Inventaire {

    private Pickup[] inventaire;
    private final int taille = 10;

    public Pickup[] getInventaire() {
        return inventaire;
    }

    public void setInventaire(Pickup[] inventaire) {
        this.inventaire = inventaire;
    }

    public int getTaille() {
        return taille;
    }
}
