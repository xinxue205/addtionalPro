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

             // 这个配置文件主要是记录 kerberos的相关配置信息，例如KDC是哪个IP？默认的realm是哪个？
             // 如果没有这个配置文件这边认证的时候肯定不知道KDC的路径喽
             // 这个文件也是从远程服务器上copy下来的
            System. setProperty("java.security.krb5.conf", "C:/Users/dongzeguang/Downloads/krb5.conf" );
            
             conf = HBaseConfiguration.create();
             conf.set("hadoop.security.authentication" , "Kerberos" );
             // 这个hbase.keytab也是从远程服务器上copy下来的, 里面存储的是密码相关信息
             // 这样我们就不需要交互式输入密码了
             conf.set("keytab.file" , "C:/Users/Downloads/hbase.keytab" );
             // 这个可以理解成用户名信息，也就是Principal
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