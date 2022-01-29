package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;


/**
 * JDBC Implementation of CourseModel
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class CourseModel {
private static Logger log = Logger.getLogger(CourseModel.class);
	
	/**
	 * Find next PK of Course
	 * 
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
	    log.debug("Model nextPK Started");
	    Connection conn = null;
	    int pk = 0;
	    try {
	        conn = JDBCDataSource.getConnection();
	        PreparedStatement pstmt = conn
	                .prepareStatement("SELECT MAX(ID) FROM ST_COURSE");
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
	    return pk + 1;
}

/**
 * Add a Course
 * 
 * @param bean
 * @throws DatabaseException
 * 
 */
	public long add(CourseBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;

		CourseBean duplicataCourse = findByName(bean.getName());
		// Check if create Course already exist
		if (duplicataCourse != null) {
			throw new DuplicateRecordException("Course already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			System.out.println(pk + " in ModelJDBC");
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_COURSE VALUES(?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			/*pstmt.setLong(2, bean.getCourseId());*/
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDescription());
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
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("CourseModel add End");
		return pk;
	}


/**
 * Delete a Course
 * 
 * @param bean
 * @throws DatabaseException
 */
public void delete(CourseBean bean) throws ApplicationException {
    log.debug("Model delete Started");
    Connection conn = null;
    try {
        conn = JDBCDataSource.getConnection();
        conn.setAutoCommit(false); // Begin transaction
        PreparedStatement pstmt = conn
                .prepareStatement("DELETE FROM ST_COURSE WHERE ID=?");
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
 * Find User by Course
 * 
 * @param name
 *            : get parameter
 * @return bean
 * @throws DatabaseException
 */

public CourseBean findByName(String name) throws ApplicationException {
    log.debug("Model findBy EmailId Started");
    StringBuffer sql = new StringBuffer(
            "SELECT * FROM ST_COURSE WHERE NAME=?");
    CourseBean bean = null;
    Connection conn = null;
    try {
        conn = JDBCDataSource.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            bean = new CourseBean();
            bean.setId(rs.getLong(1));
            bean.setName(rs.getString(2));
            bean.setDescription(rs.getString(3));
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
 * Find Course by PK
 * 
 * @param pk
 *            : get parameter
 * @return bean
 * @throws DatabaseException
 */

public CourseBean findByPK(long pk) throws ApplicationException {
    log.debug("Model findByPK Started");
    StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE ID=?");
    CourseBean bean = null;
    Connection conn = null;
    try {
        conn = JDBCDataSource.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        pstmt.setLong(1, pk);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            bean = new CourseBean();
            bean.setId(rs.getLong(1));
            bean.setName(rs.getString(2));
            bean.setDescription(rs.getString(3));
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
 * Update a Course
 * 
 * @param bean
 * @throws DatabaseException
 */

public void update(CourseBean bean) throws ApplicationException,
        DuplicateRecordException {
    log.debug("Model update Started");
    Connection conn = null;

    CourseBean duplicataCourse = findByName(bean.getName());
    // Check if updated Course already exist
    if (duplicataCourse != null && duplicataCourse.getId() != bean.getId()) {
        throw new DuplicateRecordException("Course already exists");
    }
    try {
        conn = JDBCDataSource.getConnection();

        conn.setAutoCommit(false); // Begin transaction
        PreparedStatement pstmt = conn
                .prepareStatement("UPDATE ST_COURSE SET NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
        pstmt.setString(1, bean.getName());
        pstmt.setString(2, bean.getDescription());
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
 * Search Course
 * 
 * @param bean
 *            : Search Parameters
 * @throws DatabaseException
 */

public List search(CourseBean bean) throws ApplicationException {
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

public List search(CourseBean bean, int pageNo, int pageSize)
        throws ApplicationException {
    log.debug("Model search Started");
    StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE 1=1");

    if (bean != null) {
        if (bean.getId() > 0) {
            sql.append(" AND id = " + bean.getId());
        }
        if (bean.getName() != null && bean.getName().length() > 0) {
            sql.append(" AND NAME like '" + bean.getName() + "%'");
        }
        if (bean.getDescription() != null
                && bean.getDescription().length() > 0) {
            sql.append(" AND DESCRIPTION like '" + bean.getDescription()
                    + "%'");
        }

    }

    // if page size is greater than zero then apply pagination
    if (pageSize > 0) {
        // Calculate start record index
        pageNo = (pageNo - 1) * pageSize;

        sql.append(" Limit " + pageNo + ", " + pageSize);
        // sql.append(" limit " + pageNo + "," + pageSize);
    }

    ArrayList list = new ArrayList();
    Connection conn = null;
    try {
        conn = JDBCDataSource.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            bean = new CourseBean();
            bean.setId(rs.getLong(1));
            bean.setName(rs.getString(2));
            bean.setDescription(rs.getString(3));
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
 * Get List of Course
 * 
 * @return list : List of Course
 * @throws DatabaseException
 */

public List list() throws ApplicationException {
    return list(0, 0);
}

/**
 * Get List of Course with pagination
 * 
 * @return list : List of Course
 * @param pageNo
 *            : Current Page No.
 * @param pageSize
 *            : Size of Page
 * @throws DatabaseException
 */

public List list(int pageNo, int pageSize) throws ApplicationException {
    log.debug("Model list Started");
    ArrayList list = new ArrayList();
    StringBuffer sql = new StringBuffer("select * from ST_COURSE");
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
        	CourseBean bean = new CourseBean();
            bean.setId(rs.getLong(1));
            bean.setName(rs.getString(2));
            bean.setDescription(rs.getString(3));
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
                "Exception : Exception in getting list of course");
    } finally {
        JDBCDataSource.closeConnection(conn);
    }

    log.debug("Model list End");
    return list;

}
}
