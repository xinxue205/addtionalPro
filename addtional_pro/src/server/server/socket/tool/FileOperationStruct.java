/**
 * 
 */
package server.server.socket.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import server.server.socket.JournalServerParams;


/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����9:30:15
 * @Description
 * @version 1.0 Shawn create
 */
public class FileOperationStruct {
	// �ļ���md5ֵ
	private String fileMD5 = "";

	// Դ�ļ�����
	private String fileName = ""; 

	// ��ǰ�ѷ��͵��ļ�ƫ����
	private long fileOffSet = 0;

	private String termCode = "";

	private boolean isLastBlock = false;

	private BufferedRandomAccessFile raf = null; // ����ļ������

	private BufferedRandomAccessFile rafInput = null; // ����ļ�������
	
	private String sFileTime = "" ;   //�ļ�ʱ��
	
	private String sAtmFileName = "" ; //ATM������ˮ�ļ���

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
		if (rafInput == null) {// ����ļ�������Ϊ��,�ȴ����ļ�������
			// �ж��ļ����Ƿ�Ϊ�գ����Ϊ�գ�ֱ�ӷ��ؿ��ַ���
			if (this.fileName == null || this.fileName.equals("")) {
				return -1;
			}
			rafInput = new BufferedRandomAccessFile(new File(this.fileName), "r");
			// �����ļ���������ƫ����
		}
		rafInput.seek(lFileOffset);
		int readLen = rafInput.read(readBytes, 0, JournalServerParams.JournalServerDataBlockMax);
		if (readLen <= 0) { // �����ȡ�������ݳ���С�ڵ���0 ���ش���
			return -1;
		}
		// �����ȡ�������ݳ���С��ҪҪ��ȡ���� ��Ϊ���һ������
		if (readLen < JournalServerParams.JournalServerDataBlockMax)
			isLastBlock = true;
		return readLen;
	}

	public void setFileOffSet(long fileOffSet) {
		this.fileOffSet = fileOffSet;
	}

	public int writeDataToFile(byte[] writeBytes, long byteLen, long lFileOffset) throws IOException {
		if (raf == null) { // ����ļ������Ϊ��,�ȴ����ļ������
			// �ж��ļ����Ƿ�Ϊ�գ����Ϊ�գ�ֱ�ӷ����ļ�������
			if (this.fileName == null || this.fileName.equals("")) {
				return Integer.parseInt(JournalServerParams.FileNotFound);
			}
			File file = new File(this.fileName);
			raf = new BufferedRandomAccessFile(file, "rw");
			// �����ļ��������ƫ����
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
