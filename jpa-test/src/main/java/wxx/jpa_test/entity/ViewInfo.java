package wxx.jpa_test.entity;

public class ViewInfo {
	private TenantEntity e;

	private TenantEntity1 e1;
	
	public TenantEntity getE() {
		return e;
	}

	public void setE(TenantEntity e) {
		this.e = e;
	}

	public TenantEntity1 getE1() {
		return e1;
	}

	public void setE1(TenantEntity1 e1) {
		this.e1 = e1;
	}

	public ViewInfo(TenantEntity e, TenantEntity1 e1) {
		this.e = e;
		this.e1 = e1;
	}
}
