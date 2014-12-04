package com.iyiming.mobile.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.iyiming.mobile.constants.F;


public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	// ~ Instance fields
	// ================================================================================================
	private String password;// 密码
	private String username;// 登陆用户名
	private String imageUrl;// 头像
	private String realName;// 真实姓名
	private String mobile;// 联系电话
	private String email;// 电子邮件
	private String nickName;// 昵称
	private String city;// 城市
	private String sex;// 性别
	private String address;// 地址
	
	private String sessionId;//session

//	private final boolean accountNonExpired = true;
//	private final boolean accountNonLocked = true;
//	private final boolean credentialsNonExpired = true;
//	private final boolean enabled = true;

	/** 上一次登入时间 */
	private Date previousLoginTime;
	/** 最后登入时间 */
	private Date lastLoginTime;
	/** 用户状态，默认有效 */
	private String flowId = F.User.Common;

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	@Transient
//	public boolean isEnabled() {
//		return enabled;
//	}
//
//	@Transient
//	public boolean isAccountNonExpired() {
//		return accountNonExpired;
//	}
//
//	@Transient
//	public boolean isAccountNonLocked() {
//		return accountNonLocked;
//	}
//
//	@Transient
//	public boolean isCredentialsNonExpired() {
//		return credentialsNonExpired;
//	}

	public void eraseCredentials() {
		password = null;
	}

	/**
	 * Returns {@code true} if the supplied object is a {@code User} instance
	 * with the same {@code username} value.
	 * <p>
	 * In other words, the objects are equal if they have the same username,
	 * representing the same principal.
	 */
	@Override
	public boolean equals(Object rhs) {
		if (rhs instanceof User) {
			return username.equals(((User) rhs).username);
		}
		return false;
	}
//
//	/**
//	 * Returns the hashcode of the {@code username}.
//	 */
//	@Override
//	public int hashCode() {
//		return super.hashCode();
//	}

//	@Override
//	@Id
//	@GeneratedValue
//	public Long getId() {
//		return id;
//	}

	public Date getPreviousLoginTime() {
		return previousLoginTime;
	}

	public void setPreviousLoginTime(Date previousLoginTime) {
		this.previousLoginTime = previousLoginTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

//	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//	public Set<UserToProject> getUserToProjects() {
//		return userToProjects;
//	}
//
//	public void setUserToProjects(Set<UserToProject> userToProjects) {
//		this.userToProjects = userToProjects;
//	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
