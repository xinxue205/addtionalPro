package jaas;

import java.security.Principal;

public class SamplePrincipal implements Principal {
    private String name;
 
    public SamplePrincipal(String name) {
        this.name = name;
    }
 
    public String getName() {
        return name;
    }
 
    public boolean equals(Object o) {
        if (o == null)
            return false;
 
        if (this == o)
            return true;
 
        if (!(o instanceof SamplePrincipal))
            return false;
        SamplePrincipal that = (SamplePrincipal) o;
 
        if (this.getName().equals(that.getName()))
            return true;
        return false;
    }
 
    public int hashCode() {
        return name.hashCode();
    }
}
