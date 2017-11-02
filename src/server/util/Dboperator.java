/**
 * 
 */
package server.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import server.server.socket.ServiceParameter;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 下午12:38:48
 * @Description
 * @version 1.0 Shawn create
 */
public class Dboperator {
	private static SqlMapClient sqlMapper;
	static {
		boolean isBreakFlag = false;
		int iCount = 0;
		while(!isBreakFlag){
			try {
				Reader reader = new BufferedReader(new FileReader(ServiceParameter.dbConfigFile));
				// Reader reader = Resources
				// .getResourceAsReader(ServiceParameter.dbConfigFile);
				sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
				reader.close();
				boolean isContinueFlag = false;
				int iCountNum = 0;
				while(!isContinueFlag){
				 try {
						Connection conn = sqlMapper.getDataSource().getConnection()  ;
						PreparedStatement stmt  = conn.prepareStatement("SET LOCK MODE TO WAIT 20") ;
						stmt.execute();
						isContinueFlag = true;
					} catch (SQLException e) {
						PubTools.log.error("设置锁等待时间发生异常:",e);
					}catch(Exception e){
						PubTools.log.error("设置锁等待时间发生异常:",e);
					}
					if(!isContinueFlag){
						if(iCountNum>5){
							break;
						}
						iCountNum++;
						try {
							Thread.sleep(30000);
						} catch (InterruptedException e) {
							PubTools.log.error("线程休眠失败",e);
						}
					}
				}
	        isBreakFlag = true;
			} catch (IOException e) {
				PubTools.log.error("数据库链接异常1",e);
				throw new RuntimeException("Something bad happened while building the SqlMapClient instance.", e);
			}catch(Exception e){
				PubTools.log.error("数据库链接异常2",e);
				throw new RuntimeException("Something bad happened while building the SqlMapClient instance.", e);
			}
			
			if(!isBreakFlag){
				if(iCount>5){
					break;
				}
				iCount++;
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					PubTools.log.error("线程休眠失败",e);
				}
			}
		}
	}

	public static SqlMapClient getSqlMapper() {
		return sqlMapper;
	}


}
