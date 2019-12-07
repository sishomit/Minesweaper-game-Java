/*
 gameclass
 */


import java.awt.*;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class Minesweeper extends JFrame implements ActionListener, ContainerListener {

    int fw, fh, blockr, blockc, var1, var2, num_of_mine, detectedmine = 0, savedlevel = 1,
            savedblockr, savedblockc, savednum_of_mine = 10;
   int[] r = {-1, -1, -1, 0, 1, 1, 1, 0};
   int[] c  = {-1, 0, 1, 1, 1, 0, -1, -1};

    JButton[][] blocks;
    int[][] countmine;
    int[][] colour;
    ImageIcon[] ic = new ImageIcon[14];
    JPanel panelb = new JPanel();
    JPanel panelmt = new JPanel();
    JTextField tf_mine, tf_time;
    JButton reset = new JButton("");
    Random ranr = new Random();
    Random ranc = new Random();
    boolean check = true, starttime = false;
    Point framelocation;
    Stopwatch sw;
    MouseHendeler mh;
    Point p;

    Minesweeper() {
        super("Minesweeper");
        setLocation(400, 300);

        setic();
        setpanel(1, 0, 0, 0);
        setmanue();

        sw = new Stopwatch();

        reset.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                try {
                    sw.stop();
                    setpanel(savedlevel, savedblockr, savedblockc, savednum_of_mine);
                } catch (Exception ex) {
                    setpanel(savedlevel, savedblockr, savedblockc, savednum_of_mine);
                }
                reset();

            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        show();
    }
    //reseting

    public void reset() {
        check = true;
        starttime = false;
        for (int i = 0; i < blockr; i++) {
            for (int j = 0; j < blockc; j++) {
                colour[i][j] = 'w';
            }
        }
    }
//setting game panel
    public void setpanel(int level, int setr, int setc, int setm) {
        //beginner
        if (level == 1) {
            fw = 200;
            fh = 300;
            blockr = 5;
            blockc = 5;
            num_of_mine = 3;
        }
        //intermidiate
        else if (level == 2) {
            fw = 320;
            fh = 416;
            blockr = 10;
            blockc = 10;
            num_of_mine = 10;
        }
        //expert
        else if (level == 3) {
            fw = 400;
            fh = 520;
            blockr = 15;
            blockc = 15;
            num_of_mine = 15;
        }
        //custom
        else if (level == 4) {
            if(setc<10)
                fw = (40 * setc);
            else
            fw = (20 * setc);
            if(setr<10)
                fh = (48 * setr);
                else
            fh = (24 * setr);
            blockr = setr;
            blockc = setc;
            num_of_mine = setm;
        }

        savedblockr = blockr;
        savedblockc = blockc;
        savednum_of_mine = num_of_mine;

        setSize(fw, fh);
        setResizable(false);
        detectedmine = num_of_mine;
        p = this.getLocation();

        blocks = new JButton[blockr][blockc];
        countmine = new int[blockr][blockc];
        colour = new int[blockr][blockc];
        mh = new MouseHendeler();

        getContentPane().removeAll();
        panelb.removeAll();
//creating minecount and timer panel
        tf_mine = new JTextField("" + num_of_mine, 3);
        tf_mine.setEditable(false);
        tf_mine.setFont(new Font("DigtalFont.TTF", Font.BOLD, 25));
        tf_mine.setBackground(Color.black);
        tf_mine.setForeground(Color.green);
        tf_mine.setBorder(BorderFactory.createLoweredBevelBorder());
        tf_time = new JTextField("000", 3);
        tf_time.setEditable(false);
        tf_time.setFont(new Font("DigtalFont.TTF", Font.BOLD, 25));
        tf_time.setBackground(Color.black);
        tf_time.setForeground(Color.green);
        tf_time.setBorder(BorderFactory.createLoweredBevelBorder());
        reset.setIcon(ic[11]);
        reset.setBorder(BorderFactory.createLoweredBevelBorder());

        panelmt.removeAll();
        panelmt.setLayout(new BorderLayout());
        panelmt.add(tf_mine, BorderLayout.WEST);
        panelmt.add(reset, BorderLayout.CENTER);
        panelmt.add(tf_time, BorderLayout.EAST);
        panelmt.setBorder(BorderFactory.createLoweredBevelBorder());

        panelb.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createLoweredBevelBorder()));
        panelb.setPreferredSize(new Dimension(fw, fh));
        panelb.setLayout(new GridLayout(0, blockc));
        panelb.addContainerListener(this);
