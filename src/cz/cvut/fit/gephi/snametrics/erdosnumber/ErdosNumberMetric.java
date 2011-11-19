package cz.cvut.fit.gephi.snametrics.erdosnumber;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeOrigin;
import org.gephi.data.attributes.api.AttributeRow;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.statistics.spi.Statistics;
import org.gephi.utils.longtask.spi.LongTask;
import org.gephi.utils.progress.Progress;
import org.gephi.utils.progress.ProgressTicket;

/**
 *
 * @author Jaroslav Kuchar
 */
public class ErdosNumberMetric implements Statistics, LongTask {

    private boolean cancel = false;
    private ProgressTicket progressTicket;
    private Node sourceNode;
    private double erdosAverage = 0.0;
    private int numberOfUnconnected = 0;

    public void setSourceNode(Node source) {
        this.sourceNode = source;
    }

    @Override
    public void execute(GraphModel graphModel, AttributeModel attributeModel) {
        Graph graph = graphModel.getGraphVisible();
        graph.readLock();


        AttributeTable nodeTable = attributeModel.getNodeTable();
        AttributeColumn erdosColumn = nodeTable.getColumn("erdosNumber");

        if (erdosColumn == null) {
            erdosColumn = nodeTable.addColumn("erdosNumber", "Erdös Number", AttributeType.INT, AttributeOrigin.COMPUTED, 0);
        }

        // http://www.personal.kent.edu/~rmuhamma/Algorithms/MyAlgorithms/GraphAlgor/breadthSearch.htm
        Queue<Node> queue = new LinkedList<Node>();
        Set<Node> blacks = new HashSet<Node>();

        try {
            Progress.start(progressTicket, graph.getNodeCount());
            // init source node
            Node source = null;
            // if not set, select first
            if (sourceNode == null) {
                source = graph.getNodes().toArray()[0];
            } else {
                source = sourceNode;
            }
            // init source node as erdos to zero
            ((AttributeRow) source.getNodeData().getAttributes()).setValue(erdosColumn, 0);

            // start breadth search
            queue.add(source);
            double erdosSum = 0.0;
            Node current = null;
            while (queue.size() > 0) {
                current = queue.remove();
                for (Node gray : graph.getNeighbors(current)) {
                    if (!blacks.contains(gray)) {
                        // get current node attribute - erdos
                        int erdos = (Integer) ((AttributeRow) (current.getNodeData().getAttributes())).getValue(erdosColumn);
                        // erdos  +1
                        ((AttributeRow) gray.getNodeData().getAttributes()).setValue(erdosColumn, erdos + 1);
                        queue.add(gray);
                        blacks.add(gray);
                        erdosSum += (erdos + 1);
                    }
                }
                blacks.add(current);
                Progress.progress(progressTicket);
                if (cancel) {
                    break;
                }
            }

            // for all unconnected nodes set max
            if (blacks.size() < graph.getNodeCount()) {
                Set<Node> allNodes = new HashSet<Node>(Arrays.asList(graph.getNodes().toArray()));
                allNodes.removeAll(blacks);
                int erdos = (Integer) ((AttributeRow) (current.getNodeData().getAttributes())).getValue(erdosColumn);
                for (Node unconnected : allNodes) {
                    ((AttributeRow) unconnected.getNodeData().getAttributes()).setValue(erdosColumn, erdos + 1);
                }
                this.numberOfUnconnected = allNodes.size();
            }

            this.erdosAverage = erdosSum / (blacks.size() - 1);
            graph.readUnlockAll();
        } catch (Exception e) {
            e.printStackTrace();
            //Unlock graph
            graph.readUnlockAll();
        }

    }

    public double getErdosAverage() {
        return erdosAverage;
    }

    @Override
    public String getReport() {
        NumberFormat f = new DecimalFormat("#0.000");
        String report = "<HTML> <BODY> <h1>Erdös Number Report </h1> "
                + "<hr>"
                + "<br> <h2> Results: </h2>"
                + "Average number: " + f.format(getErdosAverage()) + "<br />"
                + "Number of unconnected nodes: " + numberOfUnconnected + "<br />"
                + "</BODY></HTML>";
        return report;
    }

    @Override
    public boolean cancel() {
        cancel = true;
        return true;
    }

    @Override
    public void setProgressTicket(ProgressTicket progressTicket) {
        this.progressTicket = progressTicket;
    }
}
