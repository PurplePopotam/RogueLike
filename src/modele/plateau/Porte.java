package modele.plateau;

public class Porte extends EntiteStatique {

    private boolean Verouillee = false;
    private Porte jumelle;
    private char direction = 'e';
    private int x;
    private int y;

    public Porte(Jeu _jeu, int _x, int _y) { super(_jeu); jumelle = null; x = _x; y = _y;}

    public boolean isVerouillee() {
        return Verouillee;
    }

    public void setVerouillee(boolean verouillee) {
        Verouillee = verouillee;
    }

    public void setJumelle(Porte jumelle) {
        this.jumelle = jumelle;
    }

    @Override
    public boolean traversable() {
        return(!Verouillee);
    }

    public Porte getJumelle() {
        return jumelle;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

}



