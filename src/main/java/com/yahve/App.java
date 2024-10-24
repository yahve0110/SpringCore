package com.yahve;
import com.yahve.listener.OperationsConsoleListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.yahve.config.AppConfig;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        OperationsConsoleListener consoleListener = context.getBean(OperationsConsoleListener.class);
        new Thread(consoleListener).start();

    }
}
