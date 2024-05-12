package panaderias;

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
	//variables adicionales
	private static String driver= "com.mysql.cj.jdbc.Driver";
	//
	public DBConnection(String server, int port, String user, String pass, String database){
		this.url = "jdbc:mysql://" + server + ":" + port + "/" + database;
		this.user = user;
    	this.pass  = pass;
		
	}
	
	public boolean connect() {
	  
	  try {
		if(conn!=null&&!conn.isClosed()){// Si ya hay una conexión abierta, se devuelve true
		   return true;
		  }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pass);
			return true;
			
		 }catch(ClassNotFoundException|SQLException e){
			e.printStackTrace();
			return false;
		 }
		}
		
	
		public boolean close() {
			boolean result = true;
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				result = false;
			}
			return result;
		}

	public int update(String sql) {
		PreparedStatement stmt = null;
		Connection conn =null;
		int filasAfectadas = -1;
		try {
			 conn = DriverManager.getConnection(url, user, pass);
			 stmt = conn.prepareStatement(sql); 
			filasAfectadas = stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			System.err.println("Error al ejecutar la sentencia SQL: " + e.getMessage());
		}
		return filasAfectadas;
	}
	
	public int update(String sql, ArrayList<Object> a) {
		int filasAfectadas= -1;
		PreparedStatement ps =null;
		try {
			 ps = conn.prepareStatement(sql);
			for (int i = 0; i < a.size(); i++) {
				Object param = a.get(i);
				if (param.getClass().getName().equals("java.lang.Integer")) {
					ps.setInt(i + 1, (Integer) param);
				} else if (param.getClass().getName().equals("java.lang.Float")) {
					ps.setFloat(i + 1, (Float) param);
				} else if (param.getClass().getName().equals("java.lang.Double")) {
					ps.setDouble(i + 1, (Double) param);
				} else if (param.getClass().getName().equals("java.lang.String")) {
					ps.setString(i + 1, (String) param);
				} 
				else if (param.getClass().getName().equals("java.sql.Date")) {
				    ps.setDate(i + 1, (java.sql.Date) param);
				 } else if (param.getClass().getName().equals("java.lang.Boolean")) {
					ps.setBoolean(i + 1, (Boolean) param);
				} else if (param.getClass().getName().equals("null.Int")) {
					ps.setNull(i + 1, Types.INTEGER);
				} else if (param.getClass().getName().equals("null.Float")) {
					ps.setNull(i + 1, Types.FLOAT);
				} else if (param.getClass().getName().equals("null.Double")) {
					ps.setNull(i + 1, Types.DOUBLE);
				} else if (param.getClass().getName().equals("null.String")) {
					ps.setNull(i + 1, Types.VARCHAR);
				} else if (param.getClass().getName().equals("null.Date")) {
					ps.setNull(i + 1, Types.DATE);
				} else if (param.getClass().getName().equals("null.Boolean")) {
					ps.setNull(i + 1, Types.BOOLEAN);
				}
			}
			filasAfectadas = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return filasAfectadas;
		
	}
	
	
	public ResultSet query(String sql) {
		Statement stmt=null;
		ResultSet rs=null;
		try {
			 stmt = conn.createStatement();
			 rs = stmt.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		//finally {
   /*
	 // Cerrar el ResultSet
    try {
        if (rs != null) {
            rs.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    */
    
    // Cerrar el Statement
    /*
	 try {
        if (stmt != null) {
            stmt.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
	 */
	//}
}
	public ResultSet query(String sql, ArrayList<Object> a) {

		ResultSet rs = null;
		PreparedStatement ps= null;
		try {
			 ps = conn.prepareStatement(sql);
			for (int i = 0; i < a.size(); i++) {
				Object param = a.get(i);
			if (param.getClass().getName().equals("java.lang.Integer")) {
				ps.setInt(i + 1, (Integer) param);
			} else if (param.getClass().getName().equals("java.lang.Float")) {
				ps.setFloat(i + 1, (Float) param);
			} else if (param.getClass().getName().equals("java.lang.Double")) {
				ps.setDouble(i + 1, (Double) param);
			} else if (param.getClass().getName().equals("java.lang.String")) {
				ps.setString(i + 1, (String) param);
			} 
			else if (param.getClass().getName().equals("java.sql.Date")) {
				ps.setDate(i + 1, (java.sql.Date) param);
			 } else if (param.getClass().getName().equals("java.lang.Boolean")) {
				ps.setBoolean(i + 1, (Boolean) param);
			} else if (param.getClass().getName().equals("null.Int")) {
				ps.setNull(i + 1,Types.INTEGER);
			} else if (param.getClass().getName().equals("null.Float")) {
				ps.setNull(i + 1, Types.FLOAT);
			} else if (param.getClass().getName().equals("null.Double")) {
				ps.setNull(i + 1, Types.DOUBLE);
			} else if (param.getClass().getName().equals("null.String")) {
				ps.setNull(i + 1, Types.VARCHAR);
			} else if (param.getClass().getName().equals("null.Date")) {
				ps.setNull(i + 1, Types.DATE);
			} else if (param.getClass().getName().equals("null.Boolean")) {
				ps.setNull(i + 1, Types.BOOLEAN);
			} 
		}
		rs=ps.executeQuery();
		return rs;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		//cierre de ResultSet y PreparedStatement
		/*
		 finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 */
	}
	public boolean tableExists(String tableName) {
		boolean exists = false;
	try {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SHOW TABLES");
	
		// Buscar el nombre de tabla en la lista de tablas existentes
		while (rs.next()&&exists==false) {
		  String existingTableName = rs.getString(1);
		  //ignoro las diferencias entre mayusculas y minusculas
		  if (existingTableName.equalsIgnoreCase(tableName)) {
			exists = true;
		  }
		}
		// Cierro el ResultSet y Statement
		rs.close();
		stmt.close();
	} catch (SQLException e) {
		e.printStackTrace();
		exists=false;
		
	}
	
		return exists;
	  }
	}
	
	