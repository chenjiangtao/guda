#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.gen;

/**
 * Created by foodoon on 2014/7/28.
 */
public class DemoGen {

    private static String jdbcPath = "e:${symbol_escape}${symbol_escape}repo${symbol_escape}${symbol_escape}mysql${symbol_escape}${symbol_escape}mysql-connector-java${symbol_escape}${symbol_escape}5.1.9${symbol_escape}${symbol_escape}mysql-connector-java-5.1.9.jar";

    private static String jdbUrl = "jdbc:mysql://127.0.0.1:3306/demo?useUnicode=true&characterEncoding=utf8";

    public static void main(String[] args){
        ${groupId}.gooda.gen.DemoGen demoGen = new ${groupId}.gooda.gen.DemoGen();
        demoGen.setJdbcPassword("");
        demoGen.setJdbcUsername("root");
        demoGen.setJdbcPath(jdbcPath);
        demoGen.setJdbUrl(jdbUrl);
       // demoGen.genDAO("demo","well");
         demoGen.genDaoXML("demo","well");
        demoGen.genBiz("demo","well");
        demoGen.genAction("demo","well");





    }
}
