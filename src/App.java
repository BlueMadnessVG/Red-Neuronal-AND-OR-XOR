import library.Matrix;
import library.MatrixMath;



public class App {
    
    //INPUT MATRIX INITIALIZATION
    public static double[][] i = { {1,0,1}, {0,1,1}, {0,0,1}, {1,1,1}} ;
    public static int[][] r = { {0, 0, 0, 1}, {0, 0, 1, 1}, {0, 0, 1, 1}, {1, 0, 0, 1} };
    public static Matrix inputs = new Matrix(i);
    //WEIGHT MATRIX INITIALIZATION
    public static double[][] weight = { {-0.07, 0.94, -0.22, 1}, {0.22, 0.46, 0.58, -1}, {-0.46, 0.1, 0.78, 1} }; 
    public static Matrix w = new Matrix(weight);

    public static double[] deltas = { 0, 0, 0 };
    public static double[] DeltaGrad = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    public static double[] DeltaHist = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    public static double LR = 0.7;
    public static double Momentum = 0.3;

    //variables de los humbrales
    public static double threshold[] = { 0.5, 1.5, -0.5 };

    //Funcion de activacion
    public static double AF( double result ) {
        return ( 1 / ( 1 + Math.exp( -result ) ) );
    }

    //Claculo de delta exterior
    public static void EDelta( double af, double err ) {
        deltas[2] = -err * ( af * ( 1 - af ) );
    }

    //Calculo de delta interior
    public static void IDelta( double af1, double af2, double w1, double w2 ) {
        deltas[0] = ( af1 * ( 1 - af1 ) ) * deltas[2] * w1;
        deltas[1] = ( af2 * ( 1 - af2 ) ) * deltas[2] * w2;
    }

    //Claculo Historico de delta
    public static void HDelta(){

        for( int i = 0; i < 9; i++ ){
            DeltaHist[i] = ( LR * DeltaGrad[i] ) + ( Momentum * DeltaHist[i] );

        }

    } 

    //ReValanseo de pesos
    public static void NewWeights() {
        int index = 0;
        for( int i = 0; i < 3; i++ ){

            for( int x = 0; x < 3; x++ ){

                System.out.println( weight[x][i] + "            " + (index) );
                weight[x][i] += DeltaHist[index];
                System.out.println(weight[x][i]);
                index++;
            }

        }

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
            /* if ( x == 0) {
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
            } */
            if ( x == 2) {
                System.out.println( "|       A        |       B         |       XOR        |               O1 DELTA            |" );
                //Ciclo para recorrer los valores de entrada ( inputs )
                for( int i = 0; i < inputs.getRows(); i++ ) {

                    Double af1 = AF( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(0)) );
                    Double af2 = AF( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(1)) );

                    Double aux = XOR( af1 , af2 );
                    Double error = aux - r[1][i];

                    EDelta(aux, error);
                    IDelta(af1, af2, weight[0][2], weight[1][2]);

                    for( int z = 0; z < 9; z++ ){

                        if( z >= 6 ){
                            DeltaGrad[z] += ( deltas[2] * af1 );
                            z++;
                            DeltaGrad[z] += ( deltas[2] * af2 );
                            z++;
                            DeltaGrad[z] += ( deltas[2] * 1 );
                        }
                        else if ( z >= 3 ){
                            DeltaGrad[z] += ( deltas[1] * inputs.get(i, 0) );
                            z++;
                            DeltaGrad[z] += ( deltas[1] * inputs.get(i, 1) );
                            z++;
                            DeltaGrad[z] += ( deltas[1] * 1 );
                        }
                        else {
                            DeltaGrad[z] += ( deltas[0] * inputs.get(i, 0) );
                            z++;
                            DeltaGrad[z] += ( deltas[0] * inputs.get(i, 1) );
                            z++;
                            DeltaGrad[z] += ( deltas[0] * 1 ); 
                        }
                 
                    }

                    System.out.println( "|       " + inputs.get(i, 0) + "      |       " + inputs.get(i, 1) + "       |        " + aux + "       |        " + r[1][i] + "       |        " + error + "       |        " + deltas[2]);
                    sum += Math.pow( error, 2);
                }

                System.out.println( );
                System.out.println("|           SUMATORIA         |               ESS           |               MSE          |               RMS            |");
                System.out.println("|       " + sum + "     |       " + (sum / 2) + "     |       " + ((sum / 4) * 100) + "%    |       " + (Math.sqrt(sum / 4) * 100) + "%     |");
                System.out.println( );   

                HDelta();
                NewWeights();

            }
/*             else {
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
                */ System.out.println( );   
            //}
            sum = 0.0;
        }

        System.out.println();
        
    }
}
