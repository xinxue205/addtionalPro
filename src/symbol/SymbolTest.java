package symbol;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class SymbolTest {
	public static void main(String[] args) {
        String s = "{ int x ; char y ; { bool y ; x ; y ; } x ; y ; }";
        SymbolTest lexer = new SymbolTest();
        try {
            lexer.trans(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Hashtable words = new Hashtable();
    
    public void reserve(Token t) { words.put(t.lexeme, t); }

    public SymbolTest() {
        reserve( new Token("int") ) ;
        reserve( new Token("float") );
        reserve( new Token("double") );
        reserve( new Token("char") );
        reserve( new Token("bool") );
    }
    
    public void print(String s) { System.out.print(s); }
    
    public void trans(String ss) throws IOException {
        Scanner in = new Scanner(ss);
        
        Env top = new Env(null);
        
        while (in.hasNext()) {
            String s = in.next();
            
            if (s.equals("{")) {
                top = new Env(top);
                print("{ ");
            } else if (s.equals("}")) {
                top = top.prev;
                print("} ");
            } else {
                Token w = (Token) words.get(s);
                if (w != null) {        // 类型
                    s = in.next();
                    top.put(s, new Id(s, w.lexeme));
                } else {                // 变量
                    Id id = top.get(s);
                    print(id.toString() + " ");
                }
                
                in.next();                // 去掉分号
            }
        }
    }
}

class Token {
    public final String lexeme;
    public Token(String s) { lexeme = s; }
    public String toString() { return lexeme; }
}

class Id extends Token {
    public final String type;
    public Id(String s, String t) { super(s); type = t; }
    public String toString() { return lexeme + ":" + type + ";"; }
}

class Env {
    private Hashtable table;
    protected Env prev;
    public Env(Env p) { 
    	table = new Hashtable(); 
    	prev = p; 
    }
    
    public void put(String s, Id id) {
    	table.put(s, id); 
    }

    public Id get(String s) {
        for (Env e=this; e != null; e = e.prev) {
            Id found = (Id) e.table.get(s);
            if (found != null) return found;
        }
        return null;
    }
}