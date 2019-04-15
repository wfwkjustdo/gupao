package com.wufeng.spring.DI.springframework.beans.support;

import com.wufeng.spring.DI.springframework.beans.config.MyBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Auther: ninghu
 * @Description: 用对配置文件进行查找，读取、解析
 */
public class MyBeanDefinitionReader<loadBeanDefinitions> {

    private List<String> registyBeanClasses = new ArrayList();
    private Properties config = new Properties();

    private final String SCAN_PACKAGE = "scanPackage";


    public MyBeanDefinitionReader(String... locations) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:", ""));
        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    /**
     * @Description: 解析所有class文件
     * @author ninghu
     */
    private void doScanner(String scanPackage) {
        //转换为文件路径，实际上就是把.替换为/就 OK 了
        URL url = this.getClass().getClassLoader().getResource("/" +
                scanPackage.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) { continue; }
                String className = scanPackage + "." + file.getName().replace(".class", "");
                registyBeanClasses.add(className);
            }
        }
    }

    public Properties getConfig() {
        return this.config;
    }

    /**
     * @Description: 把配置文件中扫描到的所有的配置信息转换为 GPBeanDefinition 对象，以便于之后 IOC 操作方便
     * @author ninghu
     */
    public List<MyBeanDefinition> loadBeanDefinitions() {
        List<MyBeanDefinition> result = new ArrayList<MyBeanDefinition>();
        try {
            for (String registyBeanClass : registyBeanClasses) {
                Class<?> beanClass = Class.forName(registyBeanClass);
                if(beanClass.isInterface()) { continue; }
                result.add(doCreateBeanDefinition(toLowerFirstCase(beanClass.getSimpleName()),beanClass.getName()));
                Class<?> [] interfaces = beanClass.getInterfaces();
                for (Class<?> i : interfaces) {
                    result.add(doCreateBeanDefinition(i.getName(),beanClass.getName()));
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Description: 把每一个配信息解析成一个 BeanDefinition
     * @author ninghu
     */
    private MyBeanDefinition doCreateBeanDefinition(String factoryBeanName,String beanClassName) {
        MyBeanDefinition beanDefinition = new MyBeanDefinition();
        beanDefinition.setFactoryBeanName(factoryBeanName);
        beanDefinition.setBeanClassName(beanClassName);

        return beanDefinition;
    }

    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        //之所以加，是因为大小写字母的 ASCII 码相差 32，
        // 而且大写字母的 ASCII 码要小于小写字母的 ASCII 码
        //在 Java 中，对 char 做算学运算，实际上就是对 ASCII 码做算学运算
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
