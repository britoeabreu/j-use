package org.quasar.juse.api


import org.prisma.kip.domain.projectInstance.Project
import org.prisma.zenhubetl.Zenhub2Project
import org.prisma.zenhubetl.dto.MapEntry
import org.prisma.zenhubetl.dto.ZenhubConfig

class Zenhub2ProjectUtil {
	
	List<MapEntry> prioritiesMap = [new MapEntry(key:'Priority:Very High', value: "100"), new MapEntry(key:'Priority:High', value: "80"), new MapEntry(key:'Priority:Normal', value: "50"), new MapEntry(key:'Priority:Low', value: "20")]
	List<MapEntry> statusMap = [new MapEntry(key:'New Issues', value: "todo"), new MapEntry(key:'Backlog', value: "todo"), new MapEntry(key:'In Progress', value: "doing"), new MapEntry(key:'Review/QA', value: "doing"), new MapEntry(key:'Done', value: "done")]
	List<MapEntry> processActivitiesMap = [new MapEntry(key:'Activity:Planning', value: 'Iteration Planning'), new MapEntry(key:'Review', value: 'Planning Review')]

	public Project loadFromZenhub(def githubOwner, def githubRepoName, def zenhubRepoId) {
		ZenhubConfig config = new ZenhubConfig()
		config.githubOwner = githubOwner
		config.githubRepoName = githubRepoName
		config.zenhubRepoId = zenhubRepoId
		config.prioritiesMap = prioritiesMap
		config.statusMap = statusMap
		config.processActivitiesMap = processActivitiesMap

		config.githubAccessToken = System.properties['githubAccessToken']
		if (!config.githubAccessToken) {
			throw new Exception('System.property githubAccessToken not provided!')
		}

		config.zenhubAccessToken = System.properties['zenhubAccessToken']
		if (!config.zenhubAccessToken) {
			throw new Exception('System.property zenhubAccessToken not provided!')
		}

		return new Zenhub2Project(config).loadFromZenhub()
	}

}
