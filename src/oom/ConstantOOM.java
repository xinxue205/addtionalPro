package oom;

import java.util.ArrayList;
import java.util.List;

public class ConstantOOM {
	public static void main(String[] args) {
        //使用list保持对常量池引用，避免Full GC回收常量池
        List<String> list=new ArrayList<String>();
        int i=0;
        while(true)
            list.add(String.valueOf(i++).intern());

    }
}
