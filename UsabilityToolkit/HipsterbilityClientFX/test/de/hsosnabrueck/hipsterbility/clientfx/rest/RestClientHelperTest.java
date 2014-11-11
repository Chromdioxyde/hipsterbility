package de.hsosnabrueck.hipsterbility.clientfx.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hsosnabrueck.hipsterbility.clientfx.Settings;
import de.hsosnabrueck.hipsterbility.clientfx.model.TestSession;
import de.hsosnabrueck.hipsterbility.clientfx.model.files.AudioFile;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class RestClientHelperTest {

    RestClientHelper clientHelper;

    @Before
    public void init(){
        clientHelper = new RestClientHelper();
        Settings settings = new Settings();
        settings.init();
        settings.setServer("localhost");
        settings.setPort(8080);
        settings.setUser("admin");
        settings.setPassword("test123");
        clientHelper.settings = settings;
        try {
            clientHelper.checkLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClientConnection(){
        RestClientHelper client = new RestClientHelper();
//        client.init();

    }

    @Test
    public void testPingServer(){
        RestClientHelper client = new RestClientHelper();
        try {
//            assertTrue(client.pingServer("localhost", 8080));
//            assertFalse(client.pingServer("localhost", 80));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void fileUpload(){
        System.out.println("fileUpload");
        File file = new File("test" + File.separator +"testimg.jpg");
        System.out.println(file.getAbsolutePath());
        try {
            System.out.println(file.getCanonicalPath());
            System.out.println(file.getFreeSpace());
            System.out.println(file.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file.getPath());
        AudioFile audio = new AudioFile();
        audio.setType(AudioFile.Type.MICROPHONE);
        audio.setTimestamp(new Date());
        audio.setFile(file);
        TestSession session = new TestSession();
        session.setName("test");
        try {
            System.out.println(clientHelper.checkLogin());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(session));
            System.out.println(mapper.writeValueAsString(audio));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(Entity.json(session));
        Response response = clientHelper.getTarget().path("sessions").request(MediaType.APPLICATION_JSON).post(Entity.json(session));
        System.out.println(response);
        Response r = clientHelper.getTarget().path("sessions").path("1").path("audios").request().post(Entity.json(audio));
        System.out.println(r);
    }

//    @Test
//    public void testCreateTest(){
//        System.out.println("testCreateTest");
//        de.hsosnabrueck.hipsterbility.clientfx.model.Test test = TestObjectFactory.getTestEntity();
//        Response r = clientHelper.getTarget().path("tests").request().post(Entity.json(test));
//        System.out.println(r);
//    }
//
//    @Test
//    public void createTestWithTasks(){
//        System.out.println("createTestWithTasks");
//        TestEntity test = TestObjectFactory.getTestEntity();
//        test.setTasks(new ArrayList<>());
//        for(int i = 0 ; i<10 ;i++) {
//            TaskEntity taskEntity = TestObjectFactory.getTaskEntity();
//            taskEntity.setOrderNr(i);
//            taskEntity.setTest(test);
//            test.getTasks().add(taskEntity);
//        }
//        Response r = clientHelper.getTarget().path("tests").request().post(Entity.json(test));
//        System.out.println(r);
//    }

}