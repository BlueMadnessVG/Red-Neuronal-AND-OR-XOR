public class App {

    public static int inputs[][] = { { 1 , 1 }, { 1 , 0 }, { 0 , 1 }, { 0 , 0 } };
    public static double threshold[] = { 0.5, 1.5, 0.5 };

    // calcula el peso del la entrada
    public static double weightCal( int w[], int iv1, int iv2 ){

        double i1 = iv1 * w[ 0 ];
        double i2 = iv2 * w[ 1 ];

        return (i1 + i2);
    }

    //NEURONA PARA OR
    public static int OR( int iv1, int iv2 ) {

        int w[] = { 1, 1 };
        double wi = weightCal(w, iv1, iv2);

        if( wi > threshold[0] ) {
            return 1;
        }
        else{
            return 0;
        }
    }
    //NEURONA PARA AND
    public static int AND( int iv1, int iv2 ) {

        int w[] = { 1, 1 };
        double wi = weightCal(w, iv1, iv2);

        if( wi > threshold[1] ) {
            return 1;
        }
        else{
            return 0;
        }

    }
    //NEURONA PARA XOR
    public static int XOR( int iv1, int iv2 ) {

        int w[] = { -1, 1 };

        int andw = AND(iv1, iv2);
        int orw = OR(iv1, iv2);

        double i1 = andw * w[ 0 ];
        double i2 = orw* w[ 1 ];

        if( (i1 + i2) > threshold[2] ) {
            return 1;
        }
        else {
            return 0;
        }

    }


    public static void main(String[] args) throws Exception {

        System.out.println( "A      |       B       |       AND     |       OR      |       XOR" );
        System.out.println();

        for( int i = 0; i < 4; i++ ) {

            System.out.println( inputs[i][0] + "      |       " + inputs[i][1] + "       |        "  + AND(inputs[i][0], inputs[i][1]) + "      |       " + OR(inputs[i][0], inputs[i][1]) +  "       |        " + XOR(inputs[i][0], inputs[i][1]) );

        }

        
    }
}
