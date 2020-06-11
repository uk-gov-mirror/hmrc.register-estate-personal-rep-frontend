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

package utils.mappers

import models.{Address, BusinessPersonalRep, NonUkAddress, UkAddress, UserAnswers}
import org.slf4j.LoggerFactory
import pages.business._
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsError, JsSuccess, Reads}

class BusinessMapper {

  private val logger = LoggerFactory.getLogger("application." + this.getClass.getCanonicalName)

  def apply(answers: UserAnswers): Option[BusinessPersonalRep] = {
    val readFromUserAnswers: Reads[BusinessPersonalRep] =
      (
        readName and
          TelephoneNumberPage.path.read[String] and
          UtrPage.path.readNullable[String] and
          readUkOrNonUkAddress
      ) (BusinessPersonalRep.apply _)

    answers.data.validate[BusinessPersonalRep](readFromUserAnswers) match {
      case JsSuccess(value, _) =>
        Some(value)
      case JsError(errors) =>
        logger.error(s"Failed to rehydrate Business from UserAnswers due to $errors")
        None
    }
  }

  private def readName: Reads[String] = {
    UkRegisteredYesNoPage.path.read[Boolean].flatMap {
      case true => UkCompanyNamePage.path.read[String]
      case false => NonUkCompanyNamePage.path.read[String]
    }
  }

  private def readUkOrNonUkAddress: Reads[Option[Address]] = {
    AddressUkYesNoPage.path.read[Boolean].flatMap[Option[Address]] {
      case true => UkAddressPage.path.read[UkAddress].map(Some(_))
      case false => NonUkAddressPage.path.read[NonUkAddress].map(Some(_))
    }
  }

}