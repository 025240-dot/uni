package classes
import classes.{Human, Subject}

import scala.util.Random

val RND_t = new Random()

class Teacher(age:Int=0, name:String="", address:String="") extends Human(age, name, address){
  var Subjects:List[Subject] = List()
  private val salary:Int = RND_t.between(2500, 7000) //

  val Rate:Int= new Random().nextInt(4) + 1 // 1-5
  val Salary = salary
  override val Name: String = name

  private def showSubjects(): String = {
    var subjStr = ""
    Subjects.foreach(s => subjStr += s"""-${s.Show()}\n""".stripMargin)

    subjStr
  }
  def Show():String = {
    var str:String =s"""Teacher
                       |Name: ${name}
                       |Age: ${age}
                       |Address: ${address}
                       |Rate: ${Rate}
                       |Salary: ${salary}
                       |Subjects:${showSubjects()}\n """.stripMargin

    str
  }
}
