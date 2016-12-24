package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import common.ObMessage;
import common.Observable;
import common.Observer;
import common.Position;

/**
 * Created by 徐江河 on 2016/12/24.
 * TODO 还有不少冗余的内容需要删改（各种按钮）
 * TODO 将Page保存、加载交给MainPanel与Controller，不要写在此Panel
 * TODO 保存按钮建议交给MainPanel
 * TODO 在Page变化较大时发出PAGE_ALTERED信息
 * TODO 处理PAGE_REPLACE信息
 */
class DrawingPanel extends JPanel implements ActionListener, MouseListener,  MouseMotionListener, Observable, Observer {
    JMenuBar mb;// 菜单栏
    JMenu menu1, menu2, menu3;
    JMenuItem i1, i2, i3;
    JPanel jp1;
    // 工具栏
    public JButton anj0, anj1, anj2;
    ArrayList<Position> thedraw = new ArrayList<Position>();
    // 保存画图轨迹的数组
    int style = 0;
    //保存画图类型，默认为画笔
    int x1 = 0;
    // 保存点的坐标
    int x2 = 0;
    int y1 = 0;
    int y2 = 0;

    public DrawingPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        // 上部菜单栏
        mb = new JMenuBar();
        mb.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        add(mb, BorderLayout.NORTH);
        // 加入菜单栏的组件
        menu1 = new JMenu("  文件     ");
        menu2 = new JMenu("  编辑     ");
        menu3 = new JMenu("  帮助     ");
        i1 = new JMenuItem("打开                    ", new ImageIcon(     "img/open.png"));
        i2 = new JMenuItem("保存                    ", new ImageIcon(     "img/save.png"));
        i3 = new JMenuItem("清空                    ", new ImageIcon(     "img/clear.png"));
        menu1.add(i1);
        menu1.addSeparator();
        menu1.add(i2);
        menu2.add(i3);
        mb.add(menu1);
        mb.add(menu2);
        mb.add(menu3);
        add(mb, BorderLayout.NORTH);
        // 侧边工具栏

        jp1 = new JPanel();
        jp1.setBackground(Color.LIGHT_GRAY);
        jp1.setLayout(new BoxLayout(jp1, BoxLayout.Y_AXIS));
        jp1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        add(jp1, BorderLayout.WEST);
        // 加入工具栏的组件
        anj0 = new JButton("画笔");
        anj1 = new JButton("刷子");
        anj2 = new JButton("橡皮");
        jp1.add(anj0);
        jp1.add(anj1);
        jp1.add(anj2);
        // 事件处理
        i1.addActionListener(this);
        i2.addActionListener(this);
        i3.addActionListener(this);
        anj0.addActionListener(this);
        anj1.addActionListener(this);
        anj2.addActionListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }    // 记录鼠标选择的功能
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == anj0)  {
            style = 0;
        }
        else if (e.getSource() == anj1)   {
            style = 1;
        }
        else if (e.getSource() == anj2)  {
            style = 2;
        }
        else if (e.getActionCommand().equals("清空                    ")) {
            thedraw.clear();
        }
        else if (e.getActionCommand().equals("保存                    ")) {
            JFileChooser sfc = new JFileChooser();
            int flag = -1;
            // 显示保存文件的对话框
            try {
                flag = sfc.showSaveDialog(this);
            } catch (HeadlessException he) {
                System.out.println("Save File Dialog Exception!");
            }
            // 获取选择文件的路径
            if (flag == JFileChooser.APPROVE_OPTION) {
                String filename = sfc.getSelectedFile().getPath();
                try {
                    FileOutputStream fos = new FileOutputStream(filename);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(thedraw);
                    oos.close();
                } catch (Exception es) {
                    System.out.println(es);
                }
            }
        } else if (e.getActionCommand().equals("打开        "+ "            ")) {
            JFileChooser ofc = new JFileChooser();
            int flag = -1;
            try {
                flag = ofc.showOpenDialog(this);
            } catch (HeadlessException he) {
                System.out.println("Save File Dialog Exception!");
            }    // 获取选择文件的路径
            if (flag == JFileChooser.APPROVE_OPTION) {
                String filename = ofc.getSelectedFile().getPath();
                try {
                    FileInputStream fis = new FileInputStream(filename);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    thedraw = (ArrayList<Position>) ois.readObject();
                    ois.close();
                }
                catch (Exception es) {
                    System.out.println(es);
                }
            }
        }
        repaint();
        // 刷新画板
    }
    // paintComponent方法调用绘制方法使在容器内绘制而不超出容器
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw((Graphics2D) g);
    }
    // 从数组中取出一个点后画图
    public void draw(Graphics2D g) {
        int n = thedraw.size();
        Position p;
        for (int i = 0; i < n; i++) {
                p = thedraw.get(i);
                if (p.type == 0) {// 画笔
                    x1 = x2 = p.x;
                    y1 = y2 = p.y;
                    while (p.type == 0) {
                        x2 = p.x;
                        y2 = p.y;
                        Line2D t = new Line2D.Double(x1, y1, x2, y2);
                        g.draw(t);// 递归画图
                        i++;
                        if (i == n) {
                            i--;
                            break;
                        }
                        p = thedraw.get(i);
                        x1 = x2;
                        y1 = y2;
                    }
                }
                if (p.type == 1) {// 刷子
                    while (p.type == 1) {
                        g.drawString("●", p.x, p.y);// 采用字符使点变大
                        i++;
                        if (i == n) {
                            i--;
                            break;
                        }       p = thedraw.get(i);
                    }
                }
                if (p.type == 2) {// 橡皮
                    while (p.type == 2) {
                        g.setColor(Color.WHITE);
                        g.drawString("■", p.x, p.y);// 采用字符使点变大
                        i++;
                        if (i == n) {
                            i--;
                            break;
                        }
                        p = thedraw.get(i);
                    }
                }
        }
    }
    public void mouseClicked(MouseEvent e) {  }
    public void mouseEntered(MouseEvent e) {  }     // 鼠标按下记录画图轨迹
    public void mousePressed(MouseEvent e) {
        Position p = new Position();
        p.x = e.getX();
        p.y = e.getY();
        p.type = style;
        thedraw.add(p);
    }
    public void mouseExited(MouseEvent e) {  }
    // 鼠标松开则type = -1，停止画图，但仍记录轨迹
    public void mouseReleased(MouseEvent e) {
        Position p = new Position();
        p.x = e.getX();
        p.y = e.getY();
        p.type = -1;
        thedraw.add(p);
        repaint();
    }
    public void mouseMoved(MouseEvent e) {  }
    // 鼠标拖动记录画图轨迹
    public void mouseDragged(MouseEvent e) {
        Position p = new Position();
        p.x = e.getX();
        p.y = e.getY();
        if (style == 3)   {
            p.type = -1;// 禁止文字拖动
        }
        else  {
            p.type = style;
        }
        thedraw.add(p);
        repaint();
    }

    private Set<Observer> observers = new HashSet<Observer>();

    public void notifyObservers(ObMessage arg) {
        for (Observer obs : observers) {
            obs.update(this,arg);
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void update(Observable source, ObMessage message) {
        // TODO 处理PAGE_REPLACE消息
    }
}
