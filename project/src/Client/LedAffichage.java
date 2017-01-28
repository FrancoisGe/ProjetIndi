package Client;

import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;

/**
 * Created by François on 03-12-16.
 */
public class LedAffichage implements Runnable{

    private InterfaceKitPhidget ik;
    private int min=4000;
    private int max =0;
    private int dif;//valeur contenant l'écart de valeur qu'il faut entre l'allumage de 2 leds.
    private boolean isRun=true;


    public LedAffichage(InterfaceKitPhidget ik){
        this.ik =ik;

    }
    @Override
    public void run() {
        while (isRun) {

            try {

                int val = ik.getSensorValue(0)+ik.getSensorValue(2)+ik.getSensorValue(4)+ik.getSensorValue(6);

                if(val>max){max=val;}

                if(val<min){min=val;}

                dif = (max-min)/6;

                //éteind les leds.
                //On utilise les 0 à 6 port out de l'interfacePhidget
                for (int j = 1; j < 6; j++) {
                    ik.setOutputState(j, false);

                }

                //allume les leds selon la valeur donnée par les sensors (plus la valeur est importante plus le nombre de leds allumées est élevé)
                if (val > min) {
                    ik.setOutputState(0, true);
                    if (val > min+dif) {
                        ik.setOutputState(1, true);
                        if (val > min+dif*2) {
                            ik.setOutputState(2, true);
                            if (val >min+dif*3) {
                                ik.setOutputState(3, true);
                                if (val > min+dif*5) {
                                    ik.setOutputState(4, true);
                                    if (val>min+dif*6){
                                        ik.setOutputState(5,true);
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
