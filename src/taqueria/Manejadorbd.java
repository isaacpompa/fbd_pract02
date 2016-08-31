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

    private String ORDENES = "Ordenes.txt";
    private String CLIENTES = "Clientes.txt";
    private File menu;
    private final String separador = "/";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        buscarEnArchivo("Luis Perez");
    }
    
    
    // Se podría construir el Manejador recibiendo como parámetros
    //las rutas de los archivos Menú, Clientes y Órdenes.
    
    
    public int actualizarArchivo(String actualizacion){
        return 0;
    }
    
    /**
     * Crea una orden cuyo registro quedará guardado en el archivo Órdenes
     * se permite agregar a un nuevo usuario durante el proceso de la orden
     * 
     * @param 
     */
    public void crearOrden(String nombreCliente, String platillo, int cantidad) {
        int idCliente = buscarCliente(nombreCliente);
        //verificamos si es cliente, si no lo es, se agrega a Clientes.txt
        if(idCliente == -1) {
            //agrega al nuevo cliente y obtiene su id
            idCliente = actualizarArchivo(nombreCliente);
        }
        int precio = obtenerPrecio(platillo);
        int total = cantidad * precio;
        try{
        FileWriter fw = new FileWriter(ORDENES,true);
        fw.write(idCliente+separador+platillo+separador+cantidad+separador+total+System.getProperty("line.separator"));
        fw.close();
            }catch(Exception e){
        System.err.println(e.getMessage());
        }
        
    }
    
    /**
     * Obtiene el precio de un platillo del menu.
     * @param platillo
     */
    private int obtenerPrecio(String platillo) {
        BufferedReader br;
        String line;
        try {
            br = new BufferedReader(new FileReader(menu));
            //busca la linea en donde está el platillo para obtener el precio del mismo
            while((line = br.readLine()) != null) {
                //asumiendo que el formato de los registros del menu es platillo/precio
                String[] campos = line.split(separador);
                if(campos[0].equals(platillo)) {        
                    br.close();
                    return Integer.parseInt(campos[1]);
                }
            }
            br.close();           
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }
    /**
     * Busca a un cliente en la base de datos. Si la persona buscada es cliente, el método regresa el idCliente
     * de lo contrario regresa -1
     * @param nombreCliente el cliente a buscar en la base de datos.
     */
    public int buscarCliente(final String nombreCliente) {
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
    public void buscarEnArchivo(final String nombreCliente){
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
    private void crearReporte(final int idCliente, final String nombreCliente) {
        BufferedReader br;
        FileWriter fw;
        int ordenesCliente = 0;
        String line;
        try {
            br = new BufferedReader(new FileReader(ORDENES));
            fw = new FileWriter(new File("Reporte_"+idCliente+".txt"));
            //escribe el nombre del cliente en el reporte
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