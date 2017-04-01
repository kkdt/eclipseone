# gradle-java-multiprojects

This project uses the [Eclipse Plugin](https://docs.gradle.org/current/userguide/eclipse_plugin.html) to configure a multi-project Gradle build as a single-project view in the Eclipse IDE, without the use of the Eclipse Gradle Plugin.

## Quick Start

Add an include to `eclipseone.gradle` to the **end** of your root project's build file.

```java
// make sure this goes at the end of your root project build.gradle
include from : '/path/to/eclipseone.gradle'
```

Then, execute `gradle cleanEclipse eclispe` at your root project.

In addition to using this for other various projects of mine, I also tested this using an example from [Gradle 3.3 Release](https://services.gradle.org/distributions) located at `${GRADLE_HOME}/samples/userguide/multiproject/dependencies/java` with a modified build file used in another project of mine. The original `build.gradle` was from Gradle 2.2.1 version. The work here may be obsolete but I wanted to push it out to Github anyway as I still use this as a template for all the builds I use at home.

## Background

About 4-5 years ago, I was a developer on a program whose code baseline was a multi-project Gradle build. Most developers on the program used Eclipse bundled with the Gradle plugin. The Gradle plugin for Eclipse IDE was great. For most developers, loading the project's codebase was a two-step process:

1. Check out the baseline (we used SVN at the time)
2. Import the project into Eclipse

After the import, the Eclipse project explorer looks like the picture below.

![alt text](https://github.com/kkdt/gradle-java-multiprojects/blob/master/img/screenshot1.png "Eclipse import Gradle project")

There were a couple of developers (myself included) who used the more lightweight, classic Eclipse that had minimum plugins. The Gradle plugin for Eclipse IDE was not available for us. This was a personal preference. In addition, we also preferred the single-project view in Eclipse - if the baseline checkout was a single baseline regardless of whether or not it is comprised of a single project or multiple projects, we like the IDE to organize the checkout as a single project (see screenshot below). In order to achieve this, we had to manually add sources and libraries within Eclipse.

![alt text](https://github.com/kkdt/gradle-java-multiprojects/blob/master/img/screenshot2.png "Eclipse single-project import")

## Utilize Gradle Configuration-on-Demand and Eclipse Plugin

The following steps highlight how to configure the Gralde Eclipse Plugin to build a single-project view for Eclipse.

1. Define a new configuration in Gradle to store each subproject's Java `sourceSets`.
2. Define a new configuration in Gradle to store each subproject's library dependencies.
3. Use the `eclipse` closure to process the new configurations and add new sources and library files.

This was never committed to my actual work because I had already left the program when I finished this task; however, the effort was not wasted because I apply this build file to all the projects I do on my personal time.

> Original project exported from a personal subversion server into a git repository, and pushed to Github.
