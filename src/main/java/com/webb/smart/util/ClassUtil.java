package com.webb.smart.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import com.webb.smart.mvc.annotation.Controller;
import com.webb.smart.mvc.annotation.Service;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类扫描工具类
 */
public class ClassUtil {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);
    private static final ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
    private static final String scanPackage = Config.getScanPackage();

    public static ClassLoader getCurrentClassLoader() {
        return currentClassLoader;
    }

    /**
     * 根据包名和注解,获取包下的有该注解的类列表
     */
    public static List<Class<?>> getClassListByAnnotation(String packageName, Class<? extends Annotation> annotationClass) {
        List<Class<?>> classList = getClassList(packageName);
        return classList.stream().filter(clazz -> clazz.isAnnotationPresent(annotationClass)).collect(Collectors.toList());
    }

    /**
     * 根据包名,获取包下的类列表
     *
     */
    private static List<Class<?>> getClassList(String packageName) {
        List<Class<?>> classList = new ArrayList<>();
        try {
            // 从包名获取 URL 类型的资源
            Enumeration<URL> urlList = getCurrentClassLoader().getResources(packageName.replace(".", "/"));
            while (urlList.hasMoreElements()) {
                URL url = urlList.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol(); // 获取URL协议名 file,jar
                    if ("file".equals(protocol)) {
                        // 若在 class 目录,添加类
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(classList, packagePath, packageName);
                    }
                    else if ("jar".equals(protocol)) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        JarFile jarFile = jarURLConnection.getJarFile();
                        Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                        while (jarEntryEnumeration.hasMoreElements()) {
                            JarEntry jarEntry = jarEntryEnumeration.nextElement();
                            String jarEntryName = jarEntry.getName();
                            // TODO: 17-4-10
                            /*
                             * if (jarEntryName.endsWith(".class")) { String
                             * className = jarEntryName.substring(0,
                             * jarEntryName.lastIndexOf(".")).replaceAll("/",
                             * "."); Class<?> cls = loadClass(className, false);
                             * classList.add(cls); }
                             */
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error("获取扫描类错误");
        }
        return classList;
    }

    /**
     * 根据 包路径 和 包名称,添加类到类列表(递归扫描)
     */
    private static void addClass(List<Class<?>> classList, String packagePath, String packageName) {
        try {
            // 获取包路径下的所有class文件列表或目录
            File[] fileList = new File(packagePath).listFiles(file -> file.isFile() && file.getName().endsWith(".class") || file.isDirectory());
            if (fileList != null) {
                for (File file : fileList) {
                    String fileName = file.getName();
                    if (file.isFile()) {
                        String className = fileName.substring(0, fileName.lastIndexOf("."));
                        if (StringUtils.isNoneEmpty(className)) {
                            className = packageName + "." + className;
                        }
                        Class<?> clazz = loadClass(className, false);
                        classList.add(clazz);
                    }
                    else {
                        String subPackagePath = fileName;
                        if (StringUtils.isNotEmpty(packagePath)) {
                            subPackagePath = packagePath + "/" + subPackagePath;
                        }
                        String subPackageName = fileName;
                        if (StringUtils.isNotEmpty(packageName)) {
                            subPackageName = packageName + "." + subPackageName;
                        }
                        addClass(classList, subPackagePath, subPackageName); // 递归调用添加类
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error("添加类错误");
        }
    }

    /**
     * 加载类
     */
    public static Class<?> loadClass(String className, Boolean initialize) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className, initialize, getCurrentClassLoader());
        }
        catch (ClassNotFoundException e) {
            logger.error("加载类错误");
            throw new RuntimeException(e);
        }
        return clazz;
    }

    public static List<Class<?>> getBeanClassList() {
        List<Class<?>> beanClassList = new ArrayList<>();
        beanClassList.addAll(getClassListByAnnotation(scanPackage, Controller.class));
        beanClassList.addAll(getClassListByAnnotation(scanPackage, Service.class));
        return beanClassList;
    }
}
