package cz.cvut.fit.gephi.snametrics.overlap;

import javax.swing.JPanel;
import org.gephi.statistics.spi.Statistics;
import org.gephi.statistics.spi.StatisticsUI;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jaroslav Kuchar
 */
@ServiceProvider(service = StatisticsUI.class)
public class OverlapMetricUI implements StatisticsUI {

    private OverlapMetricPanel panel;
    private OverlapMetric overlapMetric;

    @Override
    public JPanel getSettingsPanel() {
        panel = new OverlapMetricPanel();
        return panel;
    }

    @Override
    public void setup(Statistics statistics) {
        this.overlapMetric = (OverlapMetric) statistics;
        if (panel != null) {
        }
    }

    @Override
    public void unsetup() {
        if (panel != null) {
            //myMetric.
        }
        panel = null;
    }

    @Override
    public Class<? extends Statistics> getStatisticsClass() {
        return OverlapMetric.class;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Neighborhood Overlap, Embeddedness";

    }

    @Override
    public String getCategory() {
        return StatisticsUI.CATEGORY_EDGE_OVERVIEW;
    }

    @Override
    public int getPosition() {
        return 800;

    }

    @Override
    public String getShortDescription() {
        return "Computes Neighborhood Overlap, Embeddedness";
    }
}
