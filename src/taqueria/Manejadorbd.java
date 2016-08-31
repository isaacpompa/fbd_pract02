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

    private static final String ORDENES = "Ordenes.txt";
    private static final String CLIENTES = "Clientes.txt";

private static final String separador = "/";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        buscarEnArchivo("Luis Perez");
    }
    
    /**
     * Busca a un cliente en la base de datos. Si la persona buscada es cliente, el método regresa el idCliente
     * de lo contrario regresa -1
     * @param nombreCliente el cliente a buscar en la base de datos.
     */
    public static int buscarCliente(final String nombreCliente) {
        BufferedReader br;
        String line;
        try {
            br = new BufferedReader(new FileReader(CLIENTES));
            while((line = br.readLine()) != null) {
                //asumiendo que el formato de los registros de clientes es idCliente/nombreCliente
                String[] campos = line.split(separador);
                if(campos[1].equals(nombreCliente)) {        
                    br.close();
                    return Integer.parseInt(campos[0]);
                }
            }
            br.close();           
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }
    
    /**
     * Busca a un cliente en la base de datos para crear
     * un reporte con el historial de las ordenes de dicho cliente.
     * @param nombreCliente el cliente a buscar en la base de datos.
     */
    public static void buscarEnArchivo(final String nombreCliente){
        int idCliente = buscarCliente(nombreCliente);
        if(idCliente != -1) {
            crearReporte(idCliente, nombreCliente);
        }
        else {
            System.out.println("Persona no es cliente");
        }
    }
                 
                    
    /**
     * Crea un reporte basado en el historial de consumo del cliente con identificador idCliente; el reporte se 
     * escribe en el archivo Reporte_idCliente.txt
     * @param idCliente
     * @param nombreCliente
     */
    public static void crearReporte(final int idCliente, final String nombreCliente) {
    BufferedReader br;
    FileWriter fw;
    int ordenesCliente = 0;
        String line;
        try {
            br = new BufferedReader(new FileReader(ORDENES));
            //falta abodar el caso en el que el archivo Reporte_idCliente ya exista
            fw = new FileWriter(new File("Reporte_"+idCliente+".txt"));
            fw.write("Cliente: " + nombreCliente + System.getProperty("line.separator"));
            while((line = br.readLine()) != null) {
                //asumiendo que el formato de los registros de ORDENES es idCliente/Platillo/Cantidad/Total
                String[] campos = line.split(separador);
                if(Integer.parseInt(campos[0]) == idCliente) {                    
                    //escribe en el archivo Reporte_idCliente una linea con formato Platillo/Cantidad/Total
                    fw.write(campos[1] + " " + campos[2] + " " + campos[3] + System.getProperty("line.separator"));
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