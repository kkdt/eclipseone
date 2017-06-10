/** 
 * Copyright (C) 2017 thinh ho
 * This file is part of 'eclipseone' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */
package kkdt.gradle.eclipseone;

import org.gradle.api.DefaultTask;

/**
 * <p>
 * The Eclipse bootstrap class.
 * </p>
 * 
 * @author thinh ho
 *
 */
class GenerateClasspathBootstrap extends DefaultTask {
   public GenerateClasspathBootstrap() {
      super();
      setDescription("Bootstrap project with the root project's Eclipse plugin");
   }
}
