package Parser;

import java.util.ArrayList;
import java.util.List;

import Parser.IncorrectFormatException.ErrorCode;
import Tester.*;

public final class CSSParser {

	/**
	 * Reads CSS as a String and returns back a list of Rules.
	 * 
	 * @param css A String representation of CSS.
	 * @return A list of Rules
	 * @throws Exception If any errors occur.
	 */

	public static List<Rule> parse(final String css) throws Exception {

		CSSParser parser = new CSSParser();

		List<Rule> rules = new ArrayList<Rule>();

		if (css == null || css.trim().length() == 0) {
			return rules;
		}

		for (int i = 0; i < css.length(); i++) {
			char c = css.charAt(i);
                        column++;
                        if(c=='\n') {line++; column=1;}

			if (i < css.length() - 1) {

				char nextC = css.charAt(i + 1);
				parser.parse(rules, c, nextC);

			} else {

				parser.parse(rules, c, null);

			}

		}

		return rules;
	}

	private final List<SelLine> selectorNames;
	private String selectorName,lastSelectorName;
	private String propertyName;
	private String valueName;
	//	private Map<String, String> map;
	private final List<PropertyValue> values;
	private State state;
	private Character previousChar;
	private State beforeCommentMode;
        private static int line,column;
        
        private class SelLine{
            public String selector;
            public int line;
            
            SelLine(String name,int line){
                this.selector = name;
                this.line = line;
            }
        }

	/**
	 * Creates a new parser.
	 */

	private CSSParser() {
		this.selectorName = "";
		this.propertyName = "";
        this.lastSelectorName = "";
		this.valueName = "";
		this.values = new ArrayList<PropertyValue>();
		this.state = State.INSIDE_SELECTOR;
		this.previousChar = null;
		this.beforeCommentMode = null;
		this.selectorNames = new ArrayList<SelLine>();
        this.line=1;
        this.column=1;
	}

	/**
	 * Main parse logic.
	 * 
	 * @param rules The list of rules.
	 * @param c The current currency.
	 * @param nextC The next currency (or null).
	 * @throws Exception If any errors occurs.
	 */

	private void parse(final List<Rule> rules, final Character c, final Character nextC) throws Exception {

		// Special case if we find a comment
		if (Chars.SLASH.equals(c) && Chars.STAR.equals(nextC)) {

			// It's possible to find a comment in a comment
			if (state != State.INSIDE_COMMENT) {
				beforeCommentMode = state;
			}

			state = State.INSIDE_COMMENT;
		}

		switch (state) {

			case INSIDE_SELECTOR: {
				parseSelector(c);
				break;
			}
			case INSIDE_COMMENT: {
				parseComment(c);
				break;
			}
			case INSIDE_PROPERTY_NAME: {
				parsePropertyName(rules, c);
				break;
			}
			case INSIDE_VALUE: {
				parseValue(c);
				break;
			}
			case INSIDE_VALUE_ROUND_BRACKET: {
				parseValueInsideRoundBrackets(c);
				break;
			}

		}

		// Save the previous character
		previousChar = c;

	}

	/**
	 * Parse a value.
	 * 
	 * @param c The current character.
	 * @throws IncorrectFormatException If any errors occur.
	 */

	private void parseValue(final Character c) throws IncorrectFormatException {

		if (Chars.SEMI_COLON.equals(c)) {

			// Store it in the values map
			PropertyValue pv = new PropertyValue(propertyName.trim(), valueName.trim(),line);
			values.add(pv);
			propertyName = "";
			valueName = "";

			state = State.INSIDE_PROPERTY_NAME;
			return;

		} else if (Chars.ROUND_BRACKET_BEG.equals(c)) {

			valueName += Chars.ROUND_BRACKET_BEG;

			state = State.INSIDE_VALUE_ROUND_BRACKET;
			return;

		} else if (Chars.COLON.equals(c)) {

			throw new IncorrectFormatException(ErrorCode.FOUND_COLON_WHILE_READING_VALUE, "The value '" + valueName.replace("\n","") + "' for property '" + propertyName.trim() + "' in the selector '" + lastSelectorName.trim() + "' had a ':' character.",line,column);

		} else if (Chars.BRACKET_END.equals(c)) {

			throw new IncorrectFormatException(ErrorCode.FOUND_END_BRACKET_BEFORE_SEMICOLON, "The value '" + valueName.replace("\n","") + "' for property '" + propertyName.trim() + "' in the selector '" + lastSelectorName.trim() + "' should end with an ';', not with '}'.",line,column);

		} else {

			valueName += c;
			return;

		}

	}

