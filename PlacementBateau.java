import java.util.Scanner;

public class PlacementBateau {
    static Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    public static void remplirTableau(int[][] tab, int longueurBateau) {
        int ligne;
        int colonne;
        int direction;
        int sens;
        String s;

        //cas d'erreur de placement
         do{
             do {
                 System.out.println("Placez la 1ère case de votre bateau\nLigne :");
                 s = scanner.nextLine();
                 ligne = ModeDeJeu.erreurDeSaisie(s)-1;
                 System.out.println("Colonne :");
                 s = scanner.nextLine();
                 colonne = ModeDeJeu.erreurDeSaisie(s)-1;
                 System.out.println("Horizontal ou Vertical ? (0/1)");
                 s = scanner.nextLine();
                 direction = ModeDeJeu.erreurDeSaisie(s);
                 if(!(ligne>=0 && ligne<tab.length) || !(colonne>=0 && colonne<tab.length) || (direction != 0 && direction != 1))
                     System.out.println("Vous avez saisi une mauvaise coordonnée");
             }while (!(ligne>=0 && ligne<tab.length) || !(colonne>=0 && colonne<tab.length) || (direction != 0 && direction != 1));

            if(ErreursPlacement.bateauCogne(tab, ligne, colonne, direction, longueurBateau) == ErreursPlacement.SensPossible(tab, ligne, colonne, direction, longueurBateau) || tab[ligne][colonne] != 0)
                System.out.println("Erreur, le bateau sera en collision avec un autre bateau !");
        }while (ErreursPlacement.bateauCogne(tab, ligne, colonne, direction, longueurBateau) == ErreursPlacement.SensPossible(tab, ligne, colonne, direction, longueurBateau) || tab[ligne][colonne] != 0);

        if (ErreursPlacement.bateauCogne(tab, ligne, colonne, direction, longueurBateau) == 0 && ErreursPlacement.SensPossible(tab, ligne, colonne, direction, longueurBateau) == 3) {
            do {
                if (direction == 0)
                    System.out.println("Vers le gauche ou vers la droite ? (0/1)");
                else
                    System.out.println("Vers le haut ou vers le bas ? (0/1)");

            s = scanner.nextLine();
            sens = ModeDeJeu.erreurDeSaisie(s);
                if(sens != 0 && sens != 1)
                    System.out.println("Vous avez saisi une mauvaise coordonnée");
            }while (sens != 0 && sens != 1);
            creerBateau(tab, ligne, colonne, direction, sens, longueurBateau);
        }
        //cas placement automatique
        else
            creerBateau(tab, ligne, colonne, direction, ErreursPlacement.SensAuto(tab, ligne, colonne, direction, longueurBateau), longueurBateau);
    }

    public static void creerBateau( int[][] tab, int ligne, int colonne, int direction, int sens, int longueurBateau) {
        int placeBateau = 0;
        int compteur = 0;
        boolean presenceBateau3 = false;
        int i = 0;
        int j = 0;

        switch(longueurBateau) {
            case 2 :
                placeBateau = 2;
                break;
            case 3 :
                //1er bateau formé de 31, 2ᵉ bateau formé de 32
                while (i < tab.length && !presenceBateau3) {
                j=0;
                    while (j < tab[i].length && !presenceBateau3) {
                        if (tab[i][j] == 31)
                            presenceBateau3 = true;
                        else
                            j++;
                    }
                    i++;
                }
                if(presenceBateau3)
                    placeBateau = 32;
                else
                    placeBateau = 31;

                break;
            case 4 :
                placeBateau = 4;
                break;
            case 5 :
                placeBateau = 5;
                break;

        }

        //Direction horizontale
        if (direction == 0)
            //sens vers la gauche
            if (sens == 0)
                 while(compteur!=longueurBateau) {
                     tab[ligne][colonne - compteur] = placeBateau;
                    compteur++;
                }
            //sens vers la droite
            else
                while(compteur!=longueurBateau) {
                    tab[ligne][colonne + compteur] = placeBateau;
                    compteur++;
                }
                //Direction verticale
        else if (direction == 1) {
            if (sens == 0)
                while(compteur!=longueurBateau) {
                    tab[ligne - compteur][colonne] = placeBateau;
                    compteur++;
                }
            else
                while(compteur!=longueurBateau) {
                    tab[ligne + compteur][colonne] = placeBateau;
                    compteur++;
                }

        }

    }
}
