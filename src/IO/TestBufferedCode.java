package IO;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * 简繁体转换
 *
 * @author pengjianbo <pengjianbosoft@gmail.com>
 * $Id$
 */
public class TestBufferedCode {

    public TestBufferedCode() throws Exception {

        File simplFile = new File(
                "D:\\android\\JavaUtils\\src\\com\\java\\utils\\charactor\\simplified.txt");
        FileInputStream simplFis = new FileInputStream(simplFile);
        BufferedInputStream simplBis = new BufferedInputStream(simplFis);
//        BufferedReader simplBr = new BufferedReader(new InputStreamReader(simplBis));
        StringBuffer simplsb = new StringBuffer();

        byte[] simplb = new byte[1024];
        while ((simplBis.read(simplb)) != -1) {
            simplsb.append(new String(simplb));
        }
        
        simplFis.close();
        simplBis.close();
        
        
        File tradFile = new File(
                "D:\\android\\JavaUtils\\src\\com\\java\\utils\\charactor\\traditional.txt");
        FileInputStream tradFis = new FileInputStream(tradFile);
        BufferedInputStream tradBis = new BufferedInputStream(tradFis);
        StringBuffer tradsb = new StringBuffer();

        byte[] tradb = new byte[1024];
        while ((tradBis.read(tradb)) != -1) {
            tradsb.append(new String(tradb));
        }
        
        tradBis.close();
        tradFis.close();
        
        System.out.println(simplsb.toString());
        /*CnGetPinyin pinyin = new CnGetPinyin();
        //连接SQLite的JDBC
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:pai.db");
        Statement stat = conn.createStatement();
        for(int i = 0; i < simplsb.length() -1; i++ ) {
            
            stat.executeUpdate( "insert into CNLang(pinyin,simp,trad) values('" + pinyin.getPinyin(simplsb.substring(i, i + 1)) + "','"
                                + simplsb.substring(i, i + 1) + "','" + tradsb.substring(i, i + 1) + "')");
            System.out.println("正在添加:" + simplsb.substring(i, i + 1) + "-->"  + tradsb.substring(i, i + 1));
            if( i > simplsb.length() -1 ) {
                stat.close();
                conn.close();
            }
        }*/
        
    }

    public static void main(String[] args) throws Exception {
        new TestBufferedCode();
    }

}
