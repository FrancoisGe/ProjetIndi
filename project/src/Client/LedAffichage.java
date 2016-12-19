package Client;

import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;

/**
 * Created by François on 03-12-16.
 */
public class LedAffichage implements Runnable{

    private InterfaceKitPhidget ik;
    private int min=1000;
    private int dif;
    private boolean isRun;


    public LedAffichage(InterfaceKitPhidget ik, int dif){
        this.ik =ik;

        this.dif = dif;
    }
    @Override
    public void run() {
        while (isRun) {
            try {



                int val = ik.getSensorValue(0);
                if(val<min){val=min;}
                for (int j = 1; j < 6; j++) {
                    ik.setOutputState(j, false);

                }


                if (val > min) {
                    ik.setOutputState(1, true);
                    if (val > min+dif) {
                        ik.setOutputState(2, true);
                        if (val > min+dif*2) {
                            ik.setOutputState(3, true);
                            if (val >min+dif*3) {
                                ik.setOutputState(4, true);
                                if (val > min+dif*5) {
                                    ik.setOutputState(5, true);
                                    if (val>min+dif*6){
                                        ik.setOutputState(6,true);
                                    }
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
