# jooq-codegen-pojo-extension
Small extension of jOOQ's code generation features
##Requirements
* Java 1.7
* [jOOQ](http://www.jooq.org/) 3.8.2

*Note: at this moment tested only with jOOQ's 3.8.2 version*

## Usage
1. Add [jitpack.io](https://jitpack.io) repository to plugin repositories:

    ```xml
        <pluginRepositories>
          <pluginRepository>
            <id>jitpack.io</id>
            <url>http://jitpack.io</url>
          </pluginRepository>
        </pluginRepositories>
    ```

2. Add `jooq-codegen-maven` plugin to your pom plugin's section:

    ```xml
        <plugin>
          <groupId>org.jooq</groupId>
          <artifactId>jooq-codegen-maven</artifactId>
          <version>${jooq.version}</version>
        </plugin>
    ```

3. In `generator` section declare the following name of generator:

    ```xml
      <name>org.baddev.jooq.CustomGenerator</name>
    ```

3. In `generate` section declare:

    ```xml
      <pojos>true</pojos>
      <propertiesConstants>true</propertiesConstants>
      <jaxbAnnotations>true</jaxbAnnotations>
    ```
    *Note: `<pojos>true</pojos>` must be set directly or forced by declaring `<daos>true</daos>` (in case daos are required)* 

4. Finally, attach an extension dependency to plugin:

    ```xml
      <dependencies>
        <dependency>
          <groupId>com.github.regulate</groupId>
          <artifactId>jooq-codegen-pojo-extension</artifactId>
          <version>${codegen.pojo.ext.version}</version>
        </dependency>
      </dependencies>
    ```

5. Example

    ```xml
      <plugin>
        <groupId>org.jooq</groupId>
        <artifactId>jooq-codegen-maven</artifactId>
        <version>${jooq.version}</version>
        <executions>
          <execution>
            <id>generate-mysql</id>
            <goals>
              <goal>generate</goal>
            </goals>
            <configuration>
              <jdbc>
                <driver>${db.mysql.driver}</driver>
                <url>${db.mysql.url}</url>
                <user>${db.user}</user>
                <password>${db.password}</password>
              </jdbc>
              <generator>
                <name>org.baddev.jooq.CustomGenerator</name>
                <strategy>
                  <name>org.jooq.util.DefaultGeneratorStrategy</name>
                </strategy>
                <database>
                  <name>org.jooq.util.mysql.MySQLDatabase</name>
                  <inputSchema>db_schema_name</inputSchema>
                </database>
                <generate>
                  <pojos>true</pojos>
                  <propertiesConstants>true</propertiesConstants>
                  <jaxbAnnotations>true</jaxbAnnotations>
                </generate>
                <target>
                  <packageName>your_output_package_name</packageName>
                  <directory>${project.basedir}/target/generated-sources/jooq</directory>
                </target>
              </generator>
            </configuration>
          </execution>
        </executions>
        <dependencies>
          <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
          </dependency>
          <dependency>
            <groupId>com.github.regulate</groupId>
            <artifactId>jooq-codegen-pojo-extension</artifactId>
            <version>${codegen.pojo.ext.version}</version>
          </dependency>
        </dependencies>
      </plugin>
    ```