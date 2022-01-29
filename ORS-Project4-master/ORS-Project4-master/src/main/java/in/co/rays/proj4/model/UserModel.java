package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.EmailBuilder;
import in.co.rays.proj4.util.EmailMessage;
import in.co.rays.proj4.util.EmailUtility;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * JDBC Implementation of UserModel
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class UserModel {
	 private static Logger log = Logger.getLogger(UserModel.class);

	 private long roleId;

	  /**
	     * @return the roleId
	     */
	    
	public long getRoleId() {
		return roleId;
	}

	
	/**
     * @param roleId the roleId to set
     */

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	/**
     * Find next PK of user
     * 
     * @throws DatabaseException
     */
	public Integer nextPK() throws DatabaseException
    {
    	Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement ps = conn
                    .prepareStatement("SELECT MAX(ID) FROM ST_USER");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();

        } catch (Exception e)
        {
            throw new DatabaseException("Exception : Exception in getting PK");
        } 
        finally 
        {
            JDBCDataSource.closeConnection(conn);
        }
        return pk + 1;
    }
    
    /**
     * Add a User 
     * @param bean
     * @throws DatabaseException
     */

    public long add(UserBean bean) throws ApplicationException,DuplicateRecordException, DatabaseException
    {
    	
    	log.debug("usermodel Add method Start");
Connection conn = null;
int pk = 0;

UserBean existbean = findByLogin(bean.getLogin());

if (existbean != null) {
    throw new DuplicateRecordException("Login Id already exists");
}

try {
    conn = JDBCDataSource.getConnection();
    pk = nextPK();
    // Get auto-generated next primary key
    System.out.println(pk + " in ModelJDBC");
    conn.setAutoCommit(false); // Begin transaction
    PreparedStatement ps = conn.prepareStatement("INSERT INTO st_user VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    ps.setInt(1, pk);
    ps.setString(2, bean.getFirstName());
    ps.setString(3, bean.getLastName());
    ps.setString(4, bean.getLogin());
    ps.setString(5, bean.getPassword());
    System.out.println("DAte=="+bean.getDob());
    ps.setDate(6, new java.sql.Date(bean.getDob().getTime()));
    ps.setString(7, bean.getMobileNo());
    ps.setLong(8, bean.getRoleId());
    ps.setInt(9, bean.getUnsucessfulLogin());
    ps.setString(10, bean.getGender());
    ps.setTimestamp(11, bean.getLastLogin());
    ps.setString(12, bean.getLock());
    ps.setString(13, bean.getRegisteredIP());
    ps.setString(14, bean.getLastLoginIP());
    ps.setString(15, bean.getCreatedBy());
    ps.setString(16, bean.getModifiedBy());
    ps.setTimestamp(17, bean.getCreatedDatetime());
    ps.setTimestamp(18, bean.getModifiedDatetime());
    ps.executeUpdate();
    conn.commit(); // End transaction
    ps.close();
} 
catch (Exception e)
    { 
	e.printStackTrace();
    try 
    {
        conn.rollback();
    } 
    catch (Exception ex)
    {
        ex.printStackTrace();
        throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
    }
    throw new ApplicationException("Exception : Exception in add User");
    }
    finally 
    {
    JDBCDataSource.closeConnection(conn);
    }
log.debug("Usermodel Add method End");
return pk;
}
    
    /**
     * Delete a User
     * @param bean
     * @throws DatabaseException
     */
    public void delete(UserBean bean) throws ApplicationException,DatabaseException
    {
    	Connection conn = null;
    	try 
    	{
		conn=JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement ps=conn.prepareStatement("DELETE FROM ST_USER WHERE ID=?");
		ps.setLong(1,bean.getId());
		ps.executeUpdate();
		conn.commit();
		ps.close();
		
    	}
    	catch (Exception e)
    	{
			try 
			{
				conn.rollback();
			} 
			catch (Exception e1) 
			{
				throw new ApplicationException("Exception : Delete rollback exception " + e1.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete User");
		}
    	finally
    	{
    		 JDBCDataSource.closeConnection(conn);	
		}
    }
    
    /**
     * Find User by Login
     * @param login : get parameter
     * @return bean
     * @throws DatabaseException,applicationException
     */
  public UserBean findByLogin(String login) throws DatabaseException,ApplicationException
  {
	  StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN=?");
	  UserBean bean = null;
      Connection conn = null;
     
      try
      {
		conn=JDBCDataSource.getConnection();
		PreparedStatement ps=conn.prepareStatement(sql.toString());
		ps.setString(1, login);
		ResultSet rs=ps.executeQuery();
		while (rs.next())
		{
          bean=new UserBean();
          bean.setId(rs.getLong(1));
          bean.setFirstName(rs.getString(2));
          bean.setLastName(rs.getString(3));
          bean.setLogin(rs.getString(4));
          bean.setPassword(rs.getString(5));
          bean.setDob(rs.getDate(6));
          bean.setMobileNo(rs.getString(7));
          bean.setRoleId(rs.getLong(8));
          bean.setUnsucessfulLogin(rs.getInt(9));
          bean.setGender(rs.getString(10));
          bean.setLastLogin(rs.getTimestamp(11));
          bean.setLock(rs.getString(12));
          bean.setRegisteredIP(rs.getString(13));
          bean.setLastLoginIP(rs.getString(14));
          bean.setCreatedBy(rs.getString(15));
          bean.setModifiedBy(rs.getString(16));
          bean.setCreatedDatetime(rs.getTimestamp(17));
          bean.setModifiedDatetime(rs.getTimestamp(18));			
		}
		rs.close();
		
	  } 
      catch (Exception e)
      {
		e.printStackTrace();
		throw new ApplicationException("Exception : Exception in getting User by login");
	  }
      
      finally
      {
		JDBCDataSource.closeConnection(conn);
	  }
      return bean;
	  
  }
    
  /**
   * Find User by PK
   * @param pk : get parameter
   * @return bean
   * @throws DatabaseException
   */
 
  public UserBean findByPK(long pk) throws ApplicationException
  {
	 StringBuffer sql=new StringBuffer("SELECT * FROM ST_USER WHERE ID=?");
	 UserBean bean=null;
	 Connection conn=null;
	 
	 try 
	 {
		conn=JDBCDataSource.getConnection();
		PreparedStatement ps=conn.prepareStatement(sql.toString());
		ps.setLong(1,pk);
		ResultSet rs=ps.executeQuery();
		while (rs.next()) 
		{
		bean=new UserBean();
		bean.setId(rs.getLong(1));
		  bean.setFirstName(rs.getString(2));
          bean.setLastName(rs.getString(3));
          bean.setLogin(rs.getString(4));
          bean.setPassword(rs.getString(5));
          bean.setDob(rs.getDate(6));
          System.out.println("find profilr Date=="+bean.getDob());
          bean.setMobileNo(rs.getString(7));
          bean.setRoleId(rs.getLong(8));
          bean.setUnsucessfulLogin(rs.getInt(9));
          bean.setGender(rs.getString(10));
          bean.setLastLogin(rs.getTimestamp(11));
          bean.setLock(rs.getString(12));
          bean.setRegisteredIP(rs.getString(13));
          bean.setLastLoginIP(rs.getString(14));
          bean.setCreatedBy(rs.getString(15));
          bean.setModifiedBy(rs.getString(16));
          bean.setCreatedDatetime(rs.getTimestamp(17));
          bean.setModifiedDatetime(rs.getTimestamp(18));
		}
		
		rs.close();	
		ps.close();
	 } 
	 catch(Exception e)
	 {
		e.printStackTrace();
		throw new ApplicationException( "Exception : Exception in getting User by pk");
	 }
	 finally
	 {
	JDBCDataSource.closeConnection(conn);	
   	 }
	 
	 return bean;
	 
  }
    
  /**
   * Update a user
   * @param bean
   * @throws DatabaseException,DuplicateRecordException,DatabaseException
   */

  public void update(UserBean bean)throws ApplicationException,DuplicateRecordException, DatabaseException
  {
	  Connection conn=null;
      UserBean beanExist = findByLogin(bean.getLogin());
      // Check if updated LoginId already exist
      if (beanExist != null && !(beanExist.getId() == bean.getId())) {
          throw new DuplicateRecordException("LoginId is already exist");
      }

      try {
		
    	  conn=JDBCDataSource.getConnection();
    	  conn.setAutoCommit(false);//begin 
    	  PreparedStatement ps=conn.prepareStatement("UPDATE ST_USER SET FIRST_NAME=?,LAST_NAME=?,LOGIN=?,PASSWORD=?,DOB=?,MOBILE_NO=?,ROLE_ID=?,UNSUCCESSFUL_LOGIN=?,GENDER=?,LAST_LOGIN=?,USER_LOCK=?,REGISTERED_IP=?,LAST_LOGIN_IP=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
    	  ps.setString(1,bean.getFirstName());
    	  ps.setString(2, bean.getLastName());
          ps.setString(3, bean.getLogin());
          ps.setString(4, bean.getPassword());
          System.out.println("update Date=="+bean.getDob());
          ps.setDate(5, new java.sql.Date(bean.getDob().getTime()));
          ps.setString(6, bean.getMobileNo());
          ps.setLong(7, bean.getRoleId());
          ps.setInt(8, bean.getUnsucessfulLogin());
          ps.setString(9, bean.getGender());
          ps.setTimestamp(10, bean.getLastLogin());
          ps.setString(11, bean.getLock());
          ps.setString(12, bean.getRegisteredIP());
          ps.setString(13, bean.getLastLoginIP());
          ps.setString(14, bean.getCreatedBy());
          ps.setString(15, bean.getModifiedBy());
          ps.setTimestamp(16, bean.getCreatedDatetime());
          ps.setTimestamp(17, bean.getModifiedDatetime());
          ps.setLong(18, bean.getId());
          ps.executeUpdate();
          conn.commit(); // End transaction
          ps.close();
	      } 
      catch (Exception e)
      {
		e.printStackTrace();
		try
		{
		conn.rollback();
		} 
		catch (Exception e2)
		{
			throw new ApplicationException("Exception : Update rollback exception "+ e2.getMessage());
		}
		  throw new ApplicationException("Exception in updating User ");
	  }
    finally 
    {
	JDBCDataSource.closeConnection(conn);	
	}
         
  }
  
  /**
   * Search User
   * @param bean  : Search Parameters
   * @throws DatabaseException
   * return list
   */

  public List search(UserBean bean) throws ApplicationException {
      return search(bean, 0, 0);
  }
  
  /**
   * Search User with pagination
   * @return list : List of Users
   * @param bean : Search Parameters
   * @param pageNo  : Current Page No.
   * @param pageSize  : Size of Page
   * @throws DatabaseException
   */

  public List search(UserBean bean,int pageNo,int pageSize) throws ApplicationException
  {
	  
	  log.debug("Model search Started");
      StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE 1=1");

      if (bean != null) {
          if (bean.getId() > 0) {
              sql.append(" AND id = " + bean.getId());
          }
          if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
              sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
          }
          if (bean.getLastName() != null && bean.getLastName().length() > 0) {
              sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
          }
          if (bean.getLogin() != null && bean.getLogin().length() > 0) {
              sql.append(" AND LOGIN like '" + bean.getLogin() + "%'");
          }
          if (bean.getPassword() != null && bean.getPassword().length() > 0) {
              sql.append(" AND PASSWORD like '" + bean.getPassword() + "%'");
          }
          if (bean.getDob() != null && bean.getDob().getDate() > 0) {
              sql.append(" AND DOB = " + bean.getGender());
          }
          if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
              sql.append(" AND MOBILE_NO = " + bean.getMobileNo());
          }
          if (bean.getRoleId() > 0) {
              sql.append(" AND ROLE_ID = " + bean.getRoleId());
          }
          if (bean.getUnsucessfulLogin() > 0) {
              sql.append(" AND UNSUCCESSFUL_LOGIN = "
                      + bean.getUnsucessfulLogin());
          }
          if (bean.getGender() != null && bean.getGender().length() > 0) {
              sql.append(" AND GENDER like '" + bean.getGender() + "%'");
          }
          if (bean.getLastLogin() != null
                  && bean.getLastLogin().getTime() > 0) {
              sql.append(" AND LAST_LOGIN = " + bean.getLastLogin());
          }
          if (bean.getRegisteredIP() != null
                  && bean.getRegisteredIP().length() > 0) {
              sql.append(" AND REGISTERED_IP like '" + bean.getRegisteredIP()
                      + "%'");
          }
          if (bean.getLastLoginIP() != null
                  && bean.getLastLoginIP().length() > 0) {
              sql.append(" AND LAST_LOGIN_IP like '" + bean.getLastLoginIP()
                      + "%'");
          }

      }

      // if page size is greater than zero then apply pagination
      if (pageSize > 0) {
          // Calculate start record index
          pageNo = (pageNo - 1) * pageSize;

          sql.append(" Limit " + pageNo + ", " + pageSize);
          
      }

      //System.out.println(sql);
      ArrayList list = new ArrayList();
      Connection conn = null;
      try {
          conn = JDBCDataSource.getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql.toString());
          ResultSet rs = pstmt.executeQuery();
          while (rs.next()) {
              bean = new UserBean();
              bean.setId(rs.getLong(1));
              bean.setFirstName(rs.getString(2));
              bean.setLastName(rs.getString(3));
              bean.setLogin(rs.getString(4));
              bean.setPassword(rs.getString(5));
              bean.setDob(rs.getDate(6));
              bean.setMobileNo(rs.getString(7));
              bean.setRoleId(rs.getLong(8));
              bean.setUnsucessfulLogin(rs.getInt(9));
              bean.setGender(rs.getString(10));
              bean.setLastLogin(rs.getTimestamp(11));
              bean.setLock(rs.getString(12));
              bean.setRegisteredIP(rs.getString(13));
              bean.setLastLoginIP(rs.getString(14));
              bean.setCreatedBy(rs.getString(15));
              bean.setModifiedBy(rs.getString(16));
              bean.setCreatedDatetime(rs.getTimestamp(17));
              bean.setModifiedDatetime(rs.getTimestamp(18));

              list.add(bean);
          }
          rs.close();
      } catch (Exception e) {
          log.error("Database Exception..", e);
          throw new ApplicationException(
                  "Exception : Exception in search user");
      } finally {
          JDBCDataSource.closeConnection(conn);
      }

      log.debug("Model search End");
      return list;

  }
  
  
  /**
   * Get List of User
   * @return list : List of User
   * @throws DatabaseException
   */

  public List list() throws ApplicationException
  {
	  return list(0,0);
  }
  
  /**
   * Get List of User with pagination
   * @return list : List of users
   * @param pageNo  : Current Page No.
   * @param pageSize : Size of Page
   * @throws DatabaseException
   */

public List list(int pageNo,int pageSize) throws ApplicationException
{
	log.debug("Model list Started");
	ArrayList list=new ArrayList();
	StringBuffer sql=new StringBuffer("select * from ST_USER");
	// if page size is greater than zero then apply pagination
	if(pageSize>0)
	{
		// Calculate start record index
        pageNo = (pageNo - 1) * pageSize;
        sql.append(" limit " + pageNo + "," + pageSize);
	}
	Connection conn=null;
	
	try
	{
		conn=JDBCDataSource.getConnection();
		PreparedStatement ps=conn.prepareStatement(sql.toString());
		ResultSet rs=ps.executeQuery();
		while (rs.next())
		{
			UserBean bean = new UserBean();
            bean.setId(rs.getLong(1));
            bean.setFirstName(rs.getString(2));
            bean.setLastName(rs.getString(3));
            bean.setLogin(rs.getString(4));
            bean.setPassword(rs.getString(5));
            bean.setDob(rs.getDate(6));
            bean.setMobileNo(rs.getString(7));
            bean.setRoleId(rs.getLong(8));
            bean.setUnsucessfulLogin(rs.getInt(9));
            bean.setGender(rs.getString(10));
            bean.setLastLogin(rs.getTimestamp(11));
            bean.setLock(rs.getString(12));
            bean.setRegisteredIP(rs.getString(13));
            bean.setLastLoginIP(rs.getString(14));
            bean.setCreatedBy(rs.getString(15));
            bean.setModifiedBy(rs.getString(16));
            bean.setCreatedDatetime(rs.getTimestamp(17));
            bean.setModifiedDatetime(rs.getTimestamp(18));

            list.add(bean);
            
		}
		rs.close();
		ps.close();
	}
	
	catch (Exception e)
	{
	      log.error("Database Exception..", e);
		throw new ApplicationException( "Exception : Exception in getting list of users");
	}
	finally
	{
	JDBCDataSource.closeConnection(conn);	
	}
	  log.debug("Model list End");
	return list;
}
  
/**authenticate user
 
 * @param id   : long id
 * @param password : String Password
 * @throws DatabaseException
 */

public UserBean authenticate(String login,String password) throws ApplicationException
{
	StringBuffer sql=new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN = ? AND PASSWORD = ?");
	 UserBean bean = null;
     Connection conn = null;

	try 
	{
	conn=JDBCDataSource.getConnection();
	PreparedStatement ps=conn.prepareStatement(sql.toString());
	ps.setString(1, login);
	ps.setString(2, password);
	ResultSet rs=ps.executeQuery();
	
	while (rs.next()) {
        bean = new UserBean();
        bean.setId(rs.getLong(1));
        bean.setFirstName(rs.getString(2));
        bean.setLastName(rs.getString(3));
        bean.setLogin(rs.getString(4));
        bean.setPassword(rs.getString(5));
        bean.setDob(rs.getDate(6));
        bean.setMobileNo(rs.getString(7));
        bean.setRoleId(rs.getLong(8));
        bean.setUnsucessfulLogin(rs.getInt(9));
        bean.setGender(rs.getString(10));
        bean.setLastLogin(rs.getTimestamp(11));
        bean.setLock(rs.getString(12));
        bean.setRegisteredIP(rs.getString(13));
        bean.setLastLoginIP(rs.getString(14));
        bean.setCreatedBy(rs.getString(15));
        bean.setModifiedBy(rs.getString(16));
        bean.setCreatedDatetime(rs.getTimestamp(17));
        bean.setModifiedDatetime(rs.getTimestamp(18));
  
	}
	}
	catch (Exception e) 
	{
		throw new ApplicationException("Exception : Exception in authenticate");
	}
	finally
	{
		JDBCDataSource.closeConnection(conn);	
	}
	
	return bean;
	
}

/**
 * Lock User for certain time duration
 * 
 * @return boolean : true if success otherwise false
 * @param login : User Login
 * @throws ApplicationException
 * @throws RecordNotFoundException  : if user not found
 * @throws DatabaseException 
 */

public boolean lock(String login) throws ApplicationException,RecordNotFoundException, DatabaseException
{
	 boolean flag = false;
     UserBean beanExist = null;
     try
     {
         beanExist = findByLogin(login);
         if (beanExist != null) {
             beanExist.setLock(UserBean.ACTIVE);
             update(beanExist);
             flag = true;
         } else {
             throw new RecordNotFoundException("LoginId not exist");
         }
     }
     catch (DuplicateRecordException e) 
     {
    	 throw new ApplicationException("Database Exception");
	 }
     
     return flag;
}

/**
 * Get User Roles
 * @return List : User Role List
 * @param bean
 * @throws ApplicationException
 */

public List getRoles(UserBean bean) throws ApplicationException
{
	StringBuffer sql = new StringBuffer(
            "SELECT * FROM ST_USER WHERE role_Id=?");
    Connection conn = null;
    List list = new ArrayList();
    try {

        conn = JDBCDataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql.toString());
        ps.setLong(1, bean.getRoleId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            bean = new UserBean();
            bean.setId(rs.getLong(1));
            bean.setFirstName(rs.getString(2));
            bean.setLastName(rs.getString(3));
            bean.setLogin(rs.getString(4));
            bean.setPassword(rs.getString(5));
            bean.setDob(rs.getDate(6));
            bean.setMobileNo(rs.getString(7));
            bean.setRoleId(rs.getLong(8));
            bean.setUnsucessfulLogin(rs.getInt(9));
            bean.setGender(rs.getString(10));
            bean.setLastLogin(rs.getTimestamp(11));
            bean.setLock(rs.getString(12));
            bean.setRegisteredIP(rs.getString(13));
            bean.setLastLoginIP(rs.getString(14));
            bean.setCreatedBy(rs.getString(15));
            bean.setModifiedBy(rs.getString(16));
            bean.setCreatedDatetime(rs.getTimestamp(17));
            bean.setModifiedDatetime(rs.getTimestamp(18));

            list.add(bean);
        }
        rs.close();
        ps.close();
    } 
    catch (Exception e)
    {
        
        throw new ApplicationException("Exception : Exception in get roles");

    } 
    finally 
    {
        JDBCDataSource.closeConnection(conn);
    }
    
    return list;
	
}

