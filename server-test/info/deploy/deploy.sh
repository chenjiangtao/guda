tomcat_home=/usr/tomcat-sms

cd .. && mvn package -Dmaven.test.skip=true
mv assembly/target/info-1.0.0.war ROOT.zip
cp ROOT.zip ${tomcat_home}/webapps/
sh ${tomcat_home}/bin/shutdown.sh
rm -rf ${tomcat_home}/webapps/ROOT
mv ${tomcat_home}/webapps/ROOT.zip ${tomcat_home}/webapps/ROOT.war
sh ${tomcat_home}/bin/startup.sh
