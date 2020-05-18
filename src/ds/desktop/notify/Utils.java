/*
 * DS Desktop Notify
 * A small utility to show small notifications in your Desktop anytime!
 */
package ds.desktop.notify;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Utilitary class.
 * @author DragShot
 */
public class Utils {
    /** Integer ID for other Windows versions, like Me, 2K and 9x */
    public static final int WINDOWS = 0;
    /** Integer ID for Windows XP */
    public static final int WINDOWS_XP = 1;
    /** Integer ID for Windows Vista */
    public static final int WINDOWS_VISTA = 2;
    /** Integer ID for Windows 7 */
    public static final int WINDOWS_7 = 3;
    /** Integer ID for Windows 8 and 8.1 */
    public static final int WINDOWS_8 = 4;
    /** Integer ID for Windows 10 */
    public static final int WINDOWS_10 = 5;
    /** Integer ID for OSX Mac systems */
    public static final int MAC_OS = 9;
    /** Integer ID for Linux distros */
    public static final int LINUX = 10;
    /** Integer ID for Unix environments */
    public static final int UNIX = 11;
    /** Integer ID for other systems */
    public static final int OTHER = 20;
    
    /**
     * Integer ID that represents the current OS the VM is running on.
     * @see #WINDOWS
     * @see #MAC_OS
     * @see #LINUX
     * @see #UNIX
     * @see #OTHER
     */
    static int operativeSystem;
    /**
     * The area available to deploy the notifications window.
     */
    static Rectangle screen;
    
    static{
        String str=System.getProperty("os.name").toLowerCase();
        if(str.contains("windows")){
            if(str.contains("xp")){
                operativeSystem=WINDOWS_XP;
            }else if(str.contains("vista")){
                operativeSystem=WINDOWS_VISTA;
            }else if(str.contains("7")){
                operativeSystem=WINDOWS_7;
            }else if(str.contains("8")){
                operativeSystem=WINDOWS_8;
            }else if(str.contains("10")){
                operativeSystem=WINDOWS_10;
            }else{
                operativeSystem=WINDOWS;
            }
        }else if(str.contains("mac")){
            operativeSystem=MAC_OS;
        }else if(str.contains("linux")){
            operativeSystem=LINUX;
        }else if(str.contains("unix")){
            operativeSystem=UNIX;
        }else{
            operativeSystem=OTHER;
        }
    }
    
    /**
     * @return The integer ID that represents the current OS this VM is running
     *         on.
     * @see #WINDOWS
     * @see #MAC_OS
     * @see #LINUX
     * @see #UNIX
     * @see #OTHER
     */
    public static int getOperativeSystem(){
        return operativeSystem;
    }
    /**
     * An integer flag for <code>getScreenSize()</code>.<br/><br/>
     * Values:<br/>
     *  0: Check if sun.java2d.SunGraphicsEnvironment.getUsableBounds() is
     *     available.<br/>
     *  1: Class/method is available.<br/>
     * -1: Class/method is not available.
     */
    private static int doHack = 0;
    private static Method getUsableBounds = null;
    
    /**
     * Checks the area available in the desktop, excluding the taskbar.
     * In order to do this, an attempt to call
     * <code>sun.java2d.SunGraphicsEnvironment.getUsableBounds()</code> is
     * performed. If this can't be done, the method fallbacks to the default
     * <code>Toolkit.getDefaultToolkit().getScreenSize()</code>, although such
     * method doesn't exclude the taskbar area.
     * @return A Rectangle with the usable area for the notifications.
     */
    public static Rectangle getScreenSize(){
        //Class sunGE;
        //Method getUsableBounds = null;
        if (doHack == 0) {
            //Check if sun.java2d.SunGraphicsEnvironment.getUsableBounds()
            //is available.
            try {
                Class sunGE = Class.forName("sun.java2d.SunGraphicsEnvironment");
                Method[] meths = sunGE.getDeclaredMethods();
                doHack = -1;
                for (Method meth:meths) {
                    //System.out.println(meth.toString());
                    if (meth.getName().equals("getUsableBounds")
                        && Arrays.equals(meth.getParameterTypes(),
                                new Class[]{java.awt.GraphicsDevice.class})
                        && meth.getExceptionTypes().length == 0
                        && meth.getReturnType()
                                .equals(java.awt.Rectangle.class)) {
                        //We found it!
                        getUsableBounds = meth;
                        doHack = 1;
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                doHack = -1;
            }
        } if (doHack == 1/*Utils.getOperativeSystem()<9*/) { //Use a little hack
            try {
                Frame frame = new Frame();
                GraphicsConfiguration config = frame.getGraphicsConfiguration();
                //screen = sun.java2d.SunGraphicsEnvironment.getUsableBounds(config.getDevice());
                screen = (Rectangle)
                        getUsableBounds.invoke(null, config.getDevice());
                frame.dispose();
            } catch (Exception ex) {
                doHack = -1;
            }
        } if (doHack != 1) { //Do it the traditional way
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            screen=new Rectangle(0, 0, size.width, size.height);
        }
        System.out.println("Current workspace: "
                +screen.width+"x"+screen.height+"px");
        return screen;
    }
    
    /**
     * Checks if the translucency effect is supported. Java 6 does not support
     * this. Only Java 7 and higher VMs might do, depending of the Graohics
     * Environment and OS.
     * @return <code>true</code> if translucency is supported,
     *         <code>false</code> otherwise.
     */
    public static boolean isTranslucencySupported(){
        boolean nativeTrans;
        if (System.getProperty("java.version").contains("1.6")) {
            System.err.println("Per-pixel translucency is currently not "
                    + "supported.\nPlease upgrade your JRE to at least Java 7 "
                    + "to support this feature.");
            nativeTrans = false;
        } else {
            GraphicsEnvironment ge = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            if (!gd.isWindowTranslucencySupported(GraphicsDevice
                    .WindowTranslucency.PERPIXEL_TRANSLUCENT)) {
                System.err.println("Error while starting: "
                                 + "Per-pixel translucency is not supported.");
                nativeTrans = false;
            } else nativeTrans = true;
        }
        return nativeTrans;
    }
    
    /**
     * Creates a background for a fake effect of translucency.
     * @param bounds The area to capture.
     * @return A <code>BufferedImage</code> with the area behind the JDialog.
     */
    public static Image getBackgroundCap(Rectangle bounds){
        Image bg;
        try {
            Robot robot = new Robot();
            bg = robot.createScreenCapture(bounds);
        } catch (AWTException ex) {
            bg=null;
        }
        return bg;
    }
}