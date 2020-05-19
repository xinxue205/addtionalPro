package wxx.jpa_test.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tenant")
public class TenantEntity implements Serializable {

    private static final long serialVersionUID = -6160322787230271783L;
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "additional_info")
    private String additionInfo;

    public TenantEntity(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdditionInfo() {
		return additionInfo;
	}

	public void setAdditionInfo(String additionInfo) {
		this.additionInfo = additionInfo;
	}
}