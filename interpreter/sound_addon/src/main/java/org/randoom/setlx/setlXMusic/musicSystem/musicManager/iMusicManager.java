package org.randoom.setlx.setlXMusic.musicSystem.musicManager;

import org.jfugue.pattern.Pattern;
import org.jfugue.pattern.PatternProducer;
import org.jfugue.rhythm.Rhythm;
import org.jfugue.theory.ChordProgression;
import org.jfugue.tools.GetPatternStats;
import org.randoom.setlx.setlXMusic.musicSystem.exceptions.CanNotConvertException;
import org.randoom.setlx.setlXMusic.musicSystem.exceptions.KeyAlreadyInUseException;
import org.randoom.setlx.setlXMusic.musicSystem.exceptions.MidiExceptions.NotAPatternException;
import org.randoom.setlx.setlXMusic.musicSystem.exceptions.MidiExceptions.SetlXIOException;
import org.randoom.setlx.setlXMusic.musicSystem.exceptions.NullArgumentsException;
import org.randoom.setlx.setlXMusic.musicSystem.exceptions.ProducerNotFoundExceptions.PatternNotFoundException;
import org.randoom.setlx.setlXMusic.musicSystem.exceptions.ProducerNotSupportedException;
import org.randoom.setlx.setlXMusic.musicSystem.storages.PatternParameters;
import org.randoom.setlx.setlXMusic.musicSystem.storages.StorageTypes;
import org.randoom.setlx.exceptions.SetlException;

import java.util.HashMap;

/**
 * This Manager holds and manages patterns that can be created and used by setlx.
 * It is the main interface to all musical content available during runtime.
 */
public interface iMusicManager {

    /**
     * Adds a new pattern to this Music Manager. patterns can then be e. g. played or modified.
     *
     * @param name
     * @param pattern
     */
    void add(String name, PatternProducer pattern) throws NullArgumentsException, ProducerNotSupportedException, KeyAlreadyInUseException;

    /**
     * Can be used to add notes to an existing pattern.
     *
     * @param patternName
     * @param notePattern
     */
    void addNotesToPattern(String patternName, String notePattern) throws PatternNotFoundException;

    /**
     * Adds existing patterns to another pattern.
     * Consequently, you can build up your songs using smaller bricks: That patterns.
     * <p>
     * /*              song
     * /*          <           >
     * /*       pattern3     pattern4
     * /*     <        >
     * /*    p1        p2
     * <p>
     * The musical terms anologue to this are motives, themes, parts and movements.
     *
     * @param patternTargetName
     * @param patternSourceNames
     */
    void addPatternsToPatternByName(String patternTargetName, String... patternSourceNames) throws PatternNotFoundException;

    /**
     * Adds
     * @param patternName
     * @param patterns
     * @throws PatternNotFoundException
     */
    void addPatternsToPattern(String patternName, PatternProducer... patterns) throws PatternNotFoundException;

    /**
     * Can be used to add notes to an existing pattern and specify, how often it should be repeated.
     *
     * @param patternName
     * @param notePattern
     * @param repetitions
     */
    void addNotesToPattern(String patternName, String notePattern, int repetitions) throws PatternNotFoundException;

    /**
     * Modifies a property of a pattern.
     *
     * @param patternName
     * @param param
     * @param value
     */
    void modifyPatternProperty(String patternName, PatternParameters param, int value) throws SetlException;

    /**
     * Deletes an element with the given name, if it is stored in one of the storages.
     *
     * @param elementName: The name of the element to be deleted: Can be from Rhythm, ChordProgression or Pattern storage
     */
    void removeElement(String elementName) throws PatternNotFoundException;

    /**
     * Returns a specific pattern from the storage
     *
     * @param patternName
     * @return
     */
    Pattern getPattern(String patternName) throws PatternNotFoundException;

    HashMap<String, Pattern> getAllPatterns();

    /**
     * Duplicates an existing pattern. It might loose explicit set settings.
     * There are many usecases for this:
     * 1) A pattern is used in different voices at the same time
     * 2) A pattern is transposed. This is useful, when making chords or just play the melody in an
     * other tonality.
     *
     * @param newName The name of the duplicate pattern
     */
    void duplicatePattern(String sourceName, String newName) throws PatternNotFoundException, NullArgumentsException, KeyAlreadyInUseException;

    /**
     * Returns a Hashmap containing detailed statistics for a pattern. This includes
     * Harmonic, Interval, Pitch, Rest, Duration and Rythmic N, Average, SD and Range.
     *
     * @param patternName
     * @return
     * @throws PatternNotFoundException
     */
    HashMap<String, GetPatternStats.Stats> getDetailPatternStats(String patternName) throws PatternNotFoundException;

    /**
     * Returns general statistics for a pattern. These basic metrics include number of notes, rests and measures
     *
     * @param patternName
     * @return Array index 0: N of Notes; index 1: N of rests; index 2: N of measures
     * @throws PatternNotFoundException
     */
    int[] getGeneralPatternStats(String patternName) throws PatternNotFoundException;

    Rhythm getRhythm(String rhythmName) throws PatternNotFoundException;

    ChordProgression getChordProgression(String progressionName) throws PatternNotFoundException;

    HashMap<String, Rhythm> getAllRhythms();

    HashMap<String, ChordProgression> getAllChordProgressions();

    /**
     * Returns the storage, where the key is used
     *
     * @param key
     * @return
     */
    StorageTypes getStorageWhereKeyIsUsed(String key) throws PatternNotFoundException;


    ChordProgression eachChordAs(String chordprogressionName, String sequence) throws PatternNotFoundException;

    /**
     * Requires passing a string that has dollar signs followed by an index, in which case each dollar+index will be replaced by the indexed chord of the chord progression. For example, given a ChordProgression of "I IV V" and a string of "$0q $1h $2w", will return "C4MAJq F4MAJh G4MAJw". Using the underscore character instead of an index will result in the pattern of the ChordProgression itself added to the string. The final result will be returned from the getPattern() method.
     *
     * @param chordprogressionName The Name of the saved chord Progression
     * @param sequence
     * @return
     * @throws PatternNotFoundException
     */
    ChordProgression allChordsAs(String chordprogressionName, String sequence) throws PatternNotFoundException;

    /**
     * Saves a Rhythm Progression or a Chord Progression as a Pattern
     * @param elementName
     * @throws PatternNotFoundException
     * @throws NullArgumentsException
     * @throws CanNotConvertException
     */
    void saveAsPattern(String elementName) throws PatternNotFoundException, NullArgumentsException, CanNotConvertException;

    /**
     * Saves a pattern as a *.mid file at the local file system.
     *
     * @param elementName the name of the element fo be saved
     * @param filename
     */
    void saveAsMidi(String elementName, String filename) throws CanNotConvertException, PatternNotFoundException, NullArgumentsException, NotAPatternException, SetlXIOException;

    /**
     * Loads a midi file from local filesystem into a new pattern.
     *
     * @param patternName
     * @param filename
     */
    void loadMidi(String patternName, String filename) throws NullArgumentsException, SetlXIOException, org.randoom.setlx.setlXMusic.musicSystem.exceptions.MidiExceptions.InvalidMidiDataException;

    /**
     * Checks, weather all patterns, that are given in an array of String-names does already exist in the
     * storage.
     * @param patternNames
     * @return false iff at least one pattern does not exist with the given patternnames
     */
    boolean allPatternsExists(String... patternNames);
}
