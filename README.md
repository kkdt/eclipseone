> This plugin is available on the [Gradle Plugin Repository](https://plugins.gradle.org/plugin/kkdt.gradle.eclipseone).

# eclipseone

This project uses the [Eclipse Plugin](https://docs.gradle.org/current/userguide/eclipse_plugin.html) to configure a multi-project Gradle build as a single-project view in the Eclipse IDE, without the use of the Eclipse Gradle Plugin. 

The Gradle Plugin for Eclipse loads each subproject as a separate project within the IDE. With this Gradle plugin, a multiple-project view within Eclipse will look like the screen below (similar to IDEA's Gradle Plugin). In addition, you can keep your IDE lightweight and not have to install the Gradle Plugin.

![alt text](img/screenshot2.png "Eclipse single-project import")

## Quick Start

1. The master Gradle build file should have the follow snippet:
```
buildscript {
   repositories {
      maven {
         url "https://plugins.gradle.org/m2/"
      }
   }
   dependencies {
    classpath "gradle.plugin.kkdt.gradle.eclipseone:eclipseone:0.3"
  }
}

apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'kkdt.gradle.eclipseone'
```
2. Execute the Eclipse plugin as normal: `gradle cleanEclipse eclipse`

## Overview

This plugin looks for Java subprojects and will configure their sources and library configurations into the root project's Eclipse Model. Because of this, the convention is as follows:

1. The root project needs the Java plugin: `apply plugin: 'java'`
2. The root project needs the Eclipse plugin: `apply plugin: 'eclipse'`
3. Subprojects that include the Eclipse plugin will not include Eclipse artifacts (removed by this plugin)
4. You will need to run `gradle cleanEclipse eclipse` again if configurations change (i.e. add a new `compile` configuration to a subproject)

## Building

Publish to a Maven-style file repository.

```
gradle publish
```