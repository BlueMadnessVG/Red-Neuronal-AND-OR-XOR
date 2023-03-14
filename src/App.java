import java.io.Console;
import java.util.Scanner;

import library.Matrix;
import library.MatrixMath;



public class App {
    
    //INPUT MATRIX INITIALIZATION
    public static double[][] i = { {0.8,-0.2,1}, {0.7,0.05,1}, {1,0.2,1}, {0.21,0.43, 1}, {-0.76, 0.55, 1}, {0.12, -0.32, 1}} ;
    public static int[][] r = { {1, 1, 1, 0, 0, 0} };
    public static Matrix inputs = new Matrix(i);
    //WEIGHT MATRIX INITIALIZATION
    public static double[][] weight = { {0.1, -0.09, -0.01}, {-0.09, 0.04, 0.09}, {-0.09, 0.06, 0.05} }; 
    public static Matrix w = new Matrix(weight);

    public static double[] deltas = { 0, 0, 0 };
    public static double[] DeltaGrad = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    public static double[] DeltaHist = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    public static double LR = 0.7;
    public static double Momentum = 0.3;
    public static int epoch = 1;
    public static double mse = 100;

    public static double input = 2;
    public static double hidden = 2;

    //variables de los humbrales
    public static double threshold[] = { 0.5, 1.5, -0.5 };

    //Funcion de activacion
    public static double AF( double result ) {
        return ( 1 / ( 1 + Math.exp( -result ) ) );
    }

    //Calcular NGUYEN-WIDROW
    public static void NGUYENWIDROW() {

        double aux = (1 / input);
        Double beta =  0.7 * ( Math.pow(hidden, ( aux )) );
        Double sumatoria = 0.0;

        for( int i = 0; i < hidden; i++ ){
            for( int y = 0; y < input + 1; y++ ){
                sumatoria += Math.pow(weight[y][i], 2);
            }
            sumatoria = Math.sqrt(sumatoria);
            for( int x = 0; x < input + 1; x++ ){
                System.out.println(weight[x][i]);
                weight[x][i] = ( beta * weight[x][i] ) / sumatoria;
                System.out.println(weight[x][i]);
            }
            sumatoria = 0.0;
        }
        w = new Matrix(weight);
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
                if(index != 9) {
                    weight[x][i] += DeltaHist[index];
                    index++;
                }
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

            //Ciclo para recorrer los valores de entrada ( inputs )
            for( int i = 0; i < inputs.getRows(); i++ ) {

                Double af1 = AF( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(0)) );
                Double af2 = AF( MatrixMath.dotProduct(inputs.getRow(i), w.getCol(1)) );
                Double aux = XOR( af1 , af2 );

                Double error = aux - r[0][i];

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

                sum += Math.pow( error, 2);
            }

            mse = (sum / 6) * 100; 

            HDelta();
            NewWeights();

        sum = 0.0;

    }

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.print( "Utilizar NGUYEN-WIDROW en los pesos (1 .- si, 2 .- no): " );
        int message = scanner.nextInt();

        boolean Ciclo = true;

        if( message == 1 ){
            NGUYENWIDROW();
        }

        while( epoch != 1001 ){
            Epoch();
            epoch++;
        }

        while( Ciclo ){

            System.out.println();

            System.out.print( "Ingrese el valor del peso: " );
            Double ip1 = scanner.nextDouble();
            System.out.print( "Ingrese el valor del color: " );
            Double ip2 = scanner.nextDouble();

            double[][] ip = { { ip1, ip2, 1 } };
            Matrix ipM = new Matrix(ip);

            Double af1 = AF( MatrixMath.dotProduct(ipM.getRow(0), w.getCol(0)) );
            Double af2 = AF( MatrixMath.dotProduct(ipM.getRow(0), w.getCol(1)) );
            Double aux = XOR( af1 , af2 );

            System.out.println();
            if( aux > 0.5 )
                System.out.println("Salidas: " + aux + "        |   Tipo de frutas: Melon");
            else 
                System.out.println("Salidas: " + aux + "        |   Tipo de frutas: Manzana");

            System.out.println();
            System.out.print( "Finalizar comparaciones: " );
            Ciclo = scanner.nextBoolean();
        }

        
    }
}
