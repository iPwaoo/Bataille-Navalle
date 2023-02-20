

public class ErreursPlacement {

    /* Faire des boucles pour savoir si les cases que va occuper le bateau sont tous des 0 ou non
    * Retourne 0 si le bateau est libre dans les deux sens
    * Retourne 1 si le bateau cogne à gauche
    * Retourne 2 si le bateau cogne à droite
    * Retourne 3 si le bateau cogne à gauche et à droite*/
    public static int bateauCogne(int[][]tab, int ligne, int colonne, int direction, int longueurBateau) {
        boolean test_gauche_haut = true;
        boolean test_droit_bas = true;
        int cogne_gauche_haut = 0;
        int cogne_droit_bas = 0;
        int compteur;
            if (direction == 0) {
            switch (SensPossible(tab, ligne, colonne, direction, longueurBateau)) {
                //Horizontal, gauche
                case 1:
                    compteur = colonne;
                    while (compteur > colonne - longueurBateau && test_gauche_haut) {
                        if (tab[ligne][compteur] != 0)
                            test_gauche_haut = false;
                        else
                            compteur--;
                    }
                    break;

                case 2:
                    //Horizontal, droit
                    compteur = colonne;
                    while (compteur < colonne + longueurBateau && test_droit_bas) {

                        if (tab[ligne][compteur] != 0)
                            test_droit_bas = false;
                        else
                            compteur++;
                    }
                    break;

                case 3:
                    //Dans les deux sens
                    compteur = colonne;
                    while (compteur > colonne - longueurBateau && test_gauche_haut) {
                        if (tab[ligne][compteur] != 0)
                            test_gauche_haut = false;
                        else
                            compteur--;
                    }
                    compteur = colonne;
                    while (compteur < colonne + longueurBateau && test_droit_bas) {

                        if (tab[ligne][compteur] != 0)
                            test_droit_bas = false;
                        else
                            compteur++;
                    }
            }
            }
            else{
                switch (SensPossible(tab, ligne, colonne, direction, longueurBateau)) {
                    case 1:
                        //Vertical, haut
                        compteur = ligne;
                        while (compteur > ligne - longueurBateau && test_gauche_haut) {
                            if (tab[compteur][colonne] != 0)
                                test_gauche_haut = false;
                            else
                                compteur--;
                        }
                        break;

                        case 2:
                            //Vertical, bas
                            compteur = ligne;
                            while (compteur < ligne + longueurBateau && test_droit_bas) {

                                if (tab[compteur][colonne] != 0)
                                    test_droit_bas = false;
                                else
                                    compteur++;
                            }
                            break;

                        case 3:
                            //Dans les deux sens
                            compteur = ligne;
                            while (compteur > ligne - longueurBateau && test_gauche_haut) {
                                if (tab[compteur][colonne] != 0)
                                    test_gauche_haut = false;
                                else
                                    compteur--;
                            }
                            compteur = ligne;
                            while (compteur < ligne + longueurBateau && test_droit_bas) {

                                if (tab[compteur][colonne] != 0)
                                    test_droit_bas = false;
                                else
                                    compteur++;
                            }
                    }
         }

        if (!test_gauche_haut)
            cogne_gauche_haut = 1;
        if (!test_droit_bas)
            cogne_droit_bas = 2;
        return cogne_gauche_haut + cogne_droit_bas;
    }


/* Retourne 0 si pas de sens possible,
            1 si uniquement gauche/haut,
            2 si uniquement droite/bas,
            3 si les deux.
 */
    public static int SensPossible (int[][]tab, int ligne, int colonne, int direction, int longueurBateau) {

        int sensgauche = 0;
        int sensdroit = 0;
        int senshaut = 0;
        int sensbas = 0;
        if (direction == 0) {
            //Horizontal, gauche
            if ((colonne+1) - longueurBateau  >= 0)
                sensgauche++;

            //Horizontal, droit
            if (longueurBateau <= tab[ligne].length - colonne)
                sensdroit = 2;

            return (sensgauche+sensdroit);
        }
        else {
            //Vertical, haut
            if ((ligne+1) - longueurBateau >= 0) {
                senshaut++;
            }
            //Vertical, bas
            if (longueurBateau <= tab.length - ligne){
                sensbas = 2;
            }
            return senshaut+sensbas;
        }

    }
    public static int SensAuto (int[][]tab, int ligne, int colonne, int direction, int longueurBateau) {
        int sens;
        //1 = gauche/haut ; 2 = droite/bas
        if(SensPossible(tab, ligne, colonne, direction, longueurBateau) == 1 )
            sens = 0;
        else
            sens = 1;
        return  sens;
    }

}




