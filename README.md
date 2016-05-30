# mailer

Execute batch mail sending by using mailer. It can retrieve email addresses from excel, pdf and text files. 


You need to provide your own smtp server.

build with "mvn install"

execute with java -jar -DpropFile=$your_properties_file$ target/mailer*.jar

Properties file needs the following info:

mail.server=your_smtp_server

mail.port=your_smtp_server_port

mail.defaultSubject=default_subject(you can enter via the command prompt as well)

mail.defaultBody=default_body(you can enter via the command prompt as well)

mail.defaultSender=default_sender(you can enter via the command prompt as well)

preloaded=paths_to_preloaded_files_with_emails (optional)



Type 'preload' to load pre-fetched addresses

Type 'status' to get overview of loaded messages

Type 'status detail' to get overview of loaded messages

Type 'mail sender [sender]' to set mail subject

Type 'mail subject [subject]' to set mail subject

Type 'mail body [body]' to set mail subject (HTML supported)

Type 'send' to send mail

Type 'prev' to execute previous command

Type 'log' to view command history

Type 'quit' to exit,

Type 'help' for help :)

