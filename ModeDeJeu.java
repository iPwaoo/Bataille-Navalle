
import java.util.Scanner;

public class ModeDeJeu {
    static Scanner sc = new Scanner(System.in).useDelimiter("\n");

    /* Cette methode représente le déroulement de la partie*/
    public static void jeu () {

        //Creation de tous les tableaux necessaire au jeu
        int joueur = 0, longueurTab = 10, largeurTab = 10, compteurCoule1 = 0, compteurCoule2 = 0, nbBateauACoule = 5;

        int[][] tabJoueur1Bateau, tabJoueur2Bateau, tabJoueur1Bombe, tabJoueur2Bombe;

        tabJoueur1Bateau = new int[ longueurTab ][ largeurTab ];
        tabJoueur2Bateau = new int[ longueurTab ][ largeurTab ];
        tabJoueur1Bombe = new int[ longueurTab ][ largeurTab ];
        tabJoueur2Bombe = new int[ longueurTab ][ largeurTab ];

        //Premier phase du jeu
//        placementDesBateaux(tabJoueur1Bateau, tabJoueur2Bateau);

        //Deuxieme phase du jeu.
        //Le tour par tour
        while ( compteurCoule1 != nbBateauACoule && compteurCoule2 != nbBateauACoule ) {

            if ( joueur % 2 == 0 ) {
                compteurCoule1 = tour(tabJoueur1Bombe, tabJoueur2Bateau, 1,compteurCoule1);

            }
            else
                compteurCoule2 = tour(tabJoueur2Bombe, tabJoueur1Bateau, 2,compteurCoule2);

            joueur++;
        }

        if ( compteurCoule1 == nbBateauACoule )
            System.out.println("Felicitation au joueur 1. Vous avez coulé tous les bateau enemies !!");
        else
            System.out.println("Felicitation au joueur 2. Vous avez coulé tous les bateau enemies !!");
    }

    /*
     * Cette methode sert d'interface à l'utilisateur pour la phase de placement des bateaux.
     * Il y a un conteur de bateau restant et des forcés de saisie pour une mauvaise saisie de taille de bateau et s'il y a plus de bateau de la taille voulue.
     * */
    public static void placementDesBateaux ( int[][] tabBateau1, int[][] tabBateau2 ) {

        int saisie, bateau2 = 1, bateau3 = 2, bateau4 = 1, bateau5 = 1;
        String s;

        for (int i = 1; i <= 2; i++) {
            System.out.println("Joueur " + i + " place tes bateaux à l'abri du regard de l'autre joueur");
            System.out.println();
            for (int j = 1; j <= 5; j++) {
                do {
                    if ( i == 1 )
                        Affichage.affichagePlacement(tabBateau1);
                    else
                        Affichage.affichagePlacement(tabBateau2);

                    System.out.println("Il reste " + bateau2 + " bateaux de taille 2\nIl reste " + bateau3 + " bateau de taille 3\nIl reste " + bateau4 + " bateaux de taille 4\nIl reste " + bateau5 + " bateau de taille 5");
                    System.out.println();
                    System.out.println("Joueur " + i + ", donnez la taille de votre bateau :");
                    s = sc.nextLine();
                    saisie = ModeDeJeu.erreurDeSaisie(s);
                    System.out.println();
                    switch ( saisie ) {
                        case 2:
                            if ( bateau2 <= 0 ) {
                                System.out.println("Il n'y a plus de bateau de taille " + saisie);
                                System.out.println();
                                saisie = -1;
                                break;
                            }
                            else {
                                if ( i == 1 )
                                    PlacementBateau.remplirTableau(tabBateau1, 2);
                                else
                                    PlacementBateau.remplirTableau(tabBateau2, 2);
                                bateau2--;
                            }
                            break;

                        case 3:
                            if ( bateau3 <= 0 ) {
                                System.out.println("Il n'y a plus de bateau de taille " + saisie);
                                System.out.println();
                                saisie = -1;
                                break;
                            }
                            else {
                                if ( i == 1 )
                                    PlacementBateau.remplirTableau(tabBateau1, 3);
                                else
                                    PlacementBateau.remplirTableau(tabBateau2, 3);
                                bateau3--;
                            }
                            break;

                        case 4:
                            if ( bateau4 <= 0 ) {
                                System.out.println("Il n'y a plus de bateau de taille " + saisie);
                                System.out.println();
                                saisie = -1;
                                break;
                            }
                            else {
                                if ( i == 1 )
                                    PlacementBateau.remplirTableau(tabBateau1, 4);
                                else
                                    PlacementBateau.remplirTableau(tabBateau2, 4);
                                bateau4--;
                            }
                            break;

                        case 5:
                            if ( bateau5 <= 0 ) {
                                System.out.println("Il n'y plus de bateau de taille " + saisie);
                                System.out.println();
                                saisie = -1;
                                break;
                            }
                            else {
                                if ( i == 1 )
                                    PlacementBateau.remplirTableau(tabBateau1, 5);
                                else
                                    PlacementBateau.remplirTableau(tabBateau2, 5);
                                bateau5--;
                            }
                            break;

                        default:
                            System.out.println("Mauvaise saisie");
                            saisie = -1;
                            break;
                    }
                } while ( saisie < 0 );
            }
            if ( i == 1 ) {
                Affichage.affichagePlacement(tabBateau1);
                System.out.println("Entrer un nombre pour passer au tour du joueur 2");
            }
            else {
                Affichage.affichagePlacement(tabBateau2);
                System.out.println("Entrer un nombre pour passer au tour du joueur 1 pour placer les bombes");
            }
            s = sc.nextLine();
            saisie = ModeDeJeu.erreurDeSaisie(s);
            clearConsole();

            bateau2 = 1;
            bateau3 = 2;
            bateau4 = 1;
            bateau5 = 1;

        }
    }


