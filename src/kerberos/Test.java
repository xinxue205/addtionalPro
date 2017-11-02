package kerberos;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.security.UserGroupInformation;

public class Test {
       private static Configuration conf = null;

       static {

             // ��������ļ���Ҫ�Ǽ�¼ kerberos�����������Ϣ������KDC���ĸ�IP��Ĭ�ϵ�realm���ĸ���
             // ���û����������ļ������֤��ʱ��϶���֪��KDC��·���
             // ����ļ�Ҳ�Ǵ�Զ�̷�������copy������
            System. setProperty("java.security.krb5.conf", "C:/Users/dongzeguang/Downloads/krb5.conf" );
            
             conf = HBaseConfiguration.create();
             conf.set("hadoop.security.authentication" , "Kerberos" );
             // ���hbase.keytabҲ�Ǵ�Զ�̷�������copy������, ����洢�������������Ϣ
             // �������ǾͲ���Ҫ����ʽ����������
             conf.set("keytab.file" , "C:/Users/Downloads/hbase.keytab" );
             // ������������û�����Ϣ��Ҳ����Principal
             conf.set("kerberos.principal" , "hbase/1722.myip.domain@HADOOP.COM" );    
            conf.set("hbase.master.kerberos.principal",HBASE_MASTER_PRINCIPAL);
            conf.set("hbase.regionserver.kerberos.principal",HBASE_RS_PRINCIPAL);
            conf.set("hbase.zookeeper.quorum","xxx.xxx.xxx.xxx");
            conf.set("hbase.zookeeper.property.clientPort","2181");
            conf.set("hbase.security.authentication","kerberos");

            UserGroupInformation.setConfiguration(conf);
             try {
                  UserGroupInformation. loginUserFromKeytab("hbase/1722.myip.domain@HADOOP.COM", "C:/Users/Downloads/hbase.keytab" );
            } catch (IOException e) {
                   // TODO Auto-generated catch block
                  e.printStackTrace();
            }
      }

       public static void scanSpan(final String tableName) throws Exception {
            HTable table =  new HTable(conf, tableName);
            System. out.println("tablename:" +new String(table.getTableName()));
            Scan s = new Scan();
            ResultScanner rs = table.getScanner(s);
            
             for (Result r : rs) {
                  System. out.println(r.toString());
                  KeyValue[] kv = r. raw();
                   for (int i = 0; i < kv.length; i++) {
                        System. out.print(new String(kv[i].getRow()) + "");
                        System. out.print(new String(kv[i].getFamily()) + ":");
                        System. out.print(new String(kv[i].getQualifier() ) + "" );
                        System. out.print(kv[i].getTimestamp() + "" );
                        System. out.println(new String(kv[i].getValue() ));
                  }
            }

      }

       /**
       * @param args
       */
       public static void main(String[] args) {
             // TODO Auto-generated method stub
             try {
                  Test. scanSpan("h_span");
            } catch (Exception e) {
                   // TODO Auto-generated catch block
                  e.printStackTrace();
            }
      }

}