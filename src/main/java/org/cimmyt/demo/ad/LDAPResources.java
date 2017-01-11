package org.cimmyt.demo.ad;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Wrapper for ldap configurations, which can be passed by applicaiton.yml file, command line arguments or
 * system properties.
 * @author jarojas
 *
 */
@ConfigurationProperties("security.cimmyt.ldap")
class LDAPResources {
	String url;
	String managerDN;
	String managerPassword;
	String searchBase;
	String searchFilter;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getManagerDN() {
		return managerDN;
	}
	public void setManagerDN(String managerDN) {
		this.managerDN = managerDN;
	}
	public String getManagerPassword() {
		return managerPassword;
	}
	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}
	public String getSearchBase() {
		return searchBase;
	}
	public void setSearchBase(String searchBase) {
		this.searchBase = searchBase;
	}
	public String getSearchFilter() {
		return searchFilter;
	}
	public void setSearchFilter(String searchFilter) {
		this.searchFilter = searchFilter;
	}
	
}
