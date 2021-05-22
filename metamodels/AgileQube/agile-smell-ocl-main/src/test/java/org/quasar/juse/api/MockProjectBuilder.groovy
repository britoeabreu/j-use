package org.quasar.juse.api

import org.prisma.kip.domain.projectInstance.Iteration
import org.prisma.kip.domain.projectInstance.Project
import org.prisma.kip.domain.projectInstance.Task
import org.prisma.kip.domain.util.Priority

class MockProjectBuilder {

    public static Project buildMockProject() {
        Project project = new Project();
        project.setName("Project Test");

        Iteration iteration1 = new Iteration(id: 1, name: 'Iteration 1', status: 'open')
        project.iterations << iteration1

        Iteration iteration2 = new Iteration(id: 2, name: 'Iteration 2', status: 'open')
        project.iterations << iteration2

        Iteration iteration3 = new Iteration(id: 3, name: 'Iteration 3', status: 'closed')
        project.iterations << iteration3

        Task t1 = new Task(id: 1, name: 'Task 1', status: 'todo', priority: new Priority(value: 10))
        project.tasks << t1
        iteration1.tasks << t1

        Task t2 = new Task(id: 2, name: 'Task 2', status: 'doing', priority: new Priority(value: 5))
        project.tasks << t2
        iteration1.tasks << t2

        Task t3 = new Task(id: 3, name: 'Task 3', status: 'todo', priority: new Priority(value: 5))
        project.tasks << t3
        iteration2.tasks << t3

        Task t4 = new Task(id: 4, name: 'Task 4', status: 'doing', priority: new Priority(value: 10))
        project.tasks << t4
        iteration2.tasks << t4

        return project

    }
}