/**Change Password
 * 
 * @param id : long id
 * @param oldpassword : String oldPassword
 * @param newpassword : String newPassword
 * @throws DatabaseException
 */

public boolean changePassword(Long id,String oldpassword,String newpassword) throws RecordNotFoundException,ApplicationException, DatabaseException
{
	boolean flag = false;
    UserBean beanExist = null;

	
	beanExist = findByPK(id);
    if (beanExist != null && beanExist.getPassword().equals(oldpassword))
    {
        beanExist.setPassword(newpassword);
        try
        {
            update(beanExist);
        } 
        catch (DuplicateRecordException e) 
        {
            
            throw new ApplicationException("LoginId is already exist");
        }
        flag = true;
    } 
    else
    {
        throw new RecordNotFoundException("Login not exist");
    }	
    
    HashMap<String,String> map=new HashMap<String, String>();
    
    map.put("login", beanExist.getLogin());
    map.put("password", beanExist.getPassword());
    map.put("firstName", beanExist.getFirstName());
    map.put("lastName", beanExist.getLastName());
    
    String message=EmailBuilder.getChangePasswordMessage(map);
    EmailMessage msg=new EmailMessage();
    msg.setTo(beanExist.getLogin());
    msg.setSubject("SUNARYS ORS Password has been changed Successfully.");
    msg.setMessage(message);
    msg.setMessageType(EmailMessage.HTML_MSG);

    EmailUtility.sendmail(msg);

    return flag;
   
}


