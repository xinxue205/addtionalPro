package jpa.left;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_database_type_contype_conf database table.
 * 
 */
@Entity
@Table(name="t_database_type_contype_conf")
@NamedQuery(name="TDatabaseTypeContypeConf.findAll", query="SELECT t FROM TDatabaseTypeContypeConf t")
public class TDatabaseTypeContypeConf implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="default_value")
	private String defaultValue;

	@Column(name="id_database_conf")
	private Long idDatabaseConf;

	@Column(name="id_database_contype")
	private Long idDatabaseContype;

	@Column(name="id_database_type")
	private Long idDatabaseType;

	public TDatabaseTypeContypeConf() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Long getIdDatabaseConf() {
		return this.idDatabaseConf;
	}

	public void setIdDatabaseConf(Long idDatabaseConf) {
		this.idDatabaseConf = idDatabaseConf;
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

}