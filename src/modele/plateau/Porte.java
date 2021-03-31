package modele.plateau;

public class Porte extends EntiteStatique {
    private boolean Verouillee = true;
    public Porte(Jeu _jeu) { super(_jeu); }

    @Override
    public boolean traversable() {

        if (Verouillee) {

            return false;
        }
        else{

            return true;

        }
    }


    }



