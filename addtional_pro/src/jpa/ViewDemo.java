package jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aa") // �������ͼ����
public class ViewDemo {
	@Id // ���һ���յ�id��ʶ����Ϊjpa��ӳ��ʵ������Ҫһ��id���������
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
