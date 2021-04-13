package modele.plateau;

import modele.entites.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Niveau {
    private Jeu jeu;
    private Salle[][] salles;
    private String mapNiveau;
    private Porte[] portes;
    private int taille = 10;

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

    public void initialiserSalles(){

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
                                System.out.println("Créé : Salle Début");
                                break;
                            case "F":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/Fin.txt");
                                salles[x][y].addPorteWest();
                                System.out.println("Créé : Salle Fin");
                                break;
                            case "E":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleE.txt");
                                salles[x][y].addPorteEast();
                                System.out.println("Créé : Salle E");
                                break;
                            case "ES":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleES.txt");
                                salles[x][y].addPorteEast();
                                salles[x][y].addPorteSouth();
                                System.out.println("Créé : Salle ES");
                                break;
                            case "N":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleN.txt");
                                salles[x][y].addPorteNorth();
                                System.out.println("Créé : Salle N");
                                break;
                            case "NE":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleNE.txt");
                                salles[x][y].addPorteNorth();
                                salles[x][y].addPorteEast();
                                System.out.println("Créé : Salle NE");
                                break;
                            case "NSEW":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleNSEW.txt");
                                salles[x][y].addPorteNorth();
                                salles[x][y].addPorteEast();
                                salles[x][y].addPorteSouth();
                                salles[x][y].addPorteWest();
                                System.out.println("Créé : Salle NSEW");
                                break;
                            case "NS":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleNS.txt");
                                salles[x][y].addPorteNorth();
                                salles[x][y].addPorteSouth();
                                System.out.println("Créé : Salle NS");
                                break;
                            case "S":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleS.txt");
                                salles[x][y].addPorteSouth();
                                System.out.println("Créé : Salle S");
                                break;
                            case "W":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleW.txt");
                                salles[x][y].addPorteWest();
                                System.out.println("Créé : Salle W");
                                break;
                            case "WE":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleWE.txt");
                                salles[x][y].addPorteWest();
                                salles[x][y].addPorteEast();
                                System.out.println("Créé : Salle WE");
                                break;
                            case "WN":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleWN.txt");
                                salles[x][y].addPorteNorth();
                                salles[x][y].addPorteWest();
                                System.out.println("Créé : Salle WN");
                                break;
                            case "WS":
                                salles[x][y] = new Salle(jeu, "Maps/Salles/SalleWS.txt");
                                salles[x][y].addPorteWest();
                                salles[x][y].addPorteSouth();
                                System.out.println("Créé : Salle WS");
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

    public void jumellerPortes(){

        //Jumellage des portes
        // Les bords du plateaux n'ont pas de salles pour faciliter le jumellage
        // Les boucles ne couvrent donc pas les bords du tableau
        Salle s;
        Salle other;

        for(int y = 1; y < taille - 1; y++){
            for(int x = 1; x < taille - 1; x++){
                s = salles[x][y];
                if(s != null){
                    if(s.hasNorth()){
                        s.getNorth().setJumelle(salles[x][y-1].getSouth());
                    }
                    if(s.hasSouth()){
                        s.getSouth().setJumelle(salles[x][y+1].getNorth());
                    }
                    if(s.hasWest()){
                        s.getWest().setJumelle(salles[x-1][y].getEast());
                    }
                    if(s.hasEast()){
                        s.getEast().setJumelle(salles[x+1][y].getWest());
                    }
                }
            }
        }
    }

}
