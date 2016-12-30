package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import common.Position;
import type.StyleType;

public class SmallDrawPanel extends JPanel {
	
private ArrayList<Position> theDraw;
int x1 = 0;
int x2 = 0; 
int y1 = 0;  
int y2 = 0; 
	SmallDrawPanel(ArrayList<Position> theDraw){
		this.theDraw = theDraw;
		//this.paintComponent(getGraphics());
	}
	// paintComponent方法调用绘制方法使在容器内绘制而不超出容器 
	public void paintComponent(Graphics g) {  
			super.paintComponent(g);   
			draw((Graphics2D) g);  
		}   
	// 从数组中取出一个点后画图  
		public void draw(Graphics2D g) { 
			int n = theDraw.size();  
			Position p;  
			for(int j = 0;j<n;j++){
				theDraw.get(j).x = theDraw.get(j).x/10;
				theDraw.get(j).y = theDraw.get(j).y/10;
			}
			for (int i = 0; i < n; i++) {  
				try {   
					p = theDraw.get(i); 
					
					if (p.type == StyleType.PEN) {// 画笔    
						x1 = x2 = p.x;    
						y1 = y2 = p.y/10;    
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
//					if (p.type == StyleType.TEXT) {// 文字    
//						while (p.type == StyleType.TEXT) {   
//							g.setColor(p.color);   
//							g.drawString(p.s, p.x, p.y);// 点状绘制实时字符     
//							i++;      
//							if (i == n) {   
//								i--;       
//								break;   
//							}       p = thedraw.get(i);     
//						}   
//					}      
//					if (p.type == StyleType.LINE) {// 直线   
//						x1 = p.x;    
//						y1 = p.y;   
//						i++;    
//						p = thedraw.get(i);     
//						x2 = p.x;      
//						y2 = p.y;     
//						if (p.type == StyleType.LINE) {
//							// 不存在翻转问题，故不用交换坐标    
//							Line2D t = new Line2D.Double(x1, y1, x2, y2);  
//							g.setColor(p.color);   
//							g.draw(t);    
//							thedraw.remove(i);     
//						} else if (p.type == StyleType.TEMPORARY_STOP) { 
//							Line2D t = new Line2D.Double(x1, y1, x2, y2);      
//							g.setColor(p.color);     
//							g.draw(t);  
//						} else      
//							i--;
//					}    
//					if (p.type == StyleType.RECTANGLE) {// 矩形 
//						x1 = p.x;    
//						y1 = p.y;   
//						i++;   
//						p = thedraw.get(i);    
//						x2 = p.x;    
//						y2 = p.y;     
//						if (x2 < x1) {// 交换坐标使图形能上下左右翻转  
//							int temp;   
//							temp = x1;  
//							x1 = x2;   
//							x2 = temp;    
//						}  
//						if (y2 < y1) {    
//							int temp;     
//							temp = y1;    
//							y1 = y2;   
//							y2 = temp;   
//						}     
//						if (p.type == StyleType.RECTANGLE) {// 鼠标按下则动态变化   
//							Rectangle2D t = new Rectangle2D.Double(x1, y1, x2 - x1,         y2 - y1);   
//							g.setColor(p.color);     
//							g.draw(t);      
//							thedraw.remove(i);  
//						} else if (p.type == StyleType.TEMPORARY_STOP) {// 鼠标松开则固定绘图  
//							Rectangle2D t = new Rectangle2D.Double(x1, y1, x2 - x1,         y2 - y1);   
//							g.setColor(p.color);     
//							g.draw(t);     
//						} else 
//							i--;   
//					}     
//					if (p.type == StyleType.ROUNDED_RECTANGLE) {// 圆角矩形     
//						x1 = p.x;    
//						y1 = p.y;  
//						i++;  
//						p = thedraw.get(i);  
//						x2 = p.x;  
//						y2 = p.y;   
//						if (x2 < x1) {      
//							int temp;     
//							temp = x1;    
//							x1 = x2;    
//							x2 = temp; 
//						}      
//						if (y2 < y1) {    
//							int temp;     
//							temp = y1;       
//							y1 = y2;  
//							y2 = temp;     
//						} 
//						if (p.type == StyleType.ROUNDED_RECTANGLE) { 
//							RoundRectangle2D t = new RoundRectangle2D.Double(x1,         y1, x2 - x1, y2 - y1, 20, 20);    
//							g.setColor(p.color);   
//							g.draw(t);     
//							thedraw.remove(i); 
//						} else if (p.type == StyleType.TEMPORARY_STOP) {  
//							RoundRectangle2D t = new RoundRectangle2D.Double(x1,         y1, x2 - x1, y2 - y1, 20, 20);   
//							g.setColor(p.color);     
//							g.draw(t); 
//						} else     
//							i--;   
//					}     
//					if (p.type == StyleType.OVAL) {// 椭圆  
//						x1 = p.x;   
//						y1 = p.y;   
//						i++;    
//						p = thedraw.get(i);    
//						x2 = p.x;    
//						y2 = p.y;   
//						if (x2 < x1) {     
//							int temp;   
//							temp = x1;   
//							x1 = x2;   
//							x2 = temp;    
//						} 
//						if (y2 < y1) {    
//							int temp;     
//							temp = y1;   
//							y1 = y2;    
//							y2 = temp;     
//						}   
//						if (p.type == StyleType.OVAL) {   
//							Ellipse2D t = new Ellipse2D.Double(x1, y1, x2 - x1, y2         - y1);    
//							g.setColor(p.color);      
//							g.draw(t);    
//							thedraw.remove(i);   
//						} 
//						else if (p.type == StyleType.TEMPORARY_STOP) {    
//							Ellipse2D t = new Ellipse2D.Double(x1, y1, x2 - x1, y2         - y1);     
//							g.setColor(p.color);    
//							g.draw(t); 
//						} 
//						else      
//							i--;   
//					}      
				} catch (Exception ex) {  
					ex.printStackTrace();
				}   
			}  
		}  
		
		
	
}