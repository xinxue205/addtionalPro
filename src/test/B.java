package test;
class A {
  protected void print(String s) {
    System.out.println(s);
  }
  A() {
    print("A()");
  }
  public void f() {
    print("A:f()");
  }
}

public class B extends A{
  B(){
    print("B()");
  }
  public void correct(){
    print("B:f()");
  }

  public static void main(String args[]){
    B b=new B();
    b.f();
    b.correct();
  }
}