/**
 * Register a user and send mail to user
 * @param bean
 * @return
 * @throws ApplicationException
 * @throws DuplicateRecordException
 * @throws DatabaseException
 */
public long registerUser(UserBean bean)throws ApplicationException,DuplicateRecordException, DatabaseException
{
	log.debug("registerUser method Start");
	System.out.println("registerUser method Start");
	long pk = add(bean);

    HashMap<String, String> map = new HashMap<String, String>();
    map.put("login", bean.getLogin());
    map.put("password", bean.getPassword());

   String message = EmailBuilder.getUserRegistrationMessage(map);

    EmailMessage msg = new EmailMessage();

    msg.setTo(bean.getLogin());
    msg.setSubject("Registration is successful for ORS Project SunilOS");
    msg.setMessage(message);
    msg.setMessageType(EmailMessage.HTML_MSG);
    
System.out.println("--------registeruser method message print---------"+msg.toString());
System.out.println("=------email utility send mail method call--------");
    EmailUtility.sendmail(msg);
    log.debug("registerUser method End");
    System.out.println("registerUser method End");
    return pk;
 	
}


/**
 * Reset Password of User with auto generated Password
 * 
 * @return boolean : true if success otherwise false
 * @param login : User Login
 * @throws ApplicationException
 * @throws DatabaseException 
 * @throws RecordNotFoundException : if user not found
 */
