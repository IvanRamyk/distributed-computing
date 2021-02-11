package com.main;

import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;



public class BTask extends JFrame{


    private static final AtomicInteger semaphore = new AtomicInteger();
    private static boolean stopFirst = false;
    private static boolean stopSecond = false;
    private static JSlider slider;
    private static JLabel errorLabel;

    private static void showErrorMessage() {
        System.out.println("Error!");

        Thread errorThread = new Thread() {
            @Override
            public void run() {
                errorLabel.setText("Зайнято потоком");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                errorLabel.setText("");
            }
        };
        errorThread.setDaemon(true);
        errorThread.start();
    }

    private static List<JComponent> createButtons() {
        JButton startFirstThread = new JButton("ПУСК1");
        startFirstThread.setBounds(30,150, 100, 50);

        JButton stopFirstThread = new JButton("СТОП1");
        stopFirstThread.setBounds(30,250, 100, 50);

        JButton startSecondThread = new JButton("ПУСК2");
        startSecondThread.setBounds(230,150, 100, 50);

        JButton stopSecondThread = new JButton("СТОП2");
        stopSecondThread.setBounds(230,250, 100, 50);

        startFirstThread.addActionListener(ae -> {
            if (semaphore.compareAndSet(1, 0)) {
                stopFirst = false;
                Thread incThread = new Thread(() -> {
                    while (!stopFirst) {
                        if (slider.getValue() < 90)
                            slider.setValue(slider.getValue() + 1);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                incThread.setDaemon(true);
                incThread.start();
            } else {
                showErrorMessage();
            }
        });

        stopFirstThread.addActionListener(ae -> {
            stopFirst = true;
            semaphore.set(1);
        });

        startSecondThread.addActionListener(ae -> {
            if (semaphore.compareAndSet(1, 0)) {
                stopSecond = false;
                Thread decThread = new Thread(() -> {
                    while (!stopSecond) {
                        if (slider.getValue() > 10)
                            slider.setValue(slider.getValue() - 1);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                decThread.setDaemon(true);
                decThread.start();
            } else {
                showErrorMessage();
            }
        });

        stopSecondThread.addActionListener(ae -> {
            stopSecond = true;
            semaphore.set(1);

        });

        return Arrays.asList(
                startFirstThread,
                stopFirstThread,
                startSecondThread,
                stopSecondThread
        );
    }

    private static void createErrorLabel() {
        errorLabel = new JLabel();
        errorLabel.setText("");
        errorLabel.setBounds(130, 200, 100, 50);
    }


    public static void main(String[] s) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLayout(null);
        frame.setSize(400,400);
        slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider.setBounds(0,0, 300, 100);
        semaphore.set(1);
        frame.add(slider);
        createErrorLabel();
        frame.add(errorLabel);
        for (var button : createButtons()) {
            frame.add(button);
        }
        frame.setVisible(true);


    }
}