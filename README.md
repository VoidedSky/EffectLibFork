## Note: This is a VoidedSky fork of this plugin!

EffectLib - Manage your effects the nice way.
=========

You have no idea what a vector or matrix is, but you want to give your users some nice effects with particles? No problem. This library comes with a load of effects for you. It handles rotation, text-parsing, and creation of 3D objects with particles in Minecraft.

  - Text-Parsing
  - 3D cubes, spheres, stars and others
  - 2D arcs, lines and many more!
  - Entity effects that enhance every game 

Find more information on [BukkitForums] [forum] or [BukkitDev] [dev]!

Check out this plugin to demo all the effects:

https://github.com/u9g/effectlib-visualizer

Note that **this library** is no standalone plugin! You have to **create yourself a plugin** to run the effects!

See here: https://github.com/elBukkit/EffectLibDemo
for a full working example.

# How to Shade

It is recommended to shade this plugin into yours. This way, users of your plugin do not need to install EffectLib separately.

This easy to do with Maven. First, add the elMakers repository:

```
    <repositories>
      <repository>
          <id>Maven Central</id>
          <url>https://repo1.maven.org/maven2/</url>
      </repository>
<!-- Use this repository ONLY if you need snapshot builds -->
        <repository>
            <id>elMakers</id>
            <url>http://maven.elmakers.com/repository/</url>
        </repository>
    </repositories>
```

Then, add the EffectLib dependency:

```
        <dependency>
            <groupId>com.elmakers.mine.bukkit</groupId>
            <artifactId>EffectLib</artifactId>
            <version>9.4</version>
            <scope>compile</scope>
        </dependency>

```

Note the "compile" scope!

Finally, add the Maven shade plugin:

```
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <minimizeJar>false</minimizeJar>
                    <dependencyReducedPomLocation>${project.build.directory}/dependency-reduced-pom.xml</dependencyReducedPomLocation>
                    <relocations>
                        <!-- Other relocations can go here, e.g. bstats -->
                        <relocation>
                            <pattern>de.slikey</pattern>
                            <shadedPattern>my.own.plugin.namespace.slikey</shadedPattern>
                        </relocation>
                    </relocations>
                    <filters>
                        <filter>
                            <artifact>com.elmakers.mine.bukkit:EffectLib</artifact>
                            <excludes>
                                <exclude>plugin.yml</exclude>
                                <exclude>META-INF/MANIFEST.MF</exclude>
                            </excludes>
                        </filter>
                    </filters>
                </configuration>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
```

Make sure to change the "shadedPattern" to match the base package of your own plugin!

# Gradle

To use EffectLib via gradle just add

```
implementation 'com.elmakers.mine.bukkit:EffectLib:9.4'
```

To your build.gradle file, remember to change version to the latest version, you can find the latest version in the maven area above.


# Support

Come visit the Magic Discord if you'd like EffectLib help: https://discord.gg/fWJ3W3kMjG

# License

MIT

**Free Software, Hell Yeah!**

[dev]:http://dev.bukkit.org/bukkit-plugins/effectlib/
[forum]:http://forums.bukkit.org/threads/effectlib-manage-your-effects-the-nice-way-text-in-particles.259879/
