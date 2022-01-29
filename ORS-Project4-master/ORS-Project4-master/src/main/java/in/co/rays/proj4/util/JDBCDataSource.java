package in.co.rays.proj4.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import in.co.rays.proj4.exception.ApplicationException;
/**
 * JDBC DataSource is a Data Connection Pool
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public final class JDBCDataSource {
	 /**
     *A generic JDBC Data source with a connection pool
     *JDBC DATABASE static object
     */
    
	private static JDBCDataSource jds=null;
	
	/**
	 *c3p0 connection pool object 
	 */
	
	
	private ComboPooledDataSource ds=null;
	
	/**
	 *make default constructor private 
	 */
	
	
	private JDBCDataSource()
	{
		try
		{
		//create data source
		ds=new ComboPooledDataSource();
		//set data source properties
		ds.setDriverClass(PropertyReader.getValue("driver"));
		ds.setJdbcUrl(PropertyReader.getValue("url"));
		ds.setUser(PropertyReader.getValue("username"));
		ds.setPassword(PropertyReader.getValue("password"));
		ds.setInitialPoolSize(PropertyReader.getValueInt("initialPoolSize"));
		ds.setAcquireIncrement(PropertyReader.getValueInt("acquireIncrement"));
		ds.setMaxPoolSize(PropertyReader.getValueInt("maxPoolSize"));
		ds.setMinPoolSize(PropertyReader.getValueInt("minPoolSize"));
        ds.setMaxIdleTime(PropertyReader.getValueInt("timeout"));		
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	   /**
     * Create instance of Connection Pool
     * 
     * @return
     */
     
	
	public static JDBCDataSource getInstance()
	{
		if(jds==null)
		{
			jds=new JDBCDataSource();
		}
		
		return jds;
	}
	
	/**
     * Gets the connection from ComboPooledDataSource
     * 
     * @return connection
     */
	public static Connection getConnection()
	{
		try {
			return getInstance().ds.getConnection();
		} 
		catch (SQLException e) {
			return null;
		}
		
	
	
		
	}
	
	 /**
     * Closes a connection
     * 
     * @param connection
     * @throws Exception
     */
    
	public static void closeConnection(Connection conn,PreparedStatement ps,ResultSet rs)
	{
		try 
		{
			if(rs!=null)
				rs.close();
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void closeConnection(Connection conn,PreparedStatement ps)
	{
		closeConnection(conn,ps, null);
	}
	
	public static void closeConnection(Connection conn)
	{
		closeConnection(conn,null, null);
	}
	
	public static void trnRollback(Connection connection)
            throws ApplicationException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new ApplicationException(ex.toString());
            }
        }
	
	}
	
	public static void main(String[] args) throws SQLException {
		String sql="select * from user";
		//get connection from DCP
		Connection conn=JDBCDataSource.getConnection();
		PreparedStatement ps=conn.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		System.out.println("DB connection POOL");
		System.out.println("fname\tlname\temail\tdob");
		
		while (rs.next()) 
		{
		System.out.print(rs.getString(1));
		System.out.print("\t"+rs.getString(2));
		System.out.print("\t"+rs.getString(3));
		System.out.print("\t"+rs.getDate(4));
		System.out.println();
			
		}
		
		//return connection to pool
		JDBCDataSource.closeConnection(conn,ps, rs);	
		
	}
	
	
}

