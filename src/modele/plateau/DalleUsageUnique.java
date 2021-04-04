package modele.plateau;

public class DalleUsageUnique extends EntiteStatique {

    boolean enflammee = false;

    public DalleUsageUnique(Jeu _jeu){super(_jeu);}

    @Override
    public boolean traversable() {
        return !enflammee;
    }

    public boolean isEnflammee() {
        return enflammee;
    }

    public void setEnflammee(boolean enflammee) {
        this.enflammee = enflammee;
    }
}
