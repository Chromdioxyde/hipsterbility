package de.hsosnabrueck.hipsterbility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hsosnabrueck.hipsterbility.entities.*;
import de.hsosnabrueck.hipsterbility.persistence.TestObjectFactory;
import de.hsosnabrueck.hipsterbility.persistence.TestSessionDao;
import de.hsosnabrueck.hipsterbility.persistence.TodoDao;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Albert on 29.09.2014.
 */
public class JsonTest {

    @Test
    public void testJson() throws JsonProcessingException {
//        System.out.println(new ObjectMapper().writeValueAsString(TestObjectFactory.getDeviceEntity()));
//        System.out.println(new ObjectMapper().writeValueAsString(TestObjectFactory.getUserEntity()));

        UserEntity u = TestObjectFactory.getUserEntity();
        TestSessionEntity s = TestObjectFactory.getTestSessionEntity();
        DeviceEntity d = TestObjectFactory.getDeviceEntity();
        List<TestSessionEntity> l = new ArrayList<TestSessionEntity>();
        TestEntity t = TestObjectFactory.getTestEntity();
        TaskEntity t1 = TestObjectFactory.getTaskEntity();
        ArrayList<TaskEntity> ts = new ArrayList<>();
        ts.add(t1);
        t.setTasks(ts);
        l.add(s);
        u.setSessions(l);
        s.setTester(u);
        ObjectMapper m = new ObjectMapper();
//        System.out.println(m.writeValueAsString(u));
//        System.out.println(m.writeValueAsString(s));
//        System.out.println(m.writeValueAsString(d));
        System.out.println(m.writeValueAsString(t));
    }
}
