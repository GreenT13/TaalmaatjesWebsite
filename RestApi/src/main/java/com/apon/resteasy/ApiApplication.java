package com.apon.resteasy;

import com.apon.service.IService;
import org.reflections.Reflections;

import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ApiApplication extends Application {
    private Set<Object> singletons = new HashSet();
    private Set<Class<?>> classes = new HashSet();

    public ApiApplication() {
        // Add instance inside the constructor, so it will be deleted after constructing.
        Reflections reflections = new Reflections("com.apon");

        // Add all the services to the singletons.
        // Any service must have @Path, otherwise it won't be registered.
        addInstanceToSetWithAnnotation(reflections, Path.class);

        // Add all the filters to the classes
        addClassToSetWithAnnotation(reflections, Filter.class);
        addClassToSetWithAnnotation(reflections, EH.class);
    }

    /**
     * Add instances to 'singletons' which have a certain annotation.
     * @param reflections Used to search the classes.
     * @param annotation The annotation needed.
     */
    @SuppressWarnings("unused")
    private void addInstanceToSetWithAnnotation(Reflections reflections,
                                                Class<? extends java.lang.annotation.Annotation> annotation) {
        for (Class<?> myClass : reflections.getTypesAnnotatedWith(annotation)) {
            try {
                singletons.add(myClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    /**
     * Add instances to 'singletons' which extend some class.
     * @param reflections Used to search the classes.
     * @param myClass The class to extend.
     */
    private void addInstanceToSetOfSubType(Reflections reflections, Class myClass) {
        singletons.addAll(reflections.getSubTypesOf(myClass));
    }

    /**
     * Add classes to 'classes' which have a certain annotation.
     * @param reflections Used to search the classes.
     * @param annotation The annotation needed.
     */
    private void addClassToSetWithAnnotation(Reflections reflections, Class<? extends java.lang.annotation.Annotation> annotation) {
        classes.addAll(reflections.getTypesAnnotatedWith(annotation));
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
