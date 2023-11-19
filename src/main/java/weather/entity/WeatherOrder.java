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
@Table(name = "WEATHER_SENSOR")
public class WeatherOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    public WeatherOrder() {
    }

    public WeatherOrder(String sensorID, String location, Date createdDate) {
        this.sensorID = sensorID;
        this.location = location;
        this.createdDate = createdDate;
    }

    @Id
    @Column(name = "SENSOR_ID")
    private String sensorID;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public String getSensorID() {
        return sensorID;
    }

    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