//creating  empty blocks
        for (int i = 0; i < blockr; i++) {
            for (int j = 0; j < blockc; j++) {
                blocks[i][j] = new JButton("");
                blocks[i][j].addMouseListener(mh);

                panelb.add(blocks[i][j]);

            }
        }
        reset();

        panelb.revalidate();
        panelb.repaint();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().addContainerListener(this);
        getContentPane().repaint();
        getContentPane().add(panelb, BorderLayout.CENTER);
        getContentPane().add(panelmt, BorderLayout.NORTH);
        setVisible(true);
    }
    //creating menubar

    public void setmanue() {
        JMenuBar bar = new JMenuBar();

        JMenu game = new JMenu("Game Menu");

        JMenuItem menuitem = new JMenuItem("New game");
        final JCheckBoxMenuItem beginner = new JCheckBoxMenuItem("Beginner");
        final JCheckBoxMenuItem intermediate = new JCheckBoxMenuItem("Intermediate");
        final JCheckBoxMenuItem expart = new JCheckBoxMenuItem("Expert");
        final JCheckBoxMenuItem custom = new JCheckBoxMenuItem("Custom");
        final JMenuItem exit = new JMenuItem("Exit");
        final JMenu help = new JMenu("Help");
        final JMenuItem helpitem = new JMenuItem("Instruction");

        ButtonGroup status = new ButtonGroup();

        menuitem.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {


                        setpanel(1, 0, 0, 0);

                    }
                });

        beginner.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        panelb.removeAll();
                        reset();
                        setpanel(1, 0, 0, 0);
                        panelb.revalidate();
                        panelb.repaint();
                        beginner.setSelected(true);
                        savedlevel = 1;
                    }
                });
        intermediate.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        panelb.removeAll();
                        reset();
                        setpanel(2, 0, 0, 0);
                        panelb.revalidate();
                        panelb.repaint();
                        intermediate.setSelected(true);
                        savedlevel = 2;
                    }
                });
        expart.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        panelb.removeAll();
                        reset();
                        setpanel(3, 0, 0, 0);
                        panelb.revalidate();
                        panelb.repaint();
                        expart.setSelected(true);
                        savedlevel = 3;
                    }
                });

        custom.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        Customizetion cus = new Customizetion();
                        reset();
                        panelb.revalidate();
                        panelb.repaint();

                        custom.setSelected(true);
                        savedlevel = 4;
                    }
                });

        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        helpitem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Instruction:\n1.Avoid mines to win this game.\n2.Choose game level from game menu");

            }
        });

        setJMenuBar(bar);

        status.add(beginner);
        status.add(intermediate);
        status.add(expart);
        status.add(custom);

        game.add(menuitem);
        game.addSeparator();
        game.add(beginner);
        game.add(intermediate);
        game.add(expart);
        game.add(custom);
        game.addSeparator();
        game.add(exit);
        help.add(helpitem);

        bar.add(game);
        bar.add(help);

    }

    public void componentAdded(ContainerEvent ce) {
    }

    public void componentRemoved(ContainerEvent ce) {
    }

    public void actionPerformed(ActionEvent ae) {
    }

    class MouseHendeler extends MouseAdapter {
//after clicking the blocks effect
        public void mouseClicked(MouseEvent me) {
            if (check == true) {
                for (int i = 0; i < blockr; i++) {
                    for (int j = 0; j < blockc; j++) {
                        if (me.getSource() == blocks[i][j]) {
                            var1 = i;
                            var2 = j;
                            i = blockr;
                            break;
                        }
                    }
                }

                setmine();
                calculation();
                check = false;

            }

            showvalue(me);
            winner();

            if (starttime == false) {
                sw.Start();
                starttime = true;
            }

        }
    }
    //winner function

    public void winner() {
        int q = 0;
        for (int k = 0; k < blockr; k++) {
            for (int l = 0; l < blockc; l++) {
                if (colour[k][l] == 'w') {
                    q = 1;
                }
            }
        }


        if (q == 0) {

            for (int k = 0; k < blockr; k++) {
                for (int l = 0; l < blockc; l++) {
                    blocks[k][l].removeMouseListener(mh);
                }
            }

            sw.stop();
            JOptionPane.showMessageDialog(this, "Congratulation you've won!!!");
        }
    }
