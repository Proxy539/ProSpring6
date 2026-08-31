package com.apress.prospring6.eighteen.audit;

import com.apress.prospring6.eighteen.entities.Singer;
import com.apress.prospring6.eighteen.services.SingerService;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ManagedResource(description = "JMX managed resource",
objectName = "jmxDemo:name=ProSpring6SingerApp")
public class AppStatisticsImpl implements AppStatistics {

    private final SingerService singerService;

    public AppStatisticsImpl(SingerService singerService) {
        this.singerService = singerService;
    }

    @Override
    @ManagedAttribute(description = "Number of singers in the application")
    public int getTotalSingerCount() {
        return singerService.findAll().size();
    }

    @Override
    @ManagedOperation
    public String findJohn() {
        List<Singer> singers = singerService.findByFirstNameAndLastName("John", "Mayer");
        if (!singers.isEmpty()) {
            return singers.get(0).getFirstName() + " " + singers.get(0).getLastName() +
                    " " + singers.get(0).getBirthDate();
        }
        return "not found";
    }

    @Override
    @ManagedOperation(description = "Find Singer by first name and last name")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "firstName", description = "Singer's first name"),
            @ManagedOperationParameter(name = "lastName", description = "Singer's last name")
    })
    public String findSinger(String firstName, String lastName) {
        List<Singer> singers = singerService.findByFirstNameAndLastName(firstName, lastName);
        if (!singers.isEmpty()) {
            return singers.get(0).getFirstName() + " " + singers.get(0).getLastName() +
                    " " + singers.get(0).getBirthDate();
        }
        return "not found";
    }
}
