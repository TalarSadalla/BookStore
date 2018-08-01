package pl.jstk.to;

import pl.jstk.enumerations.UserRole;

public class UserTo {
	private Long id;
	private String userName;
	private String password;
	private boolean enable;
	private UserRole role;

	public UserTo() {
	}

	public UserTo(Long id, String userName, String password, boolean enable, UserRole role) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.enable = enable;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String user) {
		this.userName = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

}
