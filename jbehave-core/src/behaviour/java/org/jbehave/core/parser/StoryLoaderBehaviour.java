package org.jbehave.core.parser;

import org.jbehave.core.errors.InvalidStoryResourceException;
import org.jbehave.core.errors.StoryNotFoundException;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.Matchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

public class StoryLoaderBehaviour {


    @Test
    public void canLoadStoryFromClasspath() {
        // Given
        String storyPath = "org/jbehave/core/parser/stories/my_pending_story";
        String storyAsString = "Given my step";

        // When
        StoryLoader loader = new LoadFromClasspath();
        ensureThat(loader.loadStoryAsText(storyPath), equalTo(storyAsString));

    }

    @Test(expected = StoryNotFoundException.class)
    public void cannotDefineStoryWithClasspathLoadingForInexistentResource() {

        StoryLoader loader = new LoadFromClasspath();
        loader.loadStoryAsText("inexistent.story");

    }

    @Test(expected = InvalidStoryResourceException.class)
    public void cannotDefineStoryWithClasspathLoadingForInvalidResource() {

        StoryLoader loader = new LoadFromClasspath(new InvalidClassLoader());
        loader.loadStoryAsText("inexistent.story");

    }

    static class InvalidClassLoader extends ClassLoader {

        @Override
        public InputStream getResourceAsStream(String name) {
            return new InputStream() {

                public int available() throws IOException {
                    return 1;
                }

                @Override
                public int read() throws IOException {
                    throw new IOException("invalid");
                }

            };
        }
    }


    @Test
    public void canDefineStoryWithURLLoading() {
        // Given
        String codeLocation = new StoryLocation("", this.getClass()).getCodeLocation().getFile();
        String storyPath = "file:" + codeLocation + "org/jbehave/core/parser/stories/my_pending_story";
        String storyAsString = "Given my step";
 
        // When
        StoryLoader loader = new LoadFromURL();
        ensureThat(loader.loadStoryAsText(storyPath), equalTo(storyAsString));
    }

    @Test(expected = InvalidStoryResourceException.class)
    public void cannotDefineStoryWithURLLoadingForInexistentResource() {
        // Given
        String codeLocation = new StoryLocation("", this.getClass()).getCodeLocation().getFile();
        String storyPath = "file:" + codeLocation + "inexistent_story";

        // When
        StoryLoader loader = new LoadFromURL();
        loader.loadStoryAsText(storyPath);
        
        // Then
        // fail as expected

    }

    @Test(expected = InvalidStoryResourceException.class)
    public void cannotDefineStoryWithURLLoadingForInvalidURL() {
        // Given
        String codeLocation = new StoryLocation("", this.getClass()).getCodeLocation().getFile();
        String storyPath = "file:" + codeLocation + "inexistent_story";

        // When
        StoryLoader loader = new LoadFromURL();
        loader.loadStoryAsText(storyPath);

        // Then
        // fail as expected
    }

}