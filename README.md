# websitemon
websitemon is a simple java CLI tool which checks whether the call to one or more URLs is answered with the HTTP code 200 (OK). It also offers the option of notifying an email address if any check fails.
I have implemented the tool for my own use and would like to share it here - maybe it will also be helpful for someone else.

## basic usage
- Download latest version from https://soumasoft.com/websitemon/ and unzip websitemon.jar to a directory of your choice
- Open the command line interface of your system and navigate to the directory where websitemon.jar is placed
- prepare websitemon (init database) with following command:
```bash
java -jar websitemon.jar -cmdInstall
```
- add the website(s) you want to monitor:
````bash
java -jar websitemon.jar -cmdAdd https://soumasoft.com;https://google.com;http://your-website.com
````
- let websitemon check website states
````bash
java -jar websitemon.jar -cmdCheck all
# note: '-cmdCheck all' is optional, because this is the default command
````

## notification
If the setting 'settMailReceiver' is filled with an email address, websitemon will send a notification to this address whenever the HTTP response code is different than 200. Corresponding SMTP settings are of course also necessary to be able to send emails.

- Update the settings "settMailReceiver" and all SMTP-Settings with following command (replace example parameters and newlines before you copy the command into your CLI):
````bash
java -jar websitemon.jar -cmdSettPersist
  -settMailReceiver your.email@server.com
  -settSmtpHost smtp.yourmailserver.com
  -settSmtpSender sender@yourmailserver.com
  -settSmtpUser your_smtp_user
  -settSmtpPassword your_smtp_password
  -settSmtpPort 465
  -settSmtpSSL true
````
- now you can check your settings using following command:
````bash
java -jar websitemon.jar -cmdMailtest
````

## advanced usage
The main functions of websitemon have already been described. For information about further commands please use the help:
````bash
java -jar websitemon.jar -cmdHelp
````

## donation
websitemon is absolutely free. Nevertheless, I would appreciate a donation if you use and like the tool :)
https://soumasoft.com/websitemon/#donation
