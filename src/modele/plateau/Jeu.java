/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import java.util.Observable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Jeu extends Observable implements Runnable {

    public static final int SIZE_X = 40;    //J'ai augmenté la taille du niveau
    public static final int SIZE_Y = 20;

    private int pause = 200; // période de rafraichissement

    private Heros heros;

    private EntiteStatique[][] grilleEntitesStatiques = new EntiteStatique[SIZE_X][SIZE_Y];

    public Jeu() {
        initialisationDesEntites("Images/Map.txt");
    }

    public Heros getHeros() {
        return heros;
    }

    public EntiteStatique[][] getGrille() {
        return grilleEntitesStatiques;
    }

	public EntiteStatique getEntite(int x, int y) {
		if (x < 0 || x >= SIZE_X || y < 0 || y >= SIZE_Y) {
			// L'entité demandée est en-dehors de la grille
			return null;
		}
		return grilleEntitesStatiques[x][y];
	}

    private void initialisationDesEntites(String path) {

        /*Permet de lire une carte depuis un fichier .txt,
          la génération de niveau est donc pour le moment très simplifiée.
          ATTENTION la carte doit faire la même taille que le jeu (SIZE_X,SIZE_Y)
          sinon le comportement est complètement erronné.
         */

        try{
            File map = new File(path);
            Scanner myReader = new Scanner(map);

            for(int y = 0; y < SIZE_Y;y++) {
                for (int x = 0; x < SIZE_X; x++) {
                    if (myReader.hasNext()) {

                        String s = myReader.next();

                        switch (s) {
                            case "_":
                                addEntiteStatique(new CaseNormale(this), x, y);
                                break;
                            case "M":
                                addEntiteStatique(new Mur(this), x, y);
                                break;
                            case "P":
                                addEntiteStatique(new Porte(this), x, y);
                                break;
                            case "A":
                                addEntiteStatique(new Coffre(this), x, y);
                                break;
                            case "B":
                                addEntiteStatique(new Cle(this), x, y);
                                break;
                            case "C":
                                addEntiteStatique(new Capsule(this), x, y);
                                break;
                            case ".":
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

        heros = new Heros(this, 10, 4);
    }

    public void start() {
        new Thread(this).start();
    }

    public void run() {

        while(true) {

            setChanged();
            notifyObservers();

            try {
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


    private void addEntiteStatique(EntiteStatique e, int x, int y) {
        try {
            grilleEntitesStatiques[x][y] = e;
        }
        catch (ArrayIndexOutOfBoundsException ex){
            System.out.println("Entité en dehors du terrain");
        }
    }

}
