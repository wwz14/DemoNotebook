package view;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;


/**
 * Created by 徐江河 on 2016/12/24.
 */
class DrawingPanel implements MouseInputListener {
    // 我猜想应该有直接可以利用的画板Panel，而不用自己写这些鼠标监听
    // Page的具体实现建议使用画板Panel提供的类型，以减少总类型（现在已经挺多的了）

    // 也就是希望不要自己实现MouseInputListener接口
    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }
}
