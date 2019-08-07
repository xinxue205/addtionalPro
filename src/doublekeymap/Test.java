package doublekeymap;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table.Cell;

public class Test {
	public static void main(String[] args) {
		HashBasedTable<Object, Object, Object> tables=HashBasedTable.create();
		//��������
		tables.put("a", "javase", 80);
		tables.put("b", "javase", 90);
		tables.put("a", "oracle", 100);
		tables.put("c", "oracle", 95);
		//	���е�������
		Set<Cell<Object, Object, Object>> cells=tables.cellSet();
		for (Cell<Object, Object, Object> cell : cells) {
			System.out.println(cell.getRowKey()+"-->"+cell.getColumnKey()+"-->"+cell.getValue());
		}
		/*
		 * ���һ������
		 *	a-->javase-->80
		 *	a-->oracle-->100
		 *	b-->javase-->90
		 *	c-->oracle-->95
		 */
		
		System.out.println("=================");
		System.out.print("ѧ��\t");
		//���пγ�
		Set<Object> cours=tables.columnKeySet();
		for (Object string : cours) {
			System.out.print(string+"\t"); //������пγ�
		}
		System.out.println();
		//���е�ѧ��
		Set<Object> stus=tables.rowKeySet();
		for (Object stu : stus) {
			System.out.print(stu+"\t");	//���ѧ������ 
			Map<Object, Object> scores=tables.row(stu); //<ѧ��������>
			for (Object c : cours) {//�������еĿγ�
				System.out.print(scores.get(c)+"\t");//������пγ̵ĳɼ�
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		HashBasedTable<Object, Object, Object> tables=HashBasedTable.create();
		//��������
		tables.put("a", "javase", 80);
		tables.put("b", "javase", 90);
		tables.put("a", "oracle", 100);
		tables.put("c", "oracle", 95);
		//	���е�������
		Set<Cell<Object, Object, Object>> cells=tables.cellSet();
		for (Cell<Object, Object, Object> cell : cells) {
			System.out.println(cell.getRowKey()+"-->"+cell.getColumnKey()+"-->"+cell.getValue());
		}
		/*
		 * ���һ������
		 *	a-->javase-->80
		 *	a-->oracle-->100
		 *	b-->javase-->90
		 *	c-->oracle-->95
		 */
		
		System.out.println("=================");
		System.out.print("ѧ��\t");
		//���пγ�
		Set<Object> cours=tables.columnKeySet();
		for (Object string : cours) {
			System.out.print(string+"\t"); //������пγ�
		}
		System.out.println();
		//���е�ѧ��
		Set<Object> stus=tables.rowKeySet();
		for (Object stu : stus) {
			System.out.print(stu+"\t");	//���ѧ������ 
			Map<Object, Object> scores=tables.row(stu); //<ѧ��������>
			for (Object c : cours) {//�������еĿγ�
				System.out.print(scores.get(c)+"\t");//������пγ̵ĳɼ�
			}
			System.out.println();
		}
	}
}
