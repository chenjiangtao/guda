tomcat_home=/usr/tomcat

cd .. && mvn package -Dmaven.test.skip=true
mv assembly/target/tiaotiaogift-1.0.0.war ROOT.zip
cp ROOT.zip /usr/tomcat/webapps/
sh ${tomcat_home}/bin/shutdown.sh
rm -rf ${tomcat_home}/webapps/ROOT
mv ${tomcat_home}/webapps/ROOT.zip ${tomcat_home}/webapps/ROOT.war
sh ${tomcat_home}/bin/startup.sh
