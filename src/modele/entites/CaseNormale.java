package modele.entites;

import modele.plateau.Jeu;

public class CaseNormale extends EntiteStatique {
    public CaseNormale(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean traversable() {
        return true;
    }

}
