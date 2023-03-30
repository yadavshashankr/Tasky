package com.portfolio.tasky.globals

class Constants {

    companion object{
        object AnimationProperties {
            const val MAX_TRANSLATION_Y = -200f
            const val DURATION : Long = 400
        }

        object FieldValidationsProperties {
            const val MIN_CHARACTERS_FOR_PASSWORD = 9
            const val MAX_CHARACTERS_FOR_PASSWORD : Long = 16
        }

        object ApiProperties {
            const val URL ="https://tasky.pl-coding.com/"
            const val DEFAULT_REQUEST_TIMEOUT : Long =  400
            const val ACCESS_TOKEN_KEY = "AccessToken"
        }

        open class Agenda

        object AgendaType: Agenda()

        object AgendaState : Agenda()

        object AgendaPreTimeParams : Agenda()

//        object AgendaPreTimeValues {
//            private const val ONE_MIN = 1000 * 60
//            const val TEN_MINS_BEFORE = ONE_MIN * 10
//            const val THIRTY_MINS_BEFORE = ONE_MIN * 30
//            const val ONE_HOUR_BEFORE = ONE_MIN * 60
//            const val SIX_HOURS_BEFORE = ONE_HOUR_BEFORE * 6
//            const val ONE_DAY_BEFORE = SIX_HOURS_BEFORE * 4
//        }
    }

}