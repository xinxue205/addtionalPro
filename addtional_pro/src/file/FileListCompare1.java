/**
 * 
 */
package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * ��ȡ���嵥�ļ��м�¼���ļ�����ָ��Ŀ¼����
 * @author wxx
 * @date 2014-6-12 ����11:42:13
 * @version 1.0 wxx create
 * @CopyRight (c) 2014 �����������ϵͳ���޹�˾
 */
public class FileListCompare1 {
	
	public static void main(String[] args) throws Exception {
		String fileList1 = "H:/ftp/201406���߰�/pre.txt";//����ǰ����
		String filePath1= "H:/ftp/201406���߰�/pre/";//����ǰ����
		System.out.println("-------�Ƚ�����ǰ�����ļ�");
		checkNoExistFile(fileList1,filePath1,false);
		
		String fileList2 = "H:/ftp/201406���߰�/aft.txt";//����ǰ����
		String filePath2= "H:/ftp/201406���߰�/aft/";//����ǰ����
		System.out.println("-------�Ƚ�����ǰ�����ļ�");
		checkNoExistFile(fileList2,filePath2,false);
		
	}
	
	/**
	 * ��֤�ļ��Ƿ����
	 * @param fileList
	 * @param filePath
	 * @param withPath �Ƿ��·��
	 * @throws Exception
	 */
	public static void checkNoExistFile(String fileList,String filePath,boolean withPath) throws Exception{
		List list1 = getFileList(fileList,withPath);
		List list = checkFileExist(list1,filePath);
		if (list==null || list.size()==0){
			System.out.println("�ļ�������");
		} else {
			System.out.println("��֤��ɣ��������ļ�������"+list.size());
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		}
	}
	
	/**
	 * 
	 * @param list1
	 * @param filePath �ļ���·��
	 * @return �����ڵ��嵥
	 * @throws Exception
	 */
	private static List  checkFileExist(List list1, String filePath) throws Exception {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		for (int i = 0; i < list1.size(); i++) {
			String fileName = (String) list1.get(i);
			File file = new File(filePath+fileName);
			if(!file.exists()){
				list.add(fileName);
			}
		}
		return list;
	}

	/**
	 * ��ȡ�ļ��嵥
	 * @param listFile1
	 * @param withPath �Ƿ��·��
	 * @return
	 * @throws Exception
	 */
	public static List getFileList(String listFile1,boolean withPath) throws Exception{
		BufferedReader brr = new BufferedReader(new FileReader(listFile1));
		List list = new ArrayList();
		String ss="";
		while((ss=brr.readLine())!=null){
			if(!"".equals(ss)){
				String strT =  ss; 
				if(!withPath){
					String fileName = strT.substring(strT.lastIndexOf("/")+1);
					strT = fileName;
				}
				list.add(strT);
			}
		}
		return list;
	}
}
