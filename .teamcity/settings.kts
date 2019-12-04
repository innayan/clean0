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

    features {
        feature {
            id = "KEEP_RULE_1"
            type = "keepRules"
            param("limit.type", "NDaysSinceLastSuccessfulBuild")
            param("keepData.1.type", "artifacts")
            param("ruleDisabled", "false")
            param("limit.daysCount", "30")
            param("filters.1.status", "failed_to_start")
            param("filters.1.type", "buildStatus")
            param("keepData.1.artifactsPatterns", """
                +:**/*
                +:cleanup1_1
            """.trimIndent())
            param("preserveArtifacts", "true")
        }
    }

    cleanup {
        all(days = 10)
        history(days = 10)
        artifacts(days = 10, artifactPatterns = """
            +:**/*
            -:cleanup1
        """.trimIndent())
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

    features {
        feature {
            id = "KEEP_RULE_3"
            type = "keepRules"
            param("limit.type", "lastNBuilds")
            param("keepData.1.type", "everything")
            param("ruleDisabled", "false")
            param("filters.1.pattern", """
                +:*
                -:<default>
            """.trimIndent())
            param("filters.1.type", "branchSpecs")
            param("limit.buildsCount", "30")
            param("preserveArtifacts", "true")
        }
    }
})

object HttpsGithubComInnayanMyrepository : GitVcsRoot({
    name = "https://github.com/innayan/myrepository"
    url = "https://github.com/innayan/myrepository"
    branchSpec = "+:*"
})
