/**
 * 
 */
package file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Picture;
import org.apache.poi.hslf.usermodel.PictureData;
import org.apache.poi.hslf.usermodel.SlideShow;

/**
 *
 * @author wxx
 * @date 2014-2-11 ����9:06:34
 * @version 1.0 wxx create
 * @CopyRight (c) 2014 �����������ϵͳ���޹�˾
 */
public class PPT2IMG {
	public static void main(String[] args) {
		OutputPicture.transfer2Img();
	}

	public static void transfer2Img(String[] args) {
		SlideShow ppt = null;
		try {
			ppt = new SlideShow(new HSLFSlideShow("H:/WorkProjects/YC_EmpSys/src/file/slideshow.ppt"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//extract all pictures contained in the presentation
		PictureData[] pdata = ppt.getPictureData();
		for (int i = 0; i < pdata.length; i++){
		    PictureData pict = pdata[i];

		    // picture data
		    byte[] data = pict.getData();

		    int type = pict.getType();
		    String ext;
		    switch (type){
		      case Picture.JPEG: ext=".jpg"; break;
		      case Picture.PNG: ext=".png"; break;
		      case Picture.WMF: ext=".wmf"; break;
		      case Picture.EMF: ext=".emf"; break;
		      case Picture.PICT: ext=".pict"; break;
		      default: continue;
		    }
		    FileOutputStream out;
			try {
				out = new FileOutputStream("pict_"+i + ext);
				out.write(data);
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
class OutputPicture {    
    // ͼƬĬ�ϴ��·��    
    public final static String path = "H:/WorkProjects/YC_EmpSys/src/file/";
   
    public static void transfer2Img() {    
        // ����PPT    
    	try{
        HSLFSlideShow _hslf = new HSLFSlideShow("H:/WorkProjects/YC_EmpSys/src/file/slideshow.ppt");    
        SlideShow _slideShow = new SlideShow(_hslf);    
            
        // ��ȡPPT�ļ��е�ͼƬ����    
        PictureData[] _pictures = _slideShow.getPictureData();    
            
        // ѭ����ȡͼƬ����    
        for (int i = 0; i < _pictures.length; i++) {    
            StringBuilder fileName = new StringBuilder(path);    
            PictureData pic_data = _pictures[i];    
            fileName.append(i);    
            // ���ø�ʽ    
            switch (pic_data.getType()) {    
          case Picture.JPEG:    
                fileName.append(".jpg");    
                break;    
            case Picture.PNG:    
                fileName.append(".png");    
                break;    
            default:    
                fileName.append(".data");    
            }    
            // ����ļ�    
            FileOutputStream fileOut = new FileOutputStream(new File(fileName.toString()));    
            fileOut.write(pic_data.getData());    
            fileOut.close();    
        } 
    	} catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
    }    
}
