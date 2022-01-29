package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * JDBC Implementation of FacultyModel
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class FacultyModel {
	private static Logger log = Logger.getLogger(FacultyModel.class);

	/**
	 * Find next PK of Faculty
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
	                .prepareStatement("SELECT MAX(ID) FROM ST_FACULTY");
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
	 * Add a Faculty
	 * 
	 * @param bean
	 * @throws DatabaseException
	 * 
	 */
	public long add(FacultyBean bean) throws ApplicationException,
	        DuplicateRecordException {
	    log.debug("Model add Started");
	    Connection conn = null;

	    // get Course Name
	    CourseModel cModel = new CourseModel();
	    CourseBean courseBean = cModel.findByPK(bean.getCourseId());
	    bean.setCourseName(courseBean.getName());

	    //get subject name
	    SubjectModel sModel = new SubjectModel();
	    SubjectBean subjectBean = sModel.findByPK(bean.getSubjectId());
	    bean.setSubjectName(subjectBean.getName());
System.out.println("subject name------->"+subjectBean.getName());
	  //get subject name
	    CollegeModel ccModel = new CollegeModel(); 
	    CollegeBean collegeBean = ccModel.findByPK(bean.getCollegeId());
	    bean.setCollegeName(collegeBean.getName());

	    
	    
	    FacultyBean duplicateName = findByEmailId(bean.getEmail());
	    System.out.println("email------->"+bean.getEmail());	   
	    FacultyBean duplicatesubject = findByEmailId(bean.getSubjectName());
	    System.out.println("subject name------->"+bean.getSubjectName());	    
	    int pk = 0;

	    if(duplicateName!=null){
	    	throw new DuplicateRecordException("Email and subject already exists");
	    }

	    try {
	        conn = JDBCDataSource.getConnection();
	        pk = nextPK();
	        // Get auto-generated next primary key
	        System.out.println(pk + " in ModelJDBC");
	        conn.setAutoCommit(false); // Begin transaction
	        PreparedStatement pstmt = conn
	                .prepareStatement("INSERT INTO ST_FACULTY VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
	        pstmt.setInt(1, pk);
	        pstmt.setLong(2, bean.getCourseId());

	        pstmt.setLong(3, bean.getSubjectId());
	        pstmt.setLong(4, bean.getCollegeId());
	        pstmt.setString(5, bean.getFirstName());
	        pstmt.setString(6, bean.getLastName());
	        pstmt.setDate(7, new java.sql.Date(bean.getDob().getTime()));
	        pstmt.setString(8, bean.getEmail());
	        pstmt.setString(9, bean.getMobileNo());
	        pstmt.setString(10, bean.getCreatedBy());
	        pstmt.setString(11, bean.getModifiedBy());
	        pstmt.setTimestamp(12, bean.getCreatedDatetime());
	        pstmt.setTimestamp(13, bean.getModifiedDatetime());
	        pstmt.executeUpdate();
	        conn.commit(); // End transaction
	        pstmt.close();
	    } catch (Exception e) {
	        log.error("Database Exception..", e);
	        try {
	            conn.rollback();
	        } catch (Exception ex) {
	            throw new ApplicationException(
	                    "Exception : add rollback exception " + ex.getMessage());
	        }
	        throw new ApplicationException(
	                "Exception : Exception in add Faculty");
	    } finally {
	        JDBCDataSource.closeConnection(conn);
	    }
	    log.debug("Model add End");
	    return pk;
	}

	/**
	 * Delete a Faculty
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */
	public void delete(FacultyBean bean) throws ApplicationException {
	    log.debug("Model delete Started");
	    Connection conn = null;
	    try {
	        conn = JDBCDataSource.getConnection();
	        conn.setAutoCommit(false); // Begin transaction
	        PreparedStatement pstmt = conn
	                .prepareStatement("DELETE FROM ST_FACULTY WHERE ID=?");
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
	                "Exception : Exception in delete Faculty");
	    } finally {
	        JDBCDataSource.closeConnection(conn);
	    }
	    log.debug("Model delete End");
	}

	/**
	 * Find User by Faculty
	 * 
	 * @param Email
	 *            : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */

	public FacultyBean findByEmailId(String Email) throws ApplicationException {
	    log.debug("Model findBy Email Started");
	    StringBuffer sql = new StringBuffer(
	            "SELECT * FROM ST_FACULTY WHERE EMAIL=?");
	    FacultyBean bean = null;
	    Connection conn = null;
	    try {
	        conn = JDBCDataSource.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	        pstmt.setString(1, Email);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            bean = new FacultyBean();
	            bean.setId(rs.getLong(1));
	            bean.setCourseId(rs.getLong(2));
	            bean.setSubjectId(rs.getLong(3));
	            bean.setCollegeId(rs.getLong(4));
	            bean.setFirstName(rs.getString(5));
	            bean.setLastName(rs.getString(6));
	            bean.setDob(rs.getDate(7));
	            bean.setEmail(rs.getString(8));
	            bean.setMobileNo(rs.getString(9));
	            bean.setCreatedBy(rs.getString(10));
	            bean.setModifiedBy(rs.getString(11));
	            bean.setCreatedDatetime(rs.getTimestamp(12));
	            bean.setModifiedDatetime(rs.getTimestamp(13));

	        }
	        rs.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        log.error("Database Exception..", e);
	        throw new ApplicationException(
	                "Exception : Exception in getting User by Email");
	    } finally {
	        JDBCDataSource.closeConnection(conn);
	    }
	    log.debug("Model findBy Email End");
	    return bean;
	}

	/**
	 * Find Faculty by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */

	public FacultyBean findByPK(long pk) throws ApplicationException {
	    log.debug("Model findByPK Started");
	    StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE ID=?");
	    FacultyBean bean = null;
	    Connection conn = null;
	    try {
	        conn = JDBCDataSource.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	        pstmt.setLong(1, pk);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            bean = new FacultyBean();
	            bean.setId(rs.getLong(1));
	            bean.setCourseId(rs.getLong(2));
	            bean.setSubjectId(rs.getLong(3));
	            bean.setCollegeId(rs.getLong(4));
	            bean.setFirstName(rs.getString(5));
	            bean.setLastName(rs.getString(6));
	            bean.setDob(rs.getDate(7));
	            bean.setEmail(rs.getString(8));
	            bean.setMobileNo(rs.getString(9));
	            bean.setCreatedBy(rs.getString(10));
	            bean.setModifiedBy(rs.getString(11));
	            bean.setCreatedDatetime(rs.getTimestamp(12));
	            bean.setModifiedDatetime(rs.getTimestamp(13));

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
	 * Update a Faculty
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */

	public void update(FacultyBean bean) throws ApplicationException,
	        DuplicateRecordException {
		System.out.println("Hello");
	    log.debug("Model update Started");
	    Connection conn = null;

	    FacultyBean beanExist = findByEmailId(bean.getEmail());

	    // Check if updated Roll no. already exist
	    if (beanExist != null && beanExist.getId() != bean.getId()) {
	        throw new DuplicateRecordException("Email Id is already exist");
	    }

	    // get Course Name
	    CourseModel cModel = new CourseModel();
	    CourseBean courseBean = cModel.findByPK(bean.getCourseId());
	    bean.setCourseName(courseBean.getName());

	    // get Subject Name
	    SubjectModel sModel = new SubjectModel();
	    SubjectBean subjectBean = sModel.findByPK(bean.getSubjectId());
	    bean.setSubjectName(subjectBean.getName());
	    
	    
	  //get subject name
	    CollegeModel ccModel = new CollegeModel(); 
	    CollegeBean collegeBean = ccModel.findByPK(bean.getCollegeId());
	    bean.setCollegeName(collegeBean.getName());

	    

	    try {

	        conn = JDBCDataSource.getConnection();

	        conn.setAutoCommit(false); // Begin transaction
	        PreparedStatement pstmt = conn
	                .prepareStatement("UPDATE ST_FACULTY SET COURSE_ID=?,SUBJECT_ID=?,COLLEGE_ID=?,FIRST_NAME=?,LAST_NAME=?,DOB=?,EMAIL=?,MOBILE_NO=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
	        pstmt.setLong(1, bean.getCourseId());
	        System.out.println(bean.getCourseId());
	        
	        pstmt.setLong(2, bean.getSubjectId());
	        System.out.println(bean.getSubjectId());

	        pstmt.setLong(3, bean.getCollegeId());
	        System.out.println(bean.getCollegeId());

	        
	        
	        pstmt.setString(4, bean.getFirstName());
	        System.out.println(bean.getFirstName());

	        pstmt.setString(5, bean.getLastName());
	        System.out.println(bean.getLastName());

	        pstmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));
	        System.out.println(bean.getDob().getTime());
	        
	        pstmt.setString(7, bean.getEmail());
	        System.out.println(bean.getEmail());
	        
	        pstmt.setString(8, bean.getMobileNo());
	        System.out.println(bean.getMobileNo());
	        
	        pstmt.setString(9, bean.getCreatedBy());
	        System.out.println(bean.getCreatedBy());
	        
	        pstmt.setString(10, bean.getModifiedBy());
	        System.out.println(bean.getModifiedBy());
	        
	        pstmt.setTimestamp(11, bean.getCreatedDatetime());
	        System.out.println(bean.getCreatedDatetime());
	        
	        pstmt.setTimestamp(12, bean.getModifiedDatetime());
	        System.out.println(bean.getModifiedDatetime());
	        
	        pstmt.setLong(13, bean.getId());
	        System.out.println(bean.getId());
	        pstmt.executeUpdate();
	        conn.commit(); // End transaction
	        pstmt.close();
	    } catch (Exception e) {
	        log.error("Database Exception..", e);
	        e.printStackTrace();
	        try {
	            conn.rollback();
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	            throw new ApplicationException(
	                    "Exception : Delete rollback exception "
	                            + ex.getMessage());
	            
	        }
	        throw new ApplicationException("Exception in updating Faculty ");
	    } finally {
	        JDBCDataSource.closeConnection(conn);
	    }
	    log.debug("Model update End");
	}

	/**
	 * Search Faculty
	 * 
	 * @param bean
	 *            : Search Parameters
	 * @throws DatabaseException
	 */

	public List search(FacultyBean bean) throws ApplicationException {
	    return search(bean, 0, 0);
	}

	/**
	 * Search Faculty with pagination
	 * 
	 * @return list : List of Faculty
	 * @param bean
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * 
	 * @throws DatabaseException
	 */

	public List search(FacultyBean bean, int pageNo, int pageSize)
	        throws ApplicationException {
	    log.debug("Model search Started");
	    StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE 1=1");

	    if (bean != null) {
	        if (bean.getId() > 0) {
	            sql.append(" AND id = " + bean.getId());
	        }
	        if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
	            sql.append(" AND FIRST_NAME like '" + bean.getFirstName()
	                    + "%'");
	        }
	        if (bean.getLastName() != null && bean.getLastName().length() > 0) {
	            sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
	        }
	        if (bean.getDob() != null && bean.getDob().getDate() > 0) {
	            sql.append(" AND DOB = " + bean.getDob());
	        }
	        if (bean.getEmail() != null && bean.getEmail().length() > 0) {
	            sql.append(" AND EMAIL like '" + bean.getEmail() + "%'");
	        }
	        if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
	            sql.append(" AND MOBILE_NO like '" + bean.getMobileNo() + "%'");
	        }
	        if (bean.getCourseName() != null
	                && bean.getCourseName().length() > 0) {
	            sql.append(" AND COURSE_NAME = " + bean.getCourseName());
	        }
	        if (bean.getSubjectName() != null
	                && bean.getSubjectName().length() > 0) {
	            sql.append(" AND SUBJECT_NAME = " + bean.getSubjectName());
	        }
	        if (bean.getCollegeName() != null
	                && bean.getCollegeName().length() > 0) {
	            sql.append(" AND COLLEGE_NAME = " + bean.getCollegeName());
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
	            bean = new FacultyBean();
	            bean.setId(rs.getLong(1));
	            bean.setCourseId(rs.getLong(2));
	            bean.setSubjectId(rs.getLong(3));
	            bean.setCollegeId(rs.getLong(4));
	            bean.setFirstName(rs.getString(5));
	            bean.setLastName(rs.getString(6));
	            bean.setDob(rs.getDate(7));
	            bean.setEmail(rs.getString(8));
	            bean.setMobileNo(rs.getString(9));
	            bean.setCreatedBy(rs.getString(10));
	            bean.setModifiedBy(rs.getString(11));
	            bean.setCreatedDatetime(rs.getTimestamp(12));
	            bean.setModifiedDatetime(rs.getTimestamp(13));
	            list.add(bean);
	        }
	        rs.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        log.error("Database Exception..", e);
	        throw new ApplicationException(
	                "Exception : Exception in search Faculty");
	    } finally {
	        JDBCDataSource.closeConnection(conn);
	    }

	    log.debug("Model search End");
	    return list;
	}

	/**
	 * Get List of Faculty
	 * 
	 * @return list : List of Faculty
	 * @throws DatabaseException
	 */

	public List list() throws ApplicationException {
	    return list(0, 0);
	}

	/**
	 * Get List of Faculty with pagination
	 * 
	 * @return list : List of Faculty
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
	    log.debug("Model list Started");
	    ArrayList list = new ArrayList();
	    StringBuffer sql = new StringBuffer("select * from ST_FACULTY");
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
	            FacultyBean bean = new FacultyBean();
	            bean.setId(rs.getLong(1));
	            bean.setCourseId(rs.getLong(2));
	            
	            bean.setSubjectId(rs.getLong(3));
	            bean.setCollegeId(rs.getLong(4));
	            bean.setFirstName(rs.getString(5));
	            bean.setLastName(rs.getString(6));
	            bean.setDob(rs.getDate(7));
	            bean.setEmail(rs.getString(8));
	            bean.setMobileNo(rs.getString(9));
	            bean.setCreatedBy(rs.getString(10));
	            bean.setModifiedBy(rs.getString(11));
	            bean.setCreatedDatetime(rs.getTimestamp(12));
	            bean.setModifiedDatetime(rs.getTimestamp(13));
	               list.add(bean);
	        }
	        rs.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        log.error("Database Exception..", e);
	        throw new ApplicationException(
	                "Exception : Exception in getting list of Faculty");
	    } finally {
	        JDBCDataSource.closeConnection(conn);
	    }

	    log.debug("Model list End");
	    return list;

	}

}
