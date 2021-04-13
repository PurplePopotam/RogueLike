package modele.entites;

import modele.plateau.Jeu;

public class CaseVide extends EntiteStatique {
    private boolean Traversable = false;
    public CaseVide(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean traversable() {
       return Traversable;
    }

    public void setTraversable(boolean is_traversable) {
        Traversable = is_traversable;
    }
}
