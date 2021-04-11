package VueControleur;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;


import modele.plateau.*;


/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction, etc.))
 *
 */
public class VueControleur extends JFrame implements Observer {
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement,
    // permet de communiquer les actions clavier (ou souris)

    private int sizeX; // taille de la grille affichée
    private int sizeY;

    // icones affichées dans la grille
    private ImageIcon icoHeroN, icoHeroE, icoHeroW, icoHeroS;   //Heros
    private ImageIcon icoCaseNormale, icoDalleUsageUnique, icoDalleUsageUnique_Enflammee;       //Dalles
    private ImageIcon icoMur, icoPorte;     //Murs portes
    private ImageIcon icoCle, icoCapsule, icoCoffre;    //Pickups
    private ImageIcon icoCaseVide; //CaseVide
    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône,
                                    // suivant ce qui est présent dans le modèle)

    private ImageIcon icoCleInv, icoCapsuleInv, icoCoffreInv, icoVideInv;

    private JFrame inventaire;
    private int tailleInv;
    private JLabel[][] tabJLabelInv;    //cases graphiques pour l'inventaire

    private JFrame coffre;
    private int tailleCoffre = 5;
    private JLabel[][] tabJLabelCoffre;     //cases graphiques pour les coffres

    public VueControleur(Jeu _jeu) {
        sizeX = jeu.SIZE_X;
        sizeY = _jeu.SIZE_Y;
        jeu = _jeu;

        inventaire = new JFrame("Inventaire");
        coffre = new JFrame("Coffre");

        chargerLesIcones();
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();
        ajouterEcouteurSouris();
    }

