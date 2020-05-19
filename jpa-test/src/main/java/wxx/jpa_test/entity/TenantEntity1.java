package wxx.jpa_test.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tenant1")
public class TenantEntity1 implements Serializable {

    private static final long serialVersionUID = -6160322787230271783L;
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "tenant_month")
    private String tenant_month;
    @Column(name = "tenant_money")
    private String tenant_money;

    public TenantEntity1(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTenantMonth() {
		return tenant_month;
	}

	public void setTenantMonth(String tenant_month) {
		this.tenant_month = tenant_month;
	}

	public String getTenantMoney() {
		return tenant_money;
	}

	public void setTenantMoney(String additionInfo) {
		this.tenant_money = additionInfo;
	}
}