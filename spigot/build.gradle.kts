import kr.entree.spigradle.kotlin.enginehub
import kr.entree.spigradle.kotlin.protocolLib
import kr.entree.spigradle.kotlin.worldguard

plugins {
	id("com.github.johnrengelman.shadow") version "6.1.0"
	id("kr.entree.spigradle")
}

repositories {
	enginehub()
	protocolLib()
}

dependencies {
	implementation(project(":common"))
	implementation(project(":worldguard"))
	implementation(project(":worldguard-7"))
	compileOnly(worldguard())
	compileOnly(protocolLib())
}

tasks {
	build {
		dependsOn("shadowJar")
	}

	shadowJar {
		archiveBaseName.set("WorldEditExtraFlags")
		archiveClassifier.set("")
	}

	generateSpigotDescription {
		enabled = true
	}
}

spigot {
	name = "WorldGuardExtraFlags"
	description = "Adds more flags to WorldGuard to help manage your server easily!"
	version = "4.2.0-SNAPSHOT"
	apiVersion = "1.16"

	authors = listOf("isokissa3", "skyezerfox")
	website = "https://goldtreevers.net"

	depends("WorldGuard")
	softDepends("MythicMobs", "ProtocolLib")
}
