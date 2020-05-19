package com.ecc.liana.innermanage.action;


import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.configuration.ConfigurationException;

import com.ecc.emp.core.Context;
import com.ecc.emp.core.EMPException;
import com.ecc.emp.jdbc.JDBCAction;
import com.ecc.emp.web.multipart.implement.EMPMultipartFile;
import com.ecc.liana.base.LianaStandard;
import com.ecc.liana.exception.TranFailException;
import com.ecc.liana.format.LianaFormat;

/**
 * CheckUniqueAction.java<br>
 * EMP ���ײ�����չ<br>
 * Extends class EMPAction<br>
 * Created on 2010��04��15��16ʱ23��32��<br>
 * 
 * @author keqb<br>
 * 
 * @emp:name ����ͻ�����
 * @emp:states 0=����;-1=ʧ��;
 * @emp:document
 */
public class UploadCardAction extends JDBCAction {
	private String dataSourceName;

	/* ҵ���߼�������Ԫ��ִ����� */
	public String execute(Context context) throws EMPException {

		try {
			String imageType = (String) context.get("imageType");
			EMPMultipartFile imgMtf = (EMPMultipartFile) context
					.get("uploadFile");
			String appPath = "";
			if (imgMtf != null) {
				appPath = LianaStandard
						.getSelfDefineSettingsValue("appreciationPath");// ��ȡ�������ϴ���ļ���Ŀ¼
				File appFilePath = new File(appPath);
				if (!appFilePath.exists()) {
					appFilePath.mkdir();
				}

				String imgFileName = (String) imgMtf.getTempFileName();// �ϴ���������������ʱ�ļ���
				String fileName = null;
			
				if (imgFileName != null && imgFileName.length() > 0) {
					try {
						
						fileName = LianaStandard.getServerTime("HHmmssSSS")+(new Random()).nextInt( 1000 )+".jpg";
						
						File imgFileIn = new File(imgFileName);
						String qs = "";
						String type = imageType;
						String fp = "";
						File f;
						
						float imgLength = (float) imgFileIn.length() / 1024;
						if (imgLength > 100) {// ����100Kb�򲻸��ϴ�
							if (imgFileIn.exists())
								imgFileIn.delete();// ɾ����ʱ�ļ�
							return "-1";
						}
						
						if (imgFileIn.isFile()
								&& imgFileIn.getName().indexOf(".") != -1) {
							fp = appPath + "/" + "img";
							f = new File(fp);
							if (!f.exists()){
								f.mkdir();
							}

							// ����д���ļ�
							f = new File(fp + File.separator + fileName);
							OutputStream outStream = new FileOutputStream(f);

							InputStream in = new FileInputStream(imgFileIn);
							InputStream isImage = new FileInputStream(imgFileIn);
							if(isImage(isImage)){
								// in.skip(4);//����InputStream��ǰ��Ķ����ʶ
								isImage.close();
								byte[] buffer = new byte[1024];
								int length = 0;
								while ((length = in.read(buffer)) != -1) {
									outStream.write(buffer, 0, length);
								}
								f = null;
								in.close();
								outStream.flush();
								outStream.close();
								if (imgFileIn.exists())		imgFileIn.delete();//  ɾ����ʱ�ļ�
							
								//��ͼƬת��Ϊָ����С173*111
								String srcFileName = fp+"/"+fileName;
								BufferedImage bi = ImageIO.read(new File(srcFileName));
								if("1".equals(imageType)){
								    BufferedImage tag=new BufferedImage(173,111, BufferedImage.TYPE_INT_RGB);
								    tag.getGraphics().drawImage(bi, 0, 0, 173, 111, null);
								    ImageIO.write(tag, "jpg", new File(srcFileName));
								}else if("2".equals(imageType)){
									BufferedImage tag=new BufferedImage(187,187, BufferedImage.TYPE_INT_RGB);
									tag.getGraphics().drawImage(bi, 0, 0, 187, 187, null);
									ImageIO.write(tag, "jpg", new File(srcFileName));
								}
								
								// ��ʼ��FTP������
								SFTPUtil ftpUtil = null;
								try {
									 //Config.ftpUtil = new FTPUtil();
									ftpUtil = new SFTPUtil();
								} catch (ConfigurationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							
								if("1".equals(imageType)){
									//��Ƭ��Ϣ��creditCardIdӪ�����ģ�ctr_code��Ƭ���
									String creditCardId = (context.containsKey("creditCardId")) ? LianaFormat.nvl((String)context.getDataValue("creditCardId"),""):"";
									String ctr_code = (context.containsKey("ctr_code")) ? LianaFormat.nvl((String)context.getDataValue("ctr_code"),""):"";
									String saveFileName = creditCardId+"_"+ctr_code+".jpg";
									
									context.put("creditCardPath",saveFileName);
									
									qs = "innermanage/images/zxdy/creditCard";
									ftpUtil.upload(fp + File.separator + fileName, saveFileName, qs, "1");
									qs = "channelsLink/images/zxdy/creditCard";
									ftpUtil.upload(fp + File.separator + fileName, saveFileName, qs, "1");
								}else if("2".equals(imageType)){
									//sellerManNumӪ��Ա��ţ�ctr_codeӪ�����ģ�sellerManCenterIdֱ���͵�Ӫ��������
									String sellerManNum = (context.containsKey("sellerManNum")) ? LianaFormat.nvl((String)context.getDataValue("sellerManNum"),""):"";
									String ctr_code = (context.containsKey("ctr_code")) ? LianaFormat.nvl((String)context.getDataValue("ctr_code"),""):"";
									String sellerManCenterId = (context.containsKey("sellerManCenterId")) ? LianaFormat.nvl((String)context.getDataValue("sellerManCenterId"),""):"";
									String saveFileName = sellerManCenterId+"_"+ctr_code+"_"+sellerManNum+".jpg";
									
									context.put("sellerManPhoto",saveFileName);
									
									qs = "innermanage/images/zxdy/sellerManPhoto";
									ftpUtil.upload(fp + File.separator + fileName, saveFileName, qs, "1");
									qs = "channelsLink/images/zxdy/sellerManPhoto";
									ftpUtil.upload(fp + File.separator + fileName, saveFileName, qs, "1");
								}
							}else{
								isImage.close();
								if (imgFileIn.exists())		imgFileIn.delete();// ɾ����ʱ�ļ�
								return "-1";
							}
					 }
					} catch (Exception e) {
						throw new TranFailException("ENL10000",
								"upload File Failed��" + e);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new TranFailException(e);
		}
		return "0";
	}
	public static boolean isImage(InputStream is){        
        boolean flag = false;
        ImageInputStream iis;
        try {
        	iis = ImageIO.createImageInputStream(is);
            if(iis==null){
            	return false;
            }
            java.util.Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            if (!iter.hasNext()) {
                flag=false;
            }else{
                ImageReader reader = (ImageReader) iter.next();
                String formatname=reader.getFormatName();
                if("jpg".equalsIgnoreCase(formatname)||"jpeg".equalsIgnoreCase(formatname)||"gif".equalsIgnoreCase(formatname)){
                    flag=true;
                }
            }
        }
        catch (Exception e) {
            flag=false;
        }
        return flag;   
    }   
	/**
	 * @emp:name ����Դ����
	 * @emp:mustSet true
	 * @emp:valueList
	 * @emp:editorClass
	 */
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
}
