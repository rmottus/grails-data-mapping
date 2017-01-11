package grails.gorm.services

import org.grails.datastore.mapping.core.Datastore
import org.grails.datastore.mapping.services.DefaultServiceRegistry
import org.grails.datastore.mapping.services.ServiceRegistry
import spock.lang.Specification

/**
 * Created by graemerocher on 11/01/2017.
 */
class ServiceTransformSpec extends Specification {
    void "test service transform"() {
        given:
        ServiceRegistry reg = new DefaultServiceRegistry(Mock(Datastore))

        expect:
        org.grails.datastore.mapping.services.Service.isAssignableFrom(TestService)
        reg.getService(TestService) != null
        reg.getService(TestService2) != null
        reg.getService(TestService).datastore != null
        reg.getService(TransactionService) != null
    }
}

@Service
class TestService {
    void doStuff() {
    }
}

@Service
class TestService2 {
    void doStuff() {
    }
}
