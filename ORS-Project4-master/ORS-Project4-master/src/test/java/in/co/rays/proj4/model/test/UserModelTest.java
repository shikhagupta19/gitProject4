package in.co.rays.proj4.model.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.UserModel;

/**
 * User Model Test classes
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */

public class UserModelTest {

	// Model object to test
	public static UserModel model = new UserModel();
    public static UserBean bean=new UserBean();
	/**
	 * Main method to call test methods.
	 * 
	 * @param args
	 * @throws ParseException
	 * @throws DuplicateRecordException
	 * @throws DatabaseException 
	 * @throws ApplicationException 
	 */
	public static void main(String[] args) throws ParseException, DuplicateRecordException, ApplicationException, DatabaseException
	{
		testAdd();
		// testDelete();
		// testUpdate();
		// testFindByPK();
		// testFindByLogin();
		// testSearch();
		// testGetRoles();
		// testList();
		// testAuthenticate();
		// testRegisterUser();
		// testchangePassword();
		// testforgetPassword();
        // testresetPassword();

	}
    /**
     * Tests add a User
     * 
     * @throws ParseException
     * @throws DuplicateRecordException
     * @throws DatabaseException 
     * @throws ApplicationException 
     */
     public static void testAdd() throws ParseException,DuplicateRecordException, ApplicationException, DatabaseException
     {
    	 
    	 SimpleDateFormat sdf=new SimpleDateFormat("MM-DD-yyyy");
    	 bean.setFirstName("indu");
    	 bean.setLastName("pathak");
    	 bean.setLogin("indu@gmail.com");
    	 bean.setPassword("1234");
    	 bean.setConfirmPassword("1234");
    	 bean.setGender("male");
    	 bean.setDob(sdf.parse("07-10-1993"));
    	 bean.setMobileNo("9806613087");
    	 bean.setRoleId(1L);
    	 bean.setLastLogin(new Timestamp(new Date().getTime()));
    	 bean.setUnsucessfulLogin(2);
    	 bean.setLock("yes");
 
    	 long pk=model.add(bean);
    	 
    	 UserBean addedbean = model.findByPK(pk);
    	/* System.out.println(addedbean.getId());
    	 System.out.println(addedbean.getFirstName());
    	 System.out.println(addedbean.getLastName());
    	 System.out.println(addedbean.getLogin());
    	 System.out.println(addedbean.getPassword());
    	 System.out.println(addedbean.getGender());
    	 System.out.println(addedbean.getMobileNo());
    	 System.out.println(addedbean.getDob());
    	 System.out.println(addedbean.getRoleId());
    	 System.out.println(addedbean.getUnsucessfulLogin());
    	 System.out.println(addedbean.getLock());
    	 
         System.out.println("Test add successful");
        */ 
    	 
    	 if (addedbean == null) 
         System.out.println("Test add fail");
         
     }
     
     public static void testDelete() throws ApplicationException, DatabaseException
     {
    	 bean.setId(5);
    	 model.delete(bean);
     }
     
     public static void testUpdate() throws ApplicationException, DatabaseException, DuplicateRecordException
     {
    	 bean=model.findByPK(2);
    	 bean.setFirstName("salman");
    	 bean.setLastName("khan");
    	 bean.setGender("male");
    	 model.update(bean);
    	 UserBean addedbean=model.findByPK(2);
    	 
    	 System.out.println(addedbean.getId());
    	 System.out.println(addedbean.getFirstName());
    	 System.out.println(addedbean.getLastName());
    	 System.out.println(addedbean.getLogin());
    	 System.out.println(addedbean.getPassword());
    	 System.out.println(addedbean.getGender());
    	 System.out.println(addedbean.getMobileNo());
    	 System.out.println(addedbean.getDob());
    	 System.out.println(addedbean.getRoleId());
    	 System.out.println(addedbean.getUnsucessfulLogin());
    	 System.out.println(addedbean.getLock());
    	
     }
     
     public static void testGetRoles() 
     {

         try {
             List<UserBean> list = new ArrayList();
             bean.setRoleId(1);
             list = model.getRoles(bean);
             if (list.size() < 0) {
                 System.out.println("Test Get Roles fail");
             }
             Iterator<UserBean> it = list.iterator();
             while (it.hasNext()) {
                 bean =  it.next();
                 System.out.println(bean.getId());
                 System.out.println(bean.getFirstName());
                 System.out.println(bean.getLastName());
                 System.out.println(bean.getLogin());
                 System.out.println(bean.getPassword());
                 System.out.println(bean.getDob());
                 System.out.println(bean.getRoleId());
                 System.out.println(bean.getUnsucessfulLogin());
                 System.out.println(bean.getGender());
                 System.out.println(bean.getLastLogin());
                 System.out.println(bean.getLock());
             }
         } catch (ApplicationException e) {
             e.printStackTrace();
         }
     }
     
     public static void testSearch()
     {

         try {
        
             List list = new ArrayList();
             bean.setFirstName("salman");
             list = model.search(bean, 0, 0);
             if (list.size() < 0) {
                 System.out.println("Test Serach fail");
             }
             Iterator it = list.iterator();
             while (it.hasNext()) {
                 bean = (UserBean) it.next();
                 System.out.println(bean.getId());
                 System.out.println(bean.getFirstName());
                 System.out.println(bean.getLastName());
                 System.out.println(bean.getLogin());
                 System.out.println(bean.getPassword());
                 System.out.println(bean.getDob());
                 System.out.println(bean.getRoleId());
                 System.out.println(bean.getUnsucessfulLogin());
                 System.out.println(bean.getGender());
                 System.out.println(bean.getLastLogin());
                 System.out.println(bean.getLock());
             }

         } catch (ApplicationException e) {
             e.printStackTrace();
         }

     }

     public static void testRegisterUser() throws ParseException, DatabaseException {
         try {
            
             SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

             // bean.setId(8L);
             bean.setFirstName("sss");
              bean.setLastName("kkk");
             bean.setLogin("sourabh.pathak425@gmail.com");
             bean.setPassword("rr");
             bean.setConfirmPassword("4444");
             bean.setDob(sdf.parse("11/20/2015"));
             bean.setGender("Male");
             bean.setRoleId(2);
             long pk = model.registerUser(bean);
             System.out.println("Successfully register");
             System.out.println(bean.getFirstName());
             System.out.println(bean.getLogin());
             System.out.println(bean.getLastName());
             System.out.println(bean.getDob());
             UserBean registerbean = model.findByPK(pk);
             if (registerbean != null) {
                 System.out.println("Test registation fail");
             }
         } catch (ApplicationException e) {
             e.printStackTrace();
         } catch (DuplicateRecordException e) {
             e.printStackTrace();
         }
     }
     
     
}


