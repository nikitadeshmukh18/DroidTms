package com.example.TMSAndroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.sax.Element;
import android.view.View;
import model.Bus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import utility.LogOutManager;

import javax.xml.parsers.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConductorActivity extends Activity implements LogOutManager{

    List<Bus> busList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conductor);



        try {
           busList = getBusContent();
        } catch (IOException e) {

        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        displayBusListInComboBox(busList);

    }

    private void displayBusListInComboBox(List<Bus> busList) {

        Iterator iterator = busList.iterator();

    }

    public void controlBus(View view){
        Intent intent = new Intent(getApplicationContext(),BusManager.class);
        startActivity(intent);

    }


    @Override
    public void logOut(View view) {
        Intent intent = new Intent(getApplicationContext(),MyActivity.class);
        startActivity(intent);
    }

    public List<Bus> getBusContent() throws IOException, ParserConfigurationException, SAXException {
        String url =  "http://172.16.171.212:8080/Sample/api/buses";
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(response.getEntity().getContent());
        Element element = (Element) doc.getElementById("busNo");
        NodeList busListXml = doc.getElementsByTagName("bus");
        List<Bus> busList = new ArrayList<Bus>();
        for(int i = 0; i< busListXml.getLength() ; i++){
            List<String> busObject = new ArrayList<String>();
            Node node = busListXml.item(i);
            NodeList childNodes = node.getChildNodes();
            for (int j=0 ; j<childNodes.getLength();j++){
                Node temp = childNodes.item(j);
                busObject.add(temp.getTextContent());
            }

            Bus bus = new Bus();
            Iterator iterator = busObject.iterator();
            bus.setBusDestination((String) iterator.next());
            bus.setBusNo((Integer) iterator.next());
            bus.setBusSource((String) iterator.next());
            bus.setRouteId((Integer) iterator.next());
            bus.setStartTime((String) iterator.next());
            busList.add(bus);

        }

       return busList;
    }

}
