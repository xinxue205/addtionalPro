package ipc;


import sun.misc.*;

class TestSignal implements SignalHandler {

    public void handle(Signal arg0) {
        System.out.println(arg0.getName() + "is recevied.");
    }
}

//在Linux下支持的信号（具体信号kill -l命令查看）：
//SEGV, ILL, FPE, BUS, SYS, CPU, FSZ, ABRT, INT, TERM, HUP, USR1, USR2, QUIT, BREAK, TRAP, PIPE
//在Windows下支持的信号：
//SEGV, ILL, FPE, ABRT, INT, TERM, BREAK

public class SignalTest {
    @SuppressWarnings("restriction")
    public static void main(String[] args) {
        TestSignal handler = new TestSignal();
        Signal.handle(new Signal("TERM"), handler);
        Signal.handle(new Signal("INT"), handler);
        Signal.handle(new Signal("USR1"), handler);
        Signal.handle(new Signal("USR2"), handler);
        for (;;) {
            System.out.println("do something");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}