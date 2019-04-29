package com.builder.microsoftassistant

import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.service.voice.VoiceInteractionSessionService

class AssistLoggerSessionService : VoiceInteractionSessionService() {
    override fun onNewSession(p0: Bundle?): VoiceInteractionSession {
        return AssistLoggerSession(this)
    }

}
