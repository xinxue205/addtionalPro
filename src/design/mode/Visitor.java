package design.mode;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建一个账单接口，有接收访问者的功能
 * 
 * @author hgx
 *
 */
interface Bill {
    void accept(AccountBookView viewer);
}

/**
 * 消费单子
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
 * 收入单子
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
 * 访问者接口
 * 
 * @author hgx
 *
 */
interface AccountBookView {
    // 查看消费的单子
    void view(ConsumerBill consumerBill);

    // 查看收入单子
    void view(IncomeBill incomeBill);
}

// 老板类：访问者是老板，主要查看总支出和总收入
class Boss implements AccountBookView {

    private double totalConsumer;
    private double totalIncome;

    // 查看消费的单子
    public void view(ConsumerBill consumerBill) {
        totalConsumer = totalConsumer + consumerBill.getAmount();
    }

    // 查看收入单子
    public void view(IncomeBill incomeBill) {
        totalIncome = totalIncome + incomeBill.getAmount();
    }

    public void getTotalConsumer() {
        System.out.println("老板一共消费了" + totalConsumer);
    }

    public void getTotalIncome() {
        System.out.println("老板一共收入了" + totalIncome);
    }
}

/**
 * 会计类：访问者是会计，主要记录每笔单子
 * 
 * @author hgx
 *
 */

class CPA implements AccountBookView {

    int count = 0;

    // 查看消费的单子
    public void view(ConsumerBill consumerBill) {
        count++;
        if (consumerBill.getItem().equals("消费")) {
            System.out.println("第" + count + "个单子消费了：" + consumerBill.getAmount());
        }
    }
    // 查看收入单子

    public void view(IncomeBill incomeBill) {

        if (incomeBill.getItem().equals("收入")) {
            System.out.println("第" + count + "个单子收入了：" + incomeBill.getAmount());
        }
    }

}

/**
 * 账单类：用于添加账单，和为每一个账单添加访问者
 * 
 * @author hgx
 *
 */
class AccountBook {

    private List<Bill> listBill = new ArrayList<Bill>();

    // 添加单子
    public void add(Bill bill) {
        listBill.add(bill);
    }

    // 为每个账单添加访问者
    public void show(AccountBookView viewer) {
        for (Bill b : listBill) {
            b.accept(viewer);
        }
    }
}

/*
 *测试类
 */
public class Visitor {

    public static void main(String[] args) {
        // 创建消费和收入单子
        Bill consumerBill = new ConsumerBill("消费", 3000);
        Bill incomeBill = new IncomeBill("收入", 5000);
        Bill consumerBill2 = new ConsumerBill("消费", 4000);
        Bill incomeBill2 = new IncomeBill("收入", 8000);
        // 添加单子
        AccountBook accountBook = new AccountBook();
        accountBook.add(consumerBill);
        accountBook.add(incomeBill);
        accountBook.add(consumerBill2);
        accountBook.add(incomeBill2);
        // 创建访问者
        AccountBookView boss = new Boss();
        AccountBookView cpa = new CPA();

        // 接受访问者
        accountBook.show(boss);
        accountBook.show(cpa);
        // boss查看总收入和总消费
        ((Boss) boss).getTotalConsumer();
        ((Boss) boss).getTotalIncome();

    }

}

