package file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * �ļ��Ա���
 * <br>������ �Ƚ��ļ��嵥1�����е��ļ��Ƿ񶼼�¼���ļ��嵥2��
 * @author wxx
 * @param projectFilePath--��Ŀ��ַ
 */
public class FileListComparer2 {
	public static void main(String[] args) {
		
		String listFile2=FileListComparer2.class.getResource("").getPath()+"list2.txt"; //�Ƚ��ļ�
		String totalFile=FileListComparer2.class.getResource("").getPath()+"list1.txt"; //���ļ�

		String totalFilesList[] = getTotalList(totalFile);
		comparerList(listFile2,totalFilesList);
		
		//��Ŀ3
		/*String projectPath2="E:/WorkSpace_Dev3/innermanage_csp"; //��Ŀ·������/��β��
		String listFile2="E:/WorkSpace_Dev3/innermanage_csp/deployment/patch/innermanage_csp_patch_20130705-20130710.txt"; //�嵥�ļ�λ��
		System.out.println("��Ŀ3"+flc.comparerList(projectPath2,listFile2));*/
	}
	
	/**
	 * 
	 * @param listFile1
	 * @param totalFilesList
	 */
	public static void comparerList(String listFile1, String[] totalFilesList){
		try {
			BufferedReader br = new BufferedReader(new FileReader(listFile1));
			String s = null;
			int i = 0,j = 0,k=0;
			while ((s=br.readLine())!=null) {
				j++;    //����
				if (s.trim().equals("")||s.startsWith("#")){
					continue;
				}
				
				boolean isExist = false; 
				for (int l = 0; l < totalFilesList.length; l++) {
					String temp = totalFilesList[l];
					if(s.equals(temp)){
						isExist = true;
						break;
					}
				}
				
				if (isExist==false){
					System.out.println("��"+j+"��--"+s+"--��ƥ���ļ�");
					i++;    //�����ļ���
				}
				k++;   //�ļ���
				s="";
			}
			System.out.println(listFile1+"�Ա���ɣ���"+k+"���ļ����������ļ�"+i+"��");
			System.out.println("-------------------------------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ȡ�쳣"); 
		}
		
	}
	
	static String[] getTotalList(String listFile2){
		String ss = "";
		String returnList = "";
		try {
			BufferedReader brr = new BufferedReader(new FileReader(listFile2));
			while((ss=brr.readLine())!=null){
				if (ss.trim().equals("")||ss.startsWith("#")){
					continue;
				}
				returnList += ss+"/&";
			}
			returnList = returnList.substring(0, returnList.length()-2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList.split("/&");
	}
}
