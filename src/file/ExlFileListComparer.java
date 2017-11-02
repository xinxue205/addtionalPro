package file;

import java.io.File;
import java.io.FileInputStream;
import jxl.Sheet;
import jxl.Workbook;
/**
 * �ļ��Ա���
 * <br>������ �Ƚ�FILELIST�ļ��嵥��ָ�����ļ��Ƿ���ȷ����
 * @author wxx
 * @param listFile--�嵥��ַ
 * @param projectPath--��Ŀ��ַ
 */
public class ExlFileListComparer {
	public static void main(String[] args) {
		//��Ŀ����·��
		String listFileAll = "H:/ftp/1filelist.xls";  //�嵥�ļ�·��
		//String listFileAll = "H:/WorkProjects/YC_EmpSys/src/file/1filelist.xls";  //�嵥�ļ�·��
		//String listFileAll = "H:/WorkProjects/YC_EmpSys/src/file/fileList_201403.txt";  //�嵥�ļ�·��
		String rootPath = "H:/WorkProjects/huangjun2.zh_ATMS_ATMV1.0_APP_Dev_wxx/vobs/ATMS_ATMV_APP/ATMS_ATMV_APP/";
		int exlNum = 0; //�ڼ������,��һ����0
		
		try {
			File listFile = new File(listFileAll);
			if (!listFile.exists()) {
				System.out.println("�嵥�ļ�������");
				return;
			}
			
			Workbook workbook  = Workbook.getWorkbook(new FileInputStream(listFileAll));
			Sheet sheet = workbook.getSheet(exlNum); // ��1��sheet
			
			int maxRowNo = sheet.getRows();
			if(maxRowNo<=3){
				System.out.println("�嵥�ļ��ĵ�"+(exlNum+1)+"���δ��д�ļ�");
				return;
			}
			
			String[] files = new String[maxRowNo-3];			
			for (int i = 3,j=0; i < maxRowNo; i++,j++) {
				files[j] = sheet.getCell(19, i).getContents().toString().trim();
			}

			int j=0;
			for (int i = 0; i < files.length; i++) {
				String filePath = files[i].trim();
				if(!"".equals(filePath)){
					String projectPath = "NewATMVH_New/WebRoot/";
					String tailPath = filePath.substring(26, filePath.length());
					
					//report����
					if(filePath.startsWith("/web/monitor/report.war/")){
						tailPath = filePath.substring(24, filePath.length());
						projectPath="report/WebRoot/";
					
					//APP����
					} else if (filePath.startsWith("/app/ATMVH_APP/classes/")){
						tailPath = filePath.substring(23, filePath.length());
						projectPath="ATMVH_APP/bin/";
					}
					
					String aimPath = rootPath+projectPath+tailPath;
					File f = new File(aimPath);
					if ( !f.exists()){
						System.out.println("��"+(i+4)+"�У�"+projectPath+"Ŀ¼  "+tailPath+"�ļ�������");
						j++;
					}
				} else {
					System.out.println("��"+(i+4)+"���ļ�·��Ϊ�գ�������");
					j++;
				}
			}
			
			System.out.println("------------------------"); 
			System.out.println("�Ա���ɣ���"+files.length+"���ļ����������ļ�"+j+"��"); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ȡ�쳣");
		}
		
	}
}
