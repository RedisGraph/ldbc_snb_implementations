package redisgraph;

import com.ldbc.impls.workloads.ldbc.snb.interactive.InteractiveTest;
import com.ldbc.impls.workloads.ldbc.snb.redisgraph.interactive.RedisGraphCypherInteractiveDb;
import org.junit.Ignore;

import java.util.HashMap;
import java.util.Map;

@Ignore
public class RedisGraphCypherInteractiveTest extends InteractiveTest {

    private final String host = "localhost";
    private final String port = "6379";
    private final String graphId = "graph";
    private final String queryDir = "queries";

    public RedisGraphCypherInteractiveTest() {
        super(new RedisGraphCypherInteractiveDb());
    }

    @Override
    protected final Map<String, String> getProperties() {
        final Map<String, String> properties = new HashMap<>();
        properties.put("host", host);
        properties.put("port", port);
        properties.put("graphId", graphId);
        properties.put("queryDir", queryDir);
        properties.put("printQueryNames", "true");
        properties.put("printQueryStrings", "true");
        properties.put("printQueryResults", "true");
        return properties;
    }

}
