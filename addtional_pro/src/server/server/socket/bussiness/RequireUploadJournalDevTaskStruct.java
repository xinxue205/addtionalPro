/**
 * 
 */
package server.server.socket.bussiness;

import java.util.Vector;


/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����12:47:34
 * @Description
 * @version 1.0 Shawn create
 */
public class RequireUploadJournalDevTaskStruct {
	// ��ǰ�������ļ���λ��
	private int pos;

	// ����ÿ̨�豸Ҫ�����ļ����ļ������ṹ������
	private Vector requireUploadJournalDevTaskStruct;

	public RequireUploadJournalDevTaskStruct() {
		this.requireUploadJournalDevTaskStruct = new Vector();
		this.pos = 0;
	}

	public void addPos() {
		this.pos++;
	}

	/**
	 * ����һ���ļ������ṹ��������
	 * 
	 * @param struct��Ҫ������ļ������ṹ
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
