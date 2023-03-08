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
    public static int epoch = 1;
    public static double mse = 1;

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
            DeltaGrad[i] = 0;
        }

    } 

    //Calculo de las nuevas deltas
    public static void CalDelta(double input1, double input2, int i, int deltaI) {
        DeltaGrad[i] += ( deltas[deltaI] * input1 );
        DeltaGrad[i + 1] += ( deltas[deltaI] * input2 );
        DeltaGrad[i + 2] += ( deltas[deltaI] * 1 );
    }

    //ReValanseo de pesos
    public static void NewWeights() {
        int index = 0;
        for( int i = 0; i < 3; i++ ){
            for( int x = 0; x < 3; x++ ){
                weight[x][i] += DeltaHist[index];
                index++;
            }
        }
        w = new Matrix(weight);
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

    
    public static void Epoch() {
        

        Double sum = 0.0;

            System.out.println( "|       A        |       B         |       XOR        |               O1 DELTA            |" );
            //Ciclo para recorrer los valores de entrada ( inputs )
            for( int i = 0; i < inputs.getRows(); i++ ) {

                Double af1 = AF( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(0)) );
                Double af2 = AF( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(1)) );

                Double aux = XOR( af1 , af2 );
                Double error = aux - r[1][i];

                EDelta(aux, error);
                IDelta(af1, af2, weight[0][2], weight[1][2]);

                for( int z = 0; z < 9; z += 3 ){
                    if( z >= 6 ){
                        CalDelta(af1, af2, z, 2);
                    }
                    else if ( z >= 3 ){
                        CalDelta( inputs.get(i, 0), inputs.get(i, 1), z, 1 );
                    }
                    else {
                        CalDelta(inputs.get(i, 0), inputs.get(i, 1), z, 0);
                    }
                }

                System.out.println( "|       " + inputs.get(i, 0) + "      |       " + inputs.get(i, 1) + "       |        " + aux + "       |        " + r[1][i] + "       |        " + error + "       |        " + deltas[0]);
                sum += Math.pow( error, 2);
            }

            mse = (sum / 4) * 100;

            System.out.println( );
            System.out.println("|           SUMATORIA         |               ESS           |               MSE          |               RMS            |");
            System.out.println("|       " + sum + "     |       " + (sum / 2) + "     |       " + mse + "%    |       " + (Math.sqrt(sum / 4) * 100) + "%     |");
            System.out.println( );   

            HDelta();
            NewWeights();

        sum = 0.0;

        System.out.println();

    }

    public static void main(String[] args) throws Exception {

        while( mse != 0.00001 ){

            System.out.println("============================================================================== EPOCA " + epoch + 1 + " ==============================================================================");
            System.out.println();

            Epoch();
            epoch++;
        }
        
        
    }
}
