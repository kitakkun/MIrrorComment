package com.github.kitakkun.mirrorcomment.preferences

import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SettingsPropertiesRepositoryTest {
    private lateinit var settingsPropertiesRepository: SettingsPropertiesRepository
    private val propertiesFilename = "dummysettings.properties"

    @Before
    fun setUp() {
        settingsPropertiesRepository = SettingsPropertiesRepository(propertiesFilename)
    }

    @After
    fun tearDown() {
        File(propertiesFilename).delete()
    }

    @Test
    fun testVoiceVoxServerUrlConfig() {
        // default is null
        assertNull(settingsPropertiesRepository.getVoiceVoxServerUrl())
        // set valid url
        settingsPropertiesRepository.setVoiceVoxServerUrl("http://localhost:50021")
        assertEquals("http://localhost:50021", settingsPropertiesRepository.getVoiceVoxServerUrl())
        // set invalid url
        settingsPropertiesRepository.setVoiceVoxServerUrl("invalid url")
        assertNull(settingsPropertiesRepository.getVoiceVoxServerUrl())
    }
}
