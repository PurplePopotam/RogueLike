package modele.plateau;

import modele.entites.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Salle {
    private EntiteStatique[][] grilleEntitesStatiques;

    private Porte south;
    private Porte north;
    private Porte west;
    private Porte east;

    private String map;
    private Jeu jeu;

    public Salle(Jeu _jeu, String map) {
        jeu = _jeu;
        this.map = map;
        grilleEntitesStatiques = new EntiteStatique[jeu.SIZE_X][jeu.SIZE_Y];
        initialisationDesEntites(this.map);
    }

    public EntiteStatique getEntiteStatique(int x, int y){
        return grilleEntitesStatiques[x][y];
    }

    public void addEntiteStatique(EntiteStatique e, int x, int y){
        grilleEntitesStatiques[x][y] = e;
    }

    public void removeEntiteStatique(int x, int y){
        grilleEntitesStatiques[x][y] = null;
    }

    public void initialisationDesEntites(String path){
        double tirage = 0f;
        /*Permet de lire une carte depuis un fichier .txt,
          la génération de niveau est donc pour le moment très simplifiée.
          ATTENTION la carte doit faire la même taille que le jeu (SIZE_X,SIZE_Y)
          sinon le comportement est complètement erronné.
         */

        try{
            File map = new File(path);
            Scanner myReader = new Scanner(map);

            for(int y = 0; y < jeu.SIZE_Y;y++) {
                for (int x = 0; x < jeu.SIZE_X; x++) {
                    if (myReader.hasNext()) {

                        String s = myReader.next();

                        switch (s) {
                            case "_":
                                tirage = Math.random();

                                if(tirage < jeu.spawnRateCoffre){
                                    addEntiteStatique(new Coffre(this.jeu), x, y);
                                    Coffre c = (Coffre)getEntiteStatique(x,y);
                                    c.initialiserContenu();
                                    break;
                                }
                                else if(tirage > jeu.spawnRateCoffre && tirage < jeu.spawnRateCoffre + jeu.spawnRateCapsule){
                                    addEntiteStatique(new Capsule(this.jeu), x, y); break;
                                }
                                else if(tirage > jeu.spawnRateCoffre + jeu.spawnRateCapsule && tirage < jeu.spawnRateCapsule + jeu.spawnRateCle + jeu.spawnRateCoffre){
                                    addEntiteStatique(new Cle(this.jeu), x, y); break;
                                }
                                else{
                                    addEntiteStatique(new CaseNormale(this.jeu), x, y);
                                    break;
                                }
                            case "M":
                                addEntiteStatique(new Mur(this.jeu), x, y);
                                break;
                            case "P":
                                addEntiteStatique(new Porte(this.jeu, x, y), x, y);
                                break;
                            case "A":
                                addEntiteStatique(new Coffre(this.jeu), x, y);
                                Coffre c = (Coffre)jeu.getEntite(x,y);
                                c.initialiserContenu();
                                break;
                            case "B":
                                addEntiteStatique(new Cle(this.jeu), x, y);
                                break;
                            case "C":
                                addEntiteStatique(new Capsule(this.jeu), x, y);
                                break;
                            case "D":
                                addEntiteStatique(new DalleUsageUnique(this.jeu), x, y);
                                break;
                            case "V":
                                addEntiteStatique(new CaseVide(this.jeu), x, y);
                                break;
                            case ".":
                                grilleEntitesStatiques[x][y] = null;
                                break;
                        }
                    }

                }
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erreur, la map n'a pas été trouvée.");
            e.printStackTrace();
        }
    }

    public void addPorteWest(){
        grilleEntitesStatiques[1][9] = new Porte(jeu, 1, 9);
        ((Porte)grilleEntitesStatiques[1][9]).setDirection('w');
        west = (Porte)grilleEntitesStatiques[1][9];
    }

    public void addPorteEast(){
        grilleEntitesStatiques[28][9] = new Porte(jeu, 28, 9);
        ((Porte)grilleEntitesStatiques[28][9]).setDirection('e');
        east = (Porte)grilleEntitesStatiques[28][9];
    }

    public void addPorteNorth(){
        grilleEntitesStatiques[14][1] = new Porte(jeu, 14, 1);
        ((Porte)grilleEntitesStatiques[14][1]).setDirection('n');
        north = (Porte)grilleEntitesStatiques[14][1];
    }

    public void addPorteSouth(){
        grilleEntitesStatiques[14][18] = new Porte(jeu, 14, 18);
        ((Porte)grilleEntitesStatiques[14][18]).setDirection('s');
        south = (Porte)grilleEntitesStatiques[14][18];
    }

    public Porte getSouth() {
        return south;
    }

    public void setSouth(Porte south) {
        this.south = south;
    }

    public Porte getNorth() {
        return north;
    }

    public void setNorth(Porte north) {
        this.north = north;
    }

    public Porte getWest() {
        return west;
    }

    public void setWest(Porte west) {
        this.west = west;
    }

    public Porte getEast() {
        return east;
    }

    public void setEast(Porte east) {
        this.east = east;
    }

    public boolean hasEast(){
        return(east != null);
    }

    public boolean hasNorth(){
        return(north != null);
    }

    public boolean hasWest(){
        return(west != null);
    }

    public boolean hasSouth(){
        return(south != null);
    }
}
