/** 
 * Copyright (C) 2017 thinh ho
 * This file is part of 'eclipseone' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */
package kkdt.gradle.eclipseone

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.plugins.ide.eclipse.GenerateEclipseClasspath
import org.gradle.plugins.ide.eclipse.GenerateEclipseProject
import org.gradle.plugins.ide.eclipse.model.EclipseModel

/**
 * <p>
 * The main Gradle plugin class.
 * </p>
 * 
 * <p>
 * During configuration, this plugin will look at all subprojects and look for
 * Java configurations. It will configure the root project's Eclipse Model with
 * the sources and configured libraries. The Eclipse Plugin will then use the
 * bootstrapped Eclipse Model to generate the IDE's artifacts.
 * </p>
 * 
 * @author thinh ho
 *
 */
class EclipseOnePlugin implements Plugin<Project> {
   public static final ECLIPSEONE_TASK = 'eclipseone';
   public static final String boostrapTask = "eclipseoneBootstrap";
   
   /**
    * The root task for this plugin.
    */
   Task lifecycleTask;
   /**
    * The root project.
    */
   Project project;
   /**
    * The single eclipse model.
    */
   EclipseModel eclipseModel;
   
   @Override
   void apply(Project project) {
      this.project = project;
      lifecycleTask = project.task(ECLIPSEONE_TASK);
      lifecycleTask.description = 'Create eclipse artifacts for only the root project';
      eclipseModel = project.extensions.findByName('eclipse');
      EclipseOneModel model = project.extensions.create('eclipseone', EclipseOneModel.class);
      
      project.afterEvaluate {
         configurePlugin(model);
      }
   }
   
   def configurePlugin(EclipseOneModel model) {
      def eclipse = project.tasks.findByName("eclipse");
      if(eclipseModel != null && eclipse != null) {
         eclipseModel.classpath.downloadSources = true
         project.subprojects.findResults {
            // only include projects that are not excluded, or all subprojects if model does not specify excludes
            return (model.excludedProjects == null || !model.excludedProjects.contains(it.name) ? it : null);
         }.each { p ->
            Task t = addTask(p, boostrapTask, GenerateClasspathBootstrap.class, new Action<GenerateClasspathBootstrap>() {
               @Override
               public void execute(GenerateClasspathBootstrap task) {
                  // task logger
                  def logger = task.logger;
                  
                  // subprojects with the eclipse plugin applied will have their
                  // eclipse artifacts removed
                  task.project.tasks.findByPath(p.path + ':eclipse')?.doLast {
                     if(p.file('.classpath').exists()) {
                        p.file('.classpath').delete();
                     }
                     if(p.file('.project').exists()) {
                        p.file('.project').delete();
                     }
                     if(p.file('.settings').exists()) {
                        p.file('.settings').deleteDir();
                     }
                  }
                  
                  // bootstrapping the root project Eclipse Model
                  task.project.plugins.withType(JavaBasePlugin.class, new Action<JavaBasePlugin>() {
                     @Override
                     public void execute(JavaBasePlugin javaBasePlugin) {
                        task.project.plugins.withType(JavaPlugin.class, new Action<JavaPlugin>() {
                           @Override
                           public void execute(JavaPlugin javaPlugin) {
                              SourceSetContainer sources = p.getConvention().getPlugin(JavaPluginConvention.class).sourceSets;
                              eclipseModel.classpath.sourceSets += sources;
                              
                              Configuration compileClasspath = p.configurations.findByName("compileClasspath");
                              if(compileClasspath != null) {
                                 eclipseModel.classpath.plusConfigurations.add(compileClasspath);
                              }
                              
                              Configuration runtimeClasspath = p.configurations.findByName("runtimeClasspath");
                              if(runtimeClasspath != null) {
                                 eclipseModel.classpath.plusConfigurations.add(runtimeClasspath);
                              }
                              
                              Configuration testCompileClasspath = p.configurations.findByName("testCompileClasspath");
                              if(testCompileClasspath != null) {
                                 eclipseModel.classpath.plusConfigurations.add(testCompileClasspath);
                              }
                              
                              Configuration testRuntimeClasspath = p.configurations.findByName("testRuntimeClasspath");
                              if(testRuntimeClasspath != null) {
                                 eclipseModel.classpath.plusConfigurations.add(testRuntimeClasspath);
                              }
                           }
                        });
                        
                        if(logger.debugEnabled) {
                           project.eclipse.classpath.sourceSets.each {
                              logger.debug('(eclipseone) source: ' + it);
                           }
                        }
                        
                        project.eclipse.classpath.file {
                           withXml { xml ->
                              if(logger.debugEnabled) {
                                 logger.debug('(eclipseone) ' + xml.class.name + ': ' + xml);
                              }
                           }
                           whenMerged { classpath ->
                              // no need to allow the eclipse plugin to link subprojects
                              classpath.entries.findAll {
                                 return (it instanceof org.gradle.plugins.ide.eclipse.model.ProjectDependency) && !project.file(it.path).exists();
                              }.each {
                                 classpath.entries.remove(it)
                              }
                              
                              classpath.entries.findAll {
                                 return (it.kind == 'con' && it.path.startsWith(EclipseOneModel.defaultJreContainerPath));
                              }.each {
                                 if(model.jreContainerPath != null) {
                                    it.path = model.jreContainerPath;
                                 }
                              }
                           }
                        }
                     }
                  });
               }
            });
            eclipse.dependsOn(t);
         };
      }
   }
   
   /**
    * Method modified from the {@code org.gradle.plugins.ide.eclipse.EclipsePlugin}.
    */
   def addTask(project, taskName, taskType, action) {
      if(project.tasks.findByName(taskName) != null) {
         return tasks.getByName(taskName);
      }
      def task = project.tasks.create(taskName, taskType);
      action.execute(task);
      lifecycleTask.dependsOn(task);
      return task;
   }
}
