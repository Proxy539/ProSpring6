package com.apress.prospring6.eighteen.audit;

import jakarta.annotation.PostConstruct;
import org.hibernate.SessionFactory;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(description = "JMX managed resource", objectName = "jmxDemo:name=ProSpring6SingerApp-hibernate")
public class CustomHibernateStatistics {

    private final SessionFactory sessionFactory;
    private Statistics stats;

    public CustomHibernateStatistics(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @PostConstruct
    private void init() {
        stats = sessionFactory.getStatistics();
    }

    @ManagedOperation(description = "Get statistics for entity name")
    @ManagedOperationParameter(name = "entityName", description = "Full class name for the entity")
    public EntityStatistics getEntityStatistics(String entityName) {
        return stats.getEntityStatistics(entityName);
    }

    @ManagedAttribute
    public long getEntityDeleteCount() {
        return stats.getEntityDeleteCount();
    }

    @ManagedAttribute
    public long getEntityInsertCount() {
        return stats.getEntityInsertCount();
    }

    @ManagedAttribute
    public String[] getEntityNames() {
        return stats.getEntityNames();
    }

    @ManagedAttribute
    public String[] getQueries() {
        return stats.getQueries();
    }

    @ManagedAttribute
    public long getTransactionCount() {
        return stats.getTransactionCount();
    }

    @ManagedAttribute
    public long getPrepareStatementCount() {
        return stats.getPrepareStatementCount();
    }
}
