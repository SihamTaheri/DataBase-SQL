package panaderias;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
public class Empleado extends DBTable {
	
	private int id_empleado;
	private String n_ss;
	private String nombre;
	private String apellido1;
	private String apellido2;
	
	public Empleado(int id_empleado, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_empleado = id_empleado;
		this.n_ss=DBConnection.NULL_SENTINEL_VARCHAR;
		this.nombre=DBConnection.NULL_SENTINEL_VARCHAR;
		this.apellido1=DBConnection.NULL_SENTINEL_VARCHAR;
		this.apellido2=DBConnection.NULL_SENTINEL_VARCHAR;
		if (DBSync&&conn.connect()) {
			createTable();
			boolean insertado = insertEntry();
			if (insertado==false) {
				// No se pudo insertar el elemento, se establecen a nulo los elementos de la clave primaria (con valores centinela)
				this.id_empleado = DBConnection.NULL_SENTINEL_INT;
				this.n_ss = DBConnection.NULL_SENTINEL_VARCHAR;
				this.nombre = DBConnection.NULL_SENTINEL_VARCHAR;
				this.apellido1 = DBConnection.NULL_SENTINEL_VARCHAR;
				this.apellido2 = DBConnection.NULL_SENTINEL_VARCHAR;
				DBSync= false; // Desactivar la sincronización con la base de datos
			}
		}
	}
	
	public Empleado(int id_empleado, String n_ss, String nombre, String apellido1, String apellido2, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_empleado = id_empleado;
		this.n_ss = n_ss;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		if (DBSync&&conn.connect()) {
			createTable();
			boolean insertado = insertEntry();
			if (insertado==false) {
				// No se pudo insertar el elemento, se establecen a nulo los elementos de la clave primaria (con valores centinela)
				this.id_empleado = DBConnection.NULL_SENTINEL_INT;
				this.n_ss = DBConnection.NULL_SENTINEL_VARCHAR;
				this.nombre = DBConnection.NULL_SENTINEL_VARCHAR;
				this.apellido1 = DBConnection.NULL_SENTINEL_VARCHAR;
				this.apellido2 = DBConnection.NULL_SENTINEL_VARCHAR;
				DBSync= false; // Desactivar la sincronización con la base de datos
			}
		}
	}
	
	public int getId_empleado() {
		if(DBSync&&conn.connect()){
			getEntryChanges();
			return id_empleado;
		}
		return DBConnection.NULL_SENTINEL_INT;
	}
	
	public String getN_ss() {
		if(DBSync&&conn.connect()){
			getEntryChanges();
			return n_ss;
		}
		return DBConnection.NULL_SENTINEL_VARCHAR;
	}

	public void setN_ss(String n_ss) {
		if(DBSync&&conn.connect()){
			getEntryChanges();
            this.n_ss = n_ss;
            updateEntry();
		}
	}

	public String getNombre() {
		if(DBSync&&conn.connect()) {
			getEntryChanges();
			return nombre;
		}
	return DBConnection.NULL_SENTINEL_VARCHAR;
	}

	public void setNombre(String nombre) {
		if(DBSync&&conn.connect()){
			getEntryChanges();
            this.nombre = nombre;
            updateEntry();
		}
	}

	public String getApellido1() {
		
			if(DBSync&&conn.connect()) {
				getEntryChanges();
				return apellido1;
			}
		return DBConnection.NULL_SENTINEL_VARCHAR;
	}

	public void setApellido1(String apellido1) {
		if(DBSync&&conn.connect()) {
            getEntryChanges();
            this.apellido1 = apellido1;
            updateEntry();
        }
	}

	public String getApellido2() {
		if(DBSync&&conn.connect()){
			getEntryChanges();
			return apellido2;
		}
		return DBConnection.NULL_SENTINEL_VARCHAR;
	}

	public void setApellido2(String apellido2) {
		if(DBSync&&conn.connect()){
			getEntryChanges();
            this.apellido2 = apellido2;
            updateEntry();
		}
	}
	
	public void destroy() {	
		if(DBSync&&conn.connect()) {
				deleteEntry();
			
			id_empleado = DBConnection.NULL_SENTINEL_INT;
			n_ss = DBConnection.NULL_SENTINEL_VARCHAR;
			nombre = DBConnection.NULL_SENTINEL_VARCHAR;
			apellido1 = DBConnection.NULL_SENTINEL_VARCHAR;
			apellido2 = DBConnection.NULL_SENTINEL_VARCHAR;
			DBSync = false;
			}
	}
	
	
	 boolean createTable() {
		
		boolean creada; 
		String sql = "CREATE TABLE IF NOT EXISTS " + "Empleado" + " ("
				+ "id_Empleado INT ,"
				+ "n_ss VARCHAR(100),"
				+ "nombre VARCHAR(100),"
				+ "apellido1 VARCHAR(100),"
				+ "apellido2 VARCHAR(100),"
				+ "PRIMARY KEY (id_Empleado)"
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
	//funcion auxiliar
	private void resetear() {
		
		this.id_empleado = DBConnection.NULL_SENTINEL_INT;
				this.n_ss = DBConnection.NULL_SENTINEL_VARCHAR;
				this.nombre = DBConnection.NULL_SENTINEL_VARCHAR;
				this.apellido1 = DBConnection.NULL_SENTINEL_VARCHAR;
				this.apellido2 = DBConnection.NULL_SENTINEL_VARCHAR;
	}
	boolean insertEntry() {
		boolean result=false;
		
		if(DBSync&&conn.connect()&&conn.tableExists("Empleado")) {
				
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
					resetear();
					result = false;
				}
					
			}	
			
		return result;
	}
	
	boolean updateEntry() {
		boolean actualizado;
		String sql = "UPDATE Empleado SET  n_ss, nombre,apellido1,apellido2 WHERE  id_empleado= ?;";
		try {
			ArrayList<Object> params = new ArrayList<>();
			// Reemplaza  con el valor correspondiente
			params.add(n_ss);  
			params.add(nombre);  
			params.add(apellido1);   
			params.add(apellido2);   
			params.add(id_empleado);  
	
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
		String sql = "DELETE FROM Empleado WHERE id_empleado = ?";
		try {
			ArrayList<Object> params = new ArrayList<>();
			params.add(id_empleado);  
			
			int filasAfectadas = conn.update(sql, params);
			borrado = filasAfectadas > 0;
		} catch (Exception e) {
			
			e.printStackTrace();
			borrado = false;
		}
		return borrado;
	}
	
	
	void getEntryChanges() {
		String sql = "SELECT id_empleado, n_ss, nombre, apellido1, apellido2 FROM Empleado WHERE id_empleado= ?";
		try {
			ArrayList<Object> params = new ArrayList<>();
			params.add(id_empleado);  
			
			ResultSet rs = conn.query(sql, params);
			if (rs.next()) {
				// Actualiza los atributos del objeto actual con los valores obtenidos de la base de datos
				id_empleado = Integer.parseInt(rs.getString("id_empleado"));
				n_ss = rs.getString("n_ss");
				nombre = rs.getString("nombre");
				apellido1 = rs.getString("apellido1");
				apellido2 = rs.getString("apellido2");
				

				
			}
			rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	

}
