package panaderias;

import java.util.ArrayList;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
public class DataManager {
	
	public static ArrayList<Empleado> getEmpleadosFromDB(DBConnection conn, boolean sync) {
		 
			String sql = "SELECT * FROM Empleado";
			ArrayList<Empleado> empleados = new ArrayList<>();
		
			try {
				ResultSet rs = conn.query(sql);
		
				while (rs.next()) {
					int id_empleado=Integer.parseInt(rs.getString("id_empleado"));
					String n_ss = rs.getString("n_ss") ;
				    String nombre=rs.getString("nombre");
					String apellido1=rs.getString("apellido1");
					String apellido2=rs.getString("apellido2");
				Empleado empleado = new Empleado(id_empleado, n_ss, nombre, apellido1,apellido2, conn, sync); 
				empleado.setSync(sync);
				empleados.add(empleado);
					}
		
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		
			return empleados;
		}
		
	
	
	public static ArrayList<Empleado> getEmpleadosFromCSV(String filename, DBConnection conn, boolean sync) {
		ArrayList<Empleado> empleados = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Saltar la línea de cabecera
                }

                String[] datos = line.split(";");

                if (datos.length != 5) {
                    System.out.println("Error: formato de línea incorrecto");
                    return null;
                }
				int id_empleado =Integer.parseInt(datos[0].trim());
                String n_ss = datos[1].trim();
				String nombre = datos[2].trim();
                String apellido1 = datos[3].trim();
                String apellido2 = datos[4].trim();

				Empleado empleado = new Empleado(id_empleado, n_ss, nombre, apellido1,apellido2, conn, sync); 
                empleados.add(empleado);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

		return empleados ;
	}

	
	public static ArrayList<Local> getLocalesFromDB(DBConnection conn, boolean sync) {
	String sql = "SELECT * FROM Local";
	boolean tiene_cafeteria=false;
			ArrayList<Local> locales = new ArrayList<>();
		
			try {
				ResultSet rs = conn.query(sql);
		
				while (rs.next()) {
		        int id_local=Integer.parseInt(rs.getString("id_local"));;
				if(Integer.parseInt(rs.getString("tiene_cafeteria"))==1){
					tiene_cafeteria =true;
				   }
	             String direccion= rs.getString("direccion") ;
	              String descripcion= rs.getString("descripcion");
				Local local = new Local(id_local,tiene_cafeteria,direccion,descripcion, conn, sync); 
				local.setSync(sync);
				locales.add(local);
					}
		
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		
			return locales;
	}
	
	public static ArrayList<Local> getLocalesFromCSV(String filename, DBConnection conn, boolean sync) {
		ArrayList<Local> locales = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;
            boolean tiene_cafeteria=false;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Saltar la línea de cabecera
                }

                String[] datos = line.split(";");

                if (datos.length != 5) {
                    System.out.println("Error: formato de línea incorrecto");
                    return null;
                }
				int id_local =Integer.parseInt(datos[0].trim());
				if(Integer.parseInt(datos[1].trim())==1){
                 tiene_cafeteria =true;
				}
				String direccion = datos[2].trim();
                String descripcion = datos[3].trim();
               

				Local local = new Local(id_local,tiene_cafeteria,direccion,descripcion, conn, sync); 
                locales.add(local);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

		return locales ;
	}

}
