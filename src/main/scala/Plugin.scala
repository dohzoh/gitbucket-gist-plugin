import app.GistController
import plugin.PluginRegistry
import util.Version
import java.io.File
import util.Configurations._

class Plugin extends plugin.Plugin {
  override val pluginId: String = "gist"
  override val pluginName: String = "Gist Plugin"
  override val description: String = "Provides Gist feature on GitBucket."
  override val versions: List[Version] = List(Version(1, 0))

  override def initialize(registry: PluginRegistry): Unit = {
    // Add Snippet link to the header
    registry.addJavaScript(".*",
      """
        |$('a.brand').after($('<span style="float: left; margin-top: 10px;">|&nbsp;&nbsp;&nbsp;&nbsp;<a href="/gist" style="color: black;">Snippet</a></span>'));
      """.stripMargin)

    val rootdir = new File(GistRepoDir)
    if(!rootdir.exists){
      rootdir.mkdirs()
    }

    // Mount controller
    registry.addController(new GistController, "/*")

    registry.addImage("images/menu-revisions-active.png",
      getClass.getClassLoader.getResourceAsStream("images/menu-revisions-active.png"))

    registry.addImage("images/menu-revisions.png",
      getClass.getClassLoader.getResourceAsStream("images/menu-revisions.png"))

    registry.addImage("images/snippet.png",
      getClass.getClassLoader.getResourceAsStream("images/snippet.png"))

    println("-- Gist plug-in initialized --")
  }

  override def shutdown(registry: PluginRegistry): Unit = {
  }
}
