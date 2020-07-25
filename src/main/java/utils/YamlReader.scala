package utils




import java.io.{File, FileInputStream}

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

import scala.beans.BeanProperty


object YamlReader {

  def readEmailConfig():EmailConfig = {
    val input = readFile("/config.yaml")
    val yaml = new Yaml(new Constructor(classOf[EmailConfig]))
    yaml.load(input).asInstanceOf[EmailConfig]
  }
  def readServerSecret() = {
    val input = readFile("/secret.yaml")
    val yaml = new Yaml(new Constructor(classOf[ServerSecret]))
    yaml.load(input).asInstanceOf[ServerSecret]
  }

  private def readFile(fileName:String) ={
    val filenamePath= getClass.getResource(fileName).getPath
    new FileInputStream(new File(filenamePath))
  }
}

/**
 * With the approach shown in the main method -- load() plus
 * asInstanceOf -- this class must declare its properties in the
 * constructor.
 */
// snakeyaml requires properties to be specified in the constructor
class EmailConfig {
  @BeanProperty var content: String =""

  @BeanProperty var subject: String=""

  override def toString: String = s"$subject \n--------------------\n $content"
}

class ServerSecret{
  @BeanProperty var apiKey: String=""
}