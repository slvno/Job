package com.example.chuvak.myapplication;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by chuvak on 18.10.2014.
 */
public class EventHandler // extends Delegate
{
    public List<Delegate> handlers =  new ArrayList<Delegate>();

    public void AddHandler(Object target, String method, EventArgs e)
    {
        if (target==null)
            throw new  NullPointerException("AddHandler(Object target, String method, Object... args): target class is null");

        if (method == null)
            throw new  NullPointerException("AddHandler(Object target, String method, Object... args): method string is null");

        try {


            Delegate d = new Delegate(target, method, e != null ? e.args : null);
            handlers.add(d);

        }catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }

    public void AddHandlerEventArgs(Object target, String method, EventArgs e)
    {
        if (target==null)
            throw new  NullPointerException("AddHandler(Object target, String method, Object... args): target class is null");

        if (method == null)
            throw new  NullPointerException("AddHandler(Object target, String method, Object... args): method string is null");

        Delegate d;
        try {



            if (target instanceof Class<?>)
              d = new Delegate((Class<?>)target, method, e != null ? e : null);
            else
              d = new Delegate(target, method, e != null ? e : null);

            handlers.add(d);

        }catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }

    public void AddHandler(Class<?> target, String method, EventArgs e)
    {
        if (target==null)
            throw new  NullPointerException("AddHandler(Class<?> target, String method, Object... args): target class is null");

        if (method == null)
            throw new  NullPointerException("AddHandler(Class<?> target, String method, Object... args): method string is null");

        Delegate d = new Delegate(target, method, e != null ? e.args : null);
        handlers.add(d);
    }

    public void AddHandlerEventArgs(Class<?> target, String method, EventArgs e)
    {
        if (target==null)
            throw new  NullPointerException("AddHandler(Class<?> target, String method, Object... args): target class is null");

        if (method == null)
            throw new  NullPointerException("AddHandler(Class<?> target, String method, Object... args): method string is null");

        Delegate d = new Delegate(target, method, e != null ? e : null);
        handlers.add(d);
    }
    public void RemoveHandler(Class<?> target, String method, EventArgs e)
    {
        for(Delegate delegate: handlers)
            delegate.Free(target, method, e.args);
    }

    public void RemoveHandler(Object target, String method, EventArgs e)
    {
        for(Delegate delegate: handlers)
            delegate.Free(target, method, e.args);
    }

    public void RemoveAllHandlers()
    {
        try {
            for (Delegate delegate : handlers)
                delegate.freeDelegate();
        }
        catch (Exception e) {
        e.printStackTrace();
        }
        try {
            handlers.clear();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void RemoveHandlerEventArgs(Class<?> target, String method, EventArgs e)
    {
        if (target==null)
            throw new  NullPointerException("RemoveHandlerEventArgs(Class<?> target, String method, ): target class is null");

        if (method == null)
            throw new  NullPointerException("RemoveHandlerEventArgs(Class<?> target, String method, ): method string is null");


        if ((handlers == null) || (handlers.size() == 0))
            return;

        try {
            for (Delegate delegate : handlers)
                delegate.Free(target, method, e);
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
    }

    public void RemoveHandlerEventArgs(Object target, String method, EventArgs e)
    {
        if (target==null)
            throw new  NullPointerException("RemoveHandlerEventArgs(Object target, String method, ): target class is null");

        if (method == null)
            throw new  NullPointerException("RemoveHandlerEventArgs(Object target, String method, ): method string is null");


        if ((handlers == null) || (handlers.size() == 0))
            return;

        try {
            for (Delegate delegate : handlers)
                delegate.Free(target, method, e);
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
    }

    boolean isNull() {
        if (handlers == null)
            return true;

        return handlers.size() == 0;
    }

    public void SendEvent(Object ... args)
    {
        try {

            for (Delegate delegate : handlers)
            {
                try {
                    delegate.Run(args);
                }
                catch (Exception e)
                {
                    System.out.print("\nEventHandler.SendEvent:\n\n");
                    e.printStackTrace(System.out);
                    System.out.print("\n----------------------\n\n");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
    }

    public Object SendOneFirstEventAndReturnResult(Object ... args)
    {
        try {

            for (Delegate delegate : handlers)
                return delegate.Run(args);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }

        return null;
    }
}
