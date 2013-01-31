package cz.cvut.fit.gephi.snametrics.erdosnumber;

import java.text.DecimalFormat;
import javax.swing.JPanel;
import org.gephi.statistics.spi.Statistics;
import org.gephi.statistics.spi.StatisticsUI;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jaroslav Kuchar
 */
@ServiceProvider(service = StatisticsUI.class)
public class ErdosNumberMetricUI implements StatisticsUI {

    private ErdosNumberMetricPanel panel;
    private ErdosNumberMetric erdosMetric;

    @Override
    public JPanel getSettingsPanel() {
        panel = new ErdosNumberMetricPanel();
        return panel;
    }

    @Override
    public void setup(Statistics statistics) {
        this.erdosMetric = (ErdosNumberMetric) statistics;
        if (panel != null) {
            panel.setSelected(0);
        }
    }

    @Override
    public void unsetup() {
        if (panel != null) {
            this.erdosMetric.setSourceNode(panel.getSelected());
        }
        panel = null;
    }

    @Override
    public Class<? extends Statistics> getStatisticsClass() {
        return ErdosNumberMetric.class;
    }

    @Override
    public String getValue() {
        if (this.erdosMetric != null) {
            DecimalFormat df = new DecimalFormat("###.###");
            return "" + df.format(erdosMetric.getErdosAverage());
        }
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Erdös Number";

    }

    @Override
    public String getCategory() {
        return StatisticsUI.CATEGORY_NETWORK_OVERVIEW;
    }

    @Override
    public int getPosition() {
        return 800;

    }

    @Override
    public String getShortDescription() {
        return "Computes Erdös Number";
    }
}
