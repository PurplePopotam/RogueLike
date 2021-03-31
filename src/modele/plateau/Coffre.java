package modele.plateau;

public class Coffre extends EntiteStatique {
    public Coffre(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean traversable() {
        return true;
    }



}
