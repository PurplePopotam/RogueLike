package modele.plateau;

import modele.entites.Porte;
import modele.entites.PorteFinale;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Niveau {
    private final Jeu jeu;
    private final Salle[][] salles;
    private final String mapNiveau;
    private final int taille = 10;

    public Niveau(Jeu _jeu, String mapNiveau) {
        jeu = _jeu;
        this.mapNiveau = mapNiveau;
        salles = new Salle[taille][taille];
        initialiserSalles();
        jumellerPortes();
    }

    public Salle getSalle(int x, int y) {
        return salles[x][y];
    }

    private void initialiserSalles(){

        //Chargement des salles
        try{
            File map = new File(mapNiveau);
            Scanner myReader = new Scanner(map);
            for(int y = 0; y < taille; y++){
                for(int x = 0; x < taille; x++){
                    if(myReader.hasNext()){
                        String s = myReader.next();
                        switch(s){
                            case "D":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/Debut.txt");
                                salles[x][y].addPorteEast();
                                break;
                            case "FE":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/FinE.txt");
                                salles[x][y].addPorteEast();
                                salles[x][y].addPorteFinaleWest();
                                break;
                            case "FN":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/FinN.txt");
                                salles[x][y].addPorteNorth();
                                salles[x][y].addPorteFinaleeSouth();
                                break;
                            case "FW":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/FinW.txt");
                                salles[x][y].addPorteWest();
                                salles[x][y].addPorteFinaleEast();
                                break;
                            case "FS":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/FinS.txt");
                                salles[x][y].addPorteSouth();
                                salles[x][y].addPorteFinaleNorth();
                                break;
                            case "E":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleE.txt");
                                salles[x][y].addPorteEast();
                                break;
                            case "ES":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleES.txt");
                                salles[x][y].addPorteEast();
                                salles[x][y].addPorteSouth();
                                break;
                            case "N":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleN.txt");
                                salles[x][y].addPorteNorth();
                                break;
                            case "NE":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleNE.txt");
                                salles[x][y].addPorteNorth();
                                salles[x][y].addPorteEast();
                                break;
                            case "NSEW":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleNSEW.txt");
                                salles[x][y].addPorteNorth();
                                salles[x][y].addPorteEast();
                                salles[x][y].addPorteSouth();
                                salles[x][y].addPorteWest();
                                break;
                            case "NS":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleNS.txt");
                                salles[x][y].addPorteNorth();
                                salles[x][y].addPorteSouth();
                                break;
                            case "S":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleS.txt");
                                salles[x][y].addPorteSouth();
                                break;
                            case "W":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleW.txt");
                                salles[x][y].addPorteWest();
                                break;
                            case "WE":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleWE.txt");
                                salles[x][y].addPorteWest();
                                salles[x][y].addPorteEast();
                                break;
                            case "WN":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleWN.txt");
                                salles[x][y].addPorteNorth();
                                salles[x][y].addPorteWest();
                                break;
                            case "WS":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleWS.txt");
                                salles[x][y].addPorteWest();
                                salles[x][y].addPorteSouth();
                                break;
                            case ".":
                                salles[x][y] = null;
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

    private void jumellerPortes(){

        //Jumellage des portes
        // Les bords du plateaux n'ont pas de salles pour faciliter le jumellage
        // Les boucles ne couvrent donc pas les bords du tableau
        Salle s;

        for(int y = 1; y < taille - 1; y++){
            for(int x = 1; x < taille - 1; x++){
                s = salles[x][y];
                if(s != null){
                    if(s.hasNorth()){
                        if(!(s.getNorth() instanceof PorteFinale)){
                            s.getNorth().setJumelle(salles[x][y-1].getSouth());
                        }
                    }
                    if(s.hasSouth()){
                        if(!(s.getSouth() instanceof PorteFinale)){
                            s.getSouth().setJumelle(salles[x][y+1].getNorth());
                        }
                    }
                    if(s.hasWest()){
                        if(!(s.getWest() instanceof PorteFinale)){
                            s.getWest().setJumelle(salles[x-1][y].getEast());
                        }
                    }
                    if(s.hasEast()){
                        if(!(s.getEast() instanceof PorteFinale)){
                            s.getEast().setJumelle(salles[x+1][y].getWest());
                        }
                    }
                }
            }
        }
    }

}
