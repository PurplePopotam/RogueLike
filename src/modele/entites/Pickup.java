package modele.entites;

import modele.plateau.Jeu;

public abstract class Pickup extends EntiteStatique {

    private boolean ramassé = false;

    public Pickup(Jeu _jeu){super(_jeu);}


}
