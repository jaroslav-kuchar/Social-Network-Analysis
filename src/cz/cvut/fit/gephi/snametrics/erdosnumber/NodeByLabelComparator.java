package cz.cvut.fit.gephi.snametrics.erdosnumber;

import java.util.Comparator;
import org.gephi.graph.api.Node;

/**
 *
 * @author Jaroslav Kuchar
 */
public class NodeByLabelComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        //return o1.getNodeData().getId().compareTo(o2.getNodeData().getId());
        return o1.getNodeData().getLabel().compareTo(o2.getNodeData().getLabel());
    }
}
