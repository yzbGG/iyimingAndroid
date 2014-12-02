/**   
* @Title: Project.java 
* @Package com.iyiming.mobile.model.project 
* @Description: TODO(用一句话描述该文件做什么) 
* @author dkslbw@gmail.com   
* @date 2014年11月26日 上午11:43:39 
* @version V1.0   
*/
package com.iyiming.mobile.model.project;

import java.io.Serializable;

/** 
 * @ClassName: Project 
 * @Description: TODO(项目) 
 * @author dkslbw@gmail.com
 * @date 2014年11月26日 上午11:43:39 
 *  
 */
public class Project implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -905880610574124064L;

	private int id;
	
	private String name;
	
	private String intro;
	
	private float amt;
	
	private String type;
	
	private String country;
	
	private Long releaseDate;
	
	private String imageUrl;
	
	private String flowId;
	
	private int attentionCount;
	
	private String attentionFlag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getAmt() {
		return amt;
	}

	public void setAmt(float amt) {
		this.amt = amt;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Long releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public int getAttentionCount() {
		return attentionCount;
	}

	public void setAttentionCount(int attentionCount) {
		this.attentionCount = attentionCount;
	}

	public String getAttentionFlag() {
		return attentionFlag;
	}

	public void setAttentionFlag(String attentionFlag) {
		this.attentionFlag = attentionFlag;
	}
	
	

}
