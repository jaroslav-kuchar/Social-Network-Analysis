package cz.cvut.fit.gephi.snametrics.erdosnumber;

import org.gephi.statistics.spi.Statistics;
import org.gephi.statistics.spi.StatisticsBuilder;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Jaroslav Kuchar
 */
@ServiceProvider(service = StatisticsBuilder.class)
public class ErdosNumberMetricBuilder implements StatisticsBuilder {

    @Override
    public String getName() {
        return "Erdös Number";
    }

    @Override
    public Statistics getStatistics() {
        return new ErdosNumberMetric();
    }

    @Override
    public Class<? extends Statistics> getStatisticsClass() {
        return ErdosNumberMetric.class;
    }
}
