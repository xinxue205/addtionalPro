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
 * @date 2014-2-11 上午9:06:34
 * @version 1.0 wxx create
 * @CopyRight (c) 2014 广州南天电脑系统有限公司
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
    // 图片默认存放路径    
    public final static String path = "H:/WorkProjects/YC_EmpSys/src/file/";
   
    public static void transfer2Img() {    
        // 加载PPT    
    	try{
        HSLFSlideShow _hslf = new HSLFSlideShow("H:/WorkProjects/YC_EmpSys/src/file/slideshow.ppt");    
        SlideShow _slideShow = new SlideShow(_hslf);    
            
        // 获取PPT文件中的图片数据    
        PictureData[] _pictures = _slideShow.getPictureData();    
            
        // 循环读取图片数据    
        for (int i = 0; i < _pictures.length; i++) {    
            StringBuilder fileName = new StringBuilder(path);    
            PictureData pic_data = _pictures[i];    
            fileName.append(i);    
            // 设置格式    
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
            // 输出文件    
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