//mouse event with losing function
    public void showvalue(MouseEvent e) {
        for (int i = 0; i < blockr; i++) {
            for (int j = 0; j < blockc; j++) {

                if (e.getSource() == blocks[i][j]) {
                    if (e.isMetaDown() == false) {
                        if (blocks[i][j].getIcon() == ic[10]) {
                            if (detectedmine < num_of_mine) {
                                detectedmine++;
                            }
                            tf_mine.setText("" + detectedmine);
                        }

                        if (countmine[i][j] == -1) {
                            for (int k = 0; k < blockr; k++) {
                                for (int l = 0; l < blockc; l++) {
                                    if (countmine[k][l] == -1) {


                                        blocks[k][l].setIcon(ic[9]);

                                        blocks[k][l].removeMouseListener(mh);
                                    }
                                    blocks[k][l].removeMouseListener(mh);
                                }
                            }
                            sw.stop();
                            reset.setIcon(ic[12]);
                            JOptionPane.showMessageDialog(null, "Sorry,You've lost!!!");
                        } else if (countmine[i][j] == 0) {
                            dfs(i, j);
                        } else {
                            blocks[i][j].setIcon(ic[countmine[i][j]]);
                            colour[i][j] = 'b';
                            break;
                        }
                    } else {
                        if (detectedmine != 0) {
                            if (blocks[i][j].getIcon() == null) {
                                detectedmine--;
                                blocks[i][j].setIcon(ic[10]);
                            }
                            tf_mine.setText("" + detectedmine);
                        }


                    }
                }

            }
        }

    }
    //calculation the mineset

    public void calculation() {
        int row, column;

        for (int i = 0; i < blockr; i++) {
            for (int j = 0; j < blockc; j++) {
                int value = 0;
                int R, C;
                row = i;
                column = j;
                if (countmine[row][column] != -1) {
                    for (int k = 0; k < 8; k++) {
                        R = row + r[k];
                        C = column + c[k];

                        if (R >= 0 && C >= 0 && R < blockr && C < blockc) {
                            if (countmine[R][C] == -1) {
                                value++;
                            }

                        }

                    }
                    countmine[row][column] = value;

                }
            }
        }
    }

    public void dfs(int row, int col) {

        int R, C;
        colour[row][col] = 'b';

        blocks[row][col].setBackground(Color.gray);

        blocks[row][col].setIcon(ic[countmine[row][col]]);
        //blocks[row][col].setText("");
        for (int i = 0; i < 8; i++) {
            R = row + r[i];
            C = col + c[i];
            if (R >= 0 && R < blockr && C >= 0 && C < blockc && colour[R][C] == 'w') {
                if (countmine[R][C] == 0) {
                    dfs(R, C);
                } else {
                    blocks[R][C].setIcon(ic[countmine[R][C]]);

                    colour[R][C] = 'b';

                }
            }


        }
    }
    //setting mines in the frame

    public void setmine() {
        int row = 0, col = 0;
        Boolean[][] flag = new Boolean[blockr][blockc];


        for (int i = 0; i < blockr; i++) {
            for (int j = 0; j < blockc; j++) {
                flag[i][j] = true;
                countmine[i][j] = 0;
            }
        }

        flag[var1][var2] = false;
        colour[var1][var2] = 'b';

        for (int i = 0; i < num_of_mine; i++) {
            row = ranr.nextInt(blockr);
            col = ranc.nextInt(blockc);

            if (flag[row][col] == true) {

                countmine[row][col] = -1;
                colour[row][col] = 'b';
                flag[row][col] = false;
            } else {
                i--;
            }
        }
    }

    public void setic() {
        String name;

        for (int i = 0; i <= 8; i++) {
            name = i + ".gif";
            ic[i] = new ImageIcon(name);
        }
        ic[9] = new ImageIcon("mine.gif");
        ic[10] = new ImageIcon("flag.gif");
        ic[11] = new ImageIcon("new game.gif");
        ic[12] = new ImageIcon("crape.gif");
    }

