import java.util.Scanner;

public class IA {
    static Scanner sc = new Scanner(System.in).useDelimiter("\n");

    /* Cette methode représente le déroulement de la partie contre l'IA*/
    public static void jeu(){

        //Creation de tous les tableaux nécessaires au jeu
        int tour = 0, longueurTab = 10, largeurTab = 10, compteurCouleJoueur = 0, compteurCouleIA = 0, nbBateauACoule = 5, choixDeJeu;
        String s;

        /*  indice [0][0] = compteurCoule
            indice [1] .. = dernière position de la bombe si touche un bateau (0 = ligne, 1 = colonne)
            indice [2] .. = première position de la bombe si touche un bateau (0 = ligne, 1 = colonne)
         */
        int[][] Premier_DernierePositionBombe = new int [3][2];
        int[][] tabJoueurBateau, tabIA_Bateau,tabJoueurBombe,tabIA_Bombe;

        tabJoueurBateau = new int[ longueurTab ][ largeurTab ];
        tabIA_Bateau = new int[ longueurTab ][ largeurTab ];
        tabJoueurBombe = new int[ longueurTab ][ largeurTab ];
        tabIA_Bombe = new int[ longueurTab ][ largeurTab ];

        //tableau qui contient en x le nombre de longueurs différentes et en y le nombre de bateaux par longueur
        int[][] NombreBateau = {{2, 1}, {3, 2}, {4, 1}, {5, 1}};

        //Niveau de difficulté IA
        do{
            System.out.println("Choisissez la difficulté de la partie :\n1-Facile\n2-Difficile ");
            s = sc.nextLine();
            choixDeJeu = ModeDeJeu.erreurDeSaisie(s);

            if (choixDeJeu!=1 && choixDeJeu!=2)
                System.out.println("Saisie invalide");

        }while (choixDeJeu!=1 && choixDeJeu!=2);


        //Premier phase du jeu
        placementDesBateaux(tabJoueurBateau, NombreBateau);
        PlacementBateau_IA(tabIA_Bateau, NombreBateau);
        //Affichage.affichagePlacement(tabIA_Bateau);

        //Deuxième phase du jeu.
        //Le tour par tour avec l'IA
        if (choixDeJeu == 1) {
            while (compteurCouleJoueur != nbBateauACoule && compteurCouleIA != nbBateauACoule) {

                if (tour % 2 == 0) {
                    compteurCouleJoueur = tourJoueur(tabJoueurBombe, tabIA_Bateau, compteurCouleJoueur);

                } else
                    compteurCouleIA = compteurCouleIA + tourIA_Facile(tabIA_Bombe, tabJoueurBateau);

                tour++;
            }
        }
        else {
            while (compteurCouleJoueur != nbBateauACoule && compteurCouleIA != nbBateauACoule) {

                if (tour % 2 == 0) {
                    compteurCouleJoueur = tourJoueur(tabJoueurBombe, tabIA_Bateau, compteurCouleJoueur);

                } else
                    compteurCouleIA = tourIA_Difficile(tabIA_Bombe, tabJoueurBateau, Premier_DernierePositionBombe)[0][0];

                tour++;
            }
        }

        if ( compteurCouleJoueur == nbBateauACoule )
            System.out.println("Félicitations! Vous avez coulé tous les bateaux enemies !!");
        else
            System.out.println("Dommage! Vous avez perdu !!");
    }

    /* Cette méthode représente un tour complet pour l'IA en mode facile
     * Elle retournera un entier qui comptera le nombre de bateau coulé par l'IA le même tour
     * Se base sur des Math.Random (aucune stratégie)
     * */
    public static int tourIA_Facile(int[][] tabBombe, int[][] tabBateauJoueur){
        int ligne, colonne, compteurCoule = 0;
        boolean rejouer, AffichageTouche = false, AffichageCoule = false;

        System.out.println("C'est au tour de votre adversaire");
        do {
            do {
                ligne = (int)(Math.random()*tabBateauJoueur.length);
                colonne = (int)(Math.random()*tabBateauJoueur.length);
            } while ( ModeDeJeu.bombePresent(tabBombe, ligne, colonne) );

            //Si un bateau est sur la coordonnée elle est multiplié par 3 pour faire comprendre plus tard qu'elle est touché
            tabBateauJoueur[ ligne ][ colonne ] = tabBateauJoueur[ ligne ][ colonne ] * 3;


            /* Condition pour savoir si le joueur peu ou pas rejouée (Il rejoue s'il touche ou coule un bateau) */
            if ( !ModeDeJeu.bateauPresent(tabBateauJoueur, ligne, colonne) ) {
                tabBombe[ ligne ][ colonne ] = 1;
                rejouer = false;
            }

            else if ( !ModeDeJeu.couler(tabBateauJoueur, ligne, colonne) ) {
                tabBombe[ ligne ][ colonne ] = 2;
                AffichageTouche = true;
                rejouer = true;

            }

            else {
                ModeDeJeu.remplacerToucher( tabBateauJoueur,tabBombe,ligne,colonne);
                compteurCoule++;
                AffichageCoule = true;
                rejouer = true;
            }
        } while ( rejouer );

        Affichage.affichageBombe(tabBombe);

        if (AffichageCoule)
            System.out.println("Nous avons perdu un navire MON CAPITAIN !!");
        else if (AffichageTouche)
            System.out.println("Arg! Nous sommes touchés!");
        else
            System.out.println("PLOUF ! Rien n'a été touché");

        return compteurCoule;
    }


