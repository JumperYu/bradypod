import com.google.common.graph.ElementOrder;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * successors 相邻的且可到达的
 *
 * adjacentNodes 相邻的节点
 *
 */
public class DijkstraSolve {

    private final String sourceNode;
    private final MutableValueGraph<String, Integer> graph;

    public DijkstraSolve(String sourceNode, MutableValueGraph<String, Integer> graph) {
        this.sourceNode = sourceNode;
        this.graph = graph;
    }

    public static void main(String[] args) {
        MutableValueGraph<String, Integer> graph = buildGraph();
        DijkstraSolve dijkstraSolve = new DijkstraSolve("A", graph);

        dijkstraSolve.dijkstra();
        dijkstraSolve.printResult();
    }

    private void dijkstra() {

        initPathFromSourceNode(sourceNode);

        Set<String> nodes = graph.nodes();
        if (!nodes.contains(sourceNode)) {
            throw new IllegalArgumentException(sourceNode + " is not in this graph!");
        }

        Set<String> notVisitedNodes = new HashSet<>(graph.nodes());
        String currentVisitNode = sourceNode;
        while (!notVisitedNodes.isEmpty()) {
            String nextVisitNode = findNextNode(currentVisitNode, notVisitedNodes);
            if (nextVisitNode.equals("")) {
                break;
            }
            notVisitedNodes.remove(currentVisitNode);
            currentVisitNode = nextVisitNode;
        }
    }

    private String findNextNode(String currentVisitNode, Set<String> notVisitedNodes) {
        int shortestPath = Integer.MAX_VALUE;
        String nextVisitNode = "";

        for (String node : graph.nodes()) {

            // 节点是自己或者已经判定过的就不再重复了
            if (currentVisitNode.equals(node) || !notVisitedNodes.contains(node)) {
                continue;
            }

            // 看节点 是否可达且相邻
            if (graph.successors(currentVisitNode).contains(node)) {
                Integer edgeValue = graph.edgeValue(sourceNode, currentVisitNode).get() + graph.edgeValue(currentVisitNode, node).get();
                Integer currentPathValue = graph.edgeValue(sourceNode, node).get();
                if (edgeValue > 0) {
                    graph.putEdgeValue(sourceNode, node, Math.min(edgeValue, currentPathValue));
                }
            }

            if (graph.edgeValue(sourceNode, node).get() < shortestPath) {
                shortestPath = graph.edgeValue(sourceNode, node).get();
                nextVisitNode = node;
            }
        }

        return nextVisitNode;
    }

    /**
     *
     * 把和主节点不相邻的节点补上, 并且置为无穷大
     *
     * @param sourceNode
     */
    private void initPathFromSourceNode(String sourceNode) {
        graph.nodes().stream().filter(
                node -> !graph.adjacentNodes(sourceNode).contains(node))
                .forEach(node -> graph.putEdgeValue(sourceNode, node, Integer.MAX_VALUE));
        graph.putEdgeValue(sourceNode, sourceNode, 0);
    }

    private void printResult() {
        for (String node : graph.nodes()) {
            System.out.println(sourceNode + "->" + node + " shortest path is:" + graph.edgeValue(sourceNode, node));
        }
    }

    private static MutableValueGraph<String, Integer> buildGraph() {
        MutableValueGraph<String, Integer> graph = ValueGraphBuilder.directed()
                .nodeOrder(ElementOrder.<String>natural()).allowsSelfLoops(true).build();

        graph.putEdgeValue("A", "B", 2);
        graph.putEdgeValue("A", "C", 10);
        graph.putEdgeValue("B", "C", 3);
        graph.putEdgeValue("B", "D", 1);

        return graph;
    }

}