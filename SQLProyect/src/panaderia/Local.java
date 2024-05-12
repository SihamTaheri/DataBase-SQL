package panaderia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Local extends DBTable {
	
	private int id_local;
	private boolean tiene_cafeteria;
	private String direccion;
	private String descripcion;
	
	public Local(int id_local, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_local = id_local;
		if(DBSync &&conn.connect()) {
			System.out.println("CONEXIONPERFECTADBSYNC");
			
			createTable();
			if (!insertEntry()){
				id_local = DBConnection.NULL_SENTINEL_INT;
				descripcion = DBConnection.NULL_SENTINEL_VARCHAR;
				direccion = DBConnection.NULL_SENTINEL_VARCHAR;
				tiene_cafeteria = false; //hay que tocar esto creo que en el dbconnection
				DBSync = false;
			}
		}
	}
	
	public Local(int id_local, boolean tiene_cafeteria, String direccion, String descripcion, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_local = id_local;
		this.tiene_cafeteria = tiene_cafeteria;
		this.direccion = direccion;
		this.descripcion = descripcion; 
		if(conn.connect() && DBSync) {
			System.out.println("CONEXIONPERFECTADBSYNC");
			createTable();
			if (!insertEntry()){
				DBSync = false;
			}
		}
	}
	
	public int getId_local() {
		if(DBSync && conn.connect()) {
			getEntryChanges();
			return id_local;
		}
		return DBConnection.NULL_SENTINEL_INT;
	}
	
	public boolean getTiene_cafeteria() {
		if(DBSync && conn.connect()) {
			getEntryChanges();
			return tiene_cafeteria;
		}
		return false;  //esto ns pq no hay un sentinel boolean jaja
	}

	public void setTiene_cafeteria(boolean tiene_cafeteria) {
		
		if(DBSync) {
			if(conn.connect()) {
            getEntryChanges();
            this.tiene_cafeteria = tiene_cafeteria;
            updateEntry();
        }
		}else {
			this.tiene_cafeteria=tiene_cafeteria;
		}
	}

	public String getDireccion() {
		if(DBSync && conn.connect()) {
			getEntryChanges();
			return direccion;
		}
		return DBConnection.NULL_SENTINEL_VARCHAR;
	}

	public void setDireccion(String direccion) {
		
		if(DBSync) {
			if(conn.connect()) {
            getEntryChanges();
            this.direccion = direccion;
            updateEntry();
        }
		}else {
			this.direccion=direccion;
		}
	}

	public String getDescripcion() {
		
		if(DBSync && conn.connect()) {
			getEntryChanges();
			return descripcion;
		}
		return DBConnection.NULL_SENTINEL_VARCHAR;
	}

	public void setDescripcion(String descripcion) {
		if(DBSync) {
			if(conn.connect()) {
            getEntryChanges();
            this.descripcion = descripcion;
            updateEntry();
        }
		}else {
			this.descripcion=descripcion;
		}
	}
	
	public void destroy() {
		if (DBSync && conn.connect()) {
			deleteEntry();
		}
		id_local = DBConnection.NULL_SENTINEL_INT;
		tiene_cafeteria = false;
		direccion = DBConnection.NULL_SENTINEL_VARCHAR;
		descripcion = DBConnection.NULL_SENTINEL_VARCHAR;
		DBSync = false;
	}

	boolean createTable() {
		boolean result = false;
		if(DBSync) {
			if(conn.connect()) {
		if(conn.tableExists("Local")) {
			result = false;
		}else {
			try {
			 String sql="CREATE TABLE IF NOT EXISTS "+ "Local "+"("
						+"id_local INT UNIQUE NOT NULL,"
						+"tiene_cafeteria BOOLEAN,"
						+"direccion VARCHAR(100),"
						+"descripcion VARCHAR(100),"
						+"PRIMARY KEY(id_local)"
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
		boolean result;
		if(DBSync && conn.connect()) {
			if(conn.tableExists("local")) { //si existe la tabla o la crea correctamente(pq no existe previamente), insertamos
				try {
					System.out.println("CONEXIONPERFECTAINSERTENTRY");
					
					String sql = "INSERT INTO local VALUES ("
                            + id_local + ", " + tiene_cafeteria + ", " 
                            + (direccion != null ? "'" + direccion + "'" : DBConnection.NULL_SENTINEL_VARCHAR) + ", "
                            + (descripcion != null ? "'" + descripcion + "'" : DBConnection.NULL_SENTINEL_VARCHAR) + ") ";
					conn.update(sql);
					result = true;
				}
				catch (Exception e) {
					e.printStackTrace();
					id_local = DBConnection.NULL_SENTINEL_INT;
					descripcion = DBConnection.NULL_SENTINEL_VARCHAR;
					direccion = DBConnection.NULL_SENTINEL_VARCHAR;
					tiene_cafeteria = false; //hay que tocar esto creo que en el dbconnection
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
		return result;
	}
	
	
	
	
	
	
	boolean updateEntry() {
		
		boolean result=false;
		
		if(DBSync && conn.connect()) {
		String sql = "UPDATE Local SET "
		 + "tiene_cafeteria = ?, direccion = ?, descripcion = ? "
		 + " WHERE id_local = ? ;";
		try {
			ArrayList<Object> nuevos = new ArrayList<>();
		
			nuevos.add(tiene_cafeteria);
			nuevos.add(direccion);
			nuevos.add(descripcion);
			nuevos.add(id_local);

		int filas = conn.update(sql, nuevos);
		
		result = filas > 0;
		}catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		}
	return result;
	}
	
	boolean deleteEntry() {
        boolean deleted =false;
        if(DBSync && conn.connect()) {
        String sql = "DELETE FROM local WHERE id_local = ?";
        try {
            ArrayList<Object> lista = new ArrayList<>();
            lista.add(id_local);

            conn.update(sql, lista);
            deleted = true;
        } catch (Exception e) {

            e.printStackTrace();
            deleted = false;
        }
        }
        return deleted;
    }
	
	void getEntryChanges() {
		if(DBSync && conn.connect()) {
		String sql = "SELECT id_local, tiene_cafeteria, direccion, descripcion FROM local WHERE id_local= ?";
		try {
			ArrayList<Object> params = new ArrayList<>();
			params.add(id_local);  
			
			ResultSet rs = conn.query(sql, params);
			if (rs.next()) {
				// Actualiza los atributos del objeto actual con los valores obtenidos de la base de datos
				id_local = Integer.parseInt(rs.getString(1));
				tiene_cafeteria = rs.getBoolean(2);
				direccion = rs.getString(3);
				descripcion = rs.getString(4);
				

				
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}
	}
}