	/**
	 * Parse value inside a round bracket (
	 * 
	 * @param c The current character.
	 * @throws IncorrectFormatException If any error occurs.
	 */

	private void parseValueInsideRoundBrackets(final Character c) throws IncorrectFormatException {

		if (Chars.ROUND_BRACKET_END.equals(c)) {

			valueName += Chars.ROUND_BRACKET_END;
			state = State.INSIDE_VALUE;
			return;

		} else {

			valueName += c;
			return;

		}

	}

	/**
	 * Parse property name.
	 * 
	 * @param rules The list of rules.
	 * @param c The current character.
	 * @throws IncorrectFormatException If any error occurs
	 */

	private void parsePropertyName(final List<Rule> rules, final Character c) throws IncorrectFormatException {

		if (Chars.COLON.equals(c)) {

			state = State.INSIDE_VALUE;
			return;

		} else if (Chars.SEMI_COLON.equals(c)) {

			throw new IncorrectFormatException(ErrorCode.FOUND_SEMICOLON_WHEN_READING_PROPERTY_NAME, "Unexpected character '" + c + "' for property '" + propertyName.trim() + "' in the selector '" + lastSelectorName.trim() + "' should end with an ';', not with '}'.",line,column);

		} else if (Chars.BRACKET_END.equals(c)) {

			Rule rule = new Rule();
			for (SelLine s : selectorNames) {
				Selector selector = new Selector(s.selector.trim(),s.line);
				rule.addSelector(selector);
			}
			selectorNames.clear();
			for (PropertyValue pv : values) {
				rule.addPropertyValue(pv);
			}
			values.clear();

			if (!rule.getPropertyValues().isEmpty()) {
				rules.add(rule);
			}

			state = State.INSIDE_SELECTOR;

		} else {

			propertyName += c;
			return;

		}

	}

	/**
	 * Parse a selector.
	 * 
	 * @param c The current character.
	 */

	private void parseComment(final Character c) {

		if (Chars.STAR.equals(previousChar) && Chars.SLASH.equals(c)) {

			state = beforeCommentMode;
			return;

		}

	}

	/**
	 * Parse a selector.
	 * 
	 * @param c The current character.
	 * @throws IncorrectFormatException If an error occurs.
	 */

	private void parseSelector(final Character c) throws IncorrectFormatException {

		if (Chars.BRACKET_BEG.equals(c)) {

			state = State.INSIDE_PROPERTY_NAME;
                        if (selectorName.trim().length() == 0) {
				throw new IncorrectFormatException(ErrorCode.FOUND_COLON_WHEN_READING_SELECTOR_NAME, "Found an ',' in a selector name without any actual name before it.",line,column);
			}

			selectorNames.add(new SelLine(selectorName.trim(),line));
                        lastSelectorName = selectorName;
			selectorName = "";
			return;

		} else if (Chars.COMMA.equals(c)) {

			if (selectorName.trim().length() == 0) {
				throw new IncorrectFormatException(ErrorCode.FOUND_COLON_WHEN_READING_SELECTOR_NAME, "Found an ',' in a selector name without any actual name before it.",line,column);
			}

			selectorNames.add(new SelLine(selectorName.trim(),line));
                        lastSelectorName = selectorName;
			selectorName = "";

		} else {

			selectorName += c;
			return;

		}

	}

}
