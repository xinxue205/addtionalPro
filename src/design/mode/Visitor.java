package design.mode;

import java.util.ArrayList;
import java.util.List;

/**
 * ����һ���˵��ӿڣ��н��շ����ߵĹ���
 * 
 * @author hgx
 *
 */
interface Bill {
    void accept(AccountBookView viewer);
}

/**
 * ���ѵ���
 * 
 * @author hgx
 *
 */
class ConsumerBill implements Bill {

    private String item;
    private double amount;

    public ConsumerBill(String item, double amount) {
        this.item = item;
        this.amount = amount;
    }

    public void accept(AccountBookView viewer) {
        viewer.view(this);
    }

    public String getItem() {
        return item;
    }

    public double getAmount() {
        return amount;
    }

}

/**
 * ���뵥��
 * 
 * @author hgx
 *
 */
class IncomeBill implements Bill {

    private String item;
    private double amount;

    public IncomeBill(String item, double amount) {
        this.item = item;
        this.amount = amount;
    }

    public void accept(AccountBookView viewer) {
        viewer.view(this);
    }

    public String getItem() {
        return item;
    }

    public double getAmount() {
        return amount;
    }

}

/**
 * �����߽ӿ�
 * 
 * @author hgx
 *
 */
interface AccountBookView {
    // �鿴���ѵĵ���
    void view(ConsumerBill consumerBill);

    // �鿴���뵥��
    void view(IncomeBill incomeBill);
}

// �ϰ��ࣺ���������ϰ壬��Ҫ�鿴��֧����������
class Boss implements AccountBookView {

    private double totalConsumer;
    private double totalIncome;

    // �鿴���ѵĵ���
    public void view(ConsumerBill consumerBill) {
        totalConsumer = totalConsumer + consumerBill.getAmount();
    }

    // �鿴���뵥��
    public void view(IncomeBill incomeBill) {
        totalIncome = totalIncome + incomeBill.getAmount();
    }

    public void getTotalConsumer() {
        System.out.println("�ϰ�һ��������" + totalConsumer);
    }

    public void getTotalIncome() {
        System.out.println("�ϰ�һ��������" + totalIncome);
    }
}

/**
 * ����ࣺ�������ǻ�ƣ���Ҫ��¼ÿ�ʵ���
 * 
 * @author hgx
 *
 */

class CPA implements AccountBookView {

    int count = 0;

    // �鿴���ѵĵ���
    public void view(ConsumerBill consumerBill) {
        count++;
        if (consumerBill.getItem().equals("����")) {
            System.out.println("��" + count + "�����������ˣ�" + consumerBill.getAmount());
        }
    }
    // �鿴���뵥��

    public void view(IncomeBill incomeBill) {

        if (incomeBill.getItem().equals("����")) {
            System.out.println("��" + count + "�����������ˣ�" + incomeBill.getAmount());
        }
    }

}

/**
 * �˵��ࣺ��������˵�����Ϊÿһ���˵���ӷ�����
 * 
 * @author hgx
 *
 */
class AccountBook {

    private List<Bill> listBill = new ArrayList<Bill>();

    // ��ӵ���
    public void add(Bill bill) {
        listBill.add(bill);
    }

    // Ϊÿ���˵���ӷ�����
    public void show(AccountBookView viewer) {
        for (Bill b : listBill) {
            b.accept(viewer);
        }
    }
}

/*
 *������
 */
public class Visitor {

    public static void main(String[] args) {
        // �������Ѻ����뵥��
        Bill consumerBill = new ConsumerBill("����", 3000);
        Bill incomeBill = new IncomeBill("����", 5000);
        Bill consumerBill2 = new ConsumerBill("����", 4000);
        Bill incomeBill2 = new IncomeBill("����", 8000);
        // ��ӵ���
        AccountBook accountBook = new AccountBook();
        accountBook.add(consumerBill);
        accountBook.add(incomeBill);
        accountBook.add(consumerBill2);
        accountBook.add(incomeBill2);
        // ����������
        AccountBookView boss = new Boss();
        AccountBookView cpa = new CPA();

        // ���ܷ�����
        accountBook.show(boss);
        accountBook.show(cpa);
        // boss�鿴�������������
        ((Boss) boss).getTotalConsumer();
        ((Boss) boss).getTotalIncome();

    }

}

