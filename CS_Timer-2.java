package com.example.chuvak.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 *     Реализует таймер, который вызывает событие через определенные пользователем
 *     интервалы времени.Данный таймер должен использоваться c Activity.
 */
public class CS_Timer {

    /*
    private Timer myTimer = null;

    Context context = null;

    //delay - amount of time in milliseconds before first execution.
    // задержка - количество времени в миллисекундах до первого исполнения.
    long delay = 0;

    //period - amount of time in milliseconds between subsequent executions.
    // период - промежуток времени в миллисекундах между последующего выполнения.
    long period = 0;

    public void onCreate(Context context) {

        this.context = context;

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        },


                delay, period);
    }

    private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        ((Activity)context).runOnUiThread(Timer_Tick);
    }

    public EventHandler Tick = new EventHandler();

    public interface OnTick{

    public void onTick(EventArgs eventArgs);
    }

    OnTick tick;

    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            //This method runs in the same thread as the UI.

            //Do something to the UI thread here

            if (tick != null) tick.onTick(null);

            if (!Tick.isNull())
                Tick.SendEvent(null);
        }
    };
}

ActivityTimerListener listener;



 */
        private Object syncObj = new Object();
        private int interval = 100;
        private volatile boolean enabled = false;
        private EventHandler onTimer = new EventHandler();

        private CS_Timer.TimerNativeWindow timerWindow;
        private Object userData;

        public CS_ActivityTimer.OnTickHandler onTickHandler;

    private Context context = null;

    public CS_Timer(Context context)
    {
        this.context = context;
    }
    public Context getContext() { return context;}






    //     Возвращает или задает признак активности таймера.
    //
    // Возвращает:
    //     Значение true, если данный таймер включен; в противном случае — значение
    //     false.Значение по умолчанию — false.
        public boolean isEnabled()
        {
                if (this.timerWindow == null)
                    return false; //this.enabled;
                else
                    return this.timerWindow.IsTimerRunning();
            }
        public boolean getEnabled()
        {
            return isEnabled();
        }

        public void setEnabled(boolean value)
        {
                synchronized (this.syncObj)
                {
                    if (this.enabled == value)
                        return;

                    this.enabled = value;

                    if (value)
                    {
                        if (this.timerWindow == null)
                            this.timerWindow = new CS_Timer.TimerNativeWindow(this);

                        this.timerWindow.StartTimer(this.interval);
                    }
                    else
                    {
                        if (this.timerWindow != null)
                            this.timerWindow.StopTimer();

                    }
                }

        }



    //     Возвращает или задает время в миллисекундах до вызова события Timer.Tick
    //     относительно момента, когда событие Timer.Tick произошло
    //     последний раз.
    //
    //     При изменении интервала происходит перезапуск таймера
    //
    //                        Максимальное значение - 497 дней :)
    // Возвращает:
    //     Объект System.Int32, задающий количество миллисекунд до вызова события Timer.Tick
    //     относительно момента, когда событие Timer.Tick произошло
    //     последний раз.Значение не может быть меньше единицы.
        public int getInterval()
        {
            return this.interval;
        }
        public void setInterval(int value) throws IllegalArgumentException
        {
                synchronized (this.syncObj)
                {
                    if (value < 1)
                    {
                        throw new IllegalArgumentException("TimerInvalidInterval");
                    }
                    else
                    {
                        if (this.interval == value)
                            return;

                        this.interval = value;

                        if (!this.isEnabled()  || this.timerWindow == null)
                            return;

                        this.timerWindow.RestartTimer(value);
                    }
                }

        }



    //Для совместимости добавлено
    //     Происходит по истечении заданного интервала таймера при условии, что таймер
    //     включен. Чтобы выполнялся метод target.method(new EventArgs())
        public void addTick(Object target, String method, EventArgs e)
        {
            this.onTimer.AddHandlerEventArgs(target, method, e);
        }
    public void removeTick(Object target, String method, EventArgs e)
    {

        this.onTimer.RemoveHandlerEventArgs( target, method, e);

    }

        public CS_Timer()
        {
            this.interval = 100;
        }