public boolean resetPassword(UserBean bean) throws ApplicationException,DatabaseException
{
	String newpassword=String.valueOf(new Date().getTime()).substring(0, 4);
	UserBean userData = findByPK(bean.getId());
	userData.setPassword(newpassword);
	
	try 
	{
        update(userData);
    }
	catch (DuplicateRecordException e)
	{
        return false;
    }
	
	HashMap<String, String> map = new HashMap<String, String>();
    map.put("login", bean.getLogin());
    map.put("password", bean.getPassword());
    map.put("firstName", bean.getFirstName());
    map.put("lastName", bean.getLastName());

    String message = EmailBuilder.getForgetPasswordMessage(map);

    EmailMessage msg = new EmailMessage();

    msg.setTo(bean.getLogin());
    msg.setSubject("Password has been reset");
    msg.setMessage(message);
    msg.setMessageType(EmailMessage.HTML_MSG);

    EmailUtility.sendmail(msg);

    return true;
		
}

/**
 * Send the password of User to his Email if user forget password
 * 
 * @return boolean : true if success otherwise false
 * @param login  : User Login
 * @throws ApplicationException
 * @throws RecordNotFoundException  : if user not found
 * @throws DatabaseException 
 */

public boolean forgetPassword(String login) throws ApplicationException,RecordNotFoundException, DatabaseException 
{
	 UserBean userData = findByLogin(login);
     boolean flag = false;
     if (userData == null)
     {
         throw new RecordNotFoundException("Email ID does not exists !");

     }
     
     HashMap<String, String> map = new HashMap<String, String>();
     map.put("login", userData.getLogin());
     map.put("password", userData.getPassword());
     map.put("firstName", userData.getFirstName());
     map.put("lastName", userData.getLastName());
     String message = EmailBuilder.getForgetPasswordMessage(map);
     EmailMessage msg = new EmailMessage();
     msg.setTo(login);
     msg.setSubject("SUNARYS ORS Password reset");
     msg.setMessage(message);
     msg.setMessageType(EmailMessage.HTML_MSG);
     EmailUtility.sendmail(msg);
     flag = true;

     return flag;

}

}
