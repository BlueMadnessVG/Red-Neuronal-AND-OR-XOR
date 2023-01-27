public class App {
    //datos de entrada
    public static int inputs[][] = { { 1 , 1 }, { 1 , 0 }, { 0 , 1 }, { 0 , 0 } };
    //variables de los humbrales
    public static double threshold[] = { 0.5, 1.5, -0.5 };

    // calcula el peso del la entrada
    public static double weightCal( int w[], int iv1, int iv2 ){
        
        //CALCULO DE I * W
        double i1 = iv1 * w[ 0 ];
        double i2 = iv2 * w[ 1 ];
        //suma de los resultados
        return (i1 + i2);
    }

    //NEURONA PARA OR
    public static int OR( int iv1, int iv2 ) {
        //Pesos de la neurona
        int w[] = { 1, 1 };
        //verifica si el index pasa el humbral
        if( weightCal(w, iv1, iv2) > threshold[0] )
            return 1;
        else
            return 0;
    }
    //NEURONA PARA AND
    public static int AND( int iv1, int iv2 ) {

        //Pesos de la neurona
        int w[] = { 1, 1 };
        //verifica si el index pasa el humbral
        if( weightCal(w, iv1, iv2) > threshold[1] )
            return 1;
        else
            return 0;

    }
    //NEURONA PARA XOR
    public static int XOR( int iv1, int iv2 ) {
        //Pesos de la neurona
        int w[] = { -1, 1 };
        //verifica si el index pasa el humbral
        if( weightCal(w, AND(iv1, iv2), OR(iv1, iv2)) > threshold[0] ) 
            return 1;
        else 
            return 0;

    }

    public static int EQ( int iv1, int iv2 ) {
        
        int w[] = { 1 , -1 };
        if( weightCal(w, AND(iv1, iv2), OR(iv1, iv2)) > threshold[2] ) 
            return 1;
        else
            return 0;
    }

    public static void main(String[] args) throws Exception {
        
        System.out.println();
        System.out.println( "|       A      |       B       |       AND     |       OR      |       XOR      |       EQ       |" );

        //Ciclo para recorrer los valores de entrada ( inputs )
        for( int i = 0; i < 4; i++ ) {

            System.out.println( "|       " + inputs[i][0] + "      |       " + inputs[i][1] + "       |        "  + AND(inputs[i][0], inputs[i][1]) + "      |       " + OR(inputs[i][0], inputs[i][1]) +  "       |        " + XOR(inputs[i][0], inputs[i][1]) +  "       |        " + EQ( inputs[i][0], inputs[i][1]) + "       |" );

        }

        System.out.println();
        
    }
}
