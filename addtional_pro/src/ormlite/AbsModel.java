package ormlite;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;

public abstract class AbsModel {

	@DatabaseField(id = true, columnName = "ID")
	protected String id;

	@DatabaseField(columnName = "DELETED", format = "integer")
	protected boolean deleted;
	
	@DatabaseField(columnName = "DELETEDTIME")
	private Date deletedTime;
	
	@DatabaseField(columnName = "CREATEDTIME")
	private Date createdTime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Date getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(Date deletedTime) {
		this.deletedTime = deletedTime;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	
}
