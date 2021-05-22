package org.quasar.juse.api;

import org.prisma.kip.domain.projectInstance.Project;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.sys.MObject;

import java.util.Arrays;

public class Project2OCLUtil {

    public static void project2OCL(Project project, JUSE_ProgramingFacade api) {
        MObject oclProject = api.createObject("p", "Project");
        project.getTasks().forEach(task -> {
            String taskId = "t" + task.getId();
            //Create Object
            MObject oclTask = api.createObject(taskId, "Task");

            //Set attributes
            if (task.getPriority() != null) {
                api.setObjectAttribute(oclTask, api.attributeByName(oclTask, "priority"), new IntegerValue(task.getPriority().getValue()));
            }

            if (task.getStatus() != null) {
                api.setObjectAttribute(oclTask, api.attributeByName(oclTask, "status"), new StringValue(task.getStatus()));
            }

            //Create links
            api.createLink("projectHasTasks", Arrays.asList(new MObject[]{oclProject, oclTask}));
        });
        project.getIterations().forEach(iteration -> {
            String iterationId = "it" + iteration.getId();
            System.out.println(iterationId);

            //Create Object
            MObject oclIteration = api.createObject(iterationId, "Iteration");

            if (iteration.getStatus() != null) {
                api.setObjectAttribute(oclIteration, api.attributeByName(oclIteration, "status"), new StringValue(iteration.getStatus()));
            }

            //Create links
            api.createLink("projectHasIterations", Arrays.asList(new MObject[]{oclProject, oclIteration}));

            iteration.getTasks().forEach(task -> {
                String taskId = "t" + task.getId();
                MObject oclTask = api.objectByName(taskId);
                api.createLink("iterationHasTasks", Arrays.asList(new MObject[]{oclIteration, oclTask}));
            });


        });
        System.out.println(api.command("info state"));
    }
}
