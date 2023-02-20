import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrategieIATest {
    @Test
    final void PlacementBombeBateauTrouve_IA(){
        int[] tabPosition;
         //vers le bas
        int[] tabCas1 = {1,0};
        int[][] tabBombeCas1 = {{1,1,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}};
        int[][] tabJoueur1 = {{3,0,0,0,0}, {3,0,0,0,0}, {3,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}};
        tabPosition = StrategieIA.PlacementBombeBateauTrouve_IA(tabBombeCas1,tabJoueur1,0,0,0,0);
        assertArrayEquals(tabCas1,tabPosition);

            //vers le haut
        int[] tabCas2 = {3,0};
        int[][] tabBombeCas2 = {{0,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}, {1,1,0,0,0}};
        int[][] tabJoueur2 = {{0,0,0,0,0}, {0,0,0,0,0}, {3,0,0,0,0}, {3,0,0,0,0}, {3,0,0,0,0}};
        tabPosition = StrategieIA.PlacementBombeBateauTrouve_IA(tabBombeCas2,tabJoueur2,4,0,4,0);
        assertArrayEquals(tabCas2,tabPosition);

            //vers la gauche
        int[] tabCas3 = {0,4};
        int[][] tabBombeCas3 = {{0,0,0,1,0}, {0,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}};
        int[][] tabJoueur3 = {{0,0,3,3,3}, {0,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}};
        tabPosition = StrategieIA.PlacementBombeBateauTrouve_IA(tabBombeCas3,tabJoueur3,0,3,0,3);
        assertArrayEquals(tabCas3,tabPosition);
            //vers la droite
        int[] tabCas4 = {0,3};
        int[][] tabBombeCas4 = {{0,0,0,0,1}, {0,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}};
        int[][] tabJoueur4 = {{0,0,3,3,3}, {0,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}, {0,0,0,0,0}};
        tabPosition = StrategieIA.PlacementBombeBateauTrouve_IA(tabBombeCas4,tabJoueur4,0,4,0,4);
        assertArrayEquals(tabCas4,tabPosition);

    }

    @Test
    final void EstHorizontal(){
        int[][] tabBombe = {{0,0,1,1,1}, {0,0,0,0,1}, {0,1,1,1,0}, {0,0,0,0,0}, {0,0,0,0,0}};
        int[][] tabJoueur = {{0,3,3,3,0}, {0,0,0,0,2}, {0,0,3,0,2}, {0,0,3,0,0}, {0,0,3,0,0}};

        //
        assertTrue(StrategieIA.EstHorizontal(tabBombe, tabJoueur, 0, 4, 0, 2));
        assertTrue(StrategieIA.EstHorizontal(tabBombe, tabJoueur, 1, 4, 1, 4));
        assertFalse(StrategieIA.EstHorizontal(tabBombe, tabJoueur, 2, 1, 2, 2));
    }

    @Test
    final void SensPossibleBombe_IA(){
        int[][] tabBombe = {{0,0,0,0,0}, {0,1,0,0,1}, {1,0,0,0,0}, {0,1,0,1,0}, {0,0,1,0,0}};
        int[] tabSens;
        int[] TousSensPossible = { 1, 1, 1, 1 };
        int[] SensHaut = {1, 0, 0, 0};
        int[] SensBas = {0, 1, 0, 0};
        int[] SensGauche = {0, 0, 1, 0};
        int[] SensDroite = {0, 0, 0, 1};

        //On peut mettre des bombes partout autour de la case
        tabSens = StrategieIA.SensPossibleBombe_IA(tabBombe,3,3);
        assertArrayEquals(TousSensPossible, tabSens);
        //Uniquement en haut
        tabSens = StrategieIA.SensPossibleBombe_IA(tabBombe,1,0);
        assertArrayEquals(SensHaut,tabSens );
        //Uniquement en bas
        tabSens = StrategieIA.SensPossibleBombe_IA(tabBombe,3,0);
        assertArrayEquals(SensBas, tabSens);
        //Uniquement à gauche
        tabSens = StrategieIA.SensPossibleBombe_IA(tabBombe,0,4);
        assertArrayEquals(SensGauche, tabSens);
        //Uniquement à droite
        tabSens = StrategieIA.SensPossibleBombe_IA(tabBombe,4,3);
        assertArrayEquals(SensDroite, tabSens);


    }

}