    //Очистка перед присвоением экземпляру null, т.е. timer.Dispose(true); timer = null;
    //
    //     Освобождает используемые таймером ресурсы, кроме памяти.
    //
    // Параметры:
    //   disposing:
    //     Значение true для освобождения управляемых и неуправляемых ресурсов.Значение
    //     false, чтобы освободить только неуправляемые ресурсы.
    protected void Dispose(boolean disposing)
    {
        if (disposing)
        {
            if (this.timerWindow != null)
            {
                this.timerWindow.StopTimer();

            }
            this.setEnabled(false);
        }

        if (this.timerWindow != null)
          this.timerWindow.Dispose();

        this.timerWindow = null;
    }

    //     Вызывает событие System.Windows.Forms.Timer.Tick.
    //     Можно использовать только для внеочередного срабатывания функциональности таймера
    //     в гланом потоке
    //
    // Параметры:
    //   e:
    //     Объект System.EventArgs, содержащий данные события.Данный параметр всегда
    //     имеет значение System.EventArgs.Empty.
    protected void OnTick(EventArgs e)
    {
        if (this.onTimer == null)
            return;

        if ((onTimer != null) && (!onTimer.isNull()))
          this.onTimer.SendEvent((Object) this, e);

        if(onTickHandler != null)
        {
            onTickHandler.onTick((Object) this, e);

        }
    }

    //Одиночный запуск - true
    private boolean oneShot = false;
    public void setOneShot(boolean value)
    {
        oneShot = value;
    }
    public boolean getOneShot() { return oneShot;}

    //Интервал задержки перед первым запуском таймера миллисеьунд
    private int delay = 0;
    public void setDelay(int value)
    {
        delay = value;
    }
    public int getDelay() { return delay;}

    //     Запускает таймер.
    public void Start()
    {
        this.setEnabled(true);
    }

    //     Останавливает таймер.
    public void Stop()
    {
        this.setEnabled(false);
    }

    public String ToString()
    {
        return "timer { Id: "+ timerWindow.getTimerId() + ", Delay: " + getDelay() + ", Interval: " +getInterval() + ", Running: " + timerWindow.IsTimerRunning()+"}";
    }

    private static int TimerID = 1;

    //Поддержка таймера
    private class TimerNativeWindow {


        private synchronized int getNextTimerId()
        {
            TimerID++;
            _timerID = TimerID;
            return _timerID;
        }

        private CS_Timer _owner;

        private CS_ActivityTimer m_timer = null;
        public CS_ActivityTimer getTimer() { return m_timer; }

        private int _timerID = 0;
        public int getTimerId() { return _timerID;}

        private boolean _stoppingTimer;

        public boolean IsTimerRunning()
        {
            boolean result = false;
                if (this._owner != null)
                {
                    if (this.m_timer.isEnabled())
                    {
                        if (this.m_timer.isStarting())
                        {
                            if (this.m_timer.isFinished())
                                return false;
                            else
                                return true;
                        }
                        else
                          return false;

                    }
                    else
                      return false;
                }
                else
                    return false;

        }


        TimerNativeWindow(CS_Timer owner) {
            this._owner = owner;

            m_timer = new CS_ActivityTimer();

            if (m_timer == null)
                return;

            if (this._owner.getContext() instanceof Activity )
              m_timer.setActivity((Activity)this._owner.getContext());
        }

        public void Dispose() {
            try {


                this.StopTimer();
                this.m_timer.TickEventHandler = null;
                this.m_timer.onTickHandler = null;
                this.m_timer = null;
                this._owner = null;

            }catch (Exception e)
            {
                System.out.print(e.getMessage());
            }

        }

        public void RestartTimer(int newInterval) {
            this.StopTimer();
            this.StartTimer(newInterval);
        }

        public void StartTimer(int interval) {
            if (this._timerID != 0 || this._stoppingTimer )
                return;

            this._timerID = getNextTimerId();
            m_timer.setDelay(_owner.getDelay());
            m_timer.setFireOnlyOnce(_owner.getOneShot());
            m_timer.setPeriod((long)interval);

            if(_owner.onTickHandler != null)
            {
               m_timer.onTickHandler = new CS_ActivityTimer.OnTickHandler() {
                @Override
                public void onTick(Object object, EventArgs eventArgs) {
                    if(_owner.onTickHandler != null)
                    {
                        _owner.onTickHandler.onTick(object, eventArgs);
                    }
                }
              };
            }

            if ((_owner.onTimer != null) && (!_owner.onTimer.isNull()))
                m_timer.TickEventHandler = _owner.onTimer;

            m_timer.setEnabled(true);

        }



