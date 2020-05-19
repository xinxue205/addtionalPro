package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
/**
 * �ļ��Ա���
 * <br>������ �Ƚ�FILELIST�ļ��嵥��ָ�����ļ��Ƿ���ȷ����
 * @author wxx
 * @param listFile--�嵥��ַ
 * @param projectPath--��Ŀ��ַ
 */
public class FileListComparer {
	public static void main(String[] args) {
		//��Ŀ����·��
		String listFileAll = "H:/WorkProjects/YC_EmpSys/src/file/fileList.txt";  //�嵥�ļ�·��
		//String listFileAll = "H:/WorkProjects/YC_EmpSys/src/file/fileList_201403.txt";  //�嵥�ļ�·��
		String projectPathWeb="H:/WorkProjects/huangjun2.zh_ATMS_ATMV1.0_APP_Dev_wxx/vobs/ATMS_ATMV_APP/ATMS_ATMV_APP/NewATMVH_New/WebRoot/"; //��Ŀ·��
		String projectPathAPP="H:/WorkProjects/huangjun2.zh_ATMS_ATMV1.0_APP_Dev_wxx/vobs/ATMS_ATMV_APP/ATMS_ATMV_APP/ATMVH_APP/bin/"; //��Ŀ·��
		String projectPathReport="H:/WorkProjects/huangjun2.zh_ATMS_ATMV1.0_APP_Dev_wxx/vobs/ATMS_ATMV_APP/ATMS_ATMV_APP/report/WebRoot/"; //��Ŀ·��
		
		try {
			//�嵥�ļ�·��
			File listFile = new File(listFileAll);
			if (!listFile.exists()) {
				System.out.println("�嵥�ļ�������");
				return;
			}
			
			BufferedReader br = new BufferedReader(new FileReader(listFileAll));
			String s = null;
			String projectPath = projectPathWeb;
			int i = 0,j = 0,k=0;
			String projectName = "NewATMVH_New";
			while ((s=br.readLine())!=null) {
				j++;    //����
				if (s.trim().equals("")||s.startsWith("#")){
					if (s.startsWith("#ATMVH_APP")||s.startsWith("#report")){
						projectPath=s.startsWith("#ATMVH_APP")?projectPathAPP:projectPathReport;
						projectName = s.startsWith("#ATMVH_APP")?"ATMVH_APP":"report";
					}
					continue;
				}
				k++;   //�ļ���
				File f = new File(projectPath+s);
				if ( !f.exists()||s.endsWith(" ")||s.endsWith(".")){
					System.out.println("��"+j+"��--"+projectName+"����--"+s+"---�ļ�������");
					i++;    //�����ļ���
				}
				//System.out.println(s);
				s="";
			}
			System.out.println("�Ա���ɣ���"+k+"���ļ����������ļ�"+i+"��"); 
			System.out.println("------------------------"); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ȡ�쳣");
		}
		
	}
}
