import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2019.2"

project {

    vcsRoot(HttpsGithubComInnayanMyrepository)

    buildType(CleanConfig)

    cleanup {
        keepRule {
            id = "KEEP_RULE_1"
            keepAtLeast = days(30) {
                since = lastSuccessfulBuild()
            }
            applyToBuilds {
                withStatus = failedToStart()
            }
            dataToKeep = historyAndStatistics {
                preserveArtifacts = byPattern("""
                    +:**/*
                    +:cleanup1_1
                """.trimIndent())
            }
            preserveArtifactsDependencies = true
        }
        keepRule {
            id = "KEEP_RULE_2"
            keepAtLeast = allBuilds()
            applyToBuilds {
                inBranches {
                    branchFilter = patterns("+:*_root")
                }
            }
            dataToKeep = everything()
            preserveArtifactsDependencies = true
        }
    }
}

object CleanConfig : BuildType({
    name = "CleanConfig"

    vcs {
        root(HttpsGithubComInnayanMyrepository)
    }

    steps {
        script {
            scriptContent = "echo test"
        }
    }

    cleanup {
        keepRule {
            id = "KEEP_RULE_6"
            keepAtLeast = allBuilds()
            dataToKeep = everything()
            preserveArtifactsDependencies = true
        }
        keepRule {
            id = "KEEP_RULE_8"
            keepAtLeast = allBuilds()
            dataToKeep = everything()
            preserveArtifactsDependencies = true
        }
        keepRule {
            id = "KEEP_RULE_9"
            keepAtLeast = allBuilds()
            dataToKeep = everything()
            preserveArtifactsDependencies = true
        }
    }
})

object HttpsGithubComInnayanMyrepository : GitVcsRoot({
    name = "https://github.com/innayan/myrepository"
    url = "https://github.com/innayan/myrepository"
    branchSpec = "+:*"
})
