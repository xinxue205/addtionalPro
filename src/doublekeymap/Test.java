package doublekeymap;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table.Cell;

public class Test {
	public static void main(String[] args) {
		HashBasedTable<Object, Object, Object> tables=HashBasedTable.create();
		//测试数据
		tables.put("a", "javase", 80);
		tables.put("b", "javase", 90);
		tables.put("a", "oracle", 100);
		tables.put("c", "oracle", 95);
		//	所有的行数据
		Set<Cell<Object, Object, Object>> cells=tables.cellSet();
		for (Cell<Object, Object, Object> cell : cells) {
			System.out.println(cell.getRowKey()+"-->"+cell.getColumnKey()+"-->"+cell.getValue());
		}
		/*
		 * 输出一下内容
		 *	a-->javase-->80
		 *	a-->oracle-->100
		 *	b-->javase-->90
		 *	c-->oracle-->95
		 */
		
		System.out.println("=================");
		System.out.print("学生\t");
		//所有课程
		Set<Object> cours=tables.columnKeySet();
		for (Object string : cours) {
			System.out.print(string+"\t"); //输出所有课程
		}
		System.out.println();
		//所有的学生
		Set<Object> stus=tables.rowKeySet();
		for (Object stu : stus) {
			System.out.print(stu+"\t");	//输出学生名字 
			Map<Object, Object> scores=tables.row(stu); //<学生，分数>
			for (Object c : cours) {//遍历所有的课程
				System.out.print(scores.get(c)+"\t");//输出所有课程的成绩
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		HashBasedTable<Object, Object, Object> tables=HashBasedTable.create();
		//测试数据
		tables.put("a", "javase", 80);
		tables.put("b", "javase", 90);
		tables.put("a", "oracle", 100);
		tables.put("c", "oracle", 95);
		//	所有的行数据
		Set<Cell<Object, Object, Object>> cells=tables.cellSet();
		for (Cell<Object, Object, Object> cell : cells) {
			System.out.println(cell.getRowKey()+"-->"+cell.getColumnKey()+"-->"+cell.getValue());
		}
		/*
		 * 输出一下内容
		 *	a-->javase-->80
		 *	a-->oracle-->100
		 *	b-->javase-->90
		 *	c-->oracle-->95
		 */
		
		System.out.println("=================");
		System.out.print("学生\t");
		//所有课程
		Set<Object> cours=tables.columnKeySet();
		for (Object string : cours) {
			System.out.print(string+"\t"); //输出所有课程
		}
		System.out.println();
		//所有的学生
		Set<Object> stus=tables.rowKeySet();
		for (Object stu : stus) {
			System.out.print(stu+"\t");	//输出学生名字 
			Map<Object, Object> scores=tables.row(stu); //<学生，分数>
			for (Object c : cours) {//遍历所有的课程
				System.out.print(scores.get(c)+"\t");//输出所有课程的成绩
			}
			System.out.println();
		}
	}
}
