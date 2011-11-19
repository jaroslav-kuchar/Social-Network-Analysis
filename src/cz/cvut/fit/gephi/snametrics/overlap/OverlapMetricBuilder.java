package cz.cvut.fit.gephi.snametrics.overlap;

import org.gephi.statistics.spi.Statistics;
import org.gephi.statistics.spi.StatisticsBuilder;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jaroslav Kuchar
 */
@ServiceProvider(service = StatisticsBuilder.class)
public class OverlapMetricBuilder implements StatisticsBuilder {

    @Override
    public String getName() {
        return "Neighborhood Overlap, Embeddedness";
    }

    @Override
    public Statistics getStatistics() {
        return new OverlapMetric();
    }

    @Override
    public Class<? extends Statistics> getStatisticsClass() {
        return OverlapMetric.class;
    }
}
