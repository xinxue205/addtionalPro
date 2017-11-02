/**
 * 
 */
package server.server.socket.bussiness;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 下午12:48:06
 * @Description
 * @version 1.0 Shawn create
 */
public class RequireUploadJournalfileTimeTaskStruct {
	private String atmFileName = null;

	private String devCode = null;

	private String devIP = null;

	private String fileTime = null;

	private boolean isEnforce = false;

	private String requireTime = null;

	private String transCode = null;

	private String transResult = null;

	private String transTime = null;

	public String getAtmFileName() {
		return atmFileName;
	}

	public String getDevCode() {
		return devCode;
	}

	public String getDevIP() {
		return devIP;
	}

	public String getFileTime() {
		return fileTime;
	}

	public boolean getIsEnforce() {
		return isEnforce;
	}

	public String getRequireTime() {
		return requireTime;
	}

	public String getTransCode() {
		return transCode;
	}

	public String getTransResult() {
		return transResult;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setAtmFileName(String atmFileName) {
		this.atmFileName = atmFileName;
	}

	public void setDevCode(String devCode) {
		this.devCode = devCode;
	}

	public void setDevIP(String devIP) {
		this.devIP = devIP;
	}

	public void setFileTime(String fileTime) {
		this.fileTime = fileTime;
	}

	public void setIsEnforce(boolean isEnforce) {
		this.isEnforce = isEnforce;
	}

	public void setRequireTime(String requireTime) {
		this.requireTime = requireTime;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public void setTransResult(String transResult) {
		this.transResult = transResult;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}


}
