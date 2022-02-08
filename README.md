#Aurora Alert System

This is a small project to create an alert system for those hoping to catch a glimpse of the aurora borealis. The script is designed to query the NOAA Space Weather Prediction Center [https://www.swpc.noaa.gov/] every *t* minutes, providing an assesment of current geomagnetic disturbances in Earth's magnetic field in a desktop notification. These disturbances are measured on the Kp-index; you can learn more about the Kp-index and find out the minimum geomagnetic activity necessary to see the aurora borealis in your region at [https://seetheaurora.com/kp-index-explained]. There is also an option for an audible alert.

To run the script, navigate to the folder it is in and run:

`python main.py -t <query interval> -k <minimum Kp for chime>`

The query interval is measured in minutes. I recommend you set the -k flag to whatever is the minimum Kp needed for a visible aurora in your region.

This project is still under development, and right now it works best on Linux distributions, but I have confirmed that it *will* run on Windows; however, the notifications will go straight to the tray, and not pop up. Eventually, I want to have an android app but that may take a while.
