package jpa.view;

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
@Table(name = "v_t_role_entity_hosts_config")
public class RoleEntityHostsConfig {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long config_id;
	
    @Column(name = "status")
    private String status;
	@Column(name = "ip")
	private String ip;
	@Column(name = "start_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date start_time;
	@Column(name = "role_entity_id")
	private long role_entity_id;
	@Column(name = "group_id")
	private String group_id;
	@Column(name = "is_modified")
	private String is_modified;
	@Column(name = "role_entity_name")
	private String role_entity_name;
	@Column(name = "hostname")
	private String hostname;
	@Column(name = "config_value")
	private String config_value;
	@Column(name = "config_type_id")
	public String config_type_id;
	@Column(name = "configuration_code")
	private String configuration_code;
	
	


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public Date getStart_time() {
		return start_time;
	}


	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}


	public long getRole_entity_id() {
		return role_entity_id;
	}


	public void setRole_entity_id(long role_entity_id) {
		this.role_entity_id = role_entity_id;
	}


	public RoleEntityHostsConfig() {
		super();
	}


	public String getGroup_id() {
		return group_id;
	}


	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}


	public String getIs_modified() {
		return is_modified;
	}


	public void setIs_modified(String is_modified) {
		this.is_modified = is_modified;
	}


	public String getRole_entity_name() {
		return role_entity_name;
	}


	public void setRole_entity_name(String role_entity_name) {
		this.role_entity_name = role_entity_name;
	}


	public String getHostname() {
		return hostname;
	}


	public void setHostname(String hostname) {
		this.hostname = hostname;
	}


	public String getConfig_value() {
		return config_value;
	}


	public void setConfig_value(String config_value) {
		this.config_value = config_value;
	}


	public String getConfig_type_id() {
		return config_type_id;
	}


	public void setConfig_type_id(String config_type_id) {
		this.config_type_id = config_type_id;
	}


	public String getConfiguration_code() {
		return configuration_code;
	}


	public void setConfiguration_code(String configuration_code) {
		this.configuration_code = configuration_code;
	}
	
}

