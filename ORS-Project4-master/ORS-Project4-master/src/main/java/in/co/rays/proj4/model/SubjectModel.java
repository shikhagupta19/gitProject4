package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * JDBC Implementation of Subject Model
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class SubjectModel {
	private static Logger log = Logger.getLogger(SubjectModel.class);

	/**
	 * Find next PK of Course
	 * 
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
	    log.debug("Model nextPK Started");
	    System.out.println("----Model nextpk method started-------");
	    Connection conn = null;
	    int pk = 0;
	    try {
	        conn = JDBCDataSource.getConnection();
	        PreparedStatement pstmt = conn
	                .prepareStatement("SELECT MAX(ID) FROM ST_SUBJECT");
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            pk = rs.getInt(1);
	        }
	        rs.close();

	    } catch (Exception e) {
	        log.error("Database Exception..", e);
	        throw new DatabaseException("Exception : Exception in getting PK");
	    } finally {
	        JDBCDataSource.closeConnection(conn);
	    }
	    log.debug("Model nextPK End");
	    System.out.println("----Model nextpk method ended-------");
	    return pk + 1;
	}

	/**
	* Add a Subject
	* 
	* @param bean
	* @throws DatabaseException
	* 
	*/
	public long add(SubjectBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		System.out.println("Model subject add method Started");
		Connection conn = null;
		
		// get Course Name
	    CourseModel cModel = new CourseModel();
	    CourseBean courseBean = cModel.findByPK(bean.getCourseId());
        System.out.println(courseBean+"--------Course id in add method----------");
        System.out.println(courseBean.getName()+"-----course name--------");
	    bean.setCourseName(courseBean.getName());
 
		SubjectBean duplicateSubject = findByName(bean.getName());
		int pk = 0;
		
		// Check if create Course already exist
		if (duplicateSubject != null) {
			throw new DuplicateRecordException("Subject already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			System.out.println(pk + "----pk value in ModelJDBC------");
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_SUBJECT VALUES(?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setLong(2, bean.getCourseId());
			pstmt.setString(3, bean.getName());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("SubjectModel add End");
		System.out.println("Model subject add method end");
		return pk;
	}


	/**
	* Delete a Subject
	* 
	* @param bean
	* @throws DatabaseException
	*/
	public void delete(SubjectBean bean) throws ApplicationException {
	log.debug("Model delete Started");
	Connection conn = null;
	try {
	    conn = JDBCDataSource.getConnection();
	    conn.setAutoCommit(false); // Begin transaction
	    PreparedStatement pstmt = conn
	            .prepareStatement("DELETE FROM ST_SUBJECT WHERE ID=?");
	    pstmt.setLong(1, bean.getId());
	    pstmt.executeUpdate();
	    conn.commit(); // End transaction
	    pstmt.close();

	} catch (Exception e) {
	    log.error("Database Exception..", e);
	    try {
	        conn.rollback();
	    } catch (Exception ex) {
	        throw new ApplicationException(
	                "Exception : Delete rollback exception "
	                        + ex.getMessage());
	    }
	    throw new ApplicationException(
	            "Exception : Exception in delete course");
	} finally {
	    JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model delete Started");
	}
	/**
	* Find User by Subject
	* 
	* @param name
	*            : get parameter
	* @return bean
	* @throws DatabaseException
	*/

	public SubjectBean findByName(String name) throws ApplicationException {
	log.debug("Model findBy EmailId Started");
	StringBuffer sql = new StringBuffer(
	        "SELECT * FROM ST_SUBJECT WHERE NAME=?");
	SubjectBean bean = null;
	Connection conn = null;
	try {
	    conn = JDBCDataSource.getConnection();
	    PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setString(1, name);
	    ResultSet rs = pstmt.executeQuery();
	    while (rs.next()) {
	        bean = new SubjectBean();
	        bean.setId(rs.getLong(1));
	        bean.setCourseId(rs.getLong(2));
	        
	        bean.setName(rs.getString(3));
	        bean.setCreatedBy(rs.getString(4));
	        bean.setModifiedBy(rs.getString(5));
	        bean.setCreatedDatetime(rs.getTimestamp(6));
	        bean.setModifiedDatetime(rs.getTimestamp(7));

	    }
	    rs.close();
	} catch (Exception e) {
	    log.error("Database Exception..", e);
	    throw new ApplicationException(
	            "Exception : Exception in getting User by emailId");
	} finally {
	    JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model findBy EmailId End");
	return bean;
	}

	/**
	* FindSubject by PK
	* 
	* @param pk
	*            : get parameter
	* @return bean
	* @throws DatabaseException
	*/

	public SubjectBean findByPK(long pk) throws ApplicationException {
	log.debug("Model findByPK Started");
	StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE ID=?");
	SubjectBean bean = null;
	Connection conn = null;
	try {
	    conn = JDBCDataSource.getConnection();
	    PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	    pstmt.setLong(1, pk);
	    ResultSet rs = pstmt.executeQuery();
	    while (rs.next()) {
	        bean = new SubjectBean();
	        bean.setId(rs.getLong(1));
	        bean.setCourseId(rs.getLong(2));
	       
	        bean.setName(rs.getString(3));
	        bean.setCreatedBy(rs.getString(4));
	        bean.setModifiedBy(rs.getString(5));
	        bean.setCreatedDatetime(rs.getTimestamp(6));
	        bean.setModifiedDatetime(rs.getTimestamp(7));
	    }
	    rs.close();
	} catch (Exception e) {
	    log.error("Database Exception..", e);
	    throw new ApplicationException(
	            "Exception : Exception in getting User by pk");
	} finally {
	    JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model findByPK End");
	return bean;
	}

	/**
	* Update a Subject
	* 
	* @param bean
	* @throws DatabaseException
	*/

	public void update(SubjectBean bean) throws ApplicationException,
	    DuplicateRecordException {
	log.debug("Model update Started");
	Connection conn = null;

	SubjectBean duplicataSubject = findByName(bean.getName());
	// Check if updated Course already exist
	if (duplicataSubject != null && duplicataSubject.getId() != bean.getId()) {
	    throw new DuplicateRecordException("Subject already exists");
	}
	try {
	    conn = JDBCDataSource.getConnection();

	    conn.setAutoCommit(false); // Begin transaction
	    PreparedStatement pstmt = conn
	            .prepareStatement("UPDATE ST_SUBJECT SET COURSE_ID=?,NAME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
	   
	    pstmt.setLong(1, bean.getCourseId());
	    
	    pstmt.setString(2, bean.getName());
	    pstmt.setString(3, bean.getCreatedBy());
	    pstmt.setString(4, bean.getModifiedBy());
	    pstmt.setTimestamp(5, bean.getCreatedDatetime());
	    pstmt.setTimestamp(6, bean.getModifiedDatetime());
	    pstmt.setLong(7, bean.getId());
	    pstmt.executeUpdate();
	    conn.commit(); // End transaction
	    pstmt.close();
	} catch (Exception e) {
	    log.error("Database Exception..", e);
	    try {
	        conn.rollback();
	    } catch (Exception ex) {
	        throw new ApplicationException(
	                "Exception : Delete rollback exception "
	                        + ex.getMessage());
	    }
	    throw new ApplicationException("Exception in updating Course ");
	} finally {
	    JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model update End");
	}

	/**
	* Search Subject
	* 
	* @param bean
	*            : Search Parameters
	* @throws DatabaseException
	*/

	public List search(SubjectBean bean) throws ApplicationException {
	return search(bean, 0, 0);
	}

	/**
	* Search Course with pagination
	* 
	* @return list : List of Course
	* @param bean
	*            : Search Parameters
	* @param pageNo
	*            : Current Page No.
	* @param pageSize
	*            : Size of Page
	* 
	* @throws DatabaseException
	*/

	public List search(SubjectBean bean, int pageNo, int pageSize)
	    throws ApplicationException {
	log.debug("Model search Started");
	StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE 1=1");

	if (bean != null) {
	    if (bean.getId() > 0) {
	        sql.append(" AND id = " + bean.getId());
	    }
	    if (bean.getName() != null && bean.getName().length() > 0) {
	        sql.append(" AND NAME like '" + bean.getName() + "%'");
	    }
	    if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
	        sql.append(" AND COURSE_ID= " + bean.getCourseId());
	    }
	   /* if (bean.getDescription() != null
	            && bean.getDescription().length() > 0) {
	        sql.append(" AND DESCRIPTION like '" + bean.getDescription()
	                + "%'");
	    }
	*/
	}

	// if page size is greater than zero then apply pagination
	if (pageSize > 0) {
	    // Calculate start record index
	    pageNo = (pageNo - 1) * pageSize;

	    sql.append(" Limit " + pageNo + ", " + pageSize);
	    // sql.append(" limit " + pageNo + "," + pageSize);
	    System.out.println("sql query"+sql.toString());
	}

	ArrayList list = new ArrayList();
	Connection conn = null;
	try {
	    conn = JDBCDataSource.getConnection();
	    PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	    ResultSet rs = pstmt.executeQuery();
	    while (rs.next()) {
	        bean = new SubjectBean();
	        bean.setId(rs.getLong(1));
	        bean.setCourseId(rs.getLong(2));
	       
	        bean.setName(rs.getString(3));
	       bean.setCreatedBy(rs.getString(4));
	        bean.setModifiedBy(rs.getString(5));
	        bean.setCreatedDatetime(rs.getTimestamp(6));
	        bean.setModifiedDatetime(rs.getTimestamp(7));
	        list.add(bean);
	    }
	    rs.close();
	} catch (Exception e) {
	    log.error("Database Exception..", e);
	    throw new ApplicationException(
	            "Exception : Exception in search Course");
	} finally {
	    JDBCDataSource.closeConnection(conn);
	}

	log.debug("Model search End");
	return list;
	}

	/**
	* Get List of Subject
	* 
	* @return list : List of Subject
	* @throws DatabaseException
	*/

	public List list() throws ApplicationException {
	return list(0, 0);
	}

	/**
	* Get List of Subject with pagination
	* 
	* @return list : List of Subject
	* @param pageNo
	*            : Current Page No.
	* @param pageSize
	*            : Size of Page
	* @throws DatabaseException
	*/

	public List list(int pageNo, int pageSize) throws ApplicationException {
	log.debug("Model list Started");
	ArrayList list = new ArrayList();
	StringBuffer sql = new StringBuffer("select * from ST_SUBJECT");
	// if page size is greater than zero then apply pagination
	if (pageSize > 0) {
	    // Calculate start record index
	    pageNo = (pageNo - 1) * pageSize;
	    sql.append(" limit " + pageNo + "," + pageSize);
	}

	Connection conn = null;

	try {
	    conn = JDBCDataSource.getConnection();
	    PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	    ResultSet rs = pstmt.executeQuery();
	    while (rs.next()) {
	    	SubjectBean bean = new SubjectBean();
	        bean.setId(rs.getLong(1));
	        bean.setCourseId(rs.getLong(2));
	        bean.setName(rs.getString(3));
	        bean.setCreatedBy(rs.getString(4));
	        bean.setModifiedBy(rs.getString(5));
	        bean.setCreatedDatetime(rs.getTimestamp(6));
	        bean.setModifiedDatetime(rs.getTimestamp(7));
	        list.add(bean);
	    }
	    rs.close();
	} catch (Exception e) {
	    log.error("Database Exception..", e);
	    throw new ApplicationException(
	            "Exception : Exception in getting list of Subject");
	} finally {
	    JDBCDataSource.closeConnection(conn);
	}

	log.debug("Model list End");
	return list;

	}

}
