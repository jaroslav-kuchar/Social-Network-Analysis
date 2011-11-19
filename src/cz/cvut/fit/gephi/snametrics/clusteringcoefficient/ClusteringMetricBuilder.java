package cz.cvut.fit.gephi.snametrics.clusteringcoefficient;

import org.gephi.statistics.spi.Statistics;
import org.gephi.statistics.spi.StatisticsBuilder;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jaroslav Kuchar
 */
@ServiceProvider(service = StatisticsBuilder.class)
public class ClusteringMetricBuilder implements StatisticsBuilder {

    @Override
    public String getName() {
        return "Clustering Coefficient";
    }

    @Override
    public Statistics getStatistics() {
        return new ClusteringMetric();
    }

    @Override
    public Class<? extends Statistics> getStatisticsClass() {
        return ClusteringMetric.class;
    }
}
