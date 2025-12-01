package classes
import scala.collection.mutable.Queue
import Enums.STUDENT_LIST

class Platform(teacherList: List[Teacher], transactionThread: TransactionThread) {
private val _teacherList = teacherList
  private var token = new Token(5000, "Test")

  def CalculateScholarship(grade: Int, subjPrice: Double): Double = {
    var scholarship: Double = 0
    grade match {
      case 5 => scholarship = 1.1 * subjPrice
      case 4 => scholarship = 1 * subjPrice
      case 3 => scholarship = 0.9 * subjPrice
      case 2 => scholarship = 0.8 * subjPrice
      case 1 => scholarship = 0.7 * subjPrice
      case _ => scholarship = 0
    }
    token.amount -= scholarship
    scholarship
  }

  private def isTimeToActivateSubject(subject: Subject, month:Int):Unit = {
    if(subject.GetStartMonth() >= month && month <= subject.GetEndMonth()){
      if(!subject.IsActive()){
        subject.Activate()
        subject.studentList.foreach(s => {
          val needExtraTokenAmount = s.PayForCourse(subject)
          if(needExtraTokenAmount > 0){
            s.BuyTokens(needExtraTokenAmount)
            s.PayForCourse(subject)
          }
        })
      }
    }else
      subject.Deactivate()

  }

  def TransferScholarship(month:Int):Unit = {
    _teacherList.foreach(t => {
      t.Subjects.foreach(s => {
        isTimeToActivateSubject(s, month)
        if (s.IsActive()) {
          s.EstimateStudents()
          s.studentList.foreach(std => {
            //transactionThread.addStudent(s)
            std.GetScholarship(CalculateScholarship(std.Estimation, s.Price))
          })
        }
      })

    })
    //transactionThread.run()

  }
}
