/**
 * 
 */
package server.server.socket.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import server.server.socket.JournalServerParams;


/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午9:30:15
 * @Description
 * @version 1.0 Shawn create
 */
public class FileOperationStruct {
	// 文件的md5值
	private String fileMD5 = "";

	// 源文件名称
	private String fileName = ""; 

	// 当前已发送的文件偏移量
	private long fileOffSet = 0;

	private String termCode = "";

	private boolean isLastBlock = false;

	private BufferedRandomAccessFile raf = null; // 随机文件输出流

	private BufferedRandomAccessFile rafInput = null; // 随机文件输入流
	
	private String sFileTime = "" ;   //文件时间
	
	private String sAtmFileName = "" ; //ATM电子流水文件名

	public int closeFileInputStream() {
		try {
			if (rafInput != null) {
				rafInput.close();
				rafInput = null;
			}
		} catch (IOException ex) {
			return -1;
		}
		return 0;
	}

	public int closeFileOutputStream() {
		try {
			if (raf != null) {
				raf.close();
				raf = null;
			}
		} catch (IOException ex) {
			return -1;
		}
		return 0;
	}

	public int createFileInputStream() {
		try {
			File file = new File(this.fileName);
			if (file.exists())
				rafInput = new BufferedRandomAccessFile(file, "r");
		} catch (FileNotFoundException ex) {
			return Integer.parseInt(JournalServerParams.FileNotFound);
		} catch (IOException e) {
			return Integer.parseInt(JournalServerParams.Error);
		}
		return 0;
	}
	public int createFileOutputStream() {
		try {
			File file = new File(this.fileName);
			raf = new BufferedRandomAccessFile(file, "rw");
		} catch (FileNotFoundException ex) {
			return Integer.parseInt(JournalServerParams.FileNotFound);
		} catch (IOException e) {
			return Integer.parseInt(JournalServerParams.Error);
		}
		return 0;
	}

	public String getFileMD5() {
		return this.fileMD5;
	}

	public String getFileName() {
		return this.fileName;
	}

	public long getFileOffSet() {
		return this.fileOffSet;
	}

	public boolean getIsLastBlock() {
		return isLastBlock;
	}
	
	public String getFileTime(){
		return this.sFileTime ;
	}

	public FileOperationStruct() {
	}

	public int readDataFromFile(byte[] readBytes, long lFileOffset) throws IOException {
		if (rafInput == null) {// 如果文件输入流为空,先创建文件输入流
			// 判断文件名是否为空，如果为空，直接返回空字符串
			if (this.fileName == null || this.fileName.equals("")) {
				return -1;
			}
			rafInput = new BufferedRandomAccessFile(new File(this.fileName), "r");
			// 计算文件输入流的偏移量
		}
		rafInput.seek(lFileOffset);
		int readLen = rafInput.read(readBytes, 0, JournalServerParams.JournalServerDataBlockMax);
		if (readLen <= 0) { // 如果读取到的数据长度小于等于0 返回错误
			return -1;
		}
		// 如果读取到的数据长度小于要要读取长度 就为最后一块数据
		if (readLen < JournalServerParams.JournalServerDataBlockMax)
			isLastBlock = true;
		return readLen;
	}

	public void setFileOffSet(long fileOffSet) {
		this.fileOffSet = fileOffSet;
	}

	public int writeDataToFile(byte[] writeBytes, long byteLen, long lFileOffset) throws IOException {
		if (raf == null) { // 如果文件输出流为空,先创建文件输出流
			// 判断文件名是否为空，如果为空，直接返回文件不存在
			if (this.fileName == null || this.fileName.equals("")) {
				return Integer.parseInt(JournalServerParams.FileNotFound);
			}
			File file = new File(this.fileName);
			raf = new BufferedRandomAccessFile(file, "rw");
			// 计算文件输出流的偏移量
		}
		raf.seek(lFileOffset);
		raf.write(writeBytes, 0, writeBytes.length);
		return 0;
	}

	public void setFileMD5(String fileMD5) {
		this.fileMD5 = fileMD5;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public void setIsLastBlock(boolean isLastBlock) {
		this.isLastBlock = isLastBlock;
	}
	
	public void setFileTime(String sTmpFileTime){
		this.sFileTime = sTmpFileTime ;
	}
	
	public void setAtmFileName(String sAtmFileName){
		this.sAtmFileName = sAtmFileName ;
	}
	
	public String getAtmFileName(){
		return this.sAtmFileName ;
	}



}
