import library.Matrix;
import library.MatrixMath;



public class App {
    
    //INPUT MATRIX INITIALIZATION
    public static double[][] i = { {1,1}, {1,0}, {0,1}, {0,0}} ;
    public static Matrix inputs = new Matrix(i);
    //WEIGHT MATRIX INITIALIZATION
    public static double[][] weight = { {1, 1, -1, 1}, {1, 1, 1, -1} }; 
    public static Matrix w = new Matrix(weight);

    //variables de los humbrales
    public static double threshold[] = { 0.5, 1.5, -0.5 };

    //NEURONA PARA OR
    public static int OR( double result ) {
        //Pesos de la neurona
        //verifica si el index pasa el humbral
        if( result > threshold[0] )
            return 1;
        else
            return 0;
    }
    //NEURONA PARA AND
    public static int AND( double result ) {
        //Pesos de la neurona
        //verifica si el index pasa el humbral
        if( result > threshold[1] )
            return 1;
        else
            return 0;

    }
    //NEURONA PARA XOR
    public static int XOR( int iv1, int iv2 ) {
        //Pesos de la neurona
        double resultI[] = {iv1, iv2};
        Matrix aux = Matrix.createRowMatrix( resultI );

        //verifica si el index pasa el humbral
        if( MatrixMath.dotProduct(aux, w.getCol(2)) > threshold[0] ) 
            return 1;
        else 
            return 0;

    }

    public static int EQ( int iv1, int iv2 ) {
        
        double resultI[] = {iv1, iv2};
        Matrix aux = Matrix.createRowMatrix( resultI );
        if( MatrixMath.dotProduct(aux, w.getCol(3)) > threshold[2] ) 
            return 1;
        else
            return 0;
    }

    public static void main(String[] args) throws Exception {

        System.out.println( );
        System.out.println( "|       A        |       B         |       AND     |       OR      |       XOR      |       EQ       |" );

        //Ciclo para recorrer los valores de entrada ( inputs )
        for( int i = 0; i < inputs.getRows(); i++ ) {

            System.out.println( "|       " + inputs.get(i, 0) + "      |       " + inputs.get(i, 1) + "       |        "  + AND( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(0)) ) + "      |       " + OR( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(1)) ) +  "       |        " + XOR(AND( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(0)) ), OR( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(1)) ) ) +  "       |        " + EQ(AND( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(0)) ), OR( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(1)) ) ) + "       |" );

        }

        System.out.println();
        
    }
}