        public void StopTimer() {

            synchronized (this)
            {
                if (this._stoppingTimer)
                    return;

                if (this._timerID != 0)
                {
                    try
                    {
                        this._stoppingTimer = true;
                        m_timer.setEnabled(false);
                    }
                    finally
                    {
                        this._timerID = 0;
                        this._stoppingTimer = false;
                    }
                }
            }
        }


    }

    public class TestTimer {

        CS_Panel panel;
        CS_Label label;
        CS_Button button1;
        CS_Timer timer;
        CS_Button button2;
        CS_Button button3;
        CS_Button button4;
        CS_Button button5;
        CS_Button button6;


        public void Dispose(){

            panel.Dispose();
            label = null;
             button1 = null;
            timer.Dispose(true);
            timer = null;
             button2 = null;
             button3 = null;
             button4 = null;
             button5 = null;
            button6 = null;
        }
        int counter = 0;

        public TestTimer(final Context context, RelativeLayout layout) {
            panel = new CS_Panel(context, layout, 0, 0, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            panel.setSize(new Size(450, 300));
            panel.setBackColor(CS_Color.Control);
            panel.setBorderSize(2);
            panel.setBorderStyle(CS_Panel.BorderStyle.Fixed3D);

            label = new CS_Label(context);
            panel.controls_Add(label);
            label.setLocation(new Point(5, 5));
            label.setSize(new Size(390, 40));


            button1 = new CS_Button(context);
            panel.controls_Add(button1);
            button1.setLocation(new Point(5, 50));
            button1.setSize(new Size(100, 80));
            button1.setText("Одиночный запуск");
            button1.setFont("Arial", 18);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    timer= new CS_Timer(context);
                    timer.setDelay(4000);
                    timer.setInterval(1000);
                    timer.setOneShot(true);
                    label.setText("[Одиночный запуск]");
                    timer.onTickHandler = new CS_ActivityTimer.OnTickHandler() {
                        @Override
                        public void onTick(Object object, EventArgs eventArgs) {
                          label.setText("[Одиночный запуск - ОК]");
                        }
                    };
                    timer.Start();
                }
            });

            button2 = new CS_Button(context);
            panel.controls_Add(button2);
            button2.setLocation(new Point(120, 50));
            button2.setSize(new Size(100, 80));
            button2.setFont("Arial", 18);
            button2.setText("Множественный запуск");

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    counter = 0;
                    timer= new CS_Timer(context);
                    timer.setDelay(4000);
                    timer.setInterval(1000);
                    label.setText("[Множественный запуск]");
                    timer.onTickHandler = new CS_ActivityTimer.OnTickHandler() {
                        @Override
                        public void onTick(Object object, EventArgs eventArgs) {
                            counter++;
                            label.setText("[Множественный запуск]:" + counter);
                        }
                    };
                    timer.Start();
                }
            });

            button3 = new CS_Button(context);
            panel.controls_Add(button3);
            button3.setLocation(new Point(225, 50));
            button3.setFont("Arial", 18);
            button3.setSize(new Size(100, 80));
            button3.setText("Стоп");
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (timer != null)
                    {
                        label.setText("[Остановка - ОК]");
                        timer.Stop();
                    }
                };
            });

            button4 = new CS_Button(context);
            panel.controls_Add(button4);
            button4.setLocation(new Point(5, 110));
            button4.setSize(new Size(200, 80));
            button4.setFont("Arial", 18);
            button4.setText("Перезапуск 2с");
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (timer != null)
                    {

                        label.setText("[Перезапуск 2 сек - ОК]");
                        timer.setInterval(2000);

                    }
                };
            });

            button5 = new CS_Button(context);
            panel.controls_Add(button5);
            button5.setLocation(new Point(210, 110));
            button5.setSize(new Size(200, 80));
            button5.setFont("Arial", 18);
            button5.setText("Удалить Dispose тест");
            button5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (timer != null)
                    {

                        label.setText("[Удаление Dispose - ОК]");
                        Dispose();

                    }
                };
            });

            button6 = new CS_Button(context);
            panel.controls_Add(button6);
            button6.setLocation(new Point(5, 200));
            button6.setSize(new Size(200, 80));
            button6.setFont("Arial", 18);
            button6.setText("Состояние таймера");
            button6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (timer != null)
                    {

                        label.setText(timer.ToString());

                    }
                };
            });
        }
    }

}

