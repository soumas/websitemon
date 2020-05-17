# websitemon
websitemon is a simple java CLI tool which checks whether the call to one or more URLs is answered with the HTTP code 200 (OK). It also offers the option of notifying an email address if any check fails.
I have implemented the tool for my own use and would like to share it here - maybe it will also be helpful for someone else.

## basic usage
- Download latest version from https://soumasoft.com/websitemon/ and unzip websitemon.jar to a directory of your choice
- Open the command line interface of your system and navigate to the directory where websitemon.jar is placed
- prepare websitemon (init database) with the following command:
```
java -jar websitemon.jar -cmdInstall
```
- add the website(s) you want to monitor:
````
java -jar websitemon.jar -cmdAdd https://soumasoft.com;https://google.com;http://your-website.com
````
- let websitemon check website states
````
java -jar websitemon.jar -cmdCheck all   (note: '-cmdCheck all' is optional, because this is the default command)
````
