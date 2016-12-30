import javax.swing.JFrame;

import controller.ObserveBindController;
import view.MainPanel;

/**
 * Created by 徐江河 on 2016/12/24.
 */
public class NotebookLauncher {

    public static void main(String[] args) {
    	JFrame jf = new JFrame();
    	MainPanel mp = new MainPanel();
    	jf.add(mp);
    	jf.setSize(mp.width, mp.height);
    	jf.setResizable(false);
    	jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	jf.setVisible(true);
    }
}
