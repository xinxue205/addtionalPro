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
 * @CopyRight (c) 2014 XXXX���޹�˾  All rights reserved.
 * @author Shawn.wu
 * @date 2014-7-23 ����12:38:48
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
						PubTools.log.error("�������ȴ�ʱ�䷢���쳣:",e);
					}catch(Exception e){
						PubTools.log.error("�������ȴ�ʱ�䷢���쳣:",e);
					}
					if(!isContinueFlag){
						if(iCountNum>5){
							break;
						}
						iCountNum++;
						try {
							Thread.sleep(30000);
						} catch (InterruptedException e) {
							PubTools.log.error("�߳�����ʧ��",e);
						}
					}
				}
	        isBreakFlag = true;
			} catch (IOException e) {
				PubTools.log.error("���ݿ������쳣1",e);
				throw new RuntimeException("Something bad happened while building the SqlMapClient instance.", e);
			}catch(Exception e){
				PubTools.log.error("���ݿ������쳣2",e);
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
					PubTools.log.error("�߳�����ʧ��",e);
				}
			}
		}
	}

	public static SqlMapClient getSqlMapper() {
		return sqlMapper;
	}


}
