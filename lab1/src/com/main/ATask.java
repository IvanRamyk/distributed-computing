package com.main;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

class SliderThread extends Thread {

    boolean isIncrement;
    final JSlider slider;

    SliderThread(boolean isIncrement, JSlider slider) {
        this.isIncrement = isIncrement;
        this.slider = slider;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (slider) {
                if (isIncrement && slider.getValue() < 90) {
                    slider.setValue(slider.getValue() + 1);
                } else if (!isIncrement && slider.getValue() > 10) {
                    slider.setValue(slider.getValue() - 1);
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


public class ATask extends JFrame{


    static int firstThreadPriority = 5;
    static int secondThreadPriority = 5;


    private static List<JComponent> createButtons(SliderThread thread1, SliderThread thread2) {
        JButton incrementFirstThreadPriority = new JButton("+");
        incrementFirstThreadPriority.setBounds(30,150, 50, 50);



        JLabel labelFirst = new JLabel();
        labelFirst.setText(thread1.getPriority() + "");
        labelFirst.setBounds(50, 200, 50, 50);


        JLabel labelSecond = new JLabel();
        labelSecond.setText(thread2.getPriority() + "");
        labelSecond.setBounds(250, 200, 50, 50);

        incrementFirstThreadPriority.addActionListener(ae -> {
            if (firstThreadPriority < 10)
                firstThreadPriority++;
            thread1.setPriority(firstThreadPriority);
            labelFirst.setText(thread1.getPriority() + "");
        });



        JButton decrementFirstThreadPriority = new JButton("-");
        decrementFirstThreadPriority.setBounds(30,250, 50, 50);
        decrementFirstThreadPriority.addActionListener(ae -> {
            if (firstThreadPriority > 5)
                firstThreadPriority--;
            thread1.setPriority(firstThreadPriority);
            labelFirst.setText(thread1.getPriority() + "");
        });

        JButton incrementSecondThreadPriority = new JButton("+");
        incrementSecondThreadPriority.setBounds(230,150, 50, 50);
        incrementSecondThreadPriority.addActionListener(ae -> {
            if (secondThreadPriority < 10)
                secondThreadPriority++;
            thread2.setPriority(secondThreadPriority);
            labelSecond.setText(thread2.getPriority() + "");
        });




        JButton decrementSecondThreadPriority = new JButton("-");
        decrementSecondThreadPriority.setBounds(230,250, 50, 50);
        decrementSecondThreadPriority.addActionListener(ae -> {
            if (secondThreadPriority > 5)
                secondThreadPriority--;
            thread2.setPriority(secondThreadPriority);
            labelSecond.setText(thread2.getPriority() + "");

        });

        return Arrays.asList(
                incrementFirstThreadPriority,
                decrementFirstThreadPriority,
                incrementSecondThreadPriority,
                decrementSecondThreadPriority,
                labelFirst,
                labelSecond
        );
    }


    public static void main(String s[]) {

        JFrame frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLayout(null);
        frame.setSize(400,400);
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider.setBounds(0,0, 300, 100);

        SliderThread thread1 = new SliderThread(true, slider);
        SliderThread thread2 = new SliderThread(false, slider);
        thread1.setDaemon(true);
        thread1.setDaemon(true);
        thread1.start();
        thread2.start();

        frame.add(slider);
        for (var button : createButtons(thread1, thread2)) {
            frame.add(button);
        }
        frame.setVisible(true);
    }
}