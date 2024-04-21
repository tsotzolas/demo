## Wildfly Version 26.0.1

## Datasource 

```xml
             <datasource jndi-name="java:/demoDS" pool-name="demoDS" enabled="true" statistics-enabled="true">
                    <connection-url>jdbc:mysql://127.0.0.1:3306/demo?zeroDateTimeBehavior=convertToNull&amp;autoReconnect=true&amp;useSSL=false&amp;allowPublicKeyRetrieval=true&amp;Unicode=true&amp;characterEncoding=UTF-8&amp;characterSetResults=UTF-8</connection-url>
                    <driver>mysql</driver>
                    <security>
                        <user-name>yourDbUser</user-name>
                        <password>yourDbPassword</password>
                    </security>
                    <validation>
                        <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLValidConnectionChecker"/>
                        <background-validation>true</background-validation>
                        <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLExceptionSorter"/>
                    </validation>
                </datasource>

```