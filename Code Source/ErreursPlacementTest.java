import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErreursPlacementTest {
    @Test
    public final void bateauCogne(){
        int[][] tab1 = {{0,0,0,1,0,0,0,0,0,0},{0,0,0,0,0,0,1,0,0,0},{1,0,0,0,1,0,0,0,0,0}};

        //cas horizontal
        //cas le bord est à gauche
        assertEquals(2, ErreursPlacement.bateauCogne(tab1,0,1,0,3));
        assertEquals(2, ErreursPlacement.bateauCogne(tab1,0,2,0,3));
        assertEquals(0, ErreursPlacement.bateauCogne(tab1,0,0,0,3));

        //cas le bord est à droite
        assertEquals(1, ErreursPlacement.bateauCogne(tab1,1,8,0,3));
        assertEquals(1, ErreursPlacement.bateauCogne(tab1,1,7,0,3));
        assertEquals(0, ErreursPlacement.bateauCogne(tab1,1,9,0,3));

        //cas bateau au milieu
        assertEquals(3, ErreursPlacement.bateauCogne(tab1,2,2,0,3));
        assertEquals(0, ErreursPlacement.bateauCogne(tab1,2,1,0,3));
        assertEquals(2, ErreursPlacement.bateauCogne(tab1,2,3,0,3));

        int[][] tab2 = {{0,0,1},{0,0,0},{0,0,0},{1,0,0},{0,0,1},{0,0,0},{0,1,0},{0,0,0},{0,0,0},{0,0,0}};

        //cas vertical
        //cas bateau bord haut
        assertEquals(2, ErreursPlacement.bateauCogne(tab2,1,0,1,3));
        assertEquals(2, ErreursPlacement.bateauCogne(tab2,2,0,1,3));
        assertEquals(0, ErreursPlacement.bateauCogne(tab2,0,0,1,3));
        //cas bateau bord bas
        assertEquals(1, ErreursPlacement.bateauCogne(tab2,7,1,1,3));
        assertEquals(1, ErreursPlacement.bateauCogne(tab2,8,1,1,3));
        assertEquals(0, ErreursPlacement.bateauCogne(tab2,9,1,1,3));
        //cas bateau au milieu
        assertEquals(3, ErreursPlacement.bateauCogne(tab2,2,2,1,3));
        assertEquals(2, ErreursPlacement.bateauCogne(tab2,3,2,1,3));
        assertEquals(0, ErreursPlacement.bateauCogne(tab2,1,2,1,3));
    }
    @Test
    public final void SensPossible(){
        int [][]tab = new int[10][10];
        //int[][] tab = {{0,1,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0}};
        //cas horizontal
        assertEquals(1, ErreursPlacement.SensPossible(tab, 0,8,0,3));
        assertEquals(2, ErreursPlacement.SensPossible(tab, 0,1,0,3));
        assertEquals(3, ErreursPlacement.SensPossible(tab, 0,2,0,3));
        assertEquals(3, ErreursPlacement.SensPossible(tab, 0,7,0,3));

        //cas vertical
        assertEquals(1, ErreursPlacement.SensPossible(tab, 8,0,1,3));
        assertEquals(2, ErreursPlacement.SensPossible(tab, 1,0,1,3));
        assertEquals(3, ErreursPlacement.SensPossible(tab, 2,0,1,3));
        assertEquals(3, ErreursPlacement.SensPossible(tab, 7,1,3,3));

    }

}