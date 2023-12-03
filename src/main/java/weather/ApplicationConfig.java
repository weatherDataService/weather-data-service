package weather;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(weather.GenericExceptionMapper.class);
        resources.add(weather.JsonProcessingExceptionMapper.class);
        resources.add(weather.ValidationExceptionMapper.class);
        resources.add(weather.api.weatherdata.Add.class);
        resources.add(weather.api.weatherdata.Find.class);
        resources.add(weather.api.metricsaverage.Find.class);
        resources.add(weather.api.metricsstatistic.Find.class);
        resources.add(weather.api.metricsstatistic.FindAll.class);
        return resources;
    }

}
