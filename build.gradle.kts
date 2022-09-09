import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    // Java support
    id("java")
    // Kotlin support
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    // Gradle IntelliJ Plugin
    id("org.jetbrains.intellij") version "1.7.0"
    // Gradle Changelog Plugin
    id("org.jetbrains.changelog") version "1.3.1"
}

fun properties(key: String) = project.findProperty(key).toString()

group = properties("pluginGroup")
version = properties("pluginVersion")

// Configure project's dependencies
repositories {
    mavenCentral()
}

intellij {
    pluginName.set(properties("pluginName"))
    version.set(properties("platformVersion"))
    type.set(properties("platformType"))

    // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file.
    plugins.set(properties("platformPlugins").split(',').map(String::trim).filter(String::isNotEmpty))
}

dependencies {
    implementation(platform("io.projectreactor:reactor-bom:2020.0.20"))
    implementation("io.rsocket:rsocket-core:1.1.2")
    implementation("io.rsocket:rsocket-transport-netty:1.1.2")
    implementation("io.rsocket.broker:rsocket-broker-frames:0.3.0")


    implementation ("org.jooq:joor-java-8:0.9.7")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation ("com.google.code.gson:gson:2.8.6")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.3.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")


    testImplementation ("junit:junit:4.12")
}


tasks {

    patchPluginXml {
        version.set(properties("pluginVersion"))
        sinceBuild.set(properties("pluginSinceBuild"))
        untilBuild.set(properties("pluginUntilBuild"))

        changeNotes.set( """
            Screenshot test when 2 YALI windows is opened with layouts.<br>
          """
        )
    }

    // Configure UI tests plugin
    // Read more: https://github.com/JetBrains/intellij-ui-test-robot
    runIdeForUiTests {
        systemProperty("robot-server.port", "8082")
        systemProperty("ide.mac.message.dialogs.as.sheets", "false")
        systemProperty("jb.privacy.policy.text", "<!--999.999-->")
        systemProperty("jb.consents.confirmation.enabled", "false")
    }

}
