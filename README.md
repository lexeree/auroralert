# Aurora Alert System

This is a small project aimed at creating an alert system for those hoping to catch a glimpse of the aurora borealis. The script is designed to query the NOAA Space Weather Prediction Center [https://www.swpc.noaa.gov/] every *t* minutes, providing an assesment of current geomagnetic disturbances in Earth's magnetic field in a desktop notification. These disturbances are measured on the Kp-index; you can learn more about the Kp-index and find out the minimum geomagnetic activity necessary to see the aurora borealis in your region at [https://seetheaurora.com/kp-index-explained]. 

So far, I know these work on Ubuntu and Windows. If anyone can test it on other Linux distributions or Mac let me know.

Eventually, I want to create an android app...but that may take a while. If anyone has any suggestions on useful features to implement, let me know!


### Python Script

The auroralert was originally implemented as a simple python script. The program is designed to provide desktop notifications at regular intervals and instigate an audible notification when the actual current Kp estimated by NOAA is above a certain threshold.

You can download the python script and run:

`python main.py -t <query interval> -k <minimum Kp for sound>`

The query interval is measured in minutes. I recommend you set the -k flag to whatever is the minimum Kp needed for a visible aurora in your region

The script will run indefinitely; press ctrl+c to exit.


### Java Command Line Tool

The java implementation is a little more advanced; to run it, download `auroralert.jar` and run:

`java -jar auroralert.jar <query interval>`

where again, the query interval is in minutes. The implementation uses AWT to send notification...if anyone knows of a better cross-platform option let me know!

When you enter the above script in the command line, you will be asked to choose a mode. Your current options are:
- `update` will give you regular silent updates on current Kp levels, both estimated (current), or predicted (in one hour).
- `manual` allows you to manually set the minimum Kp for an alert; these alerts only give the estimated (current) Kp levels, and will (on most platforms) be accompanied by a small sound. 
- `geographic` will prompt you for your geographic latitude and then your geographic longitude. Then it will tell you the minimum Kp levels you need to see the aurora in your regions (more details about how I computed this can be found in the comments of the file `Coordinates.java`), and automatically alert you whenever this minimum is reached, in the same way the `manual` option does.

Again, this runs indefinitely; press ctrl+c to exit.


