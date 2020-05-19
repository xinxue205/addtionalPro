package wxx.jpa_test.entity;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "V_R_JOB")
public class JobDetail {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_JOB")
    private String id;
	@Column(name = "STATE")
	private String state;
	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	@Column(name = "CREATED_USER")
	private String createdUser;
	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;
	@Column(name = "MODIFIED_USER")
	private String modifiedUser;
	@Column(name = "WORKERID")
	private String worker;
	@Column(name = "NAME")
	private String jobName;
	@Column(name = "CURRSTARTTIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date currStartTime;
	@Column(name = "ERRCOUNT")
	public int errCount;
	@Column(name = "RUNCOUNT")
	private int runCount;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private String stringCreated;
	private String stringModified;
	private String stringCurrStart;
	private String runningTime;
	private boolean longRunning;
	
	public JobDetail(){
		
	}
	
	public JobDetail(Date createdDate, String createdUser, Date modifiedDate, String modifiedUser, int errCount, int runCount){
		this.stringCreated = df.format(createdDate);
		this.createdUser = createdUser;
		this.stringModified = df.format(modifiedDate);
		this.modifiedUser = modifiedUser;
		this.errCount = errCount;
		this.runCount = runCount;
	}
	
	public JobDetail(String worker, Date currStartTime){
		this.worker = worker;
		this.stringCurrStart = df.format(currStartTime);
		long millisTime = System.currentTimeMillis()- currStartTime.getTime();
		if(millisTime <= 60*1000){
			this.runningTime = millisTime / 1000 + "秒";
		}else if(millisTime <= 60*60*1000 && millisTime >= 60*1000){
			this.runningTime = millisTime / (60*1000) + "分"+millisTime%(60*1000)/1000+"秒";
		}else{
			this.runningTime = millisTime / (60*60*1000) + "小时" +millisTime%(60*60*1000)/(1000*60) + "分";
		}
	}
	
	public JobDetail(String jobName, Date currStartTime, String id){
		this.jobName = jobName;
		this.id = id;
		if(currStartTime != null ) {
			this.stringCurrStart = df.format(currStartTime);
			long millisTime = System.currentTimeMillis()- currStartTime.getTime();
			if(millisTime <= 60*1000){
				this.runningTime = millisTime / 1000 + "秒";
			}else if(millisTime <= 60*60*1000 && millisTime >= 60*1000){
				this.runningTime = millisTime / (60*1000) + "分";
			}else{
				this.runningTime = millisTime / (60*60*1000) + "小时";
			}
			if(millisTime <= 4 * 60 * 60 *1000 ){
				this.longRunning = false;
			} else {
				this.longRunning = true;
			}
		} else {
			this.stringCurrStart = "";
			this.runningTime = "";
			this.longRunning = false;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public Date getCurrStartTime() {
		return currStartTime;
	}

	public void setCurrStartTime(Date currStartTime) {
		this.currStartTime = currStartTime;
	}

	public String getStringCreated() {
		return stringCreated;
	}

	public void setStringCreated(String stringCreated) {
		this.stringCreated = stringCreated;
	}

	public String getStringModified() {
		return stringModified;
	}

	public void setStringModified(String stringModified) {
		this.stringModified = stringModified;
	}

	public String getStringCurrStart() {
		return stringCurrStart;
	}

	public void setStringCurrStart(String stringCurrStart) {
		this.stringCurrStart = stringCurrStart;
	}

	public String getRunningTime() {
		return runningTime;
	}

	public void setRunningTime(String runningTime) {
		this.runningTime = runningTime;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public boolean getLongRunning() {
		return longRunning;
	}

	public void setLongRunning(boolean longRunning) {
		this.longRunning = longRunning;
	}

	public int getErrCount() {
		return errCount;
	}

	public void setErrCount(int errCount) {
		this.errCount = errCount;
	}

	public int getRunCount() {
		return runCount;
	}

	public void setRunCount(int runCount) {
		this.runCount = runCount;
	}
	
	
}

