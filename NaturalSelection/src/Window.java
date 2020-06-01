
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame {

    protected static int height = 1080; //Высота всего окна
    protected static int width =  1920; //Ширина всего окна
    protected static int panelHeight = 1080; //Высота области отображения
    protected static int panelWidth = 1700; //Щирина области отображения
    protected Flock panel = new Flock();

    public Window() {

        //Settings
        this.setTitle("NS");
        this.setSize(Window.width, Window.height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        //Вставить меню
        Box box = Box.createVerticalBox();

        //Первый слайдер
        box.add(Box.createVerticalStrut(50));
        JLabel sepLabel = new JLabel("Разделительный вес");
        sepLabel.setAlignmentX(CENTER_ALIGNMENT);
        JSlider sepSlider = new JSlider();
        sepSlider.setMaximum(100);
        sepSlider.setMinimum(0);
        sepSlider.setValue(50);
        sepSlider.setPaintTicks(true);
        sepSlider.setPaintLabels(true);
        sepSlider.setMinorTickSpacing(10);
        sepSlider.setMajorTickSpacing(20);
        sepSlider.addChangeListener(new ChangeListener() {
            public void stateChanged (ChangeEvent event) {
                Flock.setSeparationWeight(((JSlider)event.getSource()).getValue() / 50.0 * Flock.separationWeightIni);
            }
        });
        box.add(sepLabel);
        box.add(sepSlider);

        //Второй слайдер
        box.add(Box.createVerticalStrut(50));
        JLabel cohLabel = new JLabel("Вес сцепления");
        cohLabel.setAlignmentX(CENTER_ALIGNMENT);
        JSlider cohSlider = new JSlider();
        cohSlider.setMaximum(100);
        cohSlider.setMinimum(0);
        cohSlider.setValue(50);
        cohSlider.setPaintTicks(true);
        cohSlider.setPaintLabels(true);
        cohSlider.setMinorTickSpacing(10);
        cohSlider.setMajorTickSpacing(20);
        cohSlider.addChangeListener(new ChangeListener() {
            public void stateChanged (ChangeEvent event) {
                Flock.setCohesionWeight(((JSlider)event.getSource()).getValue() / 50.0 * Flock.cohesionWeightIni);
            }
        });
        box.add(cohLabel, BorderLayout.NORTH);
        box.add(cohSlider, BorderLayout.NORTH);

        //Третий слайдер
        box.add(Box.createVerticalStrut(50));
        JLabel aliLabel = new JLabel("Вес выравнивания");
        aliLabel.setAlignmentX(CENTER_ALIGNMENT);
        JSlider aliSlider = new JSlider();
        aliSlider.setMaximum(100);
        aliSlider.setMinimum(0);
        aliSlider.setValue(50);
        aliSlider.setPaintTicks(true);
        aliSlider.setPaintLabels(true);
        aliSlider.setMinorTickSpacing(10);
        aliSlider.setMajorTickSpacing(20);
        aliSlider.addChangeListener(new ChangeListener() {
            public void stateChanged (ChangeEvent event) {
                Flock.setAlignmentWeight(((JSlider)event.getSource()).getValue() / 50.0 * Flock.alignmentWeightIni);
            }
        });
        box.add(aliLabel, BorderLayout.NORTH);
        box.add(aliSlider, BorderLayout.NORTH);


        box.add(Box.createVerticalStrut(30));
        JLabel obstaclesLabel = new JLabel("Препятствие");
        obstaclesLabel.setAlignmentX(CENTER_ALIGNMENT);
        box.add(obstaclesLabel);
        JButton addObstacle = new JButton("Add");
        addObstacle.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                panel.addObstacle();
            }
        });
        JButton removeObstacles = new JButton("Remove");
        removeObstacles.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                panel.removeObstacles();
            }
        });
        Box boxH = Box.createHorizontalBox();
        boxH.add(addObstacle);
        boxH.add(removeObstacles);
        box.add(boxH);

        //Кнопока "вставить хижника" и "убрать хищника"
        box.add(Box.createVerticalStrut(30));
        JLabel predatorsLabel = new JLabel("Хищник");
        predatorsLabel.setAlignmentX(CENTER_ALIGNMENT);
        box.add(predatorsLabel);
        JButton addPredator = new JButton("Add");
        addPredator.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                panel.addPredator();
            }
        });
        JButton removePredators = new JButton("Remove");
        removePredators.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                panel.removePredators();
            }
        });
        boxH = Box.createHorizontalBox();
        boxH.add(addPredator);
        boxH.add(removePredators);
        box.add(boxH);

        //Отображение окна
        this.getContentPane().add(box, BorderLayout.EAST);
        this.panel.setBackground(Color.white);
        this.getContentPane().add(panel);
        this.setVisible(true);

        //Запуск алгоритма
        animation();

    }


    public void animation() {
        for (;;) {
            this.panel.repaint(); //refresh
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }




    public static int height() {
        return Window.height;
    }

    public static int width() {
        return Window.width;
    }

    public static int panelHeight() {
        return Window.panelHeight;
    }

    public static int panelWidth() {
        return Window.panelWidth;
    }



    public static void setPanelHeight(int h) {
        Window.panelHeight = h;
    }

    public static void setPanelWidth(int w) {
        Window.panelWidth = w;
    }



}