    /* Cette methode represente un tour complet pour un jour */

    public static int tour ( int[][] tabBombe, int[][] tabBateauE, int joueur, int compteurCoule ) {

        int ligne, colonne;
        boolean rejouer;
        String s;

        do {
            /* Moment de saisie des coordonnées pour la bombe*/
            do {
                System.out.println("Joueur n° " + joueur + ", c'est à vous de jouer");
                Affichage.affichageBombe(tabBombe);
                System.out.println("Donnez la ligne où vous voulez déposer la bombe");
                s = sc.nextLine();
                ligne = ModeDeJeu.erreurDeSaisie(s)-1;
                System.out.println("Donnez la colonne ou vous voulez déposer la bombe");
                s = sc.nextLine();
                colonne = ModeDeJeu.erreurDeSaisie(s)-1;

                if ( ligne < 0 || ligne > tabBombe.length || colonne < 0 || colonne > tabBombe.length )
                    System.out.println("Les coordonnées sont mauvaises, veuillez les saisir à nouveau");

                else if ( bombePresent(tabBombe, ligne, colonne) )
                    System.out.println("Vous avez déjà mis une bombe ici. Veuillez saisir d'autres coordonnées");

                else
                    //Si un bateau est sur la coordonnée elle est multiplié par 3 pour faire comprendre plus tard qu'elle est touché
                    tabBateauE[ ligne ][ colonne ] = tabBateauE[ ligne ][ colonne ] * 3;

            } while ( ligne < 0 || ligne > tabBombe.length || colonne < tabBombe.length || colonne > 9 || bombePresent(tabBombe, ligne, colonne) );


            /* Condiction pour savoir si le joueur peu ou pas rejouée (Il rejoue s'il touche ou coule un bateau) */
            if ( !bateauPresent(tabBateauE, ligne, colonne) ) {
                System.out.println("PLOUF ! Vous n'avez pas touché de bateau");
                System.out.println();
                System.out.println();
                tabBombe[ ligne ][ colonne ] = 1;
                rejouer = false;
            }

            else if ( !couler(tabBateauE, ligne, colonne) ) {
                System.out.println("TOUCHÉ MON CAPITAIN !!\nVous pouvez tirer à nouveau");
                System.out.println();
                tabBombe[ ligne ][ colonne ] = 2;
                rejouer = true;
            }

            else {
                System.out.println("COULÉ MON CAPITAIN !!");
                System.out.println();
                remplacerToucher(tabBateauE, tabBombe, ligne, colonne);
                compteurCoule++;
                rejouer = true;
            }

        } while ( rejouer && compteurCoule != 5);
        return compteurCoule;
    }