    private void ajouterEcouteurClavier() {
        //Pour la fenêtre principale
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT : jeu.getHeros().gauche(); break;
                    case KeyEvent.VK_RIGHT : jeu.getHeros().droite();break;
                    case KeyEvent.VK_DOWN : jeu.getHeros().bas(); break;
                    case KeyEvent.VK_UP : jeu.getHeros().haut(); break;
                    case KeyEvent.VK_E: if(!inventaire.isVisible()){
                                             inventaire.setVisible(true);
                                         }
                                        else{
                                            inventaire.setVisible(false);
                                        } break;
                    case KeyEvent.VK_T: jeu.getHeros().ramasser(); break;
                    case KeyEvent.VK_L: jeu.getHeros().LancerCapsule(); break;
                    case KeyEvent.VK_O: jeu.getHeros().setCoffreActif();
                                        if(jeu.getHeros().enFaceCoffre()){
                                            if(!coffre.isVisible()){
                                                coffre.setVisible(true);
                                            }
                                            else {
                                                coffre.setVisible(false);
                                            } break;
                                        }
                }
            }
        });

        //Pour la fenêtre d'inventaire
        inventaire.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_E: inventaire.setVisible(false); break;
                    case KeyEvent.VK_O: jeu.getHeros().setCoffreActif();
                        if(!coffre.isVisible()){
                            coffre.setVisible(true);
                        }
                        else {
                            coffre.setVisible(false);
                        } break;
                }
            }
        });

        //Pour la fenêtre coffre
        coffre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_O: coffre.setVisible(false); break;
                    case KeyEvent.VK_E: if(!inventaire.isVisible()){
                        inventaire.setVisible(true);
                    }
                    else{
                        inventaire.setVisible(false);
                    } break;
                }
            }
        });


    }

    private void ajouterEcouteurSouris(){
        //Ajoute un écouteur de souris pour chaque case de l'inventaire
        for(int i = 0; i < jeu.getHeros().getInventaire().getTaille();i++){
            int finalI = i;
            tabJLabelInv[0][i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    jeu.getHeros().deposer(finalI);
                }
            });
        }

    }

    private void chargerLesIcones() {
        //Icones Heros
        icoHeroN = chargerIcone("Images/Knight_North.png");
        icoHeroE = chargerIcone("Images/Knight_East.png");
        icoHeroW = chargerIcone("Images/Knight_West.png");
        icoHeroS = chargerIcone("Images/Knight_South.png");

        //Icones cases
        icoCaseNormale = chargerIcone("Images/CaseNormale.png");
        icoDalleUsageUnique = chargerIcone("Images/DalleUsageUnique.png");
        icoDalleUsageUnique_Enflammee = chargerIcone("Images/DalleUsageUnique_Enflammee.png");
        icoMur = chargerIcone("Images/Mur.png");
        icoPorte = chargerIcone("Images/Porte.png");
        icoCaseVide = chargerIcone( "Images/CaseVide.png");
        //Icones Pickups
        icoCle = chargerIcone("Images/Clef.png");
        icoCoffre = chargerIcone("Images/Coffre.png");
        icoCapsule = chargerIcone("Images/capsule.png");

        //Icones Pickups inventaire
        icoCapsuleInv = chargerIcone("Images/CapsuleInv.png");
        icoCoffreInv = chargerIcone("Images/CoffreInv.png");
        icoCleInv = chargerIcone("Images/CleInv.png");
        icoVideInv = chargerIcone("Images/VideInv.png");
    }

    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleur.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return new ImageIcon(image);
    }

    private void placerLesComposantsGraphiques() {
        tailleInv = jeu.getHeros().getInventaire().getTaille();

        //Initialisation fenêtre de jeu
        setTitle("Roguelike");
        setSize(20 * sizeX, 22 * sizeY); //taille de la fenêtre en fonction de la taille du jeu
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre
        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille
        grilleJLabels.setBackground(Color.black);//met la couleur du fond de la fenêtre en noir, plus adapté à un jeu de donjon

        tabJLabel = new JLabel[sizeX][sizeY];

        //Initialisation fenêtre d'inventaire
        inventaire.setSize(tailleInv * 48,100);
        inventaire.setLocationRelativeTo(this);
        inventaire.setLocation(0, 22 * sizeY);
        JComponent grilleJLabelsInv = new JPanel(new GridLayout(1, tailleInv));  //Grille avec le bon nombre de slots pour l'inventaire
        grilleJLabelsInv.setBackground(Color.darkGray);

        tabJLabelInv = new JLabel[1][tailleInv];

        //Initialisation fenêtre de coffre
        coffre.setSize(tailleCoffre * 48, 100);
        coffre.setLocationRelativeTo(this);
        coffre.setLocation(tailleInv * 48, 22 * sizeY);
        JComponent grilleJLabelsCoffre = new JPanel(new GridLayout(1, tailleCoffre));
        grilleJLabelsCoffre.setBackground(Color.darkGray);

        tabJLabelCoffre = new JLabel[1][tailleCoffre];


        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();
                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }
        
        for(int y = 0; y < tailleInv; y++){
            JLabel jlab = new JLabel();
            tabJLabelInv[0][y] = jlab;
            grilleJLabelsInv.add(jlab);
        }

        for(int y = 0; y < tailleCoffre; y++){
            JLabel jlab = new JLabel();
            tabJLabelCoffre[0][y] = jlab;
            grilleJLabelsCoffre.add(jlab);
        }

        add(grilleJLabels);
        inventaire.add(grilleJLabelsInv);
        coffre.add(grilleJLabelsCoffre);
    }

    
    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichageNiveau() {
        //affichage niveau

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
				EntiteStatique e = jeu.getEntite(x, y);
                if (e instanceof Mur) {
                    tabJLabel[x][y].setIcon(icoMur);
                } else if (e instanceof CaseNormale) {
                    tabJLabel[x][y].setIcon(icoCaseNormale);
                } else if (e instanceof Coffre){
                    tabJLabel[x][y].setIcon(icoCoffre);
                } else if (e instanceof Cle){
                    tabJLabel[x][y].setIcon(icoCle);
                } else if (e instanceof Capsule){
                    tabJLabel[x][y].setIcon(icoCapsule);
                } else if (e instanceof Porte){
                    tabJLabel[x][y].setIcon(icoPorte);
                } else if (e instanceof DalleUsageUnique){
                    if(((DalleUsageUnique) e).isEnflammee()){
                        tabJLabel[x][y].setIcon(icoDalleUsageUnique_Enflammee);
                    }
                    else{
                        tabJLabel[x][y].setIcon(icoDalleUsageUnique);
                    }
                } else if (e instanceof CaseVide){
                    tabJLabel[x][y].setIcon(icoCaseVide);
                }
            }
        }

        //affichage Inventaire

        for(int y = 0; y < tailleInv; y++){
            Pickup p = jeu.getHeros().getInventaire().getContenu(y);
            if(p instanceof Capsule){
                tabJLabelInv[0][y].setIcon(icoCapsuleInv);
            }
            else if (p instanceof Cle){
                tabJLabelInv[0][y].setIcon(icoCleInv);
            }
            else if (p instanceof Coffre){
                tabJLabelInv[0][y].setIcon(icoCoffreInv);
            }
            else{
                tabJLabelInv[0][y].setIcon(icoVideInv);
            }
        }
        //affichage Heros
    }

    public void mettreAJourAffichageInventaire(){
        tailleInv = jeu.getHeros().getInventaire().getTaille();

        for(int y = 0; y < tailleInv; y++){
            Pickup p = jeu.getHeros().getInventaire().getContenu(y);
            if(p instanceof Capsule){
                tabJLabelInv[0][y].setIcon(icoCapsuleInv);
            }
            else if (p instanceof Cle){
                tabJLabelInv[0][y].setIcon(icoCleInv);
            }
            else if (p instanceof Coffre){
                tabJLabelInv[0][y].setIcon(icoCoffreInv);
            }
            else{
                tabJLabelInv[0][y].setIcon(icoVideInv);
            }
        }
    }

    public void mettreAJourAffichageHeros(){
        switch(jeu.getHeros().getOrientation()){
            case 'n':
                tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(icoHeroN); break;
            case 'e':
                tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(icoHeroE); break;
            case 's':
                tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(icoHeroS); break;
            case 'w':
                tabJLabel[jeu.getHeros().getX()][jeu.getHeros().getY()].setIcon(icoHeroW); break;

        }
    }

    public void mettreAJourAffichageCoffre(){
        if(jeu.getHeros().getCoffreActif() != null){
            for(int y = 0; y < tailleCoffre; y++){
                Pickup p = jeu.getHeros().getCoffreActif().getContenu(y);
                if(p instanceof Capsule){
                    tabJLabelCoffre[0][y].setIcon(icoCapsuleInv);
                }
                else if (p instanceof Cle){
                    tabJLabelCoffre[0][y].setIcon(icoCleInv);
                }
                else if (p instanceof Coffre){
                    tabJLabelCoffre[0][y].setIcon(icoCoffreInv);
                }
                else{
                    tabJLabelCoffre[0][y].setIcon(icoVideInv);
                }
            }
        }
        else{
            for(int y = 0; y < tailleCoffre; y++){
                tabJLabelCoffre[0][y].setIcon(icoVideInv);
            }
        }
    }
    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichageCoffre();
        mettreAJourAffichageNiveau();
        mettreAJourAffichageInventaire();
        mettreAJourAffichageHeros();
        /*
        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
                    }
                });
        */

    }
}
