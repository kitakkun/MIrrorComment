package com.github.kitakkun.mirrorcomment.preferences

import com.russhwolf.settings.MapSettings
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SettingsRepositoryTest {
    private val settingsRepository = SettingsRepository(MapSettings())

    @Test
    fun testVoiceVoxServerUrlConfig() {
        // default is null
        assertNull(settingsRepository.getVoiceVoxServerUrl())
        // set valid url
        settingsRepository.setVoiceVoxServerUrl("http://localhost:50021")
        assertEquals("http://localhost:50021", settingsRepository.getVoiceVoxServerUrl())
        // set invalid url (won't change the value)
        settingsRepository.setVoiceVoxServerUrl("invalid url")
        assertEquals("http://localhost:50021", settingsRepository.getVoiceVoxServerUrl())
    }
}
