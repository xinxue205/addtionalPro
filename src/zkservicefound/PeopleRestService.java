package zkservicefound;

import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

public class PeopleRestService {
public static final String PEOPLE_PATH = "/people";

public void init() throws Exception {
}

public Collection< Person > getPeople(final int page ) {
return Arrays.asList(
new Person( "Tom", "Bombadil" ),
new Person( "Jim", "Tommyknockers" )
);
}
}
