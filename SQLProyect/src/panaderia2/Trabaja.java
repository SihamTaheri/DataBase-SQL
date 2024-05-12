package panaderia2;
import java.util.ArrayList;



import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Trabaja extends DBTable {
	
	private int id_empleado;
	private int id_local;
	private java.sql.Date fecha_inicio;
	private java.sql.Date fecha_fin;

	
	public Trabaja(int id_empleado, int id_local, java.sql.Date fecha_inicio, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_empleado=id_empleado;
        this.fecha_inicio=fecha_inicio;
        this.id_local=id_local;
		this.fecha_fin= DBConnection.NULL_SENTINEL_DATE;
		if (DBSync&&conn.connect()) {
			createTable();
			boolean insertado =  insertEntry();
            if (insertado==false) {
				// No se pudo insertar el elemento, se establecen a nulo los elementos de la clave primaria (con valores centinela)
				this.id_empleado = DBConnection.NULL_SENTINEL_INT;
				this.id_local = DBConnection.NULL_SENTINEL_INT;
				//this.fecha_fin= DBConnection.NULL_SENTINEL_DATE;
				this.fecha_inicio=DBConnection.NULL_SENTINEL_DATE;
				DBSync= false; // Desactivar la sincronización con la base de datos
			}
		}
	}
	public Trabaja(int id_empleado, int id_local, java.sql.Date fecha_inicio, DBConnection conn, boolean DBSync,java.sql.Date fecha_fin) {
		super(conn, DBSync);
		this.id_empleado=id_empleado;
		this.id_local=id_local;
		this.fecha_inicio=fecha_inicio;
		this.fecha_fin=fecha_fin;
		if (DBSync&&conn.connect()) {
			createTable();
            boolean insertado = insertEntry();
			if (insertado==false) {
				// No se pudo insertar el elemento, se establecen a nulo los elementos de la clave primaria (con valores centinela)
				this.id_empleado = DBConnection.NULL_SENTINEL_INT;
				this.id_local = DBConnection.NULL_SENTINEL_INT;
				this.fecha_fin= DBConnection.NULL_SENTINEL_DATE;
				this.fecha_inicio=DBConnection.NULL_SENTINEL_DATE;
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

	public int getId_local() {
		if(DBSync&&conn.connect()){
			getEntryChanges();
			return id_local;
		}
		return DBConnection.NULL_SENTINEL_INT;
	}

	public java.sql.Date getFecha_inicio() {
		if(DBSync&&conn.connect()) {
			getEntryChanges();
			return fecha_inicio;
		}
		return DBConnection.NULL_SENTINEL_DATE;
	}
	public java.sql.Date getFecha_fin() {
		if(DBSync&&conn.connect()) {
			getEntryChanges();
			return fecha_fin;
		}
		return DBConnection.NULL_SENTINEL_DATE;
	}

	public void setFecha_fin(java.sql.Date fecha_fin) {
		if(DBSync&&conn.connect()){
			getEntryChanges();
            this.fecha_fin = fecha_fin;
            updateEntry();
		}
	}
	
	public void destroy() {
        if (DBSync) {
            deleteEntry();
            id_empleado = DBConnection.NULL_SENTINEL_INT;
            id_local = DBConnection.NULL_SENTINEL_INT;
            fecha_inicio = DBConnection.NULL_SENTINEL_DATE;
            fecha_fin = DBConnection.NULL_SENTINEL_DATE;
            DBSync = false;
        }
	}
	
	
	boolean createTable() {
		boolean creada; 
		String sql = "CREATE TABLE IF NOT EXISTS " + "Trabaja" + " ("
				+ "id_empleado INT ,"
				+ "id_local INT,"
				+"  fecha_inicio DATE ,"
				+"  fecha_fin DATE ,"
				+ "PRIMARY KEY (id_empleado,id_local,fecha_inicio)"
                +"FOREIGN KEY(id_empleado) REFERENCES Empleado(id_Empleado)"
                +"ON DELETE CASCADE ON UPDATE CASCADE "
                +"FOREIGN KEY(id_local) REFERENCES Local(id_local)"
                +"ON DELETE CASCADE ON UPDATE CASCADE "
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
	private void resetear() {
		id_local = DBConnection.NULL_SENTINEL_INT;
		id_empleado = DBConnection.NULL_SENTINEL_INT;
		fecha_fin = DBConnection.NULL_SENTINEL_DATE;
		fecha_inicio = DBConnection.NULL_SENTINEL_DATE;
	}
	
	boolean insertEntry() {
		boolean result = false;
		String tabla = "trabaja";
		if (DBSync && conn.connect()&&conn.tableExists(tabla)) {
			
				try {
					String sql = "INSERT INTO " + tabla + " VALUES ("
							+ id_empleado + ", "
							+ id_local + ", "
							+ (fecha_inicio != null ? "'" + fecha_inicio + "'" : DBConnection.NULL_SENTINEL_DATE) + ", "
							+ (fecha_fin != null ? "'" + fecha_fin + "'" : DBConnection.NULL_SENTINEL_DATE) + ") ";
					conn.update(sql);
					result = true;
				} catch (Exception e) {
					e.printStackTrace();
					resetear();
					result = false;
				}
		}
		return result;
	}
	
	boolean updateEntry() {
		boolean actualizado;
		String sql = "UPDATE Trabaja SET fecha_fin = ? WHERE id_empleado = ? AND id_local= ? AND fecha_inicio = ? ;";
		try {
			ArrayList<Object> params = new ArrayList<>();
			// Reemplaza  con el valor correspondiente
			params.add(id_empleado);  
			params.add(id_local);  
			params.add(fecha_inicio); 
            params.add(fecha_fin); 
	        
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
		String sql =  "DELETE FROM trabaja WHERE id_empleado = ? AND id_local = ? AND fecha_inicio = ?";
		try {
			ArrayList<Object> params = new ArrayList<>();
			params.add(id_empleado);  
			params.add(id_local);  
			params.add(fecha_inicio);  
			
			int filasAfectadas = conn.update(sql, params);
			borrado = filasAfectadas > 0;
		} catch (Exception e) {
			
			e.printStackTrace();
			borrado = false;
		}
		return borrado;
	}
	
	void getEntryChanges() {
        String sql ="SELECT id_empleado, id_local, fecha_inicio, fecha_fin FROM trabaja WHERE id_empleado= ? AND id_local = ? AND fecha_inicio = ?";
		try {
			ArrayList<Object> params = new ArrayList<>();
			params.add(id_empleado);  
			
			ResultSet rs = conn.query(sql, params);
			if (rs.next()) {
				// Actualiza los atributos del objeto actual con los valores obtenidos de la base de datos
				id_empleado = Integer.parseInt(rs.getString("id_empleado"));
				id_local =  Integer.parseInt((rs.getString("id_local")));
			    fecha_inicio = Date.valueOf(rs.getString("fecha_inicio"));
                fecha_fin=Date.valueOf(rs.getString("fecha_fin"));
			
				
			}
			rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

}