    /* Cette méthode représente un tour complet pour l'IA en mode difficile
     * Elle retournera un tableau à deux dimensions qui retournera respectivement :
     * L'indice [0][0] représente le compteur de bateau coulé
     * L'indice [1] .. représente la dernière position de la bombe si touche un bateau
     * L'indice [2] .. représente la première position de la bombe si touche un bateau
     * */

    public static int[][] tourIA_Difficile(int[][] tabBombe, int[][] tabBateauJoueur, int[][] CompteurCouleEtPosition){
        int ligne, colonne, derniereLigne = CompteurCouleEtPosition[1][0], dernierColonne = CompteurCouleEtPosition[1][1];
        int compteurCoule = 0;
        boolean rejouer, AffichageTouche = false, AffichageCoule = false, toucherPremiereFois;
        int[] PositionnementBombe;

        /* Moment de saisie des coordonnées pour la bombe*/
        System.out.println("C'est au tour de votre adversaire");
        do {
            System.out.println("Il a placé ses bombes respectivement sur les cases :");
            // Si l'IA a touché un bateau le tour précédent, il cherchera à couler ce dernier
            if(ModeDeJeu.bateauPresent(tabBateauJoueur, derniereLigne, dernierColonne) && ModeDeJeu.bombePresent(tabBombe,derniereLigne, dernierColonne) && !ModeDeJeu.couler(tabBateauJoueur, derniereLigne, dernierColonne)) {

                PositionnementBombe = StrategieIA.PlacementBombeBateauTrouve_IA(tabBombe, tabBateauJoueur, derniereLigne, dernierColonne, CompteurCouleEtPosition[2][0], CompteurCouleEtPosition[2][1]);
                ligne = PositionnementBombe[0];
                colonne = PositionnementBombe[1];

                for (int i = 0; i< PositionnementBombe.length; i++)
                    System.out.print(PositionnementBombe[i]+1 + " ");
                System.out.println();

                toucherPremiereFois = false;
            }

            // Position de bombe aléatoire si aucun bateau touché
            else {
                do {
                    PositionnementBombe = StrategieIA.PlacementBombeDebut_IA(tabBombe);
                    ligne = PositionnementBombe[0];
                    colonne = PositionnementBombe[1];

                    for (int i = 0; i< PositionnementBombe.length; i++)
                        System.out.print(PositionnementBombe[i]+1 + " ");
                    System.out.println();

                    toucherPremiereFois = true;
                } while ( ModeDeJeu.bombePresent(tabBombe, ligne, colonne));
            }



            //Si un bateau est sur la coordonnée elle est multiplié par 3 pour faire comprendre plus tard qu'elle est touché
            tabBateauJoueur[ ligne ][ colonne ] = tabBateauJoueur[ ligne ][ colonne ] * 3;


            //Condition pour savoir si le joueur peu ou pas rejouée (Il rejoue s'il touche ou coule un bateau)
            if ( !ModeDeJeu.bateauPresent(tabBateauJoueur, ligne, colonne) ) {
                tabBombe[ ligne ][ colonne ] = 1;
                rejouer = false;
            }
            else if (!ModeDeJeu.couler(tabBateauJoueur, ligne, colonne)) {
                tabBombe[ ligne ][ colonne ] = 2;
                derniereLigne = ligne;
                dernierColonne = colonne;
                AffichageTouche = true;
                rejouer = true;
            }
            else {
                ModeDeJeu.remplacerToucher( tabBateauJoueur,tabBombe,ligne,colonne);
                compteurCoule++;
                AffichageCoule = true;
                rejouer = true;
            }

            CompteurCouleEtPosition[0][0] = CompteurCouleEtPosition[0][0] + compteurCoule;
            CompteurCouleEtPosition[1][0 ]= derniereLigne;
            CompteurCouleEtPosition[1][1] = dernierColonne;

            // Si un bateau est touché pour la première fois, il faut noter le point d'impact
            if(toucherPremiereFois) {
                CompteurCouleEtPosition[2][0] = derniereLigne;
                CompteurCouleEtPosition[2][1] = dernierColonne;
            }

        } while ( rejouer );

        Affichage.affichageBombe(tabBombe);


        if (AffichageCoule)
            System.out.println("Nous avons perdu un navire MON CAPITAIN !!");
        else if (AffichageTouche)
            System.out.println("Arg! Nous sommes touchés!");
        else
            System.out.println("PLOUF ! Rien n'a été touché");

        return CompteurCouleEtPosition;
    }

