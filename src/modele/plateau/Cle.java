package modele.plateau;

public class Cle extends Pickup {
    public Cle(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean traversable() {
        return true;
    }

}
