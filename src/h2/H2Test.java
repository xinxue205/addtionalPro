/**
 * 
 */
package h2;



/**
 * @CopyRight (c) 2014 XXXX有限公司  All rights reserved.
 * @author Shawn.wu
 * @date 2014-10-28 下午3:42:25
 * @Description
 * @version 1.0 Shawn create
 */
public class H2Test {

	
	public static void main(String[] args) throws Exception {
		String sDropIndex = "drop table if exists tbl_atmphpos_mon";
		H2Tool.h2Tools.createTableOrIndex(sDropIndex);
		String sCreateTable = "create table tbl_atmphpos_mon(test1 integer)";
		H2Tool.h2Tools.createTableOrIndex(sCreateTable);
		String sInsertSql = "insert into tbl_atmphpos_mon values (1)";
		H2Tool.h2Tools.insertDB(sInsertSql);
		String sQuerySql = "select count(*) from tbl_atmphpos_mon";
		int num = H2Tool.h2Tools.hasData(sQuerySql);
		System.out.println("表中数据个数："+num);
	}
}
