package cz.cvut.fit.gephi.snametrics.clusteringcoefficient;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeOrigin;
import org.gephi.data.attributes.api.AttributeRow;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.HierarchicalGraph;
import org.gephi.graph.api.Node;
import org.gephi.statistics.spi.Statistics;
import org.gephi.utils.longtask.spi.LongTask;
import org.gephi.utils.progress.Progress;
import org.gephi.utils.progress.ProgressTicket;

/**
 * The local clustering coefficient of the light blue node is computed as the proportion of connections among its neighbors which are actually realized compared with the number of all possible connections.
 * http://en.wikipedia.org/wiki/Clustering_coefficient
 * @author Jaroslav Kuchar
 */
public class ClusteringMetric implements Statistics, LongTask {

    // cancel progress
    private boolean cancel = false;
    private ProgressTicket progressTicket;
    // average coefficient and node coefficients
    private double averageCoefficient = 0.0;
    private double[] nodeCoefficients = null;
    // is directed
    private boolean directed = false;

    @Override
    public void execute(GraphModel graphModel, AttributeModel attributeModel) {
        //Graph graph = graphModel.getGraphVisible();
        HierarchicalGraph graph = null;
        // get visible graph
        if (directed) {
            graph = graphModel.getHierarchicalDirectedGraphVisible();
        } else {
            graph = graphModel.getHierarchicalUndirectedGraphVisible();
        }

        // lock graph
        graph.readLock();
        try {
            Progress.start(progressTicket, graph.getNodeCount());
            // all coefficients
            nodeCoefficients = new double[graph.getNodeCount()];

            // attribute column
            AttributeTable nodeTable = attributeModel.getNodeTable();
            AttributeColumn clusteringColumn = nodeTable.getColumn("newClusteringCoefficient");
            if (clusteringColumn == null) {
                clusteringColumn = nodeTable.addColumn("newClusteringCoefficient", "Local Clustering Coefficient", AttributeType.DOUBLE, AttributeOrigin.COMPUTED, 0.0);
            }

            int i = 0;
            // for each node
            for (Node e : graph.getNodes()) {
                // compute coefficient
                double coeficient = 0.0;
                double denominator = (graph.getDegree(e) * (graph.getDegree(e) - 1));
                if (!directed) {
                    denominator /= 2;
                }
                double numerator = 0.0;
                // get neighbors as list
                List<Node> n2 = Arrays.asList(graph.getNeighbors(e).toArray());
                List<Node> neighbors2 = new ArrayList<Node>(n2);
                for (Node neighbor1 : graph.getNeighbors(e)) {
                    neighbors2.remove(neighbor1);
                    // count edges betwwen neighbors
                    for (Node neighbor2 : neighbors2) {
                        if (graph.getEdge(neighbor1, neighbor2) != null || graph.getEdge(neighbor2, neighbor1) != null) {
                            numerator++;
                        }
                    }
                }
                // compute coefficient
                if (denominator > 0) {
                    coeficient = numerator / denominator;
                } else {
                    coeficient = 0.0;
                }
                averageCoefficient += coeficient;
                nodeCoefficients[i] = coeficient;
                i++;
                // set attribute
                AttributeRow row = (AttributeRow) e.getNodeData().getAttributes();
                row.setValue(clusteringColumn, coeficient);
                Progress.progress(progressTicket);
                if (cancel) {
                    break;
                }
            }
            if (graph.getNodeCount() > 0) {
                averageCoefficient = averageCoefficient / graph.getNodeCount();
            }

            graph.readUnlockAll();
        } catch (Exception e) {
            e.printStackTrace();
            //Unlock graph
            graph.readUnlockAll();
        }
    }

    @Override
    public String getReport() {
        NumberFormat f = new DecimalFormat("#0.000");
        String report = "<HTML> <BODY> <h1>Clustering Coefficient Report </h1> "
                + "<hr>"
                + "<br> <h2> Results: </h2>"
                + "Graph: " + (directed ? "directed" : "undirected") + "<br />"
                + "Average Clustering Coefficient: " + f.format(averageCoefficient) + "<br />"
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

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public boolean isDirected() {
        return directed;
    }

    public double getAverageCoefficient() {
        return averageCoefficient;
    }
}
