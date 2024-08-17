pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        val githubUsername: String by settings
        val githubToken: String by settings
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/stephen-bapple/jokes-on-the-go")
            credentials {
                username = githubUsername
                password = githubToken
            }
        }
    }
}

rootProject.name = "Jokes On The Go Client"
include(":app")
 