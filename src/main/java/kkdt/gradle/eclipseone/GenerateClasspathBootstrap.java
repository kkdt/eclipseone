/** 
 * Copyright (C) 2017 thinh ho
 * This file is part of 'eclipseone' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */
package kkdt.gradle.eclipseone;

import org.gradle.api.DefaultTask;

/**
 * <p>
 * The Eclipse bootstrap class. Note: This was written in Java to leverage 
 * auto-completion when first exploring the {@code gradleApi}.
 * </p>
 * 
 * @author thinh ho
 *
 */
public class GenerateClasspathBootstrap extends DefaultTask {
   private static final String description = "Bootstrap project with the root project's Eclipse plugin";
   
   public GenerateClasspathBootstrap() {
      super();
      setDescription(description);
   }
}
