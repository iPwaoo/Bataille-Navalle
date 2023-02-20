import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModeDeJeuTest {

    @Test
    void bateauPresent () {
        int[][] tab = {{1, 0, 1, 0, 0}, {0, 0, 2, 0, 0}, {0, 0, 0, 0, 0}};
        assertFalse(ModeDeJeu.bateauPresent(tab, 0, 1));
        assertTrue(ModeDeJeu.bateauPresent(tab, 1, 2));
    }

    @Test
    void bombePresent () {
        int[][] tab = {{1, 0, 1, 0, 0}, {0, 0, 2, 0, 0}, {0, 0, 0, 0, 0}};
        assertFalse(ModeDeJeu.bombePresent(tab, 0, 1));
        assertTrue(ModeDeJeu.bombePresent(tab, 1, 2));
    }

    @Test
    void couler () {
        int[][] tab1 = {{15,2,31,93,93},{5,0,4,0,0},{5,96,12,0,0},{5,96,0,0,0},{15,96,4,2,6}};

        assertFalse(ModeDeJeu.couler(tab1, 0, 3));
        assertTrue(ModeDeJeu.couler(tab1, 2, 1));
        assertTrue(ModeDeJeu.couler(tab1, 3, 1));
        assertTrue(ModeDeJeu.couler(tab1, 4, 1));
        assertFalse(ModeDeJeu.couler(tab1,0,0));
        assertFalse(ModeDeJeu.couler(tab1,4,4));
        assertFalse(ModeDeJeu.couler(tab1,4,0));
        assertFalse(ModeDeJeu.couler(tab1,2,2));
    }

}