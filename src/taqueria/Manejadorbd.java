/*
 * Falta mejorar el manejo de excepciones
 */

package taqueria;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
 
 

/**
 *
 * @author Rolando
 */
public class Manejadorbd {

    
// ORDENES debe ser inicializado con el archivo ORDENES.txt
public static File ORDENES;
// ORDENES debe ser inicializado con el archivo CLIENTES.txt
public static File CLIENTES;
private static final String separador = "/";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    }
    
    /**
     * Busca a un cliente en la base de datos. Si la persona buscada es cliente, se crea 
     * un reporte con el historial de las ordenes de dicho cliente.
     * @param nombreCliente el cliente a buscar en la base de datos.
     */
    public static void buscarEnArchivo(final String nombreCliente){
        BufferedReader br;
        String line;
        try {
            br = new BufferedReader(new FileReader(CLIENTES));
            while((line = br.readLine()) != null) {
                //asumiendo que el formato de los registros de clientes es idCliente/nombreCliente
                String[] campos = line.split(separador);
                if(campos[1].equals(nombreCliente)) {          
                    crearReporte(campos[0], nombreCliente);
                    br.close();
                    break;              
                }
            }
            br.close();
            System.out.println("Persona no es cliente");
        }
        catch(Exception e) {
            System.out.println("Persona no es cliente");
        }
    }
    /**
     * Crea un reporte basado en el historial de consumo del cliente con identificador idCliente; el reporte se 
     * escribe en el archivo Reporte_idCliente.txt
     * @param idCliente
     * @param nombreCliente
     */
    public static void crearReporte(final String idCliente, final String nombreCliente) {
    BufferedReader br;
    FileWriter fw;
    int ordenesCliente = 0;
        String line;
        try {
            br = new BufferedReader(new FileReader(ORDENES));
            //falta abodar el caso en el que el archivo Reporte_idCliente ya exista
            fw = new FileWriter(new File("Reporte_+idCliente.txt"));
            while((line = br.readLine()) != null) {
                //asumiendo que el formato de los registros de ORDENES es idCliente/Platillo/Cantidad/Total
                String[] campos = line.split(separador);
                if(campos[0].equals(idCliente)) {
                    //escribe en el archivo Reporte_idCliente una linea con formato nombreCliente/Platillo/Cantidad/Total
                    fw.write(nombreCliente + " " + campos[1] + " " + campos[2] + " " + campos[3] + "\n");
                    ordenesCliente++;
                }
            }
            br.close();
            //al final escribe el total de ordenes realizadas por el cliente
            fw.write("Total de ordenes =" + ordenesCliente);
            fw.close();
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
}