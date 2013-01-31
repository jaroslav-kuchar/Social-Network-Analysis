package cz.cvut.fit.gephi.snametrics.clusteringcoefficient;

import java.text.DecimalFormat;
import javax.swing.JPanel;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.statistics.spi.Statistics;
import org.gephi.statistics.spi.StatisticsUI;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jaroslav Kuchar
 */
@ServiceProvider(service = StatisticsUI.class)
public class ClusteringMetricUI implements StatisticsUI {

    private ClusteringMetricPanel panel;
    private ClusteringMetric clusteringMetric;

    @Override
    public JPanel getSettingsPanel() {
        panel = new ClusteringMetricPanel();
        return panel;
    }

    @Override
    public void setup(Statistics statistics) {
        this.clusteringMetric = (ClusteringMetric) statistics;
        GraphController graphController = Lookup.getDefault().lookup(GraphController.class);
        GraphModel model = graphController.getModel();
        Graph graph = model.getGraph();
        if (panel != null) {
            panel.setDirected(model.isDirected());
        }
    }

    @Override
    public void unsetup() {
        if (panel != null) {
            clusteringMetric.setDirected(panel.isDirected());
        }
        panel = null;
    }

    @Override
    public Class<? extends Statistics> getStatisticsClass() {
        return ClusteringMetric.class;
    }

    @Override
    public String getValue() {
        if (clusteringMetric != null) {
            DecimalFormat df = new DecimalFormat("###.###");
            return "" + df.format(clusteringMetric.getAverageCoefficient());
        }
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Clustering Coefficient";
    }

    @Override
    public String getCategory() {
        return StatisticsUI.CATEGORY_NODE_OVERVIEW;
    }

    @Override
    public int getPosition() {
        return 800;
    }

    @Override
    public String getShortDescription() {
        return "Computes Clustering Coefficient";
    }
}
