package org.example.util;


import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

//动态代理工厂
public class ProxyFactory {
    private Object target;
    private FileWriter fileWriter;
    private LocalTime localTime;
    private String proxyName;

    public ProxyFactory(Object target, String proxyName) {
        this.target = target;
        this.proxyName = proxyName;
        try {
            this.fileWriter = new FileWriter("./src/main/java/org/example/logs/log.txt", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getProxy() {

        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                /**
                 * proxy：代理对象
                 * method：代理对象需要实现的⽅法，即其中需要
                 重写的⽅法
                 * args：method所对应⽅法的参数
                 */
                Object result = null;
                String formattedTime = null;
                try {
                    localTime = LocalTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    // 格式化时间
                    formattedTime = localTime.format(formatter);
                    fileWriter.write("[动态代理][" + proxyName + "][⽇志] " + formattedTime + " 完成了" + method.getName() + "，参数：" + Arrays.toString(args) + "\n");
                    result = method.invoke(target, args);
                    fileWriter.write("[动态代理][" + proxyName + "][⽇志] " + formattedTime + " 完成了" + method.getName() + "，结果：" + result + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                    fileWriter.write("[动态代理][" + proxyName + "][⽇志] " + formattedTime + " 完成了" + method.getName() + "，异常：" + e.getMessage() + "\n");
                } finally {
                    fileWriter.write("[动态代理][" + proxyName + "][⽇志] " + formattedTime + " 完成了" + method.getName() + "，⽅法执⾏完毕" + "\n\n");
                    fileWriter.flush();
                }
                return result;
            }
        };
        return Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }
}
