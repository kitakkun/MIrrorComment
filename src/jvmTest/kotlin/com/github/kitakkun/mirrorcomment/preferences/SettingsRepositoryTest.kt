package com.github.kitakkun.mirrorcomment.preferences

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SettingsRepositoryTest {
    private lateinit var settingsRepository: SettingsRepository
    private val propertiesFilename = "dummysettings.properties"

    @Before
    fun setUp() {
        settingsRepository = SettingsRepository(propertiesFilename)
    }

    @After
    fun tearDown() {
        File(propertiesFilename).delete()
    }

    @Test
    fun testVoiceVoxServerUrlConfig() {
        // default is null
        assertNull(settingsRepository.getVoiceVoxServerUrl())
        // set valid url
        settingsRepository.setVoiceVoxServerUrl("http://localhost:50021")
        assertEquals("http://localhost:50021", settingsRepository.getVoiceVoxServerUrl())
        // set invalid url
        settingsRepository.setVoiceVoxServerUrl("invalid url")
        assertNull(settingsRepository.getVoiceVoxServerUrl())
    }
}
