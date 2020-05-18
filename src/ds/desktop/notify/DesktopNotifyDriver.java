/*
 * DS Desktop Notify
 * A small utility to show small notifications in your Desktop anytime!
 */
package ds.desktop.notify;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * This Driver keeps track of the notifications being displayed.
 * @author DragShot
 */
public final class DesktopNotifyDriver {
    /**
     * The list of notifications currently on queue.
     */
    static ArrayList<DesktopNotify> windows=new ArrayList();
    /**
     * The window used to show the notifications.
     */
    static DesktopLayoutFrame frame;
    /**
     * The DesktopNotify driver Thread.
     */
    static volatile Thread tredo;

    private DesktopNotifyDriver() {}
    
    /**
     * Invoked by DesktopNotify, adds a notification to the queue. Notifications
     * are shown only when there is room for them to fit in the screen.
     * 
     * @param window a <code>DesktopNotify</code> object
     */
    protected static void postPane(DesktopNotify window){
        if(frame==null){
            boolean bool = JDialog.isDefaultLookAndFeelDecorated();
            JDialog.setDefaultLookAndFeelDecorated(false);
            frame=new DesktopLayoutFrame();
            JDialog.setDefaultLookAndFeelDecorated(bool);
        }
        if(!frame.isVisible()) frame.setVisible(true);
        window.setWidth(300);
        window.sortMessage();
        window.setVisible(true);
        windows.add(window);
        sparkControlThread();
    }
    
    /**
     * Starts the thread used to show and control the notifications, if
     * necessary.
     */
    private static void sparkControlThread(){
        if(tredo == null){
            tredo = new Thread(new Runnable(){@Override public void run() {
                System.out.println(Thread.currentThread().getName()+" started.");
                frame.finished = false;
                while (!frame.finished) {
                    frame.repaint();
                    try{
                        Thread.sleep(20); //FPS> 10:Super-high, 20: High, 40: Normal, 80: Low
                    }catch(InterruptedException ex){}
                }
                frame.dispose();
                tredo = null;
                System.out.println(Thread.currentThread().getName()+" finished.");
            }},"DesktopNotify Driver Thread");
            tredo.start();
        }
    }

    /**
     * Utilitary method for gathering the FontMetrics of a given Font.
     * Made because of laziness.
     * @param font A Font.
     * @return     The FontMetrics in effect for the selected font.
     */
    protected static FontMetrics getFontMetrics(Font font) {
        return frame.getGraphics().getFontMetrics(font);
    }
    
    /**
     * An undecorated JDialog used to show all the notifications on screen.
     */
    private static class DesktopLayoutFrame extends JDialog {
        Image bg;
        boolean nativeTrans;
        
        boolean finished=true;
        boolean clicked=false;

        public DesktopLayoutFrame() {
            super((JFrame)null,"DesktopLayoutFrame");
            setUndecorated(true);
            nativeTrans=Utils.isTranslucencySupported();
            setBackground(new Color(0,0,0,nativeTrans? 0:255));
            setContentPane(new JComponent(){
                @Override
                public void paintComponent(Graphics g){
                    render((Graphics2D)g);
                }
            });
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt){
                    clicked=true;
                }
            });
            setFocusableWindowState(false);
            setAlwaysOnTop(true);
            System.out.println("Desktop Notify Frame deployed.");
        }
        
        @Override
        public void setVisible(boolean visible) {
            boolean bool=isVisible();
            if (visible) {
                Rectangle screenSize = Utils.getScreenSize();
                setBounds(screenSize.x+screenSize.width-305, screenSize.y,
                          300, screenSize.height-5);
                if (!bool && !nativeTrans)
                    bg=Utils.getBackgroundCap(getBounds());
            }
            super.setVisible(visible);
        }
        
        /**
         * Paints the window contents.
         * @param rd a graphics2D object received from the original paint event.
         */
        public void render(Graphics2D rd) {
            finished=false;
            int x=0,y=getHeight();
            long l=System.currentTimeMillis();
            if (windows.isEmpty()) finished=true;
            int cur=Cursor.DEFAULT_CURSOR;
            if (!nativeTrans) rd.drawImage(bg, 0, 0, this);
            for (int i=0;i<windows.size();i++) {
                DesktopNotify window=windows.get(i);
                if (window.isVisible()) {
                    y-=window.h;
                    if (window.popupStart==0)
                        window.popupStart=System.currentTimeMillis();
                    if (y>0) {
                        boolean hover=false;
                        Point p=getMousePosition();
                        if (p!=null) {
                            if (p.y>y && p.y<y+window.h) {
                                hover=true;
                                if (window.getAction()!=null)
                                    cur=Cursor.HAND_CURSOR;
                                if (clicked) {
                                    if (window.getAction()!=null) {
                                        final DesktopNotify w=window;
                                        final long lf=l;
                                        java.awt.EventQueue.invokeLater(new Runnable(){@Override public void run(){
                                            w.getAction().actionPerformed(new ActionEvent(w, ActionEvent.ACTION_PERFORMED, "fireAction", lf, 0));
                                        }});
                                    } if (window.expTime()==Long.MAX_VALUE) {
                                        window.timeOut=l-window.popupStart+500;
                                    }
                                }
                            }
                        }
                        window.render(x,y,hover,rd,l);
                    } else window.popupStart=l;
                    if (l>window.expTime()) {
                        window.setVisible(false);
                        windows.remove(window); 
                        i--;
                    }
                    y-=5;
                }
            }
            clicked=false;
            setCursor(new Cursor(cur));
        }
    }
}