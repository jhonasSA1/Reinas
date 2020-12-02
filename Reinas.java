import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Jonas
 */
public class Reinas extends JFrame {
    private static final long serialVersionUID = 1L;

    private int NUM_REINAS;
    private int ind;
    private int[] solution;
    private final static int SIZE = 600;
    private JPanel panelBoard;
    private List<int[]> listSolutions;
    private JButton[][] btnCells;

    public Reinas(int NUM_REINAS) {
        this.NUM_REINAS = NUM_REINAS;
        this.ind=0;
        solution = new int[NUM_REINAS];
        init();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SIZE, SIZE);

        BorderLayout gestorLayout = new BorderLayout();
        // GridLayout gestor = new GridLayout(3,3);
        // setLayout(gestor);
        setLayout(gestorLayout);

        panelBoard = new JPanel();
        listSolutions = new LinkedList<>();

        add(BorderLayout.NORTH, getOptions());
        add(BorderLayout.SOUTH, getResult());
        add(BorderLayout.CENTER, getBoard());

        setLocationRelativeTo(this);
        setVisible(true);

        // String strArray = Arrays.toString(solution);
        // System.out.println(strArray);
    }

    public JPanel getBoard() {
        panelBoard.removeAll();
        panelBoard.revalidate();
        panelBoard.repaint();
        btnCells = new JButton[NUM_REINAS][NUM_REINAS];
        GridLayout gestor = new GridLayout(NUM_REINAS, NUM_REINAS);
        panelBoard.setLayout(gestor);
        for (int i = 0; i < NUM_REINAS; i++) {
            for (int j = 0; j < NUM_REINAS; j++) {
                JButton cell = new JButton(" ");
                if ((i + j) % 2 == 0) {
                    cell.setBackground(Color.GRAY);
                } else {
                    cell.setBackground(Color.WHITE);
                }
                cell.setEnabled(false);
                btnCells[i][j] = cell;
                panelBoard.add(cell);
            }
        }
        panelBoard.revalidate();
        panelBoard.repaint();
        return panelBoard;
    }

    public JPanel getOptions() {
        JPanel panelNorth = new JPanel();
        /*
         * FlowLayout gestor = new FlowLayout(); panelNorth.setLayout(gestor);
         */
        JLabel lbnnumreinas = new JLabel("NUMERO DE REINAS: ");
        panelNorth.add(lbnnumreinas);
        JTextField txtNumReinas = new JTextField(10);
        panelNorth.add(txtNumReinas);
        JButton btnGo = new JButton("Go");

        btnGo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strNumReinas = txtNumReinas.getText();
                NUM_REINAS = Integer.parseInt(strNumReinas);
                solution =new int[NUM_REINAS];
                getBoard();
                
                searchSolution();
                getResult();
                paintSolution(listSolutions.get(ind));
            }
        });

        panelNorth.add(btnGo);
        return panelNorth;
    }

    public void paintSolution(int[] s) {
        for (int i = 0; i < s.length; i++) {
            btnCells[s[i]][i].setText("X");
        }
    }
    public void limpiarTabla(){
        for (int i = 0; i < NUM_REINAS; i++) {
            for(int j=0;j<NUM_REINAS;j++)
                btnCells[i][j].setText("");
        }
    }
    public JPanel getResult() {
        JPanel panelSouth = new JPanel();
        JLabel lbnmsjcantidad = new JLabel("CANT SOLUCION: ");
        JLabel lbnCantidad = new JLabel("0");
        JButton btnPreview = new JButton("<<");
        JLabel lbnNumSolCurrent = new JLabel("0");
        JButton btnNext = new JButton(">>");

        btnPreview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {      
                if(ind>0){               
                    lbnCantidad.setText(String.valueOf(listSolutions.size()));
                    limpiarTabla();        
                    paintSolution(listSolutions.get(ind-1));  
                    ind--;         
                    lbnNumSolCurrent.setText(String.valueOf(ind));
                }     
            }
        });
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ind<listSolutions.size()-1){        
                    lbnCantidad.setText(String.valueOf(listSolutions.size()));
                    limpiarTabla();               
                    paintSolution(listSolutions.get(ind+1));   
                    ind++;
                    lbnNumSolCurrent.setText(String.valueOf(ind));
                }
            }
        });


        panelSouth.add(lbnmsjcantidad);
        panelSouth.add(lbnCantidad);
        panelSouth.add(btnPreview);
        panelSouth.add(lbnNumSolCurrent);
        panelSouth.add(btnNext);
        return panelSouth;
    }

    public void init() {
        for (int i = 0; i < solution.length; i++) {
            solution[i] = -1;
        }
    }

    public void searchSolution() {
        init();
        listSolutions.clear();
        backtracking(solution, 0);
    }

    public boolean backtracking(int[] solucion, int reina) {
        boolean success = false;
        // CONDICION PARA EVALUAR SI HEMOS PROBADO TODAS LAS REINAS
        if (reina < NUM_REINAS) {// CASO BASE
            do {
                solucion[reina]++;// [0,-1,-1,-1] // [0,2,-1,-1]
                // Es para determinar las soluciones parciales
                boolean valid = isValid(solution, reina);

                if (valid) {
                    // reina = reina + 1;
                    success = backtracking(solucion, reina + 1);
                    if (reina == NUM_REINAS - 1) {
                        String strSol = Arrays.toString(solucion);
                        System.out.println(strSol);
                        int[] sClone = solucion.clone();
                        listSolutions.add(sClone);
                        // System.out.println(strSol + " " + (valid ? "SOL PARCIAL " : "") + (valid &&
                        // (reina == NUM_REINAS - 1) ? "SOLUTION COMPLETA" : ""));
                    }
                }
            } while (solution[reina] < (NUM_REINAS - 1) && (!success));
            solucion[reina] = -1;
        } else {

        }
        return success;
    }

    // ESTUDIAR Y EXPLICAR LA SIGUIENTE CLASE COMO ES QUE SE DETERMINA
    // SI LA RESTRINCCION SE CUMPLE O NO (FILA Y DIAGONALES)
    public boolean isValid(int[] solution, int reina) {
        boolean ok = true;
        for (int i = 0; i < reina; i++) {
            if ((solution[i] == solution[reina]) || (Math.abs(solution[i] - solution[reina]) == Math.abs(i - reina))) {
                ok = false;
                break;
            }
        }
        return ok;
    }

    public static void main(String[] args) {
        Reinas reina = new Reinas(4);
        // reina.searchSolution();
    }
}