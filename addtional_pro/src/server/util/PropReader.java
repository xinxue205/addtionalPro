/**
 * 
 */
package server.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-18 上午9:42:09
 * @Description 读取属性工具类
 * @version 1.0 Shawn create
 */
public class PropReader {
	private static Map<String,Object> mProperties = null;
	private static Log log = LogFactory.getLog(PropReader.class);

	/**
	 * 初始化配置文件
	 * 
	 * @param configfile
	 * @throws IOException
	 */
	public static Properties getStaticProperties(String configfile) {
		log.debug("initConfig(String configfile)");
		log.debug("configfile:" + configfile);
		configfile = configfile.trim();
		if (mProperties == null)
			mProperties = new HashMap<String, Object>();
		if (!mProperties.containsKey(configfile)) {
			Properties properties = new Properties();
			InputStream inputStream = null;
			try {
				inputStream = PropReader.class.getResourceAsStream(configfile);// class1.getResourceAsStream(s);
				if (inputStream == null) {
					log.debug("Config File [" + configfile + "] not found, makesure it's placed at right position!");
					return null;
				}
				properties.load(inputStream);
				mProperties.put(configfile, properties);
			} catch (IOException e) {
				log.error("IOException!", e);
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						log.error(e);
					}
				}
			}
		}
		return (Properties) mProperties.get(configfile);
	}

	/**
	 * 获取配置信息
	 * 
	 * @param configfile 要读取的文件路径
	 * @param key 配置的主键
	 * @return 配置的值
	 */
	public static String getProperty(String configfile, String key) {
		if (configfile==null||key == null) {
			return null;
		}
		return getStaticProperties(configfile).getProperty(key);
	}
 
	/**
	 * 获取配置信息（如果没有配置信息，则返回默认值）
	 * 
	 * @param configfile 要读取的文件路径
	 * @param key 配置的主键
	 * @param defaultValue 默认值
	 * @return 配置的值
	 */
	public static String getProperty(String configfile,String key, String defaultValue) {
		if (configfile==null||key == null) {
			return null;
		}
		return getStaticProperties(configfile).getProperty(key,defaultValue);
	}
}
