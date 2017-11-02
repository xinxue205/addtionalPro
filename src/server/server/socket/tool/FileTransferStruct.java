/**
 * 
 */
package server.server.socket.tool;

import java.util.Vector;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午9:29:29
 * @Description
 * @version 1.0 Shawn create
 */
public class FileTransferStruct {
	// 保存每台设备要操作文件的文件操作结构的向量
	private Vector fileOperationStruct;

	// 当前操作的文件的位置
	private int pos;

	public FileTransferStruct() {
		fileOperationStruct = new Vector();
		pos = 0;
	}

	/**
	 * 增加一个文件操作结构都向量中
	 * 
	 * @param struct：要加入的文件操作结构
	 */
	public void addFileOperationStruct(FileOperationStruct struct) {
		fileOperationStruct.add(struct);
	}

	/**
	 * 当增加处理的文件操作结构结束后，指示位置加一
	 */
	public void addPos() {
		pos++;
	}

	/**
	 * 获取当前操作的文件操作结构
	 * 
	 * @return OperationStruct：返回当前操作的文件操作结构，如果都操作完了，则返回null
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
