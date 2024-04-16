/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
public class DispersaF1
{
    private int Mat[][];
    private int N , M;
    public DispersaF1(int n, int m)
    {
        M =3;
        N = 2;
        Mat = new int[N][M];
        Mat[0][0]= n;
        Mat[0][1]= m;
        Mat[0][2]= 0;
        Mat[1][0]= n+1;
        Mat[1][1]= m+1;
    }
    public int obtenerDato(int f, int c)
    {
        return Mat[f][c];
    }

    public void pintar(Graphics g, int f, int c)
    {
        Color colorFondo= new Color(233,243,243);
        int saltoC= 40;
        int saltoF = 30;
        g.setColor(Color.blue);
        g.drawString("MAT", c-20, f);
        f= f +20;

        for(int i =0 ;i<Mat.length;i++)			// PARA RECORRER LAS FILAS DE LA MATRIZ
        {
            c=50;
            g.setColor(Color.red);
            g.drawString(""+ ( i+1), c-20, f+20);
            for(int j =0; j<Mat[i].length;j++)   // PARA RECORRER LAS COLUMNAS DE LA MATRIZ
            {
                if(i ==0  || i==Mat[0][2]+1)
                {
                    g.setColor(Color.yellow);

                }
                else if( i>Mat[0][2]+1)
                {
                    g.setColor(Color.LIGHT_GRAY);
                }
                else
                {
                    g.setColor(Color.white);

                }
                g.fillRect(c,f,saltoC,saltoF);
                g.setColor(Color.blue);
                g.drawRect(c,f,saltoC,saltoF);
                g.setColor(Color.black);
                g.drawString(""+ Mat[i][j], c+10, f+20);
                c = c+ saltoC;
            }
            f=f+saltoF;
        }
    }
    public void redimensionar( int x)
    {
        int aux[][];
        if( x > 0)
        {
            aux = new int[N+x][M];
            for(int  i1=0 ; i1< N; i1++ )
            {
                for( int j=0;j<M;j++)
                {
                    aux[i1][j]=Mat[i1][j];
                }
            }
            N = N+x;
            Mat = aux;
        }
        else
        {
            if( N+ x >=2)
            {
                aux = new int[N+x][M];
                for(int  i1=0 ; i1< N+x; i1++ )
                {
                    for( int j=0;j<M;j++)
                    {
                        aux[i1][j]=Mat[i1][j];
                    }
                }
                N = N+x;
                Mat = aux;
            }
        }

    }



    public void insetarDato( int f, int c, int d )
    {

        int i =1,j;
        while( i < Mat[0][2] +1&&  Mat[i][0]<f)
        {
            i= i+1;
        }
        while( i < Mat[0][2] +1&&  Mat[i][0]==f &&Mat[i][1]<c)
        {

            i= i+1;
        }
        if(i < Mat[0][2]  +1 &&  Mat[i][0]==f &&Mat[i][1]==c)
        {
            Mat[i][2] = Mat[i][2] + d ;
            if( Mat[i][2]  ==0)
            {
                for( int k =i+1; k < Mat[0][2]  +2; k++)
                {
                    Mat[k-1][0]=Mat[k][0];
                    Mat[k-1][1]=Mat[k][1];
                    Mat[k-1][2]=Mat[k][2];
                }
                Mat[0][2]=Mat[0][2]-1;

                redimensionar(- 1 );
            }

        }
        else
        {
            if( Mat.length==Mat[0][2] +2)
            {
                redimensionar( 1 );
            }
            for( int k= Mat[0][2] +1; k>=i;        k=k-1)
            {
                Mat[k+1][0]=Mat[k][0];
                Mat[k+1][1]=Mat[k][1];
                Mat[k+1][2]=Mat[k][2];
            }
            Mat[i][0]=f;
            Mat[i][1]=c;
            Mat[i][2]=d;
            Mat[0][2]=Mat[0][2]+1;
        }

    }

    public void promedioPares(Graphics g1) {
        double promedio = 0;
        for (int i = 1; i < N-1; i++) {
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
        g.drawString(""+ Mat[fila][columna], c+10, f+20); // Dibujar el contenido de la celda
    }
    
    public void promedioPorColumna() {
        String str = "";
        int ant, cont = 0;
        double prom = 0;
        // Copia de matriz
        int[][] copMat = new int[N - 2][3];
        for (int i = 0; i < N - 2; i++) {
            copMat[i] = Mat[i+1].clone();
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
        int i = 1, j;
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
    //punto11-Mostrar cada digito y las veces que se presenta en la matriz, ordenados por las veces que se presenta.
    
    //punto12- mostrar cada dato de la matriz y las veces que se presenta ( no repetir mensajes).
    //punto13-Pintar los datos de una columna dada.
    public void pintarColumna (int c){

    }
    //punto14-Pintar los datos de una fila dada.
    //punto15-Mostrar las veces que se presenta el dato mayor de la matriz( pintarlos)

}