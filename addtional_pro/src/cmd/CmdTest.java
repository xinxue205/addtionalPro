/**
 * 
 */
package cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *
 * @Author&Copyright xxxx��˾
 * @version ����1:22:52 Administrator 
 */
public class CmdTest {
	/*ִ�з�ʽ
	  0 cmd /c dir ��ִ����dir�����ر�����ڡ�
	  1 cmd /k dir ��ִ����dir����󲻹ر�����ڡ�
	  2 cmd /c start dir ���һ���´��ں�ִ��dirָ�ԭ���ڻ�رա�
	  3 cmd /k start dir ���һ���´��ں�ִ��dirָ�ԭ���ڲ���رա�
	  4 cmd /?�鿴������Ϣ*/
	static String[] methodArray = {"cmd /c ","cmd /k ","cmd /c start ","cmd /c start ","cmd /?"};
	
	public static void main(String[] args) {
	    System.out.println(excuteCmd(0, "dir"));
	    System.out.println(excute("TASKLIST"));
	}
	
	static String excuteCmd(int method, String command){
		String wholeCommond = methodArray[method]+command;
		try {
			Process process = Runtime.getRuntime().exec(wholeCommond);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    	String s= "";
	    	StringBuffer result = new StringBuffer();
	    	while ((s=br.readLine())!=null) { 
	    		result.append(s).append("\r\n");  
	    	}   
	    	return result.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ִ�г���";
		}
	}
	
	static String excute(String command){
		try {
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    	String s= "";
	    	StringBuffer result = new StringBuffer();
	    	while ((s=br.readLine())!=null) { 
	    		result.append(s).append("\r\n");  
	    	}   
	    	return result.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ִ�г���";
		}
	}
	/*��CMD�����
ASSOC          ��ʾ���޸��ļ���չ��������
ATTRIB         ��ʾ������ļ����ԡ�
BREAK          ���û������չʽ CTRL+C ��顣
BCDEDIT        �����������ݿ��е������Կ�������
CACLS          ��ʾ���޸��ļ��ķ��ʿ����б�(AC
CALL           ����һ�����������������һ����
CD             ��ʾ��ǰĿ¼�����ƻ�����ġ�
CHCP           ��ʾ�����û����ҳ����
CHDIR          ��ʾ��ǰĿ¼�����ƻ�����ġ�
CHKDSK         �����̲���ʾ״̬���档
CHKNTFS        ��ʾ���޸�����ʱ����̼�顣
CLS            �����Ļ��
CMD            ����һ�� Windows ������ͳ���
COLOR          ����Ĭ�Ͽ���̨ǰ���ͱ�����ɫ��
COMP           �Ƚ������������ļ������ݡ�
COMPACT        ��ʾ����� NTFS �������ļ���ѹ��
CONVERT        �� FAT ��ת���� NTFS��������ת��s��ǰ��������
COPY           ������һ���ļ����Ƶ���һ��λ�á�
DATE           ��ʾ���������ڡ�
DEL            ɾ������һ���ļ���
DIR            ��ʾһ��Ŀ¼�е��ļ�����Ŀ¼��
DISKCOMP       �Ƚ��������̵����ݡ�
DISKCOPY       ��һ�����̵����ݸ��Ƶ���һ������
DISKPART       ��ʾ�����ô��̷������ԡ�
DOSKEY         �༭�����С����� Windows ���
DRIVERQUERY    ��ʾ��ǰ�豸��������״̬�����ԡ�
ECHO           ��ʾ��Ϣ����������Դ򿪻����
ENDLOCAL       �������ļ��л������ĵı��ػ���
ERASE          ɾ��һ�������ļ���
EXIT           �˳� CMD.EXE ����(������ͳ���)
FC             �Ƚ������ļ��������ļ�������ʾ��
FIND           ��һ�������ļ�������һ���ı���
FINDSTR        �ڶ���ļ��������ַ�����
FOR            Ϊһ���ļ��е�ÿ���ļ�����һ��ָ
FORMAT         ��ʽ�����̣��Ա�� Windows ʹ��
FSUTIL         ��ʾ�������ļ�ϵͳ�����ԡ�
FTYPE          ��ʾ���޸������ļ���չ����������
GOTO           �� Windows ������ͳ���ָ��������ĳ������ǩ���С�
GPRESULT       ��ʾ�������û����������Ϣ��
GRAFTABL       ���� Windows ��ͼ��ģʽ��ʾ��չ
HELP           �ṩ Windows ����İ�����Ϣ��
ICACLS         ��ʾ���޸ġ����ݻ�ԭ�ļ���Ŀ¼�� ACL��
IF             ��������������ִ���������Ĵ�����
LABEL          ���������Ļ�ɾ�����̵ľ��ꡣ
MD             ����һ��Ŀ¼��
MKDIR          ����һ��Ŀ¼��
MKLINK         �����������Ӻ�Ӳ����
MODE           ����ϵͳ�豸��
MORE           ������ʾ�����
MOVE           ��һ�������ļ���һ��Ŀ¼�ƶ���
OPENFILES      ��ʾԶ���û�Ϊ���ļ��������򿪵�
PATH           Ϊ��ִ���ļ���ʾ����������·����
PAUSE          ֹͣ�������ļ��Ĵ�������ʾ��Ϣ��
POPD           ��ԭ�� PUSHD ����ĵ�ǰĿ¼��һ
PRINT          ��ӡһ���ı��ļ���
PROMPT         �ı� Windows ������ʾ��
PUSHD          ���浱ǰĿ¼��Ȼ�������и��ġ�
RD             ɾ��Ŀ¼��
RECOVER        ���𻵵Ĵ����лָ��ɶ�ȡ����Ϣ��
REM            ��¼�������ļ��� CONFIG.SYS �е�
REN            ���������ļ���
RENAME         ���������ļ���
REPLACE        �滻�ļ���
RMDIR          ɾ��Ŀ¼��
ROBOCOPY       �����ļ���Ŀ¼���ĸ߼�ʵ�ó���
SET            ��ʾ�����û�ɾ�� Windows ������
SETLOCAL       ��ʼ�����ļ��ı价���ı��ػ���
SC             ��ʾ�����÷���(��̨����)��
SCHTASKS       ��������ͳ�����һ��������ϰ���
SHIFT          �����������ļ��п��滻������λ��
SHUTDOWN       �û����ڱ��ػ�Զ����ȷ�رա�
SORT           ����������
START          �򿪵����Ӵ�����ָ����������
SUBST          ������������·��������
SYSTEMINFO     ��ʾ�����ľ�������Ժ����á�
TASKLIST       ��ʾ������������е�ǰ���е�����
TASKKILL       ��ֹ�������еĽ��̻�Ӧ�ó���
TIME           ��ʾ������ϵͳʱ�䡣
TITLE          ���� CMD.EXE �Ự�Ĵ��ڱ��⡣
TREE           ��ͼ����ʾ��������·����Ŀ¼�ṹ
TYPE           ��ʾ�ı��ļ������ݡ�
VER            ��ʾ Windows �İ汾��
VERIFY         ���� Windows ��֤�ļ��Ƿ���ȷд
VOL            ��ʾ���̾�������кš�
XCOPY          �����ļ���Ŀ¼����
WMIC           �ڽ��������������ʾ WMI ��Ϣ��
*/
	
}