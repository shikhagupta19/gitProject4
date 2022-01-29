package in.co.rays.proj4.model.test;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CollegeModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * College Model Test classes
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public class CollegeModelTest {

    /**
     * Model object to test
     */
	public static CollegeModel model=new CollegeModel();
	  /**
     * Main method to call test methods.
     * 
     * @param args
     * @throws DuplicateRecordException
     */
	public static void main(String []args)throws DuplicateRecordException{
		
		testAdd();
	  //	testDelete();
	  //	testUpdate();
	//	testFindByName();
	//	testFindByPK();
	//	testSearch();
		testList();
		
	}
	
	  /**
     * Tests add a College
     * 
     * @throws DuplicateRecordException
     */
	public static void testAdd()throws DuplicateRecordException{
		
		try{
			CollegeBean bean=new CollegeBean();
			 // bean.setId(2L);
			bean.setName("gsits");
			bean.setAddress("race course road");
			bean.setState("M.P");
			bean.setCity("indore");
			bean.setPhoneNo("9846739374");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk=model.add(bean);
			System.out.println("testAdd is Successfull"+pk);
			CollegeBean addedBean=model.findByPK(pk);
			if(addedBean==null){
				System.out.println("testAdd fail");
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


    /**
     * Tests delete a College
     */

	public static void testDelete(){
		try{
		CollegeBean bean=new CollegeBean();
		long pk=15L;
		bean.setId(pk);
		model.delete(bean);
		System.out.println("testDelete is Successful");
		
		CollegeBean deletedBean =new CollegeBean();
		if(deletedBean!=null){
			System.out.println("testDelete Fail");
		}
		}
		catch(ApplicationException e){
			e.printStackTrace();
			
		}
	
	
	
	}
	

    /**
     * Tests update a College
     */

		public static void testUpdate(){
			
			try{
				CollegeBean bean=model.findByPK(8L);
				bean.setName("mit");
				bean.setCity("bhopal");
				model.update(bean);
				System.out.println("testUpdate success");
				
				CollegeBean updatedBean=model.findByPK(2L);
				if(!"sankalp".equals(updatedBean.getName())){
					System.out.println("test Update fail");
				}
			}
				catch(ApplicationException e)
				{
					e.printStackTrace();
				}
				catch(DuplicateRecordException e){
					e.printStackTrace();
				}
				
			
		}
		

	    /**
	     * Tests find a College by Name.
	     */

		public static void testFindByName(){
			try{
				CollegeBean bean= model.findByName("SVIM");
				if(bean==null){
					System.out.println("testFindByPK fail");
				}
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println(bean.getState());
				System.out.println(bean.getCity());
				System.out.println(bean.getPhoneNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
				
			}catch(ApplicationException e){
				e.printStackTrace();
			}
		}
	

	    /**
	     * Tests find a College by PK.
	     */
		
		public static void testFindByPK(){
			try{
				CollegeBean bean=model.findByPK(2L);
				
				if(bean==null)
				{
					System.out.println("testFindByPk fail");
					
				}
				 System.out.println(bean.getId());
		            System.out.println(bean.getName());
		            System.out.println(bean.getAddress());
		            System.out.println(bean.getState());
		            System.out.println(bean.getCity());
		            System.out.println(bean.getPhoneNo());
		            System.out.println(bean.getCreatedBy());
		            System.out.println(bean.getCreatedDatetime());
		            System.out.println(bean.getModifiedBy());
		            System.out.println(bean.getModifiedDatetime());
		            
			}catch(ApplicationException e){
				e.printStackTrace();
			}
			
		}
		

	    /**
	     * Tests search a College by Name
	     */
		
		public static void testSearch(){
			try{
			CollegeBean bean=new CollegeBean();
			List list=new ArrayList();
			//bean.setName("LNCT");
			  bean.setAddress("Indore");
			list=model.search(bean, 1, 10);
				if(list.size()<0){
					System.out.println("testSearch fail");
				}
				Iterator it=list.iterator();
				while(it.hasNext()){
					bean=(CollegeBean)it.next();
					System.out.println(bean.getId());
					 System.out.println(bean.getName());
		                System.out.println(bean.getAddress());
		                System.out.println(bean.getState());
		                System.out.println(bean.getCity());
		                System.out.println(bean.getPhoneNo());
		                System.out.println(bean.getCreatedBy());
		                System.out.println(bean.getCreatedDatetime());
		                System.out.println(bean.getModifiedBy());
		                System.out.println(bean.getModifiedDatetime());
				}
			}catch(ApplicationException e){
				e.printStackTrace();
			}
		  }	
		

	    /**
	     * Tests get List a College.
	     */
			
		public static void testList(){
			
			try{
				CollegeBean bean=new CollegeBean();
				List list=new ArrayList<CollegeBean>();
				list=model.list(1,10);
				if(list.size()<0)
				{
					System.out.println("testList fail");
				}
				Iterator<CollegeBean> it=list.iterator();
				while(it.hasNext()){
					bean=it.next();
					System.out.println(bean.getId());
	                System.out.println(bean.getName());
	                System.out.println(bean.getAddress());
	                System.out.println(bean.getState());
	                System.out.println(bean.getCity());
	                System.out.println(bean.getPhoneNo());
	                System.out.println(bean.getCreatedBy());
	                System.out.println(bean.getCreatedDatetime());
	                System.out.println(bean.getModifiedBy());
	                System.out.println(bean.getModifiedDatetime());

				}
				
			}catch(ApplicationException e){
				e.printStackTrace();
			}
		}
		
		
}
