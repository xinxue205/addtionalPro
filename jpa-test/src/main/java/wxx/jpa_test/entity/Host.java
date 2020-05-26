package wxx.jpa_test.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigInteger;


/**
 * The persistent class for the hosts database table.
 * 
 */
@Entity
@Table(name="hosts")
@NamedQuery(name="Host.findAll", query="SELECT h FROM Host h")
public class Host implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String ip;

	private int cpu;

	@Column(name="disk_totle")
	private BigInteger diskTotle;

	@Column(name="disk_used")
	private BigInteger diskUsed;

	private String hostname;

	@Column(name="last_time")
	private Timestamp lastTime;

	@Lob
	@Column(name="mem_totle")
	private String memTotle;

	@Lob
	@Column(name="mem_used")
	private String memUsed;

	private String oclocation;

	@Column(name="octopus_port")
	private int octopusPort;

	private String os;

	@Lob
	private String password;

	@Column(name="ssh_port")
	private int sshPort;

	private String user;

	public Host() {
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getCpu() {
		return this.cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public BigInteger getDiskTotle() {
		return this.diskTotle;
	}

	public void setDiskTotle(BigInteger diskTotle) {
		this.diskTotle = diskTotle;
	}

	public BigInteger getDiskUsed() {
		return this.diskUsed;
	}

	public void setDiskUsed(BigInteger diskUsed) {
		this.diskUsed = diskUsed;
	}

	public String getHostname() {
		return this.hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Timestamp getLastTime() {
		return this.lastTime;
	}

	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}

	public String getMemTotle() {
		return this.memTotle;
	}

	public void setMemTotle(String memTotle) {
		this.memTotle = memTotle;
	}

	public String getMemUsed() {
		return this.memUsed;
	}

	public void setMemUsed(String memUsed) {
		this.memUsed = memUsed;
	}

	public String getOclocation() {
		return this.oclocation;
	}

	public void setOclocation(String oclocation) {
		this.oclocation = oclocation;
	}

	public int getOctopusPort() {
		return this.octopusPort;
	}

	public void setOctopusPort(int octopusPort) {
		this.octopusPort = octopusPort;
	}

	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSshPort() {
		return this.sshPort;
	}

	public void setSshPort(int sshPort) {
		this.sshPort = sshPort;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}