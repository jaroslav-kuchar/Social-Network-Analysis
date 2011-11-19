package cz.cvut.fit.gephi.snametrics.overlap;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeOrigin;
import org.gephi.data.attributes.api.AttributeRow;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.graph.api.Edge;
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
public class OverlapMetric implements Statistics, LongTask {

    private boolean cancel = false;
    private ProgressTicket progressTicket;
    private double averageOverlap = 0.0;
    private double averageEmbeddedness = 0.0;

    @Override
    public void execute(GraphModel graphModel, AttributeModel attributeModel) {
        Graph graph = graphModel.getGraphVisible();
        graph.readLock();

        try {
            Progress.start(progressTicket, graph.getEdgeCount());

            // attributes od edges
            AttributeTable edgeTable = attributeModel.getEdgeTable();
            AttributeColumn overlapColumn = edgeTable.getColumn("neighborhoodOverlap");
            AttributeColumn embeddednessColumn = edgeTable.getColumn("embeddedness");
            if (overlapColumn == null) {
                overlapColumn = edgeTable.addColumn("neighborhoodOverlap", "Neighborhood Overlap", AttributeType.DOUBLE, AttributeOrigin.COMPUTED, 0.0);
            }
            if (embeddednessColumn == null) {
                embeddednessColumn = edgeTable.addColumn("embeddedness", "Embeddedness", AttributeType.DOUBLE, AttributeOrigin.COMPUTED, 0.0);
            }

            // for each edge
            for (Edge e : graph.getEdges()) {                
                // get nodes on both sides od edge
                List<Node> sourceNeighbors = Arrays.asList(graph.getNeighbors(e.getSource()).toArray());
                List<Node> targetNeighbors = Arrays.asList(graph.getNeighbors(e.getTarget()).toArray());
                Set<Node> sourceSet = new HashSet<Node>(sourceNeighbors);
                Set<Node> targetSet = new HashSet<Node>(targetNeighbors);
                Set<Node> numerator = new HashSet<Node>(sourceSet);
                Set<Node> denominator = new HashSet<Node>(sourceSet);
                double overlap = 0;
                // find intersection and union
                numerator.retainAll(targetSet);
                denominator.addAll(targetSet);                
                if (denominator.size() > 0) {
                    overlap = ((double) numerator.size() / (double) denominator.size());
                }
                AttributeRow row = (AttributeRow) e.getEdgeData().getAttributes();
                row.setValue(overlapColumn, overlap);
                row.setValue(embeddednessColumn, numerator.size());
                averageOverlap += overlap;
                averageEmbeddedness += numerator.size();

                Progress.progress(progressTicket);
                if (cancel) {
                    break;
                }

            }
            if (graph.getEdgeCount() > 0) {
                averageOverlap = averageOverlap / graph.getEdgeCount();
                averageEmbeddedness = averageEmbeddedness / graph.getEdgeCount();
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
        String report = "<HTML> <BODY> <h1>Overlap Neighborhood and Embeddeddness Report </h1> "
                + "<hr>"
                + "<br> <h2> Results: </h2>"
                + "Average Neighborhood Overlap: " + f.format(averageOverlap) + "<br />"
                + "Average Embeddedness: " + f.format(averageEmbeddedness)
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
