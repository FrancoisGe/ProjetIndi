package Client;

import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;

/**
 * Created by François on 03-12-16.
 */
public class LedAffichage implements Runnable{

    private InterfaceKitPhidget ik;
    private int min;
    private int dif;
    private boolean isRun;

    public LedAffichage(InterfaceKitPhidget ik, int min, int dif){
        this.ik =ik;
        this.min = min;
        this.dif = dif;
    }
    @Override
    public void run() {
        while (isRun) {
            try {


                int val = ik.getSensorValue(0);
                for (int j = 1; j < 6; j++) {
                    ik.setOutputState(j, false);

                }
                System.out.println(val);
                ik.setOutputState(1, true);
                if (val > min) {
                    ik.setOutputState(2, true);
                    if (val > min+dif) {
                        ik.setOutputState(3, true);
                        if (val > min+dif*2) {
                            ik.setOutputState(4, true);
                            if (val >min+dif*3) {
                                ik.setOutputState(5, true);
                                if (val > min+dif*5) {
                                    ik.setOutputState(6, true);
                                }

                            }

                        }
                    }
                }
                Thread.sleep(100);
            } catch (PhidgetException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public void stopRun(){
        isRun=false;
    }
}
