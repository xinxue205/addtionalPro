package IO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOTest {
	 private int ports[];
	 private ByteBuffer echoBuffer = ByteBuffer.allocate(1024);
	 public NIOTest(int ports[]) throws IOException {
	      this.ports = ports;
	      go();
	 }
	 private void go() throws IOException {
	      // 1. ����һ��selector��select��NIO�еĺ��Ķ���
	      // �������������ָ���Ȥ��IO�¼�
	      Selector selector = Selector.open();
	      // Ϊÿ���˿ڴ�һ������, ������Щ����ע�ᵽselector��
	      for (int i = 0; i < ports.length; ++i) {
	           //2. ��һ��ServerSocketChannel
	           //��ʵ����ÿ����һ���˿ھ���Ҫһ��channel
	           ServerSocketChannel ssc = ServerSocketChannel.open();
	           ssc.configureBlocking(false);//����Ϊ������
	           InetSocketAddress address = new InetSocketAddress(ports[i]);
	           ssc.socket().bind(address);//����һ���˿�
	           //3. ע�ᵽselector
	           //register�ĵ�һ��������Զ����selector
	           //�ڶ�������������Ҫ�������¼�
	           //OP_ACCEPT���½������ӵ��¼�
	           //Ҳ��������ServerSocketChannel��Ψһ�¼�����
	           ssc.register(selector, SelectionKey.OP_ACCEPT);
	           System.out.println("Going to listen on " + ports[i]);
	      }
	      //4. ��ʼѭ���������Ѿ�ע����һЩIO��Ȥ�¼�
	      while (true) {
	           //���������������ֱ��������һ����ע����¼���������һ�����߸�����¼�����ʱ
	           // select() �������������������¼���������
	           int num = selector.select();
	           //��ȡ�����¼��� SelectionKey ���� ����
	           Set selectedKeys = selector.selectedKeys();
	           //����ͨ������ SelectionKeys �����δ���ÿ�� SelectionKey �������¼�
	           //����ÿһ�� SelectionKey��������ȷ����������ʲô I/O �¼����Լ�����¼�Ӱ����Щ I/O ����
	           Iterator it = selectedKeys.iterator();
	           while (it.hasNext()) {
	                SelectionKey key = (SelectionKey) it.next();
	                //5. ���������ӡ�����ִ�е�������ǽ�ע���� ServerSocketChannel
	                //���ҽ�ע�����ǡ����ա��¼���Ϊȷ����һ��
	                //���Ƕ� SelectionKey ���� readyOps() ����������鷢����ʲô���͵��¼�
	                if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
	                     //6. ������һ�������ӡ���Ϊ����֪������������׽�������һ�����������ڵȴ�
	                     //���Կ��԰�ȫ�ؽ�������Ҳ����˵�����õ��� accept() ����������
	                     ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
	                     SocketChannel sc = ssc.accept();
	                     sc.configureBlocking(false);
	                     // 7. ��������ע�ᵽselector���������ӵ� SocketChannel ����Ϊ��������
	                     //�������ڽ���������ӵ�Ŀ����Ϊ�˶�ȡ�����׽��ֵ����ݣ��������ǻ����뽫 SocketChannel ע�ᵽ Selector��
	                     SelectionKey newKey = sc.register(selector,SelectionKey.OP_READ);
	                     it.remove();
	                     System.out.println("Got connection from " + sc);
	                } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
	                     // Read the data
	                     SocketChannel sc = (SocketChannel) key.channel();
	                     // Echo data
	                     int bytesEchoed = 0;
	                     while (true) {
	                          echoBuffer.clear();
	                          int r = sc.read(echoBuffer);
	                          if (r <= 0) {
	                               break;
	                          }
	                          echoBuffer.flip();
	                          sc.write(echoBuffer);
	                          bytesEchoed += r;
	                     }
	                     System.out.println("Echoed " + bytesEchoed + " from " + sc);
	                     it.remove();
	                }
	           }
	           // System.out.println( "going to clear" );
	           // selectedKeys.clear();
	           // System.out.println( "cleared" );
	      }
	 }
	 static public void main(String args2[]) throws Exception {
	      String args[]={"9001","9002","9003"};
	      if (args.length <= 0) {
	           System.err.println("Usage: java MultiPortEcho port [port port ...]");
	           System.exit(1);
	      }
	      int ports[] = new int[args.length];
	      for (int i = 0; i < args.length; ++i) {
	           ports[i] = Integer.parseInt(args[i]);
	      }
	      new NIOTest(ports);
	 }
}
