/**
 * 
 */
package server.server.socket.bussiness;

import java.util.Vector;


/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 下午12:47:34
 * @Description
 * @version 1.0 Shawn create
 */
public class RequireUploadJournalDevTaskStruct {
	// 当前操作的文件的位置
	private int pos;

	// 保存每台设备要操作文件的文件操作结构的向量
	private Vector requireUploadJournalDevTaskStruct;

	public RequireUploadJournalDevTaskStruct() {
		this.requireUploadJournalDevTaskStruct = new Vector();
		this.pos = 0;
	}

	public void addPos() {
		this.pos++;
	}

	/**
	 * 增加一个文件操作结构都向量中
	 * 
	 * @param struct：要加入的文件操作结构
	 */
	public void addRequireUploadJournalDevTaskStruct(RequireUploadJournalfileTimeTaskStruct struct) {
		this.requireUploadJournalDevTaskStruct.add(struct);
	}

	public int getPos() {
		return this.pos;
	}

	public Vector getRequireUploadJournalDevTaskStruct() {
		return requireUploadJournalDevTaskStruct;
	}

	public RequireUploadJournalfileTimeTaskStruct getRequireUploadJournalfileTimeTaskStruct() {
		if (this.pos < requireUploadJournalDevTaskStruct.size()) {
			return (RequireUploadJournalfileTimeTaskStruct) this.requireUploadJournalDevTaskStruct
					.get(this.pos);
		} else
			return null;
	}

	public int getSize() {
		return this.requireUploadJournalDevTaskStruct.size();
	}


}
