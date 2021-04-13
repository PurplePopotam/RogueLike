/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.entites.*;

/**
 * Héros du jeu
 */
public class Heros {
    private int x;
    private int y;

    private char orientation = 's';

    private Jeu jeu;

    private Inventaire inventaire;

    private Coffre coffreActif;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getOrientation() {
        return orientation;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public Coffre getCoffreActif(){return coffreActif;}

    public Heros(Jeu _jeu, int _x, int _y) {
        jeu = _jeu;
        x = _x;
        y = _y;
        inventaire = new Inventaire(jeu);
        coffreActif = new Coffre(jeu);
    }

    public void droite() {
        if(orientation == 'e'){
            if(jeu.getEntite(x+1,y) instanceof Porte){
                traverserPorte(x+1, y);
            }
            else if (jeu.getEntite(x+1,y) instanceof CaseVide) {
                sautVide(x+1,y);
            }
            else if (traversable(x+1, y)) {
                x ++;
            }
        }
        else {
            orientation = 'e';
        }
    }


    public void gauche() {
        if(orientation == 'w'){
            if(jeu.getEntite(x-1,y) instanceof Porte){
                traverserPorte(x-1, y);
            }
            else if (jeu.getEntite(x-1,y) instanceof CaseVide) {
                sautVide(x-1,y);
            }
            else if (traversable(x-1, y)) {
                x --;
            }
        }
        else{
            orientation = 'w';
        }
    }

    public void bas() {
        if(orientation == 's'){
            if(jeu.getEntite(x,y+1) instanceof Porte){
                traverserPorte(x, y+1);
            }
            else if (jeu.getEntite(x,y+1) instanceof CaseVide) {
                sautVide(x,y+1);
            }
            else if (traversable(x, y+1)) {
                y ++;
            }
        }
        else{
            orientation = 's';
        }
    }

    public void haut() {
        if(orientation == 'n'){
            if(jeu.getEntite(x,y-1) instanceof Porte){
                traverserPorte(x, y-1);
            }
            else if (jeu.getEntite(x,y-1) instanceof CaseVide) {
                sautVide(x,y-1);
            }
            else if (traversable(x, y-1)) {
                y --;
            }
        }
        else{
            orientation = 'n';
        }
    }

    public boolean traversable(int x, int y) {

        if (x >0 && x < jeu.SIZE_X && y > 0 && y < jeu.SIZE_Y) {
            return jeu.getEntite(x, y).traversable();
        } else {
            return false;
        }
    }

    public void traverserPorte(int _x, int _y){
        EntiteStatique e = jeu.getEntite(_x,_y);

        if(e instanceof Porte && ((Porte) e).getJumelle() != null){
            if(((Porte) e).isTraversee() != true ){
                enleverCapsule();
                rechargerCapsule();
                ((Porte) e).setTraversee(true);

            }

            Porte dest = ((Porte) e).getJumelle();
            switch (dest.getDirection()){
                case 'n':
                    x = dest.getX(); y = dest.getY() + 1;
                    jeu.setIndSalleCouranteY(jeu.getIndSalleCouranteY() + 1 );
                    jeu.chargerSalle(jeu.getNiveau(jeu.getIndNiveauCourant()).getSalle(jeu.getIndSalleCouranteX(),jeu.getIndSalleCouranteY()));
                    break;
                case 's':
                    x = dest.getX(); y = dest.getY() - 1;
                    jeu.setIndSalleCouranteY(jeu.getIndSalleCouranteY() - 1 );
                    jeu.chargerSalle(jeu.getNiveau(jeu.getIndNiveauCourant()).getSalle(jeu.getIndSalleCouranteX(),jeu.getIndSalleCouranteY()));
                    break;
                case 'e':
                    x = dest.getX() - 1; y = dest.getY();
                    jeu.setIndSalleCouranteX(jeu.getIndSalleCouranteX() - 1 );
                    jeu.chargerSalle(jeu.getNiveau(jeu.getIndNiveauCourant()).getSalle(jeu.getIndSalleCouranteX(),jeu.getIndSalleCouranteY()));
                    break;
                case 'w':
                    x = dest.getX() + 1; y = dest.getY();
                    jeu.setIndSalleCouranteX(jeu.getIndSalleCouranteX() + 1 );
                    jeu.chargerSalle(jeu.getNiveau(jeu.getIndNiveauCourant()).getSalle(jeu.getIndSalleCouranteX(),jeu.getIndSalleCouranteY()));
                    break;
            }
        }
    }

    public void ramasser(){
        EntiteStatique e;
        switch(orientation){
            case 'n':
                e = jeu.getEntite(x,y-1);
                if(e instanceof Pickup && !inventaire.isFull()){
                    if(e instanceof Coffre){
                        if(((Coffre) e).isEmpty()){
                            inventaire.addItem((Pickup) e);
                            jeu.removeEntiteStatique(x, y-1);
                            jeu.addEntiteStatique(new CaseNormale(jeu), x , y-1);
                        }
                    }
                    else{
                        inventaire.addItem((Pickup) e);
                        jeu.removeEntiteStatique(x, y-1);
                        jeu.addEntiteStatique(new CaseNormale(jeu), x , y-1);
                    }
                }
                break;
            case 'e':
                e = jeu.getEntite(x+1,y);
                if(e instanceof Pickup && !inventaire.isFull()){
                    if(e instanceof Coffre){
                        if(((Coffre) e).isEmpty()){
                            inventaire.addItem((Pickup) e);
                            jeu.removeEntiteStatique(x+1, y);
                            jeu.addEntiteStatique(new CaseNormale(jeu), x+1, y);
                        }
                    }
                    else{
                        inventaire.addItem((Pickup) e);
                        jeu.removeEntiteStatique(x+1, y);
                        jeu.addEntiteStatique(new CaseNormale(jeu), x+1, y);
                    }
                }
                break;
            case 's':
                e = jeu.getEntite(x,y+1);
                if(e instanceof Pickup && !inventaire.isFull()){
                    if(e instanceof Coffre){
                        if(((Coffre) e).isEmpty()){
                            inventaire.addItem((Pickup) e);
                            jeu.removeEntiteStatique(x, y+1);
                            jeu.addEntiteStatique(new CaseNormale(jeu), x, y+1);
                        }
                    }
                    else{
                        inventaire.addItem((Pickup) e);
                        jeu.removeEntiteStatique(x, y+1);
                        jeu.addEntiteStatique(new CaseNormale(jeu), x, y+1);
                    }
                }
                break;
            case 'w':
                e = jeu.getEntite(x-1,y);
                if(e instanceof Pickup && !inventaire.isFull()){
                    if(e instanceof Coffre){
                        if(((Coffre) e).isEmpty()){
                            inventaire.addItem((Pickup) e);
                            jeu.removeEntiteStatique(x-1, y);
                            jeu.addEntiteStatique(new CaseNormale(jeu), x-1, y);
                        }
                    }
                    else{
                        inventaire.addItem((Pickup) e);
                        jeu.removeEntiteStatique(x-1, y);
                        jeu.addEntiteStatique(new CaseNormale(jeu), x-1, y);
                    }
                }
                break;
        }
    }

    public void deposer(int i){
        EntiteStatique e;
        switch(orientation){
            case 'n':
                e = jeu.getEntite(x,y-1);
                if(e instanceof CaseNormale){
                    if(inventaire.getContenu(i) instanceof Cle){
                        inventaire.removeItem(i);
                        jeu.removeEntiteStatique(x, y-1);
                        jeu.addEntiteStatique(new Cle(jeu), x, y-1);
                    } else if(inventaire.getContenu(i) instanceof Coffre){
                        if(((Coffre)inventaire.getContenu(i)).isEmpty()){
                            inventaire.removeItem(i);
                            jeu.removeEntiteStatique(x, y-1);
                            jeu.addEntiteStatique(new Coffre(jeu), x, y-1);
                        }
                        else{
                            jeu.removeEntiteStatique(x, y-1);
                            jeu.addEntiteStatique(new Coffre(jeu), x, y-1);
                            inventaire.removeItem(i);
                        }

                    } else if(inventaire.getContenu(i) instanceof Capsule){
                        inventaire.removeItem(i);
                        jeu.removeEntiteStatique(x, y-1);
                        jeu.addEntiteStatique(new Capsule(jeu), x, y-1);
                    }
                }
                break;
            case 'e':
                e = jeu.getEntite(x+1,y);
                if(e instanceof CaseNormale){
                    if(inventaire.getContenu(i) instanceof Cle){
                        inventaire.removeItem(i);
                        jeu.removeEntiteStatique(x+1,y);
                        jeu.addEntiteStatique(new Cle(jeu), x+1,y);
                    } else if(inventaire.getContenu(i) instanceof Coffre){
                        inventaire.removeItem(i);
                        jeu.removeEntiteStatique(x+1,y);
                        jeu.addEntiteStatique(new Coffre(jeu), x+1,y);
                    } else if(inventaire.getContenu(i) instanceof Capsule){
                        inventaire.removeItem(i);
                        jeu.removeEntiteStatique(x+1,y);
                        jeu.addEntiteStatique(new Capsule(jeu), x+1,y);
                    }
                }
                break;
            case 's':
                e = jeu.getEntite(x,y+1);
                if(e instanceof CaseNormale){
                    if(inventaire.getContenu(i) instanceof Cle){
                        inventaire.removeItem(i);
                        jeu.removeEntiteStatique(x,y+1);
                        jeu.addEntiteStatique(new Cle(jeu), x,y+1);
                    } else if(inventaire.getContenu(i) instanceof Coffre){
                        inventaire.removeItem(i);
                        jeu.removeEntiteStatique(x,y+1);
                        jeu.addEntiteStatique(new Coffre(jeu), x,y+1);
                    } else if(inventaire.getContenu(i) instanceof Capsule){
                        inventaire.removeItem(i);
                        jeu.removeEntiteStatique(x,y+1);
                        jeu.addEntiteStatique(new Capsule(jeu), x,y+1);
                    }
                }
                break;
            case 'w':
                e = jeu.getEntite(x-1,y);
                if(e instanceof CaseNormale){
                    if(inventaire.getContenu(i) instanceof Cle){
                        inventaire.removeItem(i);
                        jeu.removeEntiteStatique(x-1,y);
                        jeu.addEntiteStatique(new Cle(jeu), x-1,y);
                    } else if(inventaire.getContenu(i) instanceof Coffre){
                        inventaire.removeItem(i);
                        jeu.removeEntiteStatique(x-1,y);
                        jeu.addEntiteStatique(new Coffre(jeu), x-1,y);
                    } else if(inventaire.getContenu(i) instanceof Capsule){
                        inventaire.removeItem(i);
                        jeu.removeEntiteStatique(x-1,y);
                        jeu.addEntiteStatique(new Capsule(jeu),x-1,y);
                    }
                }
                break;
        }
    }

    public boolean enFaceCoffre(){
        EntiteStatique e;
        switch (orientation){
            case 'n':
                e = jeu.getEntite(x, y-1);
                if(e instanceof Coffre){
                    return true;
                }
                break;
            case 'e':
                e = jeu.getEntite(x+1, y);
                if(e instanceof Coffre){
                    return true;
                }
                break;
            case 's':
                e = jeu.getEntite(x, y+1 );
                if(e instanceof Coffre){
                    return true;
                }
                break;
            case 'w':
                e = jeu.getEntite(x-1, y);
                if(e instanceof Coffre){
                    return true;
                }
                break;
        }
        return false;
    }

    public void setCoffreActif(){
        EntiteStatique e;
        switch (orientation){
            case 'n':
                e = jeu.getEntite(x, y-1);
                if(e instanceof Coffre){
                    coffreActif = (Coffre)e;
                }
                break;
            case 'e':
                e = jeu.getEntite(x+1, y);
                if(e instanceof Coffre){
                    coffreActif = (Coffre)e;
                }
                break;
            case 's':
                e = jeu.getEntite(x, y+1 );
                if(e instanceof Coffre){
                    coffreActif = (Coffre)e;
                }
                break;
            case 'w':
                e = jeu.getEntite(x-1, y);
                if(e instanceof Coffre){
                    coffreActif = (Coffre)e;
                }
                break;
        }
    }

    public int PossedeCapsule(){
        for (int i = 0; i < getInventaire().getTaille(); i++){
            if(getInventaire().getContenu(i) instanceof Capsule){
                return i;
            }
        }
        return -1;
    }

    public void LancerCapsule(){

        EntiteStatique e;
        int i = PossedeCapsule();
        switch(orientation){
            case 'n':
                e = jeu.getEntite(x,y-1);
                if(e instanceof DalleUsageUnique && ((DalleUsageUnique) e).isEnflammee()){
                    if(i > -1){
                        inventaire.removeItem(i);
                        ((DalleUsageUnique) e).setEnflammee(false);
                    }
                }
                break;
            case 'e':
                e = jeu.getEntite(x+1,y);
                if(e instanceof DalleUsageUnique && ((DalleUsageUnique) e).isEnflammee()){
                    if(i > -1){
                        inventaire.removeItem(i);
                        ((DalleUsageUnique) e).setEnflammee(false);
                    }
                }
                break;
            case 's':
                e = jeu.getEntite(x,y+1);
                if(e instanceof DalleUsageUnique && ((DalleUsageUnique) e).isEnflammee()){
                    if(i > -1){
                        inventaire.removeItem(i);
                        ((DalleUsageUnique) e).setEnflammee(false);
                    }
                }
                break;
            case 'w':
                e = jeu.getEntite(x-1,y);
                if(e instanceof DalleUsageUnique && ((DalleUsageUnique) e).isEnflammee()){
                    if(i > -1){
                        inventaire.removeItem(i);
                        ((DalleUsageUnique) e).setEnflammee(false);
                    }
                }
                break;
        }


    }

    public void enleverCapsule(){
        for(int i = 0; i < getInventaire().getTaille(); i++){
            if(getInventaire().getContenu(i) instanceof Capsule){
                getInventaire().removeItem(i);

            }
        }
    }

    public void rechargerCapsule(){
        int capsuleposee = 0;
        for(int i = 0; i < getInventaire().getTaille(); i++){
            if(getInventaire().getContenu(i) == null && capsuleposee < 3){
                capsuleposee++;
                getInventaire().addItem(new Capsule(jeu));
            }
        }
    }

    public void sautVide(int _x, int _y){

        CaseVide e = (CaseVide)jeu.getEntite(_x,_y);

        switch(orientation){
            case 'n':
                e.setTraversable(traversable(x, y-2));
                if(e.traversable()){
                    y = y-2;
                    e.setTraversable(false);

                }

                break;
            case 'e':
                e.setTraversable(traversable(x+2, y));
                if(e.traversable()){
                    x = x+2;
                    e.setTraversable(false);

                }
                break;
            case 's':
                e.setTraversable(traversable(x, y+2));
                if(e.traversable()){
                    y = y+2;
                    e.setTraversable(false);

                }
                break;
            case 'w':
                e.setTraversable(traversable(x-2, y));
                if(e.traversable()){
                    x = x-2;
                    e.setTraversable(false);

                }
                break;

        }

    }
}
