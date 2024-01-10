package org.example.Spring;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class AnnotationApplicationContext implements ApplicationContext {
    //存储bean的容器
    private HashMap<Class, Object> beanFactory = new HashMap<>();
    private static String rootPath;

    @Override
    public Object getBean(Class clazz) {
        return beanFactory.get(clazz);
    }

    public AnnotationApplicationContext(String basePackage) {
        try {
            String packageDirName = basePackage.replaceAll("\\.", "\\\\");
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String filePath = URLDecoder.decode(url.getFile(), "utf-8");
                rootPath = filePath.substring(0, filePath.length() - packageDirName.length());
                loadBean(new File(filePath));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //依赖注⼊
        loadDi();
    }

    private void loadBean(File fileParent) {
        if (fileParent.isDirectory()) {
            File[] childrenFiles = fileParent.listFiles();
            if (childrenFiles == null || childrenFiles.length == 0) {
                return;
            }
            for (File child : childrenFiles) {
                if (child.isDirectory()) {
                    //如果是个⽂件夹就继续调⽤该⽅法,使⽤了递归
                    loadBean(child);
                } else {
                    //通过⽂件路径转变成全类名,第⼀步把绝对路径部分去掉
                    String pathWithClass = child.getAbsolutePath().substring(rootPath.length() - 1);
                    //选中class⽂件
                    if (pathWithClass.contains(".class")) {
                        // com.xinzhi.dao.UserDao
                        //去掉.class后缀，并且把 \ 替换成 .
                        String fullName = pathWithClass.replaceAll("\\\\", ".").replace(".class", "");
                        try {
                            Class<?> aClass = Class.forName(fullName);
                            //把⾮接⼝的类实例化放在map中
                            if (!aClass.isInterface()) {
                                Bean_DreamBo annotation = aClass.getAnnotation(Bean_DreamBo.class);
                                if (annotation != null) {
                                    Object instance = aClass.newInstance();
                                    //判断⼀下有没有接⼝

                                    if (aClass.getInterfaces().length > 0) {
                                        //如果有接⼝把接⼝的class当成key，实例对象当成value
//                                        System.out.println("正在加载【" + aClass.getInterfaces()[0] + "】,实例对象是：" + instance.getClass().getName());
                                        beanFactory.put(aClass.getInterfaces()[0], instance);
                                    } else {
                                        //如果有接⼝把⾃⼰的class当成key，实例对象当成value
//                                        System.out.println("正在加载【" + aClass.getName() + "】,实例对象是：" + instance.getClass().getName());
                                        beanFactory.put(aClass, instance);
                                    }
                                }
                            }
                        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void loadDi() {
        for (Map.Entry<Class, Object> entry : beanFactory.entrySet()) {
            //就是咱们放在容器的对象
            Object obj = entry.getValue();
            Class<?> aClass = obj.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field field : declaredFields) {
                Di_DreamBo annotation = field.getAnnotation(Di_DreamBo.class);
                if (annotation != null) {
                    field.setAccessible(true);
                    try {
//                        System.out.println("正在给【" + obj.getClass().getName() + "】属性【" + field.getName() + "】注⼊值【" + beanFactory.get(field.getType()).getClass().getName() + "】");
                        field.set(obj, beanFactory.get(field.getType()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
