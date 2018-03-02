package org.randoom.setlx.SetlXMusic.MidiManager;

import org.jfugue.pattern.Pattern;
import org.junit.Before;
import org.junit.Test;
import org.randoom.setlx.SetlXMusic.Patterns.SetlXPatternManager.MusicManager;
import org.randoom.setlx.SetlXMusic.SetlXMusicPlayer.MusicPlayer;

import static org.junit.Assert.*;

public class MidiManagerTest {

    Pattern testPattern;
    MidiManager manager;
    MusicPlayer Player;
    @Before
    public void setUp() throws Exception {
      testPattern = new Pattern("C D E F G A B H");
      manager = new MidiManager();
      Player = new MusicPlayer(new MusicManager());
    }

    @Test
    public void load() {
        manager.save("test.mid", testPattern); //Assuming correctness of this method.
        Pattern loadedPattern = manager.load("test.mid");
        assertNotNull(loadedPattern);
        // loadedPattern = manager.load("Fugue12.mid");
        // manager.save("test2", loadedPattern);
    }

    @Test
    public void save() {
        manager.save("", testPattern);
    }

    @Test
    public void hasFileEnding() {
        assertTrue(manager.hasFileEnding("test.mid"));
        assertFalse(manager.hasFileEnding("test"));
        assertFalse(manager.hasFileEnding(""));
    }
}