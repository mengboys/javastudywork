import org.example.Spring.AnnotationApplicationContext;
import org.example.Spring.ApplicationContext;
import org.example.dao.UserDao;
import org.example.dao.impl.UserDaoImpl;
import org.example.service.Impl.UserServiceImpl;
import org.example.util.ProxyFactory;
import org.junit.jupiter.api.Test;
import org.example.service.UserService;


public class SpringIocTest {
    @Test
    public void testIoc() {
        ApplicationContext applicationContext = new AnnotationApplicationContext("org.example");
        UserService userService = (UserService) applicationContext.getBean(UserService.class);
        userService.out();
        System.out.println("run success");
    }


//    @Test
//    public void testUser(){
//        ApplicationContext applicationContext = new AnnotationApplicationContext("org.example");
//        UserService userService = (UserService) applicationContext.getBean(UserService.class,"UserService");
//        ProxyFactory factory = new ProxyFactory(userService,"UserService");
//        UserService proxy = (UserService) factory.getProxy();
//        proxy.selectById(1);
//    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}

