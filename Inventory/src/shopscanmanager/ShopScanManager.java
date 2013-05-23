/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;

/**
 *
 * @author xkalibaer
 */
public class ShopScanManager {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        Thread mSplashThread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        // Wait given period of time or exit on touch
                        Splash splash=new Splash();
                        wait(10000);
                        splash.setVisible(false);
                        HomeScreen homeScreen=new HomeScreen();
                        homeScreen.setVisible(true);
                    }
                }
                catch(InterruptedException ex){                    
                    new UtilUse().Log(ex);
                }

                
                
                //finish();
                
                // Run next activity
                
                //stop();                    
            }
        };
        
        mSplashThread.start();    
        
    }
}
