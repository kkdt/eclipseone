/** 
 * Copyright (C) 2017 thinh ho
 * This file is part of 'eclipseone-plugin' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */
package kkdt.gradle.eclipseone

/**
 * <p>
 * The main model for <code>EclipseOnePlugin</code>.
 * </p>
 * 
 * @author thinh ho
 *
 */
class EclipseOneModel {
   public static final String defaultJreContainerPath = 'org.eclipse.jdt.launching.JRE_CONTAINER';
   
   /**
    * <p>
    * Custom JRE container path.
    * </p>
    */
   String jreContainerPath = null;
   /**
    * <p>
    * Java project(s) that are not need when building the classpath.
    * </p>
    */
   String[] excludedProjects;
}
