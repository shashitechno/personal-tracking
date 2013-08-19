import java.io.IOException;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.*;
import javax.microedition.media.*;
import javax.microedition.media.control.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.media.control.VideoControl;
import javax.microedition.lcdui.*;
import javax.microedition.location.Coordinates;
import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationProvider;

import com.sun.midp.io.Base64;

public class myMidlet extends MIDlet implements CommandListener{
	private Display display;
    private Form form;
    private Command exit, back, capture, camera, send,info;
    private Player player;
    private VideoControl videoControl;
    private Video video;
    private Image img;
    String s="Animal Tracking System";
    

    int status = 0;
    byte localData[];
    
    public myMidlet() {
	display = Display.getDisplay(this);
	form = new Form("My Form");
	try
	{
		img=Image.createImage("/sagavisions_logo.gif");
				
	}
	catch(Exception e)
	{}
        exit = new Command("Exit", Command.EXIT, 0);
        camera = new Command("Camera", Command.SCREEN, 1);
        back = new Command("Back", Command.BACK, 2);
        capture = new Command("Capture", Command.SCREEN, 3);
        send = new Command("Send", Command.OK, 1);
        info=new Command("Enter Info",Command.SCREEN,4);
        form.addCommand(camera);
        form.addCommand(exit);
        form.setCommandListener(this);
        try
        {
        form.append(img);
        }
        catch(Exception e){}
        form.append(s);
    }

    public void startApp() {
        display.setCurrent(form);
    }

    public void pauseApp() {}

    public void destroyApp(boolean unconditional){
		notifyDestroyed();
	}

    public void commandAction(Command c, Displayable s){
		String label = c.getLabel();
        if (label.equals("Exit")){
            destroyApp(true);
        } else if (label.equals("Camera")) {
            showCamera();
            form.delete(0);
        } else if (label.equals("Back"))
            display.setCurrent(form);
        else if (label.equals("Capture")) {
            video = new Video(this);
            video.start();
            form.addCommand(info);
            form.addCommand(send);
            form.removeCommand(camera);
        }
        else if( label.equalsIgnoreCase("Send") ){
            try {
                startSendOperation();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
        }
        else if(label.equalsIgnoreCase("Enter Info"))
        { 
        	
        	new loctest(this);
        }
        

    }
        public boolean uploadImage( String uri, byte[] rawImage)throws Exception
    {

    HttpConnection httpConnection;
    OutputStream    out;

    // Open connection to the script
    httpConnection = (HttpConnection)Connector.open( uri );

    // Setup the request as an HTTP POST and encode with form data
    httpConnection.setRequestMethod( HttpConnection.POST );
    httpConnection.setRequestProperty( "Content-type", "application/x-www-form-urlencoded" );

    // Encode the imagedata with Base64
    String encoded = Base64.encode( rawImage, status, status ).toString();

    // Build the output and encoded string
    String    output    = "imgdata=" + encoded;

    // Set the content length header
    httpConnection.setRequestProperty("Content-Length", Integer.toString((output.getBytes().length)));

    // Open the output stream and publish data
    out = httpConnection.openOutputStream();
    out.write( output.getBytes() );

    // Flush the buffer (might not be necessary?)
    out.flush();

    // Here you might want to read a response from the POST to make
    // sure everything went OK.

    // Close everything down
    if( out != null )
    if( httpConnection != null )
    httpConnection.close();

    // All good
    return true;
    }

    public void startSendOperation() throws Exception{

        boolean res = uploadImage( "http://localhost/1/upload.php", localData);

    }
    


    public void showCamera(){
        try{
            player = Manager.createPlayer("capture://video");
            player.realize();
            videoControl = (VideoControl)player.getControl("VideoControl");
            Canvas canvas = new VideoCanvas(this, videoControl);
            canvas.addCommand(back);
            canvas.addCommand(capture);
            canvas.setCommandListener(this);
            display.setCurrent(canvas);
            player.start();
        } catch (IOException ioe) {} catch (MediaException me) {}
    }

    class Video extends Thread {
        myMidlet midlet;
        public Video(myMidlet midlet) {
            this.midlet = midlet;
        }

        public void run() {
            captureVideo();
        }
        

        public void captureVideo() {
            try {
                byte[] photo = videoControl.getSnapshot(null);
                localData = photo;
                Image image = Image.createImage(photo, 0, photo.length);

                form.append(image);
                display.setCurrent(form);
                player.close();
                player = null;
                videoControl = null;
            } catch (MediaException me) { }
        }
    };
}

class VideoCanvas extends Canvas {
    private myMidlet midlet;

