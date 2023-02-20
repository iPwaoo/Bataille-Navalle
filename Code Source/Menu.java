import java.util.Scanner;

public class Menu {

    public static void MenuJeu() {

        Scanner scanner = new Scanner(System.in).useDelimiter("\n");

        int choix;
        String s;

        do {
            System.out.println("1-Règles du jeu\n2-Jouer\n3-Jouer contre une IA\n4-Quitter");
            s = scanner.nextLine();
            choix = ModeDeJeu.erreurDeSaisie(s);

            switch (choix) {
                case 1:
                    System.out.println("Une grille de jeu numérotée de 0 à 9 horizontalement et verticalement.\nBateaux disponibles :\n1 porte avion (5 cases)\n1 croiseur (4 cases)\n2 sous-marin (3 cases)\n1 torpilleur (2 cases)");
                    System.out.println("En 1 contre 1, vous devez couler tous les bateaux de votre ennemi avant que lui ne le fasse.\nTour par tour, vous enverrez des missiles sur la grille de votre adversaire pour toucher et couler les bateaux adverses.\nBonne chance !\n");
                    break;

                case 2:
                    ModeDeJeu.jeu();
                    break;

                case 3:
                    IA.jeu();
                    break;

                case 4:
                    System.out.println("Au revoir");
                    break;

                default:
                    System.out.println("Mauvaise saisie");
                    break;
            }
        } while (choix <= 1 || choix >= 5);
    }
}
