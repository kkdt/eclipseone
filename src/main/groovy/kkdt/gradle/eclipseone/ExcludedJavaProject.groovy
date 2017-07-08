/** 
 * Copyright (C) 2017 thinh ho
 * This file is part of 'eclipseone-plugin' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */
package kkdt.gradle.eclipseone

/**
 * <p>
 * Data structure to store some information about excluded Java project.
 * </p>
 * 
 * @author thinh ho
 *
 */
class ExcludedJavaProject {
   /**
    * Project name.
    */
   def name;
   /**
    * File reference to jar artifacts.
    */
   def jars;
   /**
    * File reference to source directories.
    */
   def sourceSets
}
