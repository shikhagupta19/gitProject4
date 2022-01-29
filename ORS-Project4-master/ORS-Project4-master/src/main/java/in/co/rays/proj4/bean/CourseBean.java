package in.co.rays.proj4.bean;

import in.co.rays.proj4.bean.BaseBean;

// TODO: Auto-generated Javadoc
/**
 * The Class CourseBean.
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class CourseBean extends BaseBean {

	
	/** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.bean.DropdownListBean#getKey()
	 */
	public String getKey() {
		
		return id+"";
	}

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.bean.DropdownListBean#getValue()
	 */
	public String getValue() {
		
		return name;
	}

}
