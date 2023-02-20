/* Classe pour tester l'IA sans devoir refaire toutes les manipulations*/
public class TestIA {
    public static void main(String[] args) {

        int tour = 0, longueurTab = 5, largeurTab = 5, compteurCouleJoueur = 0, compteurCouleIA = 0, nbBateauACoule = 3;

        /*  indice 0 0 = compteurCoule
            indice 1 = première position de la bombe si touche un bateau (0 = ligne, 1 = colonne)
            indice 2 = dernière position de la bombe si touche un bateau (0 = ligne, 1 = colonne)
         */
        int[][] Premier_DernierePositionBombe = new int [3][2];
        int[][] tabIA_Bateau,tabJoueurBombe,tabIA_Bombe;

        tabIA_Bateau = new int[ longueurTab ][ largeurTab ];
        tabJoueurBombe = new int[ longueurTab ][ largeurTab ];
        tabIA_Bombe = new int[ longueurTab ][ largeurTab ];

        //tableau qui contient en x le nombre de longueurs différentes et en y le nombre de bateaux par longueur
        int[][] NombreBateau = {{2, 1}, {3, 2}, {4, 1}, {5, 1}};

        int[][] tabJoueurBateau = {{2,2,0,31,0}, {0,0,0,31,0}, {0,0,0,31,0}, {0,0,0,0,0}, {0,32,32,32,0}};



        //Deuxième phase du jeu.
        //Le tour par tour avec l'IA

            while (compteurCouleJoueur != nbBateauACoule && compteurCouleIA != nbBateauACoule) {

                if (tour % 2 == 0) {
                    compteurCouleJoueur = IA.tourJoueur(tabJoueurBombe, tabIA_Bateau,compteurCouleJoueur);

                } else
                    compteurCouleIA = IA.tourIA_Difficile(tabIA_Bombe, tabJoueurBateau, Premier_DernierePositionBombe)[0][0];

                tour++;
            }


        if ( compteurCouleJoueur == nbBateauACoule )
            System.out.println("Félicitations! Vous avez coulé tous les bateaux enemies !!");
        else
            System.out.println("Dommage! Vous avez perdu !!");
    }
}
