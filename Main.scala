import classes.{Human, Platform, Student, Subject, Teacher, Token, TransactionThread}
import Enums.{STUDENT_LIST, SUBJECT_LIST, TEACHER_LIST}

import scala.util.Random

val RND = new Random()





//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
object Main {

  def main(args: Array[String]): Unit = {



    SUBJECT_LIST.foreach(s => {
      if (s.studentList.length == 0) {
        SUBJECT_LIST = SUBJECT_LIST.filter(_ != s)
      }
    })

    TEACHER_LIST.foreach(t => {
      val length = SUBJECT_LIST.length
      if ( length> 0) {
        var subjList = RND.shuffle(SUBJECT_LIST)
        val rndNum = RND.nextInt(2)+1// 1 - 3
        val rndNumber = if(rndNum > length) length else rndNum
        for(i <- 0 until rndNumber){
          var subj = subjList.apply(0)
          t.Subjects :+= subj
          subjList = subjList.filter(_ != subj)
          SUBJECT_LIST = SUBJECT_LIST.filter(_ != subj)
        }
      }
    })

    TEACHER_LIST.foreach(s => {
      if (s.Subjects.isEmpty) {
        TEACHER_LIST = TEACHER_LIST.filter(_ != s)
      }
    })
    //TEACHER_LIST.foreach(t => println(t.Show()))
    var transactionThread = new TransactionThread()
    val platform = new Platform(TEACHER_LIST, transactionThread)
    for(i <- 1 to 5){
      platform.TransferScholarship(i)
      print(s"${i}--------------------\n")
      TEACHER_LIST.foreach(t => println(t.Show()))
    }
  }


}

