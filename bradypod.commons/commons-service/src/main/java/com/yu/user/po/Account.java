package com.yu.user.po;

import java.io.Serializable;

public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String passport;
	private String password;

	public Account() {
	}

	public Account(String passport, String password) {
		this.passport = passport;
		this.password = password;
	}

	public Account(String passport) {
		this.passport = passport;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return String.format("passport:%s, password:%s", passport, password);
	}

}
