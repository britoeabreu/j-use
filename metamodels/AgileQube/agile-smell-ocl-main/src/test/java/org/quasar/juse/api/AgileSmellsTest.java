/**
 * 
 */
package org.quasar.juse.api;

import org.junit.*;
import org.prisma.kip.domain.projectInstance.Project;
import org.quasar.juse.api.implementation.ProgramingFacade;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.sys.MObject;

import java.util.Arrays;

/**
 * @author fba
 *
 */
public class AgileSmellsTest {
	private final static String	USE_BASE_DIRECTORY	= "/Users/utelemaco/Downloads/use-6.0.0";

	private final static String MODEL_DIRECTORY = "/Users/utelemaco/development/sandbox/ocl/agile-smell-ocl/src/main/resources/";
	private final static String MODEL_FILE = "agileProjectMetamodel.use";
	
	static JUSE_ProgramingFacade api = new ProgramingFacade();

	private static Project project;
	
	/**
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		api.initialize(null, USE_BASE_DIRECTORY, MODEL_DIRECTORY);
		api.compileSpecification(MODEL_FILE, false);
//		String githubOwner = "utelemaco";
//		String githubRepoName = "journal-submission-system";
//		String zenhubRepoId = "289429343";
//		project = new Zenhub2ProjectUtil().loadFromZenhub(githubOwner, githubRepoName, zenhubRepoId);
		project = MockProjectBuilder.buildMockProject();
		Project2OCLUtil.project2OCL(project, api);
	}


	@Test
	public final void testSmell01() {
		project.getIterations().forEach(iteration -> {
			String iterationId = "it" + iteration.getId();
			String resultSmell01 = calculateResult(api.command("? " + iterationId + ".lowerPriorityTaskExecutedFirst()"));
			System.out.println(iteration.getName() + " lowerPriorityTaskExecutedFirst: #" + resultSmell01 + "#");
		});
	}

	public final String calculateResult(String result) {
//		System.out.println("###########################################");
//		System.out.println(result);
//		System.out.println("###########################################");
		if (result.contains("IsApplicable")) {
			return "Not Applicable";
		}

		if (result.contains("PreConditions")) {
			return "PreConditions Not Met";
		}

		if (result.contains("true")) {
			return "No Smell";
		}

		return "Smell";
	}

}
