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

        /*Work in Progress*/
        /*
        try{
            File map = new File(path);
            Scanner myReader = new Scanner(map);

            for(int x = 0; x < SIZE_X;x++) {
                for (int y = 0; y < SIZE_Y; y++) {
                    if (myReader.hasNext()) {
                        switch (myReader.next()) {
                            case "_":
                                addEntiteStatique(new CaseNormale(this), y, x);
                                break;
                            case "M":
                                addEntiteStatique(new Mur(this), y, x);
                                break;
                            case "H":
                                heros = new Heros(this, y, x);
                                break;
                            case "P":
                                addEntiteStatique(new Coffre(this), y, x);
                                break;
                            case "A":
                                addEntiteStatique(new Coffre(this), y, x);
                                break;
                            case "B":
                                addEntiteStatique(new Cle(this), y, x);
                                break;
                            case "C":
                                addEntiteStatique(new Capsule(this), y, x);
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
        */


        heros = new Heros(this, 4, 4);



        // murs extérieurs horizontaux
        for (int x = 0; x < 20; x++) {
            addEntiteStatique(new Mur(this), x, 0);
            addEntiteStatique(new Mur(this), x, 9);
        }

        // murs extérieurs verticaux
        for (int y = 1; y < 9; y++) {
            addEntiteStatique(new Mur(this), 0, y);
            addEntiteStatique(new Mur(this), 19, y);
        }

        addEntiteStatique(new Mur(this), 2, 6);
        addEntiteStatique(new Mur(this), 3, 6);

        // Les pickups
        addEntiteStatique(new Cle(this), 1, 1);
        addEntiteStatique(new Coffre(this), 3, 5);
        addEntiteStatique(new Capsule(this), 10, 3);
        addEntiteStatique(new Porte(this), 19, 4);

        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if (grilleEntitesStatiques[x][y] == null) {
                    grilleEntitesStatiques[x][y] = new CaseNormale(this);
                }
            }
        }


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
        grilleEntitesStatiques[x][y] = e;
    }

}
