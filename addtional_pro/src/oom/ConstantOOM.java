package oom;

import java.util.ArrayList;
import java.util.List;

public class ConstantOOM {
	public static void main(String[] args) {
        //ʹ��list���ֶԳ��������ã�����Full GC���ճ�����
        List<String> list=new ArrayList<String>();
        int i=0;
        while(true)
            list.add(String.valueOf(i++).intern());

    }
}
