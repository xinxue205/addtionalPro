package GUI;


import javax.swing.JOptionPane;
 
/**
	 ConfirmDialog��ȷ�϶Ի����û������YES�� or ��No��<br>
	��InputDialog��������ʾ��<br>
	��MessageDialog����Ϣ��ʾ��<br>
	��OptionDialog������������ֿ���
 */
public class Dialog {
	
	public static void main(String[] args) throws Exception {
		//confirmDialog();
		//inputDialog(1);
		messageDialog();
		//optionDialog();
	}
	
	//ConfirmDialog
	public static void confirmDialog() {
		int res = JOptionPane.showConfirmDialog(null, "�Ƿ��������", "�Ƿ����", JOptionPane.YES_NO_CANCEL_OPTION);
		System.out.println("���ѡ���ǣ�"+res);
		if (res == JOptionPane.YES_OPTION) {//���ݲ�ͬѡ����JOptionPane.YES_OPTION��JOptionPane.NO_OPTION��JOptionPane.CANCEL_OPTION��JOptionPane.CLOSED_OPTION�������
			System.out.println("ѡ���Ǻ�ִ�еĴ���"); // ������ǡ���ִ����������
		} else {
			System.out.println("ѡ����ִ�еĴ���"); // ������񡱺�ִ����������
		}
	}
	
	/**
	 * inputDialog
	 * @param type  0-����ѡ�1-����ѡ��
	 */
	public static void inputDialog(int type) {
		if(type == 0 ) {
			Object[] possibleValues = { "First", "Second", "Third" }; //�û���ѡ����Ŀ
			Object selectedValue = JOptionPane.showInputDialog(null, "Choose one", "Input",JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
			System.out.println("ѡ��"+selectedValue); // ������ǡ���ִ����������
		} else if (type == 1){
			String str= JOptionPane.showInputDialog("������");
			System.out.println("��������ǣ�"+str);
		}
	}
	
	/**
	 * messageDialog
	 * @throws Exception 
	 */
	public static void messageDialog() throws Exception {
		new Thread() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(null,"�û��������ڻ��������!\n���������룡", "ϵͳ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
			}
		}.start();
		System.out.println(123);
		Thread.sleep(3000);
		System.out.println(123);
		Thread.sleep(3000);
		System.out.println(123);
//		JOptionPane.showMessageDialog(null,"�û��������ڻ��������!\n���������룡", "ϵͳ��Ϣ", JOptionPane.WARNING_MESSAGE);
//		JOptionPane.showMessageDialog(null,"�û��������ڻ��������!\n���������룡", "ϵͳ��Ϣ", JOptionPane.ERROR_MESSAGE);
//		JOptionPane.showMessageDialog(null,"�û��������ڻ��������!\n���������룡", "ϵͳ��Ϣ", JOptionPane.QUESTION_MESSAGE);
	}
	
	//optionDialog
	public static void optionDialog() {
		Object[] options = {"a","aa","aaa","aaaa"};//�û��ڴ˿ɶ��尴ť����
		int res = JOptionPane.showOptionDialog(null,"ѡ��ť������������","ѡ��Ի������",JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (res <0) System.out.println("��ûѡ");
		System.out.println("���ѡ���ǣ�"+options[res]);
	}
	
	
}
