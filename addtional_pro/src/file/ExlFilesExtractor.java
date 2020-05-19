package file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
/**
 * �ļ���ȡ��
 * <br>��������ȡ�ļ�����Ŀ¼��ִ��ǰ��ɾ��H:/extraPath/��Ŀ¼�������ļ����ļ��У�
 * @author wxx
 * @param listFile--�嵥��ַ
 * @param projectPath--��Ŀ��ַ
 */
public class ExlFilesExtractor {
	public static void main(String[] args) {
		//��Ŀ����·��
		String listFilePath = "H:/WorkProjects/YC_EmpSys/src/file/1fileList.xls";  //�嵥�ļ�·��
		String projectRootPath="H:/WorkProjects/huangjun2.zh_ATMS_ATMV1.0_APP_Dev_wxx/vobs/ATMS_ATMV_APP/ATMS_ATMV_APP/"; //��Ŀ·��
		String extractRoot = "H:/extraPath/";
		int exlNum = 1; //�ڼ������
		boolean createPathFlag = false;
		
		File listFile = new File(listFilePath);
		if (!listFile.exists()) {
			System.out.println("�嵥�ļ�������");
			return;
		}
		
		List<String> filesList = getFilesPathList(listFilePath,exlNum);
		
		if(filesList.size()==0){
			System.out.println("�嵥��δ��д��Ч�ļ�");
			return;
		}
		
		int i = 0;
		for (int j = 0; j < filesList.size(); j++) {
			String filePath = (String) filesList.get(j);
			String filePathTail = filePath.substring(26, filePath.length());
			String middlePath = "NewATMVH_New/WebRoot/";
			
			//report����
			if(filePath.startsWith("/web/monitor/report.war/")){
				filePathTail = filePath.substring(24, filePath.length());
				middlePath = "report/WebRoot/";
			
			//app����
			} else if (filePath.startsWith("/app/ATMVH_APP/classes/")){
				filePathTail = filePath.substring(23, filePath.length());
				middlePath = "ATMVH_APP/bin/";
			}
			
			String oldFilePath = projectRootPath+middlePath+filePathTail;
			File oldFile = new File(oldFilePath);
			if (!oldFile.exists()) {
				System.out.println(oldFilePath+"---�ļ�������");
				i++;
				continue;
			}
			
			String fileName = filePathTail.substring(filePathTail.lastIndexOf("/")+1, filePathTail.length());
			String extractFilePath = createPathFlag ? extractRoot+middlePath+filePathTail : extractRoot+middlePath+fileName;
			File extractFile = new File(extractFilePath);
			
			System.out.println("��ʼ��ȡ�ļ���"+middlePath+filePathTail);
			try {
				if (!extractFile.exists()) {
					File newFilePath = new File(extractFilePath.substring(0,extractFilePath.lastIndexOf("/")));
					newFilePath.mkdirs();
					extractFile.createNewFile();
				}
				
				InputStream is = new BufferedInputStream(new FileInputStream(oldFile));
				OutputStream os = new BufferedOutputStream(new FileOutputStream(extractFile));
				byte[] buffer = new byte[2048]; 
				int b = 0;
				while ( (b = is.read(buffer)) != -1) {
					os.write(buffer,0, b);
				}
				
				is.close();
				os.flush();
				os.close();
			} catch (Exception e1) {
				System.out.println(oldFilePath+"---�ļ���ȡ�쳣");
				i++;
				e1.printStackTrace();
				// TODO: handle exception
			} 
		}
		System.out.println("----------------------------"); 
		System.out.println("��ȡ��ɣ���"+filesList.size()+"���ļ����������ļ�"+i+"��"); 
	}
	
	static List<String> getFilesPathList(String listFilePath,int exlNum){
		List<String> list = new ArrayList<String>();
		try {
			Workbook workbook  = Workbook.getWorkbook(new FileInputStream(listFilePath));
			Sheet sheet = workbook.getSheet(exlNum);
			
			int maxRowNo = sheet.getRows();
			if(maxRowNo<=3){
				System.out.println("�嵥�ļ��ĵ�"+(exlNum+1)+"���δ��д�ļ�");
				return null;
			}
			
			for (int i = 3; i < maxRowNo; i++) {
				String file = sheet.getCell(18, i).getContents().toString().trim();
				if(!"".equals(file)){
					list.add(file);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
