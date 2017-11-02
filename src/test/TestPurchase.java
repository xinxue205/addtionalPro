package test;

public class TestPurchase {
	public static void main(String[] args) {
		PurchaseRequest pr = new PurchaseRequest();
		pr.setName("ENG1");
		pr.setSum(623000);
		Sender sen = new Sender();
		sen.send(pr);
	}
}

class PurchaseRequest{
	private String name;
	private int sum;
	private boolean judgeResult;
	private String judgerName;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setJudgeResult(boolean judgeResult) {
		this.judgeResult = judgeResult;
	}
	public boolean getJudgeResult() {
		return judgeResult;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public int getSum() {
		return sum;
	}
	public void setJudgerName(String judgerName) {
		this.judgerName = judgerName;
	}
	public String getJudgerName() {
		return judgerName;
	}
}

class Sender{
	void send(PurchaseRequest pr){
		int sum = pr.getSum();
		if (sum>=500000){
			Meeting meet = new Meeting();
			meet.judge(pr);
		} else if (sum>=100000){
			Presider pre = new Presider();
			pre.judge(pr);
		} else {
			VicPresider vp = new VicPresider();
			vp.judge(pr);
		}
			
	}
}

class Judger{
	void judge(PurchaseRequest pr){};
}

class Meeting extends Judger{
	void judge(PurchaseRequest pr){
		System.out.println("Meeting judged this purchase: "+ pr.getName());
		pr.setJudgeResult(true);
	}
}

class Presider extends Judger{
	void judge(PurchaseRequest pr){
		System.out.println("Presider judged this purchase: "+ pr.getName());
		pr.setJudgeResult(true);
	}
}

class VicPresider extends Judger{
	void judge(PurchaseRequest pr){
		System.out.println("VicPresider judged this purchase: "+ pr.getName());
		pr.setJudgeResult(true);
	}
}