package view;

import type.StyleType;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import common.MessageType;
import common.ObMessage;
import common.Observable;
import common.Observer;
import common.Page;
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
	
	private MainPanel parent;

    private Set<Observer> observers = new HashSet<Observer>();

	public static String directoryName = "src/file/";	//默认的保存路径是src的file中
	
	public int width;	// 本Panel的宽
	public int height;	// 本Panel的高
	
	public int noteLineStartX;		// 笔记线的起始位置的横坐标
	public int noteLineStartY;		// 第一行笔记线的纵坐标
	public int noteLineLength;		// 笔记线的长度
	public int noteLineHeight;		//笔记线的上下两行间距
	
	public JPanel funcPanel;	// 工具栏  
	
	public JButton saveBtn;		// 保存按钮
	
	public JButton penBtn;		// 铅笔按钮
	public JButton brushBtn;	// 刷子按钮
	public JButton eraserBtn;	// 橡皮按钮
//	public JButton textBtn;		// 文本按钮
//	public JButton lineBtn;		// 直线按钮
//	public JButton recBtn;		// 矩形按钮
//	public JButton roundRecBtn;	// 圆角矩形按钮
//	public JButton ovalBtn;		// 椭圆按钮
	public JButton colorBtn;  	// 颜色按钮
	public JLabel colorNameLabel, colorIconLabel; // 颜色按钮的子组件
	public JButton clearBtn;	// 清除画布内容按钮
	
	// 保存画图轨迹的数组
	public ArrayList<Position> theDraw = new ArrayList<Position>(); 
	
	//当前画图类型，默认为画笔 
	public StyleType style = StyleType.PEN; 	
	
	// 当前点的坐标，默认为界面左上角
	int x1 = 0;
	int x2 = 0; 
	int y1 = 0;  
	int y2 = 0; 
	
	// 输入文字的内容，默认为空  
	String input = "";
	
	// 线条颜色，默认为黑色 
	Color lineColor = Color.BLACK; 
	
	public DrawingPanel(MainPanel mp) {
		this.parent = mp;
		observers.add(mp);
		
		this.setBackground(Color.WHITE);  
		this.setLayout(new BorderLayout()); 
		this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		
		// 工具栏   
		funcPanel = new JPanel();   
		funcPanel.setBackground(Color.LIGHT_GRAY);   
//		funcPanel.setLayout(new BoxLayout(funcPanel, BoxLayout.X_AXIS));   
		funcPanel.setLayout(new GridLayout(1,8));
		funcPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); 
		funcPanel.setSize(200, 300);
		this.add(funcPanel, BorderLayout.NORTH);   
		
		// 工具栏的组件  
		saveBtn = new JButton("保存", new ImageIcon("src/main/java/img/save-small.png"));
		penBtn = new JButton("画笔", new ImageIcon("src/main/java/img/pencil-small.png")); 
		brushBtn = new JButton("刷子", new ImageIcon("src/main/java/img/brush-small.png"));  
		eraserBtn = new JButton("橡皮", new ImageIcon("src/main/java/img/eraser-small.jpg"));  
//		textBtn = new JButton("文字", new ImageIcon("img/word.png"));  
//		lineBtn = new JButton("直线", new ImageIcon("img/sline.png"));  
//		recBtn = new JButton("矩形", new ImageIcon("img/rec.png"));  
//		roundRecBtn = new JButton("圆矩", new ImageIcon("img/frec.png"));  
//		ovalBtn = new JButton("椭圆", new ImageIcon("img/eli.png")); 
//		colorBtn = new JButton("颜色", new ImageIcon("src/main/java/img/palette-small.png"));
		colorBtn = new JButton("");   
		colorIconLabel = new JLabel("■");
		colorNameLabel = new JLabel("       颜色");  
		colorBtn.add(colorIconLabel);  
		colorBtn.add(colorNameLabel);  
		clearBtn = new JButton("清除所有");
		
		// 将组件加到工具栏上
		funcPanel.add(saveBtn);
		funcPanel.add(new JLabel("   "));// 用于填充gridLayout界面布局
		funcPanel.add(penBtn);  
		funcPanel.add(brushBtn); 
		funcPanel.add(eraserBtn);  
