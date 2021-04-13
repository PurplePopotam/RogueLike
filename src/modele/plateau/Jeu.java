/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.entites.*;

import java.util.Observable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Jeu extends Observable implements Runnable {

    public static final int SIZE_X = 30;    //On règle ici la taille maximum des salles
    public static final int SIZE_Y = 20;
    public static final int nbNiveau = 1;
    private int pause = 200; // période de rafraichissement

    public final double spawnRateCoffre = 0.004;
    public final double spawnRateCle = 0.004;
    public final double spawnRateCapsule = 0.01;

    private Heros heros;

    private Niveau[] niveaux;
    private int[] indSalleCourante = new int[2];
    private int indNiveauCourant = 0;

    private EntiteStatique[][] grilleEntitesStatiques = new EntiteStatique[SIZE_X][SIZE_Y];

    public Jeu() {
        indSalleCourante[0] = 1;
        indSalleCourante[1] = 1;
        niveaux = new Niveau[1];
        niveaux[0] = new Niveau(this, "Maps/Niveaux/Niveau_1.txt");
        chargerSalle(niveaux[0].getSalle(1, 1));
        placerHeros(15, 9);
    }

    public Heros getHeros() {
        return heros;
    }

    public int getIndSalleCouranteX() {
        return indSalleCourante[0];
    }

    public int getIndSalleCouranteY() {
        return indSalleCourante[1];
    }

    public void setIndSalleCouranteX(int _x) {
        this.indSalleCourante[0] = _x;
    }

    public void setIndSalleCouranteY(int _y) {
        this.indSalleCourante[1] = _y;
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

	public Niveau getNiveau(int i){
        return niveaux[i];
    }

    public int getIndNiveauCourant(){return indNiveauCourant;}

    public void chargerSalle(Salle s){
        for(int x = 0; x < SIZE_X; x++){
            for(int y = 0; y < SIZE_Y; y++){
                grilleEntitesStatiques[x][y] = s.getEntiteStatique(x,y);
            }
        }
    }

    private void placerHeros(int x, int y){
        heros = new Heros(this, x , y);
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

    public void addEntiteStatique(EntiteStatique e, int x, int y) {
        try {
            grilleEntitesStatiques[x][y] = e;
        }
        catch (ArrayIndexOutOfBoundsException ex){
            System.out.println("Entité en dehors du terrain");
        }
    }

    public void removeEntiteStatique(int x, int y){
        try {
            grilleEntitesStatiques[x][y] = null;
        }
        catch (ArrayIndexOutOfBoundsException ex){
            System.out.println("Entité en dehors du terrain");
        }
    }
}
