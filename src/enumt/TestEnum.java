package enumt;



public enum TestEnum {
	TRANS	(1, "TRANS"),
	JOB		(2, "JOB");
	
	// 成员变量
	private int index;

	// 成员变量
	private String name;

    // 构造方法
	private TestEnum(int index, String name) {
		this.index = index;
		this.name = name;
    }
	
    public int getIndex() {
        return index;
    }

    public String getName() {
    	return name;
    }

}
