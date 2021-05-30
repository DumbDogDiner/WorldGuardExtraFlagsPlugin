import kr.entree.spigradle.kotlin.enginehub
import kr.entree.spigradle.kotlin.worldguard

repositories {
	enginehub()
}

dependencies {
	compileOnly(worldguard())
}
