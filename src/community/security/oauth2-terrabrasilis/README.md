To build this plugin, you need to run this command:

mvn clean install

To build for eclipse use project:

mvn clean install eclipse:eclipse

To build complete geoserver for eclipse use (on geoserver/src):

mvn clean install  -P allExtensions -Poauth2-terrabrasilis eclipse:eclipse

To run on eclipse with the extension, you need to build inside de web/app folder:

mvn clean install -Poauth2-terrabrasilis eclipse:eclipse
