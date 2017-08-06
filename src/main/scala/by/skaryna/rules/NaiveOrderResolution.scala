package by.skaryna.rules

import org.apache.spark.sql.catalyst.expressions.{Alias, AttributeReference, Expression, NamedExpression, SortOrder}
import org.apache.spark.sql.catalyst.plans.logical.{LogicalPlan, Project, Sort}
import org.apache.spark.sql.catalyst.rules.Rule

/**
  * Created by yuri on 8/5/17.
  */
object NaiveOrderResolution extends Rule[LogicalPlan] {
  def apply(plan: LogicalPlan): LogicalPlan = plan transform {
    case Sort(Seq(order1), global1, Project(projectList, Sort(Seq(order2), global2, child2)))
      if global1 == global2 && order1.direction == order2.direction &&
        isOrderColumnRenamed(projectList, order1.child, order2.child) =>

      val prunedProject = Project(projectList, child2)
      Sort(Seq(order1), global1, prunedProject)
  }

  private[skaryna] def isOrderColumnRenamed(projectList: Seq[NamedExpression],
                                            orderExpr1: Expression, orderExpr2: Expression): Boolean = {
    val aliasMatch = projectList.collect {
      case alias: Alias =>
        alias.child == orderExpr2 && orderExpr1.isInstanceOf[AttributeReference] &&
          alias.exprId == orderExpr1.asInstanceOf[AttributeReference].exprId
    }
    aliasMatch.contains(true)
  }
}
