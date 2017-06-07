> [Gradle Plugin Repository](https://plugins.gradle.org/plugin/kkdt.gradle.eclipseone) also contains instructions on how to include this plugin.

# eclipseone

This project uses the [Eclipse Plugin](https://docs.gradle.org/current/userguide/eclipse_plugin.html) to configure a multi-project Gradle build as a single-project view in the Eclipse IDE, without the use of the Eclipse Gradle Plugin. 

The Gradle Plugin for Eclipse loads each subproject as a separate project within the IDE. With this Gradle plugin, a multiple-project view within Eclipse will look like the screen below (similar to IDEA's Gradle Plugin). In addition, you can keep your IDE lightweight and not have to install the Gradle Plugin.

![alt text](https://github.com/kkdt/gradle-java-multiprojects/blob/master/img/screenshot2.png "Eclipse single-project import")

## Quick Start

1. Clone the project and execute: `gradle clean install` to add it to your `mavenLocal` repository. (Gradle 3.3)
2. In another project, the master Gradle build file should have the follow snippet:
```
buildscript {
   repositories { 
      mavenLocal()
      mavenCentral()
   }
   dependencies {
      classpath 'kkdt.gradle.eclipseone:eclipseone:0.1'
   }
}

apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'kkdt.gradle.eclipseone'
```
3. Execute the Eclipse plugin as normal: `gradle cleanEclipse eclipse`

## Overview

This plugin looks for Java subprojects and will configure their sources and library configurations into the root project's Eclipse Model. Because of this, the convention is as follows:

1. The root project needs the Java plugin: `apply plugin: 'java'`
2. The root project needs the Eclipse plugin: `apply plugin: 'eclipse'`
3. Subprojects that have the Java plugin should **not** include the Eclipse plugin; otherwise, those subprojects will have Eclipse artifacts that are not used
4. You will need to run `gradle cleanEclipse eclipse` again if configurations change (i.e. add a new `compile` configuration to a subproject)