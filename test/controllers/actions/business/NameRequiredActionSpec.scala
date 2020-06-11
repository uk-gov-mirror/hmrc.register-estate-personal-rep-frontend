/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers.actions.business

import base.SpecBase
import controllers.actions.BusinessNameRequest
import models.requests.DataRequest
import models.{Name, NormalMode}
import org.scalatest.concurrent.ScalaFutures
import pages.business.{NonUkCompanyNamePage, UkCompanyNamePage}
import pages.individual.NamePage
import play.api.mvc.Result
import play.api.test.Helpers._

import scala.concurrent.{ExecutionContext, Future}

class NameRequiredActionSpec extends SpecBase with ScalaFutures {

  implicit val executionContext: ExecutionContext = injector.instanceOf[ExecutionContext]

  class Harness() extends NameRequiredAction {
    def callRefine[A](request: DataRequest[A]): Future[Either[Result, BusinessNameRequest[A]]] = refine(request)
  }

  "Individual personal rep name required answer Action" when {

    "there is no answer" must {

      "redirect to What is the name" in {

        val action = new Harness()

        val futureResult = action.callRefine(
          DataRequest(
            fakeRequest,
            "id",
            emptyUserAnswers
          )
        )

        whenReady(futureResult) { r =>
          val result = Future.successful(r.left.get)
          status(result) mustEqual SEE_OTHER
          redirectLocation(result).value mustEqual controllers.business.routes.UkRegisteredYesNoController.onPageLoad(NormalMode).url
        }
      }
    }

    "there is an answer" must {

      "add the answer to the request for a UK company" in {

        val action = new Harness()

        val userAnswers = emptyUserAnswers.set(UkCompanyNamePage, "UK Company Name").success.value

        val futureResult = action.callRefine(
          DataRequest(
            fakeRequest,
            "id",
            userAnswers
          )
        )

        whenReady(futureResult) { result =>
          result.right.get.businessName mustEqual "UK Company Name"
        }
      }

      "add the answer to the request for a non UK company" in {

        val action = new Harness()

        val userAnswers = emptyUserAnswers.set(NonUkCompanyNamePage, "Non UK Company Name").success.value

        val futureResult = action.callRefine(
          DataRequest(
            fakeRequest,
            "id",
            userAnswers
          )
        )

        whenReady(futureResult) { result =>
          result.right.get.businessName mustEqual "Non UK Company Name"
        }
      }
    }
  }

}