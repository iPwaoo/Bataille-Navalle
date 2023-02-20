

/* Classe pour la stratégie utilisée par l'IA*/
public class StrategieIA {

    /* Méthode de placement de bombe aléatoire lorsque la partie commence ou lorsque aucun bateau n'a encore été coulé par l'IA */
    public static int[] PlacementBombeDebut_IA(int[][] tabJoueur){
   int ligne, colonne;
   int[] tabPosition = new int [2];

//   Si la ligne est paire alors la colonne est paire ; et inversement

        ligne = (int)(Math.random()*tabJoueur.length);
        colonne = (int) (Math.random() * tabJoueur[0].length);

        if (ligne%2 != 0 ) {
            if(colonne%2 == 0)
                if (colonne == 0)
                    colonne++;
                else if(colonne == tabJoueur[0].length-1)
                    colonne--;
                else colonne++;
        }
        else {
            if (colonne % 2 != 0) {
                if(colonne == tabJoueur[0].length-1)
                    colonne--;
                else colonne++;
            }
        }

    tabPosition[0] = ligne;
    tabPosition[1] = colonne;

    return tabPosition;

    }
     /* Placement des bombes de l'IA lorsqu'un bateau a été touché le tour précédent :
     * L'IA placera des bombes à droite puis à gauche de la case touchée précédemment.
     * S'il trouve un bateau dans l'une de ces zones, EstHorizontal est vrai et il continuera à chercher à l'horizontal
     * Sinon s'il ne trouve pas de bateau, alors EstHorizontal est faux et il cherchera à la verticale
     * */
    public static int[] PlacementBombeBateauTrouve_IA(int[][] tabBombe, int[][] tabJoueur, int ligne, int colonne, int ligneToucherPremiereFois, int colonneToucherPremiereFois){
        boolean PotentiellementHorizontal;
        int[] tabPosition = new int[2];

        if (EstHorizontal(tabBombe, tabJoueur, ligne, colonne, ligneToucherPremiereFois, colonneToucherPremiereFois)){
            //cas bombe déjà à gauche ou mur à gauche
            if (colonne != tabJoueur[0].length- 1 && !ModeDeJeu.bombePresent(tabBombe, ligne, colonne+1 )){
                tabPosition[0] = ligne;
                tabPosition[1] = colonne+1;
            }
            //cas la bombe n'a rien touché après avoir touché un bateau puis allée vers la droite
            else if(colonne != tabJoueur[0].length- 1 && !ModeDeJeu.bateauPresent(tabJoueur, ligne, colonne +1)) {
                ligne = ligneToucherPremiereFois;
                colonne = colonneToucherPremiereFois;
                tabPosition[0] = ligne;
                tabPosition[1] = colonne-1;
            }
//            cas bombe déjà à droite
            else {
                tabPosition[0] = ligne;
                tabPosition[1] = colonne - 1;
            }
        }
        else {
            //cas bombe déjà en haut ou mur en haut
            if (ligne != tabJoueur.length-1 && !(ModeDeJeu.bombePresent(tabBombe, ligne + 1, colonne))) {
                tabPosition[0] = ligne + 1;
                tabPosition[1] = colonne;
            }
            //cas la bombe n'a rien touché après avoir touché un bateau puis allée vers le bas
            else if (ligne != tabJoueur.length-1 && !ModeDeJeu.bateauPresent(tabJoueur, ligne+1, colonne)) {
                ligne = ligneToucherPremiereFois;
                colonne = colonneToucherPremiereFois;
                tabPosition[0] = ligne - 1;
                tabPosition[1] = colonne;
            }
            //cas la bombe déjà en bas
            else {
                tabPosition[0] = ligne - 1;
                tabPosition[1] = colonne;
            }
        }
    return tabPosition;
    }


/* Retourne un booleen qui permet à l'IA de savoir si le bateau est (ou peut être) horizontal, ou non en fonction des infos qu'il a */
    public static boolean EstHorizontal (int[][] tabBombe, int[][] tabJoueur, int ligne, int colonne, int ligneToucherPremiereFois, int colonneToucherPremiereFois){
        boolean PotentiellementHorizontal = false, estHorizontal = false;
        if (colonneToucherPremiereFois == 0) {
            if (ModeDeJeu.bombePresent(tabBombe, ligneToucherPremiereFois, colonneToucherPremiereFois + 1) && ModeDeJeu.bateauPresent(tabJoueur, ligneToucherPremiereFois, colonneToucherPremiereFois + 1))
                estHorizontal = true;
        }
        else if (colonneToucherPremiereFois == tabJoueur.length-1) {
            if (ModeDeJeu.bombePresent(tabBombe, ligneToucherPremiereFois, colonneToucherPremiereFois - 1) && ModeDeJeu.bateauPresent(tabJoueur, ligneToucherPremiereFois, colonneToucherPremiereFois - 1))
                estHorizontal = true;
        }
        else {
            if ( ModeDeJeu.bombePresent(tabBombe, ligneToucherPremiereFois, colonneToucherPremiereFois + 1) && ModeDeJeu.bateauPresent(tabJoueur, ligneToucherPremiereFois, colonneToucherPremiereFois + 1) || ModeDeJeu.bombePresent(tabBombe, ligneToucherPremiereFois, colonneToucherPremiereFois - 1) && ModeDeJeu.bateauPresent(tabJoueur, ligneToucherPremiereFois, colonneToucherPremiereFois - 1))
                estHorizontal = true;
        }


        PotentiellementHorizontal = ( SensPossibleBombe_IA(tabBombe, ligneToucherPremiereFois, colonneToucherPremiereFois)[ 2 ] == 1 && !ModeDeJeu.bombePresent(tabBombe, ligne, colonne - 1) ) || ( SensPossibleBombe_IA(tabBombe, ligneToucherPremiereFois, colonneToucherPremiereFois)[ 3 ] == 1 && !ModeDeJeu.bombePresent(tabBombe, ligne, colonne + 1) );

        if(estHorizontal)
            return  true;
        else
            return PotentiellementHorizontal;
    }

    /*  Retourne un tableau qui montre les différents sens où l'IA peut placer sa bombe en fonction de la dernière case touchée où il y avait un bateau
        Indice 0 = senshaut
        Indice 1 = sensbas
        Indice 2 = sensgauche
        Indice 3 = sensdroite
        tabPositionBateauTrouve[1] = ligne
        tabPositionBateauTrouve[2] = colonne
        */
    public static int[] SensPossibleBombe_IA (int[][] tabBombe, int ligne, int colonne){
        int[] tabSensPossibleBombe = new int[4];
        int sensgauche = 0, sensdroite= 0, senshaut = 0, sensbas = 0;

        if (ligne != 0 && !ModeDeJeu.bombePresent(tabBombe, ligne-1, colonne))
            senshaut = 1;
        if (ligne != tabBombe.length-1 && !ModeDeJeu.bombePresent(tabBombe, ligne+1, colonne))
            sensbas = 1;
        if(colonne != 0 && !ModeDeJeu.bombePresent(tabBombe,ligne, colonne-1))
            sensgauche = 1;
        if(colonne != tabBombe[0].length-1 && !ModeDeJeu.bombePresent(tabBombe, ligne, colonne+1))
            sensdroite = 1;

        tabSensPossibleBombe[0] = senshaut;
        tabSensPossibleBombe[1] = sensbas;
        tabSensPossibleBombe[2] = sensgauche;
        tabSensPossibleBombe[3] = sensdroite;

        return  tabSensPossibleBombe;
    }
}
