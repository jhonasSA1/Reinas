import java.util.Arrays;

/**
 *
 * @author Jonas
 */
public class Caballos {
    
    private int NUM_CABALLOS;
    private int[] solution;

    public Caballos(int NUM_CABALLOS) {
        this.NUM_CABALLOS = NUM_CABALLOS;
        solution = new int[NUM_CABALLOS];
        init();
        // String strArray = Arrays.toString(solution);
        // System.out.println(strArray);
    }

    public void init() {
        for (int i = 0; i < solution.length; i++) {
            solution[i] = -1;
        }
    }

    public void searchSolution() {
        init();
        backtracking(solution, 0);
    }

    public boolean backtracking(int[] solucion, int caballo) {
        boolean success = false;
        // CONDICION PARA EVALUAR SI HEMOS PROBADO TODAS LAS REINAS
        if (caballo < NUM_CABALLOS) {// CASO BASE
            do {
                solucion[caballo]++;// [0,-1,-1,-1] // [0,2,-1,-1]
                // Es para determinar las soluciones parciales
                boolean valid = isValid(solution, caballo);
                String strSol = Arrays.toString(solucion);
                System.out.println(strSol + " " + (valid ? "SOL PARCIAL " : "")
                        + (valid && (caballo == NUM_CABALLOS - 1) ? "SOLUTION" : ""));
                if (valid) {
                    // reina = reina + 1;
                    success = backtracking(solucion, caballo + 1);
                }
            } while (solution[caballo] < (NUM_CABALLOS - 1) && (!success));
            solucion[caballo] = -1;
        } else {

        }
        return success;
    }

    // ESTUDIAR Y EXPLICAR LA SIGUIENTE CLASE COMO ES QUE SE DETERMINA
    // SI LA RESTRINCCION SE CUMPLE O NO (FILA Y DIAGONALES)
    public boolean isValid(int[] solution, int caballo) {
        boolean ok = true;
        for (int i = 0; i < caballo; i++) {
            if((solution[i+1]+2)==solution[caballo] || (solution[i+1]-2)==solution[caballo]) {
                ok = false;
                break;
            }
            if(i+2<=NUM_CABALLOS-1) {
                if((solution[i+2]+1)==solution[caballo] || (solution[i+2]-1)==solution[caballo]) {
                    ok = false;
                    break;
                }
            }
        }
        return ok;
        
       
    }
    
    
    public static void main(String[] args) {
        
        Caballos caba = new Caballos(4);
        caba.searchSolution();
        
        
    }
    
}