//		funcPanel.add(textBtn);  
//		funcPanel.add(lineBtn);  
//		funcPanel.add(recBtn); 
//		funcPanel.add(roundRecBtn);  
//		funcPanel.add(ovalBtn);  
		funcPanel.add(colorBtn);  
		funcPanel.add(new JLabel("   "));// 用于填充gridLayout界面布局
		funcPanel.add(clearBtn);
		
//		saveBtn.setLocation(5, 5);
//		penBtn.setLocation(25, 5);
//		brushBtn.setLocation(45, 5);
//		eraserBtn.setLocation(65, 5);
//		colorBtn.setLocation(85, 5);
//		clearBtn.setLocation(105, 5);
		
		// 事件处理   
		saveBtn.addActionListener(this);
		penBtn.addActionListener(this);  
		brushBtn.addActionListener(this);  
		eraserBtn.addActionListener(this);  
//		textBtn.addActionListener(this);  
//		lineBtn.addActionListener(this);  
//		recBtn.addActionListener(this);  
//		roundRecBtn.addActionListener(this);  
//		ovalBtn.addActionListener(this); 
		colorBtn.addActionListener(this);  
		clearBtn.addActionListener(this);
		
		addMouseListener(this);   
		addMouseMotionListener(this);   
	}    
	
	// 记录鼠标选择的功能  
	public void actionPerformed(ActionEvent event) {  
		if (event.getSource() == penBtn)  {  
			style = StyleType.PEN; 
		}
		else if (event.getSource() == brushBtn)   {
			style = StyleType.BRUSH;  
		}
		else if (event.getSource() == eraserBtn)  {
			style = StyleType.ERASER;  
		}
//		else if (event.getSource() == textBtn) { 
//			style = StyleType.TEXT;   
//			input = JOptionPane.showInputDialog("输入文字后在画板上点击放置");  
//		} 
//		else if (event.getSource() == lineBtn)   {
//			style = StyleType.LINE;  
//		}
//		else if (event.getSource() == recBtn)   {
//			style = StyleType.RECTANGLE; 
//		}
//		else if (event.getSource() == roundRecBtn)   {
//			style = StyleType.ROUNDED_RECTANGLE;   
//		}
//		else if (event.getSource() == ovalBtn)  {
//			style = StyleType.OVAL; 
//		}
		else if (event.getSource() == colorBtn) {   
			lineColor = JColorChooser.showDialog(null, "请选择颜色", Color.BLACK);   
			colorIconLabel.setForeground(lineColor);   
		} 
		else if (event.getSource() == clearBtn) {   // 清除画布，向外界通知新的变化
			//theDraw.removeAllElements();  
			theDraw.clear();
		} 
		else if (event.getSource() == saveBtn) {  	// 显式保存，向外界通知新的变化
			saveDrawings();
		}    
		repaint();
	}  
	
	// paintComponent方法调用绘制方法使在容器内绘制而不超出容器 
	public void paintComponent(Graphics g) {  
		super.paintComponent(g);   
		draw((Graphics2D) g);  
		
		drawNoteLines(g);
	}   
	
    public void drawNoteLines(Graphics g){
    	noteLineStartX = (int)(width * 5 / 100);
    	noteLineStartY = (int)(height * 5 / 100);
    	noteLineLength = (int)(width * 90 / 100);
    	noteLineHeight = (int)(height * 3 / 100);
    	
    	g.setColor(Color.GRAY);

    	for (int i=0; i<19; i++) {
    		int currentY = noteLineStartY + i * noteLineHeight;
    		g.drawLine(noteLineStartX, currentY, noteLineLength, currentY);
    	}
    
    }
    
	// 从数组中取出一个点后画图  
	public void draw(Graphics2D g) { 
		int n = theDraw.size();  
		Position p;  
		for (int i = 0; i < n; i++) {  
			try {   
				p = theDraw.get(i);  
				if (p.type == StyleType.PEN) {// 画笔    
					x1 = x2 = p.x;    
					y1 = y2 = p.y;    
					while (p.type == StyleType.PEN) {    
						x2 = p.x;   
						y2 = p.y;    
						Line2D t = new Line2D.Double(x1, y1, x2, y2);    
						g.setColor(p.color);    
						g.draw(t);// 递归画图 
						i++;     
						if (i == n) {    
							i--;    
							break;    
						}    
						p = theDraw.get(i);      
						x1 = x2;    
						y1 = y2;    
					}   
				}     
				if (p.type == StyleType.BRUSH) {// 刷子 
					while (p.type == StyleType.BRUSH) {     
						g.setColor(p.color);   
						g.drawString("●", p.x, p.y);// 采用字符使点变大      
						i++;      
						if (i == n) {     
							i--;      
							break;    
						}       p = theDraw.get(i);   
					}   
				}     
				if (p.type == StyleType.ERASER) {// 橡皮    
					while (p.type == StyleType.ERASER) {     
						g.setColor(Color.WHITE);     
						g.drawString("■", p.x, p.y);// 采用字符使点变大    
						i++;    
						if (i == n) {    
							i--;       
							break;    
						}    
						p = theDraw.get(i);    
					}   
				}  
//				if (p.type == StyleType.TEXT) {// 文字    
//					while (p.type == StyleType.TEXT) {   
//						g.setColor(p.color);   
//						g.drawString(p.s, p.x, p.y);// 点状绘制实时字符     
//						i++;      
//						if (i == n) {   
//							i--;       
//							break;   
//						}       p = thedraw.get(i);     
//					}   
//				}      
//				if (p.type == StyleType.LINE) {// 直线   
//					x1 = p.x;    
//					y1 = p.y;   
//					i++;    
//					p = thedraw.get(i);     
//					x2 = p.x;      
//					y2 = p.y;     
//					if (p.type == StyleType.LINE) {
//						// 不存在翻转问题，故不用交换坐标    
//						Line2D t = new Line2D.Double(x1, y1, x2, y2);  
//						g.setColor(p.color);   
//						g.draw(t);    
//						thedraw.remove(i);     
//					} else if (p.type == StyleType.TEMPORARY_STOP) { 
//						Line2D t = new Line2D.Double(x1, y1, x2, y2);      
//						g.setColor(p.color);     
//						g.draw(t);  
//					} else      
//						i--;
//				}    
//				if (p.type == StyleType.RECTANGLE) {// 矩形 
//					x1 = p.x;    
//					y1 = p.y;   
//					i++;   
//					p = thedraw.get(i);    
//					x2 = p.x;    
//					y2 = p.y;     
//					if (x2 < x1) {// 交换坐标使图形能上下左右翻转  
//						int temp;   
//						temp = x1;  
//						x1 = x2;   
//						x2 = temp;    
//					}  
//					if (y2 < y1) {    
//						int temp;     
//						temp = y1;    
//						y1 = y2;   
//						y2 = temp;   
//					}     
//					if (p.type == StyleType.RECTANGLE) {// 鼠标按下则动态变化   
//						Rectangle2D t = new Rectangle2D.Double(x1, y1, x2 - x1,         y2 - y1);   
//						g.setColor(p.color);     
//						g.draw(t);      
//						thedraw.remove(i);  
//					} else if (p.type == StyleType.TEMPORARY_STOP) {// 鼠标松开则固定绘图  
//						Rectangle2D t = new Rectangle2D.Double(x1, y1, x2 - x1,         y2 - y1);   
//						g.setColor(p.color);     
//						g.draw(t);     
//					} else 
//						i--;   
//				}     
//				if (p.type == StyleType.ROUNDED_RECTANGLE) {// 圆角矩形     
//					x1 = p.x;    
//					y1 = p.y;  
//					i++;  
//					p = thedraw.get(i);  
//					x2 = p.x;  
//					y2 = p.y;   
//					if (x2 < x1) {      
//						int temp;     
//						temp = x1;    
//						x1 = x2;    
//						x2 = temp; 
//					}      
//					if (y2 < y1) {    
//						int temp;     
//						temp = y1;       
//						y1 = y2;  
//						y2 = temp;     
//					} 
//					if (p.type == StyleType.ROUNDED_RECTANGLE) { 
//						RoundRectangle2D t = new RoundRectangle2D.Double(x1,         y1, x2 - x1, y2 - y1, 20, 20);    
//						g.setColor(p.color);   
//						g.draw(t);     
//						thedraw.remove(i); 
//					} else if (p.type == StyleType.TEMPORARY_STOP) {  
//						RoundRectangle2D t = new RoundRectangle2D.Double(x1,         y1, x2 - x1, y2 - y1, 20, 20);   
//						g.setColor(p.color);     
//						g.draw(t); 
//					} else     
//						i--;   
//				}     
//				if (p.type == StyleType.OVAL) {// 椭圆  
//					x1 = p.x;   
//					y1 = p.y;   
//					i++;    
//					p = thedraw.get(i);    
//					x2 = p.x;    
//					y2 = p.y;   
//					if (x2 < x1) {     
//						int temp;   
//						temp = x1;   
//						x1 = x2;   
//						x2 = temp;    
//					} 
//					if (y2 < y1) {    
//						int temp;     
//						temp = y1;   
//						y1 = y2;    
//						y2 = temp;     
//					}   
//					if (p.type == StyleType.OVAL) {   
//						Ellipse2D t = new Ellipse2D.Double(x1, y1, x2 - x1, y2         - y1);    
//						g.setColor(p.color);      
//						g.draw(t);    
//						thedraw.remove(i);   
//					} 
//					else if (p.type == StyleType.TEMPORARY_STOP) {    
//						Ellipse2D t = new Ellipse2D.Double(x1, y1, x2 - x1, y2         - y1);     
//						g.setColor(p.color);    
//						g.draw(t); 
//					} 
//					else      
//						i--;   
//				}      
			} catch (Exception ex) {  
				ex.printStackTrace();
			}   
		}  
	}  

	public void mouseClicked(MouseEvent e) {
		// 用mousePressed实现
	} 
	
	public void mouseEntered(MouseEvent e) {  
		// 暂时没有鼠标进入某个按钮的监听
	}     
	
	// 鼠标按下记录画图轨迹
	public void mousePressed(MouseEvent e) {  
		Position p = new Position();  
		p.x = e.getX();  
		p.y = e.getY();  
		p.type = style; 
		p.s = input; 
		p.color = lineColor;  
		theDraw.add(p); 
	}
	
	public void mouseExited(MouseEvent e) { 
		// 用mouseReleased实现
	}  
	
	// 鼠标松开，向外界通知新的变化，type = -1，停止画图，但仍记录轨迹 
	public void mouseReleased(MouseEvent e) { 
		Position p = new Position();  
		p.x = e.getX();  
		p.y = e.getY();  
		p.type = StyleType.TEMPORARY_STOP; 
		p.s = input;   
		p.color = lineColor;  
		theDraw.add(p);  
		repaint(); 
		
		saveDrawings();
	}   
	
	public void mouseMoved(MouseEvent e) { 
		// 用mouseDragged实现
	}   
	
	// 鼠标拖动记录画图轨迹  
	public void mouseDragged(MouseEvent e) {  
		Position p = new Position();  
		p.x = e.getX(); 
		p.y = e.getY();  
		if (style == StyleType.TEXT)   {
			p.type = StyleType.TEMPORARY_STOP;// 禁止文字拖动   
		}
		else { 
			p.type = style;  
		}
		p.s = input;   
		p.color = lineColor;  
		theDraw.add(p);  
		repaint(); 
	} 
	
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
    	// 应该就是把PagePanel里面选择的Page加载到DrawingPanel里，就是所谓的“打开”操作
    	if (message.getType() == MessageType.PAGE_REPLACE) { // 从MainPanel中传来的、来自于PreviewPanel的方法
    		Page pages = (Page)message.getContent();
    		theDraw = pages.getView();
    		repaint();
    	} 
    	else if (message.getType() == MessageType.PAGE_ALTERED) { // 从MainPanel中传来的、来自于自己的方法
    		// 什么也不做
    	}
	} 
   
    //通知MainPanel，让MainPanel自动保存，并更新PreviewPanel的内容
    public void saveDrawings(){
    	ObMessage obm = new ObMessage(MessageType.PAGE_ALTERED, this.theDraw);
		notifyObservers(obm);
    }
    
}