//counting the time
    public class Stopwatch extends JFrame implements Runnable {

        long startTime;

        Thread updater;
        boolean isRunning = false;
        long a = 0;
        Runnable displayUpdater = new Runnable() {

            public void run() {
                displayElapsedTime(a);
                a++;
            }
        };

        public void stop() {
            long elapsed = a;
            isRunning = false;
            try {
                updater.join();
            } catch (InterruptedException ie) {
            }
            displayElapsedTime(elapsed);
            a = 0;
        }
//showing the time
        private void displayElapsedTime(long elapsedTime) {

            if (elapsedTime >= 0 && elapsedTime < 9) {
                tf_time.setText("00" + elapsedTime);
            } else if (elapsedTime > 9 && elapsedTime < 99) {
                tf_time.setText("0" + elapsedTime);
            } else if (elapsedTime > 99 && elapsedTime < 999) {
                tf_time.setText("" + elapsedTime);
            }
        }

        public void run() {
            try {
                while (isRunning) {
                    SwingUtilities.invokeAndWait(displayUpdater);
                    Thread.sleep(1000);
                }
            } catch (java.lang.reflect.InvocationTargetException ite) {
                ite.printStackTrace(System.err);
            } catch (InterruptedException ie) {
            }
        }

        public void Start() {
            startTime = System.currentTimeMillis();
            isRunning = true;
            updater = new Thread(this);
            updater.start();
        }
    }
//creating custom setting page
    class Customizetion extends JFrame implements ActionListener {

        JTextField t1, t2, t3;
        JLabel lb1, lb2, lb3,lb4,lb5;
        JButton b1, b2;
        int cr, cc, cm, actionc = 0;

        Customizetion() {
            super("CUSTOMIZETION");
            setSize(300, 200);
            setResizable(false);
            setLocation(p);

            t1 = new JTextField();
            t2 = new JTextField();
            t3 = new JTextField();

            b1 = new JButton("OK");
            b2 = new JButton("Cancel");

            b1.addActionListener(this);
            b2.addActionListener(this);

            lb4 = new JLabel("At least 5 rows,5 columns. ");
            lb5 = new JLabel("At least 1 mine");
            lb1 = new JLabel("Row");
            lb2 = new JLabel("Column");
            lb3 = new JLabel("Mine");

            getContentPane().setLayout(new GridLayout(0, 2));

            getContentPane().add(lb4);
            getContentPane().add(lb5);
            getContentPane().add(lb1);
            getContentPane().add(t1);
            getContentPane().add(lb2);
            getContentPane().add(t2);
            getContentPane().add(lb3);
            getContentPane().add(t3);

            getContentPane().add(b1);
            getContentPane().add(b2);

            show();
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == b1) {
                try {
                    cr = Integer.parseInt(t1.getText());
                    cc = Integer.parseInt(t2.getText());
                    cm = Integer.parseInt(t3.getText());
                    setpanel(4, row(), column(), mine());
                    dispose();
                } catch (Exception any) {
                    JOptionPane.showMessageDialog(this, "Wrong");
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                }

            }

            if (e.getSource() == b2) {
                dispose();
            }
        }
        //setting the rules for customization.not less than 5 and not more than 30 row and column and at least 1 mine

        public int row() {
            if (cr > 30) {
                return 30;
            } else if (cr < 5) {
                return 5;
            } else {
                return cr;
            }
        }

        public int column() {
            if (cc > 30) {
                return 30;
            } else if (cc < 5) {
                return 5;
            } else {
                return cc;
            }
        }

        public int mine() {
            if (cm > ((row() - 1) * (column() - 1))) {
                return ((row() - 1) * (column() - 1));
            } else if (cm < 1) {
                return 1;
            } else {
                return cm;
            }
        }
    }
}
