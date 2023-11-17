package weather.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class MetricsStatistic implements Serializable {

    private static final long serialVersionUID = 1L;

    public MetricsStatistic() {
    }

    public MetricsStatistic(String sensorID, String metricsName, BigDecimal minValue, BigDecimal maxValue, BigDecimal sum, BigDecimal average) {
        this.sensorID = sensorID;
        this.metricsName = metricsName;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.sum = sum;
        this.average = average;
    }

    private String sensorID;
    private String metricsName;
    private BigDecimal minValue;
    private BigDecimal maxValue;
    private BigDecimal sum;
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

    public BigDecimal getMinValue() {
        return minValue;
    }

    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }

    public BigDecimal getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }

}
