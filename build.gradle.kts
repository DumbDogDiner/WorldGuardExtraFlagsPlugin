import kr.entree.spigradle.kotlin.paper
import kr.entree.spigradle.kotlin.papermc
import kr.entree.spigradle.module.common.YamlGenerate

plugins {
	// lombok + annotation processing
	id("io.freefair.lombok") version "6.0.0-m2"
	// spigradle for quick dep resolution
	id("kr.entree.spigradle") version "2.2.3"
	// spotless for formatting
	id("com.diffplug.spotless") version "5.12.5"
	java
}

allprojects {
	// set the global version
	version = "4.2.0-SNAPSHOT"

	repositories {
		jcenter()
		mavenCentral()
		papermc()
	}

	// disable yaml generation by default
	tasks.withType<YamlGenerate> {
		enabled = false
	}
}

subprojects {
	apply(plugin = "java")
	apply(plugin = "io.freefair.lombok")
	apply(plugin = "com.diffplug.spotless")

	dependencies {
		compileOnly(paper())
	}
	// target jvm 14
	tasks.withType<JavaCompile> {
		targetCompatibility = JavaVersion.VERSION_14.toString()
		sourceCompatibility = JavaVersion.VERSION_14.toString()
	}

	spotless {
		java {
			importOrder()
			removeUnusedImports()
			eclipse().configFile(rootProject.file("eclipse-ddd-modified.xml"))
			licenseHeaderFile(rootProject.file("LICENSE_HEADER"))
		}
	}
}
