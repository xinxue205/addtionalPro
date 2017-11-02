/**
 * 
 */
package server.server.socket.tool;

import java.util.Vector;

/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����9:29:29
 * @Description
 * @version 1.0 Shawn create
 */
public class FileTransferStruct {
	// ����ÿ̨�豸Ҫ�����ļ����ļ������ṹ������
	private Vector fileOperationStruct;

	// ��ǰ�������ļ���λ��
	private int pos;

	public FileTransferStruct() {
		fileOperationStruct = new Vector();
		pos = 0;
	}

	/**
	 * ����һ���ļ������ṹ��������
	 * 
	 * @param struct��Ҫ������ļ������ṹ
	 */
	public void addFileOperationStruct(FileOperationStruct struct) {
		fileOperationStruct.add(struct);
	}

	/**
	 * �����Ӵ�����ļ������ṹ������ָʾλ�ü�һ
	 */
	public void addPos() {
		pos++;
	}

	/**
	 * ��ȡ��ǰ�������ļ������ṹ
	 * 
	 * @return OperationStruct�����ص�ǰ�������ļ������ṹ��������������ˣ��򷵻�null
	 */
	public FileOperationStruct getFileOperationStruct() {
		if (pos < fileOperationStruct.size()) {
			return (FileOperationStruct) fileOperationStruct.get(pos);
		} else
			return null;
	}

	public int getPos() {
		return pos;
	}

	public int getSize() {
		return fileOperationStruct.size();
	}


}
