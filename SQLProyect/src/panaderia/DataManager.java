package panaderia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataManager {
	
	
	public static ArrayList<Empleado> getEmpleadosFromDB(DBConnection conn, boolean sync) {
		 ArrayList<Empleado> empleados=null;
		 try {
	            // Obtener datos de la tabla "empleado"
	            // Suponiendo que el objeto de conexión se llama "conn"
	           
	            ResultSet resultSet = conn.query("SELECT * FROM empleado");
	            System.out.println("LLEGA HASTA AQUI22222");
	            if(resultSet !=null) {
	            	empleados = new ArrayList<>();
	            while (resultSet.next()) {
	            	Empleado empleado = new Empleado(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), conn, false);

	                empleados.add(empleado);
	                
	                
	                
	            }
	            for (Empleado empleado: empleados) {
	            empleado.setSync(true);  
	            }
	            }

	            ///OJO EN ESTA OPCION TENDRIAMOS QUE METERLO EN EL WHILE
	            //O BIEN QUE SE ACTIVE LA SINCRONIZACION INDIVIDUALMENTE PARA CADA OBJETO EMPLEADO DESPUES DE CREARLO
	           // empleado.setSync(true);
               // empleados.add(empleado);
	            
	           
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null; // Retornar null en caso de excepción
	        }
		 return empleados;
	}
	
	
	public static ArrayList<Empleado> getEmpleadosFromCSV(String filename, DBConnection conn, boolean sync) {
		 ArrayList<Empleado> empleados = new ArrayList<>();

		 try (Scanner br = new Scanner(new File(filename))) {
			   br.nextLine();
	                
			   
			   while(br.hasNextLine()) {
				   	String line=br.nextLine();System.out.println("llega hasta aqui");
				  
	                    String[] values = line.split(";",-1);
	                   
	                    int id_empleado ;
	                    if(values[0].trim().equals("")){
	                    	id_empleado=DBConnection.NULL_SENTINEL_INT;
	                    }else {
	                    	id_empleado = Integer.parseInt(values[0].trim());
	                    }
	                    
	                     System.out.println("lega");
	                    int i=1;
	                    while(i<values.length) {
	                    if(values[i].trim().equals("")){
	                    	values[i]=DBConnection.NULL_SENTINEL_VARCHAR;
	                    }else {
	                    	values[i]=values[i].trim();
	                    }
	                    i++;
	                    }
	                    
	                 	System.out.println("SON"+id_empleado+ values[1]+ values[2]+ values[3]+ values[4]);
	                 	
	                    Empleado empleado = new Empleado(id_empleado, values[1], values[2], values[3], values[4], conn, sync); //LO QUE HEMOS ECHO AQUI ES CREARLO Y A LA VEZ METERLO, NO SE PUEDE CREAR Y NO METERLO YA QUE EN EL CONSTRUCTOR 
		                    //DE LA CLASE YA INSERTA EL ELEMENTO DE NORMAL, Y POR LO TANTO SI LUEGO HICIESEMOS UN ENTRY TAMPOCO FUNCIONARIA YA QUE TIENE 
		                    //tenemos un problema porque si esto lo definimos como falso, da igual que luego hagamos un insertEntry, que si esta a falso no va a funcionar 
		                empleados.add(empleado);
		                
	                    }
	            return empleados;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return null;
	}
	
	/*private int id_local;
	private boolean tiene_cafeteria;
	private String direccion;
	private String descripcion;*/
	public static ArrayList<Local> getLocalesFromDB(DBConnection conn, boolean sync) {
		 ArrayList<Local> locales = new ArrayList<>();
		 try {

	            ResultSet resultSet = conn.query("SELECT * FROM local");
	            System.out.println("LLEGA HASTA AQUI22222");
	            
	            while (resultSet.next()) {
	                System.out.println("LLEGA HASTA AQUI");
	                Local local = new Local(resultSet.getInt(1), resultSet.getBoolean(2), resultSet.getString(3), resultSet.getString(3), conn, false);

	                // Añadir el objeto Empleado al ArrayList
	                locales.add(local);
	            

	            }
	            /*Se activa la sincronizacion para todos los objetos empleado del arraylist al mismo tiempo, en caso de que el parámetro sync sea tru
	           */
	            for (Local local2: locales) {
	    	            local2.setSync(true);  
	    	            }
	            ///OJO EN ESTA OPCION TENDRIAMOS QUE METERLO EN EL WHILE
	            //O BIEN QUE SE ACTIVE LA SINCRONIZACION INDIVIDUALMENTE PARA CADA OBJETO EMPLEADO DESPUES DE CREARLO
	           // empleado.setSync(true);
               // empleados.add(empleado);
	             
	           
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null; // Retornar null en caso de excepción
	        }
		 return locales;
	}
	
	public static ArrayList<Local> getLocalesFromCSV(String filename, DBConnection conn, boolean sync) {
		 ArrayList<Local> locales = new ArrayList<>();

		 try (Scanner br = new Scanner(new FileReader(filename))) {
		
			    br.nextLine();

			    while(br.hasNextLine()) {
			    	    String line=br.nextLine();
                        String[] values = line.split(";",-1);
                        int id_local;
                        boolean tiene_cafeteria;
	                  
	                    if(values[0].trim().equals("")){
	                    	id_local=DBConnection.NULL_SENTINEL_INT;
	                    }else {
	                    	id_local=Integer.parseInt(values[0].trim());
	                    }
	                    if(values[1].trim().equals("") || values[1].trim().equals("0")){
	                    	tiene_cafeteria=false;
	                    }else {
	                    	tiene_cafeteria=true;
	                    }
	                    
	                    int j=2;
	                    while(j<values.length) {
		                    if(values[j].trim().equals("")){
		                    	values[j]=DBConnection.NULL_SENTINEL_VARCHAR;
		                    }else {
		                    	values[j]=values[j].trim();
		                   }
		                    j++;
		                   }
	                    
	                   
	                    Local local = new Local(id_local, tiene_cafeteria, values[2], values[3], conn, sync);

		                locales.add(local);
	                    
	                }
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	
		return locales;
	}

}
