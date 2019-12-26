package cn.sinobest.sdi.manager.web.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 历史作业排队实体类
 * @author jiangfeipeng
 */
@Entity
@Table(name = "T_JOB_HISTORY_SCHEDULE")
public class JobHistoryScheduled implements Serializable{

	private static final long serialVersionUID = 2146989571882558982L;
	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
	@Column(name = "ID")
	private String id;
	@Column(name = "LOGDATE")
	private Timestamp logdate;
	@Column(name = "COUNT")
	private long count;
	
	public JobHistoryScheduled(Object date, long count){
		this.logdate = Timestamp.valueOf(date.toString());
		this.count = count;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getLogdate() {
		return logdate;
	}
	public void setLogdate(Timestamp date) {
		this.logdate = date;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
}
