package modele.plateau;

public class DalleUsageUnique extends EntiteStatique {

    private static boolean enflammee = false;

    public DalleUsageUnique(Jeu _jeu){super(_jeu);}

    @Override
    public boolean traversable() {
        if(!enflammee){
            enflammee = true;
            return true;
        }
        else return false;
    }

    public static boolean isEnflammee() {
        return enflammee;
    }

    public static void setEnflammee(boolean enflammee) {
       enflammee = enflammee;
    }
}
