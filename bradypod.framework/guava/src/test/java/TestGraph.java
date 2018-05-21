import com.google.common.graph.ElementOrder;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.junit.Before;
import org.junit.Test;

public class TestGraph {

    MutableValueGraph<String, Integer> graph = ValueGraphBuilder.directed()
            .nodeOrder(ElementOrder.<String>natural()).allowsSelfLoops(true).build();

    @Before
    public void testInit() {
        graph.putEdgeValue("A", "B", 10);
        graph.putEdgeValue("A", "C", 3);
        graph.putEdgeValue("A", "D", 20);
        graph.putEdgeValue("B", "D", 5);
        graph.putEdgeValue("C", "B", 2);
        graph.putEdgeValue("C", "E", 15);
        graph.putEdgeValue("D", "E", 11);
    }

    @Test
    public void test() {
        System.out.println(graph.edgeValue("A", "D").orElse(Integer.MAX_VALUE));
    }

}