    public VideoCanvas(myMidlet midlet, VideoControl videoControl) {
        int width = getWidth();
        int height = getHeight();
        this.midlet = midlet;

        videoControl.initDisplayMode(VideoControl.USE_DIRECT_VIDEO, this);
        try {
            videoControl.setDisplayLocation(2, 2);
            videoControl.setDisplaySize(width - 4, height - 4);
        } catch (MediaException me) {}
        videoControl.setVisible(true);
    }

    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        g.setColor(255, 0, 0);
        g.drawRect(0, 0, width - 1, height - 1);
        g.drawRect(1, 1, width - 3, height - 3);
    }



}
class loctest implements CommandListener{

	  //  private Display display;
	    public Form form;
	    public Command cmdExit,Send,Store,cmdok;
	    public StringItem stiInfo;
	    public String Animal_cat,Location,Animal_name,Field_Force;
	    public TextField Animal_tname,Animal_tcat,Animal_tFieldForce,tlat,tlon;
		Display display;
		 myMidlet midlet;
		 
	    public loctest(myMidlet midlet) {
	    	
	    	 this.midlet=midlet;
	    	System.out.println("Entered in loctest");
	       display=Display.getDisplay(midlet);
	        form = new Form("Enter The Information");
			
	        cmdExit = new Command("Exit", Command.EXIT, 4);
	        Send = new Command("Send", Command.SCREEN, 2);
	        Store =new Command("Store",Command.SCREEN,3);
	        stiInfo = new StringItem("Geo Location", "Click OK");
	        cmdok=new Command("Ok",Command.BACK,1);
	        form.append(stiInfo);
	        form.addCommand(cmdExit);
	        form.addCommand(Send);
	        form.addCommand(Store);
	        form.addCommand(cmdok);
	        Animal_tname=new TextField("Enter Animal name:", Animal_name, 30,TextField.ANY);
			Animal_tcat=new TextField("Enter Animal Categary", Animal_cat, 30,TextField.ANY);
			Animal_tFieldForce=new TextField("Field Force", Field_Force, 30, TextField.ANY);
			tlat=new TextField("Latitude", "", 30,TextField.ANY);
			tlon=new TextField("Longitude","",30,TextField.ANY);
			Animal_tname.setString(Animal_name);
			Animal_tcat.setString(Animal_cat);
			Animal_tFieldForce.setString(Field_Force);
			form.append(Animal_tname);
			form.append(Animal_tcat);
			form.append(Animal_tFieldForce);
			
	        form.setCommandListener(this);
	        display.setCurrent(form);
	    }

	    

	    public void commandAction(Command c, Displayable d) {
	        //throw new UnsupportedOperationException("Not supported yet.");
	        if(c ==Send) {
	        	 try {
	                midlet.startSendOperation();
	             } catch (Exception ex) {
	                 ex.printStackTrace();
	             }
	             
	        } else if(c == cmdExit) {
	           midlet.destroyApp(false);
	        }
	        else if(c==Store)
	        {
	        	new ReadWriteRMS(Animal_tname.getString(),Animal_tcat.getString(),Animal_tFieldForce.getString(),tlat.getString(),tlon.getString());
	        }
	        else if(c==cmdok)
	        {
	        	Retriever obj=new Retriever(this);
	        	obj.start();
	        }
	    }

	    public void displayString(String string) {
	        stiInfo.setText(string);
	    }

	}
class Retriever extends Thread implements CommandListener{

    loctest midlet;
   double lat,lon;

	

	


    public Retriever(loctest midlet) {
        System.out.println("entered Retriever");
      
        this.midlet = midlet;
        
		
				
	       
	   
	        
    }

    public void run() {
        //while(true) {
            try{
                this.sleep(5000);
                checkLocation();
            }catch(InterruptedException ie) {
                ie.printStackTrace();
                midlet.displayString("InterruptedException: " + ie.getMessage());
            }catch(Exception e) {
                e.printStackTrace();
                midlet.displayString("checkLocationException: " + e.getMessage());
            }
        //}
    }

    public void checkLocation() throws Exception {
        String string;
        Location l;
        LocationProvider lp;
        Coordinates c;
        // Set Criteria on which to base the selection of the location provider
        Criteria cr = new Criteria();
        // Set horizontal accuracy to 500 meters
        cr.setHorizontalAccuracy(50);
        // Get the instance of the locationProvider
        lp = LocationProvider.getInstance(cr);
        // Request the location with 1 minute timeout
        l = lp.getLocation(60);
        c = l.getQualifiedCoordinates();

        if(c != null) {
            // Got some nice location, now extract them
            
        	lat = c.getLatitude();
             lon = c.getLongitude();
            string = "\nLatitude: " + lat + "; Longitude: " + lon;
        }else {
            string = "Location API failed!";
        }
                  midlet.tlat.setString(lat+"");
                  midlet.tlon.setString(lon+"");
            midlet.displayString(string);   
    }

	public void commandAction(Command arg0, Displayable arg1) {
		// TODO Auto-generated method stub
		
	}
}
