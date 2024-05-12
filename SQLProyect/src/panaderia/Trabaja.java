package panaderia;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Trabaja extends DBTable {
	
	private int id_empleado;
	private int id_local;
	private java.sql.Date fecha_inicio;
	private java.sql.Date fecha_fin;

	
	public Trabaja(int id_empleado, int id_local, java.sql.Date fecha_inicio, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_empleado = id_empleado;
		this.id_local = id_local;
		this.fecha_inicio = fecha_inicio;
		if(DBSync && conn.connect() ) {	
			createTable();
			if (!insertEntry()){
				id_empleado = DBConnection.NULL_SENTINEL_INT;
				id_local = DBConnection.NULL_SENTINEL_INT;
				fecha_inicio = DBConnection.NULL_SENTINEL_DATE;
				fecha_fin = DBConnection.NULL_SENTINEL_DATE;
				DBSync = false;
			}
		}
	}
	
	public Trabaja(int id_empleado, int id_local, java.sql.Date fecha_inicio, java.sql.Date fecha_fin, DBConnection conn, boolean DBSync) {
		super(conn, DBSync);
		this.id_empleado = id_empleado;
		this.id_local = id_local;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin =  fecha_fin;
		if(DBSync && conn.connect()) {	
			createTable();
			if (!insertEntry()){
				id_empleado = DBConnection.NULL_SENTINEL_INT;
				id_local = DBConnection.NULL_SENTINEL_INT;
				fecha_inicio = DBConnection.NULL_SENTINEL_DATE;
				fecha_fin = DBConnection.NULL_SENTINEL_DATE;
				DBSync = false;
			}
		}
	}

	public int getId_empleado() {
		if(conn.connect()) {
			if(DBSync) {
				getEntryChanges();
				return id_empleado;
			}
		}
		return DBConnection.NULL_SENTINEL_INT;
	}

	public int getId_local() {
		if(DBSync && conn.connect()) {
			getEntryChanges();
			return id_local;
		}
		return DBConnection.NULL_SENTINEL_INT;
	}

	public java.sql.Date getFecha_inicio() {
		if(DBSync && conn.connect()) {
			getEntryChanges();
			return fecha_inicio;
		}
		return DBConnection.NULL_SENTINEL_DATE;
	}

	public java.sql.Date getFecha_fin() {
		if(DBSync && conn.connect()) {
			getEntryChanges();
			return fecha_fin;
		}
		return DBConnection.NULL_SENTINEL_DATE;
	}

	public void setFecha_fin(java.sql.Date fecha_fin){
		if(DBSync) {
            if(conn.connect()) {
			getEntryChanges();
			this.fecha_fin = fecha_fin;
			updateEntry();
		}
		}else {
			this.fecha_fin = fecha_fin;
		}
	}
	
	public void destroy() {
		if (DBSync && conn.connect()) {
			deleteEntry();
		}
		id_empleado = DBConnection.NULL_SENTINEL_INT;
		id_local = DBConnection.NULL_SENTINEL_INT;
		fecha_inicio = DBConnection.NULL_SENTINEL_DATE;
		fecha_fin = DBConnection.NULL_SENTINEL_DATE;
		DBSync = false;	
	}
	
	
	boolean createTable() {
		boolean result = false;
		if(DBSync) {
			if(conn.connect()) {
		if(conn.tableExists("trabaja")) {
			result = false;
		}else {
			try {
			 String sql="CREATE TABLE IF NOT EXISTS "+ "trabaja"+"("
						+"id_empleado INT NOT NULL,"
						+"id_local INT NOT NULL,"
						+"fecha_inicio DATE NOT NULL,"
						+"fecha_fin DATE,"
						+"PRIMARY KEY(id_empleado, id_local, fecha_inicio),"
						+"FOREIGN KEY(id_empleado) REFERENCES empleado (id_empleado)"
						+"ON UPDATE CASCADE ON DELETE RESTRICT,"
						+"FOREIGN KEY(id_local) REFERENCES local (id_local)"
						+"ON UPDATE CASCADE ON DELETE RESTRICT"
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
			if(conn.tableExists("trabaja")) { //si existe la tabla o la crea correctamente(pq no existe previamente), insertamos
				try {
					System.out.println("CONEXIONPERFECTAINSERTENTRY");
					
					String sql = "INSERT INTO trabaja VALUES ("
                            + id_empleado + ", " 
							+ id_local + ", " 
                            + (fecha_inicio != null ? "'" + fecha_inicio + "'" : DBConnection.NULL_SENTINEL_DATE) + ", " 
                            + (fecha_fin != null ? "'" + fecha_fin + "'" : DBConnection.NULL_SENTINEL_DATE) + ") ";
					
					System.out.println(sql);
					conn.update(sql);
					result = true;
				}
				catch (SQLException e) {
					e.printStackTrace();
					
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
		 if(DBSync) {
	            if(conn.connect()) {	
		String sql = "UPDATE trabaja SET "
		 + "fecha_fin = ?"
		 + " WHERE id_empleado = ? AND id_local = ? AND fecha_inicio = ? ;";
		try {
			ArrayList<Object> nuevos = new ArrayList<>();
			nuevos.add(fecha_fin);
			nuevos.add(id_empleado);
			nuevos.add(id_local);
			nuevos.add(fecha_inicio);

		int filas = conn.update(sql, nuevos);
		result = true;
		}catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
	            }
		 }
	return result;
	}
	
	boolean deleteEntry() {
        boolean borrado=false;
        if(DBSync) {
            if(conn.connect()) {
            	String sql = "DELETE FROM trabaja WHERE id_empleado = ? AND id_local = ? AND fecha_inicio = ?";
            	try {
            		ArrayList<Object> lista = new ArrayList<>();
            		lista.add(id_empleado);
            		lista.add(id_local);
            		lista.add(fecha_inicio);

            		conn.update(sql, lista);
            		borrado = true;
            	} catch (Exception e) {

            		e.printStackTrace();
            		borrado = false;
            	}
            }
        }
        return borrado;
	}





	void getEntryChanges() {
		// utiliza valores que estan en la base de datos, pero si no se han metido, como es el caso que pasa en DATAMANAGER, que pasa???
		if(conn.connect()) {
			if(DBSync) {
        	String sql = "SELECT id_empleado, id_local, fecha_inicio, fecha_fin FROM trabaja WHERE id_empleado= ? AND id_local = ? AND fecha_inicio = ?";
        	try {
        		ArrayList<Object> lista = new ArrayList<>();
        		lista.add(id_empleado);
        		lista.add(id_local);
        		lista.add(fecha_inicio);
        		ResultSet rs = conn.query(sql, lista);
        		if (rs.next()) {
        			// Actualiza los atributos del objeto actual con los valores obtenidos de la base de datos
        			id_empleado = Integer.parseInt(rs.getString(1));
        			id_local = Integer.parseInt(rs.getString(2));
        			fecha_inicio = Date.valueOf(rs.getString(3));
        			fecha_fin = Date.valueOf(rs.getString(4));
           }
        		rs.close();
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}
			}
		}
	}
}