    public static boolean bateauPresent ( int[][] tabBateauE, int ligne, int colonne ) {
        return tabBateauE[ ligne ][ colonne ] != 0;
    }

    public static boolean bombePresent ( int[][] tabBombe, int ligne, int colonne ) {
        return tabBombe[ ligne ][ colonne ] != 0;
    }

    /*
     * Cette methode sert à savoir si la case toucher par le joueur coule un bateau
     * Pour comprendre:
     * 2,31,32,4,5 = case du bateau non touché.
     * 6,93,96,12,15 = case du bateau touché.
     *
     * Pour se faire la methode va d'abord prendre la valeur sur laquelle le joueur est tombé divisé par 3, car deja mis comme étant touché
     * Puis la methode regarder aux alentour des coordonnées ( en-bas puis en haut ensuite ç droite et enfin à gauche )
     * Et prends le nombre de fois que la valeur *3 est apparue
     * Si c'est égal à la taille du bateau alors il est coulé et retourne vrai si non faux.
     *
     * Par exemple, il a eu 2 fois la valeur 6 alors il est coulé et retourne vrai
     * Mais s'il y a que 3 fois la valeur 12 alors le bateau est seulement touché et retourne faux.
     * */
    public static boolean couler ( int[][] tabBateauE, int ligne, int colonne ) {

        int val, val3, indice = 1, indiceToucher = 1;

        val = tabBateauE[ ligne ][ colonne ] / 3;
        val3 = tabBateauE[ ligne ][ colonne ];

        if ( val == 31 || val == 32 )
            val = 3;

        while ( ligne + indice < tabBateauE.length && indiceToucher != val && tabBateauE[ ligne + indice ][ colonne ] == val3 ) {
            indiceToucher++;
            indice++;
        }
        indice = 1;

        while ( ligne - indice >= 0 && indiceToucher != val && tabBateauE[ ligne - indice ][ colonne ] == val3 ) {
            indiceToucher++;
            indice++;
        }
        indice = 1;

        while ( colonne + indice < tabBateauE[ ligne ].length && indiceToucher != val && tabBateauE[ ligne ][ colonne + indice ] == val3 ) {
            indiceToucher++;
            indice++;
        }
        indice = 1;

        while ( colonne - indice >= 0 && indiceToucher != val && tabBateauE[ ligne ][ colonne - indice ] == val3 ) {
            indiceToucher++;
            indice++;
        }

        return indiceToucher == val;
    }
    /*
     *Cette methode sert à changer les valeurs du tableau Bombe pour qu'à l'affichage on voit que le bateau est coulé
     *Comme pour la methode couler on cherche à savoir si le bateau est horizontale ou verticale pour moins de travail au niveau du scan
     */

    public static void remplacerToucher ( int[][] tabBateauE, int[][] tabBombe, int ligne, int colonne ) {

        int val3, indice = 1;

        val3 = tabBateauE[ ligne ][ colonne ];
        tabBombe[ ligne ][ colonne ] = 3;

        while ( ligne + indice < tabBateauE.length && tabBateauE[ ligne + indice ][ colonne ] == val3 ) {
            tabBombe[ ligne + indice ][ colonne ] = 3;
            indice++;
        }
        indice = 1;

        while ( ligne - indice >= 0 && tabBateauE[ ligne - indice ][ colonne ] == val3 ) {
            tabBombe[ ligne - indice ][ colonne ] = 3;
            indice++;
        }
        indice = 1;

        while ( colonne + indice < tabBateauE[ ligne ].length && tabBateauE[ ligne ][ colonne + indice ] == val3 ) {
            tabBombe[ ligne ][ colonne + indice ] = 3;
            indice++;
        }
        indice = 1;

        while ( colonne - indice >= 0 && tabBateauE[ ligne ][ colonne - indice ] == val3 ) {
            tabBombe[ ligne ][ colonne - indice ] = 3;
            indice++;
        }
    }

    public static void clearConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static int erreurDeSaisie(String s) {

        int nb = 0;
        boolean correct = false;

        while ( !correct ) {
            try {
                nb = Integer.parseInt(s);
                correct = true;
            } catch (NumberFormatException exception) {
                System.out.println("Saisie incorrect. Il faut un nombre");
                s = sc.nextLine();

            }

        }

        return nb;
    }
}


