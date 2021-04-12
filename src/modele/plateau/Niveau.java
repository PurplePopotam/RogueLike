package modele.plateau;

import modele.entites.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Niveau {
    private Jeu jeu;
    private Salle[] salles;
    private String mapNiveau;
    private Porte[] portes;
    private int taille = 5;

    public Niveau(Jeu _jeu, String mapNiveau) {
        jeu = _jeu;
        this.mapNiveau = mapNiveau;
        salles = new Salle[taille];
    }

    public Salle getSalle(int i) {
        return salles[i];
    }

    public void initialiserSalles(){

        //Chargement des salles
        try{
            File map = new File(mapNiveau);
            Scanner myReader = new Scanner(map);
            for(int i = 0; i < taille; i++){
                if(myReader.hasNext()){
                    String s = myReader.next();
                    switch(s){
                        case "D":
                            salles[i] = new Salle(jeu, "Maps/Debut.txt");
                            salles[i].addPorteEst();
                            break;
                        case "F":
                            salles[i] = new Salle(jeu, "Maps/Fin.txt");
                            salles[i].addPorteWest();
                            break;
                        case "H":
                            salles[i] = new Salle(jeu, "Maps/SalleHorizontale_1.txt");
                            salles[i].addPorteWest();
                            salles[i].addPorteEst();
                            break;
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erreur, la map n'a pas été trouvée.");
            e.printStackTrace();
        }

        //Jumellage des portes
        for(int i = 1; i < taille; i++){
            salles[i].getEntrée().setJumelle(salles[i-1].getSortie());
            salles[i-1].getSortie().setJumelle(salles[i].getEntrée());
        }
    }

}
