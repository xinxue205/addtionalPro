package GUI;


import javax.swing.JOptionPane;
 
/**
	 ConfirmDialog—确认对话框，用户点击“YES” or “No”<br>
	　InputDialog—输入提示框<br>
	　MessageDialog—信息提示框<br>
	　OptionDialog—组合其余三种框体
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
		int res = JOptionPane.showConfirmDialog(null, "是否继续操作", "是否继续", JOptionPane.YES_NO_CANCEL_OPTION);
		System.out.println("你的选择是："+res);
		if (res == JOptionPane.YES_OPTION) {//根据不同选择有JOptionPane.YES_OPTION、JOptionPane.NO_OPTION、JOptionPane.CANCEL_OPTION、JOptionPane.CLOSED_OPTION四种情况
			System.out.println("选择是后执行的代码"); // 点击“是”后执行这个代码块
		} else {
			System.out.println("选择否后执行的代码"); // 点击“否”后执行这个代码块
		}
	}
	
	/**
	 * inputDialog
	 * @param type  0-下拉选项，1-输入选项
	 */
	public static void inputDialog(int type) {
		if(type == 0 ) {
			Object[] possibleValues = { "First", "Second", "Third" }; //用户的选择项目
			Object selectedValue = JOptionPane.showInputDialog(null, "Choose one", "Input",JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
			System.out.println("选择："+selectedValue); // 点击“是”后执行这个代码块
		} else if (type == 1){
			String str= JOptionPane.showInputDialog("请输入");
			System.out.println("你输入的是："+str);
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
				JOptionPane.showMessageDialog(null,"用户名不存在或密码错误!\n请重新输入！", "系统信息", JOptionPane.INFORMATION_MESSAGE);
			}
		}.start();
		System.out.println(123);
		Thread.sleep(3000);
		System.out.println(123);
		Thread.sleep(3000);
		System.out.println(123);
//		JOptionPane.showMessageDialog(null,"用户名不存在或密码错误!\n请重新输入！", "系统信息", JOptionPane.WARNING_MESSAGE);
//		JOptionPane.showMessageDialog(null,"用户名不存在或密码错误!\n请重新输入！", "系统信息", JOptionPane.ERROR_MESSAGE);
//		JOptionPane.showMessageDialog(null,"用户名不存在或密码错误!\n请重新输入！", "系统信息", JOptionPane.QUESTION_MESSAGE);
	}
	
	//optionDialog
	public static void optionDialog() {
		Object[] options = {"a","aa","aaa","aaaa"};//用户在此可定义按钮数量
		int res = JOptionPane.showOptionDialog(null,"选择按钮，个数可设置","选项对话框标题",JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (res <0) System.out.println("你没选");
		System.out.println("你的选择是："+options[res]);
	}
	
	
}
