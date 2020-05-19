package jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aa") // 这个是视图名称
public class ViewDemo {
	@Id // 添加一个空的id标识，因为jpa在映射实体是需要一个id，这个必须
	@Column(name = "id")
	private String id;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "stdval")
	private String stdval;
	
	@Column(name = "COMMENTS")
	private String COMMENTS;
	
	@Column(name = "typeid")
	private String typeid;
	
	@Column(name = "hostIp")
	private String hostIp;
	
	@Column(name = "hostPath")
	private String hostPath;
	
	@Column(name = "confPer")
	private String confPer;
	
	
}
