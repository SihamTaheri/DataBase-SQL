package panaderia;



import java.sql.Connection;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
	
	final static String NULL_SENTINEL_VARCHAR = "NULL";
	final static int NULL_SENTINEL_INT = Integer.MIN_VALUE;
	final static java.sql.Date NULL_SENTINEL_DATE = java.sql.Date.valueOf("1900-01-01");
	
	private Connection conn = null;
	private String user;
	private String pass;
	private String url;
	
	public DBConnection(String server, int port, String user, String pass, String database) {
		
		
		//EL INIT SIRVE PARA CARGAR EL DRIVER -NECESARIO
		//no es siempre necesario cargarlo el init si la version es mayor que 8.0, ya que funciona solo 
		String serverAddress = server+":"+port;
		String db = database;
		this.user = user;
		this.pass = pass;
		this.url = "jdbc:mysql://" + serverAddress + "/" + db;
		System.out.println("Driver encontrado!");
	
	}
	
	public boolean connect() {
		try {
			if(conn== null|| conn.isClosed()) {	
				String drv= "com.mysql.cj.jdbc.Driver";
				try {
					Class.forName(drv);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				conn = DriverManager.getConnection(url, user, pass);
				System.out.println("Conectados correctamente a la base de datos");
			}else {
				return true;
			}
		}catch (final SQLException e) {
			System.err.println("Error al abrir la conexion");
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	
	public boolean close() {
		boolean bien=false;
		try {
		if(conn!=null) {
			conn.close();
			System.out.println("Desconexion correcta de la base de datos");
			bien=true;
		}
		}catch(SQLException e) {
			e.printStackTrace();
			bien=false;
		}
		return bien;
	}

	public int update(String sql){ //crear tabla, cambiar(insert,delete,update) y drops
		int n=-1;
		try {
		Statement st =conn.createStatement();
		n =st.executeUpdate(sql);
		System.out.println("Valores actualizados " + n);
		st.close();
		
		}catch(SQLException e) {
			System.out.println("ola");
			e.printStackTrace();
			
		}
		return n;
	}
	
	
	
	public ResultSet query(String sql) {
		final String sql2=sql;
		Statement st=null;
		ResultSet rs=null;
	
		try {
			st=conn.createStatement();
			rs=st.executeQuery(sql2);
			System.out.println("Consulta ejecutada correctamente");
		}catch (final SQLException e) {
			System.err.println("error SQL al ejecutar la consulta");
			System.err.println(e.getMessage());
		}catch (final Exception e) {
			System.err.println("otro tipo de error al ejecutar la consulta de los actores");
			System.err.println(e.getMessage());
		}finally {
			
			//QUITADO OJOOO
		
		}
		return rs;
	}
	
	public ResultSet query(String sql, ArrayList<Object> a) {
		PreparedStatement st=null;
		ResultSet rs=null;

		try {
		st=conn.prepareStatement(sql);
		if (a != null) {
	        for (int i = 0; i < a.size(); i++) {
	            Object param = a.get(i);
	            if(param  !=null) {
	            	String obtenemosnombre=param.getClass().getName();
	            	if(obtenemosnombre.equals("java.lang.Integer")) {	
	            		
	            		if(param.equals(NULL_SENTINEL_INT)) {
	            			st.setNull(i + 1, Types.INTEGER);
                        } else {
                            st.setInt(i + 1, (int) param);
                        }
                        	
                    }else if(obtenemosnombre.equals("java.lang.String")) {
                        	
                    	if(param.equals(NULL_SENTINEL_VARCHAR)) {
	            			st.setNull(i + 1, Types.VARCHAR);
                        } else {
                            st.setString(i + 1, (String) param);
                        }
                    	
                    }else if(obtenemosnombre.equals("java.lang.Date")){
                    	
                    	if(param.equals(NULL_SENTINEL_DATE)) {
	            			st.setNull(i + 1, Types.DATE);
                        } else {
                            st.setDate(i + 1, (java.sql.Date) param);
                        }
                    	
                    }else {
                    	st.setObject(i+1, param);
                    }
   	
	            	}
	            	
	            }
	        }	
		rs=st.executeQuery();
		
		}catch (final SQLException e) {
		System.err.println("error SQL al ejecutar la consulta");
		System.err.println(e.getMessage());
		}catch (final Exception e) {
		System.err.println("otro tipo de error al ejecutar la consulta de los actores");
		System.err.println(e.getMessage());
		}finally {
		/*
		try {
			if(st!=null)
				st.close();
			if(rs !=null) {
				rs.close();
			}
		}catch (final SQLException e) {
			System.err.println("error al liberar los recursos");
			System.err.println(e.getMessage());
		}	
		*/
	}
	return rs;
	}
	
	public boolean tableExists(String tableName) {
		String sql= "SHOW TABLES";
		boolean encontrado=false;
		try {
		Statement st=conn.createStatement();
		ResultSet rs=st.executeQuery(sql);
		while(rs.next() && !encontrado) {
			if(rs.getString(1).equals(tableName)) {
				encontrado=true;
			}
		}
		
		rs.close();
		st.close();
		}catch(SQLException e) {
			e.printStackTrace();
			encontrado=false;
		}
		return encontrado;
	}

}
