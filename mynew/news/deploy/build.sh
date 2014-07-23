tomcat_home=/usr/tomcat-sms
sh ${tomcat_home}/bin/shutdown.sh
cd .. && mvn package -Dmaven.test.skip=true
mv assembly/target/news-1.0.0.war ROOT.war
cp ROOT.war /usr/tomcat/webapps/

rm -rf ${tomcat_home}/webapps/ROOT
sh ${tomcat_home}/bin/startup.sh
