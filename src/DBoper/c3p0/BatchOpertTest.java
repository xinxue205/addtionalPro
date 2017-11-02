package DBoper.c3p0;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import DBoper.c3p0.DB;

import jxl.Sheet;
import jxl.Workbook;

public class BatchOpertTest {
	private static Log log = LogFactory.getLog(BatchOpertTest.class);
	private Map outMsgMap = new HashMap();
	private Map mContacatLevel = new HashMap();
	private Map mFaultType = new HashMap();

	public static void main(String args[]){
//		long startNo = 526159;
//		int totalCount = 500000;
//		int batchNo = 50;
		long startNo = 990000;
		int totalCount = 100000;
		int batchNo = 4;
		int eachBatch = totalCount/batchNo;
		
		for (int i = 0; i < batchNo; i++) {
			StringBuffer sql = new StringBuffer("insert into test_from01 VALUES ");
			for (int j = 0; j < eachBatch; j++) {
				startNo++;
				String data = ",("+ startNo +", '1', '11', '111', '111')";
				if(j==0){
					data = "("+ startNo +", '1', '11', '111', '111')";
				}
				sql.append(data);
			}
			new BatchOper(sql.toString()).start();
		}
	}
}

class BatchOper extends Thread {
	String sqls;
	public BatchOper(String sqls) {
		this.sqls = sqls;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		DB.insertDB(sqls);
	}
}