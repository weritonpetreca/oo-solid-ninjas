plugins {
    id("java")
    id("application")
}

group = "com.petreca"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.11.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    // Defina a classe principal se quiser rodar com "gradle run" direto
    // mainClass.set("capitulo3_acoplamento.SimuladorDeAcoplamento")

    // ESTA é a configuração mestre para aplicações
    applicationDefaultJvmArgs = listOf("-Dfile.encoding=UTF-8")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("file.encoding", "UTF-8")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

tasks.withType<JavaExec> {
    // Força a JVM a usar UTF-8 durante a execução
    jvmArgs("-Dfile.encoding=UTF-8")

    // Garante que a propriedade de sistema também esteja setada
    systemProperty("file.encoding", "UTF-8")
}