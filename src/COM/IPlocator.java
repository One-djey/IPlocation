package COM;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IPlocator {
    private String ip;
    private String format;
    private String key;

    public IPlocator(String ip,String key) {
        this.ip = ip;
        this.key = key;
    }

    public String getXML() throws IOException {
        HttpURLConnection connection = null;
        if(this.format != "xml") this.format = "xml";
        String xml="";

        try {
            URL monURL = new URL("http://api.ipinfodb.com/v3/ip-city/?key="+this.key+"&ip="+ this.ip + "&format=" + this.format );

            //System.out.println("Connexion a l'url ...");
            connection = (HttpURLConnection) monURL.openConnection();
            //System.out.println(connection.getRequestMethod());
            //System.out.println(connection.getResponseMessage());
            //System.out.println(connection.getHeaderFields());

            BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                xml+=inputLine;

            in.close();
        } finally {
            connection.disconnect();
        }
        return xml;
    }

    public IPinfo getIPinfo() throws IOException, ParserConfigurationException, SAXException {
        IPinfo info;
        String xml = this.getXML();
        String statusCode;
        String ipAddress;
        String countryCode;
        String countryName;
        String regionName;
        String cityName;
        String zipCode;
        String latitude;
        String longitude;
        String timeZone;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8.name()));
        Document d = db.parse(stream);

        Element root = d.getDocumentElement();
        NodeList nodeList;

        nodeList = root.getElementsByTagName("statusCode");
        statusCode = (nodeList.getLength() > 0)? nodeList.item(0).getFirstChild().getNodeValue() : "";
        nodeList = root.getElementsByTagName("ipAddress");
        ipAddress = (nodeList.getLength() > 0)? nodeList.item(0).getFirstChild().getNodeValue() : "";
        nodeList = root.getElementsByTagName("countryCode");
        countryCode =  (nodeList.getLength() > 0)? nodeList.item(0).getFirstChild().getNodeValue() : "";
        nodeList = root.getElementsByTagName("countryName");
        //System.out.println("-------------"+ nodeList.item(0).getFirstChild().getNodeValue()+"-------------------");
        countryName = (nodeList.getLength() > 0)? nodeList.item(0).getFirstChild().getNodeValue() : "";
        nodeList = root.getElementsByTagName("regionName");
        regionName = (nodeList.getLength() > 0)? nodeList.item(0).getFirstChild().getNodeValue():"";
        nodeList = root.getElementsByTagName("cityName");
        cityName = (nodeList.getLength() > 0)? nodeList.item(0).getFirstChild().getNodeValue() : "";
        nodeList = root.getElementsByTagName("zipCode");
        zipCode = (nodeList.getLength() > 0)? nodeList.item(0).getFirstChild().getNodeValue() : "";
        nodeList = root.getElementsByTagName("latitude");
        latitude = (nodeList.getLength() > 0)? nodeList.item(0).getFirstChild().getNodeValue() : "";
        nodeList = root.getElementsByTagName("longitude");
        longitude = (nodeList.getLength() > 0)? nodeList.item(0).getFirstChild().getNodeValue() : "";
        nodeList = root.getElementsByTagName("timeZone");
        timeZone = (nodeList.getLength() > 0)? nodeList.item(0).getFirstChild().getNodeValue() : "";

        info = new IPinfo(statusCode,ipAddress,countryCode,countryName,regionName,cityName,zipCode,latitude,longitude,timeZone);
        return info;
    }

}
