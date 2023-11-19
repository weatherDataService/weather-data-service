package weather.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class MetricsAverage implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public MetricsAverage() {
    }

    public MetricsAverage(String sensorID, String metricsName, BigDecimal average) {
        this.sensorID = sensorID;
        this.metricsName = metricsName;
        this.average = average;
    }
    
    private String sensorID;
    
    private String metricsName;
    
    private BigDecimal average;

    public String getSensorID() {
        return sensorID;
    }

    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    public String getMetricsName() {
        return metricsName;
    }

    public void setMetricsName(String metricsName) {
        this.metricsName = metricsName;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }
       
}
