package panaderia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Empleado extends DBTable {
	
	private int id_empleado;
	private String n_ss;
	private String nombre;
	private String apellido1;
	private String apellido2;

	public Empleado(int id_empleado, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_empleado = id_empleado;
		if(conn.connect() && DBSync) {
			createTable();
			if (!insertEntry()){
				
				DBSync = false;
			}
		}
		
	}
	
	public Empleado(int id_empleado, String n_ss, String nombre, String apellido1, String apellido2, DBConnection conn, boolean DBSync) {
		//se pasa la conexión
		super(conn, DBSync);
		//se pasan los atributos
		this.n_ss = n_ss;
		this.id_empleado = id_empleado;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		if(conn.connect() && DBSync) {
			createTable();
			if (!insertEntry()){
				DBSync = false;
			}
		}
	}
	public int getId_empleado() {
		if(DBSync) {
			if(conn.connect()) {
				getEntryChanges();
				return id_empleado;
			}
		}
		return DBConnection.NULL_SENTINEL_INT;
	}
	
	public String getN_ss() {
		if(DBSync) {
			if(conn.connect()) {
				getEntryChanges();
				return n_ss;
			}
		}
		
		return DBConnection.NULL_SENTINEL_VARCHAR;
	}

	
	public void setN_ss(String n_ss) {
		if(DBSync) {
			if(conn.connect()) {
            getEntryChanges();
            this.n_ss = n_ss;
            updateEntry();
        }
	}else {
		    this.n_ss = n_ss;
	}
	}
	public String getNombre() {
		if(DBSync) {
			if(conn.connect()) {
				getEntryChanges();
				return nombre;
			}
		}
		return DBConnection.NULL_SENTINEL_VARCHAR;
	}

	public void setNombre(String nombre) {
		if(DBSync) {
			if(conn.connect()) {
            getEntryChanges();
            this.nombre = nombre;
            updateEntry();
        }
	}else {
		   this.nombre = nombre;
	}
	}

	public String getApellido1() {
		if(DBSync) {
			if(conn.connect()) {
				getEntryChanges();
				return apellido1;
			}
		}
		return DBConnection.NULL_SENTINEL_VARCHAR;
	}

	public void setApellido1(String apellido1) {
		if(DBSync) {
			if(conn.connect()) {
            getEntryChanges();
            this.apellido1 = apellido1;
            updateEntry();
        }
		}else {
			this.apellido1=apellido1;
		}
	}

	public String getApellido2() {
		if(DBSync) {
			if(conn.connect()) {
				getEntryChanges();
				return apellido1;
			}
		}
		return DBConnection.NULL_SENTINEL_VARCHAR;
	}

	public void setApellido2(String apellido2) {
		
		if(DBSync) {
			if(conn.connect()) {
            getEntryChanges();
            this.apellido2 = apellido2;
            updateEntry();
        }
		}else {
			this.apellido2=apellido2;
		}
	}//ISCLOSED!!!
	
	public void destroy() {
		if (DBSync && conn.connect()) {
			deleteEntry();
		}
		id_empleado = DBConnection.NULL_SENTINEL_INT;
		n_ss = DBConnection.NULL_SENTINEL_VARCHAR;
		nombre = DBConnection.NULL_SENTINEL_VARCHAR;
		apellido1 = DBConnection.NULL_SENTINEL_VARCHAR;
		apellido2 = DBConnection.NULL_SENTINEL_VARCHAR;
		DBSync = false;
	}

	
	boolean createTable() {
		boolean result = false;
		if(DBSync) {
		if(conn.connect()) {
		if(conn.tableExists("empleado")) {
			System.out.println("SI EXISTE LA TABLA");
			result = false;
		}else {
			try {
				System.out.println("NO EXISTE LA TABLA");
				
			 String sql="CREATE TABLE IF NOT EXISTS "+ "empleado"+"("
						+"id_empleado INT UNIQUE NOT NULL,"
						+"n_ss VARCHAR(100),"
						+"nombre VARCHAR(100),"
						+"apellido1 VARCHAR(100),"
						+"apellido2 VARCHAR(100),"
						+"PRIMARY KEY(id_empleado)"
						+");";
				conn.update(sql);
				result=true;
			} catch (Exception e) {
				e.printStackTrace();
				result=false;
			}	
		}
		}
		}
		return result;
	}


	
	

	boolean insertEntry() {
		boolean result=false;
		if(DBSync) {	
		if(conn.connect()) {
			if(conn.tableExists("empleado")) { //si existe la tabla, insertamos // no va aqui el create table
				
				System.out.println("si existe la tabla, insertamos");
				
				try {
					String sql = "INSERT INTO empleado VALUES ("
                            + id_empleado + ", "
                            + (n_ss != null ? "'" + n_ss + "'" : DBConnection.NULL_SENTINEL_VARCHAR) + ", "
                            + (nombre != null ? "'" + nombre + "'" : DBConnection.NULL_SENTINEL_VARCHAR) + ", "
                            + (apellido1 != null ? "'" + apellido1 + "'" : DBConnection.NULL_SENTINEL_VARCHAR) + ", "
                            + (apellido2 != null ? "'" + apellido2 + "'" : DBConnection.NULL_SENTINEL_VARCHAR) + ");";
					conn.update(sql);
					
					result = true;
				}
				catch (Exception e) {
					e.printStackTrace();
					id_empleado = DBConnection.NULL_SENTINEL_INT;
					n_ss = DBConnection.NULL_SENTINEL_VARCHAR;
					nombre = DBConnection.NULL_SENTINEL_VARCHAR;
					apellido1 = DBConnection.NULL_SENTINEL_VARCHAR;
					apellido2 = DBConnection.NULL_SENTINEL_VARCHAR;
					result = false;
				}
					
			}//end if
			else {
				result = false;
				}
			
		}//end if
		else {
			result = false;
		}
		}else {
		System.out.println("NO ESTA CONECTADO");
		}
		return result;
	}
	
	
	boolean updateEntry() {	
	 boolean result=false;
		if(DBSync) {	
			if(conn.connect()) { 
	       System.out.println("ERROR AQUI;");
	       
	        String sql = "UPDATE empleado SET "
	         + "n_ss = ?, nombre = ?, apellido1 = ?, apellido2 = ? "
	         + "WHERE id_empleado = ? ;";
	        try {
	            ArrayList<Object> nuevos = new ArrayList<>();
	            
	            nuevos.add(n_ss);
	            nuevos.add(nombre);
	            nuevos.add(apellido1);
	            nuevos.add(apellido2);
	            nuevos.add(id_empleado);

	      
	        System.out.println("ERROR AQUI2;");
	        result =  true;
	        }catch (Exception e) {
	        	  System.out.println( "semete");
	            e.printStackTrace();
	            result = false;
	        }
			}
		}
	    return result;
	    }

	
	
	
	boolean deleteEntry() {
        boolean deleted = false;
        if(DBSync) {
            if(conn.connect()) {
        String sql = "DELETE FROM Empleado WHERE id_empleado = ?";
        try {
            ArrayList<Object> lista = new ArrayList<>();
            lista.add(id_empleado);

            
            deleted = true;

        } catch (Exception e) {

            e.printStackTrace();
            deleted = false;
        }
            }
        }
        return deleted;
    }

	

	
	void getEntryChanges() {
		// utiliza valores que estan en la base de datos, pero si no se han metido, como es el caso que pasa en DATAMANAGER, que pasa???
		if(DBSync) {	
			if(conn.connect()) {
        String sql = "SELECT id_empleado, n_ss, nombre, apellido1, apellido2 FROM empleado WHERE id_empleado= ?";
        try {
            ArrayList<Object> params = new ArrayList<>();
            params.add(id_empleado);
            ResultSet rs = conn.query(sql, params);
            if (rs.next()) {
                // Actualiza los atributos del objeto actual con los valores obtenidos de la base de datos
                id_empleado = Integer.parseInt(rs.getString(1));
                n_ss = rs.getString(2);
                nombre = rs.getString(3);
                apellido1 = rs.getString(4);
                apellido2 = rs.getString(5);
               


            }
            rs.close();
        } catch (SQLException e) {

            e.printStackTrace();
        }
			}
		}
    }



}
