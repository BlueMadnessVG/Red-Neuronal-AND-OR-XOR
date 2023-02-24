import library.Matrix;
import library.MatrixMath;



public class App {
    
    //INPUT MATRIX INITIALIZATION
    public static double[][] i = { {0,0,1}, {0,1,1}, {1,0,1}, {1,1,1}} ;
    public static int[][] r = { {0, 0, 0, 1}, {0, 1, 1, 1}, {0, 1, 1, 0}, {1, 0, 0, 1} };
    public static Matrix inputs = new Matrix(i);
    //WEIGHT MATRIX INITIALIZATION
    public static double[][] weight = { {-2.468, 4.61, -4.3665, 1}, {-2.438, 4.636, 8.17533, -1}, {1.1162, -2.254, -1.8211, 1} }; 
    public static Matrix w = new Matrix(weight);

    //variables de los humbrales
    public static double threshold[] = { 0.5, 1.5, -0.5 };

    //Funcion de activacion
    public static double AF( double result ) {
        return ( 1 / ( 1 + Math.exp( -result ) ) );
    }

    //NEURONA PARA OR
    public static double OR( double result ) {
        //Pesos de la neurona
        //verifica si el index pasa el humbral
        return AF(result);
    }
    //NEURONA PARA AND
    public static double AND( double result ) {
        //Pesos de la neurona
        //verifica si el index pasa el humbral
        return AF(result);
    }
    //NEURONA PARA XOR
    public static double XOR( Double iv1, Double iv2 ) {
        //Pesos de la neurona
        double resultI[] = {iv1, iv2, 1.0};
        Matrix aux = Matrix.createRowMatrix( resultI );
        double x = MatrixMath.dotProduct(aux, w.getCol(2));

        //verifica si el index pasa el humbral
        return AF(x);
    }

    public static double EQ( Double iv1, Double iv2 ) {
        
        double resultI[] = {iv1, iv2, 1};
        Matrix aux = Matrix.createRowMatrix( resultI );
        double x = MatrixMath.dotProduct(aux, w.getCol(3));

        return AF(x);
    }

    public static void main(String[] args) throws Exception {

        Double sum = 0.0;

        for( int x = 0; x < 4; x++ ){
            if ( x == 0) {
                System.out.println( "|       A        |       B         |                AND              |      IDEAL     |        ERROR       " );
                //Ciclo para recorrer los valores de entrada ( inputs )
                for( int i = 0; i < inputs.getRows(); i++ ) {
                    Double aux = AND( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(0)) );
                    Double error = r[0][i] - aux;
                    System.out.println( "|       " + inputs.get(i, 0) + "      |       " + inputs.get(i, 1) + "       |        " + aux + "       |        " + r[0][i] + "       |        " + Math.round(Math.abs( error * 100 ) ) + " % ");
                    sum += Math.pow( error, 2);
                }

                System.out.println( );
                System.out.println("|           SUMATORIA         |               ESS           |               MSE          |               RMS            |");
                System.out.println("|       " + sum + "     |       " + (sum / 2) + "     |       " + ((sum / 4) * 100) + "%    |       " + (Math.sqrt(sum / 4) * 100) + "%     |");
                System.out.println( );       

            }
            else if ( x == 1 ) {
                System.out.println( "|       A        |       B         |       OR" );
                //Ciclo para recorrer los valores de entrada ( inputs )
                for( int i = 0; i < inputs.getRows(); i++ ) {
                    Double aux = OR( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(1)) );
                    Double error = r[1][i] - aux;
                    System.out.println( "|       " + inputs.get(i, 0) + "      |       " + inputs.get(i, 1) + "       |        " + aux + "       |        " + r[1][i] + "       |        " + Math.round(Math.abs( error * 100 ) ) + " % ");
                    sum += Math.pow( error, 2);
                }

                System.out.println( );
                System.out.println("|           SUMATORIA         |               ESS           |               MSE          |               RMS            |");
                System.out.println("|       " + sum + "     |       " + (sum / 2) + "     |       " + ((sum / 4) * 100) + "%    |       " + (Math.sqrt(sum / 4) * 100) + "%     |");
                System.out.println( );   
            }
            else if ( x == 2) {
                System.out.println( "|       A        |       B         |       XOR" );
                //Ciclo para recorrer los valores de entrada ( inputs )
                for( int i = 0; i < inputs.getRows(); i++ ) {
                    Double aux = XOR(AND( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(0)) ), OR( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(1)) ) );
                    Double error = r[1][i] - aux;
                    System.out.println( "|       " + inputs.get(i, 0) + "      |       " + inputs.get(i, 1) + "       |        " + aux + "       |        " + r[1][i] + "       |        " + Math.round(Math.abs( (error) * 100 ) ) + " %" );
                    sum += Math.pow( error, 2);
                }

                System.out.println( );
                System.out.println("|           SUMATORIA         |               ESS           |               MSE          |               RMS            |");
                System.out.println("|       " + sum + "     |       " + (sum / 2) + "     |       " + ((sum / 4) * 100) + "%    |       " + (Math.sqrt(sum / 4) * 100) + "%     |");
                System.out.println( );   
            }
            else {
                System.out.println( "|       A        |       B         |       EQ" );
                //Ciclo para recorrer los valores de entrada ( inputs )
                for( int i = 0; i < inputs.getRows(); i++ ) {
                    double aux = EQ(AND( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(0)) ), OR( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(1)) ) );
                    Double error = r[3][i] - aux;
                    System.out.println( "|       " + inputs.get(i, 0) + "      |       " + inputs.get(i, 1) + "       |        " + aux + "       |        " + r[3][i] + "       |        " + Math.round(Math.abs( error * 100 ) ) + " % ");
                    sum += Math.pow( error, 2);
                }

                System.out.println( );
                System.out.println("|           SUMATORIA         |               ESS           |               MSE          |               RMS            |");
                System.out.println("|       " + sum + "     |       " + (sum / 2) + "     |       " + ((sum / 4) * 100) + "%    |       " + (Math.sqrt(sum / 4) * 100) + "%     |");
                System.out.println( );   
            }
            sum = 0.0;
        }

        System.out.println();
        
    }
}
