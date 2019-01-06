/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cambiobilletes;

/**
 *
 * @author diego
 */
public class CambioBilletesMain {

    /**
     * @param args the command line arguments
     */
    private static final int[] valoresBilletes = {1, 2, 5, 10, 20, 50, 100};
    
    private static void imprimeMatrices(int[][] matriz){
        for (int x=0; x < matriz.length; x++) {
            
            for (int y=0; y < matriz[x].length; y++) {
                if (matriz[x][y] == 1000000) System.out.print("∞");
                else System.out.print (matriz[x][y]);
                
                if (y!=matriz[x].length-1) System.out.print("\t");
            }
            
            System.out.println("\n");
        }
    }
    
    private static void imprimeArray(int[] array){
        System.out.print("{");
        for (int i = 0; i < array.length; i++){
            if (i == array.length-1) System.out.print(array[i] + "}");
            else System.out.print(array[i] + ", ");
        }
        System.out.println();
    }
    
    /*
    *   Se ha decidido tomar 1.000.000 como infinito, ya que considera un numero que no se alcanzará
    */
    
    private static int[] cambioBilletes(int D, int[] billetes, int[] disp){
        //La matriz debe ser con tantas filas como billetes +1 haya y de tantas columnas como valor de D + 1
        //por tanto, recuerda durante la lectura, que no coinciden exactamente los valores de billetes y disp con Mb
        int[][] Mb = new int[billetes.length + 1][D + 1];//la primera columna va con 0, y la primera fila con infinitos
        int[] cambio = new int[disp.length];
        int i, j, d, n, resto = 0;
        
        for (i = 0; i < Mb.length; i++){
            Mb[i][0] = 0;//iniciamos la primera columna a 0
        }
        
        for (i = 1; i < Mb[0].length; i++){
            Mb[0][i] = 1000000;//inicimaos la primera fila a ∞ (1.000.000)
        }
        
        imprimeMatrices(Mb);//Muestra de la matriz q se usará
        System.out.println("\n\n");
        
        for (i = 1; i < Mb.length; i++){// /!\ para Mb i va bien, para disp y billetes, no, empiezan en 0, recordemos
            for (j = 1; j <= D; j++){
                resto = Mb[i-1][j];
                if (billetes[i-1]<=j) {
                    if (j <= billetes[i-1] * disp[i-1])//Check si hay suficientes monedas
                        resto = Integer.min(resto, Integer.min(Mb[i - 1][j - billetes[i-1]] + 1, Mb[i][j - billetes[i-1]] + 1));
                    else //No quedan suficientes billetes
                        //comprobamos que cuesta más, devolver el valor en los billetes ya reconocidos
                        //p combinar los billetes de insuficiente valor combinado, con los de menor valor
                        resto = Integer.min(resto, Mb[i - 1][j - disp[i-1] * billetes[i-1]]);
                }
                
                Mb[i][j] = resto;
            }
        }
        
        imprimeMatrices(Mb);//imprimimos la nueva matriz
        
        if (Mb[billetes.length][D] < 1000000){// <-- tendrá solucion
            d = D;
            n = Mb.length - 1;
            while (d > 0 && n > 0){
                if (Mb[n][d] == Mb[n - 1][d])
                    //ya no quedan más billetes del valor y hay que pasar al siguiente menor
                    n--;
                else {
                    //vmos restando el valor del billete hasta que no podamos más
                    cambio[n - 1] = cambio[n - 1]+1;
                    d = d - billetes[n - 1];
                }
            }
        }
        
        return cambio;
    }
    
    public static void main(String[] args) {
        int[] billetesDisponibles = {8, 5, 2, 1, 0, 2, 3}, billetes;
        int dinero = 21;
        
        System.out.println("Disponemos de los siguientes billetes:\n");
        
        for (int i = 0; i < valoresBilletes.length; i++){
            System.out.println(billetesDisponibles[i] + " Billete(s) de " + valoresBilletes[i]);
        }
        
        billetes = cambioBilletes(dinero, valoresBilletes, billetesDisponibles);
        System.out.println("Se devolverán:");
        imprimeArray(billetes);
    }
    
}
