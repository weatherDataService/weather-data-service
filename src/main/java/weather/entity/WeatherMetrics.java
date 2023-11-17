package weather.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "WEATHER_METRICS")
public class WeatherMetrics implements Serializable {

    private static final long serialVersionUID = 1L;

    public WeatherMetrics() {
    }

    public WeatherMetrics(String metricsName, String description, Date createdDate) {
        this.metricsName = metricsName;
        this.description = description;
        this.createdDate = createdDate;
    }

    @Id
    @Column(name = "METRICS_NAME")
    private String metricsName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public String getMetricsName() {
        return metricsName;
    }

    public void setMetricsName(String metricsName) {
        this.metricsName = metricsName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
