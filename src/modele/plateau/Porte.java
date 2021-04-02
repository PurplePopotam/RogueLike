package modele.plateau;

public class Porte extends EntiteStatique {

    private boolean Verouillee = true;

    public Porte(Jeu _jeu) { super(_jeu); }

    public boolean isVerouillee() {
        return Verouillee;
    }

    public void setVerouillee(boolean verouillee) {
        Verouillee = verouillee;
    }

    @Override
    public boolean traversable() {
        return(!Verouillee);
    }


    }



