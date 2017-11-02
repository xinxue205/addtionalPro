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
public class FileListComparer3 {
	public static void main(String[] args) {
		//��Ŀ����·��
		String listFileAll = "E:/WorkSpace_TEST/YC_EmpSys/src/file/fileList.txt";  //�嵥�ļ�·��
		try {
			//�嵥�ļ�·��
			File listFile = new File(listFileAll);
			if (!listFile.exists()) {
				System.out.println("�嵥�ļ�������");
				return;
			}
			
			BufferedReader br = new BufferedReader(new FileReader(listFileAll));
			String s = "";
			int j=1,i=0,k=0;
			while ((s=br.readLine())!=null) {
				
				if (s.trim().equals("")||s.startsWith("#")){
					continue;
				}
				
				k++;
				File f = new File(s);
				if ( !f.exists()){
					System.out.println("��"+j+"��----"+s+"---�ļ�������");
					i++;    //�����ļ���
				}
				j++;
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
