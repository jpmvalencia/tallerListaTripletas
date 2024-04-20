/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
public class DispersaF1 {

    private int Mat[][];
    private int N, M;

    public DispersaF1(int n, int m) {
        M = 3;
        N = 2;
        Mat = new int[N][M];
        Mat[0][0] = n;
        Mat[0][1] = m;
        Mat[0][2] = 0;
        Mat[1][0] = n + 1;
        Mat[1][1] = m + 1;
    }

    public int obtenerDato(int f, int c) {
        return Mat[f][c];
    }

    public void pintar(Graphics g, int f, int c) {
        Color colorFondo = new Color(233, 243, 243);
        int saltoC = 40;
        int saltoF = 30;
        g.setColor(Color.blue);
        g.drawString("MAT", c - 20, f);
        f = f + 20;

        for (int i = 0; i < Mat.length; i++) // PARA RECORRER LAS FILAS DE LA MATRIZ
        {
            c = 50;
            g.setColor(Color.red);
            g.drawString("" + (i + 1), c - 20, f + 20);
            for (int j = 0; j < Mat[i].length; j++) // PARA RECORRER LAS COLUMNAS DE LA MATRIZ
            {
                if (i == 0 || i == Mat[0][2] + 1) {
                    g.setColor(Color.yellow);

                } else if (i > Mat[0][2] + 1) {
                    g.setColor(Color.LIGHT_GRAY);
                } else {
                    g.setColor(Color.white);

                }
                g.fillRect(c, f, saltoC, saltoF);
                g.setColor(Color.blue);
                g.drawRect(c, f, saltoC, saltoF);
                g.setColor(Color.black);
                g.drawString("" + Mat[i][j], c + 10, f + 20);
                c = c + saltoC;
            }
            f = f + saltoF;
        }
    }
  
    public void redimensionar(int x) {
        int aux[][];
        if (x > 0) {
            aux = new int[N + x][M];
            for (int i1 = 0; i1 < N; i1++) {
                for (int j = 0; j < M; j++) {
                    aux[i1][j] = Mat[i1][j];
                }
            }
            N = N + x;
            Mat = aux;
        } else {
            if (N + x >= 2) {
                aux = new int[N + x][M];
                for (int i1 = 0; i1 < N + x; i1++) {
                    for (int j = 0; j < M; j++) {
                        aux[i1][j] = Mat[i1][j];
                    }
                }
                N = N + x;
                Mat = aux;
            }
        }

    }

    public void insetarDato(int f, int c, int d) {

        int i = 1, j;
        while (i < Mat[0][2] + 1 && Mat[i][0] < f) {
            i = i + 1;
        }
        while (i < Mat[0][2] + 1 && Mat[i][0] == f && Mat[i][1] < c) {

            i = i + 1;
        }
        if (i < Mat[0][2] + 1 && Mat[i][0] == f && Mat[i][1] == c) {
            Mat[i][2] = Mat[i][2] + d;
            if (Mat[i][2] == 0) {
                for (int k = i + 1; k < Mat[0][2] + 2; k++) {
                    Mat[k - 1][0] = Mat[k][0];
                    Mat[k - 1][1] = Mat[k][1];
                    Mat[k - 1][2] = Mat[k][2];
                }
                Mat[0][2] = Mat[0][2] - 1;

                redimensionar(- 1);
            }

        } else {
            if (Mat.length == Mat[0][2] + 2) {
                redimensionar(1);
            }
            for (int k = Mat[0][2] + 1; k >= i; k = k - 1) {
                Mat[k + 1][0] = Mat[k][0];
                Mat[k + 1][1] = Mat[k][1];
                Mat[k + 1][2] = Mat[k][2];
            }
            Mat[i][0] = f;
            Mat[i][1] = c;
            Mat[i][2] = d;
            Mat[0][2] = Mat[0][2] + 1;
        }

    }

