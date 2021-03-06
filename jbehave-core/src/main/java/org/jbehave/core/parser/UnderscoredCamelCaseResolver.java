package org.jbehave.core.parser;

import static java.util.regex.Pattern.compile;

import java.util.regex.Matcher;

import org.jbehave.core.RunnableStory;

/**
 * <p>
 * Resolves story paths converting the camel-cased Java core class to
 * lower-case underscore-separated paths e.g.:
 * "org.jbehave.core.ICanLogin.java" -> "org/jbehave/core/i_can_login".
 * </p>
 * <p>
 * By default no extension is used, but this can be configured via the
 * constructor.  So with extension ".story", we can resolve the class to e.g.
 * "org/jbehave/core/i_can_login.story".
 * </p>
 * <p>
 * The default resolution pattern {@link #NUMBERS_AS_LOWER_CASE_LETTERS_PATTERN}
 * treats numbers as lower case letters, eg:
 * "org.jbehave.core.ICanLoginTo1Site.java" ->
 * "org/jbehave/core/i_can_login_to1_site"
 * </p>
 * <p>
 * Choose {@link #NUMBERS_AS_UPPER_CASE_LETTERS_PATTERN} to treat numbers as
 * upper case letters, eg: "org.jbehave.core.ICanLoginTo1Site.java" ->
 * "org/jbehave/core/i_can_login_to_1_site"
 * </p>
 */
public class UnderscoredCamelCaseResolver extends AbstractStoryPathResolver {

	public static final String NUMBERS_AS_LOWER_CASE_LETTERS_PATTERN = "([A-Z].*?)([A-Z]|\\z)";
	public static final String NUMBERS_AS_UPPER_CASE_LETTERS_PATTERN = "([A-Z0-9].*?)([A-Z0-9]|\\z)";
	private static final String UNDERSCORE = "_";
	private final String resolutionPattern;
    private String wordToRemove = "";

    public UnderscoredCamelCaseResolver() {
		this(DEFAULT_EXTENSION);
	}

	public UnderscoredCamelCaseResolver(String extension) {
		this(extension, NUMBERS_AS_LOWER_CASE_LETTERS_PATTERN);
	}

	public UnderscoredCamelCaseResolver(String extension,
			String resolutionPattern) {
		super(extension);
		this.resolutionPattern = resolutionPattern;
	}

	@Override
	protected String resolveName(
			Class<? extends RunnableStory> storyClass) {
        String simpleName = storyClass.getSimpleName();
        simpleName = simpleName.replace(wordToRemove, "");
        Matcher matcher = compile(resolutionPattern).matcher(
                simpleName);
		int startAt = 0;
		StringBuilder builder = new StringBuilder();
		while (matcher.find(startAt)) {
			builder.append(matcher.group(1).toLowerCase());
			builder.append(UNDERSCORE);
			startAt = matcher.start(2);
		}
		return builder.substring(0, builder.length() - 1);
	}

    public StoryPathResolver removeFromClassName(String wordToRemove) {
        this.wordToRemove = wordToRemove;
        return this;
    }
}
