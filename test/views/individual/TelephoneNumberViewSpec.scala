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

package views.individual

import forms.TelephoneNumberFormProvider
import models.{Name, NormalMode}
import play.api.data.Form
import play.twirl.api.HtmlFormat
import views.behaviours.QuestionViewBehaviours
import views.html.individual.TelephoneNumberView

class TelephoneNumberViewSpec extends QuestionViewBehaviours[String] {

  val prefix = "individual.telephoneNumber"
  val name: Name = Name("First", Some("Middle"), "Last")

  override val form: Form[String] = new TelephoneNumberFormProvider().withPrefix(prefix)

  val view: TelephoneNumberView = viewFor[TelephoneNumberView](Some(emptyUserAnswers))

  def applyView(form: Form[_]): HtmlFormat.Appendable =
    view.apply(form, NormalMode, name.displayName)(fakeRequest, messages)

  "TelephoneNumber View" must {

    behave like dynamicTitlePage(applyView(form), prefix, name.displayName)

    behave like pageWithBackLink(applyView(form))

    behave like pageWithTextFields(form, applyView,
      prefix,
      Some(name.displayName),
      "value"
    )

    behave like pageWithASubmitButton(applyView(form))

  }
}