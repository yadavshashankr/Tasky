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
        }
    }

}