package serveur.fonctionTest;import java.io.FileWriter;import java.io.IOException;/** * Created by François on 13-11-16. * !!!!!!!!!!!!!!Classe uniquement utilisée pour faire des tests !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */public class test {    public static void main(String[] args) {        try {            FileWriter fw = new FileWriter("salut.txt");            System.out.println(fw);        } catch (IOException e) {            e.printStackTrace();        }    }        /*java.util.Date date = new java.util.Date();        //date.setTime(1000000);        System.out.println("voici le test :" + date.getHours());        System.out.println("voici le jour"+ date.getDay());        date.setMonth(0);        System.out.println(date);*//*        try {            Class.forName("org.sqlite.JDBC");        } catch (ClassNotFoundException e) {            e.printStackTrace();        }        System.out.println("Driver O.K.");        Connection conn = null;        try {            conn = DriverManager.getConnection("jdbc:sqlite:C:\\ProjetIndividuel2\\INFOB318-16-17-pds\\project\\BaseDeDonnees.db");        } catch (SQLException e) {            e.printStackTrace();        }        System.out.println("Opened database successfully");            String sql = "CREATE TABLE `Boite2` ("+       "`Jour`	INTEGER NOT NULL,"+        "`Heure`	INTEGER NOT NULL,"+        "`Ind`	INTEGER NOT NULL,"+       " `Valeur`	INTEGER NOT NULL"+       " );";            System.out.println(sql);        Statement state = null;        try {            state = conn.createStatement();        } catch (SQLException e) {            e.printStackTrace();        }        try {            state.executeUpdate(sql);        } catch (SQLException e) {            e.printStackTrace();        }            /*System.out.println(date.getDay());            File f2 = new File("C:\\wamp\\www\\data.csv") ;//fichier data pour mapage3            ResourceBundle rb = ResourceBundle.getBundle("serveur.domaine.properties.config");            String driver = rb.getString("sgbd.driver");            System.out.print(driver);        try {            Process proc = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe http://localhost/mapage2");        } catch (IOException e1) {                e1.printStackTrace();        }        //Runtime.getRuntime().ex*/       /*try{        InterfaceKitPhidget ik = new InterfaceKitPhidget();        System.out.println(Phidget.getLibraryVersion());        ik.addDetachListener(new DetachListener() {            public void detached(DetachEvent ae) {                System.out.println("detachment of " + ae);            }        });        ik.addErrorListener(new ErrorListener() {            public void error(ErrorEvent ee) {                System.out.println("error event for " + ee);            }        });            ik.addSensorChangeListener(new SensorChangeListener() {                @Override                public void sensorChanged(SensorChangeEvent sensorChangeEvent) {                    System.out.println(sensorChangeEvent.getValue());                }            });            ik.openAny();            java.util.Date date = new java.util.Date();            System.out.println(date);            System.out.println("waiting for InterfaceKit attachment...");            ik.waitForAttachment();            System.out.println(ik.getDeviceName());            ik.setOutputState(0,true);            for (int i = 0; i < 100; i++) {                double x = ik.getSensorValue(0);                System.out.println("test : "+x);                Thread.sleep(1000);            }        } catch (PhidgetException e) {            e.printStackTrace();        } catch (InterruptedException e) {            e.printStackTrace();        }        System.out.println(jourAnnee(3,1));    }    public static int jourAnnee(int j,int m){        if(m==0){return j;}        if(m==1){return j+31;}        if (m==2){return 31+28+j;}        if (m % 2 ==0){return jourAnnee(j,m-1)+30;}        else{return jourAnnee(j,m-1)+31;}    }*/      /*  while (true) {            ResourceBundle rb = ResourceBundle.getBundle("serveur.domaine.properties.config");            // String nf1 = "C:\\wamp\\www\\data1.tsv";            String test = rb.getString("test");            System.out.println(test);            try {                Thread.sleep(1000);            } catch (InterruptedException e) {                e.printStackTrace();            }        }*/       /* try {            Process proc = Runtime.getRuntime().exec("java -jar C:\\ProjetIndividuel\\project\\out\\artifacts\\ProjetIndividuel_jar\\ProjetIndividuel.jar");        } catch (IOException e) {            e.printStackTrace();        }*/}