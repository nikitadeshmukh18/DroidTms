package com.example.TMSAndroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.sax.Element;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import model.Bus;
import model.BusStop;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyActivity extends Activity implements AdapterView.OnItemSelectedListener{

    private List<BusStop> stops;
    private String source;
    private String destination;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try {
            stops = getBusStops();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        setSourceList();
        setDestinationList();

    }

    private void setDestinationList() {
        Spinner destSpinner = (Spinner) findViewById(R.id.spinner1);
        Iterator iterator = stops.iterator();
        destSpinner.setOnItemSelectedListener(this);

        List<String> stopNames = new ArrayList<String>();
        while(iterator.hasNext())
        {
            stopNames.add(((BusStop) iterator.next()).getStopName());
        }
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,stopNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        destSpinner.setAdapter(spinnerAdapter);

    }

    private void setSourceList() {
        Spinner sourceSpinner = (Spinner) findViewById(R.id.spinner);
        Iterator iterator = stops.iterator();
        sourceSpinner.setOnItemSelectedListener(this);

        List<String> stopNames = new ArrayList<String>();
        while(iterator.hasNext())
        {
            stopNames.add(((BusStop) iterator.next()).getStopName());
        }
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,stopNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sourceSpinner.setAdapter(spinnerAdapter);

    }


    public void loginForConductor(View v){
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }


    public List<BusStop> getBusStops() throws IOException, ParserConfigurationException, SAXException {


        String url =  "http://172.16.171.215:8080/Sample/api/stops";
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(response.getEntity().getContent());
        NodeList stopsXml = doc.getElementsByTagName("stop");
        List<BusStop> stopList = new ArrayList<BusStop>();
        for(int i = 0; i< stopsXml.getLength() ; i++){
            List<String> stopObject = new ArrayList<String>();
            Node node = stopsXml.item(i);
            NodeList childNodes = node.getChildNodes();
            for (int j=0 ; j<childNodes.getLength();j++){
                Node temp = childNodes.item(j);
                stopObject.add(temp.getTextContent());
            }

            BusStop busStop = new BusStop();
            Iterator iterator = stopObject.iterator();
            busStop.setStopId(Integer.parseInt((String) iterator.next()));
            busStop.setStopName((String) iterator.next());
            stopList.add(busStop);

        }





        return stopList;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

        String tag = (String) adapterView.getTag();
        String val = (String) adapterView.getItemAtPosition(pos);
        if(tag.equals("source")){
            this.source = (String) adapterView.getItemAtPosition(pos);
        }
        else {
            this.destination = (String) adapterView.getItemAtPosition(pos);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


}
