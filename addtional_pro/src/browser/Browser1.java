package browser;

public class Browser1 {

	public static void main(String[] args) {
        try {
            String url = "http://www.baidu.com";
            java.net.URI uri = java.net.URI.create(url);

            // ��ȡ��ǰϵͳ������չ
            java.awt.Desktop dp = java.awt.Desktop.getDesktop();

            // �ж�ϵͳ�����Ƿ�֧��Ҫִ�еĹ���
            if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                // File file = new File("D:\\aa.txt");
                // dp.edit(file);// �༭�ļ�
                dp.browse(uri);// ��ȡϵͳĬ�������������
                // dp.open(file);// ��Ĭ�Ϸ�ʽ���ļ�
                // dp.print(file);// �ô�ӡ����ӡ�ļ�
            }
        } catch (java.lang.NullPointerException e) {
            // ��ΪuriΪ��ʱ�׳��쳣
            e.printStackTrace();
        } catch (java.io.IOException e) {
            // ��Ϊ�޷���ȡϵͳĬ�������
            e.printStackTrace();
        }
    }
}
