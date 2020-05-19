/**
 * 
 */
package h2;

/**
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-10-28 ����3:41:31
 * @Description
 * @version 1.0 Shawn create
 */
public class H2Tool {

	public static H2Manager h2Tools = createH2Tools();

	/**
	 * ����H2���ݿ�ʵ������
	 * 
	 * @author RenShuliang
	 * @return H2Manager
	 * @since 20120601
	 */
	private static H2Manager createH2Tools() {
		try {
			H2Manager h2 = H2Manager.getInstance();
			return h2;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return null;
		}
	}
}