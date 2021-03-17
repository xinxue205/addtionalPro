package browser;

public class Browser1 {

	public static void main(String[] args) {
        try {
            String url = "http://www.baidu.com";
            java.net.URI uri = java.net.URI.create(url);

            // 获取当前系统桌面扩展
            java.awt.Desktop dp = java.awt.Desktop.getDesktop();

            // 判断系统桌面是否支持要执行的功能
            if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                // File file = new File("D:\\aa.txt");
                // dp.edit(file);// 编辑文件
                dp.browse(uri);// 获取系统默认浏览器打开链接
                // dp.open(file);// 用默认方式打开文件
                // dp.print(file);// 用打印机打印文件
            }
        } catch (java.lang.NullPointerException e) {
            // 此为uri为空时抛出异常
            e.printStackTrace();
        } catch (java.io.IOException e) {
            // 此为无法获取系统默认浏览器
            e.printStackTrace();
        }
    }
}
