/**
 * 
 */
package multi.state;

/**
 * @author Administrator
 * @date 2013-10-9 ÉÏÎç09:50:31
 * @version 1.0  Administrator create
 * @CopyRight (c) 2013 xxxx¹«Ë¾ 
 */
public class TestAbstractType extends door{

	/* (non-Javadoc)
	 * @see multi.state.door#open()
	 */
	@Override
	public void open() {
		// TODO Auto-generated method stub
		
	}
	
}

abstract class door implements Openable{
	String name;
	public abstract void open();
}

interface Openable{
	void open();
}