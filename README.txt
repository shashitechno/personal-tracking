A. Setup Database:
	1. Go to "ServerSide-setup" folder. Run "current_location.sql" script to your databse to create tables for the application.
	2. Open "config.inc.php" file and provide necessary information (username, password, hostname) of your database setup.

B. Setup MobileApp:
	1. "MapMe" is a Netbeans project. So, it will be easy to open it with Netbeans. It's also possible to compile it with j2ME wireless toolkit's control panel.
	2. I have used j2me wireless toolkit 2.5.2 beacuse it has Location API support. Older version may not have this support.
	3. If Netbeans shows reference problem, Right click on the project and bring up the project properties Window. In the "Optional Packages" list at Right-Bottom, check the checkbox "Location Based Api"
	4. In "MapMe.java" line 21, change the "URL" field according to your setup.
	5. In "MapViewer.java" line 20, provide your google map API key. (for google map api version-2)
	
C. Setup WebApp:
	1. Open "Functions\DBInteractor.php" file and provide provide necessary information (username, password, hostname) of your database setup.	

 