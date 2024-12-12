package com.example.chuvak.myapplication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by chuvak on 18.10.2014.
 */
public class Delegate
{

            public  Class<?>[] parameterTypes;
            public int parametersLenght;
            public Object[] parameterObjects;

            public Object classObject;
            public  Class<?> staticClass;
            public String methodName;



            public Delegate()
            {
                parametersLenght=0;
                parameterTypes = null;
                classObject = null;
                methodName = null;
                parameterObjects = null;
                staticClass = null;
            }

            public void freeDelegate()
            {
                    parametersLenght=0;
                    parameterTypes = null;
                    classObject = null;
                    methodName = null;
                    parameterObjects = null;
                    staticClass = null;
            }

            public void LoadParameters(Object... args)
            {
                if (args != null)
                {
                 if (args.length>0)
                   {
                       parameterTypes = new Class<?>[args.length];
                       parametersLenght = args.length;

                       for (int i=0; i <parametersLenght; i++) {
                           Class<?> c = args[i].getClass();

                           if (c!=null) {
                              //c = args[i].getClass();
                              parameterTypes[i] = c;
                             }
                           else
                           {
                               System.out.println("Object of args[i"+i+"] is null");
                               throw new  NullPointerException("Object of args[i"+i+"] is null");
                           }


                           }

                       return;
                   }

                }
                parameterTypes = null;
                parametersLenght=0;
            }

    public boolean CheckParameters(Object... args)
    {
        if (args == null)
        {
            if(parametersLenght == 0)
                return true;

            return false;
        }

        if (args.length==0)
        {
            if(parametersLenght == 0)
                return true;

            return false;
        }

        if (parametersLenght == 0)
            return false;

        if (parametersLenght != args.length)
            return false;

        if ((args.length>0) && (parametersLenght > 0) && (args.length == parametersLenght))
        {
                for (int i=0; i <parametersLenght; i++)
                {
                    Class<?> c = args[i].getClass();

                    if (c!=null) {
                        if (! parameterTypes[i].isInstance(args[i]))
                           return false;
                    }
                    else
                        throw new  NullPointerException("Delegate.CheckParameters: Object of args[i"+i+"] is null");


                }
            return true;


        }
        return false;
    }

    public Object run_object_class_method(Object class_object, String method_name, Object... args)
    {
        Class<?> c;
        Method method;
        Object object_result = null;

        if (class_object == null)
            throw new  NullPointerException("Delegate.run_object_class_method: class_object is null");

        if (method_name == null)
            throw new  NullPointerException("Delegate.run_object_class_method: method_name is null");

        if (!CheckParameters(args))
            return null;

        try {
            c = class_object.getClass();
            if (parametersLenght == 0)
              method = c.getMethod(method_name);//,  parameterTypes);
            else
            {
                Class[] pTypes = new Class[parametersLenght];
                for (int i=0; i <parametersLenght; i++) {
                    pTypes[i] = parameterTypes[i];
                }
                    method = c.getMethod(method_name,  /*parameterTypes*/pTypes);
            }

        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace(System.out);
            return null;
        }

        if (method == null)
            return null;

        method.setAccessible(true);
        try
        {
            object_result = method.invoke(class_object, args);
        }
        catch (Exception e)
        {
            System.out.print("\nMethod :"+method_name+"\n");
            if (e instanceof NullPointerException)
            {

            }
            if (e instanceof IllegalAccessException)
            {

            }
            else if (e instanceof IllegalArgumentException)
            {

            }
            else if (e instanceof InvocationTargetException)
            {

            }
            e.printStackTrace(System.out);
            return null;
        }
        return object_result;
    }

    public Object run_static_class_method(Class<?> static_class, String method_name, Object... args)
    {
        Class<?> c;
        Method method;
        Object object_result = null;

        if (static_class == null)
            throw new  NullPointerException("Delegate.run_static_class_method: static_class is null");

        if (method_name == null)
            throw new  NullPointerException("Delegate.run_static_class_method: method_name is null");

        if (!CheckParameters(args))
            return null;

        try {
            c = static_class;
            //method = c.getMethod(method_name);//,  parameterTypes);
            if (parametersLenght == 0)
                method = c.getMethod(method_name);//,  parameterTypes);
            else
                method = c.getMethod(method_name,  parameterTypes);

        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace(System.out);
            return null;
        }

        if (method == null)
            return null;

        method.setAccessible(true);
        try {
            object_result = method.invoke(null, args);
        }
        catch (Exception e)
        {
            if (e instanceof NullPointerException)
            {

            }
            if (e instanceof IllegalAccessException)
            {

            }
            else if (e instanceof IllegalArgumentException)
            {

            }
            else if (e instanceof InvocationTargetException)
            {

            }
            e.printStackTrace(System.out);
            return null;
        }
        return object_result;
    }

