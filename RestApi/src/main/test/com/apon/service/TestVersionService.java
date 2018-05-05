package com.apon.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for VersionService. There is no need to extend BaseTest, since there are no database functions.
 */
public class TestVersionService {
    private VersionService versionService;

    @Before
    public void init() {
        versionService = new VersionService();
    }

    @Test
    public void getVersion() {
        assertEquals(versionService.getVersion().getValue(), versionService.VERSION);
    }

    @Test
    public void getSecuredVersion() {
        assertEquals(versionService.getSecuredVersion().getValue(), versionService.VERSION);
    }
}