    /*
     * Cette methode sert d'interface à l'utilisateur pour la phase de placement des bateaux.
     * Il y a un conteur de bateau restant et des forcés de saisie pour une mauvaise saisie de taille de bateau et s'il y a plus de bateau de la taille voulue.
     * */
    public static void placementDesBateaux (int[][] tabPlacementBateau, int[][] tabNombreDeBateau) {

        int saisie;
        String s;
        
        System.out.println("Placez vos bateaux dans la grille");
        System.out.println();
        
        for (int i = 1; i <= 5; i++) {
            do {
                do {
                    Affichage.affichagePlacement(tabPlacementBateau);
                    for (int j=0; j<tabNombreDeBateau.length; j++) {
                        System.out.println("Il reste " + tabNombreDeBateau[j][1] + " bateaux de taille " + tabNombreDeBateau[j][0]);
                    }
                    System.out.println();
                    System.out.println("Donnez la taille de votre bateau :");
                    
                    saisie = sc.nextInt();
                    System.out.println();
                    if ( saisie <= 1 || saisie >= 6 )
                        System.out.println("Mauvaise saisie");
                } while ( saisie <= 1 || saisie >= 6 );

                switch ( saisie ) {

                    case 2:
                        if ( tabNombreDeBateau[ 0 ][ 1 ] <= 0 ) {
                            System.out.println("Il n'y a plus de bateau de taille " + saisie);
                            System.out.println();
                            saisie = -1;
                            break;
                        }
                        else {
                            PlacementBateau.remplirTableau(tabPlacementBateau, 2);
                            tabNombreDeBateau[ 0 ][ 1 ]--;
                        }
                        break;

                    case 3:
                        if ( tabNombreDeBateau[ 1 ][ 1 ] <= 0 ) {
                            System.out.println("Il n'y a plus de bateau de taille " + saisie);
                            System.out.println();
                            saisie = -1;
                            break;
                        }
                        else {
                            PlacementBateau.remplirTableau(tabPlacementBateau, 3);
                            tabNombreDeBateau[ 1 ][ 1 ]--;
                        }
                        break;
                    case 4:
                        if ( tabNombreDeBateau[ 2 ][ 1 ] <= 0 ) {
                            System.out.println("Il n'y a plus de bateau de taille " + saisie);
                            System.out.println();
                            saisie = -1;
                            break;
                        }
                        else {
                            PlacementBateau.remplirTableau(tabPlacementBateau, 4);
                            tabNombreDeBateau[ 2 ][ 1 ]--;
                        }
                        break;
                    case 5:
                        if ( tabNombreDeBateau[ 3 ][ 0 ] <= 0 ) {
                            System.out.println("Il n'y plus de bateau de taille " + saisie);
                            System.out.println();
                            saisie = -1;
                            break;
                        }
                        else {
                            PlacementBateau.remplirTableau(tabPlacementBateau, 5);
                            tabNombreDeBateau[ 3 ][ 1 ]--;
                        }
                        break;
                }
            } while ( saisie < 0 );

        }
        Affichage.affichagePlacement(tabPlacementBateau);
        tabNombreDeBateau[0][1] = 1;
        tabNombreDeBateau[1][1] = 2;
        tabNombreDeBateau[2][1] = 1;
        tabNombreDeBateau[3][1] = 1;
    }
    /* Placement de bateaux aléatoire par l'IA en faisant des Math.Random*/
    public static void PlacementBateau_IA (int[][] tab_IA, int[][] tabNombreDeBateau) {
        int ligne, colonne, direction, sens, longueurBateau;
        for (int i = 0; i < tabNombreDeBateau.length; i++) {
            longueurBateau = tabNombreDeBateau[ i ][ 0 ];
            do {
                tabNombreDeBateau[ i ][ 1 ]--;

                //cas d'erreur de placement
                do {
                    do {
                        ligne = (int) ( Math.random() * tab_IA.length );
                        colonne = (int) ( Math.random() * tab_IA.length );
                    } while ( ModeDeJeu.bateauPresent(tab_IA, ligne, colonne) );
                    direction = (int) ( Math.random() * 2 );
                    if ( ErreursPlacement.bateauCogne(tab_IA, ligne, colonne, direction, longueurBateau) == ErreursPlacement.SensPossible(tab_IA, ligne, colonne, direction, longueurBateau) || tab_IA[ ligne ][ colonne ] != 0 ) {
                        if ( direction == 1 )
                            direction--;
                        else
                            direction++;
                    }
                } while ( ErreursPlacement.bateauCogne(tab_IA, ligne, colonne, direction, longueurBateau) == ErreursPlacement.SensPossible(tab_IA, ligne, colonne, direction, longueurBateau) || tab_IA[ ligne ][ colonne ] != 0 );

                if ( ErreursPlacement.bateauCogne(tab_IA, ligne, colonne, direction, longueurBateau) == 0 && ErreursPlacement.SensPossible(tab_IA, ligne, colonne, direction, longueurBateau) == 3 ) {
                    sens = (int) ( Math.random() * 2 );
                    PlacementBateau.creerBateau(tab_IA, ligne, colonne, direction, sens, longueurBateau);
                }

                //cas placement automatique
                else
                    PlacementBateau.creerBateau(tab_IA, ligne, colonne, direction, ErreursPlacement.SensAuto(tab_IA, ligne, colonne, direction, longueurBateau), longueurBateau);

            } while ( tabNombreDeBateau[ i ][ 1 ] != 0 );
        }
    }

