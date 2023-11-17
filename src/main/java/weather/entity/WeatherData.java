package weather.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "WEATHER_DATA")
public class WeatherData implements Serializable {

    private static final long serialVersionUID = 1L;

    public WeatherData() {
    }

    public WeatherData(String sensorID, String metricsName, BigDecimal metricsValue, Date createdDate) {
        this.sensorID = sensorID;
        this.metricsName = metricsName;
        this.metricsValue = metricsValue;
        this.createdDate = createdDate;
    }

    @SequenceGenerator(name = "WEATHER_DATA_SEQUENCE_GENERATOR", sequenceName = "SEQ_WEATHER_DATA_ID", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WEATHER_DATA_SEQUENCE_GENERATOR")
    @Column(name = "DATA_ID")
    private Long dataID;

    @Column(name = "SENSOR_ID")
    private String sensorID;

    @Column(name = "METRICS_NAME")
    private String metricsName;

    @Column(name = "METRICS_VALUE")
    private BigDecimal metricsValue;

    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public Long getDataID() {
        return dataID;
    }

    public void setDataID(Long dataID) {
        this.dataID = dataID;
    }

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

    public BigDecimal getMetricsValue() {
        return metricsValue;
    }

    public void setMetricsValue(BigDecimal metricsValue) {
        this.metricsValue = metricsValue;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
