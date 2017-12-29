package org.randoom.setlx.SetlXMusic.Patterns;

import org.jfugue.pattern.Pattern;
import org.randoom.setlx.Exceptions.NullArgumentsException;

import java.util.HashMap;

/**
 *
 */
public class SetlXSetlXPatternStorage implements iSetlXPatternStorage {

    HashMap<String, Pattern> patternStorage = new HashMap<>();

    @Override
        public void addPattern(String name, Pattern pattern) throws NullArgumentsException {
            if(name!=null&&pattern!=null) {
                patternStorage.put(name, pattern);
            }else{
                throw new NullArgumentsException();
            }
    }

    @Override
    public boolean checkExisting(String name) {
        return patternStorage.containsKey(name);
    }

    @Override
    public Pattern getPattern(String name) {
        return patternStorage.get(name);
    }

    @Override
    public void deletePattern(String name) {
        patternStorage.remove(name);
    }

    @Override
    public HashMap<String, Pattern> getAllPatterns() {
        return patternStorage;
    }


}