package modele.plateau;

public class Capsule extends Pickup {
    public Capsule(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean traversable() {
        return true;
    }
}
