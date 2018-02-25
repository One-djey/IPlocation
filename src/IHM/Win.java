package IHM;

import COM.IPinfo;
import COM.IPlocator;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class Win extends JFrame implements ActionListener {
    static final long serialVersionUID = 712286517053415187L;;
    static final String key = "d57b1e68028f1e5629d553346e48f7f2457981a385726f9c6daf4ddff36f5eb6";
    JTextField ipText;
    JLabel country;
    JLabel region;
    JLabel city;
    JLabel latitude;
    JLabel longitude;
    JLabel time;
    JLabel error;

    public Win() {
        super("Jérémy 2.0");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 300);
        this.setLocation(200, 200);

        Container contentPane = getContentPane();
        createErrorField(contentPane);
        createInfoField(contentPane);
        createButtonBar(contentPane);

        setVisible(true);
    }

    public void createErrorField(Container contentPane){
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new FlowLayout());
        error = new JLabel("No error(s)");
        error.setForeground(Color.GRAY);
        errorPanel.add(error);

        contentPane.add(errorPanel,BorderLayout.NORTH);
    }

    public void createInfoField(Container contentPane){

        JPanel infoField = new JPanel();
        infoField.setLayout(new GridLayout(7, 2));

        JLabel ipLabel = new JLabel("ipAddress :");
        ipText= new JTextField("");
        JLabel countryLabel = new JLabel("countryName :");
        country= new JLabel("---");
        JLabel regionLabel = new JLabel("regionName :");
        region= new JLabel("---");
        JLabel cityLabel = new JLabel("cityName :");
        city= new JLabel("---");
        JLabel latitudeLabel = new JLabel("latitude :");
        latitude= new JLabel("---");
        JLabel longitudeLabel = new JLabel("longitude :");
        longitude= new JLabel("---");
        JLabel timeLabel = new JLabel("timeZone :");
        time= new JLabel("---");

        infoField.add(ipLabel);
        infoField.add(ipText);
        infoField.add(countryLabel);
        infoField.add(country);
        infoField.add(regionLabel);
        infoField.add(region);
        infoField.add(cityLabel);
        infoField.add(city);
        infoField.add(latitudeLabel);
        infoField.add(latitude);
        infoField.add(longitudeLabel);
        infoField.add(longitude);
        infoField.add(timeLabel);
        infoField.add(time);

        contentPane.add(infoField,BorderLayout.CENTER);
    }

    private void createButtonBar(Container contentPane) {
        JPanel buttonBar = new JPanel();
        buttonBar.setLayout(new FlowLayout());

        JButton send = new JButton("Send");
        JButton fill = new JButton("My IP");
        JButton clear = new JButton("Clear");

        send.setActionCommand("send");
        fill.setActionCommand("fill");
        clear.setActionCommand("clear");

        send.addActionListener(this);
        fill.addActionListener(this);
        clear.addActionListener(this);

        buttonBar.add(send);
        buttonBar.add(fill);
        buttonBar.add(clear);

        contentPane.add(buttonBar, "South");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println(e.getActionCommand());
        switch(e.getActionCommand()){
            case "send":
                send();
                break;
            case "fill":
                fill();
                break;
            case "clear":
                clear();
                break;
        }
    }

    public void errorMessage(String msg,Color color){
        error.setForeground(color);
        error.setText(msg);
    }

    private boolean validIP (String ip) {
        try {
            if ( ip == null || ip.isEmpty() ) {
                return false;
            }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            if ( ip.endsWith(".") ) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private void send(){
        errorMessage("Loading ...",Color.GRAY);
        String currentip = ipText.getText();
        if(validIP(currentip)) {
            IPlocator loc = new IPlocator(currentip,key);
            IPinfo info;
            try {
                info = loc.getIPinfo();
                country.setText(info.getCountryName());
                region.setText(info.getRegionName());
                city.setText(info.getCityName());
                latitude.setText(info.getLatitude());
                longitude.setText(info.getLongitude());
                time.setText(info.getTimeZone());
                errorMessage("Informations received",new Color(50,180,50));
            }  catch (SAXException e) {
                errorMessage("SAXException",Color.RED);
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                errorMessage("ParserConfigurationException",Color.RED);
                e.printStackTrace();
            }catch (IOException e) {
                errorMessage("IOException",Color.RED);
                e.printStackTrace();
            }
        }
        else{
            errorMessage("'"+currentip+"' is not an IP address", Color.RED);
        }
    }

    private void fill(){
        clear();
        errorMessage("Loading ...",Color.GRAY);
        String url = "http://checkip.amazonaws.com";
        try {
            ipText.setText(InetAddress.getLocalHost().getHostAddress());
            URL whatismyip = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            String ip = in.readLine(); //you get the IP as a String
            //System.out.println(ip);
            ipText.setText(ip);
            errorMessage("Your IP address is updated",new Color(50,180,50));
        } catch (UnknownHostException e1) {
            errorMessage("Unknown Host '"+url+"'",Color.RED);
            e1.printStackTrace();
        } catch (MalformedURLException e) {
            errorMessage("Malformed URL '"+url+"'",Color.RED);
            e.printStackTrace();
        } catch (IOException e) {
            errorMessage("IOException",Color.RED);
            e.printStackTrace();
        }
    }

    private void clear(){
        ipText.setText("");
        country.setText("---");
        region.setText("---");
        city.setText("---");
        latitude.setText("---");
        longitude.setText("---");
        time.setText("---");
        errorMessage("No error(s)",Color.GRAY);
    }

}