    /* Cette methode représente un tour complet pour un jour */

    public static int tourJoueur(int[][] tabBombe, int[][] tabBateauE, int compteurCoule ) {

        int ligne, colonne;
        boolean rejouer;
        String s;

        do {
            /* Moment de saisie des coordonnées pour la bombe*/
            do {
                System.out.println("C'est à vous de jouer");
                Affichage.affichageBombe(tabBombe);
                System.out.println("Donnez la ligne où vous voulez déposer la bombe");
                s = sc.nextLine();
                ligne = ModeDeJeu.erreurDeSaisie(s)-1;
                System.out.println("Donnez la colonne ou vous voulez déposer la bombe");
                s = sc.nextLine();
                colonne = ModeDeJeu.erreurDeSaisie(s)-1;

                if ( ligne < 0 || ligne > tabBateauE.length || colonne < 0 || colonne > tabBateauE.length )
                    System.out.println("Les coordonnées sont mauvaises, veuillez les saisir à nouveau");

                else if ( ModeDeJeu.bombePresent(tabBombe, ligne, colonne) )
                    System.out.println("Vous avez déjà mis une bombe ici. Veuillez saisir d'autres coordonnées");

                else
                    //Si un bateau est sur la coordonnée elle est multiplié par 3 pour faire comprendre plus tard qu'elle est touché
                    tabBateauE[ ligne ][ colonne ] = tabBateauE[ ligne ][ colonne ] * 3;

            } while ( ligne < 0 || ligne > tabBateauE.length || colonne < 0 || colonne > tabBateauE.length || ModeDeJeu.bombePresent(tabBombe, ligne, colonne) );


            /* Condition pour savoir si le joueur peu ou pas rejouée (Il rejoue s'il touche ou coule un bateau) */
            if ( !ModeDeJeu.bateauPresent(tabBateauE, ligne, colonne) ) {
                System.out.println("PLOUF ! Vous n'avez pas touché de bateau");
                System.out.println();
                System.out.println();
                tabBombe[ ligne ][ colonne ] = 1;
                rejouer = false;
            }

            else if ( !ModeDeJeu.couler(tabBateauE, ligne, colonne) ) {
                System.out.println("TOUCHÉ MON CAPITAIN !!\nVous pouvez tirer à nouveau\n");
                System.out.println();
                tabBombe[ ligne ][ colonne ] = 2;
                rejouer = true;
            }

            else {
                System.out.println("COULÉ MON CAPITAIN !!\n");
                System.out.println();
                ModeDeJeu.remplacerToucher( tabBateauE,tabBombe,ligne,colonne);
                compteurCoule++;
                rejouer = true;
            }

        } while ( rejouer && compteurCoule != 5);
        return compteurCoule;
    }
}
