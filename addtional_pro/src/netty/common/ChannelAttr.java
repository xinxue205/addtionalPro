package netty.common;


import java.util.Date;

import io.netty.util.AttributeKey;
 
public class ChannelAttr {
	public static final AttributeKey<ChannelAttr> NETTY_CHANNEL_KEY = AttributeKey.valueOf("netty.channel");
    
    private String name;
    
    
    private Date createDate;
 
 
    public ChannelAttr(String name,Date createDate) {
        this.name = name;
        this.createDate = createDate;
    }
 
    public String getName() {
        return name;
    }
 
 
    public void setName(String name) {
        this.name = name;
    }
 
 
    public Date getCreateDate() {
        return createDate;
    }
 
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
 
}
