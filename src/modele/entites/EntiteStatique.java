package modele.entites;

import modele.plateau.Jeu;

/**
 * Ne bouge pas (murs...)
 */
public abstract class EntiteStatique {
    protected Jeu jeu;

    public EntiteStatique(Jeu _jeu) {
        jeu = _jeu;
    }

    public abstract boolean traversable();

}