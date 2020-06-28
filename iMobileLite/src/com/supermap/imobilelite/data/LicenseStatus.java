package com.supermap.imobilelite.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.text.format.DateFormat;

/**
 * <p>
 * imobile移植类
 * </p>
 */

public class LicenseStatus {

	private boolean isRegister = false;
	

	private boolean isTrailLicense = false;
	

	private boolean isActivated = false;
	
	@Override
	public String toString() {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String trial = InternalResource.loadString("", InternalResource.TrialLicense, InternalResource.BundleName);
		String formal = InternalResource.loadString("", InternalResource.FormalLicense, InternalResource.BundleName);
		String type = isTrailLicense?trial:formal;
		String activated = InternalResource.loadString("", InternalResource.LicenseActivated, InternalResource.BundleName);
		String notActivated = InternalResource.loadString("", InternalResource.LicenseNotActivated, InternalResource.BundleName);
		
		String status;
		if (isActivated) {
			status = activated;
		} else if(isRegister) {
			status = InternalResource.loadString("", InternalResource.ValidLicense, InternalResource.BundleName);
		} else {
			if(isLicenseExsit){
				status = InternalResource.loadString("", InternalResource.InvaliLicense, InternalResource.BundleName);
			}else{
				status = InternalResource.loadString("", InternalResource.LicenseNotExsit, InternalResource.BundleName);
			}
		}
		return "LicenseStatus["
				+ "\nStatus = " + status
				+ "\nType = " + type
				+ "\nVersion = " + version
				+ "\nSartDate = " + formater.format(startDate)
				+ "\nExpireDate = " + formater.format(expireDate)
				+ "\n]";
 	}

	
	private long version = 0;
	

	private Date startDate = null;

	private Date expireDate = null;
	

	private boolean isLicenseExsit = false;


	public boolean isLicenseValid() {
		return isRegister;
	}
	

	public boolean isTrailLicense() {
		return isTrailLicense;
	}


	public long getVersion() {
		return version;
	}


	public Date getStartDate() {
		return startDate;
	}


	public Date getExpireDate() {
		return expireDate;
	}
	

	public boolean isLicenseExsit(){
		return isLicenseExsit;
	}
	
	
	public boolean isActivated(){
		return isActivated;
	}

	LicenseStatus(boolean isRegister, boolean isTrailLicense, long version,
			Date startDate, Date expireDate,boolean isLicenseExsit) {
		super();
		this.isRegister = isRegister;
		this.isTrailLicense = isTrailLicense;
		this.version = version;
		this.startDate = startDate;
		this.expireDate = expireDate;
		this.isLicenseExsit = isLicenseExsit;
		
		if (isLicenseExsit && isRegister && !isTrailLicense) {
			this.isActivated = true;
		} else {
			this.isActivated = false;
		}
	}
	
}
