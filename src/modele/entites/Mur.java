package modele.entites;

import modele.plateau.Jeu;

public class Mur extends EntiteStatique {
    public Mur(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean traversable() {
        return false;
    }

}