    public void promedioPares(Graphics g1) {
        double promedio = 0;
        for (int i = 1; i < N - 1; i++) {
            if (Mat[i][2] % 2 == 0) {
                promedio += Mat[i][2];
                resaltarCelda(g1, i, 2);
            }
        }
        promedio /= Mat[0][2];
        JOptionPane optionPane = new JOptionPane(promedio, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(null, "Mensaje");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    public void resaltarCelda(Graphics g, int fila, int columna) {
        int f = 40 + (fila + 1) * 30; // Ajustar la coordenada Y de la fila a resaltar
        int c = 50 + columna * 40; // Ajustar la coordenada X de la columna a resaltar

        // Dibujar la celda resaltada
        g.setColor(Color.green); // Cambiar color a verde (por ejemplo)
        g.fillRect(c, f, 40, 30); // Dibujar el rectángulo de la celda resaltada
        g.setColor(Color.blue);
        g.drawRect(c, f, 40, 30); // Dibujar el borde de la celda
        g.setColor(Color.black);
        g.drawString("" + Mat[fila][columna], c + 10, f + 20); // Dibujar el contenido de la celda
    }

    public void promedioPorColumna() {
        String str = "";
        int ant, cont = 0;
        double prom = 0;
        // Copia de matriz
        int[][] copMat = new int[N - 2][3];
        for (int i = 0; i < N - 2; i++) {
            copMat[i] = Mat[i + 1].clone();
        }
        Arrays.sort(copMat, Comparator.comparingInt(arr -> arr[1]));

        ant = copMat[0][1];

        for (int[] row : copMat) {
            if (row[1] != ant && cont > 0) {
                prom /= cont;
                str += "Promedio columna " + ant + ": " + prom + "\n";
                prom = 0;
                cont = 0;
            }
            prom += row[2];
            cont++;
            ant = row[1];
        }

        if (cont > 0) {
            prom /= cont;
            str += "Promedio columna " + ant + ": " + prom + "\n";
        }
        JOptionPane optionPane = new JOptionPane(str, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(null, "Mensaje");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    public void mayPromFilas() {
        String str = "";
        int ant = Mat[1][0], cont = 0, j = 0;
        double prom = 0;
        double[][] promList = new double[N][2];
        for (int i = 1; i < N; i++) {
            if (Mat[i][0] == ant) {
                cont++;
                prom += Mat[i][2];
            } else {
                prom /= cont;
                promList[j][0] = ant;
                promList[j][1] = prom;
                j++;
                cont = 1;
                prom = Mat[i][2];
                ant = Mat[i][0];
            }
        }
        ordenarMayMen(promList);

        for (int i = 0; i < promList.length; i++) {
            if (promList[i][1] != 0) {
                str += "Fila " + promList[i][0] + ". Promedio: " + promList[i][1] + "\n";
            }
        }
        JOptionPane optionPane = new JOptionPane(str, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(null, "Mensaje");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    //pinta el que no es
    public void mayorImparPorFila(Graphics g) {
        StringBuilder str = new StringBuilder();
        boolean[] filaP = new boolean[N];

        for (int i = 1; i <= N - 1; i++) {
            int filActual = Mat[i][0];
            if (!filaP[filActual]) {
                filaP[filActual] = true;
                boolean encontradoImparEnFila = false;
                int mayorImparEnFila = 0;

                for (int j = i; j <= N - 1 && Mat[j][0] == filActual; j++) {
                    if (Mat[j][2] % 2 != 0 && Mat[j][2] > mayorImparEnFila) {
                        encontradoImparEnFila = true;
                        mayorImparEnFila = Mat[j][2];
                    }
                }

                if (encontradoImparEnFila) {
                    str.append("El mayor número impar en la fila ").append(filActual).append(" es: ").append(mayorImparEnFila).append("\n");
                    resaltarCelda(g, i, 2);
                }
            }
        }

        if (str.length() == 0) {
            str.append("No se encontraron números impares en ninguna fila.");
        }

        JOptionPane optionPane = new JOptionPane(str.toString(), JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(null, "Datos de números impares por fila");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    //FUNCIONA
    public void MayorSumaDigitos(Graphics g) {
        int mayor = 0;
        int numeroMayor = 0;
        StringBuilder str = new StringBuilder();

        for (int i = 1; i < N - 1; i++) {
            int filActual = Mat[i][0];
            boolean[] filaP = new boolean[N];
            if (!filaP[filActual]) {
                filaP[filActual] = true;
                int numero = Mat[i][2];
                int sumaD = sumaDigitos(numero);

                if (sumaD > mayor) {
                    mayor = sumaD;
                    numeroMayor = numero;
                }
            }
        }

        // Resaltar la celda que contiene el número con la mayor suma de dígitos
        for (int i = 1; i < N; i++) {
            if (Mat[i][2] == numeroMayor) {
                resaltarCelda(g, i, 2);
            }
        }

        str.append("Número con la mayor suma de dígitos: ").append(numeroMayor).append(", Suma de dígitos: ").append(mayor).append("\n");

        JOptionPane optionPane = new JOptionPane(str.toString(), JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(null, "Datos de mayor suma de dígitos");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    // Método para calcular la suma de los dígitos de un número
    public int sumaDigitos(int numero) {
        int suma = 0;
        while (numero != 0) {
            suma += numero % 10; //obtiene los residuos de las divisiones enteras que serían los ultimos digitos
            numero /= 10; //división entera y a esa le sacan el residuo y se va acumulando  
        }
        return suma;
    }

    //FUNCIONA
    public void MayorCantDigitosParFila(Component parentComponent) {
        boolean[] filaP = new boolean[N];
        StringBuilder str = new StringBuilder();

        for (int i = 1; i <= N - 1; i++) {
            int filActual = Mat[i][0];
            if (!filaP[filActual]) {
                filaP[filActual] = true;
                int mejorNumero = -1; // Inicializar el mejor número con -1 (ninguno encontrado aún)
                int mejorNumDigitosPares = 0; // Inicializar la cantidad de dígitos pares del mejor número con 0

                // Recorrer toda la fila actual
                for (int j = i; j <= N - 1 && Mat[j][0] == filActual; j++) {
                    int numero = Mat[j][2];
                    int numDigitosPares = contarDigitosPares(numero);

                    // Si el número actual tiene más dígitos pares que el mejor número encontrado hasta ahora
                    if (numDigitosPares > mejorNumDigitosPares) {
                        mejorNumero = numero;
                        mejorNumDigitosPares = numDigitosPares;
                    }
                }

                if (mejorNumDigitosPares > 0) {
                    str.append("En la fila: ").append(filActual).append(", Dato: ").append(mejorNumero);
                    str.append(" Con ").append(mejorNumDigitosPares);
                    str.append(mejorNumDigitosPares > 1 ? " Dígitos Pares\n" : " Dígito Par\n");
                }
            }
        }

        JOptionPane optionPane = new JOptionPane(str.toString(), JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(null, "Datos de mayor cantidad de dígitos pares");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    public static int contarDigitosPares(int numero) {
        int cont = 0;

        while (numero != 0) {
            int x = numero % 10;

            if (x != 0 && x % 2 == 0) {
                cont++;
            }

            numero = numero / 10;
        }
        return cont;
    }

    //FUNCIONA
    public void PorcentajeDigPares(Component parentComponent) {
        StringBuilder str = new StringBuilder();
        boolean[] filaP = new boolean[N]; // Reiniciar filaP para cada fila

        for (int i = 1; i <= N - 1; i++) {
            int filaActual = Mat[i][0];

            if (!filaP[filaActual]) {
                filaP[filaActual] = true;

                for (int j = i; j <= N - 2; j++) {
                    if (Mat[j][0] == filaActual) {
                        int dato = Mat[j][2];
                        // int totalDigitos = contarDigitos(dato);
                        int digitosPares = contarDigitosPares(dato);

                        double porcentajeDigPares = (double) digitosPares / digitosPares * 100.0;

                        str.append("Fila ").append(filaActual).append(":\n");
                        str.append("  Dato: ").append(dato).append("\n");
                        // str.append("  Total de dígitos: ").append(totalDigitos).append("\n");
                        str.append("  Total de dígitos pares: ").append(digitosPares).append("\n");
                        str.append("  Porcentaje de dígitos pares: ").append(porcentajeDigPares).append("%\n");
                    }
                }
            }
        }
        JOptionPane optionPane = new JOptionPane(str.toString(), JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(null, "Porcentaje de dígitos pares en cada dato");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    private int contarDigitos(int numero) {
        return String.valueOf(Math.abs(numero)).length();
    }

    public void OrdenarDigDatos(Component parentComponent) {
        StringBuilder str = new StringBuilder();
        boolean[] filaP = new boolean[N]; 

        for (int i = 1; i <= N - 1; i++) {
            int filaActual = Mat[i][0];

            if (!filaP[filaActual]) {
                filaP[filaActual] = true;

                for (int j = i; j <= N - 2; j++) {
                    if (Mat[j][0] == filaActual) {
                        int dato = Mat[j][2];
                        int[] digitos = descomponerNumeroEnDigitos(dato);
                        Arrays.sort(digitos); // Ordenar los dígitos
                        int datoOrdenado = recomponerDigitosEnNumero(digitos);

                        str.append("Fila ").append(filaActual).append(":\n");
                        str.append("  Dato original: ").append(dato).append("\n");
                        str.append("  Dato ordenado: ").append(datoOrdenado).append("\n");
                    }
                }
            }
        }
        JOptionPane optionPane = new JOptionPane(str.toString(), JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(null, "Dígitos ordenados en cada dato");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    private int[] descomponerNumeroEnDigitos(int numero) {
        String numeroStr = String.valueOf(numero);
        int[] digitos = new int[numeroStr.length()];
        for (int i = 0; i < numeroStr.length(); i++) {
            digitos[i] = Character.getNumericValue(numeroStr.charAt(i));
        }
        return digitos;
    }

    private int recomponerDigitosEnNumero(int[] digitos) {
        StringBuilder str = new StringBuilder();
        for (int digito : digitos) {
            str.append(digito);
        }
        return Integer.parseInt(str.toString());
    }

    private void ordenarMayMen(double[][] list) {
        double[][] aux = new double[N][2];
        for (int i = 0; i < list.length; i++) {
            for (int j = i + 1; j < list.length; j++) {
                if (list[j][1] > list[i][1]) {
                    aux[i][0] = list[j][0];
                    aux[i][1] = list[j][1];
                    list[j][0] = list[i][0];
                    list[j][1] = list[i][1];
                    list[i][0] = aux[i][0];
                    list[i][1] = aux[i][1];
                }
            }
        }
    }

    public void eliminarDato(int f, int c) {
        int i = 1;
        while (i < Mat[0][2] + 1 && Mat[i][0] < f) {
            i = i + 1;
        }
        while (i < Mat[0][2] + 1 && Mat[i][0] == f && Mat[i][1] < c) {
            i = i + 1;
        }
        if (Mat[i][0] != f || Mat[i][1] != c) {
            JOptionPane optionPane = new JOptionPane("No hay dato en la posición indicada.", JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = optionPane.createDialog(null, "Mensaje");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
            return;
        }
        if (i < Mat[0][2] + 1 && Mat[i][0] == f && Mat[i][1] == c) {
            Mat[i][2] = 0;
            if (Mat[i][2] == 0) {
                for (int k = i + 1; k < Mat[0][2] + 1; k++) {
                    Mat[k - 1][0] = Mat[k][0];
                    Mat[k - 1][1] = Mat[k][1];
                    Mat[k - 1][2] = Mat[k][2];
                }
                Mat[0][2] = Mat[0][2] - 1;

                redimensionar(-1);
            }
        }
    }
    
    
    //---------------------------punto11-Mostrar cada digito y las veces que se presenta en la matriz, ordenados por las veces que se presenta.
    
        public void contarFrecuencias(Graphics g1) {
            ArrayList<Integer> digitos = new ArrayList<>();
            ArrayList<Integer> frecuencias = new ArrayList<>();
    
            // Contar frecuencias
            for (int i = 0; i < Mat.length; i++) {
                int dato = Mat[i][2]; // Suponiendo que el tercer valor de la tripleta es el dato
                int indice = digitos.indexOf(dato);
    
                if (indice != -1) {
                    // Si el dato ya está en la lista, incrementar su frecuencia
                    frecuencias.set(indice, frecuencias.get(indice) + 1);
                } else {
                    digitos.add(dato);
                    frecuencias.add(1);
                }
            }
    
            // Ordenar por frecuencia descendente
            ordenarPorFrecuenciaDescendente(digitos, frecuencias);
    
            // Mostrar los resultados
            mostrarResultados(digitos, frecuencias);
        }
    
        private void ordenarPorFrecuenciaDescendente(ArrayList<Integer> digitos, ArrayList<Integer> frecuencias) {
            // Ordenar los dígitos y sus frecuencias en orden descendente
            ArrayList<Integer> indices = new ArrayList<>();
            for (int i = 0; i < digitos.size(); i++) {
                indices.add(i);
            }
    
            Collections.sort(indices, new Comparator<Integer>() {
                @Override
                public int compare(Integer i1, Integer i2) {
                    return frecuencias.get(i2) - frecuencias.get(i1);
                }
            });
    
            // Reordenar digitos y frecuencias según los índices ordenados
            ArrayList<Integer> digitosOrdenados = new ArrayList<>();
            ArrayList<Integer> frecuenciasOrdenadas = new ArrayList<>();
    
            for (int indice : indices) {
                digitosOrdenados.add(digitos.get(indice));
                frecuenciasOrdenadas.add(frecuencias.get(indice));
            }
    
            // Actualizar las listas originales
            digitos.clear();
            frecuencias.clear();
            digitos.addAll(digitosOrdenados);
            frecuencias.addAll(frecuenciasOrdenadas);
        }
    
        private void mostrarResultados(ArrayList<Integer> digitos, ArrayList<Integer> frecuencias) {
            // Mostrar los dígitos y sus frecuencias ordenados
            StringBuilder mensaje = new StringBuilder("Dígitos y sus frecuencias ordenados por frecuencia descendente:\n");
            for (int i = 0; i < digitos.size(); i++) {
                mensaje.append("Dígito: ").append(digitos.get(i)).append(", Frecuencia: ").append(frecuencias.get(i)).append("\n");
            }
    
            // Mostrar en un cuadro de diálogo
            JOptionPane optionPane = new JOptionPane(mensaje.toString(), JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = optionPane.createDialog(null, "Frecuencias de dígitos ordenadas");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
        }
    

    
    //------------------------------punto12- mostrar cada dato de la matriz y las veces que se presenta ( no repetir mensajes).

    public void conteoDatos(Graphics g){
    ArrayList<Integer> datosUnicos = new ArrayList<>();
    ArrayList<Integer> frecuencias = new ArrayList<>();   
    
    for (int i = 1; i < Mat[0][2] + 1; i++) {
        int dato = Mat[i][2];
        int indice = datosUnicos.indexOf(dato);

        if (indice != -1) {
            // Si el dato ya está en la lista, incrementar su frecuencia
            frecuencias.set(indice, frecuencias.get(indice) + 1);
        } else {
            datosUnicos.add(dato);
            frecuencias.add(1);
        }
    }

    // Mostrar los datos y sus frecuencias
    StringBuilder mensaje = new StringBuilder("Datos y sus frecuencias:\n");
    for (int i = 0; i < datosUnicos.size(); i++) {
        mensaje.append("Dato: ").append(datosUnicos.get(i)).append(", Frecuencia: ").append(frecuencias.get(i)).append("\n");
    }

    // Mostrar el mensaje
    JOptionPane optionPane = new JOptionPane(mensaje.toString(), JOptionPane.INFORMATION_MESSAGE);
    JDialog dialog = optionPane.createDialog(null, "Frecuencias de datos");
    dialog.setAlwaysOnTop(true);
    dialog.setVisible(true);
    
}
    //----------------------------------pintar datos de una columna dada
    public void pintarColumna(Graphics g, int columnaSeleccionada) {
    for (int i = 1; i < N-1; i++) {
        // Mat[i][0] es la fila, Mat[i][1] es la columna
        if (Mat[i][1] == columnaSeleccionada) {
            // Resaltar la celda en la fila Mat[i][0] y la columna Mat[i][1]
            resaltarCelda(g, i, 2);
        }
    }
    String mensaje = "Se han resaltado los datos de la columna " + columnaSeleccionada;
    JOptionPane optionPane = new JOptionPane(mensaje, JOptionPane.INFORMATION_MESSAGE);
    JDialog dialog = optionPane.createDialog(null, "Mensaje");
    dialog.setAlwaysOnTop(true);
    dialog.setVisible(true);
    }
    //---------------------------------punto14-Pintar los datos de una fila dada.
    public void pintarFila(Graphics g4, int filaSeleccionada){
        // Comenzamos desde 1 porque la primera fila (índice 0) contiene metadatos de la matriz
        for (int i = 1; i < N-1; i++) {
            // Mat[i][0] es la fila, Mat[i][1] es la columna
            if (Mat[i][0] == filaSeleccionada) {
                // Resaltar la celda en la fila Mat[i][0] y la columna Mat[i][1]
                resaltarCelda(g4, i, 2);
            }
        }
        String mensaje = "Se han resaltado los datos de la fila dada";
        JOptionPane optionPane = new JOptionPane(mensaje,JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(null, "Mensaje");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }
    //----------------------------------punto15-Mostrar las veces que se presenta el dato mayor de la matriz( pintarlos)

    private int encontrarDatoMayor() {
        int datoMayor = Mat[1][2];
        for (int i = 1; i < Mat[0][2] + 1; i++) {
            if (Mat[i][2] > datoMayor) {
                datoMayor = Mat[i][2];
            }
        }
        return datoMayor;
    }
    
    private int contarDatoMay(int dato) {
        int contador = 0;
        for (int i = 1; i < Mat[0][2] + 1; i++) {
            if (Mat[i][2] == dato) {
                contador++;
            }
        }
        return contador;
    }

    public void mostrarDatoMayor(Graphics g) {
        int datoMayor = encontrarDatoMayor();

        for (int i = 1; i < Mat[0][2] + 1; i++) {
            if (Mat[i][2] == datoMayor) {
                resaltarCelda(g, i, 2);}
        }
        String mensaje = "Dato may:" + datoMayor + "conteo:" + contarDatoMay(datoMayor);
        JOptionPane optionPane = new JOptionPane(mensaje, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(null, "Mensaje");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }







}
