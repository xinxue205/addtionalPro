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
 * EMP 交易步骤扩展<br>
 * Extends class EMPAction<br>
 * Created on 2010年04月15日16时23分32秒<br>
 * 
 * @author keqb<br>
 * 
 * @emp:name 导入客户名单
 * @emp:states 0=正常;-1=失败;
 * @emp:document
 */
public class UploadCardAction extends JDBCAction {
	private String dataSourceName;

	/* 业务逻辑操作单元的执行入口 */
	public String execute(Context context) throws EMPException {

		try {
			String imageType = (String) context.get("imageType");
			EMPMultipartFile imgMtf = (EMPMultipartFile) context
					.get("uploadFile");
			String appPath = "";
			if (imgMtf != null) {
				appPath = LianaStandard
						.getSelfDefineSettingsValue("appreciationPath");// 读取服务器上存放文件的目录
				File appFilePath = new File(appPath);
				if (!appFilePath.exists()) {
					appFilePath.mkdir();
				}

				String imgFileName = (String) imgMtf.getTempFileName();// 上传到服务器本地临时文件中
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
						if (imgLength > 100) {// 大于100Kb则不给上传
							if (imgFileIn.exists())
								imgFileIn.delete();// 删掉临时文件
							return "-1";
						}
						
						if (imgFileIn.isFile()
								&& imgFileIn.getName().indexOf(".") != -1) {
							fp = appPath + "/" + "img";
							f = new File(fp);
							if (!f.exists()){
								f.mkdir();
							}

							// 接着写入文件
							f = new File(fp + File.separator + fileName);
							OutputStream outStream = new FileOutputStream(f);

							InputStream in = new FileInputStream(imgFileIn);
							InputStream isImage = new FileInputStream(imgFileIn);
							if(isImage(isImage)){
								// in.skip(4);//调过InputStream流前面的多余标识
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
								if (imgFileIn.exists())		imgFileIn.delete();//  删掉临时文件
							
								//将图片转换为指定大小173*111
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
								
								// 初始化FTP工具类
								SFTPUtil ftpUtil = null;
								try {
									 //Config.ftpUtil = new FTPUtil();
									ftpUtil = new SFTPUtil();
								} catch (ConfigurationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							
								if("1".equals(imageType)){
									//卡片信息：creditCardId营销中心，ctr_code卡片序号
									String creditCardId = (context.containsKey("creditCardId")) ? LianaFormat.nvl((String)context.getDataValue("creditCardId"),""):"";
									String ctr_code = (context.containsKey("ctr_code")) ? LianaFormat.nvl((String)context.getDataValue("ctr_code"),""):"";
									String saveFileName = creditCardId+"_"+ctr_code+".jpg";
									
									context.put("creditCardPath",saveFileName);
									
									qs = "innermanage/images/zxdy/creditCard";
									ftpUtil.upload(fp + File.separator + fileName, saveFileName, qs, "1");
									qs = "channelsLink/images/zxdy/creditCard";
									ftpUtil.upload(fp + File.separator + fileName, saveFileName, qs, "1");
								}else if("2".equals(imageType)){
									//sellerManNum营销员编号，ctr_code营销中心，sellerManCenterId直销和电营渠道区别
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
								if (imgFileIn.exists())		imgFileIn.delete();// 删掉临时文件
								return "-1";
							}
					 }
					} catch (Exception e) {
						throw new TranFailException("ENL10000",
								"upload File Failed！" + e);
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
	 * @emp:name 数据源名称
	 * @emp:mustSet true
	 * @emp:valueList
	 * @emp:editorClass
	 */
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
}
