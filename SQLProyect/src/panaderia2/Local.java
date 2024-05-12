package panaderias2;
import java.util.ArrayList;
import  java.sql.ResultSet;
import java.sql.SQLException;
public class Local extends DBTable {
	
	private int id_local;
	private boolean tiene_cafeteria;
	private String direccion;
	private String descripcion;
	
	public Local(int id_local, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_local= id_local;
		this.tiene_cafeteria = false; // Establecer a falso por defecto
		this.direccion = DBConnection.NULL_SENTINEL_VARCHAR; // Establecer a nulo por defecto
		this.descripcion = DBConnection.NULL_SENTINEL_VARCHAR; // Establecer a nulo por defecto
		
		if (DBSync&&conn.connect()) {
			createTable();
			boolean insertado = insertEntry();
			if (insertado==false) {
				// No se pudo insertar el elemento, se establecen a nulo los elementos de la clave primaria
				this.id_local = DBConnection.NULL_SENTINEL_INT;
				this.tiene_cafeteria = false;
				this.direccion = DBConnection.NULL_SENTINEL_VARCHAR;
				this.descripcion = DBConnection.NULL_SENTINEL_VARCHAR;
				DBSync= false; // Desactivar la sincronización con la base de datos
			}
		}
	}
	
	public Local(int id_local, boolean tiene_cafeteria, String direccion, String descripcion, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_local = id_local;
		this.tiene_cafeteria = tiene_cafeteria;
		this.direccion = direccion;
		this.descripcion = descripcion;
		
		if (DBSync&&conn.connect()) {
			createTable();
			boolean insertado= insertEntry();
			if (insertado==false) {
				// No se pudo insertar el elemento, se establecen a nulo los elementos de la clave primaria
				this.id_local = DBConnection.NULL_SENTINEL_INT;
				this.tiene_cafeteria = false;
				this.direccion = DBConnection.NULL_SENTINEL_VARCHAR;
				this.descripcion = DBConnection.NULL_SENTINEL_VARCHAR;
				DBSync= false; // Desactivar la sincronización con la base de datos
			}
		}
	}
	
	public int getId_local() {
		if(DBSync&&conn.connect()) {
			getEntryChanges();
			return id_local;
		}
		return DBConnection.NULL_SENTINEL_INT;
	}
	
	public boolean getTiene_cafeteria() {
		if(DBSync&&conn.connect()) {
			getEntryChanges();
			return tiene_cafeteria;
		}
		else
		return false;
	}

	public void setTiene_cafeteria(boolean tiene_cafeteria) {
		if(DBSync&&conn.connect()){
            getEntryChanges();
            this.tiene_cafeteria = tiene_cafeteria;
            updateEntry();
        }
	}

	public String getDireccion() {
		if (DBSync) {
			getEntryChanges();
			return direccion;
		}
		else
		return DBConnection.NULL_SENTINEL_VARCHAR;
	}

	public void setDireccion(String direccion) {
		if(DBSync&&conn.connect()){
            getEntryChanges();
            this.direccion = direccion;
            updateEntry();
        }
	}

	public String getDescripcion() {
		if(DBSync&&conn.connect()) {
			getEntryChanges();
			return descripcion;
		}
	else
	return DBConnection.NULL_SENTINEL_VARCHAR;
	}

	public void setDescripcion(String descripcion) {
		if(DBSync&&conn.connect()){
            getEntryChanges();
            this.descripcion = descripcion;
            updateEntry();
        }
	}
	
	public void destroy() {
		if(DBSync&&conn.connect()) {
			deleteEntry();
			resetear();
		DBSync = false;
		}
	}
	//funcion auxiliar restear
	private void resetear() {
		id_local = DBConnection.NULL_SENTINEL_INT;
		descripcion = DBConnection.NULL_SENTINEL_VARCHAR;
		direccion = DBConnection.NULL_SENTINEL_VARCHAR;
		tiene_cafeteria = false;
	}
	
	
	boolean createTable() {
		boolean creada; 
		String sql = "CREATE TABLE IF NOT EXISTS " + "Local" + " ("
				+ "id_Local INT ,"
				+ "tiene_cafeteria INT,"
				+ "direccion VARCHAR(100),"
				+ "descripcion VARCHAR(100),"
				+ "PRIMARY KEY (id_local)"
				+ ");";

		try {
			conn.update(sql);
			creada= true;
		} catch (Exception e) {
			e.printStackTrace();
			creada = false;
		}
	
		return creada;
		
	}
	
	boolean insertEntry() {
		boolean result = false;
		String tabla = "Local";
		if (DBSync && conn.connect()) {
			if (conn.tableExists(tabla)) {
				try {
					String sql = "INSERT INTO local VALUES ("
							+ id_local + ", " + tiene_cafeteria + ", "+ (direccion != null ? "'" + direccion + "'" : DBConnection.NULL_SENTINEL_VARCHAR) + ", "
	               + (descripcion != null ? "'" + descripcion + "'" : DBConnection.NULL_SENTINEL_VARCHAR) + ") ";
					conn.update(sql);
					result = true;
				} catch (Exception e) {
					e.printStackTrace();
					resetear();
					result=false;
				}
			}
		}
		return result;
	}
	
	
	
	
	boolean updateEntry() {
		boolean actualizado=false;
		if(!DBSync||!conn.connect()){
       return false;
		}
		String sql = "UPDATE Local SET tiene_cafeteria = ?,  direccion= ?, descripcion = ? WHERE id_local = ?;";
		try {
			ArrayList<Object> params = new ArrayList<>();
			// Reemplaza  con el valor correspondiente
			
			params.add(tiene_cafeteria);  
			params.add(direccion);   
			params.add(descripcion);   
			params.add(id_local);  
	
			int filasAfectadas = conn.update(sql, params);
			actualizado = filasAfectadas > 0;
		} catch (Exception e) {
			
			e.printStackTrace();
			actualizado = false;
		}
		return actualizado;
	}
	
	boolean deleteEntry() {
		boolean borrado;
		String sql = "DELETE FROM Local WHERE id_local = ?";
		try {
			ArrayList<Object> params = new ArrayList<>();
			params.add(id_local);  
			
			int filasAfectadas = conn.update(sql, params);
			borrado = filasAfectadas > 0;
		} catch (Exception e) {
			
			e.printStackTrace();
			borrado = false;
		}
		return borrado;
	}
	
	void getEntryChanges() {
		String sql = "SELECT id_local, tiene_cafeteria, direccion, descripcion FROM local WHERE id_local= ?";
		try {
			ArrayList<Object> params = new ArrayList<>();
			params.add(id_local);  
			
			ResultSet rs = conn.query(sql, params);
			if (rs.next()) {
				// Actualiza los atributos del objeto actual con los valores obtenidos de la base de datos
				id_local = Integer.parseInt(rs.getString("id_local"));
				tiene_cafeteria=false;
				if(Integer.parseInt(rs.getString("tiene_cafeteria"))==1){
					tiene_cafeteria=true;
				}
			    direccion = rs.getString("direccion");
				 descripcion= rs.getString("descripcion");
				

				
			}
			rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

}
