package modele.plateau;

public class Cle extends EntiteStatique  {
    public Cle(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean traversable() {
        return true;
    }



}
