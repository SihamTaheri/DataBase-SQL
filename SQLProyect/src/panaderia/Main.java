package panaderia;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws SQLException {
		
		
			//DBConnection objeto= new DBConnection("127.0.0.1",3306,"panaderia_user","panaderia_pass","panaderias");
			DBConnection objeto= new DBConnection("127.0.0.1",3306,"root","ELKACHACHI","asociacion_cervecera");

			objeto.connect();//INICIALIZAMOS EL DRIVER
			//objeto.update("INSERT INTO gusta VALUES (100, 200)");//tmbn puedes crear tablas aqui
			/*
			ResultSet rs=objeto.query("\r\n"
					+ "SELECT * "
					+ "FROM gusta\r\n"
					+ "WHERE ID_socio = 1;");
			
			while(rs.next()) {
				System.out.println(rs.getString("ID_socio"));
				}
			*/
	    	//objeto.update("INSERT INTO empleado VALUES (444444, '5555-5555', 'Cristina', 'Martinez', 'Callejas');");
			boolean resul=objeto.tableExists("empleado");
			System.out.println(resul);
		
			/*
			String filename = Main.class.getResource("empleados2.csv").getPath();
            boolean sync = true; // Activar sincronización en la inserción de filas
            ArrayList<Empleado> empleados = DataManager.getEmpleadosFromCSV(filename, objeto, sync);
            
            if(empleados !=null) {
            	for(int i=0;i<empleados.size();i++) {
            		 System.out.println(empleados.get(i).getN_ss());
            	}
            }*/
			/*
			String filename = Main.class.getResource("locales.csv").getPath();
            boolean sync = true; // Activar sincronización en la inserción de filas
            ArrayList<Local> locales = DataManager.getLocalesFromCSV(filename, objeto, sync);
            
            if(locales !=null) {
            	for(int i=0;i<locales.size();i++) {
            		 System.out.println(locales.get(i).getDescripcion());
            	}
            }
			*/
			/*
            Empleado pepe= new Empleado (8,"perrrrra", null , "dj4444f", "nMMMMsn",objeto,true);
            pepe.setApellido2("micasa");
            */
			/*
			java.sql.Date fecha_ini2 = Date.valueOf("2007-02-04");
			java.sql.Date fecha_fin2 = Date.valueOf("2007-02-04");
			
			
			Trabaja pepe= new Trabaja (4,4,fecha_ini2,fecha_fin2,objeto,true);
          //  pepe.getEntryChanges();
           // pepe.updateEntry();
           pepe.deleteEntry();
           // pepe.deleteEntry();
           /* 
            ArrayList<Empleado> empleados=DataManager.getEmpleadosFromDB(objeto,true);
            
            */
            
         //  Local pepe2= new Local (235,true,"dj4444f", "nMMMMsn",objeto,true);
         //  pepe2.updateEntry();
           //  pepe2.setDescripcion("micasa");
           // pepe2.deleteEntry();
         //  }
			
            
		
		} 
		}


