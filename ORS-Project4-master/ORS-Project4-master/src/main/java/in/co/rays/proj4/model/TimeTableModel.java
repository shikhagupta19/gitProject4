package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.TimeTableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * JDBC Implementation of Timetable Model
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class TimeTableModel {
	private static Logger log = Logger.getLogger(TimeTableModel.class);

    /**
     * Find next PK of TimeTable
     * 
     * @throws DatabaseException
     */
    public Integer nextPK() throws DatabaseException {
        log.debug("Model nextPK Started");
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_TIMETABLE");
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
     * Add a TimeTable
     * 
     * @param bean
     * @throws DatabaseException
     * 
     */
    public long add(TimeTableBean bean) throws ApplicationException, DuplicateRecordException {
        log.debug("Model add Started");
        Connection conn = null;

        int pk = 0;
        
        TimeTableBean existbean = findByCouSubSem( bean.getCourseId(),bean.getSemester(),bean.getSubjectId());
        TimeTableBean existbean2 = findByDateCouSem(new java.sql.Date(bean.getExamDate().getTime()),bean.getCourseId(),bean.getSemester());
        
        if (existbean != null || existbean2 != null) {
            throw new DuplicateRecordException("TimeTable  already exists");
        }
        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();
            // Get auto-generated next primary key
            System.out.println(pk + " in ModelJDBC");
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_TIMETABLE VALUES(?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, pk);
            pstmt.setLong(2, bean.getCourseId());
            pstmt.setString(3, bean.getSemester());
            pstmt.setLong(4, bean.getSubjectId());
            pstmt.setDate(5, new java.sql.Date(bean.getExamDate().getTime()));
            pstmt.setString(6, bean.getExamTime());
            pstmt.setString(7, bean.getCreatedBy());
            pstmt.setString(8, bean.getModifiedBy());
            pstmt.setTimestamp(9, bean.getCreatedDatetime());
            pstmt.setTimestamp(10, bean.getModifiedDatetime());
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
                throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException(
                    "Exception : Exception in add TimeTable");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model add End");
        return pk;
    }

    /**
     * Delete a TimeTable
     * 
     * @param bean
     * @throws DatabaseException
     */
    public void delete(TimeTableBean bean) throws ApplicationException {
        log.debug("Model delete Started");
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_TIMETABLE WHERE ID=?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();
            conn.commit(); // End transaction
            pstmt.close();

        } catch (Exception e) {
            log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception : Exception in delete TimeTable");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model delete Started");
    }

    /**
     * Find TimeTable by PK
     * 
     * @param pk
     *            : get parameter
     * @return bean
     * @throws DatabaseException
     */

    public TimeTableBean findByPK(long pk) throws ApplicationException {
        log.debug("Model findByPK Started");
        StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE ID=?");
        TimeTableBean bean = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new TimeTableBean();
                bean.setId(rs.getLong(1));
                bean.setCourseId(rs.getLong(2));
                bean.setSemester(rs.getString(3));
                bean.setSubjectId(rs.getLong(4));
                bean.setExamDate(rs.getDate(5));
                bean.setExamTime(rs.getString(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDatetime(rs.getTimestamp(9));
                bean.setModifiedDatetime(rs.getTimestamp(10));

            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting TimeTable by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findByPK End");
        return bean;
    }
    /**
     * Find TimeTable by course subject and semester
     * 
     * @param pk
     *            : get parameter
     * @return bean
     * @throws DatabaseException
     */

    public TimeTableBean findByCouSubSem(long CourseId,String Semester, long SubjectId) throws ApplicationException {
        log.debug("Model findByCourSubSem Started");
        StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE  COURSE_ID=? AND SEMESTER=? AND SUBJECT_ID=?");
        TimeTableBean bean = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, CourseId);
            pstmt.setString(2, Semester);
            pstmt.setLong(3, SubjectId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new TimeTableBean();
                bean.setId(rs.getLong(1));
                bean.setCourseId(rs.getLong(2));
                bean.setSemester(rs.getString(3));
                bean.setSubjectId(rs.getLong(4));
                bean.setExamDate(rs.getDate(5));
                bean.setExamTime(rs.getString(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDatetime(rs.getTimestamp(9));
                bean.setModifiedDatetime(rs.getTimestamp(10));

            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException(
                    "Exception : Exception in getting TimeTable by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findByPK End");
        return bean;
    }
    /**
     * Find TimeTable by examdate course semester
     * 
     * @param pk
     *            : get parameter
     * @return bean
     * @throws DatabaseException
     */

    public TimeTableBean findByDateCouSem(Date examdate,long CourseId, String Semester) throws ApplicationException {
        log.debug("Model findByDateCouSem Started");
        StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE  EXAM_DATE=? AND COURSE_ID=? AND SEMESTER=?");
        TimeTableBean bean = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
           pstmt.setDate(1, (java.sql.Date) examdate);
            pstmt.setLong(2, CourseId);
            pstmt.setString(3, Semester);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new TimeTableBean();
                bean.setId(rs.getLong(1));
                bean.setCourseId(rs.getLong(2));
                bean.setSemester(rs.getString(3));
                bean.setSubjectId(rs.getLong(4));
                bean.setExamDate(rs.getDate(5));
                bean.setExamTime(rs.getString(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDatetime(rs.getTimestamp(9));
                bean.setModifiedDatetime(rs.getTimestamp(10));

            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException(
                    "Exception : Exception in getting TimeTable by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findByDateCouSem End");
        return bean;
    }

    /**
     * Update a TimeTable
     * 
     * @param bean
     * @throws DatabaseException
     */
    public void update(TimeTableBean bean) throws ApplicationException,
    DuplicateRecordException {
log.debug("Model update Started");
Connection conn = null;


TimeTableBean existbean = findByCouSubSem( bean.getCourseId(),bean.getSemester(),bean.getSubjectId());
TimeTableBean existbean2 = findByDateCouSem(new java.sql.Date(bean.getExamDate().getTime()),bean.getCourseId(),bean.getSemester());
//System.out.println("exist bean1 id"+existbean.getId());
//System.out.println("exist bean2 id"+existbean2.getId());
//System.out.println(" bean id"+bean.getId());

if (existbean != null&&existbean.getId()!=bean.getId() || existbean2 != null&&existbean2.getId()!=bean.getId()) {
    throw new DuplicateRecordException("TimeTable  already exists");

   

}

try {
    conn = JDBCDataSource.getConnection();

    conn.setAutoCommit(false); // Begin transaction
    PreparedStatement pstmt = conn.prepareStatement("UPDATE ST_TIMETABLE SET COURSE_ID=?,SEMESTER=?,SUBJECT_ID=?"
    		+ ",EXAM_DATE=?,EXAM_TIME=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
    pstmt.setLong(1, bean.getCourseId());
    pstmt.setString(2, bean.getSemester());
    pstmt.setLong(3, bean.getSubjectId());
    pstmt.setDate(4, new java.sql.Date(bean.getExamDate().getTime()));
    pstmt.setString(5, bean.getExamTime());
    pstmt.setString(6, bean.getCreatedBy());
    pstmt.setString(7, bean.getModifiedBy());
    pstmt.setTimestamp(8, bean.getCreatedDatetime());
    pstmt.setTimestamp(9, bean.getModifiedDatetime());
    pstmt.setLong(10, bean.getId());
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
        throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
    }
    throw new ApplicationException("Exception in updating Course ");
} finally {
    JDBCDataSource.closeConnection(conn);
}
log.debug("Model update End");
}

    /**
     * Search TimeTable
     * 
     * @param bean
     *            : Search Parameters
     * @throws DatabaseException
     */

    public List search(TimeTableBean bean) throws ApplicationException {
        return search(bean, 0, 0);
    }

    /**
     * Search TimeTable with pagination
     * 
     * @return list : List of TimeTable
     * @param bean
     *            : Search Parameters
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * 
     * @throws DatabaseException
     */

    public List search(TimeTableBean bean, int pageNo, int pageSize) throws ApplicationException {
        log.debug("Model search Started");
        System.out.println("bean obj date value"+bean.getExamDate());
        StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE 1=1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" AND id = " + bean.getId());
            }
            if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
                sql.append(" AND COURSE_ID= " + bean.getCourseId());
            }
            if (bean.getSubjectName() != null && bean.getSubjectName().length() > 0) {
                sql.append(" AND SUBJECT_ID=" + bean.getSubjectId() );
            }
            if (bean.getExamDate() != null && bean.getExamDate().getDate() > 0) {
                sql.append(" AND EXAM_DATE like'" +new java.sql.Date (bean.getExamDate().getTime())+"%'");
                System.out.println("sql query"+sql);
            }
            if (bean.getExamTime() != null) {
                sql.append(" AND EXAM_TIME like '" + bean.getExamTime() + "%'");
            }

        }

        // if page size is greater than zero then apply pagination
        if (pageSize > 0) {
            // Calculate start record index
            pageNo = (pageNo - 1) * pageSize;

            sql.append(" Limit " + pageNo + ", " + pageSize);
            // sql.append(" limit " + pageNo + "," + pageSize);
        }
        System.out.println("query----------->"+sql);

        ArrayList list = new ArrayList();
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            	 bean = new TimeTableBean();
            	 bean.setId(rs.getLong(1));
                 bean.setCourseId(rs.getLong(2));
                 bean.setSemester(rs.getString(3));
                 bean.setSubjectId(rs.getLong(4));
                 bean.setExamDate(rs.getDate(5));
                 bean.setExamTime(rs.getString(6));
                 bean.setCreatedBy(rs.getString(7));
                 bean.setModifiedBy(rs.getString(8));
                 bean.setCreatedDatetime(rs.getTimestamp(9));
                 bean.setModifiedDatetime(rs.getTimestamp(10));
                 list.add(bean);
            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            e.printStackTrace();
            throw new ApplicationException("Exception : Exception in search TimeTable");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model search End");
        return list;
    }

    /**
     * Get List of TimeTable
     * 
     * @return list : List of TimeTable
     * @throws DatabaseException
     */

    public List list() throws ApplicationException {
        return list(0, 0);
    }

    /**
     * Get List of TimeTable with pagination
     * 
     * @return list : List of TimeTable
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws DatabaseException
     */

    public List list(int pageNo, int pageSize) throws ApplicationException {
        log.debug("Model list Started");
        ArrayList list = new ArrayList();
        StringBuffer sql = new StringBuffer("select * from ST_TIMETABLE");
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
            	TimeTableBean bean = new TimeTableBean();
			    bean.setId(rs.getLong(1)); 
                bean.setCourseId(rs.getLong(2));
                bean.setSemester(rs.getString(3));
                bean.setSubjectId(rs.getLong(4));
                bean.setExamDate(rs.getDate(5));
                bean.setExamTime(rs.getString(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDatetime(rs.getTimestamp(9));
                bean.setModifiedDatetime(rs.getTimestamp(10));
                list.add(bean);
            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException("Exception : Exception in getting list of TimeTable");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model list End");
        return list;

    }

	
}
