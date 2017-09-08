/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */

package Parser;

/**
 * An exception that is thrown when the CSS parser finds a character it shouldn't have.
 * 
 * @author <a href="mailto:christoffer@christoffer.me">Christoffer Pettersson</a>
 */

public class IncorrectFormatException extends Exception {

	private static final long serialVersionUID = 1L;

	private final ErrorCode errorCode;
        private final String message;
        private int line,column;

	/**
	 * Creates a new IncorrectFormatExeption with an error message;
	 * 
	 * @param errorCode S unique error code associated with the error.
	 * @param message Error message describing the problem.
	 */

	IncorrectFormatException(final ErrorCode errorCode, final String message) {
		super(message);
                this.message = message;
		this.errorCode = errorCode;
	}
        
        IncorrectFormatException(final ErrorCode errorCode, final String message,final int line,final int col) {
		super(message);
                this.message = message;
		this.errorCode = errorCode;
                this.line=line;
                this.column = col;
	}

	/**
	 * Returns a unique error code associated with the error.
	 * 
	 * @return A unique error code associated with the error.
	 */
        
	public ErrorCode getErrorCode() {
		return errorCode;
	}
        
        public String getErrorMessage(){
            return errorCode.toString();
        }
        
        @Override
        public String toString(){
            return message+" at Line No: "+line+" Col No: "+column;
        }
        
        public String getEMessage(){
            return message;
        }
        
        public int getLineNo(){
            return line;
        }
        
        public int getColumn(){
            return column;
        }

	/**
	 * List of unique error codes.
	 * 
	 * @author <a href="mailto:christoffer@christoffer.me">Christoffer Pettersson</a>
	 */

	public enum ErrorCode {

		/**
		 * When the parse founds a semicolon ; when reading the property name.
		 */
		FOUND_SEMICOLON_WHEN_READING_PROPERTY_NAME,

		/**
		 * When the parse founds an end bracket } before the value's semicolon ; ending.
		 */
		FOUND_END_BRACKET_BEFORE_SEMICOLON,

		/**
		 * When the parse founds a colon , before reading a real selector name.
		 */
		FOUND_COLON_WHEN_READING_SELECTOR_NAME,

		/**
		 * When the parse the value a colon : was found.
		 */
		FOUND_COLON_WHILE_READING_VALUE;

	}

}
