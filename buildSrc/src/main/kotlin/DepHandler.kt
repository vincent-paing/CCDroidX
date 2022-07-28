import org.gradle.api.artifacts.dsl.DependencyHandler


fun DependencyHandler.implementation(depName: String) {
  add("implementation", depName)
}

fun DependencyHandler.debugImplementation(depName: String) {
  add("debugImplementation", depName)
}

fun DependencyHandler.kapt(depName: String) {
  add("kapt", depName)
}

fun DependencyHandler.compileOnly(depName: String) {
  add("compileOnly", depName)
}

fun DependencyHandler.api(depName: String) {
  add("api", depName)
}

fun DependencyHandler.testImplementation(depName: String) {
  add("testImplementation", depName)
}

fun DependencyHandler.testRuntimeOnly(depName: String) {
  add("testRuntimeOnly", depName)
}

fun DependencyHandler.kaptTest(depName: String) {
  add("kaptTest", depName)
}

fun DependencyHandler.androidTestImplementation(depName: String) {
  add("androidTestImplementation", depName)
}

fun DependencyHandler.androidTestRuntimeOnly(depName: String) {
  add("androidTestRuntimeOnly", depName)
}

fun DependencyHandler.kaptAndroidTest(depName: String) {
  add("kaptAndroidTest", depName)
}