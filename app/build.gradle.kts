
plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")

    implementation("com.google.guava:guava:30.1.1-jre")

    implementation("org.postgresql:postgresql:42.5.0")
}

application {
    mainClass.set("jdbclab.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
