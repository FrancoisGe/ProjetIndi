package Client;

import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;

/**
 * Created by François on 03-12-16.
 */
public class LedTemp implements Runnable{

    private InterfaceKitPhidget ik;

    public LedTemp(InterfaceKitPhidget ik){
        this.ik =ik;
    }
    @Override
    public void run() {
        while (true) {
            try {


                int val = ik.getSensorValue(0);
                for (int j = 1; j < 6; j++) {
                    ik.setOutputState(j, false);

                }
                System.out.println(val);
                ik.setOutputState(1, true);
                if (val > 400) {
                    ik.setOutputState(2, true);
                    if (val > 410) {
                        ik.setOutputState(3, true);
                        if (val > 420) {
                            ik.setOutputState(4, true);
                            if (val > 450) {
                                ik.setOutputState(5, true);
                                if (val > 500) {
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
}