    // Сводка:
    //     Инициализирует делегат, вызывающий заданный метод экземпляра указанного класса.
    //
    // Параметры:
    //   target:
    //     Экземпляр класса, метод method которого вызывает делегат.
    //
    //   method:
    //     Имя представленного делегатом метода экземпляра.
    //
    //   args:
    //     Список параметров метода
    // Исключения:
    //   System.ArgumentNullException:
    //     target имеет значение null;— или — method имеет значение null;
    //
    //   System.ArgumentException:
    //     Произошла ошибка связывания с целевым методом.

    public Delegate(Object target, String method, Object... args)
    {
        loadObject(target, method, args);
    }

    private void loadObject(Object target, String method, Object... args)
    {
        if (target==null)
            throw new  NullPointerException("Delegate(Object target, String method, Object... args): target object is null");

        if (method == null)
            throw new  NullPointerException("Delegate(Object target, String method, Object... args): method string is null");

        classObject = target;
        staticClass =  null;

        methodName = new String(method);

        LoadParameters(args);
        parameterObjects = null;
        if (parametersLenght > 0) {
            parameterObjects = new Object[parametersLenght];
            for (int i = 0; i < parametersLenght; i++)
                parameterObjects[i] = args[i];
        }

    }

    private void freeObject(Object target, String method, Object... args)
    {
        if (target==null)
            throw new  NullPointerException("freeObject(Object target, String method, Object... args): target object is null");

        if (method == null)
            throw new  NullPointerException("freeObject(Object target, String method, Object... args): method string is null");

        if( classObject.getClass().isInstance( target) && (staticClass == null) && methodName.equals(method) &&
                CheckParameters(args) )
            freeDelegate();
    }

    private void freeStatic(Class<?> target, String method, Object... args)
    {
        if (target==null)
            throw new  NullPointerException("freeStatic(Object target, String method, Object... args): target object is null");

        if (method == null)
            throw new  NullPointerException("freeStatic(Object target, String method, Object... args): method string is null");

        if( (classObject == null) && (staticClass == target) && methodName.equals(method) &&
                CheckParameters(args) )
            freeDelegate();
    }

    //Очистка содержимого
    public void Free()
    {
        if ((classObject == null) && (staticClass == null))
            return;
        else if (classObject != null)
            freeObject(classObject, methodName, parameterObjects);
        else if (staticClass != null)
            freeStatic(staticClass, methodName, parameterObjects);
    }

    public void Free(Class<?> target, String method, Object... args)
    {
        if ((classObject == null) && (staticClass == null))
            return;
        else if (staticClass != null)
            freeStatic(target, method, args);
    }

    public void Free(Object target, String method, Object... args)
    {
        if ((classObject == null) && (staticClass == null))
            return;
        else if (classObject != null)
            freeObject(target, method, args);
    }

    public Delegate(Class<?> target, String method, Object... args)
    {
        loadStatic(target, method, args);
    }

    private void loadStatic(Class<?> target, String method, Object... args)
    {
        if (target==null)
            throw new  NullPointerException("Delegate(Class<?> target, String method, Object... args): target class is null");

        if (method == null)
            throw new  NullPointerException("Delegate(Class<?> target, String method, Object... args): method string is null");

        classObject = null;
        staticClass = target;

        methodName = new String(method);

        LoadParameters(args);
        parameterObjects = null;
        if (parametersLenght > 0) {
            parameterObjects = new Object[parametersLenght];
            for (int i = 0; i < parametersLenght; i++)
                parameterObjects[i] = args[i];
        }

    }

    private Object run_object()
    {
        return run_object_class_method(classObject, methodName, parameterObjects);
    }

    private Object run_static()
    {
        return run_static_class_method(staticClass, methodName, parameterObjects);
    }

    public Object Run()
    {
         if ((classObject == null) && (staticClass == null))
             return null;
         else if (classObject != null)
            return run_object();
         else if (staticClass != null)
             return run_static();

        return null;
    }

    public Object Run(Object... args)
    {
        parameterObjects = null;
        if (parametersLenght > 0) {
            parameterObjects = new Object[parametersLenght];
            for (int i = 0; i < parametersLenght; i++)
                parameterObjects[i] = args[i];
        }

        return Run();
    }

}
