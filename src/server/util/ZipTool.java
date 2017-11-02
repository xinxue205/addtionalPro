/**
 * 
 */
package server.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.apache.log4j.Logger;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 上午11:06:44
 * @Description
 * @version 1.0 Shawn create
 */
public class ZipTool {
	static final int BUFFER = 2048;
	  Logger log = Logger.getLogger("getCasReport");

	  public boolean gzipFile(String sourceFile, String destFile)
	    throws IOException
	  {
	    try
	    {
	      int num;
	      if (destFile.indexOf(".") < 0) {
	        destFile = destFile + ".gz";
	      }

	      FileOutputStream dest = new FileOutputStream(destFile);

	      GZIPOutputStream gzout = new GZIPOutputStream(dest);

	      FileInputStream fin = new FileInputStream(sourceFile);
	      byte[] buf = new byte[1024];

	      while ((num = fin.read(buf)) != -1) {
	        gzout.write(buf, 0, num);
	      }
	      fin.close();
	      gzout.close();
	      dest.close();
	    } catch (Exception ex) {
	      this.log.error(ex.getMessage(), ex);
	      new File(destFile).delete();
	      return false;
	    }
	    return true;
	  }

	  public static void zipFile(String[] files, String destFile)
	    throws IOException
	  {
	    try
	    {
	      if (destFile.indexOf(".") < 0)
	        destFile = destFile + ".zip";
	      BufferedInputStream origin = null;

	      FileOutputStream dest = new FileOutputStream(destFile);
	      ZipOutputStream out = new ZipOutputStream(
	        new BufferedOutputStream(dest));

	      byte[] data = new byte[2048];
	      for (int i = 0; i < files.length; ++i) {
	        int count;
	        System.out.println("Adding: " + files[i]);
	        FileInputStream fi = new FileInputStream(files[i]);
	        origin = new BufferedInputStream(fi, 2048);
	        ZipEntry entry = new ZipEntry(files[i].substring(
	          files[i].lastIndexOf(File.separator) + 1));
	        System.out.println("entry===" + entry.getName());
	        out.putNextEntry(entry);

	        while ((count = origin.read(data, 0, 2048)) != -1) {
	          out.write(data, 0, count);
	        }
	        origin.close();

	        fi.close();
	      }
	      out.close();

	      dest.close();
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }

	  public void zipFileAbsolute(String[] files, String destFile)
	    throws IOException
	  {
	    try
	    {
	      if (destFile.indexOf(".") < 0)
	        destFile = destFile + ".zip";
	      BufferedInputStream origin = null;

	      FileOutputStream dest = new FileOutputStream(destFile);

	      ZipOutputStream out = new ZipOutputStream(
	        new BufferedOutputStream(dest));

	      byte[] data = new byte[2048];

	      for (int i = 0; i < files.length; ++i) {
	        int count;
	        System.out.println("Adding: " + files[i]);
	        FileInputStream fi = new FileInputStream(files[i]);
	        origin = new BufferedInputStream(fi, 2048);
	        ZipEntry entry = new ZipEntry(files[i]);
	        out.putNextEntry(entry);

	        while ((count = origin.read(data, 0, 2048)) != -1) {
	          out.write(data, 0, count);
	        }
	        origin.close();

	        fi.close();
	      }
	      out.close();

	      dest.close();
	    }
	    catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }

	  public static void zipFile(String file, String destFile)
	    throws IOException
	  {
	    String[] files = new String[1];
	    files[0] = file;
	    zipFile(files, destFile);
	  }

	  private static String[] toString(Object[] s)
	  {
	    if (s == null)
	      return null;
	    String[] ss = new String[s.length];
	    for (int i = 0; i < s.length; ++i) {
	      ss[i] = ((String)s[i]);
	    }
	    return ss;
	  }

	  public String[] unzipFile(String zipFile) {
	    return unzipFile(zipFile, null);
	  }

	  public static String[] unzipFile(String zipFile, String destfilepath) {
	    ZipFile zipfile = null;
	    File destFile = null;
	    try {
	      ArrayList list = new ArrayList();
	      BufferedOutputStream dest = null;
	      BufferedInputStream is = null;

	      zipfile = new ZipFile(zipFile);
	      System.out.println("zipfile>>>>>>>>" + zipFile);
	      Enumeration e = zipfile.entries();
	      if (destfilepath != null) {
	        destfilepath = destfilepath + File.separator;
	        destFile = new File(destfilepath);
	        if (!(destFile.exists())) {
	          destFile.mkdirs();
	        }
	      }
	      while (e.hasMoreElements()) {
	        ZipEntry entry = (ZipEntry)e.nextElement();
	        System.out.println("Extracting: " + entry);
	        list.add(entry.getName());
	        if (entry.isDirectory()) {
	          System.out
	            .println("Dir: " + entry.getName() + " skipped..");
	        }
	        else
	        {
	          int count;
	          is = new BufferedInputStream(zipfile.getInputStream(entry));

	          byte[] data = new byte[2048];
	          FileOutputStream fos = null;

	          if (destfilepath != null)
	            fos = new FileOutputStream(
	              getRealFileName(destfilepath, 
	              entry.getName()));
	          else {
	            fos = new FileOutputStream(entry.getName());
	          }
	          dest = new BufferedOutputStream(fos, 2048);
	          while ((count = is.read(data, 0, 2048)) != -1) {
	            dest.write(data, 0, count);
	          }
	          dest.flush();
	          dest.close();
	          is.close(); }
	      }
	      zipfile.close();
	      return toString(list.toArray());
	    } catch (Exception e) {
	    }
	    return null;
	  }

	  private static File getRealFileName(String baseDir, String absFileName)
	  {
	    String[] dirs = PubTools.doSplit(absFileName, '/');

	    File ret = new File(baseDir);

	    if (dirs.length > 1) {
	      for (int i = 0; i < dirs.length - 1; ++i) {
	        ret = new File(ret, dirs[i]);
	      }
	    }
	    if (!(ret.exists())) {
	      ret.mkdirs();
	    }
	    ret = new File(ret, dirs[(dirs.length - 1)]);
	    return ret;
	  }
}
