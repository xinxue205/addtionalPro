package enumt;



public enum TestEnum {
	TRANS	(1, "TRANS"),
	JOB		(2, "JOB");
	
	// ��Ա����
	private int index;

	// ��Ա����
	private String name;

    // ���췽��
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
