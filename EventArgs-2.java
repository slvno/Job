package com.example.chuvak.myapplication;

/**
 * Created by chuvak on 18.10.2014.
 */
public class EventArgs {
        // Сводка:
        //     System.EventArgs — это базовый класс для классов, содержащих данные о событии.
    public Object[] args = null;
    // Сводка:
    //     Инициализирует новый экземпляр класса System.EventArgs.
    public EventArgs() {}
    public EventArgs(Object... args)
    {
        if (args != null)
        {
            if (args.length > 0) {
                this.args = new Object[args.length];

                for (int i = 0; i < args.length; i++) {
                    this.args[i] = args[i];
                }
            }
        }
    }

    public int length()
    {
        if ((args != null) && (args.length > 0))
            return args.length;

        return 0;
    }


    public void Dispose()
    {
      if ((args != null) && (args.length > 0))
      {
        for (int i = 0; i < args.length; i++)
        {
            args[i] = null;
        }

          args = null;
      }
    }

    public void getEventArgs(EventArgs eventArgs) {
        if ((eventArgs != null) && (eventArgs.args != null))
        {
            if (eventArgs.args.length > 0) {
                this.args = new Object[eventArgs.args.length];

                for (int i = 0; i < eventArgs.args.length; i++) {
                    this.args[i] = eventArgs.args[i];
                }
            }
        }
    }

            // Сводка:
            //     Представляет событие, не содержащее данных.
    public static EventArgs Empty()
    {
        return emptyEventArgs;
    }

    private static final EventArgs emptyEventArgs = new EventArgs();


}
