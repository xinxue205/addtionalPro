package jpa.left;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the r_database database table.
 * 
 */
@Entity
@Table(name="r_database")
@NamedQuery(name="RDatabase.findAll", query="SELECT r FROM RDatabase r")
public class RDatabase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_database")
	private Long idDatabase;

	@Column(name="connect_timeout")
	private String connectTimeout;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_user")
	private String createdUser;

	@Column(name="data_tbs")
	private String dataTbs;

	@Column(name="database_name")
	private String databaseName;

	@Column(name="host_name")
	private String hostName;

	@Column(name="id_database_contype")
	private Long idDatabaseContype;

	@Column(name="id_database_type")
	private Long idDatabaseType;

	@Column(name="index_tbs")
	private String indexTbs;

	@Column(name="modified_date")
	private Timestamp modifiedDate;

	@Column(name="modified_user")
	private String modifiedUser;

	private String name;

	private String password;

	private Integer port;

	private String servername;

	private String username;

	public RDatabase() {
	}

	public Long getIdDatabase() {
		return this.idDatabase;
	}

	public void setIdDatabase(Long idDatabase) {
		this.idDatabase = idDatabase;
	}

	public String getConnectTimeout() {
		return this.connectTimeout;
	}

	public void setConnectTimeout(String connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedUser() {
		return this.createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getDataTbs() {
		return this.dataTbs;
	}

	public void setDataTbs(String dataTbs) {
		this.dataTbs = dataTbs;
	}

	public String getDatabaseName() {
		return this.databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getHostName() {
		return this.hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Long getIdDatabaseContype() {
		return this.idDatabaseContype;
	}

	public void setIdDatabaseContype(Long idDatabaseContype) {
		this.idDatabaseContype = idDatabaseContype;
	}

	public Long getIdDatabaseType() {
		return this.idDatabaseType;
	}

	public void setIdDatabaseType(Long idDatabaseType) {
		this.idDatabaseType = idDatabaseType;
	}

	public String getIndexTbs() {
		return this.indexTbs;
	}

	public void setIndexTbs(String indexTbs) {
		this.indexTbs = indexTbs;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedUser() {
		return this.modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getServername() {
		return this.servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}