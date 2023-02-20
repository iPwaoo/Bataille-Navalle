public class Affichage {

    public static void affichageBombe ( int[][] tabBombe ) {

        System.out.print(" ");
        for (int i = 0; i < tabBombe.length; i++)
            if ( i == 9)
                System.out.print(" " + (i+1));
            else
                System.out.print(" 0" + (i+1));
        System.out.println();

        for (int i = 0; i < tabBombe.length; i++) {
            if ( i == 9)
                System.out.print((i+1));
            else
                System.out.print("0" + (i+1));

            for (int j = 0; j < tabBombe[ i ].length; j++) {
                switch ( tabBombe[ i ][ j ] ) {
                    case 0:
                        System.out.print(" ~ ");
                        break;
                    case 1:
                        System.out.print(" X ");
                        break;
                    case 2:
                        System.out.print(" T ");
                        break;
                    case 3:
                        System.out.print(" C ");
                        break;
                }
            }
            System.out.println();
        }
    }

    public static void affichagePlacement ( int[][] tabBateauE ) {

        System.out.print(" ");
        for (int i = 0; i < tabBateauE.length; i++)
            if ( i == 9)
                System.out.print(" " + (i+1));
            else
                System.out.print(" 0" + (i+1));

        System.out.println();

        for (int i = 0; i < tabBateauE.length; i++) {
            if ( i == 9)
                System.out.print((i+1));
            else
                System.out.print("0" + (i+1));

            for (int j = 0; j < tabBateauE[ i ].length; j++) {
                switch ( tabBateauE[ i ][ j ] ){
                    case 0:
                        System.out.print(" ~ ");
                        break;
                    case 2:
                        System.out.print(" T ");
                        break;
                    case 31:
                        System.out.print(" S1");
                        break;
                    case 32:
                        System.out.print(" S2");
                        break;
                    case 4:
                        System.out.print(" C ");
                        break;
                    case 5:
                        System.out.print(" PA");
                        break;
                }
            }
            System.out.println();

        }

    }
}
